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
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Editorial - Books
 *
 * @author ISIS2603
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ PeliculaService.class, PeliculaReseniaService.class })
class PeliculaReseniaServiceTest {

	@Autowired
	private PeliculaReseniaService peliculaReseniaService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PeliculaEntity> peliculasList = new ArrayList<>();
	private List<ReseniaEntity> reseniasList = new ArrayList<>();

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ReseniaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PeliculaEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			ReseniaEntity resenia = factory.manufacturePojo(ReseniaEntity.class);
            resenia.setPelicula(new PeliculaEntity());
			entityManager.persist(resenia);
			this.reseniasList.add(resenia);
		}

		for (int i = 0; i < 3; i++) {
			PeliculaEntity entity = factory.manufacturePojo(PeliculaEntity.class);
			entityManager.persist(entity);
			this.peliculasList.add(entity);
			if (i == 0) {
				this.reseniasList.get(i).setPelicula(entity);
				entity.getResenias().add(this.reseniasList.get(i));
			}
		}
	}


	
	@Test
	void testAddBook() throws EntityNotFoundException {
		PeliculaEntity entity = this.peliculasList.get(0);
		ReseniaEntity reseniaEntity = this.reseniasList.get(1);
		ReseniaEntity response = this.peliculaReseniaService.addResenia(entity.getId(), reseniaEntity.getId());

		assertNotNull(response);
		assertEquals(reseniaEntity.getId(), response.getId());
	}
	
	/**
	 * Prueba para asociar un Book que no existe a un Editorial.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddInvalidBook() {
		assertThrows(EntityNotFoundException.class, ()->{
			PeliculaEntity entity = this.peliculasList.get(0);
			this.peliculaReseniaService.addResenia(entity.getId(),0L );
		});
	}
	
	/**
	 * Prueba para asociar un Book a una Editorial que no existe.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddBookInvalidEditorial() {
		assertThrows(EntityNotFoundException.class, ()->{
			ReseniaEntity reseniaEntity = this.reseniasList.get(1);
			this.peliculaReseniaService.addResenia(0L,reseniaEntity.getId() );
		});
	}

	/**
	 * Prueba para obtener una colección de instancias de Books asociadas a una
	 * instancia Editorial.
	 * 
	 * @throws EntityNotFoundException
	 */

	@Test
	void testGetBooks() throws EntityNotFoundException {
		List<ReseniaEntity> list = this.peliculaReseniaService.getResenias(this.peliculasList.get(0).getId());
		assertEquals(1, list.size());
	}
	
	/**
	 * Prueba para obtener una colección de instancias de Books asociadas a una
	 * instancia Editorial que no existe.
	 * 
	 * @throws EntityNotFoundException
	 */

	@Test
	void testGetBooksInvalidEditorial() {
		assertThrows(EntityNotFoundException.class,()->{
			this.peliculaReseniaService.getResenias(0L);
		});
	}

	/**
	 * Prueba para obtener una instancia de Book asociada a una instancia Editorial.
	 * 
	 * @throws IllegalOperationException
	 * @throws EntityNotFoundException
	 *
	 * @throws co.edu.uniandes.csw.reseniastore.exceptions.BusinessLogicException
	 */
	@Test
	void testGetBook() throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity entity = this.peliculasList.get(0);
		ReseniaEntity reseniaEntity = this.reseniasList.get(0);
		ReseniaEntity response = this.peliculaReseniaService.getResenia(entity.getId(), reseniaEntity.getId());

		assertEquals(reseniaEntity.getId(), response.getId());
		assertEquals(reseniaEntity.getTexto(), response.getTexto());
		assertEquals(reseniaEntity.getClasificacion(), response.getClasificacion());
	}
	
	/**
	 * Prueba para obtener una instancia de Book asociada a una instancia Editorial que no existe.
	 * 
	 * @throws EntityNotFoundException
	 *
	 */
	@Test
	void testGetBookInvalidEditorial()  {
		assertThrows(EntityNotFoundException.class, ()->{
			ReseniaEntity reseniaEntity = this.reseniasList.get(0);
			this.peliculaReseniaService.getResenia(0L, reseniaEntity.getId());
		});
	}
	
	/**
	 * Prueba para obtener una instancia de Book que no existe asociada a una instancia Editorial.
	 * 
	 * @throws EntityNotFoundException
	 * 
	 */
	@Test
	void testGetInvalidBook()  {
		assertThrows(EntityNotFoundException.class, ()->{
			PeliculaEntity entity = this.peliculasList.get(0);
			this.peliculaReseniaService.getResenia(entity.getId(), 0L);
		});
	}

	/**
	 * Prueba para obtener una instancia de Books asociada a una instancia Editorial
	 * que no le pertenece.
	 *
	 * @throws co.edu.uniandes.csw.reseniastore.exceptions.BusinessLogicException
	 */
	@Test
	public void getReseniaNoAsociadoTest() {
		assertThrows(IllegalOperationException.class, () -> {
			PeliculaEntity entity = this.peliculasList.get(0);
			ReseniaEntity reseniaEntity = this.reseniasList.get(1);
			this.peliculaReseniaService.getResenia(entity.getId(), reseniaEntity.getId());
		});
	}

	/**
	 * Prueba para remplazar las instancias de Books asociadas a una instancia de
	 * Editorial.
	 */
	@Test
	void testReplaceBooks() throws EntityNotFoundException {
		PeliculaEntity entity = this.peliculasList.get(0);
		List<ReseniaEntity> list = this.reseniasList.subList(1, 3);
		this.peliculaReseniaService.replaceResenias(entity.getId(), list);

		for (ReseniaEntity resenia : list) {
			ReseniaEntity b = entityManager.find(ReseniaEntity.class, resenia.getId());
			assertTrue(b.getPelicula().equals(entity));
		}
	}
	
	/**
	 * Prueba para remplazar las instancias de Books que no existen asociadas a una instancia de
	 * Editorial.
	 */
	@Test
	void testReplaceInvalidBooks() {
		assertThrows(EntityNotFoundException.class, ()->{
			PeliculaEntity entity = this.peliculasList.get(0);
			
			List<ReseniaEntity> resenias = new ArrayList<>();
			ReseniaEntity newBook = factory.manufacturePojo(ReseniaEntity.class);
			newBook.setId(0L);
			resenias.add(newBook);
			
			this.peliculaReseniaService.replaceResenias(entity.getId(), resenias);
		});
	}
	
	/**
	 * Prueba para remplazar las instancias de Books asociadas a una instancia de
	 * Editorial que no existe.
	 */
	@Test
	void testReplaceBooksInvalidEditorial() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			List<ReseniaEntity> list = this.reseniasList.subList(1, 3);
			this.peliculaReseniaService.replaceResenias(0L, list);
		});
	}
}