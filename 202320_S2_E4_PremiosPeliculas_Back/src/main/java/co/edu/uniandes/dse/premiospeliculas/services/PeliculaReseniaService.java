package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.ReseniaRepository;

@Service
public class PeliculaReseniaService {
    @Autowired
	private PeliculaRepository peliculaRepository;

	@Autowired
	private ReseniaRepository reseniaRepository;

    @Transactional
	
	public ReseniaEntity addResenia(Long peliculaId, Long reseniaId) throws EntityNotFoundException {
		Optional<ReseniaEntity> reseniaEntity = this.reseniaRepository.findById(reseniaId);
        Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
        
		if (reseniaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		peliculaEntity.get().getResenias().add(reseniaEntity.get());

		return reseniaEntity.get();
	}

    @Transactional
	public List<ReseniaEntity> getResenias(Long peliculaId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		return peliculaEntity.get().getResenias();
	}

    @Transactional
	public ReseniaEntity getResenia(Long peliculaId, Long reseniaId) throws EntityNotFoundException, IllegalOperationException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		Optional<ReseniaEntity> reseniaEntity = this.reseniaRepository.findById(reseniaId);

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada ");

		if (reseniaEntity.isEmpty())
			throw new EntityNotFoundException("Resenia no encontrada");

		if (!reseniaEntity.get().getPelicula().equals(peliculaEntity.get()))
			throw new IllegalOperationException("La resenia no esta asociada con la pelicula");
		
		return reseniaEntity.get();
	}

    @Transactional
	public List<ReseniaEntity> replaceResenias(Long peliculaId, List<ReseniaEntity> resenias) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if(peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");
		
		for(ReseniaEntity resenia : resenias) {
			Optional<ReseniaEntity> r = this.reseniaRepository.findById(resenia.getId());
			if(r.isEmpty())
				throw new EntityNotFoundException("Resenia no encontrada");
			
			r.get().setPelicula(peliculaEntity.get());
		}		
		return resenias;
	}
    
}
