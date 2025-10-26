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
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PeliculaPremiacionService.class)
class PeliculaPremiacionServiceTest {
	
	@Autowired
	private PeliculaPremiacionService peliculaPremiacionService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private PeliculaEntity pelicula = new PeliculaEntity();
	private List<PremiacionEntity> premiacionEntities = new ArrayList<>();

	
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
			PremiacionEntity premiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
			premiacionEntity.setPeliculas(new ArrayList<PeliculaEntity>());
			premiacionEntity.getPeliculas().add(this.pelicula);
			entityManager.persist(premiacionEntity);
			this.premiacionEntities.add(premiacionEntity);
			this.pelicula.getPremiaciones().add(premiacionEntity);	
		}
	}

	
	@Test
	void testAddPremiacion() throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
		entityManager.persist(newPelicula);
		
		PremiacionEntity premiacion = factory.manufacturePojo(PremiacionEntity.class);
		premiacion.setPeliculas(new ArrayList<PeliculaEntity>());
		premiacion.getPeliculas().add(newPelicula);
		entityManager.persist(premiacion);
		
		this.peliculaPremiacionService.addPremiacion(newPelicula.getId(), premiacion.getId());
		
		
		PremiacionEntity lastPremiacion = this.peliculaPremiacionService.getPremiacion(newPelicula.getId(), premiacion.getId());
		assertEquals(premiacion.getId(), lastPremiacion.getId());
		assertEquals(premiacion.getCategoria(), lastPremiacion.getCategoria());		
		assertEquals(premiacion.getHistoria(), lastPremiacion.getHistoria());
		
		
	}
	
	
	@Test
	void testAddInvalidPremiacion() {
		assertThrows(EntityNotFoundException.class, ()->{
			PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(newPelicula);
			this.peliculaPremiacionService.addPremiacion(newPelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testAddPremiacionInvaliPelicula() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			PremiacionEntity premiacion = factory.manufacturePojo(PremiacionEntity.class);
			entityManager.persist(premiacion);
			this.peliculaPremiacionService.addPremiacion(0L, premiacion.getId());
		});
	}


	@Test
	void testGetPremiaciones() throws EntityNotFoundException {
		List<PremiacionEntity> premiacionEntities = this.peliculaPremiacionService.getPremiaciones(this.pelicula.getId());

		assertEquals(this.premiacionEntities.size(), premiacionEntities.size());

		for (int i = 0; i < this.premiacionEntities.size(); i++) {
			assertTrue(premiacionEntities.contains(this.premiacionEntities.get(0)));
		}
	}
	
	
	@Test
	void testGetPremiacionesInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPremiacionService.getPremiaciones(0L);
		});
	}


	@Test
	void testGetPremiacion() throws EntityNotFoundException, IllegalOperationException {
		PremiacionEntity premiacionEntity = this.premiacionEntities.get(0);
		PremiacionEntity premiacion = this.peliculaPremiacionService.getPremiacion(this.pelicula.getId(), premiacionEntity.getId());
		assertNotNull(premiacion);

		assertEquals(premiacionEntity.getId(), premiacion.getId());
		assertEquals(premiacionEntity.getHistoria(), premiacion.getHistoria());
		assertEquals(premiacionEntity.getCategoria(), premiacion.getCategoria());
	}
	

	@Test
	void testGetInvalidPremiacion()  {
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPremiacionService.getPremiacion(this.pelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testGetPremiacionInvalidPelicula() {
		assertThrows(EntityNotFoundException.class, ()->{
			PremiacionEntity premiacionEntity = this.premiacionEntities.get(0);
			this.peliculaPremiacionService.getPremiacion(0L, premiacionEntity.getId());
		});
	}
	
	
	@Test
	void testGetNotAssociatedPremiacion() {
		assertThrows(IllegalOperationException.class, ()->{
			PeliculaEntity newPelicula = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(newPelicula);
			PremiacionEntity premiacion = factory.manufacturePojo(PremiacionEntity.class);
			premiacion.setPeliculas(new ArrayList<PeliculaEntity>());
			entityManager.persist(premiacion);
			this.peliculaPremiacionService.getPremiacion(newPelicula.getId(), premiacion.getId());
		});
	}


	@Test
	void testReplacePremiaciones() throws EntityNotFoundException {
		List<PremiacionEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PremiacionEntity entity = factory.manufacturePojo(PremiacionEntity.class);
			entityManager.persist(entity);
			this.pelicula.getPremiaciones().add(entity);
			nuevaLista.add(entity);
		}
		this.peliculaPremiacionService.replacePremiaciones(this.pelicula.getId(), nuevaLista);
		
		List<PremiacionEntity> premiacionEntities = this.peliculaPremiacionService.getPremiaciones(this.pelicula.getId());
		for (PremiacionEntity aNuevaLista : nuevaLista) {
			assertTrue(premiacionEntities.contains(aNuevaLista));
		}
	}

	@Test
	void testReplacePremiaciones2() throws EntityNotFoundException {
		List<PremiacionEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PremiacionEntity entity = factory.manufacturePojo(PremiacionEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		this.peliculaPremiacionService.replacePremiaciones(this.pelicula.getId(), nuevaLista);
		
		List<PremiacionEntity> premiacionEntities = this.peliculaPremiacionService.getPremiaciones(this.pelicula.getId());
		for (PremiacionEntity aNuevaLista : nuevaLista) {
			assertTrue(premiacionEntities.contains(aNuevaLista));
		}
	}
	
	
	
	@Test
	void testReplacePremiacionesInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<PremiacionEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PremiacionEntity entity = factory.manufacturePojo(PremiacionEntity.class);
				entity.setPeliculas(new ArrayList<PeliculaEntity>());
				entity.getPeliculas().add(this.pelicula);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			this.peliculaPremiacionService.replacePremiaciones(0L, nuevaLista);
		});
	}
	

	@Test
	void testReplaceInvalidPremiaciones() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<PremiacionEntity> nuevaLista = new ArrayList<>();
			PremiacionEntity entity = factory.manufacturePojo(PremiacionEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			this.peliculaPremiacionService.replacePremiaciones(this.pelicula.getId(), nuevaLista);
		});
	}
	
	
	@Test
	void testReplacePreInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<PremiacionEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PremiacionEntity entity = factory.manufacturePojo(PremiacionEntity.class);
				entity.getPeliculas().add(this.pelicula);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			this.peliculaPremiacionService.replacePremiaciones(0L, nuevaLista);
		});
	}

	
	@Test
	void testRemovePremiacion() throws EntityNotFoundException {
		for (PremiacionEntity premiacion : this.premiacionEntities) {
			this.peliculaPremiacionService.removePremiacion(this.pelicula.getId(), premiacion.getId());
		}
		assertTrue(this.peliculaPremiacionService.getPremiaciones(this.pelicula.getId()).isEmpty());
	}
	
	@Test
	void testRemoveInvalidPremiacion(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPremiacionService.removePremiacion(this.pelicula.getId(), 0L);
		});
	}
	
	
	@Test
	void testRemovePremiacionInvalidPelicula(){
		assertThrows(EntityNotFoundException.class, ()->{
			this.peliculaPremiacionService.removePremiacion(0L, this.premiacionEntities.get(0).getId());
		});
	}

}

