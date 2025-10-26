package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import co.edu.uniandes.dse.premiospeliculas.entities.OrganizacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

//anotaciones para crear los test de los services
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(OrganizacionService.class)
public class OrganizacionServiceTest {

    // Instancia del servicio
    @Autowired
    private OrganizacionService organizacionService;

    @Autowired
    private TestEntityManager entityManager;

    // Crear una fabrica de datos con la libreria podam para probar con varios datos
    private PodamFactory factory = new PodamFactoryImpl();

    private List<OrganizacionEntity> organizacionList = new ArrayList<>();
    private List<PremioEntity> premioList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from OrganizacionEntity");
    }

    private void insertData() {

        for (int i = 0; i < 3; i++) {
            PremioEntity premioEntity = factory.manufacturePojo(PremioEntity.class);
            entityManager.persist(premioEntity);
            premioList.add(premioEntity);
        }

        for (int i = 0; i < 3; i++) {
            OrganizacionEntity organizacionEntity = factory.manufacturePojo(OrganizacionEntity.class);
            entityManager.persist(organizacionEntity);
            organizacionList.add(organizacionEntity);
            organizacionEntity.setPremios(premioList);
        }

        // creacion de organizacion sin premios
        OrganizacionEntity organizacionEntity = factory.manufacturePojo(OrganizacionEntity.class);
        List<PremioEntity> premioList1 = new ArrayList<>();
        organizacionEntity.setPremios(premioList1);
        entityManager.persist(organizacionEntity);
        organizacionList.add(organizacionEntity);

    }

    // Test para ver si se crear la organizacion
    @Test
    void testCreateOrganizacion() throws IllegalOperationException {
        // se instancia la fabrica de datos para crear una nueva organizacion con datos
        // aleatorios
        OrganizacionEntity newOrganizacionEntity = factory.manufacturePojo(OrganizacionEntity.class);
        newOrganizacionEntity.setNombre("Oscares");
        newOrganizacionEntity.setPremios(premioList);
        OrganizacionEntity resultado = organizacionService.createOrganizacion(newOrganizacionEntity);
        assertNotNull(resultado);
        OrganizacionEntity organizacionEntity = entityManager.find(OrganizacionEntity.class, resultado.getId());
        assertEquals(newOrganizacionEntity.getId(), organizacionEntity.getId());
        assertEquals(newOrganizacionEntity.getNombre(), organizacionEntity.getNombre());

    }

    @Test
    void testCreateOrganizacionWithNullName() throws IllegalOperationException {
        // se instancia la fabrica de datos para crear una nueva organizacion con datos
        // aleatorios
        OrganizacionEntity newOrganizacionEntity = factory.manufacturePojo(OrganizacionEntity.class);
        newOrganizacionEntity.setNombre(null);
        newOrganizacionEntity.setPremios(premioList);
        try {
            OrganizacionEntity resultado = organizacionService.createOrganizacion(newOrganizacionEntity);
            assertNotNull(resultado);
            OrganizacionEntity organizacionEntity = entityManager.find(OrganizacionEntity.class, resultado.getId());
            assertEquals(newOrganizacionEntity.getId(), organizacionEntity.getId());
            assertEquals(newOrganizacionEntity.getNombre(), organizacionEntity.getNombre());
        } catch (IllegalOperationException e) {
            assertEquals("El nombre no es validó", e.getMessage());
        }
    }

    @Test
    void testCreateOrganizacionWithEmptyName() throws IllegalOperationException {
        // se instancia la fabrica de datos para crear una nueva organizacion con datos
        // aleatorios
        OrganizacionEntity newOrganizacionEntity = factory.manufacturePojo(OrganizacionEntity.class);
        newOrganizacionEntity.setNombre("");
        newOrganizacionEntity.setPremios(premioList);
        try {
            OrganizacionEntity resultado = organizacionService.createOrganizacion(newOrganizacionEntity);
            assertNotNull(resultado);
            OrganizacionEntity organizacionEntity = entityManager.find(OrganizacionEntity.class, resultado.getId());
            assertEquals(newOrganizacionEntity.getId(), organizacionEntity.getId());
            assertEquals(newOrganizacionEntity.getNombre(), organizacionEntity.getNombre());
        } catch (IllegalOperationException e) {
            assertEquals("El nombre no es validó", e.getMessage());
        }
    }

    @Test
    void testGetOrganizacion() throws EntityNotFoundException {
        OrganizacionEntity entity = organizacionList.get(0);
        OrganizacionEntity resultEntity = organizacionService.getOrganizacion(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getPremios(), resultEntity.getPremios());
    }

    @Test
    void testGetInvalidOrganizacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            organizacionService.getOrganizacion(0L);
        });
    }

    @Test
    void testUpdateOrganizacion() throws EntityNotFoundException {
        OrganizacionEntity entity = organizacionList.get(0);
        OrganizacionEntity pojoEntity = factory.manufacturePojo(OrganizacionEntity.class);
        pojoEntity.setId(entity.getId());
        organizacionService.updateOrganizacion(entity.getId(), pojoEntity);
        OrganizacionEntity resp = entityManager.find(OrganizacionEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getPremios(), resp.getPremios());
    }

    @Test
    void testUpdateOrganizacionInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            OrganizacionEntity pojoEntity = factory.manufacturePojo(OrganizacionEntity.class);
            pojoEntity.setId(0L);
            organizacionService.updateOrganizacion(0L, pojoEntity);
        });
    }

    // Crear una entidad organizacion que no tenga premios

    @Test
    void testDeleteOrganizacion() throws EntityNotFoundException,
            IllegalOperationException {
        OrganizacionEntity entity = organizacionList.get(3);
        organizacionService.deleteOrganizacion(entity.getId());
        OrganizacionEntity deleted = entityManager.find(OrganizacionEntity.class,
                entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidOrganizacion() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> {
            organizacionService.deleteOrganizacion(0L);
        });
    }

    @Test
    void testDeleteOrganizacionWithPremios() {
        assertThrows(IllegalOperationException.class, () -> {
            OrganizacionEntity entity = organizacionList.get(0);
            organizacionService.deleteOrganizacion(entity.getId());
        });
    }
}
