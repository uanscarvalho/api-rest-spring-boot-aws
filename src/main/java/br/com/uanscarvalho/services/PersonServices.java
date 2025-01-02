package br.com.uanscarvalho.services;

import br.com.uanscarvalho.controllers.PersonController;
import br.com.uanscarvalho.exceptions.ResourceNotFoundException;
import br.com.uanscarvalho.mapper.ModelMapperConfig;
import br.com.uanscarvalho.model.Person;
import br.com.uanscarvalho.repository.PersonRepository;
import br.com.uanscarvalho.vo.PersonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");

        var persons = repository.findAll().stream()
                .map(entity -> {
                    var vo = ModelMapperConfig.parseObject(entity, PersonVO.class);
                    vo.setKey(entity.getId()); // Ajuste para garantir que o key não seja null
                    return vo;
                })
                .collect(Collectors.toList());
        persons
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        return persons;
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var vo = ModelMapperConfig.parseObject(entity, PersonVO.class);
        vo.setKey(entity.getId());

        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {

        logger.info("Creating one person!");
        var entity = ModelMapperConfig.parseObject(person, Person.class);
        var vo = ModelMapperConfig.parseObject(repository.save(entity), PersonVO.class);
        vo.setKey(entity.getId());
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) {

        logger.info("Updating one person!");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = ModelMapperConfig.parseObject(repository.save(entity), PersonVO.class);
        vo.setKey(entity.getId());

        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}

