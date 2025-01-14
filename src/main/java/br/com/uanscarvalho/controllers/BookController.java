package br.com.uanscarvalho.controllers;

import br.com.uanscarvalho.services.BookServices;
import br.com.uanscarvalho.vo.BookVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for Managing Book")
public class BookController {

    @Autowired
    private BookServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Finds all Book",
               description = "Finds all Book",
               tags = {"Book"},
               responses = {
                       @ApiResponse(description = "Sucess", responseCode = "200",
                               content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
                                    )
                               }),
                       @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                       @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                       @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                       @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
               }
    )
    public List<BookVO> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public BookVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public BookVO create(@RequestBody BookVO Book) {
        return service.create(Book);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public BookVO update(@RequestBody BookVO Book) {
        return service.update(Book);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}