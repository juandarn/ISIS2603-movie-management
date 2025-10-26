package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremioRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.ReseniaRepository;

@Service
public class ReseniaPremioService {
    @Autowired
	private PremioRepository premioRepository;

	@Autowired
	private ReseniaRepository reseniaRepository;

    @Transactional
	public ReseniaEntity replacePremio(Long reseniaId, Long premioId) throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaentity = reseniaRepository.findById(reseniaId);
        if(reseniaentity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }
        Optional<PremioEntity> premioentity = premioRepository.findById(premioId);
        if(premioentity.isEmpty()){
            throw new EntityNotFoundException("Premio no encontrado");
        }
        reseniaentity.get().setPremio(premioentity.get());
        
        return reseniaentity.get();

    }

    public void removePremio(Long reseniaId) throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaentity = reseniaRepository.findById(reseniaId);

        if(reseniaentity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }

        Optional<PremioEntity> premioentity = premioRepository.findById(reseniaentity.get().getPremio().getId());
        premioentity.ifPresent(premio -> premio.getResenias().remove(reseniaentity.get()));

        reseniaentity.get().setPremio(null);        
    }

}
