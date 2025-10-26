package co.edu.uniandes.dse.premiospeliculas.services;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.GanadorEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.GanadorRepository;

@Service
public class GanadorService {
    @Autowired
    private GanadorRepository ganadorRepository;

    public List<GanadorEntity> GetGanadores(){
        return ganadorRepository.findAll();
    }


    public GanadorEntity CreateGanador(GanadorEntity ganador) throws EntityNotFoundException,IllegalOperationException{
        ganador = ganadorRepository.save(ganador);
        return ganador;
    }

    public GanadorEntity getGanador(Long id) throws EntityNotFoundException{
        Optional<GanadorEntity> ganadorentity = ganadorRepository.findById(id);
        if(ganadorentity.isEmpty()){
            throw new EntityNotFoundException("Ganador no encontrado");
        }
        return ganadorentity.get();
        }

    public GanadorEntity UpdateGanador(Long id,GanadorEntity ganador) throws IllegalOperationException,EntityNotFoundException{
        Optional<GanadorEntity> ganadorentity = ganadorRepository.findById(id);
        if(ganadorentity.isEmpty()){
            throw new EntityNotFoundException("Ganador no encontrado");
        }
        ganador.setId(id);
        return ganadorRepository.save(ganador);
    }

    public void DeleteGanador(Long id) throws EntityNotFoundException{
        Optional<GanadorEntity> ganadorentity = ganadorRepository.findById(id);
        if(ganadorentity.isEmpty()){
            throw new EntityNotFoundException("Ganador no encontrado");
        }
        ganadorRepository.deleteById(id);
    }
}
