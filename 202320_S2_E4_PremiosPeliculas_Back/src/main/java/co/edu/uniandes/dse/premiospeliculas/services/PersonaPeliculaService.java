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
public class PersonaPeliculaService {
	@Autowired
	private PersonaRepository personaRepository;

    @Autowired
	private PeliculaRepository peliculaRepository;

    @Transactional
	public PeliculaEntity addPelicula(Long personaId, Long peliculaId) throws EntityNotFoundException {
		Optional<PersonaEntity> personaEntity = personaRepository.findById(personaId);
        Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);

		if (peliculaEntity.isEmpty())
			throw new EntityNotFoundException("Pelicula no encontrada para añadirla a una persona");
        
		if (personaEntity.isEmpty())
			throw new EntityNotFoundException("Persona no encontrada para añadirle una pelicula");

		peliculaEntity.get().getPersonas().add(personaEntity.get());

		return peliculaEntity.get();
	}

    @Transactional
	public List<PeliculaEntity> getPeliculas(Long personaId) throws EntityNotFoundException {
		Optional<PersonaEntity> personaEntity = personaRepository.findById(personaId);
		if (personaEntity.isEmpty())
			throw new EntityNotFoundException("persona no encontrada para obtener peliculas");

		return personaEntity.get().getPeliculas();
	}

    @Transactional
	public PeliculaEntity getPelicula(Long personaId, Long peliculaId) throws EntityNotFoundException, IllegalOperationException {
		Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);
		Optional<PersonaEntity> personaEntity = personaRepository.findById(personaId);
		
		if (personaEntity.isEmpty()){
			throw new EntityNotFoundException("Persona no encontrada para obtener una pelicula");
		}

		if (peliculaEntity.isEmpty()){
			throw new EntityNotFoundException("Pelicula no encontrada para ser obtenida");
		}

		if (!peliculaEntity.get().getPersonas().contains(personaEntity.get())){
			throw new IllegalOperationException("La pelicula no esta asociada con esta persona");
		}
		
		return peliculaEntity.get();
	}

    

    @Transactional
	public void removePelicula(Long personaId, Long peliculaId) throws EntityNotFoundException {
		Optional<PersonaEntity> personaEntity = personaRepository.findById(personaId);
		if (personaEntity.isEmpty()){
			throw new EntityNotFoundException("Persona no encontrada para remover pelicula");
		}
		
		Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(peliculaId);
		
		if (peliculaEntity.isEmpty()){
			throw new EntityNotFoundException("Pelicula no encontrada para ser removida");	
		}

		personaEntity.get().getPeliculas().remove(peliculaEntity.get());
		peliculaEntity.get().getPersonas().remove(personaEntity.get());
	}


	@Transactional
	public List<PeliculaEntity> addPeliculas(Long personaId, List<PeliculaEntity> peliculas) throws EntityNotFoundException {
		Optional<PersonaEntity> personaEntity = personaRepository.findById(personaId);
		if (personaEntity.isEmpty()){
			throw new EntityNotFoundException("Persona no encontrada para añadirle peliculas");
		}

		for (PeliculaEntity pelicula : peliculas) {
			Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(pelicula.getId());
			if (peliculaEntity.isEmpty()){
				throw new EntityNotFoundException("Pelicula no encontrada para añadirse a una persona");
			}

			if (!peliculaEntity.get().getPersonas().contains(personaEntity.get())){
				peliculaEntity.get().getPersonas().add(personaEntity.get());
			}
		}
		personaEntity.get().setPeliculas(peliculas);
		return personaEntity.get().getPeliculas();
	}
    
}
