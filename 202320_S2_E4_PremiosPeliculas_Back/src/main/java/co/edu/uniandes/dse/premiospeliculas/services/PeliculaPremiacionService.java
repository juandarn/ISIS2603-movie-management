package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremiacionRepository;

@Service
public class PeliculaPremiacionService {
	@Autowired
	private PeliculaRepository peliculaRepository;

	@Autowired
	private PremiacionRepository premiacionRepository;

	@Transactional
	public PremiacionEntity addPremiacion(Long peliculaId, Long premiacionId) throws EntityNotFoundException {
		Optional<PremiacionEntity> premiacionEntity = this.premiacionRepository.findById(premiacionId);
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);

		if (premiacionEntity.isEmpty())
			throw new EntityNotFoundException("Premiacion no encontrada");

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		peliculaEntity.get().getPremiaciones().add(premiacionEntity.get());

		return premiacionEntity.get();
	}

	@Transactional
	public List<PremiacionEntity> getPremiaciones(Long peliculaId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		return peliculaEntity.get().getPremiaciones();
	}

	@Transactional
	public PremiacionEntity getPremiacion(Long peliculaId, Long premiacionId)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		Optional<PremiacionEntity> premiacionEntity = this.premiacionRepository.findById(premiacionId);

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada ");

		if (premiacionEntity.isEmpty())
			throw new EntityNotFoundException("premiacion no encontrada");

		if (!premiacionEntity.get().getPeliculas().contains(peliculaEntity.get()))
			throw new IllegalOperationException("La premiación no esta asociada con la pelicula");

		return premiacionEntity.get();
	}

	@Transactional
	public void removePremiacion(Long peliculaId, Long premiacionId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		Optional<PremiacionEntity> premiacionEntity = this.premiacionRepository.findById(premiacionId);
		if (premiacionEntity.isEmpty())
			throw new EntityNotFoundException("Premiación no encontrada");

		premiacionEntity.get().getPeliculas().remove(peliculaEntity.get());
		peliculaEntity.get().getPremiaciones().remove(premiacionEntity.get());
	}

	@Transactional
	public List<PremiacionEntity> replacePremiaciones(Long peliculaId, List<PremiacionEntity> list)
			throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		for (PremiacionEntity premiacion : list) {
			Optional<PremiacionEntity> premiacionEntity = this.premiacionRepository.findById(premiacion.getId());
			if (premiacionEntity.isEmpty())
				throw new EntityNotFoundException("Premiacion no encontrada");

			if (!peliculaEntity.get().getPremiaciones().contains(premiacionEntity.get()))
				peliculaEntity.get().getPremiaciones().add(premiacionEntity.get());
		}

		return getPremiaciones(peliculaId);
	}

	@Transactional
	public List<PremiacionEntity> addPremiaciones(Long peliculaId, List<PremiacionEntity> premiaciones)
			throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		for (PremiacionEntity premiacion : premiaciones) {
			Optional<PremiacionEntity> premiacionEntity = this.premiacionRepository.findById(premiacion.getId());
			if (premiacionEntity.isEmpty())
				throw new EntityNotFoundException("Premiacion no encontrada");

			if (!premiacionEntity.get().getPeliculas().contains(peliculaEntity.get()))
				premiacionEntity.get().getPeliculas().add(peliculaEntity.get());
		}
		peliculaEntity.get().setPremiaciones(premiaciones);
		return peliculaEntity.get().getPremiaciones();
	}

}
