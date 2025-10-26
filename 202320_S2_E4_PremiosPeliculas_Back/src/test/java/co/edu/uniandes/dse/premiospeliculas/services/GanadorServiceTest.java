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

import co.edu.uniandes.dse.premiospeliculas.entities.GanadorEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
//import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(GanadorService.class)

public class GanadorServiceTest {
    @Autowired // Funciona para no tener que generar un new cuando se importa de la logica a la
               // persistencia
    private GanadorService ganadorService;
    private List<GanadorEntity> ListaGanadores = new ArrayList<>();
    private List<PersonaEntity> ListaPersonas = new ArrayList<>();
    private List<PremiacionEntity> ListaPremiacion = new ArrayList<>();

    private PodamFactory factory = new PodamFactoryImpl();
    GanadorEntity newEntity = factory.manufacturePojo(GanadorEntity.class);

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        insertData();
        clearData();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            GanadorEntity ganadorentity = factory.manufacturePojo(GanadorEntity.class);
            this.entityManager.persist(ganadorentity);
            this.ListaGanadores.add(ganadorentity);
        }
        for (int i = 0; i < 3; i++) {
            PersonaEntity personaEntity = factory.manufacturePojo(PersonaEntity.class);
            this.entityManager.persist(personaEntity);
            this.ListaPersonas.add(personaEntity);
        }
        for (int i = 0; i < 3; i++) {
            PremiacionEntity premiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
            this.entityManager.persist(premiacionEntity);
            this.ListaPremiacion.add(premiacionEntity);
        }
    }

    @Test
    void testCreateGanador() throws EntityNotFoundException, IllegalOperationException {
        GanadorEntity newGanadorEntity = factory.manufacturePojo(GanadorEntity.class);
        newGanadorEntity.setPersona(ListaPersonas.get(0));
        newGanadorEntity.setPremiacion(ListaPremiacion.get(0));
        GanadorEntity Resultado = ganadorService.CreateGanador(newGanadorEntity);
        assertNotNull(Resultado);
        GanadorEntity entity = entityManager.find(GanadorEntity.class, Resultado.getId());
        assertEquals(newGanadorEntity.getId(), entity.getId());
        assertEquals(newGanadorEntity.getPersona(), entity.getPersona());
        assertEquals(newGanadorEntity.getPremiacion(), entity.getPremiacion());
    }



    @Test
    void testGetGanadores() {
        List<GanadorEntity> lista = ganadorService.GetGanadores();
        assertEquals(ListaGanadores.size(), lista.size());
        for (GanadorEntity entity : lista) {
            boolean found = false;
            for (GanadorEntity storedentity : ListaGanadores) {
                if (entity.getId().equals(storedentity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);

        }

    }

    @Test
    void testGetGanador() throws EntityNotFoundException {
        GanadorEntity entity = ListaGanadores.get(0);
        GanadorEntity resulEntity = ganadorService.getGanador(entity.getId());
        assertNotNull(resulEntity);
        assertEquals(entity.getId(), resulEntity.getId());
        assertEquals(entity.getPersona(), resulEntity.getPersona());
        assertEquals(entity.getPremiacion(), resulEntity.getPremiacion());

    }

    @Test
    void testGetGanadorInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            ganadorService.getGanador(0L);
        });
    }

    @Test
    void testUpdateGanador() throws IllegalOperationException, EntityNotFoundException {
        GanadorEntity entity = ListaGanadores.get(0);
        GanadorEntity factorentity = factory.manufacturePojo(GanadorEntity.class);
        factorentity.setId(entity.getId());
        ganadorService.UpdateGanador(entity.getId(), factorentity);

        GanadorEntity resultado = entityManager.find(GanadorEntity.class, entity.getId());
        assertEquals(factorentity.getId(), resultado.getId());
        assertEquals(factorentity.getPersona(), resultado.getPersona());
        assertEquals(factorentity.getPremiacion(), resultado.getPremiacion());
    }

    @Test
    void testUpdateGanadorInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            GanadorEntity newEntity = factory.manufacturePojo(GanadorEntity.class);
            newEntity.setId(0L);
            ganadorService.UpdateGanador(0L, newEntity);
        });
    }

    @Test
    void testDeleteGanador() throws EntityNotFoundException, IllegalOperationException {
        GanadorEntity entity = ListaGanadores.get(1);
        ganadorService.DeleteGanador(entity.getId());
        GanadorEntity delted = entityManager.find(GanadorEntity.class, entity.getId());
        assertNull(delted);

    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from GanadorEntity");
    }

}
