package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;

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
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;

//import co.edu.uniandes.dse.premiospeliculas.services.PeliculaService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PeliculaService.class)
public class PeliculaServiceTest {
    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PeliculaEntity> peliculaList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PeliculaEntity peliculalEntity = factory.manufacturePojo(PeliculaEntity.class);
            entityManager.persist(peliculalEntity);
            this.peliculaList.add(peliculalEntity);
        }
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PeliculaEntity");
    }

    @Test
    void testCreateBook() throws EntityNotFoundException, IllegalOperationException {
        PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
        PeliculaEntity result = this.peliculaService.createPelicula(newEntity);
        assertNotNull(result);
        PeliculaEntity entity = this.entityManager.find(PeliculaEntity.class, result.getId());

        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getDuracion(), entity.getDuracion());
        assertEquals(newEntity.getPais(), entity.getPais());
        assertEquals(newEntity.getIdiomaOriginal(), entity.getIdiomaOriginal());
        assertEquals(newEntity.getFechaEstreno(), entity.getFechaEstreno());
        assertEquals(newEntity.getLinkTrailer(), entity.getLinkTrailer());
    }

    @Test
    void testCreatePeliculaWithNameNull() {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
            newEntity.setNombre(null);
            this.peliculaService.createPelicula(newEntity);
        });
    }

    @Test
    void testCreatePeliculaWithEmptyName() {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
            newEntity.setNombre("");
            this.peliculaService.createPelicula(newEntity);
        });
    }

    @Test
    void testCreatePeliculaWithInvalidCountry() {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
            newEntity.setPais("per");
            this.peliculaService.createPelicula(newEntity);
        });
    }

    @Test
    void testCreatePeliculaWithNullLanguage() {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
            newEntity.setIdiomaOriginal(null);
            this.peliculaService.createPelicula(newEntity);
        });
    }

    @Test
    void testCreatePeliculaWithNullDate() {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
            newEntity.setFechaEstreno(null);
            this.peliculaService.createPelicula(newEntity);
        });
    }

    @Test
    void testCreatePeliculaWithNullTrailer() {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity newEntity = factory.manufacturePojo(PeliculaEntity.class);
            newEntity.setLinkTrailer(null);
            this.peliculaService.createPelicula(newEntity);
        });
    }

    @Test
    void testGetBooks() {
        List<PeliculaEntity> list = this.peliculaService.getPeliculas();
        assertEquals(this.peliculaList.size(), list.size());
        for (PeliculaEntity entity : list) {
            boolean found = false;
            for (PeliculaEntity storedEntity : this.peliculaList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetBook() throws EntityNotFoundException {
        PeliculaEntity entity = this.peliculaList.get(0);
        PeliculaEntity resultEntity = this.peliculaService.getPelicula(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        assertEquals(entity.getPais(), resultEntity.getPais());
        assertEquals(entity.getIdiomaOriginal(), resultEntity.getIdiomaOriginal());
        assertEquals(entity.getFechaEstreno(), resultEntity.getFechaEstreno());
        assertEquals(entity.getLinkTrailer(), resultEntity.getLinkTrailer());
    }

    @Test
    void testGetInvalidBook() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.peliculaService.getPelicula(0L);
        });
    }

    @Test
    void testUpdatePelicula() throws EntityNotFoundException, IllegalOperationException {
        PeliculaEntity entity = this.peliculaList.get(0);
        PeliculaEntity pojoEntity = this.factory.manufacturePojo(PeliculaEntity.class);
        pojoEntity.setId(entity.getId());
        this.peliculaService.updatePelicula(entity.getId(), pojoEntity);

        PeliculaEntity resp = this.entityManager.find(PeliculaEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDuracion(), resp.getDuracion());
        assertEquals(pojoEntity.getPais(), resp.getPais());
        assertEquals(pojoEntity.getIdiomaOriginal(), resp.getIdiomaOriginal());
        assertEquals(pojoEntity.getFechaEstreno(), resp.getFechaEstreno());
        assertEquals(pojoEntity.getLinkTrailer(), resp.getLinkTrailer());
    }

    @Test
    void testUpdateBookInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            PeliculaEntity pojoEntity = this.factory.manufacturePojo(PeliculaEntity.class);
            pojoEntity.setId(0L);
            this.peliculaService.updatePelicula(0L, pojoEntity);
        });
    }

    @Test
    void testUpdatePeliculaWithNullName() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity entity = this.peliculaList.get(0);
            PeliculaEntity pojoEntity = this.factory.manufacturePojo(PeliculaEntity.class);
            pojoEntity.setNombre(null);
            pojoEntity.setId(entity.getId());
            this.peliculaService.updatePelicula(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdatePeliculaWithInvalidName() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity entity = this.peliculaList.get(0);
            PeliculaEntity pojoEntity = this.factory.manufacturePojo(PeliculaEntity.class);
            pojoEntity.setNombre("");
            pojoEntity.setId(entity.getId());
            this.peliculaService.updatePelicula(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdatePeliculaWithInvalidCountry() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity entity = this.peliculaList.get(0);
            PeliculaEntity pojoEntity = this.factory.manufacturePojo(PeliculaEntity.class);
            pojoEntity.setPais("per");
            pojoEntity.setId(entity.getId());
            this.peliculaService.updatePelicula(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdatePeliculaWithNullLanguage() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity entity = this.peliculaList.get(0);
            PeliculaEntity pojoEntity = this.factory.manufacturePojo(PeliculaEntity.class);
            pojoEntity.setIdiomaOriginal(null);
            pojoEntity.setId(entity.getId());
            this.peliculaService.updatePelicula(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdatePeliculaWithNullReleaseDate() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity entity = this.peliculaList.get(0);
            PeliculaEntity pojoEntity = this.factory.manufacturePojo(PeliculaEntity.class);
            pojoEntity.setFechaEstreno(null);
            pojoEntity.setId(entity.getId());
            this.peliculaService.updatePelicula(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testUpdatePeliculaWithNullLinkTrailer() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            PeliculaEntity entity = this.peliculaList.get(0);
            PeliculaEntity pojoEntity = this.factory.manufacturePojo(PeliculaEntity.class);
            pojoEntity.setLinkTrailer(null);
            pojoEntity.setId(entity.getId());
            this.peliculaService.updatePelicula(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testDeletePelicula() throws EntityNotFoundException, IllegalOperationException {
        PeliculaEntity entity = this.peliculaList.get(1);
        this.peliculaService.deletePelicula(entity.getId());
        PeliculaEntity deleted = entityManager.find(PeliculaEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidPelicula() {
        assertThrows(EntityNotFoundException.class, () -> {
            this.peliculaService.deletePelicula(0L);
        });

    }

}
