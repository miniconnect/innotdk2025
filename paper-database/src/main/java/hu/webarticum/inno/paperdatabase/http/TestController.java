package hu.webarticum.inno.paperdatabase.http;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import hu.webarticum.inno.paperdatabase.abstractgenerator.AbstractGenerator;
import hu.webarticum.inno.paperdatabase.facegenerator.FaceGeneratorService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/test")
public class TestController {
    
    private final FaceGeneratorService faceGeneratorService;
    
    public TestController(FaceGeneratorService faceGeneratorService) {
        this.faceGeneratorService = faceGeneratorService;
    }

    @Get("/abstract")
    @Transactional
    public Map<String, Object> demoAbstract() {
        Map<String, Object> result = new HashMap<>();
        result.put("abstract", new AbstractGenerator().generate());
        return result;
    }

    @Get("/face")
    @Transactional
    public HttpResponse<byte[]> demoFace() throws IOException, InterruptedException, ExecutionException {
        BufferedImage image = faceGeneratorService.renderFace().get();
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        byte[] pngBytes = out.toByteArray();

        return HttpResponse.ok(pngBytes).contentType(MediaType.IMAGE_PNG);
    }
    
}
