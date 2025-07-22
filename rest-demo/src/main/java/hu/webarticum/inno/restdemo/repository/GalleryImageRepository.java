package hu.webarticum.inno.restdemo.repository;

import hu.webarticum.inno.restdemo.model.GalleryImage;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface GalleryImageRepository extends CrudRepository<GalleryImage, Long> {
}
