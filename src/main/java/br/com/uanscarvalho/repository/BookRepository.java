package br.com.uanscarvalho.repository;

import br.com.uanscarvalho.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
