package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremioRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.ReseniaRepository;



@Service
public class PremioReseniaService {
    @Autowired
    private ReseniaRepository reseniaRepository;

    @Autowired
    private PremioRepository premioRepository;

    @Transactional
    public ReseniaEntity addResenia(Long ReseniaID, Long PremioID) throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(ReseniaID);
        if(reseniaEntity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }

        Optional<PremioEntity> premioEntity = premioRepository.findById(PremioID);
        if(premioEntity.isEmpty()){
            throw new EntityNotFoundException("Premio no encontrado");
        }
        
        reseniaEntity.get().setPremio(premioEntity.get());
        return reseniaEntity.get();

        
    }

    @Transactional
    public List<ReseniaEntity> getResenias(Long PremioID) throws EntityNotFoundException{
        Optional<PremioEntity> premioEntity = premioRepository.findById(PremioID);
        if(premioEntity.isEmpty()){
            throw new EntityNotFoundException("Premio no encontrado");
        }
        return premioEntity.get().getResenias();
    }

    @Transactional
    public ReseniaEntity getResenia(Long premioId, Long reseniaId) throws EntityNotFoundException, IllegalOperationException {
    Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);
    if (premioEntity.isEmpty()) {
        throw new EntityNotFoundException("Premio no encontrado");
    }

    Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(reseniaId);
    if (reseniaEntity.isEmpty()) {
        throw new EntityNotFoundException("Resenia no encontrada");
    }

    if (!premioEntity.get().getResenias().contains(reseniaEntity.get())) {
        throw new IllegalOperationException("La resenia no está asociada al premio");
    }

    return reseniaEntity.get();
}

    @Transactional
    public List<ReseniaEntity> replaceResenias(Long premioId, List<ReseniaEntity> resenias) throws EntityNotFoundException {

    Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);
    if (premioEntity.isEmpty()) {
        throw new EntityNotFoundException("Premio no encontrado");
    }

    for (ReseniaEntity resenia : resenias) {
        Optional<ReseniaEntity> r = reseniaRepository.findById(resenia.getId());
        if (r.isEmpty()) {
            throw new EntityNotFoundException("Resenia no encontrada");
        }

        r.get().setPremio(premioEntity.get());
    }

    return resenias;
}


}
