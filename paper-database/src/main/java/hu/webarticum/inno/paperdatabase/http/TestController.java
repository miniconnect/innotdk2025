package hu.webarticum.inno.paperdatabase.http;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import hu.webarticum.inno.paperdatabase.abstractgenerator.AbstractGenerator;
import hu.webarticum.inno.paperdatabase.facegenerator.experiments.RandomCirleRenderingService;
import hu.webarticum.inno.paperdatabase.facegenerator.experiments.SomeShaderRendernigService;
import hu.webarticum.inno.paperdatabase.facegenerator.experiments.TextureRenderingService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/test")
public class TestController {

    private final RandomCirleRenderingService randomCirleRenderingService;
    private final SomeShaderRendernigService someShaderRendernigService;
    private final TextureRenderingService textureRenderingService;
    
    public TestController(
            RandomCirleRenderingService randomCirleRenderingService,
            SomeShaderRendernigService someShaderRendernigService,
            TextureRenderingService textureRenderingService) {
        this.randomCirleRenderingService = randomCirleRenderingService;
        this.someShaderRendernigService = someShaderRendernigService;
        this.textureRenderingService = textureRenderingService;
    }

    @Get("/abstract")
    @Transactional
    public Map<String, Object> demoAbstract() {
        Map<String, Object> result = new HashMap<>();
        result.put("abstract", new AbstractGenerator().generate());
        return result;
    }

    @Get("/render/random-circle")
    @Transactional
    public HttpResponse<byte[]> demoRenderRandomCircle() throws IOException, InterruptedException, ExecutionException {
        return createIPngResponse(randomCirleRenderingService.render().get());
    }

    @Get("/render/some-shader")
    @Transactional
    public HttpResponse<byte[]> demoRenderSomeShader() throws IOException, InterruptedException, ExecutionException {
        return createIPngResponse(someShaderRendernigService.render().get());
    }

    @Get("/render/texture")
    @Transactional
    public HttpResponse<byte[]> demoRenderTexture() throws IOException, InterruptedException, ExecutionException {
        return createIPngResponse(textureRenderingService.render().get());
    }

    private HttpResponse<byte[]> createIPngResponse(RenderedImage image) throws IOException, InterruptedException, ExecutionException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        byte[] pngBytes = out.toByteArray();

        return HttpResponse.ok(pngBytes).contentType(MediaType.IMAGE_PNG_TYPE);
    }
    
}
