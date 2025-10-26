package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import co.edu.uniandes.dse.premiospeliculas.entities.CinefiloEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ReseniaService.class)

public class ReseniaServiceTest {
    @Autowired
    private ReseniaService reseniaService;
    private List<ReseniaEntity> ListaResenia = new ArrayList<>();
    private List<CinefiloEntity> ListaCinefilo = new ArrayList<>();
    private List<PeliculaEntity> ListaPelicula = new ArrayList<>();
    private List<PremioEntity> ListaPremiacion = new ArrayList<>();
    

    private PodamFactory factory = new PodamFactoryImpl();
    ReseniaEntity newReseniaEntity = factory.manufacturePojo(ReseniaEntity.class);

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp(){
        insertData();
        clearData();
    }


    private void insertData(){
        for(int i=0; i<3;i++){
            ReseniaEntity reseniaEntity = factory.manufacturePojo(ReseniaEntity.class);
            entityManager.persist(reseniaEntity);
            ListaResenia.add(reseniaEntity);
        }
        for(int i=0; i<3;i++){
            CinefiloEntity cinefiloEntity = factory.manufacturePojo(CinefiloEntity.class);
            entityManager.persist(cinefiloEntity);
            ListaCinefilo.add(cinefiloEntity);
        }
        for(int i=0; i<3;i++){
            PeliculaEntity peliculaEntity = factory.manufacturePojo(PeliculaEntity.class);
            entityManager.persist(peliculaEntity);
            ListaPelicula.add(peliculaEntity);
        }
        for(int i=0; i<3;i++){
            PremioEntity premiacionEntity = factory.manufacturePojo(PremioEntity.class);
            entityManager.persist(premiacionEntity);
            ListaPremiacion.add(premiacionEntity);
        }

    }


    @Test
    void testCreateResenia() throws EntityNotFoundException, IllegalOperationException{
        ReseniaEntity newReseniaEntity = factory.manufacturePojo(ReseniaEntity.class);
        newReseniaEntity.setCinefilo(ListaCinefilo.get(0));
        newReseniaEntity.setPelicula(ListaPelicula.get(0));
        newReseniaEntity.setPremio(ListaPremiacion.get(0));
        newReseniaEntity.setClasificacion(5);
        newReseniaEntity.setFechaEstreno(new Date(2005,12,30));
        newReseniaEntity.setTexto("Muy buena");
        ReseniaEntity resultado = reseniaService.CreateResenia(newReseniaEntity);
        ReseniaEntity entity = entityManager.find(ReseniaEntity.class,resultado.getId());
        assertEquals(newReseniaEntity.getId(),entity.getId());
        assertEquals(newReseniaEntity.getCinefilo(),entity.getCinefilo());
        assertEquals(newReseniaEntity.getClasificacion(),entity.getClasificacion());
        assertEquals(newReseniaEntity.getFechaEstreno(),entity.getFechaEstreno());
        assertEquals(newReseniaEntity.getPelicula(),entity.getPelicula());
        assertEquals(newReseniaEntity.getPremio(),entity.getPremio());
        assertEquals(newReseniaEntity.getTexto(),entity.getTexto());
    }

    @Test
    void testCreateReseniaLenghtInvalid(){
        assertThrows(IllegalOperationException.class, ()->{
            ReseniaEntity newReseniaEntity = factory.manufacturePojo(ReseniaEntity.class);
            newReseniaEntity.setTexto("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            reseniaService.CreateResenia(newReseniaEntity);
        });
    }

    @Test
    void testCreateReseniaCalificacionInvalid(){
        assertThrows(IllegalOperationException.class, ()->{
            ReseniaEntity newReseniaEntity = factory.manufacturePojo(ReseniaEntity.class);
            newReseniaEntity.setClasificacion(10);
            reseniaService.CreateResenia(newReseniaEntity);
        });
    }


    
    @Test
    void testDeletePremio() throws EntityNotFoundException{
        ReseniaEntity enitity = ListaResenia.get(1);
        reseniaService.DeleteResenia(enitity.getId());
        ReseniaEntity deleted = entityManager.find(ReseniaEntity.class,enitity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteReseniaInvalid(){
        assertThrows(EntityNotFoundException.class,()->{
            reseniaService.DeleteResenia(0L);
        });
    }

    @Test
    void testGetResenia() throws EntityNotFoundException{
        ReseniaEntity entity = ListaResenia.get(0);
        ReseniaEntity resulEntity = reseniaService.getResenia(entity.getId());
        assertNotNull(resulEntity);
        assertEquals(resulEntity.getId(),entity.getId());
        assertEquals(resulEntity.getCinefilo(),entity.getCinefilo());
        assertEquals(resulEntity.getClasificacion(),entity.getClasificacion());
        assertEquals(resulEntity.getFechaEstreno(),entity.getFechaEstreno());
        assertEquals(resulEntity.getPelicula(),entity.getPelicula());
        assertEquals(resulEntity.getPremio(),entity.getPremio());
        assertEquals(resulEntity.getTexto(),entity.getTexto());

    }


    @Test
    void testGetInvalidResenia(){
        assertThrows(EntityNotFoundException.class,()->{
            reseniaService.getResenia(0L);
        });
    }

    

    @Test
    void testUpdateResenia() throws EntityNotFoundException, IllegalOperationException{
        ReseniaEntity entity = ListaResenia.get(0);
        ReseniaEntity factoryentity = factory.manufacturePojo(ReseniaEntity.class);
        factoryentity.setId(entity.getId());
        reseniaService.UpdateResenia(entity.getId(), factoryentity);
        ReseniaEntity resultado = entityManager.find(ReseniaEntity.class, entity.getId());
        assertEquals(entity.getId(),resultado.getId());
        assertEquals(entity.getCinefilo(),resultado.getCinefilo());
        assertEquals(entity.getClasificacion(),resultado.getClasificacion());
        assertEquals(entity.getFechaEstreno(),resultado.getFechaEstreno());
        assertEquals(entity.getPelicula(),resultado.getPelicula());
        assertEquals(entity.getPremio(),resultado.getPremio());
        assertEquals(entity.getTexto(),resultado.getTexto());

    }
    
    @Test
    void testUpdateReseniaInvalid(){
        assertThrows(EntityNotFoundException.class, ()->{
            ReseniaEntity newEntity = factory.manufacturePojo(ReseniaEntity.class);
            newEntity.setId(0L);
            reseniaService.UpdateResenia(0L, newEntity);
        });
    }




    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ReseniaEntity");
    }
}
