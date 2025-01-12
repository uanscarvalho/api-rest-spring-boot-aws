package br.com.uanscarvalho.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@JsonPropertyOrder({"id", "firstName", "lastName", "address", "gender"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

    @JsonProperty("id")
    private Long key;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}