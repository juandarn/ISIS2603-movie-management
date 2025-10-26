package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;

import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.GeneroRepository;

@Service
public class PeliculaGeneroService {
	@Autowired
	private PeliculaRepository peliculaRepository;

	@Autowired
	private GeneroRepository generoRepository;

	@Transactional
	public GeneroEntity addGenero(Long peliculaId, Long generoId) throws EntityNotFoundException {
		Optional<GeneroEntity> generoEntity = this.generoRepository.findById(generoId);
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);

		if (generoEntity.isEmpty())
			throw new EntityNotFoundException("Genero no encontrado");

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		peliculaEntity.get().getGeneros().add(generoEntity.get());

		return generoEntity.get();
	}

	@Transactional
	public List<GeneroEntity> getGeneros(Long peliculaId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		return peliculaEntity.get().getGeneros();
	}

	@Transactional
	public GeneroEntity getGenero(Long peliculaId, Long generoId)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		Optional<GeneroEntity> generoEntity = this.generoRepository.findById(generoId);

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada ");

		if (generoEntity.isEmpty())
			throw new EntityNotFoundException("Genero no encontrado");

		if (!generoEntity.get().getPeliculas().contains(peliculaEntity.get()))
			throw new IllegalOperationException("El genero no esta asociado con la pelicula");

		return generoEntity.get();
	}

	@Transactional
	public List<GeneroEntity> replaceGeneros(Long peliculaId, List<GeneroEntity> list) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		for (GeneroEntity genero : list) {
			Optional<GeneroEntity> generoEntity = this.generoRepository.findById(genero.getId());
			if (generoEntity.isEmpty())
				throw new EntityNotFoundException("Genero no encontrada");

			if (!peliculaEntity.get().getGeneros().contains(generoEntity.get()))
				peliculaEntity.get().getGeneros().add(generoEntity.get());
		}

		return getGeneros(peliculaId);
	}

	@Transactional
	public void removeGenero(Long peliculaId, Long generoId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		Optional<GeneroEntity> generoEntity = this.generoRepository.findById(generoId);
		if (generoEntity.isEmpty())
			throw new EntityNotFoundException("Genero no encontrado");

		generoEntity.get().getPeliculas().remove(peliculaEntity.get());
		peliculaEntity.get().getGeneros().remove(generoEntity.get());
	}

	@Transactional
	public List<GeneroEntity> addGeneros(Long peliculaId, List<GeneroEntity> generos) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		for (GeneroEntity genero : generos) {
			Optional<GeneroEntity> generoEntity = this.generoRepository.findById(genero.getId());
			if (generoEntity.isEmpty())
				throw new EntityNotFoundException("Genero no encontrado");

			if (!generoEntity.get().getPeliculas().contains(peliculaEntity.get()))
				generoEntity.get().getPeliculas().add(peliculaEntity.get());
		}
		peliculaEntity.get().setGeneros(generos);
		return peliculaEntity.get().getGeneros();
	}

}
