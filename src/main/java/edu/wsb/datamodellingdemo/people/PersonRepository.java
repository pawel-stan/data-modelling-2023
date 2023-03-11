package edu.wsb.datamodellingdemo.people;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByUsername(String username);

    Iterable<Person> findAllByPasswordLikeIgnoreCase(String v);

    Iterable<Person> findAllByDateCreatedAfterOrderByUsername(Date date);

    @Query("select p from Person p where p.dateCreated >= :date order by p.username")
    Iterable<Person> findAllUsersCreatedAfter(@Param("date") Date date);
}
