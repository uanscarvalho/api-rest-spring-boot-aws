package br.com.uanscarvalho.services;

import br.com.uanscarvalho.controllers.BookController;
import br.com.uanscarvalho.exceptions.RequiredObjectIsNullException;
import br.com.uanscarvalho.exceptions.ResourceNotFoundException;
import br.com.uanscarvalho.mapper.ModelMapperConfig;
import br.com.uanscarvalho.model.Book;
import br.com.uanscarvalho.repository.BookRepository;
import br.com.uanscarvalho.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookVO> findAll() {

        logger.info("Finding all book!");

        var books = repository.findAll().stream()
                .map(entity -> {
                    var vo = ModelMapperConfig.parseObject(entity, BookVO.class);
                    vo.setKey(entity.getId()); // Ajuste para garantir que o key não seja null
                    return vo;
                })
                .collect(Collectors.toList());
        books
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

        return books;
    }

    public BookVO findById(Long id) {

        logger.info("Finding one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var vo = ModelMapperConfig.parseObject(entity, BookVO.class);
        vo.setKey(entity.getId());

        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book) {

        if (book == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Creating one book!");
        var entity = ModelMapperConfig.parseObject(book, Book.class);
        var vo = ModelMapperConfig.parseObject(repository.save(entity), BookVO.class);
        vo.setKey(entity.getId());
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {

        if (book == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Updating one book!");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(Double.valueOf(book.getPrice()));
        entity.setTitle(book.getTitle());

        var vo = ModelMapperConfig.parseObject(repository.save(entity), BookVO.class);
        vo.setKey(entity.getId());

        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}

