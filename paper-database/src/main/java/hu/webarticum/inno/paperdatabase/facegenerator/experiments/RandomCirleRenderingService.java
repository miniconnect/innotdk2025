package hu.webarticum.inno.paperdatabase.facegenerator.experiments;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL33.*;

import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;

import hu.webarticum.inno.paperdatabase.facegenerator.GlRenderingService;
import hu.webarticum.inno.paperdatabase.facegenerator.GlRenderingService.DrawContext;
import jakarta.inject.Singleton;

@Singleton
public class RandomCirleRenderingService {

    private static final int IMAGE_WIDTH = 350;
    private static final int IMAGE_HEIGHT = 500;
    
    private final GlRenderingService glRenderingService;
    
    public RandomCirleRenderingService(GlRenderingService glRenderingService) {
        this.glRenderingService = glRenderingService;
    }

    public CompletableFuture<BufferedImage> render() {
        return glRenderingService.submit(IMAGE_WIDTH, IMAGE_HEIGHT, this::drawOnGl);
    }
    
    private void drawOnGl(DrawContext ctx) {
        glOrtho(0, ctx.width, ctx.height, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glClearColor(0.0f, 0.2f, 0.4f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        glColor3f(1.0f, 0.2f, 0.4f);
        float radius = (float) ((Math.random() * 40) + 10);
        float cx = (float) (Math.random() * (IMAGE_WIDTH - (radius * 2))) + radius;
        float cy = (float) (Math.random() * (IMAGE_HEIGHT - (radius * 2))) + radius;
        drawCircle(cx, cy, radius);
    }
    
    private void drawCircle(float cx, float cy, float radius) {
        int numSegments = 100;

        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(cx, cy);

        for (int i = 0; i <= numSegments; i++) {
            double angle = 2.0 * Math.PI * i / numSegments;
            float x = (float)(cx + Math.cos(angle) * radius);
            float y = (float)(cy + Math.sin(angle) * radius);
            glVertex2f(x, y);
        }
        glEnd();
    }
    
}
