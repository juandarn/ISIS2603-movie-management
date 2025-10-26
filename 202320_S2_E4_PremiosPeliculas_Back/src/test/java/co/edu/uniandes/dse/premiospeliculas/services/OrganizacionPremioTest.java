package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
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
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ OrganizacionPremioService.class, PremioService.class })
public class OrganizacionPremioTest {
    @Autowired
    private OrganizacionPremioService organizacionPremioService;

    @Autowired
    private PremioService premioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private OrganizacionEntity organizacion = new OrganizacionEntity();
    private List<PremioEntity> premiosList = new ArrayList<>();
    private PremiacionEntity premiacion = new PremiacionEntity();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from OrganizacionEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from PremioEntity").executeUpdate();
    }

    private void insertData() {
        organizacion = factory.manufacturePojo(OrganizacionEntity.class);
        organizacion.setPremios(new ArrayList<>());
        entityManager.persist(organizacion);

        premiacion = factory.manufacturePojo(PremiacionEntity.class);
        entityManager.persist(premiacion);

        for (int i = 0; i < 3; i++) {
            PremioEntity premio = factory.manufacturePojo(PremioEntity.class);
            premio.setFecha(null);
            premio.setPais("US");
            premio.setPremiacion(premiacion);
            premio.setOrganizacion(organizacion);
            entityManager.persist(premio);
            organizacion.getPremios().add(premio);
            premiosList.add(premio);
        }
    }

    // @Test
    // void testAddPremio() throws EntityNotFoundException,
    // IllegalOperationException {
    // PremioEntity newPremio = factory.manufacturePojo(PremioEntity.class);
    // newPremio.setOrganizacion(organizacion);
    // newPremio.setPremiacion(premiacion);
    // newPremio.setFecha(new Date());
    // newPremio.setPais("US");
    // premioService.createPremio(newPremio);

    // PremioEntity premio =
    // organizacionPremioService.addPremio(organizacion.getId(), newPremio.getId());
    // assertNotNull(premio);

    // assertEquals(premio.getId(), newPremio.getId());
    // assertEquals(premio.getFecha(), newPremio.getFecha());
    // assertEquals(premio.getPais(), newPremio.getPais());
    // assertEquals(premio.getPremiacion(), newPremio.getPremiacion());

    // PremioEntity lastPremio =
    // organizacionPremioService.getPremio(organizacion.getId(), newPremio.getId());

    // assertEquals(lastPremio.getId(), newPremio.getId());
    // assertEquals(lastPremio.getFecha(), newPremio.getFecha());
    // assertEquals(lastPremio.getPais(), newPremio.getPais());
    // assertEquals(lastPremio.getPremiacion(), newPremio.getPremiacion());

    // }

    @Test
    void testAddPremioWithOrganizationNull() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(IllegalOperationException.class, () -> {
            PremioEntity newPremio = factory.manufacturePojo(PremioEntity.class);
            newPremio.setOrganizacion(null);
            newPremio.setPremiacion(premiacion);
            newPremio.setFecha(new Date());
            newPremio.setPais("US");
            premioService.createPremio(newPremio);

            PremioEntity premio = organizacionPremioService.addPremio(organizacion.getId(), newPremio.getId());
            assertNotNull(premio);

            assertEquals(premio.getId(), newPremio.getId());
            assertEquals(premio.getFecha(), newPremio.getFecha());
            assertEquals(premio.getPais(), newPremio.getPais());
            assertEquals(premio.getPremiacion(), newPremio.getPremiacion());

            PremioEntity lastPremio = organizacionPremioService.getPremio(organizacion.getId(), newPremio.getId());

            assertEquals(lastPremio.getId(), newPremio.getId());
            assertEquals(lastPremio.getFecha(), newPremio.getFecha());
            assertEquals(lastPremio.getPais(), newPremio.getPais());
            assertEquals(lastPremio.getPremiacion(), newPremio.getPremiacion());
        });

    }

    @Test
    void getPremio() throws EntityNotFoundException, IllegalOperationException {
        PremioEntity premioEntity = premiosList.get(0);
        PremioEntity premio = organizacionPremioService.getPremio(organizacion.getId(), premioEntity.getId());
        assertNotNull(premio);

        assertEquals(premio.getId(), premioEntity.getId());
        assertEquals(premio.getFecha(), premioEntity.getFecha());
        assertEquals(premio.getPais(), premioEntity.getPais());
        assertEquals(premio.getPremiacion(), premioEntity.getPremiacion());
    }

    @Test
    void getPremioWithInvalidOrganizacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            PremioEntity premioEntity = premiosList.get(0);
            organizacionPremioService.getPremio(0L, premioEntity.getId());
        });
    }
    // TODO: Revisar
    // @Test
    // void getPremioNotAssociatedOrganizacion() {
    // assertThrows(IllegalOperationException.class, () -> {
    // OrganizacionEntity organizacionEntity =
    // factory.manufacturePojo(OrganizacionEntity.class);
    // organizacionEntity.setPremios(premiosList);
    // entityManager.persist(organizacionEntity);

    // PremioEntity premioEntity = factory.manufacturePojo(PremioEntity.class);
    // premioEntity.setOrganizacion(new OrganizacionEntity());
    // premioEntity.setPais("US");
    // premioEntity.setFecha(new Date());
    // premioEntity.setPremiacion(premiacion);
    // entityManager.persist(premioEntity);

    // organizacionPremioService.getPremio(organizacionEntity.getId(),
    // premioEntity.getId());
    // });
    // }

    @Test
    void testGetPremios() throws EntityNotFoundException {
        List<PremioEntity> premios = organizacionPremioService.getPremios(organizacion.getId());
        assertEquals(premiosList.size(), premios.size());

        for (int i = 0; i < premiosList.size(); i++) {
            assertTrue(premios.contains(premiosList.get(i)));
        }
    }

    @Test
    void testGetPremiosWithInvalidOrganizacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            organizacionPremioService.getPremios(0L);
        });
    }

    @Test
    void replacePremios() throws EntityNotFoundException, IllegalOperationException {
        List<PremioEntity> newPremios = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            PremioEntity premio = factory.manufacturePojo(PremioEntity.class);
            premio.setFecha(new Date());
            premio.setPais("US");
            premio.setPremiacion(premiacion);
            premio.setOrganizacion(organizacion);
            entityManager.persist(premio);
            newPremios.add(premio);
        }

        organizacionPremioService.replacePremios(organizacion.getId(), newPremios);

        List<PremioEntity> premios = organizacionPremioService.getPremios(organizacion.getId());
        for (PremioEntity premio : premios) {
            assertTrue(newPremios.contains(premio));
        }
    }

    @Test
    void replacePremiosWithInvalidOrganizacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<PremioEntity> newPremios = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                PremioEntity premio = factory.manufacturePojo(PremioEntity.class);
                premio.setFecha(new Date());
                premio.setPais("US");
                premio.setPremiacion(premiacion);
                premio.setOrganizacion(organizacion);
                entityManager.persist(premio);
                newPremios.add(premio);
            }

            organizacionPremioService.replacePremios(0L, newPremios);
        });
    }

    @Test
    void replaceInvalidPremios() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<PremioEntity> nuevaLita = new ArrayList<>();
            PremioEntity premio = factory.manufacturePojo(PremioEntity.class);
            premio.setFecha(new Date());
            premio.setPais("US");
            premio.setPremiacion(premiacion);
            premio.setOrganizacion(organizacion);
            premio.setId(0L);
            nuevaLita.add(premio);
            organizacionPremioService.replacePremios(organizacion.getId(), nuevaLita);
        });
    }

    @Test
    void removePremio() throws EntityNotFoundException {
        for (PremioEntity premio : premiosList) {
            organizacionPremioService.removePremio(organizacion.getId(), premio.getId());
        }
        assertTrue(organizacionPremioService.getPremios(organizacion.getId()).isEmpty());
    }

    @Test
    void removePremioWithInvalidOrganizacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            PremioEntity premio = premiosList.get(0);
            organizacionPremioService.removePremio(0L, premio.getId());
        });
    }
}
