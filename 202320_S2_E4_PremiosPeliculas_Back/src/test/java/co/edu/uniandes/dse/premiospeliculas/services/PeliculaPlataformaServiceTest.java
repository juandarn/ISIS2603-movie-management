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
import co.edu.uniandes.dse.premiospeliculas.entities.PlataformaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PeliculaPlataformaService.class)
class PeliculaPlataformaServiceTest {
	
	@Autowired
	private PeliculaPlataformaService peliculaPlataformaService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private PeliculaEntity pelicula = new PeliculaEntity();
	private List<PlataformaEntity> plataformaEntities = new ArrayList<>();

	
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
			PlataformaEntity plataformaEntity = factory.manufacturePojo(PlataformaEntity.class);
			plataformaEntity.setPeliculas(new ArrayList<PeliculaEntity>());
			plataformaEntity.getPeliculas().add(this.pelicula);
			entityManager.persist(plataformaEntity);
			this.plataformaEntities.add(plataformaEntity);
			this.pelicula.getPlataformas().add(plataformaEntity);	
		}
	}

	
	@Test
	void testAddPlataforma() throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
		entityManager.persist(newPelicula);
		
		PlataformaEntity plataforma = factory.manufacturePojo(PlataformaEntity.class);
		plataforma.setPeliculas(new ArrayList<PeliculaEntity>());
		plataforma.getPeliculas().add(newPelicula);
		entityManager.persist(plataforma);
		
		this.peliculaPlataformaService.addPlataforma(newPelicula.getId(), plataforma.getId());
		
		
		PlataformaEntity lastPlataforma = this.peliculaPlataformaService.getPlataforma(newPelicula.getId(), plataforma.getId());
		assertEquals(plataforma.getId(), lastPlataforma.getId());
		assertEquals(plataforma.getNombre(), lastPlataforma.getNombre());		
		
		
	}
	
	
	@Test
	void testAddInvalidPlataforma() {
		assertThrows(EntityNotFoundException.class, ()->{
			PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(newPelicula);
			this.peliculaPlataformaService.addPlataforma(newPelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testAddPlataformaInvaliPelicula() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			PlataformaEntity plataforma = factory.manufacturePojo(PlataformaEntity.class);
			entityManager.persist(plataforma);
			this.peliculaPlataformaService.addPlataforma(0L, plataforma.getId());
		});
	}


	@Test
	void testGetPlataformaes() throws EntityNotFoundException {
		List<PlataformaEntity> plataformaEntities = this.peliculaPlataformaService.getPlataformas(this.pelicula.getId());

		assertEquals(this.plataformaEntities.size(), plataformaEntities.size());

		for (int i = 0; i < this.plataformaEntities.size(); i++) {
			assertTrue(plataformaEntities.contains(this.plataformaEntities.get(0)));
		}
	}
	
	
	@Test
	void testGetPlataformaesInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPlataformaService.getPlataformas(0L);
		});
	}


	@Test
	void testGetPlataforma() throws EntityNotFoundException, IllegalOperationException {
		PlataformaEntity plataformaEntity = this.plataformaEntities.get(0);
		PlataformaEntity plataforma = this.peliculaPlataformaService.getPlataforma(this.pelicula.getId(), plataformaEntity.getId());
		assertNotNull(plataforma);

		assertEquals(plataformaEntity.getId(), plataforma.getId());
		assertEquals(plataformaEntity.getNombre(), plataforma.getNombre());
	}
	

	@Test
	void testGetInvalidPlataforma()  {
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPlataformaService.getPlataforma(this.pelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testGetPlataformaInvalidPelicula() {
		assertThrows(EntityNotFoundException.class, ()->{
			PlataformaEntity plataformaEntity = this.plataformaEntities.get(0);
			this.peliculaPlataformaService.getPlataforma(0L, plataformaEntity.getId());
		});
	}
	
	
	@Test
	void testGetNotAssociatedPlataforma() {
		assertThrows(IllegalOperationException.class, ()->{
			PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(newPelicula);
			PlataformaEntity plataforma = factory.manufacturePojo(PlataformaEntity.class);
			plataforma.setPeliculas(new ArrayList<PeliculaEntity>());
			entityManager.persist(plataforma);
			this.peliculaPlataformaService.getPlataforma(newPelicula.getId(), plataforma.getId());
		});
	}


	@Test
	void testReplacePlataformaes() throws EntityNotFoundException {
		List<PlataformaEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PlataformaEntity entity = factory.manufacturePojo(PlataformaEntity.class);
			entityManager.persist(entity);
			this.pelicula.getPlataformas().add(entity);
			nuevaLista.add(entity);
		}
		this.peliculaPlataformaService.replacePlataformas(this.pelicula.getId(), nuevaLista);
		
		List<PlataformaEntity> plataformaEntities = this.peliculaPlataformaService.getPlataformas(this.pelicula.getId());
		for (PlataformaEntity aNuevaLista : nuevaLista) {
			assertTrue(plataformaEntities.contains(aNuevaLista));
		}
	}

	@Test
	void testReplacePlataformaes2() throws EntityNotFoundException {
		List<PlataformaEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PlataformaEntity entity = factory.manufacturePojo(PlataformaEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		this.peliculaPlataformaService.replacePlataformas(this.pelicula.getId(), nuevaLista);
		
		List<PlataformaEntity> plataformaEntities = this.peliculaPlataformaService.getPlataformas(this.pelicula.getId());
		for (PlataformaEntity aNuevaLista : nuevaLista) {
			assertTrue(plataformaEntities.contains(aNuevaLista));
		}
	}
	
	
	
	@Test
	void testReplacePlataformaesInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<PlataformaEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PlataformaEntity entity = factory.manufacturePojo(PlataformaEntity.class);
				entity.setPeliculas(new ArrayList<PeliculaEntity>());
				entity.getPeliculas().add(this.pelicula);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			this.peliculaPlataformaService.replacePlataformas(0L, nuevaLista);
		});
	}
	

	@Test
	void testReplaceInvalidPlataformaes() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<PlataformaEntity> nuevaLista = new ArrayList<>();
			PlataformaEntity entity = factory.manufacturePojo(PlataformaEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			this.peliculaPlataformaService.replacePlataformas(this.pelicula.getId(), nuevaLista);
		});
	}
	
	
	@Test
	void testReplacePreInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<PlataformaEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PlataformaEntity entity = factory.manufacturePojo(PlataformaEntity.class);
				entity.getPeliculas().add(this.pelicula);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			this.peliculaPlataformaService.replacePlataformas(0L, nuevaLista);
		});
	}

	
	@Test
	void testRemovePlataforma() throws EntityNotFoundException {
		for (PlataformaEntity plataforma : this.plataformaEntities) {
			this.peliculaPlataformaService.removePlataforma(this.pelicula.getId(), plataforma.getId());
		}
		assertTrue(this.peliculaPlataformaService.getPlataformas(this.pelicula.getId()).isEmpty());
	}
	
	@Test
	void testRemoveInvalidPlataforma(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPlataformaService.removePlataforma(this.pelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testRemovePlataformaInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPlataformaService.removePlataforma(0L, this.plataformaEntities.get(0).getId());
		});
	}

}
