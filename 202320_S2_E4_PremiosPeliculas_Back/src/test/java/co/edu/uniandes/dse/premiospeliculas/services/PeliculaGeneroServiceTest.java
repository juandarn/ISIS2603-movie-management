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
import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PeliculaGeneroService.class)
class PeliculaGeneroServiceTest {
	
	@Autowired
	private PeliculaGeneroService peliculaGeneroService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private PeliculaEntity pelicula = new PeliculaEntity();
	private List<GeneroEntity> generoEntities = new ArrayList<>();

	
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
			GeneroEntity generoEntity = factory.manufacturePojo(GeneroEntity.class);
			generoEntity.setPeliculas(new ArrayList<PeliculaEntity>());
			generoEntity.getPeliculas().add(this.pelicula);
			entityManager.persist(generoEntity);
			this.generoEntities.add(generoEntity);
			this.pelicula.getGeneros().add(generoEntity);	
		}
	}

	
	@Test
	void testAddGenero() throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
		entityManager.persist(newPelicula);
		
		GeneroEntity genero = factory.manufacturePojo(GeneroEntity.class);
		genero.setPeliculas(new ArrayList<PeliculaEntity>());
		genero.getPeliculas().add(newPelicula);
		entityManager.persist(genero);
		
		this.peliculaGeneroService.addGenero(newPelicula.getId(), genero.getId());
		
		
		GeneroEntity lastGenero = this.peliculaGeneroService.getGenero(newPelicula.getId(), genero.getId());
		assertEquals(genero.getId(), lastGenero.getId());
		assertEquals(genero.getNombre(), lastGenero.getNombre());		
		
		
	}
	
	
	@Test
	void testAddInvalidGenero() {
		assertThrows(EntityNotFoundException.class, ()->{
			PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(newPelicula);
			this.peliculaGeneroService.addGenero(newPelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testAddGeneroInvaliPelicula() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			GeneroEntity genero = factory.manufacturePojo(GeneroEntity.class);
			entityManager.persist(genero);
			this.peliculaGeneroService.addGenero(0L, genero.getId());
		});
	}


	@Test
	void testGetGeneroes() throws EntityNotFoundException {
		List<GeneroEntity> generoEntities = this.peliculaGeneroService.getGeneros(this.pelicula.getId());

		assertEquals(this.generoEntities.size(), generoEntities.size());

		for (int i = 0; i < this.generoEntities.size(); i++) {
			assertTrue(generoEntities.contains(this.generoEntities.get(0)));
		}
	}
	
	
	@Test
	void testGetGeneroesInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaGeneroService.getGeneros(0L);
		});
	}


	@Test
	void testGetGenero() throws EntityNotFoundException, IllegalOperationException {
		GeneroEntity generoEntity = this.generoEntities.get(0);
		GeneroEntity genero = this.peliculaGeneroService.getGenero(this.pelicula.getId(), generoEntity.getId());
		assertNotNull(genero);

		assertEquals(generoEntity.getId(), genero.getId());
		assertEquals(generoEntity.getNombre(), genero.getNombre());
	}
	

	@Test
	void testGetInvalidGenero()  {
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaGeneroService.getGenero(this.pelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testGetGeneroInvalidPelicula() {
		assertThrows(EntityNotFoundException.class, ()->{
			GeneroEntity generoEntity = this.generoEntities.get(0);
			this.peliculaGeneroService.getGenero(0L, generoEntity.getId());
		});
	}
	
	
	@Test
	void testGetNotAssociatedGenero() {
		assertThrows(IllegalOperationException.class, ()->{
			PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(newPelicula);
			GeneroEntity genero = factory.manufacturePojo(GeneroEntity.class);
			genero.setPeliculas(new ArrayList<PeliculaEntity>());
			entityManager.persist(genero);
			this.peliculaGeneroService.getGenero(newPelicula.getId(), genero.getId());
		});
	}


	@Test
	void testReplaceGeneroes() throws EntityNotFoundException {
		List<GeneroEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			GeneroEntity entity = factory.manufacturePojo(GeneroEntity.class);
			entityManager.persist(entity);
			this.pelicula.getGeneros().add(entity);
			nuevaLista.add(entity);
		}
		this.peliculaGeneroService.replaceGeneros(this.pelicula.getId(), nuevaLista);
		
		List<GeneroEntity> generoEntities = this.peliculaGeneroService.getGeneros(this.pelicula.getId());
		for (GeneroEntity aNuevaLista : nuevaLista) {
			assertTrue(generoEntities.contains(aNuevaLista));
		}
	}

	@Test
	void testReplaceGeneroes2() throws EntityNotFoundException {
		List<GeneroEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			GeneroEntity entity = factory.manufacturePojo(GeneroEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		this.peliculaGeneroService.replaceGeneros(this.pelicula.getId(), nuevaLista);
		
		List<GeneroEntity> generoEntities = this.peliculaGeneroService.getGeneros(this.pelicula.getId());
		for (GeneroEntity aNuevaLista : nuevaLista) {
			assertTrue(generoEntities.contains(aNuevaLista));
		}
	}
	
	
	
	@Test
	void testReplaceGeneroesInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<GeneroEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				GeneroEntity entity = factory.manufacturePojo(GeneroEntity.class);
				entity.setPeliculas(new ArrayList<PeliculaEntity>());
				entity.getPeliculas().add(this.pelicula);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			this.peliculaGeneroService.replaceGeneros(0L, nuevaLista);
		});
	}
	

	@Test
	void testReplaceInvalidGeneroes() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<GeneroEntity> nuevaLista = new ArrayList<>();
			GeneroEntity entity = factory.manufacturePojo(GeneroEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			this.peliculaGeneroService.replaceGeneros(this.pelicula.getId(), nuevaLista);
		});
	}
	
	
	@Test
	void testReplacePreInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<GeneroEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				GeneroEntity entity = factory.manufacturePojo(GeneroEntity.class);
				entity.getPeliculas().add(this.pelicula);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			this.peliculaGeneroService.replaceGeneros(0L, nuevaLista);
		});
	}

	
	@Test
	void testRemoveGenero() throws EntityNotFoundException {
		for (GeneroEntity genero : this.generoEntities) {
			this.peliculaGeneroService.removeGenero(this.pelicula.getId(), genero.getId());
		}
		assertTrue(this.peliculaGeneroService.getGeneros(this.pelicula.getId()).isEmpty());
	}
	
	@Test
	void testRemoveInvalidGenero(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaGeneroService.removeGenero(this.pelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testRemoveGeneroInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaGeneroService.removeGenero(0L, this.generoEntities.get(0).getId());
		});
	}

}
