package hu.webarticum.inno.restdemo.repository;

import hu.webarticum.inno.restdemo.model.Post;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
}
