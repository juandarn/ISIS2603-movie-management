package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PlataformaEntity;

import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PlataformaRepository;

@Service
public class PeliculaPlataformaService {
	@Autowired
	private PeliculaRepository peliculaRepository;

	@Autowired
	private PlataformaRepository plataformaRepository;

	@Transactional
	public PlataformaEntity addPlataforma(Long peliculaId, Long plataformaId) throws EntityNotFoundException {
		Optional<PlataformaEntity> plataformaEntity = this.plataformaRepository.findById(plataformaId);
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);

		if (plataformaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		peliculaEntity.get().getPlataformas().add(plataformaEntity.get());

		return plataformaEntity.get();
	}

	@Transactional
	public List<PlataformaEntity> getPlataformas(Long peliculaId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		return peliculaEntity.get().getPlataformas();
	}

	@Transactional
	public PlataformaEntity getPlataforma(Long peliculaId, Long plataformaId)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		Optional<PlataformaEntity> plataformaEntity = this.plataformaRepository.findById(plataformaId);

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada ");

		if (plataformaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		if (!plataformaEntity.get().getPeliculas().contains(peliculaEntity.get()))
			throw new IllegalOperationException("La plataforma no esta asociada con la pelicula");

		return plataformaEntity.get();
	}

	@Transactional
	public void removePlataforma(Long peliculaId, Long plataformaId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		Optional<PlataformaEntity> plataformaEntity = this.plataformaRepository.findById(plataformaId);
		if (plataformaEntity.isEmpty())
			throw new EntityNotFoundException("Plataforma no encontrada");

		plataformaEntity.get().getPeliculas().remove(peliculaEntity.get());
		peliculaEntity.get().getPlataformas().remove(plataformaEntity.get());
	}

	@Transactional
	public List<PlataformaEntity> replacePlataformas(Long peliculaId, List<PlataformaEntity> list)
			throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		for (PlataformaEntity plataforma : list) {
			Optional<PlataformaEntity> plataformaEntity = this.plataformaRepository.findById(plataforma.getId());
			if (plataformaEntity.isEmpty())
				throw new EntityNotFoundException("Premiacion no encontrada");

			if (!peliculaEntity.get().getPlataformas().contains(plataformaEntity.get()))
				peliculaEntity.get().getPlataformas().add(plataformaEntity.get());
		}

		return getPlataformas(peliculaId);
	}

	@Transactional
	public List<PlataformaEntity> addPlataformas(Long peliculaId, List<PlataformaEntity> plataformas)
			throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		for (PlataformaEntity plataforma : plataformas) {
			Optional<PlataformaEntity> plataformaEntity = this.plataformaRepository.findById(plataforma.getId());
			if (plataformaEntity.isEmpty())
				throw new EntityNotFoundException("Plataforma no encontrada");

			if (!plataformaEntity.get().getPeliculas().contains(peliculaEntity.get()))
				plataformaEntity.get().getPeliculas().add(peliculaEntity.get());
		}
		peliculaEntity.get().setPlataformas(plataformas);
		return peliculaEntity.get().getPlataformas();
	}

}
