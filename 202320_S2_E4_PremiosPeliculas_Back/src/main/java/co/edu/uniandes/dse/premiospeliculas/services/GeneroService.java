package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.GeneroRepository;

@Service
public class GeneroService {
    @Autowired
    private GeneroRepository generoRepository;

    public List<GeneroEntity> getGeneros() {
        return this.generoRepository.findAll();
    }

    public GeneroEntity CreateGenero(GeneroEntity genero) throws IllegalOperationException {
        if (genero.getNombre() == null) {
            throw new IllegalOperationException("Valor no válido");
        }
        genero = this.generoRepository.save(genero);
        return genero;
    }

    public GeneroEntity GetGenero(Long id) throws EntityNotFoundException {
        Optional<GeneroEntity> generoEntity = this.generoRepository.findById(id);
        if (generoEntity.isEmpty()) {
            throw new EntityNotFoundException("Género no encontrado");
        }

        return generoEntity.get();
    }

    public void DeleteGenero(Long id) throws EntityNotFoundException {
        Optional<GeneroEntity> generoEntity = this.generoRepository.findById(id);
        if (generoEntity.isEmpty()) {
            throw new EntityNotFoundException("Género no encontrado");
        }
        this.generoRepository.deleteById(id);
    }

    public GeneroEntity UpdateGenero(Long id, GeneroEntity genero)
            throws EntityNotFoundException, IllegalOperationException {
        Optional<GeneroEntity> generoEntity = this.generoRepository.findById(id);
        if (generoEntity.isEmpty()) {
            throw new EntityNotFoundException("Género no encontrado");
        } else if (genero.getNombre() == null) {
            throw new IllegalOperationException("Valor no válido");
        }
        genero.setId(id);
        return this.generoRepository.save(genero);
    }
}
