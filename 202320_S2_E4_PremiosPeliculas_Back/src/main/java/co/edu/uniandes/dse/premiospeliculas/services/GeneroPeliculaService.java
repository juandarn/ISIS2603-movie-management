package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.GeneroRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GeneroPeliculaService {
    @Autowired
	private GeneroRepository generoRepository;

	@Autowired
	private PeliculaRepository peliculaRepository;

    @Transactional
	public PeliculaEntity addPelicula(Long generoId, Long peliculaId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una película al género con id = {0}", generoId);
		Optional<GeneroEntity> generoEntity = generoRepository.findById(generoId);
		Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);

		if (generoEntity.isEmpty())
			throw new EntityNotFoundException("Género no encontrado");

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Película no encontrada");

		peliculaEntity.get().getGeneros().add(generoEntity.get());
		log.info("Termina proceso de asociarle una pelicula al género con id = {0}", generoId);
		return peliculaEntity.get();
	}

    @Transactional
	public List<PeliculaEntity> getPeliculas(Long generoId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos las peliculas del género con id = {0}", generoId);
		Optional<GeneroEntity> generoEntity = generoRepository.findById(generoId);
		if (generoEntity.isEmpty())
			throw new EntityNotFoundException("Género no encontrado");

		log.info("Termina proceso de consultar todos las peliculas del género con id = {0}", generoId);
		return generoEntity.get().getPeliculas();
	}

    @Transactional
	public PeliculaEntity getPelicula(Long generoId, Long peliculaId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la película con id = {0} del genero con id = " + generoId, peliculaId);
		Optional<GeneroEntity> generoEntity = generoRepository.findById(generoId);
		Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);

		if (generoEntity.isEmpty())
			throw new EntityNotFoundException("Género no encontrado");

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		log.info("Termina proceso de consultar la pelicula con id = {0} del género con id = " + generoId, peliculaId);
		if (!peliculaEntity.get().getGeneros().contains(generoEntity.get()))
			throw new IllegalOperationException("La pelicula no está asociada al género");
		
		return peliculaEntity.get();
	}

    @Transactional
	public List<PeliculaEntity> addPeliculas(Long generoId, List<PeliculaEntity> peliculas) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las peliculas asociadas al género con id = {0}", generoId);
		Optional<GeneroEntity> generoEntity = generoRepository.findById(generoId);
		if (generoEntity.isEmpty())
			throw new EntityNotFoundException("Género no encontrado");

		for (PeliculaEntity pelicula : peliculas) {
			Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(pelicula.getId());
			if (peliculaEntity.isEmpty())
				throw new EntityNotFoundException("Película no encontrada");

			if (!peliculaEntity.get().getGeneros().contains(generoEntity.get()))
				peliculaEntity.get().getGeneros().add(generoEntity.get());
		}
		log.info("Finaliza proceso de reemplazar las peliculas asociadas al género con id = {0}", generoId);
		generoEntity.get().setPeliculas(peliculas);
		return generoEntity.get().getPeliculas();
	}
    @Transactional
	public void removePelicula(Long generoId, Long peliculaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una película del género con id = {0}", generoId);
		Optional<GeneroEntity> generoEntity = generoRepository.findById(generoId);
		if (generoEntity.isEmpty())
			throw new EntityNotFoundException("Género no encontrado");

		Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Película no encontrada");

		peliculaEntity.get().getGeneros().remove(generoEntity.get());
		generoEntity.get().getPeliculas().remove(peliculaEntity.get());
		log.info("Finaliza proceso de borrar una película de la género con id = {0}", generoId);
	}
}
