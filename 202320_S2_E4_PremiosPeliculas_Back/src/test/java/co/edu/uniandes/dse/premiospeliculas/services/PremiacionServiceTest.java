package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import co.edu.uniandes.dse.premiospeliculas.entities.GanadorEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.NominadoEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PremiacionService.class)
public class PremiacionServiceTest {

    @Autowired
    private PremiacionService premiacionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PremiacionEntity> premiacionList = new ArrayList<>();
    private List<GanadorEntity> ganadorList = new ArrayList<>();
    private List<NominadoEntity> nominadoList = new ArrayList<>();
    private List<PeliculaEntity> peliculaList = new ArrayList<>();
    private List<PremioEntity> premioList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PremiacionEntity");
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            GanadorEntity ganadorEntity = factory.manufacturePojo(GanadorEntity.class);
            entityManager.persist(ganadorEntity);
            ganadorList.add(ganadorEntity);
        }

        for (int i = 0; i < 3; i++) {
            NominadoEntity nominadoEntity = factory.manufacturePojo(NominadoEntity.class);
            entityManager.persist(nominadoEntity);
            nominadoList.add(nominadoEntity);
        }

        for (int i = 0; i < 3; i++) {
            PeliculaEntity peliculaEntity = factory.manufacturePojo(PeliculaEntity.class);
            entityManager.persist(peliculaEntity);
            peliculaList.add(peliculaEntity);
        }

        for (int i = 0; i < 3; i++) {
            PremioEntity premioEntity = factory.manufacturePojo(PremioEntity.class);
            entityManager.persist(premioEntity);
            premioList.add(premioEntity);
        }

        for (int i = 0; i < 3; i++) {
            PremiacionEntity premiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
            premiacionEntity.setGanador(ganadorList.get(0));
            premiacionEntity.setNominados(nominadoList);
            premiacionEntity.setPremios(premioList);
            premiacionEntity.setPeliculas(peliculaList);
            entityManager.persist(premiacionEntity);
            premiacionList.add(premiacionEntity);
        }
        PremiacionEntity premiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
        premiacionEntity.setGanador(null);
        premiacionEntity.setNominados(new ArrayList<>());
        premiacionEntity.setPremios(new ArrayList<>());
        premiacionEntity.setPeliculas(new ArrayList<>());
        entityManager.persist(premiacionEntity);
        premiacionList.add(premiacionEntity);

    }

    @Test
    void testCreatePremiacion() throws IllegalOperationException {
        PremiacionEntity newPremiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
        newPremiacionEntity.setGanador(ganadorList.get(0));
        newPremiacionEntity.setNominados(nominadoList);
        newPremiacionEntity.setPremios(premioList);
        newPremiacionEntity.setPeliculas(peliculaList);
        newPremiacionEntity.setCategoria("Mejor Actor");
        newPremiacionEntity.setHistoria(
                "En una pequenia ciudad amante del teatro, un anciano llamado Leonard creo el \"Premio Leonard\" para reconocer al mejor actor del festival anual. El premio se convirtio en una tradicion y se extendio globalmente");
        PremiacionEntity resultado = premiacionService.createPremiacion(newPremiacionEntity);
        assertNotNull(resultado);
        PremiacionEntity premiacionEntity = entityManager.find(PremiacionEntity.class, resultado.getId());
        assertEquals(newPremiacionEntity.getId(), premiacionEntity.getId());
        assertEquals(newPremiacionEntity.getCategoria(), premiacionEntity.getCategoria());
        assertEquals(newPremiacionEntity.getHistoria(), premiacionEntity.getHistoria());
    }

    @Test
    void testCreatePremiacionWithNullCategoria() throws IllegalOperationException {
        PremiacionEntity newPremiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
        newPremiacionEntity.setCategoria(null);
        newPremiacionEntity.setHistoria(
                "En una pequenia ciudad amante del teatro, un anciano llamado Leonard creo el \"Premio Leonard\" para reconocer al mejor actor del festival anual. El premio se convirtio en una tradicion y se extendio globalmente");
        newPremiacionEntity.setGanador(ganadorList.get(0));
        newPremiacionEntity.setNominados(nominadoList);
        newPremiacionEntity.setPremios(premioList);
        newPremiacionEntity.setPeliculas(peliculaList);
        try {
            PremiacionEntity resultado = premiacionService.createPremiacion(newPremiacionEntity);
            assertNotNull(resultado);
            PremiacionEntity premiacionEntity = entityManager.find(PremiacionEntity.class, resultado.getId());
            assertEquals(newPremiacionEntity.getId(), premiacionEntity.getId());
            assertEquals(newPremiacionEntity.getCategoria(), premiacionEntity.getCategoria());
            assertEquals(newPremiacionEntity.getHistoria(), premiacionEntity.getHistoria());
        } catch (IllegalOperationException e) {
            assertEquals("La categoria no es valida", e.getMessage());
        }
    }

    @Test
    void testCreatePremiacionWithNullHistoria() throws IllegalOperationException {
        PremiacionEntity newPremiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
        newPremiacionEntity.setCategoria("Mejor Actor");
        newPremiacionEntity.setHistoria(null);
        newPremiacionEntity.setGanador(ganadorList.get(0));
        newPremiacionEntity.setNominados(nominadoList);
        newPremiacionEntity.setPremios(premioList);
        newPremiacionEntity.setPeliculas(peliculaList);
        try {
            PremiacionEntity resultado = premiacionService.createPremiacion(newPremiacionEntity);
            assertNotNull(resultado);
            PremiacionEntity premiacionEntity = entityManager.find(PremiacionEntity.class, resultado.getId());
            assertEquals(newPremiacionEntity.getId(), premiacionEntity.getId());
            assertEquals(newPremiacionEntity.getCategoria(), premiacionEntity.getCategoria());
            assertEquals(newPremiacionEntity.getHistoria(), premiacionEntity.getHistoria());
        } catch (IllegalOperationException e) {
            assertEquals("La historia no es valida", e.getMessage());
        }
    }

    /*
     * @Test
     * void testGetPremiaciones() {
     * List<PremiacionEntity> list = premiacionService.getPremiaciones();
     * assertEquals(premiacionList.size(), list.size());
     * for (PremiacionEntity entity : list) {
     * boolean found = false;
     * for (PremiacionEntity storedEntity : premiacionList) {
     * if (entity.getId().equals(storedEntity.getId())) {
     * found = true;
     * }
     * }
     * assertTrue(found);
     * }
     * }
     */
    @Test
    void testGetPremiacion() throws EntityNotFoundException {
        PremiacionEntity entity = premiacionList.get(0);
        PremiacionEntity resulEntity = premiacionService.getPremiacion(entity.getId());
        assertNotNull(resulEntity);
        assertEquals(entity.getId(), resulEntity.getId());
        assertEquals(entity.getCategoria(), resulEntity.getCategoria());
        assertEquals(entity.getHistoria(), resulEntity.getHistoria());
        assertEquals(entity.getGanador(), resulEntity.getGanador());
        assertEquals(entity.getNominados(), resulEntity.getNominados());
        assertEquals(entity.getPeliculas(), resulEntity.getPeliculas());
        assertEquals(entity.getPremios(), resulEntity.getPremios());

    }

    @Test
    void testGetInvalidPremiacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            premiacionService.getPremiacion(0L);
        });
    }

    @Test
    void testUpdatePremiacion() throws EntityNotFoundException {
        PremiacionEntity entity = premiacionList.get(0);
        PremiacionEntity pojoEntity = factory.manufacturePojo(PremiacionEntity.class);
        pojoEntity.setId(entity.getId());
        premiacionService.updatePremiacion(entity.getId(), pojoEntity);

        PremiacionEntity resp = entityManager.find(PremiacionEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getCategoria(), resp.getCategoria());
        assertEquals(pojoEntity.getHistoria(), resp.getHistoria());
        assertEquals(pojoEntity.getGanador(), resp.getGanador());
        assertEquals(pojoEntity.getNominados(), resp.getNominados());
        assertEquals(pojoEntity.getPeliculas(), resp.getPeliculas());
        assertEquals(pojoEntity.getPremios(), resp.getPremios());
    }

    @Test
    void testUpdatePremiacionInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            PremiacionEntity pojoEntity = factory.manufacturePojo(PremiacionEntity.class);
            pojoEntity.setId(0L);
            premiacionService.updatePremiacion(0L, pojoEntity);
        });
    }

    // Crear una entidad de premiacion que no tenga nada ligado

    @Test
    void testDeletePremiacion() throws EntityNotFoundException,
            IllegalOperationException {
        PremiacionEntity entity = premiacionList.get(3);
        premiacionService.deletePremiacion(entity.getId());
        PremiacionEntity deleted = entityManager.find(PremiacionEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidPremiacion() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> {
            premiacionService.deletePremiacion(0L);
        });
    }

    @Test
    void testDeletePremiacionWithGanador() {
        assertThrows(IllegalOperationException.class, () -> {
            PremiacionEntity entity = premiacionList.get(0);
            premiacionService.deletePremiacion(entity.getId());
        });
    }

    @Test
    void testDeletePremiacionWithNominados() {
        assertThrows(IllegalOperationException.class, () -> {
            PremiacionEntity entity = premiacionList.get(1);
            premiacionService.deletePremiacion(entity.getId());
        });
    }

    @Test
    void testDeletePremiacionWithPeliculas() {
        assertThrows(IllegalOperationException.class, () -> {
            PremiacionEntity entity = premiacionList.get(1);
            premiacionService.deletePremiacion(entity.getId());
        });
    }

    @Test
    void testDeletePremiacionWithPremios() {
        assertThrows(IllegalOperationException.class, () -> {
            PremiacionEntity entity = premiacionList.get(1);
            premiacionService.deletePremiacion(entity.getId());
        });
    }

}
