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
import co.edu.uniandes.dse.premiospeliculas.entities.PlataformaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PlataformaService.class)
public class PlataformaServiceTest {
    @Autowired
    private PlataformaService plataformaService;
    private PodamFactory factory = new PodamFactoryImpl();
    PlataformaEntity newEntity = factory.manufacturePojo(PlataformaEntity.class);
    private List<PlataformaEntity> plataformaList = new ArrayList<>();
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PlataformaEntity plataformaEntity = this.factory.manufacturePojo(PlataformaEntity.class);
            this.entityManager.persist(plataformaEntity);
            this.plataformaList.add(plataformaEntity);
        }
    }

    @Test
    void testCreatePlataforma() throws EntityNotFoundException, IllegalOperationException {
        PlataformaEntity newEntity = factory.manufacturePojo(PlataformaEntity.class);
        PlataformaEntity result = this.plataformaService.CreatePlataforma(newEntity);
        assertNotNull(result);
        PlataformaEntity entity = entityManager.find(PlataformaEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getNombre(), entity.getNombre());

    }

    @Test
    void testCreatePlataformaWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            PlataformaEntity newEntity = factory.manufacturePojo(PlataformaEntity.class);
            newEntity.setNombre(null);
            this.plataformaService.CreatePlataforma(newEntity);
        });
    }

    @Test
    void testGetPlataformas() {
        List<PlataformaEntity> list = this.plataformaService.getPlataformas();
        assertEquals(this.plataformaList.size(), list.size());
        for (PlataformaEntity entity : list) {
            boolean found = false;
            for (PlataformaEntity storedEntity : this.plataformaList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetPlataforma() throws EntityNotFoundException, IllegalOperationException {
        PlataformaEntity entity = this.plataformaList.get(0);
        PlataformaEntity resultEntity = this.plataformaService.GetPlataforma(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PlataformaEntity");
    }

    @Test
    void testUpdatePlataforma() throws EntityNotFoundException, IllegalOperationException {
        PlataformaEntity entity = this.plataformaList.get(0);
        PlataformaEntity pojoEntity = factory.manufacturePojo(PlataformaEntity.class);
        pojoEntity.setId(entity.getId());
        this.plataformaService.UpdatePlataforma(entity.getId(), pojoEntity);

        PlataformaEntity resp = this.entityManager.find(PlataformaEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }

    @Test
    void testUpdatePlataformaInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            PlataformaEntity pojoEntity = this.factory.manufacturePojo(PlataformaEntity.class);
            pojoEntity.setId(0L);
            this.plataformaService.UpdatePlataforma(0L, pojoEntity);
        });
    }

    @Test
    void testUpdatePlataformaWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            PlataformaEntity entity = this.plataformaList.get(0);
            PlataformaEntity pojoEntity = this.factory.manufacturePojo(PlataformaEntity.class);
            pojoEntity.setNombre(null);
            pojoEntity.setId(entity.getId());
            this.plataformaService.UpdatePlataforma(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testDeletePlataforma() throws EntityNotFoundException, IllegalOperationException {
        PlataformaEntity entity = this.plataformaList.get(1);
        this.plataformaService.DeletePlataforma(entity.getId());
        PlataformaEntity deleted = entityManager.find(PlataformaEntity.class, entity.getId());
        assertNull(deleted);
    }

    // revisar
    @Test
    void testDeleteInvalidPlataforma() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.plataformaService.DeletePlataforma(0L);
        });
    }
}
