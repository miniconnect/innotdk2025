package hu.webarticum.inno.paperdatabase.http;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import hu.webarticum.inno.paperdatabase.facegenerator.FaceGeneratorService;
import hu.webarticum.inno.paperdatabase.model.Person;
import hu.webarticum.inno.paperdatabase.repository.PersonRepository;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.views.View;

@Controller("/persons")
public class PersonController {

    private static final int ITEMS_PER_PAGE = 25;
    
    private final PersonRepository personRepository;

    private final FaceGeneratorService faceGeneratorService;
    
    public PersonController(PersonRepository personRepository, FaceGeneratorService faceGeneratorService) {
        this.personRepository = personRepository;
        this.faceGeneratorService = faceGeneratorService;
    }
    
    @Get("/{?page}")
    @View("persons")
    @Transactional
    public Map<String, Object> index(@Nullable @QueryValue Integer page) {
        int effectivePage = Optional.ofNullable(page).orElse(1) - 1;
        Page<Person> itemsPage = personRepository.findAllOrderByLastnameAscAndFirstnameAsc(Pageable.from(effectivePage, ITEMS_PER_PAGE));
        Map<String, Object> result = new HashMap<>();
        result.put("persons", itemsPage.getContent());
        result.put("totalPages", itemsPage.getTotalPages());
        result.put("page", effectivePage);
        return result;
    }
    
    @Get("/{id}")
    @View("person")
    @Transactional
    public Map<String, Object> person(@PathVariable Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "No such person"));
        Map<String, Object> result = new HashMap<>();
        result.put("person", person);
        return result;
    }

    @Get("/{id}/images/large.jpg")
    @Transactional
    public HttpResponse<byte[]> largeImage(@PathVariable Long id) throws IOException, InterruptedException, ExecutionException {
        return createIJpegResponse(faceGeneratorService.render().get());
    }

    @Get("/{id}/images/thumb.jpg")
    @Transactional
    public HttpResponse<byte[]> thumbImage(@PathVariable Long id) throws IOException, InterruptedException, ExecutionException {
        return createIJpegResponse(faceGeneratorService.renderThumb().get());
    }
    
    private HttpResponse<byte[]> createIJpegResponse(RenderedImage image) throws IOException, InterruptedException, ExecutionException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", out);
        byte[] pngBytes = out.toByteArray();

        return HttpResponse.ok(pngBytes).contentType(MediaType.IMAGE_JPEG_TYPE);
    }
    
}