package hu.webarticum.inno.restdemo.repository;

import hu.webarticum.inno.restdemo.model.Category;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
