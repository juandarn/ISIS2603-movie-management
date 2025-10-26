package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.NominadoEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.NominadoRepository;

@Service
public class NominadoService {

    @Autowired
    NominadoRepository nominadorepository;

    public List<NominadoEntity> getNominados() {
        return nominadorepository.findAll();
    }

    public NominadoEntity getNominado(Long id) throws EntityNotFoundException {
        Optional<NominadoEntity> nominadoentity = nominadorepository.findById(id);
        if (nominadoentity.isEmpty()) {
            throw new EntityNotFoundException("Nominado no encontrado");
        }
        return nominadoentity.get();
    }

    public NominadoEntity createNominado(NominadoEntity nominado) throws IllegalOperationException {
        nominado = this.nominadorepository.save(nominado);
        return nominado;

    }

    public NominadoEntity UpdateNominado(long id, NominadoEntity nominado)
            throws IllegalOperationException, EntityNotFoundException {
        Optional<NominadoEntity> nominadoentity = nominadorepository.findById(id);
        if (nominadoentity.isEmpty()) {
            throw new EntityNotFoundException("Nominado no encontrado");
        }
        nominado.setId(id);
        return nominadorepository.save(nominado);

    }

    public void deleteNominado(long id) throws EntityNotFoundException {
        Optional<NominadoEntity> nominadoentity = nominadorepository.findById(id);
        if (nominadoentity.isEmpty()) {
            throw new EntityNotFoundException("Nominado no encontrado");
        }
        nominadorepository.deleteById(id);
    }
}
