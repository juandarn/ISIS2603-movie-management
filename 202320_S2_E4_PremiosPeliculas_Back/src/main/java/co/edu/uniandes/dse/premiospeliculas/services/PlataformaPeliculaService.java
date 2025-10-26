package co.edu.uniandes.dse.premiospeliculas.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PlataformaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PlataformaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlataformaPeliculaService {
    @Autowired
	private PlataformaRepository plataformaRepository;

	@Autowired
	private PeliculaRepository peliculaRepository;

    @Transactional
	public PeliculaEntity addPelicula(Long plataformaId, Long peliculaId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una película a la plataforma con id = {0}", plataformaId);
		Optional<PlataformaEntity> plataformaEntity = plataformaRepository.findById(plataformaId);
		Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);

		if (plataformaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Película no encontrada");

		peliculaEntity.get().getPlataformas().add(plataformaEntity.get());
		log.info("Termina proceso de asociarle una pelicula a la plataforma con id = {0}", plataformaId);
		return peliculaEntity.get();
	}

    @Transactional
	public List<PeliculaEntity> getPeliculas(Long plataformaId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos las peliculas de la plataforma con id = {0}", plataformaId);
		Optional<PlataformaEntity> plataformaEntity = plataformaRepository.findById(plataformaId);
		if (plataformaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		log.info("Termina proceso de consultar todos las peliculas de la plataforma con id = {0}", plataformaId);
		return plataformaEntity.get().getPeliculas();
	}

    @Transactional
	public PeliculaEntity getPelicula(Long plataformaId, Long peliculaId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la película con id = {0} de la plataforma con id = " + plataformaId, peliculaId);
		Optional<PlataformaEntity> plataformaEntity = plataformaRepository.findById(plataformaId);
		Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);

		if (plataformaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		log.info("Termina proceso de consultar la pelicula con id = {0} de la plataforma con id = " + plataformaId, peliculaId);
		if (!peliculaEntity.get().getPlataformas().contains(plataformaEntity.get()))
			throw new IllegalOperationException("La pelicula no está asociada a la plataforma");
		
		return peliculaEntity.get();
	}


    @Transactional
	public List<PeliculaEntity> addPeliculas(Long plataformaId, List<PeliculaEntity> peliculas) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las peliculas asociadas a la plataforma con id = {0}", plataformaId);
		Optional<PlataformaEntity> plataformaEntity = plataformaRepository.findById(plataformaId);
		if (plataformaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		for (PeliculaEntity pelicula : peliculas) {
			Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(pelicula.getId());
			if (peliculaEntity.isEmpty())
				throw new EntityNotFoundException("Película no encontrada");

			if (!peliculaEntity.get().getPlataformas().contains(plataformaEntity.get()))
				peliculaEntity.get().getPlataformas().add(plataformaEntity.get());
		}
		log.info("Finaliza proceso de reemplazar las peliculas asociadas a la plataforma con id = {0}", plataformaId);
		plataformaEntity.get().setPeliculas(peliculas);
		return plataformaEntity.get().getPeliculas();
	}
    @Transactional
	public void removePelicula(Long plataformaId, Long peliculaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una película de la plataforma con id = {0}", plataformaId);
		Optional<PlataformaEntity> plataformaEntity = plataformaRepository.findById(plataformaId);
		if (plataformaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Película no encontrada");

		peliculaEntity.get().getPlataformas().remove(plataformaEntity.get());
		plataformaEntity.get().getPeliculas().remove(peliculaEntity.get());
		log.info("Finaliza proceso de borrar una película de la plataforma con id = {0}", plataformaId);
	}
}
