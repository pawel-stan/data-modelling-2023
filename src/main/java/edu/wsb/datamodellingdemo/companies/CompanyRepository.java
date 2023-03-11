package edu.wsb.datamodellingdemo.companies;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    Optional<Company> findByName(String name);
}
