package hu.webarticum.inno.restdemo.repository;

import hu.webarticum.inno.restdemo.model.PostComment;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Long> {
}
