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
//import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.premiospeliculas.entities.CinefiloEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(CinefiloService.class)
public class CinefiloServiceTest {
    @Autowired
    private CinefiloService cinefiloService;
    private PodamFactory factory = new PodamFactoryImpl();

    private List<CinefiloEntity> cinefiloList = new ArrayList<>();
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CinefiloEntity cinefiloEntity = this.factory.manufacturePojo(CinefiloEntity.class);
            this.entityManager.persist(cinefiloEntity);
            this.cinefiloList.add(cinefiloEntity);
        }
    }

    @Test
    void testCreateCinefilo() throws EntityNotFoundException, IllegalOperationException {
        CinefiloEntity newEntity = factory.manufacturePojo(CinefiloEntity.class);
        CinefiloEntity result = this.cinefiloService.createCinefilo(newEntity);
        assertNotNull(result);
        CinefiloEntity entity = entityManager.find(CinefiloEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getNombre(), entity.getNombre());

    }

    @Test
    void testCreateCinefiloWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            CinefiloEntity newEntity = factory.manufacturePojo(CinefiloEntity.class);
            newEntity.setNombre(null);
            this.cinefiloService.createCinefilo(newEntity);
        });
    }

    @Test
    void testCreateCinefiloWithShortName() {
        assertThrows(IllegalOperationException.class, () -> {
            CinefiloEntity newEntity = factory.manufacturePojo(CinefiloEntity.class);
            newEntity.setNombre("pepe");
            this.cinefiloService.createCinefilo(newEntity);
        });
    }

    @Test
    void testGetCinefilos() {
        List<CinefiloEntity> list = this.cinefiloService.getCinefilos();
        assertEquals(this.cinefiloList.size(), list.size());
        for (CinefiloEntity entity : list) {
            boolean found = false;
            for (CinefiloEntity storedEntity : this.cinefiloList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetCinefilo() throws EntityNotFoundException {
        CinefiloEntity entity = this.cinefiloList.get(0);
        CinefiloEntity resultEntity = this.cinefiloService.getCinefilo(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getIdentificador(), resultEntity.getIdentificador());
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from CinefiloEntity");
    }

    @Test
    void testUpdateCinefilo() throws EntityNotFoundException, IllegalOperationException {
        CinefiloEntity entity = this.cinefiloList.get(0);
        CinefiloEntity pojoEntity = factory.manufacturePojo(CinefiloEntity.class);
        pojoEntity.setId(entity.getId());
        this.cinefiloService.updateCinefilo(entity.getId(), pojoEntity);

        CinefiloEntity resp = this.entityManager.find(CinefiloEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getIdentificador(), resp.getIdentificador());
    }

    @Test
    void testUpdateCinefiloInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            CinefiloEntity pojoEntity = this.factory.manufacturePojo(CinefiloEntity.class);
            pojoEntity.setId(0L);
            this.cinefiloService.updateCinefilo(0L, pojoEntity);
        });
    }

    @Test
    void testUpdateCinefiloWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            CinefiloEntity entity = this.cinefiloList.get(0);
            CinefiloEntity pojoEntity = this.factory.manufacturePojo(CinefiloEntity.class);
            pojoEntity.setNombre(null);
            pojoEntity.setId(entity.getId());
            this.cinefiloService.updateCinefilo(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdateCinefiloWithShortName() {
        assertThrows(IllegalOperationException.class, () -> {
            CinefiloEntity entity = this.cinefiloList.get(0);
            CinefiloEntity pojoEntity = this.factory.manufacturePojo(CinefiloEntity.class);
            pojoEntity.setNombre("pepe");
            pojoEntity.setId(entity.getId());
            this.cinefiloService.updateCinefilo(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testDeleteCinefilo() throws EntityNotFoundException, IllegalOperationException {
        CinefiloEntity entity = this.cinefiloList.get(1);
        this.cinefiloService.deleteCinefilo(entity.getId());
        CinefiloEntity deleted = entityManager.find(CinefiloEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidCinefilo() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.cinefiloService.deleteCinefilo(0L);
        });
    }

}
