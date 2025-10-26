package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PlataformaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PlataformaRepository;

@Service
public class PlataformaService {

    @Autowired
    private PlataformaRepository plataformaRepository;

    public List<PlataformaEntity> getPlataformas() {
        return this.plataformaRepository.findAll();
    }

    public PlataformaEntity CreatePlataforma(PlataformaEntity plataforma) throws IllegalOperationException {
        if (plataforma.getNombre() == null) {
            throw new IllegalOperationException("Valor no válido");
        }

        plataforma = this.plataformaRepository.save(plataforma);
        return plataforma;
    }

    public PlataformaEntity GetPlataforma(Long id) throws EntityNotFoundException {
        Optional<PlataformaEntity> PlataformaEntity = this.plataformaRepository.findById(id);
        if (PlataformaEntity.isEmpty()) {
            throw new EntityNotFoundException("Plataforma no encontrada");
        }

        return PlataformaEntity.get();
    }

    public void DeletePlataforma(Long id) throws EntityNotFoundException {
        Optional<PlataformaEntity> plataformaEntity = this.plataformaRepository.findById(id);
        if (plataformaEntity.isEmpty()) {
            throw new EntityNotFoundException("Plataforma no encontrada");
        }
        this.plataformaRepository.deleteById(id);
    }

    public PlataformaEntity UpdatePlataforma(Long id, PlataformaEntity plataforma) throws EntityNotFoundException, IllegalOperationException{
        Optional<PlataformaEntity> plataformaEntity= this.plataformaRepository.findById(id);
        if (plataformaEntity.isEmpty()){
            throw new EntityNotFoundException("Plataforma no encontrada");
        }
        else if (plataforma.getNombre()==null){
            throw new IllegalOperationException("Valor no válido");
        }
        plataforma.setId(id);
        return this.plataformaRepository.save(plataforma);
    }
}
