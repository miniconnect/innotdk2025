package hu.webarticum.inno.paperdatabase.facegenerator;

import org.lwjgl.*;
import org.lwjgl.opengl.*;

import jakarta.inject.Singleton;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

@Singleton
public class GlRenderingService {

    @FunctionalInterface
    public interface GLRenderTask {
        void render(DrawContext ctx);
    }

    public static class GlobalContext {

        public final int fbo;
        public final int tex;

        public GlobalContext(int fbo, int tex) {
            this.fbo = fbo;
            this.tex = tex;
        }
        
    }

    public static class DrawContext {
        
        public final int width;
        public final int height;
        public final GlobalContext global;

        public DrawContext(int width, int height, GlobalContext global) {
            this.width = width;
            this.height = height;
            this.global = global;
        }
        
    }

    public static class RenderJob {
        
        private final int width;
        private final int height;
        private final GLRenderTask task;
        private final CompletableFuture<BufferedImage> result;
        
        private RenderJob(int width, int height, GLRenderTask task, CompletableFuture<BufferedImage> result) {
            this.width = width;
            this.height = height;
            this.task = task;
            this.result = result;
        }
        
    }

    private final BlockingQueue<RenderJob> jobQueue = new LinkedBlockingQueue<>();
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Thread renderThread;

    public GlRenderingService() {
        renderThread = new Thread(this::runLoop, getClass().getSimpleName());
        renderThread.start();
    }

    public CompletableFuture<BufferedImage> submit(int width, int height, GLRenderTask task) {
        CompletableFuture<BufferedImage> result = new CompletableFuture<>();
        jobQueue.add(new RenderJob(width, height, task, result));
        return result;
    }

    public void shutdown() {
        running.set(false);
        renderThread.interrupt();
    }

    private void runLoop() {
        initGlBeforeLoop();
        int fbo = glGenFramebuffers();
        int tex = glGenTextures();
        GlobalContext global = new GlobalContext(fbo, tex);
        while (running.get()) {
            RenderJob job;
            try {
                job = jobQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            DrawContext ctx = new DrawContext(job.width, job.height, global);
            renderAndProvideImage(job, ctx);
        }
        
        cleanUpGlAfterLoop(global);
    }

    private void initGlBeforeLoop() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to init GLFW");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long window = glfwCreateWindow(1, 1, "", NULL, NULL);
        if (window == NULL) {
            throw new IllegalStateException("GLFW window creation failed");
        }
        
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    private void renderAndProvideImage(RenderJob job, DrawContext ctx) {
        try {
            initGlBeforeRender(ctx);
            job.task.render(ctx);
            BufferedImage img = extractImage(ctx);
            job.result.complete(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGlBeforeRender(DrawContext ctx) {
        glBindTexture(GL_TEXTURE_2D, ctx.global.tex);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, ctx.width, ctx.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glBindFramebuffer(GL_FRAMEBUFFER, ctx.global.fbo);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, ctx.global.tex, 0);
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException("FBO incomplete");
        }
        
        glViewport(0, 0, ctx.width, ctx.height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, ctx.width, ctx.height, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private BufferedImage extractImage(DrawContext ctx) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(ctx.width * ctx.height * 4);
        glReadPixels(0, 0, ctx.width, ctx.height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        BufferedImage img = new BufferedImage(ctx.width, ctx.height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < ctx.height; y++) {
            for (int x = 0; x < ctx.width; x++) {
                int i = (x + (ctx.height - y - 1) * ctx.width) * 4;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                int a = buffer.get(i + 3) & 0xFF;
                img.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | (b));
            }
        }
        
        return img;
    }
    
    private void cleanUpGlAfterLoop(GlobalContext global) {
        glDeleteTextures(global.tex);
        glDeleteFramebuffers(global.fbo);
        glfwTerminate();
    }

}
