package hu.webarticum.inno.paperdatabase.facegenerator;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;

import hu.webarticum.inno.paperdatabase.facegenerator.GlRenderingService.DrawContext;
import jakarta.inject.Singleton;

@Singleton
public class RenderingExperimentsService {

    private static final int IMAGE_WIDTH = 350;
    private static final int IMAGE_HEIGHT = 500;
    
    private final GlRenderingService glRenderingService;
    
    public RenderingExperimentsService(GlRenderingService glRenderingService) {
        this.glRenderingService = glRenderingService;
    }

    public CompletableFuture<BufferedImage> renderSomething() {
        return glRenderingService.submit(IMAGE_WIDTH, IMAGE_HEIGHT, this::drawOnGl);
    }
    
    private void drawOnGl(DrawContext ctx) {
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    private void drawOnGl_OLD_B(DrawContext ctx) {
        glClearColor(0.0f, 0.2f, 0.4f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        
        // Vertex data: 3 vertices with RGB colors
        float[] vertices = {
            100f, 300f, 0.0f,   1f, 0f, 0f, // Bottom right
            200f, 300f, 0.0f,   0f, 1f, 0f, // Bottom left
            150f,  200f, 0.0f,   0f, 0f, 1f  // Top
        };

        int shaderProgram = createShaderProgram();
        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();

        glBindVertexArray(vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Position attribute
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        // Color attribute
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glUseProgram(shaderProgram);
        
        int projectionLocation = glGetUniformLocation(shaderProgram, "uProjection");

        float[] ortho = createOrthoMatrix(0, IMAGE_WIDTH, 0, IMAGE_HEIGHT); // top-left origin
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrixBuffer.put(ortho).flip();

        glUniformMatrix4fv(projectionLocation, false, matrixBuffer);
        
        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        // Cleanup
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        glDeleteProgram(shaderProgram);
    }
    
    private int createShaderProgram() {
        String vertexShaderSrc = "\n"
                + "            #version 330 core\n"
                + "            layout (location = 0) in vec3 aPos;\n"
                + "            layout (location = 1) in vec3 aColor;\n"
                + "            uniform mat4 uProjection;\n"
                + "            out vec3 ourColor;\n"
                + "            void main() {\n"
                + "                gl_Position = uProjection * vec4(aPos, 1.0);\n"
                + "                ourColor = aColor;\n"
                + "            }";

        String fragmentShaderSrc = "\n"
                + "            #version 330 core\n"
                + "            in vec3 ourColor;\n"
                + "            out vec4 FragColor;\n"
                + "            void main() {\n"
                + "                FragColor = vec4(ourColor, 1.0);\n"
                + "            }";

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSrc);
        glCompileShader(vertexShader);
        checkShaderCompile(vertexShader);

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSrc);
        glCompileShader(fragmentShader);
        checkShaderCompile(fragmentShader);

        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        checkProgramLink(shaderProgram);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        return shaderProgram;
    }

    private void checkShaderCompile(int shader) {
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader compile error: " + glGetShaderInfoLog(shader));
        }
    }

    private void checkProgramLink(int program) {
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Program link error: " + glGetProgramInfoLog(program));
        }
    }
    
    private float[] createOrthoMatrix(float left, float right, float top, float bottom) {
        return new float[] {
                2f / (right - left), 0f, 0f, 0f,
                0f, 2f / (top - bottom), 0f, 0f,
                0f, 0f, -1f, 0f,
                -(right + left) / (right - left), -(top + bottom) / (top - bottom), 0f, 1f
        };
    }
    */
    
    
    
    
    
    
    
    
    
    
    /*
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
            texture = loadTexture("/textures/test-texture.png");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public void uninit() {
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        glDeleteProgram(shaderProgram);
        glDeleteTextures(texture);
    }

    public void draw() {
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

    private int loadTexture(String resourcePath) throws IOException {
        ByteBuffer imageBuffer = BufferUtils.createByteBuffer(1024 * 1024);

        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) throw new IOException("Resource not found: " + resourcePath);
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
    */
    
}
