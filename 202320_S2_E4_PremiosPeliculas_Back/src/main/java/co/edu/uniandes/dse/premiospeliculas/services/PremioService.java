package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremioRepository;


@Service
public class PremioService {

    @Autowired
    PremioRepository premiorepository;

    public List<PremioEntity> getPremios(){
        return premiorepository.findAll();
    }

    public PremioEntity getPremio(Long id) throws EntityNotFoundException {
        Optional<PremioEntity> premioentity = premiorepository.findById(id);
        if(premioentity.isEmpty()){
            throw new EntityNotFoundException("Premio no encontrado");
        }
        return premioentity.get();

    }

    public PremioEntity UpdatePremio(Long id, PremioEntity premio) throws EntityNotFoundException,IllegalOperationException{
        Optional<PremioEntity> premioentity = premiorepository.findById(id);
       if(premioentity.isEmpty()){
        throw new EntityNotFoundException("Premio no encontrado");
       }
       if(premio == null){
        throw new IllegalOperationException("El premio esta vacio");
       }
       premio.setId(id);
        return premiorepository.save(premio); 
    }




    public PremioEntity createPremio(PremioEntity premio) throws EntityNotFoundException, IllegalOperationException{
        if(premio.getFecha() == null){
            throw new IllegalOperationException("Fecha no valida");
        }
        if(premio.getPais()==null){
            throw new IllegalOperationException("Pais no valido");
        }
        premio = premiorepository.save(premio);
        return premio;
    }

    public void deletePremio(long id) throws EntityNotFoundException{
        Optional<PremioEntity> premioentity = premiorepository.findById(id);
        if(premioentity.isEmpty()){
            throw new EntityNotFoundException("Premio no encontrado");
        }
        premiorepository.deleteById(id);
    }

    
}
