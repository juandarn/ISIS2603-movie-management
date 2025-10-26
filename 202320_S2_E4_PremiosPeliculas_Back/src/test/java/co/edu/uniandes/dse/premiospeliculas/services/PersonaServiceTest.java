package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PersonaService.class)

public class PersonaServiceTest {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PersonaEntity> personaList = new ArrayList<>();
    private List<PeliculaEntity> peliculaList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void insertData() {
        for (int i = 0; i<3;i ++ ){
            PersonaEntity personaEntity = factory.manufacturePojo(PersonaEntity.class);
            personaEntity.setPeliculas(new ArrayList<>());
            entityManager.persist(personaEntity);
            personaList.add(personaEntity);
        }

        PeliculaEntity peliculaEntity = factory.manufacturePojo(PeliculaEntity.class);
        peliculaList.add(peliculaEntity);

        PersonaEntity personaEntity = factory.manufacturePojo(PersonaEntity.class);
        personaEntity.setPeliculas(peliculaList);
        entityManager.persist(personaEntity);
        personaList.add(personaEntity);

    }

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from PersonaEntity");
	}

    @Test
    public void testCreatePersona() throws EntityNotFoundException, IllegalOperationException {
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
        newEntity.setNombre("Nombre Apellido");
        newEntity.setRol("Actor");
        PersonaEntity result = personaService.createPersona(newEntity);
        assertNotNull(result);
        PersonaEntity entity = entityManager.find(PersonaEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getBiografia(), entity.getBiografia());
        assertEquals(newEntity.getFechaNacimiento(), entity.getFechaNacimiento());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNacionalidad(), entity.getNacionalidad());
        assertEquals(newEntity.getRol(), entity.getRol());
    }

    @Test
    public void testCreatePersonaWithNoValidNombre() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("");
            newEntity.setRol("Actor");
            personaService.createPersona(newEntity);


        });
    }

    @Test
    public void testCreatePersonaWithNoValidNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre(null);
            newEntity.setRol("Actor");
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidNombre3() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre(newEntity.getNombre().replace(" ", ""));
            newEntity.setRol("Actor");
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidNacionalidad() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setNacionalidad(null);
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidNacionalidad2() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setNacionalidad("");
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidFechaNacimiento() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setFechaNacimiento(null);
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidBiografia() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setBiografia("");
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidBiografia2() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setBiografia(null);
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidBiografia3() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setBiografia("a".repeat(301));
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidRol() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("");
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidRol2() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol(null);
            personaService.createPersona(newEntity);
        });
    }

    @Test
    public void testCreatePersonaWithNoValidRol3() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            personaService.createPersona(newEntity);
        });
    }

    @Test
    void testGetPersonas() {
            List<PersonaEntity> list = personaService.getPersonas();
            assertEquals(personaList.size(), list.size());
            for (PersonaEntity entity : list) {
                    boolean found = false;
                    for (PersonaEntity storedEntity : personaList) {
                            if (entity.getId().equals(storedEntity.getId())) {
                                    found = true;
                            }
                    }
                    assertTrue(found);
            }
    }

    @Test
    void testGetPersona() throws EntityNotFoundException {
            PersonaEntity entity = personaList.get(0);
            PersonaEntity resultEntity = personaService.getPersona(entity.getId());
            assertNotNull(resultEntity);
            assertEquals(entity.getNombre(), resultEntity.getNombre());
            assertEquals(entity.getBiografia(), resultEntity.getBiografia());
            assertEquals(entity.getFechaNacimiento(), resultEntity.getFechaNacimiento());
            assertEquals(entity.getId(), resultEntity.getId());
            assertEquals(entity.getNacionalidad(), resultEntity.getNacionalidad());
            assertEquals(entity.getRol(), resultEntity.getRol());
    }

    @Test
    void testGetInvalidPersona() {
        assertThrows(EntityNotFoundException.class,()->{
                personaService.getPersona(0L);
        });
    }

    @Test
    void testUpdatePersona() throws EntityNotFoundException, IllegalOperationException {
        PersonaEntity entity = personaList.get(0);
        PersonaEntity pojoEntity = factory.manufacturePojo(PersonaEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("Nombre Apellido");
        pojoEntity.setRol("Actor");
        personaService.updatePersona(entity.getId(), pojoEntity);

        PersonaEntity resp = entityManager.find(PersonaEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    
    @Test
    void testUpdatePersona2() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            PersonaEntity pojoEntity = factory.manufacturePojo(PersonaEntity.class);
            pojoEntity.setId(0L);
            personaService.updatePersona(pojoEntity.getId(), pojoEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidNombre() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(0);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("");
            newEntity.setRol("Actor");
            personaService.updatePersona(entity.getId(), newEntity);


        });
    }

    @Test
    public void testUpdatePersonaWithNoValidNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre(null);
            newEntity.setRol("Actor");
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidNombre3() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre(newEntity.getNombre().replace(" ", ""));
            newEntity.setRol("Actor");
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidNacionalidad() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setNacionalidad(null);
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidNacionalidad2() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setNacionalidad("");
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidFechaNacimiento() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setFechaNacimiento(null);
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidBiografia() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setBiografia("");
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidBiografia2() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setBiografia(null);
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidBiografia3() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("Actor");
            newEntity.setBiografia("a".repeat(301));
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidRol() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol("");
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidRol2() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            newEntity.setRol(null);
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    public void testUpdatePersonaWithNoValidRol3() {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(1);
            PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
            newEntity.setNombre("Nombre Apellido");
            personaService.updatePersona(entity.getId(), newEntity);
        });
    }

    @Test
    void testDeletePersona() throws EntityNotFoundException, IllegalOperationException {
        PersonaEntity entity = personaList.get(0);
        personaService.deletePersona(entity.getId());

        PersonaEntity resp = entityManager.find(PersonaEntity.class, entity.getId());
        assertNull(resp);
    }

    @Test
    void testDeletePersona2() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            personaService.deletePersona(0L);
        });
    }

    /*Eliminar persona con peliculas*/ 
    @Test
    void testDeletePersona3() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            PersonaEntity entity = personaList.get(3);
            personaService.deletePersona(entity.getId());
        });
    }

}
