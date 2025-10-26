package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.ReseniaRepository;

@Service
public class ReseniaPeliculaService {
    @Autowired
	private PeliculaRepository peliculaRepository;

	@Autowired
	private ReseniaRepository reseniaRepository;

    @Transactional
	public ReseniaEntity replacePelicula(Long reseniaId, Long peliculaId) throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaentity = reseniaRepository.findById(reseniaId);
        if(reseniaentity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }
        Optional<PeliculaEntity> peliculaentity = peliculaRepository.findById(peliculaId);
        if(peliculaentity.isEmpty()){
            throw new EntityNotFoundException("Pelicula no encontrada");
        }
        reseniaentity.get().setPelicula(peliculaentity.get());
        
        return reseniaentity.get();

    }

    public void removePelicula(Long reseniaId) throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaentity = reseniaRepository.findById(reseniaId);

        if(reseniaentity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }

        Optional<PeliculaEntity> peliculaentity = peliculaRepository.findById(reseniaentity.get().getPelicula().getId());
        peliculaentity.ifPresent(pelicula -> pelicula.getResenias().remove(reseniaentity.get()));

        reseniaentity.get().setPelicula(null);
        
    }
    
}
