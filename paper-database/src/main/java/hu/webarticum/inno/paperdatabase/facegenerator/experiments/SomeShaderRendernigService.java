package hu.webarticum.inno.paperdatabase.facegenerator.experiments;

import static org.lwjgl.opengl.GL33.*;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.concurrent.CompletableFuture;

import org.lwjgl.BufferUtils;

import hu.webarticum.inno.paperdatabase.facegenerator.GlRenderingService;
import hu.webarticum.inno.paperdatabase.facegenerator.GlRenderingService.DrawContext;
import jakarta.inject.Singleton;

@Singleton
public class SomeShaderRendernigService {

    private static final int IMAGE_WIDTH = 350;
    private static final int IMAGE_HEIGHT = 500;
    
    private final GlRenderingService glRenderingService;
    
    public SomeShaderRendernigService(GlRenderingService glRenderingService) {
        this.glRenderingService = glRenderingService;
    }

    public CompletableFuture<BufferedImage> render() {
        return glRenderingService.submit(IMAGE_WIDTH, IMAGE_HEIGHT, this::drawOnGl);
    }
    
    private void drawOnGl(DrawContext ctx) {
        clearScreen();
        int shaderProgram = createShaderProgram();
        int vao = initVertexArrayObject();
        int vbo = initVertexBufferObject();
        float[] vertices = createTargetVertices();
        putToArrayBuffer(vertices);
        linkFloatLayoutAttribute(0, 6, 0, 3);
        linkFloatLayoutAttribute(1, 6, 3, 3);
        glUseProgram(shaderProgram);
        float[] ortho = createOrthoMatrix(0, IMAGE_WIDTH, 0, IMAGE_HEIGHT);
        putUniformMatrix(shaderProgram, "uProjection", ortho);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        uninit(vbo, vao, shaderProgram);
    }
    
    private void clearScreen() {
        glClearColor(0.0f, 0.2f, 0.4f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    private int createShaderProgram() {
        int vertexShader = createVertexShader();
        int fragmentShader = createFragmentShader();
        int linkedPipeline = linkShaders(vertexShader, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        return linkedPipeline;
    }
    
    private int createVertexShader() {
        String vertexShaderSrc =
                "#version 330 core\n" +
                "layout (location = 0) in vec3 aPos;\n" +
                "layout (location = 1) in vec3 aColor;\n" +
                "uniform mat4 uProjection;\n" +
                "out vec3 ourColor;\n" +
                "void main() {\n" +
                "    gl_Position = uProjection * vec4(aPos, 1.0);\n" +
                "    ourColor = aColor;\n" +
                "}";
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSrc);
        glCompileShader(vertexShader);
        checkShaderCompile(vertexShader);
        return vertexShader;
    }

    private int createFragmentShader() {
        String fragmentShaderSrc =
                "#version 330 core\n" +
                "in vec3 ourColor;\n" +
                "out vec4 FragColor;\n" +
                "void main() {\n" +
                "    FragColor = vec4(ourColor, 1.0);\n" +
                "}";
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSrc);
        glCompileShader(fragmentShader);
        checkShaderCompile(fragmentShader);
        return fragmentShader;
    }
    
    private int linkShaders(int vertexShader, int fragmentShader) {
        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        checkProgramLink(shaderProgram);
        return shaderProgram;
    }
    
    private int initVertexArrayObject() {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        return vao;
    }
    
    private int initVertexBufferObject() {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        return vbo;
    }
    
    private float[] createTargetVertices() {
        return new float[] {
                100f, 300f, 0.0f,   1f, 0f, 0f,
                200f, 300f, 0.0f,   0f, 1f, 0f,
                150f, 200f, 0.0f,   0f, 0f, 1f,
        };
    }
    
    private void putToArrayBuffer(float[] vertices) {
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
    }
    
    private void linkFloatLayoutAttribute(int attributeIndex, int fullLength, int offset, int length) {
        glVertexAttribPointer(attributeIndex, length, GL_FLOAT, false, fullLength * Float.BYTES, ((long) offset) * Float.BYTES);
        glEnableVertexAttribArray(attributeIndex);
    }
    
    private void checkShaderCompile(int shader) {
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throwGlError("Shader compile error: " + glGetShaderInfoLog(shader));
        }
    }

    private void checkProgramLink(int program) {
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throwGlError("Program link error: " + glGetProgramInfoLog(program));
        }
    }
    
    private void throwGlError(String message) {
        throw new IllegalStateException(message);
    }

    private float[] createOrthoMatrix(float left, float right, float top, float bottom) {
        return new float[] {
                2f / (right - left), 0f, 0f, 0f,
                0f, 2f / (top - bottom), 0f, 0f,
                0f, 0f, -1f, 0f,
                -(right + left) / (right - left), -(top + bottom) / (top - bottom), 0f, 1f
        };
    }
    
    private void putUniformMatrix(int shaderProgram, String uniformVariableName, float[] matrix) {
        int projectionLocation = glGetUniformLocation(shaderProgram, uniformVariableName);
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrixBuffer.put(matrix).flip();
        glUniformMatrix4fv(projectionLocation, false, matrixBuffer);
    }
    
    private void uninit(int vbo, int vao, int shaderProgram) {
        glUseProgram(0);
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        glDeleteProgram(shaderProgram);
    }
    
}
