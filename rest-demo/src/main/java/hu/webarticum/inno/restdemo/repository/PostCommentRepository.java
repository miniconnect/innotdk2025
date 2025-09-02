package hu.webarticum.inno.restdemo.repository;

import java.util.List;

import hu.webarticum.inno.restdemo.model.PostComment;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    public List<PostComment> findByPostIdOrderByCreatedAt(long postId);

}
