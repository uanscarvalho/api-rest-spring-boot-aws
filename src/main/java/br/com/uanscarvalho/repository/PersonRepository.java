package br.com.uanscarvalho.repository;

import br.com.uanscarvalho.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
