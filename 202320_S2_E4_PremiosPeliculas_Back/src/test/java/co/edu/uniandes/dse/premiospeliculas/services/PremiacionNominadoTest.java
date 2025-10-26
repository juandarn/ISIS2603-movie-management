package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;

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

import co.edu.uniandes.dse.premiospeliculas.entities.NominadoEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ PremiacionNominadoService.class, NominadoService.class })
public class PremiacionNominadoTest {
    @Autowired
    private PremiacionNominadoService premiacionNominadoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PremiacionEntity> premiacionList = new ArrayList<>();
    private List<NominadoEntity> nominadoList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from NominadoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from PremiacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            NominadoEntity nominado = factory.manufacturePojo(NominadoEntity.class);
            entityManager.persist(nominado);
            nominadoList.add(nominado);
        }

        for (int i = 0; i < 3; i++) {
            PremiacionEntity entity = factory.manufacturePojo(PremiacionEntity.class);
            entityManager.persist(entity);
            premiacionList.add(entity);
            if (i == 0) {
                nominadoList.get(i).setPremiacion(entity);
                entity.getNominados().add(nominadoList
                        .get(i));
            }
        }
    }

    @Test
    void testAddNominado() throws EntityNotFoundException {
        PremiacionEntity entity = premiacionList.get(0);
        NominadoEntity nominadoEntity = nominadoList.get(1);
        NominadoEntity response = premiacionNominadoService.addNominado(entity.getId(), nominadoEntity.getId());

        assertNotNull(response);
        assertEquals(nominadoEntity.getId(), response.getId());
    }

    @Test
    void testAddInvalidNominado() {
        assertThrows(EntityNotFoundException.class, () -> {
            PremiacionEntity entity = premiacionList.get(0);
            premiacionNominadoService.addNominado(entity.getId(), 0L);
        });
    }

    @Test
    void testAddNominadoInvalidPremiacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            NominadoEntity nominadoEntity = nominadoList.get(1);
            premiacionNominadoService.addNominado(0L, nominadoEntity.getId());
        });
    }

    @Test
    void testGetNominados() throws EntityNotFoundException {
        List<NominadoEntity> list = premiacionNominadoService.getNominados(premiacionList.get(0).getId());
        assertEquals(1, list.size());
    }

    @Test
    void testGetNominadosInvalidPremiacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            premiacionNominadoService.getNominados(0L);
        });
    }

    /**
     * Prueba para obtener una instancia de Book asociada a una instancia Editorial.
     * 
     * @throws IllegalOperationException
     * @throws EntityNotFoundException
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    void testGetNominado() throws EntityNotFoundException, IllegalOperationException {
        PremiacionEntity entity = premiacionList.get(0);
        NominadoEntity nominadoEntity = nominadoList.get(0);
        NominadoEntity response = premiacionNominadoService.getNominado(entity.getId(), nominadoEntity.getId());

        assertEquals(nominadoEntity.getId(), response.getId());

    }

    /**
     * Prueba para obtener una instancia de Book asociada a una instancia Editorial
     * que no existe.
     * 
     * @throws EntityNotFoundException
     *
     */
    @Test
    void testGetNominadoInvalidPremiacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            NominadoEntity nominadoEntity = nominadoList.get(0);
            premiacionNominadoService.getNominado(0L, nominadoEntity.getId());
        });
    }

    @Test
    void testGetInvalidNominado() {
        assertThrows(EntityNotFoundException.class, () -> {
            PremiacionEntity entity = premiacionList.get(0);
            premiacionNominadoService.getNominado(entity.getId(), 0L);
        });
    }

    @Test
    public void getNominadoNoAsociadoTest() {
        assertThrows(IllegalOperationException.class, () -> {
            PremiacionEntity entity = premiacionList.get(0);
            NominadoEntity nominadoEntity = nominadoList.get(1);
            premiacionNominadoService.getNominado(entity.getId(), nominadoEntity.getId());
        });
    }

    /**
     * Prueba para remplazar las instancias de Books asociadas a una instancia de
     * Editorial.
     */
    // @Test
    // void testReplaceNominados() throws EntityNotFoundException {
    // PremiacionEntity entity = premiacionList.get(0);
    // List<NominadoEntity> list = nominadoList.subList(1, 3);
    // premiacionNominadoService.replaceNominados(entity.getId(), list);

    // for (NominadoEntity nominado : list) {
    // NominadoEntity n = entityManager.find(NominadoEntity.class,
    // nominado.getId());
    // assertTrue(n.getPremiacion().equals(entity));
    // }
    // }

    /**
     * Prueba para remplazar las instancias de Books que no existen asociadas a una
     * instancia de
     * Editorial.
     */
    @Test
    void testReplaceInvalidNominado() {
        assertThrows(EntityNotFoundException.class, () -> {
            PremiacionEntity entity = premiacionList.get(0);

            List<NominadoEntity> nominados = new ArrayList<>();
            NominadoEntity newNominado = factory.manufacturePojo(NominadoEntity.class);
            newNominado.setId(0L);
            nominados.add(newNominado);

            premiacionNominadoService.replaceNominados(entity.getId(), nominados);
        });
    }

    /**
     * Prueba para remplazar las instancias de Books asociadas a una instancia de
     * Editorial que no existe.
     */
    @Test
    void testReplaceNominadosInvalidPremiacion() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> {
            List<NominadoEntity> list = nominadoList.subList(1, 3);
            premiacionNominadoService.replaceNominados(0L, list);
        });
    }
}
