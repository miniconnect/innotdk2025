package hu.webarticum.inno.paperdatabase.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.views.View;

@Controller("/notfound") 
class NotFoundController {
    
    @Error(exception = HttpStatusException.class, global = true)
    @View("error")
    public HttpResponse<Object> notFound(HttpRequest<?> request, HttpStatusException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", Optional.ofNullable(e.getMessage()).orElse("Oops"));
        return HttpResponse.status(e.getStatus()).body(result);
    }
    
}
