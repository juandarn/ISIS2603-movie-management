package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.premiospeliculas.entities.OrganizacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ PremioService.class, PremioOrganizacionService.class })
class PremioOrganizacionTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PremioOrganizacionService premioOrganizacionService;

    @Autowired
    private PremioService premioService;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<OrganizacionEntity> organizacionList = new ArrayList<>();
    private List<PremioEntity> premioList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PremioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from OrganizacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PremioEntity premios = factory.manufacturePojo(PremioEntity.class);
            entityManager.persist(premios);
            premioList.add(premios);
        }
        for (int i = 0; i < 3; i++) {
            OrganizacionEntity entity = factory.manufacturePojo(OrganizacionEntity.class);
            entityManager.persist(entity);
            organizacionList.add(entity);
            if (i == 0) {
                premioList.get(i).setOrganizacion(entity);
            }
        }
    }

    @Test
    void testReplaceOrganizacion() throws EntityNotFoundException {
        PremioEntity entity = premioList.get(0);
        premioOrganizacionService.replaceOrganizacion(entity.getId(), organizacionList.get(1).getId());
        entity = premioService.getPremio(entity.getId());
        assertEquals(entity.getOrganizacion(), organizacionList.get(1));
    }

    /**
     * Prueba para remplazar las instancias de Books asociadas a una instancia de
     * Editorial con un libro que no existe
     * 
     * @throws EntityNotFoundException
     */
    @Test
    void testReplaceOrganizacionInvalidPremio() {
        assertThrows(EntityNotFoundException.class, () -> {
            premioOrganizacionService.replaceOrganizacion(0L, organizacionList.get(1).getId());
        });
    }

    /**
     * Prueba para remplazar las instancias de Books asociadas a una instancia de
     * Editorial que no existe.
     * 
     * @throws EntityNotFoundException
     */
    @Test
    void testReplaceInvalidOrganizacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            PremioEntity entity = premioList.get(0);
            premioOrganizacionService.replaceOrganizacion(entity.getId(), 0L);
        });
    }

    /**
     * Prueba para desasociar un Book existente de un Editorial existente
     * 
     * @throws EntityNotFoundException
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    void testRemoveOrganizacion() throws EntityNotFoundException {
        premioOrganizacionService.removeOrganizacion(premioList.get(0).getId());
        PremioEntity response = premioService.getPremio(premioList.get(0).getId());
        assertNull(response.getOrganizacion());
    }

    /**
     * Prueba para desasociar un Book que no existe de un Editorial
     * 
     * @throws EntityNotFoundException
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    void testRemoveOrganizacionInvalidPremio() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> {
            premioOrganizacionService.removeOrganizacion(0L);
        });
    }

}
