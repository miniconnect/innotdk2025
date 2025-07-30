package hu.webarticum.inno.paperdatabase.http;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import hu.webarticum.inno.paperdatabase.abstractgenerator.PaperAbstractGeneratorService;
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
        result.put("alternativesAbstracts", new PaperAbstractGeneratorService().generateAlternatives());
        return result;
    }

    @Get("/random-iteration")
    @Transactional
    public Map<String, Map<String, String>> randomIteration() {
        Map<String, Map<String, String>> result = new LinkedHashMap<>();
        for (int mod = 2; mod < 5; mod++) {
            Map<String, String> modResult = new LinkedHashMap<>();
            for (int seed = 0; seed < 10; seed++) {
                Random random = new Random(seed);
                List<String> modSeedResult = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    modSeedResult.add("" + random.nextInt(mod));
                }
                modResult.put("Seed: " + seed, String.join(", ", modSeedResult));
            }
            result.put("Mod: " + mod, modResult);
        }
        return result;
    }

    @Get("/splittable-random-iteration")
    @Transactional
    public Map<String, Map<String, String>> splittableRandomIteration() {
        Map<String, Map<String, String>> result = new LinkedHashMap<>();
        for (int mod = 2; mod < 5; mod++) {
            Map<String, String> modResult = new LinkedHashMap<>();
            for (int seed = 0; seed < 10; seed++) {
                SplittableRandom random = new SplittableRandom(seed);
                List<String> modSeedResult = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    modSeedResult.add("" + random.nextInt(mod));
                }
                modResult.put("Seed: " + seed, String.join(", ", modSeedResult));
            }
            result.put("Mod: " + mod, modResult);
        }
        return result;
    }

    @Get("/render/random-circle")
    @Transactional
    public HttpResponse<byte[]> demoRenderRandomCircle() throws IOException, InterruptedException, ExecutionException {
        return createPngResponse(randomCirleRenderingService.render().get());
    }

    @Get("/render/some-shader")
    @Transactional
    public HttpResponse<byte[]> demoRenderSomeShader() throws IOException, InterruptedException, ExecutionException {
        return createPngResponse(someShaderRendernigService.render().get());
    }

    @Get("/render/texture")
    @Transactional
    public HttpResponse<byte[]> demoRenderTexture() throws IOException, InterruptedException, ExecutionException {
        return createPngResponse(textureRenderingService.render().get());
    }

    private HttpResponse<byte[]> createPngResponse(RenderedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        byte[] pngBytes = out.toByteArray();

        return HttpResponse.ok(pngBytes).contentType(MediaType.IMAGE_PNG_TYPE);
    }
    
}
