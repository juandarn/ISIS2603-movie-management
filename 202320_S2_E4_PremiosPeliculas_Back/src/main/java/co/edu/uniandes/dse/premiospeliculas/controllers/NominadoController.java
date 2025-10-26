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
import co.edu.uniandes.dse.premiospeliculas.dto.NominadoDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.NominadoDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.NominadoEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.NominadoService;


@RestController
@RequestMapping("/nominados")
public class NominadoController {
    @Autowired
	private NominadoService nominadoService;

	@Autowired
	private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus( code= HttpStatus.OK)
    public List<NominadoDetailDTO> findAll(){
        List<NominadoEntity> nominados= nominadoService.getNominados();
        return modelMapper.map(nominados, new TypeToken<List<NominadoDetailDTO>>() {
		}.getType());
	}

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public NominadoDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException{
        NominadoEntity nominado = nominadoService.getNominado(id);
        return modelMapper.map(nominado, NominadoDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public NominadoDTO create(@RequestBody NominadoDTO nominadoDTO) throws IllegalOperationException, EntityNotFoundException{
        NominadoEntity nominadoEntity = nominadoService.createNominado(modelMapper.map(nominadoDTO, NominadoEntity.class));
        return modelMapper.map(nominadoEntity, NominadoDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public NominadoDTO update(@PathVariable("id") Long id, @RequestBody NominadoDTO nominadoDTO) throws EntityNotFoundException, IllegalOperationException{
        NominadoEntity nominadoEntity = nominadoService.UpdateNominado(id, 
        modelMapper.map(nominadoDTO, NominadoEntity.class));
        return modelMapper.map(nominadoEntity, NominadoDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) throws EntityNotFoundException{
        nominadoService.deleteNominado(id);
    }

}

