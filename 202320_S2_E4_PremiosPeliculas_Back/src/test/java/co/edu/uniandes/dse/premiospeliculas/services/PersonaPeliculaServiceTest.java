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

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({PersonaPeliculaService.class, PeliculaService.class})

public class PersonaPeliculaServiceTest {

    @Autowired
    private PersonaPeliculaService personaPeliculaService;

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private PersonaEntity persona = new PersonaEntity();
    private List<PeliculaEntity> peliculaList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void insertData() {
        persona = factory.manufacturePojo(PersonaEntity.class);
		entityManager.persist(persona);
        
        for (int i = 0; i < 3; i++) {
            PeliculaEntity peliculalEntity = factory.manufacturePojo(PeliculaEntity.class);
            peliculalEntity.getPersonas().add(persona);
            entityManager.persist(peliculalEntity);
            peliculaList.add(peliculalEntity);
            persona.getPeliculas().add(peliculalEntity);
        }
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PeliculaEntity");
        entityManager.getEntityManager().createQuery("delete from PersonaEntity");
    }

    @Test
	void testAddPelicula() throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
		peliculaService.createPelicula(newPelicula);

		PeliculaEntity peliculaEntity = personaPeliculaService.addPelicula(persona.getId(), newPelicula.getId());
		assertNotNull(peliculaEntity);

        assertEquals(newPelicula.getId(), peliculaEntity.getId());
        assertEquals(newPelicula.getDuracion(), peliculaEntity.getDuracion());
        assertEquals(newPelicula.getPais(), peliculaEntity.getPais());
        assertEquals(newPelicula.getIdiomaOriginal(), peliculaEntity.getIdiomaOriginal());
        assertEquals(newPelicula.getFechaEstreno(), peliculaEntity.getFechaEstreno());
        assertEquals(newPelicula.getLinkTrailer(), peliculaEntity.getLinkTrailer());

		PeliculaEntity lastPelicula = personaPeliculaService.getPelicula(persona.getId(), newPelicula.getId());

		assertEquals(lastPelicula.getId(), newPelicula.getId());
        assertEquals(lastPelicula.getDuracion(), newPelicula.getDuracion());
        assertEquals(lastPelicula.getPais(), newPelicula.getPais());
        assertEquals(lastPelicula.getIdiomaOriginal(), newPelicula.getIdiomaOriginal());
        assertEquals(lastPelicula.getFechaEstreno(), newPelicula.getFechaEstreno());
        assertEquals(lastPelicula.getLinkTrailer(), newPelicula.getLinkTrailer());

	}

    @Test
	void testAddPeliculaInvalidPersona() {
		assertThrows(EntityNotFoundException.class, () -> {
		    PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
		    peliculaService.createPelicula(newPelicula);
		    personaPeliculaService.addPelicula(0L, newPelicula.getId());
		});
	}

    @Test
	void testAddInvalidPelicula() {
		assertThrows(EntityNotFoundException.class, () -> {
		    personaPeliculaService.addPelicula(persona.getId(), 0L);
		});
	}

    @Test
	void testGetPeliculas() throws EntityNotFoundException {
		List<PeliculaEntity> peliculaEntities = personaPeliculaService.getPeliculas(persona.getId());

		assertEquals(peliculaList.size(), peliculaEntities.size());

		for (int i = 0; i < peliculaList.size(); i++) {
			assertTrue(peliculaEntities.contains(peliculaList.get(0)));
		}
	}

    @Test
	void testGetPeliculasInvalidPersona() {
		assertThrows(EntityNotFoundException.class, () -> {
			personaPeliculaService.getPeliculas(0L);
		});
	}

    @Test
	void testGetPelicula() throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity peliculaEntity = peliculaList.get(0);
		PeliculaEntity newPelicula = personaPeliculaService.getPelicula(persona.getId(), peliculaEntity.getId());
		assertNotNull(newPelicula);

		assertEquals(newPelicula.getId(), peliculaEntity.getId());
        assertEquals(newPelicula.getDuracion(), peliculaEntity.getDuracion());
        assertEquals(newPelicula.getPais(), peliculaEntity.getPais());
        assertEquals(newPelicula.getIdiomaOriginal(), peliculaEntity.getIdiomaOriginal());
        assertEquals(newPelicula.getFechaEstreno(), peliculaEntity.getFechaEstreno());
        assertEquals(newPelicula.getLinkTrailer(), peliculaEntity.getLinkTrailer());
	}

    @Test
	void testGetPeliculaInvalidPersona() {
		assertThrows(EntityNotFoundException.class, () -> {
		    PeliculaEntity peliculaEntity = peliculaList.get(0);
			personaPeliculaService.getPelicula(0L, peliculaEntity.getId());
		});
	}

    @Test
	void testGetInvalidPelicula() {
		assertThrows(EntityNotFoundException.class, () -> {
			personaPeliculaService.getPelicula(persona.getId(), 0L);
		});
	}

    @Test
	void testGetPeliculaNotAssociatedPersona() {
		assertThrows(IllegalOperationException.class, () -> {
			PersonaEntity personaEntity = factory.manufacturePojo(PersonaEntity.class);
			entityManager.persist(personaEntity);

			PeliculaEntity peliculaEntity = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(peliculaEntity);
			personaPeliculaService.getPelicula(personaEntity.getId(), peliculaEntity.getId());
		});
	}

    @Test
	void testReplacePeliculas() throws EntityNotFoundException, IllegalOperationException {
		List<PeliculaEntity> nuevaLista = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) {
			PeliculaEntity entity = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		
		personaPeliculaService.addPeliculas(persona.getId(), nuevaLista);
		
		List<PeliculaEntity> peliculaEntities = entityManager.find(PersonaEntity.class, persona.getId()).getPeliculas();
		for (PeliculaEntity item : nuevaLista) {
			assertTrue(peliculaEntities.contains(item));
		}
    }

    @Test
	void testReplacePeliculasInvalidPersona() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<PeliculaEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PeliculaEntity entity = factory.manufacturePojo(PeliculaEntity.class);
				peliculaService.createPelicula(entity);
				nuevaLista.add(entity);
			}
			personaPeliculaService.addPeliculas(0L, nuevaLista);
		});
	}

	@Test
	void testReplaceInvalidPeliculas() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<PeliculaEntity> nuevaLista = new ArrayList<>();
			PeliculaEntity entity = factory.manufacturePojo(PeliculaEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			personaPeliculaService.addPeliculas(persona.getId(), nuevaLista);
		});
	}

	@Test
	void testRemovePelicula() throws EntityNotFoundException {
		for (PeliculaEntity pelicula : peliculaList) {
			personaPeliculaService.removePelicula(persona.getId(), pelicula.getId());
		}
		assertTrue(personaPeliculaService.getPeliculas(persona.getId()).isEmpty());
	}

	@Test
	void testRemovePeliculaInvalidPersona() {
		assertThrows(EntityNotFoundException.class, () -> {
			for (PeliculaEntity pelicula : peliculaList) {
				personaPeliculaService.removePelicula(0L, pelicula.getId());
			}
		});
	}

	@Test
	void testRemoveInvalidPelicula() {
		assertThrows(EntityNotFoundException.class, () -> {
			personaPeliculaService.removePelicula(persona.getId(), 0L);
		});
	}
	

}
