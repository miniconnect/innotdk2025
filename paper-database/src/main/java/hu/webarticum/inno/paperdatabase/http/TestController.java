package hu.webarticum.inno.paperdatabase.http;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import hu.webarticum.inno.paperdatabase.abstractgenerator.AbstractGenerator;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/test")
public class TestController {

    @Get("/abstract")
    @Transactional
    public Map<String, Object> index() {
        Map<String, Object> result = new HashMap<>();
        result.put("abstract", new AbstractGenerator().generate());
        return result;
    }
    
}
