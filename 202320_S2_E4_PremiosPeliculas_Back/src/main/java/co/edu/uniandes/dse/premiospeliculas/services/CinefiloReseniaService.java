package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.CinefiloEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.CinefiloRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.ReseniaRepository;

@Service
public class CinefiloReseniaService {
    @Autowired
	private CinefiloRepository cinefiloRepository;

	@Autowired
	private ReseniaRepository reseniaRepository;

    @Transactional
	
	public ReseniaEntity addResenia(Long cinefiloId, Long reseniaId) throws EntityNotFoundException {
		Optional<ReseniaEntity> reseniaEntity = this.reseniaRepository.findById(reseniaId);
        Optional<CinefiloEntity> cinefiloEntity = this.cinefiloRepository.findById(cinefiloId);
        
		if (reseniaEntity.isEmpty())
			throw new EntityNotFoundException("Resenia no encontrada");

		
		if (cinefiloEntity.isEmpty())
			throw new EntityNotFoundException("Cinefilo no encontrado");

		cinefiloEntity.get().getResenias().add(reseniaEntity.get());

		return reseniaEntity.get();
	}

    @Transactional
	public List<ReseniaEntity> getResenias(Long cinefiloId) throws EntityNotFoundException {
		Optional<CinefiloEntity> cinefiloEntity = this.cinefiloRepository.findById(cinefiloId);
		if (cinefiloEntity.isEmpty())
			throw new EntityNotFoundException("Cinefilo no encontrado");

		return cinefiloEntity.get().getResenias();
	}

    @Transactional
	public ReseniaEntity getResenia(Long cinefiloId, Long reseniaId) throws EntityNotFoundException, IllegalOperationException {
		Optional<CinefiloEntity> cinefiloEntity = this.cinefiloRepository.findById(cinefiloId);
		Optional<ReseniaEntity> reseniaEntity = this.reseniaRepository.findById(reseniaId);

		if (cinefiloEntity.isEmpty())
			throw new EntityNotFoundException("Cinefilo no encontrado ");

		if (reseniaEntity.isEmpty())
			throw new EntityNotFoundException("Resenia no encontrada");

		if (!reseniaEntity.get().getCinefilo().equals(cinefiloEntity.get()))
			throw new IllegalOperationException("El cinefilo no esta asociada con la resenia");
		
		return reseniaEntity.get();
	}

    @Transactional
	public List<ReseniaEntity> replaceResenias(Long cinefiloId, List<ReseniaEntity> resenias) throws EntityNotFoundException {
		Optional<CinefiloEntity> cinefiloEntity = this.cinefiloRepository.findById(cinefiloId);
		if(cinefiloEntity.isEmpty())
			throw new EntityNotFoundException("Cinefilo no encontrado");
		
		for(ReseniaEntity resenia : resenias) {
			Optional<ReseniaEntity> r = this.reseniaRepository.findById(resenia.getId());
			if(r.isEmpty())
				throw new EntityNotFoundException("Resenia no encontrada");
			
			r.get().setCinefilo(cinefiloEntity.get());
		}		
		return resenias;
	}
    
}