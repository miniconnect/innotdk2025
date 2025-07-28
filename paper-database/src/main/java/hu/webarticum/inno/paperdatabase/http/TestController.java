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
import hu.webarticum.inno.paperdatabase.facegenerator.RenderingExperimentsService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/test")
public class TestController {
    
    private final RenderingExperimentsService renderingExperimentsService;;
    
    public TestController(RenderingExperimentsService renderingExperimentsService) {
        this.renderingExperimentsService = renderingExperimentsService;
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
        BufferedImage image = renderingExperimentsService.renderSomething().get();
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        byte[] pngBytes = out.toByteArray();

        return HttpResponse.ok(pngBytes).contentType(MediaType.IMAGE_PNG_TYPE);
    }
    
}
