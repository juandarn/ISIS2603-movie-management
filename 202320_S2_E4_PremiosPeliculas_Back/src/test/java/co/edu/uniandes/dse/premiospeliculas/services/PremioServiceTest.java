package co.edu.uniandes.dse.premiospeliculas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
@Import(PremioService.class)

public class PremioServiceTest {
    @Autowired
    private PremioService premioservice;
    private List<PremioEntity> ListaPremios = new ArrayList<>();
    private List<PremiacionEntity> ListaPremiacion= new ArrayList<>();
    private List<OrganizacionEntity> ListaOrganizacion= new ArrayList<>();
    
    private PodamFactory factory = new PodamFactoryImpl();
    PremioEntity newPremioEntity = factory.manufacturePojo(PremioEntity.class);

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void insertData() {
        for (int i = 0; i<3;i ++ ){
            PremioEntity premioEntity = factory.manufacturePojo(PremioEntity.class);
            this.entityManager.persist(premioEntity);
            this.ListaPremios.add(premioEntity);
        }
        for (int i = 0; i<3;i ++ ){
            PremiacionEntity premiacionEntity = factory.manufacturePojo(PremiacionEntity.class);
            this.entityManager.persist(premiacionEntity);
            this.ListaPremiacion.add(premiacionEntity);
        }
        for (int i = 0; i<3;i ++ ){
            OrganizacionEntity organizacionEntity = factory.manufacturePojo(OrganizacionEntity.class);
            this.entityManager.persist(organizacionEntity);
            this.ListaOrganizacion.add(organizacionEntity);
        }
        
    }
    @Test
    public void testCreatePremio() throws EntityNotFoundException, IllegalOperationException{
        PremioEntity newPremioEntity = factory.manufacturePojo(PremioEntity.class);
        newPremioEntity.setPremiacion(ListaPremiacion.get(0));
        newPremioEntity.setOrganizacion(ListaOrganizacion.get(0));
        newPremioEntity.setPais("Colombia");
        newPremioEntity.setFecha(new Date(2005, 5, 4));
        PremioEntity resultado = this.premioservice.createPremio(newPremioEntity);
        assertNotNull(resultado);
        PremioEntity entity = entityManager.find(PremioEntity.class, resultado.getId());
        assertEquals(newPremioEntity.getId(),entity.getId());
        assertEquals(newPremioEntity.getFecha(),entity.getFecha());
        assertEquals(newPremioEntity.getPremiacion(),entity.getPremiacion());
        assertEquals(newPremioEntity.getOrganizacion(),entity.getOrganizacion());
        assertEquals(newPremioEntity.getPais(),entity.getPais());
    }

    @Test
    void testCreatePremioFechaInvalid(){
        assertThrows(IllegalOperationException.class, ()->{
            PremioEntity newPremioEntity = factory.manufacturePojo(PremioEntity.class);
            newPremioEntity.setFecha(null);
            premioservice.createPremio(newPremioEntity);
        });
    }

    @Test
    void testCreatePremioPaisInvalid(){
        assertThrows(IllegalOperationException.class, ()->{
            PremioEntity newPremioEntity = factory.manufacturePojo(PremioEntity.class);
            newPremioEntity.setPais(null);
            premioservice.createPremio(newPremioEntity);
        });
    }


    @Test
    public void testGetPremios(){
        List<PremioEntity> lista = premioservice.getPremios();
        assertEquals(ListaPremios.size(),lista.size());
        for(PremioEntity entity: lista){
            boolean found = false;
            for(PremioEntity StoredEntity : ListaPremios){
                if(entity.getId().equals(StoredEntity.getId())){
                    found = true;
                }
            }
            assertTrue(found);
        }

    }

    @Test
    public void testGetPremio() throws EntityNotFoundException{
        PremioEntity entity = ListaPremios.get(0);
        PremioEntity resulEntity = premioservice.getPremio(entity.getId());
        assertNotNull(resulEntity);
        assertEquals(entity.getId(),resulEntity.getId());
        assertEquals(entity.getFecha(),resulEntity.getFecha());
        assertEquals(entity.getOrganizacion(),resulEntity.getOrganizacion());
        assertEquals(entity.getPais(),resulEntity.getPais());
        assertEquals(entity.getPremiacion(),resulEntity.getPremiacion());        
    }

    @Test
    void testGetInvalidPremio(){
        assertThrows(EntityNotFoundException.class,()->{
            premioservice.getPremio(0L);
        });
    }

    @Test
    public void testUpdatePremio() throws EntityNotFoundException, IllegalOperationException{
        PremioEntity entity = ListaPremios.get(0);
        PremioEntity factoryentity = factory.manufacturePojo(PremioEntity.class);
        factoryentity.setId(entity.getId());
        premioservice.UpdatePremio(entity.getId(),factoryentity);
        PremioEntity resultado = entityManager.find(PremioEntity.class, entity.getId());
        assertEquals(entity.getId(),resultado.getId());
        assertEquals(entity.getFecha(),resultado.getFecha());
        assertEquals(entity.getOrganizacion(),resultado.getOrganizacion());
        assertEquals(entity.getPais(),resultado.getPais());
        assertEquals(entity.getPremiacion(),resultado.getPremiacion());  
    }

    @Test
    void testUpdatePremioInvalid(){
        assertThrows(EntityNotFoundException.class, ()->{
            PremioEntity newEntity = factory.manufacturePojo(PremioEntity.class);
            newEntity.setId(0L);
            premioservice.UpdatePremio(0L, newEntity);
        });
    }


    @Test
    public void testDeletePremio() throws EntityNotFoundException, IllegalOperationException{
        PremioEntity entity = ListaPremios.get(1);
        premioservice.deletePremio(entity.getId());
        PremioEntity deleted = entityManager.find(PremioEntity.class,entity.getId());
        assertNull(deleted);

    }

    @Test
    void testDeletePremioInvalid(){
        assertThrows(EntityNotFoundException.class,()->{
            premioservice.deletePremio(0L);
        });
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PremioEntity");
    }
}
