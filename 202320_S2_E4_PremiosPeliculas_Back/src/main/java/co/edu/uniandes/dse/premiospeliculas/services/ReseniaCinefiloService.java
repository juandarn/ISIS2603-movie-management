package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.CinefiloEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.repositories.CinefiloRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.ReseniaRepository;

@Service
public class ReseniaCinefiloService {
    @Autowired
	private CinefiloRepository cinefiloRepository;

	@Autowired
	private ReseniaRepository reseniaRepository;

    @Transactional
	public ReseniaEntity replaceCinefilo(Long reseniaId, Long cinefiloId) throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaentity = reseniaRepository.findById(reseniaId);
        if(reseniaentity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }
        Optional<CinefiloEntity> cinefiloentity = cinefiloRepository.findById(cinefiloId);
        if(cinefiloentity.isEmpty()){
            throw new EntityNotFoundException("Cinefilo no encontrado");
        }
        reseniaentity.get().setCinefilo(cinefiloentity.get());
        
        return reseniaentity.get();

    }

    public void removeCinefilo(Long reseniaId) throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaentity = reseniaRepository.findById(reseniaId);

        if(reseniaentity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }

        Optional<CinefiloEntity> cinefiloentity = cinefiloRepository.findById(reseniaentity.get().getCinefilo().getId());
        cinefiloentity.ifPresent(cinefilo -> cinefilo.getResenias().remove(reseniaentity.get()));

        reseniaentity.get().setCinefilo(null);
        
    }
}
