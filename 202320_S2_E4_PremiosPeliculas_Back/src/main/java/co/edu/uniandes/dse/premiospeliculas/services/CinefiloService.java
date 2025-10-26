package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.CinefiloEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.CinefiloRepository;

@Service
public class CinefiloService {
    @Autowired
    private CinefiloRepository cinefiloRepository;

    public List<CinefiloEntity> getCinefilos(){
        return this.cinefiloRepository.findAll();
        
    }

    public CinefiloEntity getCinefilo(Long id) throws EntityNotFoundException{
        Optional<CinefiloEntity> cinefiloEntity =cinefiloRepository.findById(id);
        if (cinefiloEntity.isEmpty()){
            throw new EntityNotFoundException("Cinefilo no encontado");
        }
        return cinefiloEntity.get();
        
    }

    public CinefiloEntity createCinefilo(CinefiloEntity cinefilo) throws IllegalOperationException{
        if(cinefilo.getNombre() == null ){
            throw new IllegalOperationException("El nombre de usuario no puede ser nulo");
        }
        else if (cinefilo.getNombre().length() < 5){
            throw new IllegalOperationException("El nombre de usuario no puede ser menor a 5 caracteres");
        }

        cinefilo = this.cinefiloRepository.save(cinefilo);
        return cinefilo;
        
    }

    public CinefiloEntity updateCinefilo(Long id, CinefiloEntity cinefilo) throws EntityNotFoundException, IllegalOperationException{
        Optional<CinefiloEntity> cinefiloEntity = this.cinefiloRepository.findById(id);
        if (cinefiloEntity.isEmpty()){
            throw new EntityNotFoundException("Cinefilo no encontrado");
        }
        if (cinefilo.getNombre() == null){
            throw new IllegalOperationException("No se puede tener un cinefilo con nombre nulo");
        }
        if (cinefilo.getNombre().length() < 5){
            throw new IllegalOperationException("No se puede tener un cinefilo con nombre menor a 5");
        }
        cinefilo.setId(id);

        return this.cinefiloRepository.save(cinefilo);
    }

    public void deleteCinefilo (Long id) throws EntityNotFoundException{
        Optional<CinefiloEntity> cinefiloEntity = cinefiloRepository.findById(id);
        if (cinefiloEntity.isEmpty()){
            throw new EntityNotFoundException ("Cinefilo no encontrado");
         }

        this.cinefiloRepository.deleteById(id);
        
    }

    




}
