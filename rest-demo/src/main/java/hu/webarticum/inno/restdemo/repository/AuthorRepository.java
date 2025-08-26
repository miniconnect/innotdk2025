package hu.webarticum.inno.restdemo.repository;

import hu.webarticum.inno.restdemo.model.Author;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
