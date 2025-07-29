package hu.webarticum.inno.paperdatabase.facegenerator.experiments;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.CompletableFuture;

import org.lwjgl.BufferUtils;

import hu.webarticum.inno.paperdatabase.facegenerator.GlRenderingService;
import hu.webarticum.inno.paperdatabase.facegenerator.GlRenderingService.DrawContext;
import jakarta.inject.Singleton;

@Singleton
public class TextureRenderingService {

    private static final int IMAGE_WIDTH = 350;
    private static final int IMAGE_HEIGHT = 500;
    
    private static final String TEXTURE_IMAGE_PATH = "/tmp/textures/test-texture.png";
    
    private final GlRenderingService glRenderingService;
    
    public TextureRenderingService(GlRenderingService glRenderingService) {
        this.glRenderingService = glRenderingService;
    }

    public CompletableFuture<BufferedImage> render() {
        return glRenderingService.submit(IMAGE_WIDTH, IMAGE_HEIGHT, this::drawOnGl);
    }

    private void drawOnGl(DrawContext ctx) {
        init();
        draw();
        uninit();
    }
    

    private int shaderProgram;
    private int vao;
    private int vbo;
    private int texture;

    public void init() {
        shaderProgram = createShaderProgram();
        vbo = glGenBuffers();
        vao = createVAO(vbo);
        try {
            texture = loadTexture(TEXTURE_IMAGE_PATH);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private void uninit() {
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        glDeleteProgram(shaderProgram);
        glDeleteTextures(texture);
    }

    private void draw() {
        glClearColor(0.0f, 0.2f, 0.4f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        
        glUseProgram(shaderProgram);
        
        int texLocation = glGetUniformLocation(shaderProgram, "uTex");
        glUniform1i(texLocation, 0);
        
        int projectionLocation = glGetUniformLocation(shaderProgram, "uProjection");
        float[] ortho = createOrthoMatrix(0, IMAGE_WIDTH, 0, IMAGE_HEIGHT); // top-left origin
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrixBuffer.put(ortho).flip();
        glUniformMatrix4fv(projectionLocation, false, matrixBuffer);
        
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        
        glUseProgram(0);
    }

    private int createVAO(int vbo) {
        float xDiff = (float) (Math.random() * 100); 
        float yDiff = (float) (Math.random() * 100); 
        float[] vertices = {
                50f + xDiff, 300f + yDiff, 0.0f, 1.0f,
                250f + xDiff, 300f + yDiff, 1.0f, 1.0f,
                150f + xDiff, 100f + yDiff,  0.5f, 0.0f,
        };

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        return vao;
    }

    private int createShaderProgram() {
        String vertexShader = "#version 330 core\n"
                + "            layout (location = 0) in vec2 aPos;\n"
                + "            layout (location = 1) in vec2 aUV;\n"
                + "            uniform mat4 uProjection;\n"
                + "            out vec2 vUV;\n"
                + "            void main() {\n"
                + "                vUV = aUV;\n"
                + "                gl_Position = uProjection * vec4(aPos, 0.0, 1.0);\n"
                + "            }";

        String fragmentShader = "#version 330 core\n"
                + "            in vec2 vUV;\n"
                + "            out vec4 FragColor;\n"
                + "            uniform sampler2D uTex;\n"
                + "            void main() {\n"
                + "                FragColor = texture(uTex, vUV);\n"
                + "            }";

        int vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, vertexShader);
        glCompileShader(vs);
        checkShader(vs);

        int fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs, fragmentShader);
        glCompileShader(fs);
        checkShader(fs);

        int program = glCreateProgram();
        glAttachShader(program, vs);
        glAttachShader(program, fs);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Link error: " + glGetProgramInfoLog(program));
        }

        glDeleteShader(vs);
        glDeleteShader(fs);

        return program;
    }

    private void checkShader(int shader) {
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader error: " + glGetShaderInfoLog(shader));
        }
    }

    private int loadTexture(String imagePath) throws IOException {
        ByteBuffer imageBuffer = BufferUtils.createByteBuffer(1024 * 1024);

        try (InputStream is = new FileInputStream(imagePath)) {
            byte[] bytes = is.readAllBytes();
            imageBuffer.put(bytes);
            imageBuffer.flip();
        }

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);
        
        //stbi_set_flip_vertically_on_load(true);
        
        ByteBuffer data = stbi_load_from_memory(imageBuffer, w, h, c, 4);
        if (data == null) throw new IOException("Failed to load image: " + stbi_failure_reason());

        int tex = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, tex);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, w.get(), h.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(data);
        return tex;
    }

    private float[] createOrthoMatrix(float left, float right, float top, float bottom) {
        return new float[] {
                2f / (right - left), 0f, 0f, 0f,
                0f, 2f / (top - bottom), 0f, 0f,
                0f, 0f, -1f, 0f,
                -(right + left) / (right - left), -(top + bottom) / (top - bottom), 0f, 1f
        };
    }
    
}
