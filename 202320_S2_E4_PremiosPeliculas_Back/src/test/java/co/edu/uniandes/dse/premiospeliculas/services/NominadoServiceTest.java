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
import co.edu.uniandes.dse.premiospeliculas.entities.NominadoEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(NominadoService.class)

public class NominadoServiceTest {
    @Autowired
    private NominadoService nominadoservice;
    private List<NominadoEntity> ListNominados = new ArrayList<>();
    private List<PersonaEntity> ListPersonas = new ArrayList<>();
    private List<PremiacionEntity> ListPremiacion = new ArrayList<>();

    private PodamFactory factory = new PodamFactoryImpl();
    NominadoEntity newEntity = factory.manufacturePojo(NominadoEntity.class);
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            NominadoEntity nominadoEntity = factory.manufacturePojo(NominadoEntity.class);
            this.entityManager.persist(nominadoEntity);
            this.ListNominados.add(nominadoEntity);
        }
        for (int i = 0; i < 3; i++) {
            PersonaEntity personaEntity = factory.manufacturePojo(PersonaEntity.class);
            this.entityManager.persist(personaEntity);
            this.ListPersonas.add(personaEntity);
        }
        for (int i = 0; i < 3; i++) {
            PremiacionEntity premiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
            this.entityManager.persist(premiacionEntity);
            this.ListPremiacion.add(premiacionEntity);
        }

    }

    @Test
    void testCreateNominado() throws EntityNotFoundException, IllegalOperationException {
        NominadoEntity newNominadoEntity = factory.manufacturePojo(NominadoEntity.class);
        // newNominadoEntity.setPersona(ListPersonas.get(0));
        newNominadoEntity.setPremiacion(ListPremiacion.get(0));
        NominadoEntity Resultado = this.nominadoservice.createNominado(newNominadoEntity);
        assertNotNull(Resultado);
        NominadoEntity entity = entityManager.find(NominadoEntity.class, Resultado.getId());
        assertEquals(newNominadoEntity.getId(), entity.getId());
        // assertEquals(newNominadoEntity.getPersona(), entity.getPersona());
        assertEquals(newNominadoEntity.getPremiacion(), entity.getPremiacion());
    }



    @Test
    void testGetNominados() {
        List<NominadoEntity> list = nominadoservice.getNominados();
        assertEquals(ListNominados.size(), list.size());
        for (NominadoEntity entity : list) {
            boolean found = false;
            for (NominadoEntity storedEntity : ListNominados) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetNominado() throws EntityNotFoundException {
        NominadoEntity entity = ListNominados.get(0);
        NominadoEntity resulEntity = nominadoservice.getNominado(entity.getId());
        assertNotNull(resulEntity);
        assertEquals(entity.getId(), resulEntity.getId());
        // assertEquals(entity.getPersona(), resulEntity.getPersona());
        assertEquals(entity.getPremiacion(), resulEntity.getPremiacion());
    }

    @Test
    void testGetInvalidNominado() {
        assertThrows(EntityNotFoundException.class, () -> {
            nominadoservice.getNominado(0L);
        });
    }

    @Test
    void testUpdateNominado() throws IllegalOperationException, EntityNotFoundException {
        NominadoEntity entity = ListNominados.get(0);
        NominadoEntity factorentity = factory.manufacturePojo(NominadoEntity.class);
        factorentity.setId(entity.getId());
        nominadoservice.UpdateNominado(entity.getId(), factorentity);

        NominadoEntity resultado = entityManager.find(NominadoEntity.class, entity.getId());
        assertEquals(factorentity.getId(), resultado.getId());
        // assertEquals(factorentity.getPersona(), resultado.getPersona());
        assertEquals(factorentity.getPremiacion(), resultado.getPremiacion());

    }

    @Test
    void testUpdateNominadoInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            NominadoEntity newEntity = factory.manufacturePojo(NominadoEntity.class);
            newEntity.setId(0L);
            nominadoservice.UpdateNominado(0L, newEntity);
        });
    }

    @Test
    void testDeleteNominado() throws EntityNotFoundException, IllegalOperationException {
        NominadoEntity entity = ListNominados.get(1);
        nominadoservice.deleteNominado(entity.getId());
        NominadoEntity deleted = entityManager.find(NominadoEntity.class, entity.getId());
        assertNull(deleted);

    }

    @Test
    void testDeleteNominadoInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            nominadoservice.deleteNominado(0L);
        });
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from NominadoEntity");
    }

}
