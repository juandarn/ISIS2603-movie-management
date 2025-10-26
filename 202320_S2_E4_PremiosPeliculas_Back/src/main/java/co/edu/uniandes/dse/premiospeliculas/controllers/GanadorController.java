package co.edu.uniandes.dse.premiospeliculas.controllers;
import java.util.List;
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

import co.edu.uniandes.dse.premiospeliculas.dto.GanadorDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.GanadorDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.GanadorEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.GanadorService;

@RestController 
@RequestMapping("/ganadores")
public class GanadorController {

    @Autowired
	private GanadorService ganadorService;

	@Autowired
	private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus( code= HttpStatus.OK)
    public List<GanadorDetailDTO> findAll(){
        List<GanadorEntity> ganadores= ganadorService.GetGanadores();
        return modelMapper.map(ganadores, new TypeToken<List<GanadorDetailDTO>>() {
		}.getType());
	}

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GanadorDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException{
        GanadorEntity ganador = ganadorService.getGanador(id);
        return modelMapper.map(ganador, GanadorDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GanadorDTO create(@RequestBody GanadorDTO ganadorDTO) throws IllegalOperationException, EntityNotFoundException{
        GanadorEntity ganadorEntity = ganadorService.CreateGanador(modelMapper.map(ganadorDTO,GanadorEntity.class));
        return modelMapper.map(ganadorEntity, GanadorDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GanadorDTO update(@PathVariable("id") Long id, @RequestBody GanadorDTO ganadorDTO) throws EntityNotFoundException, IllegalOperationException{
        GanadorEntity ganadorEntity = ganadorService.UpdateGanador(id,
         modelMapper.map(ganadorDTO, GanadorEntity.class));
         return modelMapper.map(ganadorEntity, GanadorDTO.class);
    }    

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) throws EntityNotFoundException{
        ganadorService.DeleteGanador(id);
    }

}
