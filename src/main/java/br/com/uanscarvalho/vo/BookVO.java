package br.com.uanscarvalho.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;

@JsonPropertyOrder({"id", "author", "launchDate", "price", "title"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookVO extends RepresentationModel<BookVO> implements Serializable {

    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    private String author;
    private Date launchDate;
    private Double price;
    private String title;
}