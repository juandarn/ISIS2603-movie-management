package co.edu.uniandes.dse.premiospeliculas.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.premiospeliculas.dto.PersonaDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.PersonaDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PersonaService;
import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PersonaDetailDTO> findAll() {
        List<PersonaEntity> personas = personaService.getPersonas();
        return modelMapper.map(personas, new TypeToken<List<PersonaDetailDTO>>() {
        }.getType());

    }
    
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PersonaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        PersonaEntity personaEntity = personaService.getPersona(id);
        return modelMapper.map(personaEntity, PersonaDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PersonaDTO create(@RequestBody PersonaDTO personaDTO) throws IllegalOperationException{
        PersonaEntity personaEntity = personaService.createPersona(modelMapper.map(personaDTO,PersonaEntity.class));
        return modelMapper.map(personaEntity, PersonaDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PersonaDTO update(@PathVariable("id") Long id, @RequestBody PersonaDTO personaDTO) throws EntityNotFoundException, IllegalOperationException{
        PersonaEntity personaEntity = personaService.updatePersona(id,
         modelMapper.map(personaDTO, PersonaEntity.class));
         return modelMapper.map(personaEntity, PersonaDTO.class);
    }    

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) throws EntityNotFoundException, IllegalOperationException{
        personaService.deletePersona(id);
    }

}
