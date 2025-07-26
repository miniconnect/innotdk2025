package hu.webarticum.inno.paperdatabase.http;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

@Controller("/")
public class HomeController {

    @Get("/")
    @View("home")
    @Transactional
    public Map<String, Object> index() {
        return new HashMap<>();
    }
    
}
