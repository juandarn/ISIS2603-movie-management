package co.edu.uniandes.dse.premiospeliculas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PersonaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository personaRepository;

    public List<PersonaEntity> getPersonas() {
        return personaRepository.findAll();        
    }

    public PersonaEntity getPersona(Long id) throws EntityNotFoundException {
        Optional<PersonaEntity> personaEntity =personaRepository.findById(id);
        if (personaEntity.isEmpty()){
            throw new EntityNotFoundException("Persona no encontada para obtenerla");
        }
        return personaEntity.get();
        
    }

    public PersonaEntity createPersona(PersonaEntity personaEntity) throws IllegalOperationException {
        if (personaEntity.getNombre() == null || personaEntity.getNombre().isEmpty()){
            throw new IllegalOperationException("El nombre no puede ser vacio");
        }

        if (!personaEntity.getNombre().strip().contains(" ")){
            throw new IllegalOperationException("Incluya otro nombre o apellido");
        }

        if (personaEntity.getNacionalidad() == null || personaEntity.getNacionalidad().isEmpty()){
            throw new IllegalOperationException("La nacionalidad no es valida");
        }

        if (personaEntity.getFechaNacimiento() == null){
            throw new IllegalOperationException("La fecha de nacimieno no es valida");
        }

        if (personaEntity.getBiografia() == null || personaEntity.getBiografia().isEmpty()){
            throw new IllegalOperationException("La biografia no es valida");
        }

        if (personaEntity.getBiografia().strip().length() > 300){
            throw new IllegalOperationException("La biografia debe ser de maximo 300 palabras");
        }

        if ( personaEntity.getRol()== null || personaEntity.getRol().isEmpty()){
                throw new IllegalOperationException("Rol no para crear valido");
            }

        String[] roles = {"actriz", "actor", "director", "directora"};
        boolean rolBoolean = false;

        for(String rol : roles){
            if ( personaEntity.getRol().equalsIgnoreCase(rol)){
                rolBoolean = true;
            }
        }

        if (!rolBoolean){
            throw new IllegalOperationException("Rol " + personaEntity.getRol() +" no valido");
        }

        
        personaEntity = personaRepository.save(personaEntity);
        return personaEntity;
        
    }

    public void deletePersona(Long personaId) throws EntityNotFoundException, IllegalOperationException {
        Optional<PersonaEntity> personaEntity =personaRepository.findById(personaId);

        if (personaEntity.isEmpty()){
            throw new EntityNotFoundException("Persona no encontada para eliminar");
        } 
        List<PeliculaEntity> peliculas = personaEntity.get().getPeliculas();

        if (!peliculas.isEmpty()) {
            throw new IllegalOperationException("No se puede borrar esta persona porque tiene peliculas asociadas");
        }

        personaRepository.deleteById(personaId);
    }

    public PersonaEntity updatePersona(Long personaId, PersonaEntity persona) throws EntityNotFoundException, IllegalOperationException {
        Optional<PersonaEntity> personaEntity =personaRepository.findById(personaId);
        if (personaEntity.isEmpty()){
            throw new EntityNotFoundException("Persona no encontada para actualizar");
        }

        if (persona.getNombre() == null || persona.getNombre().isEmpty()){
            throw new IllegalOperationException("El nombre no puede ser vacio");
        }

        if (!persona.getNombre().strip().contains(" ")){
            throw new IllegalOperationException("Incluya otro nombre o apellido");
        }

        if (persona.getNacionalidad() == null || persona.getNacionalidad().isEmpty()){
            throw new IllegalOperationException("La nacionalidad no es valida");
        }

        if (persona.getFechaNacimiento() == null){
            throw new IllegalOperationException("La fecha de nacimieno no es valida");
        }

        if (persona.getBiografia() == null || persona.getBiografia().isEmpty()){
            throw new IllegalOperationException("La biografia no es valida");
        }

        if (persona.getBiografia().strip().length() > 300){
            throw new IllegalOperationException("La biografia debe ser de maximo 300 palabras");
        }

        if ( persona.getRol()== null || persona.getRol().isEmpty()){
                throw new IllegalOperationException("Rol nulo para actucalizar no valido");
            }

        String[] roles = {"actriz", "actor", "director", "directora"};
        boolean rolBoolean = false;

        for(String rol : roles){
            if ( persona.getRol().equalsIgnoreCase(rol)){
                rolBoolean = true;
            }
        }

        if (!rolBoolean){
            throw new IllegalOperationException("Rol " + persona.getRol() +" no valido");
        }
        
        persona.setId(personaId);
        return personaRepository.save(persona);
        
    }

}
