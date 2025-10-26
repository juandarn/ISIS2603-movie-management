package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;

import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PersonaRepository;

@Service
public class PeliculaPersonaService {
	@Autowired
	private PeliculaRepository peliculaRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Transactional
	public PersonaEntity addPersona(Long peliculaId, Long personaId) throws EntityNotFoundException {
		Optional<PersonaEntity> personaEntity = this.personaRepository.findById(personaId);
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);

		if (personaEntity.isEmpty())
			throw new EntityNotFoundException("Persona no encontrada");

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		peliculaEntity.get().getPersonas().add(personaEntity.get());

		return personaEntity.get();
	}

	@Transactional
	public List<PersonaEntity> getPersonas(Long peliculaId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		return peliculaEntity.get().getPersonas();
	}

	@Transactional
	public PersonaEntity getPersona(Long peliculaId, Long personaId)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		Optional<PersonaEntity> personaEntity = this.personaRepository.findById(personaId);

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada ");

		if (personaEntity.isEmpty())
			throw new EntityNotFoundException("Persona no encontrada");

		if (!personaEntity.get().getPeliculas().contains(peliculaEntity.get()))
			throw new IllegalOperationException("La persona no esta asociada con la pelicula");

		return personaEntity.get();
	}

	@Transactional
	public void removePersona(Long peliculaId, Long personaId) throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		Optional<PersonaEntity> personaEntity = this.personaRepository.findById(personaId);
		if (personaEntity.isEmpty())
			throw new EntityNotFoundException("Persona no encontrada");

		peliculaEntity.get().getPersonas().remove(personaEntity.get());
	}

	@Transactional
	public List<PersonaEntity> replacePersonas(Long peliculaId, List<PersonaEntity> list)
			throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		for (PersonaEntity persona : list) {
			Optional<PersonaEntity> personaEntity = this.personaRepository.findById(persona.getId());
			if (personaEntity.isEmpty())
				throw new EntityNotFoundException("Persona no encontrada");

			if (!peliculaEntity.get().getPersonas().contains(personaEntity.get()))
				peliculaEntity.get().getPersonas().add(personaEntity.get());
		}

		return getPersonas(peliculaId);
	}

	@Transactional
	public List<PersonaEntity> addPersonas(Long peliculaId, List<PersonaEntity> personas)
			throws EntityNotFoundException {
		Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(peliculaId);
		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada");

		for (PersonaEntity persona : personas) {
			Optional<PersonaEntity> personaEntity = this.personaRepository.findById(persona.getId());
			if (personaEntity.isEmpty())
				throw new EntityNotFoundException("Persona no encontrada");

			if (!personaEntity.get().getPeliculas().contains(peliculaEntity.get()))
				personaEntity.get().getPeliculas().add(peliculaEntity.get());
		}
		peliculaEntity.get().setPersonas(personas);
		return peliculaEntity.get().getPersonas();
	}

}
