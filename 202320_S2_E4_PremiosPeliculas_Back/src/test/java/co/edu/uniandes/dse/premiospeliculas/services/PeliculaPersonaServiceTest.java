package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.*;

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
@Import(PeliculaPersonaService.class)

class PeliculaPersonaServiceTest {
	
	@Autowired
	private PeliculaPersonaService peliculaPersonaService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private PeliculaEntity pelicula = new PeliculaEntity();
	private List<PersonaEntity> personaEntities = new ArrayList<>();

	
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	
	
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from PeliculaEntity");
        entityManager.getEntityManager().createQuery("delete from PersonaEntity");
	}

	
	private void insertData() {

		this.pelicula = factory.manufacturePojo(PeliculaEntity.class);
		entityManager.persist(this.pelicula);


		for (int i = 0; i < 3; i++) {
			PersonaEntity personaEntity = factory.manufacturePojo(PersonaEntity.class);
			personaEntity.setPeliculas(new ArrayList<PeliculaEntity>());
			personaEntity.getPeliculas().add(this.pelicula);
			entityManager.persist(personaEntity);
			this.personaEntities.add(personaEntity);
			this.pelicula.getPersonas().add(personaEntity);	
		}
	}

	
	@Test
	void testAddPersona() throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
		entityManager.persist(newPelicula);
		
		PersonaEntity persona = factory.manufacturePojo(PersonaEntity.class);
		persona.setPeliculas(new ArrayList<PeliculaEntity>());
		persona.getPeliculas().add(newPelicula);
		entityManager.persist(persona);
		
		this.peliculaPersonaService.addPersona(newPelicula.getId(), persona.getId());
		
		
		PersonaEntity lastPersona = this.peliculaPersonaService.getPersona(newPelicula.getId(), persona.getId());
		assertEquals(persona.getId(), lastPersona.getId());
		assertEquals(persona.getNombre(), lastPersona.getNombre());	
		assertEquals(persona.getBiografia(), lastPersona.getBiografia());
        assertEquals(persona.getFechaNacimiento(), lastPersona.getFechaNacimiento());
        assertEquals(persona.getId(), lastPersona.getId());
        assertEquals(persona.getNacionalidad(), lastPersona.getNacionalidad());
        assertEquals(persona.getRol(), lastPersona.getRol());

		
		
	}
	
	
	@Test
	void testAddInvalidPersona() {
		assertThrows(EntityNotFoundException.class, ()->{
			PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(newPelicula);
			this.peliculaPersonaService.addPersona(newPelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testAddPersonaInvaliPelicula() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			PersonaEntity persona = factory.manufacturePojo(PersonaEntity.class);
			entityManager.persist(persona);
			this.peliculaPersonaService.addPersona(0L, persona.getId());
		});
	}


	@Test
	void testGetPersonaes() throws EntityNotFoundException {
		List<PersonaEntity> personaEntities = this.peliculaPersonaService.getPersonas(this.pelicula.getId());

		assertEquals(this.personaEntities.size(), personaEntities.size());

		for (int i = 0; i < this.personaEntities.size(); i++) {
			assertTrue(personaEntities.contains(this.personaEntities.get(0)));
		}
	}
	
	
	@Test
	void testGetPersonaesInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPersonaService.getPersonas(0L);
		});
	}


	@Test
	void testGetPersona() throws EntityNotFoundException, IllegalOperationException {
		PersonaEntity personaEntity = this.personaEntities.get(0);
		PersonaEntity persona = this.peliculaPersonaService.getPersona(this.pelicula.getId(), personaEntity.getId());
		assertNotNull(persona);

		assertEquals(personaEntity.getId(), persona.getId());
		assertEquals(personaEntity.getNombre(), persona.getNombre());
		assertEquals(personaEntity.getBiografia(), persona.getBiografia());
        assertEquals(personaEntity.getFechaNacimiento(), persona.getFechaNacimiento());
        assertEquals(personaEntity.getId(), persona.getId());
        assertEquals(personaEntity.getNacionalidad(), persona.getNacionalidad());
        assertEquals(personaEntity.getRol(), persona.getRol());
	}
	

	@Test
	void testGetInvalidPersona()  {
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPersonaService.getPersona(this.pelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testGetPersonaInvalidPelicula() {
		assertThrows(EntityNotFoundException.class, ()->{
			PersonaEntity personaEntity = this.personaEntities.get(0);
			this.peliculaPersonaService.getPersona(0L, personaEntity.getId());
		});
	}
	
	
	@Test
	void testGetNotAssociatedPersona() {
		assertThrows(IllegalOperationException.class, ()->{
			PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(newPelicula);
			PersonaEntity persona = factory.manufacturePojo(PersonaEntity.class);
			persona.setPeliculas(new ArrayList<PeliculaEntity>());
			entityManager.persist(persona);
			this.peliculaPersonaService.getPersona(newPelicula.getId(), persona.getId());
		});
	}


	@Test
	void testReplacePersonaes() throws EntityNotFoundException {
		List<PersonaEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PersonaEntity entity = factory.manufacturePojo(PersonaEntity.class);
			entityManager.persist(entity);
			this.pelicula.getPersonas().add(entity);
			nuevaLista.add(entity);
		}
		this.peliculaPersonaService.replacePersonas(this.pelicula.getId(), nuevaLista);
		
		List<PersonaEntity> personaEntities = this.peliculaPersonaService.getPersonas(this.pelicula.getId());
		for (PersonaEntity aNuevaLista : nuevaLista) {
			assertTrue(personaEntities.contains(aNuevaLista));
		}
	}

	@Test
	void testReplacePersonaes2() throws EntityNotFoundException {
		List<PersonaEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PersonaEntity entity = factory.manufacturePojo(PersonaEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		this.peliculaPersonaService.replacePersonas(this.pelicula.getId(), nuevaLista);
		
		List<PersonaEntity> personaEntities = this.peliculaPersonaService.getPersonas(this.pelicula.getId());
		for (PersonaEntity aNuevaLista : nuevaLista) {
			assertTrue(personaEntities.contains(aNuevaLista));
		}
	}
	
	
	
	@Test
	void testReplacePersonaesInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<PersonaEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PersonaEntity entity = factory.manufacturePojo(PersonaEntity.class);
				entity.setPeliculas(new ArrayList<PeliculaEntity>());
				entity.getPeliculas().add(this.pelicula);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			this.peliculaPersonaService.replacePersonas(0L, nuevaLista);
		});
	}
	

	@Test
	void testReplaceInvalidPersonaes() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<PersonaEntity> nuevaLista = new ArrayList<>();
			PersonaEntity entity = factory.manufacturePojo(PersonaEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			this.peliculaPersonaService.replacePersonas(this.pelicula.getId(), nuevaLista);
		});
	}
	
	
	@Test
	void testReplacePreInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<PersonaEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PersonaEntity entity = factory.manufacturePojo(PersonaEntity.class);
				entity.getPeliculas().add(this.pelicula);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			this.peliculaPersonaService.replacePersonas(0L, nuevaLista);
		});
	}

	
	@Test
	void testRemovePersona() throws EntityNotFoundException {
		for (PersonaEntity persona : this.personaEntities) {
			this.peliculaPersonaService.removePersona(this.pelicula.getId(), persona.getId());
		}
		assertTrue(this.peliculaPersonaService.getPersonas(this.pelicula.getId()).isEmpty());
	}
	
	@Test
	void testRemoveInvalidPersona(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPersonaService.removePersona(this.pelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testRemovePersonaInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPersonaService.removePersona(0L, this.personaEntities.get(0).getId());
		});
	}

}