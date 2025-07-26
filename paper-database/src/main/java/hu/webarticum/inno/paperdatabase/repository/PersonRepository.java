package hu.webarticum.inno.paperdatabase.repository;

import hu.webarticum.inno.paperdatabase.model.Person;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @NonNull Page<Person> findAllOrderByLastnameAscAndFirstnameAsc(@NonNull Pageable pageable);
    
}
