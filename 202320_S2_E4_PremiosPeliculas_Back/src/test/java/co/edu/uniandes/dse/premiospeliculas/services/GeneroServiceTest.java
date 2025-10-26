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

import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(GeneroService.class)
public class GeneroServiceTest {

    @Autowired
    private GeneroService generoService;
    private PodamFactory factory = new PodamFactoryImpl();
    GeneroEntity newEntity = factory.manufacturePojo(GeneroEntity.class);
    private List<GeneroEntity> generoList = new ArrayList<>();
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            GeneroEntity generoEntity = this.factory.manufacturePojo(GeneroEntity.class);
            entityManager.persist(generoEntity);
            generoList.add(generoEntity);
        }
    }

    @Test
    void testCreateGenero() throws EntityNotFoundException, IllegalOperationException {
        GeneroEntity newEntity = factory.manufacturePojo(GeneroEntity.class);
        newEntity.setNombre("Drama");
        GeneroEntity result = generoService.CreateGenero(newEntity);
        assertNotNull(result);
        GeneroEntity entity = entityManager.find(GeneroEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getNombre(), entity.getNombre());

    }

    @Test
    void testCreateGeneroWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            GeneroEntity newEntity = factory.manufacturePojo(GeneroEntity.class);
            newEntity.setNombre(null);
            this.generoService.CreateGenero(newEntity);
        });
    }

    @Test
    void testGetGeneros() {
        List<GeneroEntity> list = this.generoService.getGeneros();
        assertEquals(this.generoList.size(), list.size());
        for (GeneroEntity entity : list) {
            boolean found = false;
            for (GeneroEntity storedEntity : this.generoList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetGenero() throws EntityNotFoundException {
        GeneroEntity entity = generoList.get(0);
        GeneroEntity resultEntity = generoService.GetGenero(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from GeneroEntity");
    }

    @Test
    void testUpdateGenero() throws EntityNotFoundException, IllegalOperationException {
        GeneroEntity entity = this.generoList.get(0);
        GeneroEntity pojoEntity = factory.manufacturePojo(GeneroEntity.class);
        pojoEntity.setId(entity.getId());
        this.generoService.UpdateGenero(entity.getId(), pojoEntity);

        GeneroEntity resp = this.entityManager.find(GeneroEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }

    @Test
    void testUpdateGeneroInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            GeneroEntity pojoEntity = this.factory.manufacturePojo(GeneroEntity.class);
            pojoEntity.setId(0L);
            this.generoService.UpdateGenero(0L, pojoEntity);
        });
    }

    @Test
    void testUpdateGeneroWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            GeneroEntity entity = this.generoList.get(0);
            GeneroEntity pojoEntity = this.factory.manufacturePojo(GeneroEntity.class);
            pojoEntity.setNombre(null);
            pojoEntity.setId(entity.getId());
            this.generoService.UpdateGenero(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testDeleteGenero() throws EntityNotFoundException, IllegalOperationException {
        GeneroEntity entity = this.generoList.get(1);
        this.generoService.DeleteGenero(entity.getId());
        GeneroEntity deleted = entityManager.find(GeneroEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidGenero() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.generoService.DeleteGenero(0L);
        });
    }
}
