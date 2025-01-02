package br.com.uanscarvalho.services;

import br.com.uanscarvalho.exceptions.RequiredObjectIsNullException;
import br.com.uanscarvalho.model.Person;
import br.com.uanscarvalho.repository.PersonRepository;
import br.com.uanscarvalho.unittests.mapper.mocks.MockPerson;
import br.com.uanscarvalho.vo.PersonVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices services;

    @Mock
    PersonRepository repository;

    @Mock
    PersonVO vo;

    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testeFindAll() {

        List<Person> entityList = input.mockEntityList();
        Mockito.when(repository.findAll()).thenReturn(entityList);

        var people = services.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);

        personOne.setKey(personOne.getKey());

        assertNotNull(personOne);
        assertNotNull(personOne.getKey());
        assertNotNull(personOne.getLinks());
        //assertTrue(result.toString().contains("</person/1>;rel=\"self\""));
        assertEquals("Addres Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of(person));

        var result = services.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        //assertTrue(result.toString().contains("</person/1>;rel=\"self\""));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testeCreate() {

        Person entity = input.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        Mockito.when(repository.save(any(Person.class))).thenReturn(persisted);


        var result = services.create(vo);
        result.setKey(vo.getKey());

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        //assertTrue(result.toString().contains("</person/1>;rel=\"self\""));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void testeCreateWithNullPerson() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.create(null);
        });

        String exceptedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));

    }

    @Test
    void testeUpdate() {

        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of(entity));
        Mockito.when(repository.save(any(Person.class))).thenReturn(persisted);


        var result = services.update(vo);
        result.setKey(vo.getKey());

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        //assertTrue(result.toString().contains("</person/1>;rel=\"self\""));
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testeUpdateWithNullPerson() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.update(null);
        });

        String exceptedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(exceptedMessage));

    }

    @Test
    void testeDelete() {

        Person person = input.mockEntity(1);
        person.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of(person));

        services.delete(1L);
    }
}