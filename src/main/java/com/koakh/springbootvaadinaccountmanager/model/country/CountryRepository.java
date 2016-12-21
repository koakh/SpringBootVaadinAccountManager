package com.koakh.springbootvaadinaccountmanager.model.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
//@EnableJpaRepositories
public interface CountryRepository extends JpaRepository<Country, Long> {

  List<Country> findByNameStartsWithIgnoreCase(String name);
}