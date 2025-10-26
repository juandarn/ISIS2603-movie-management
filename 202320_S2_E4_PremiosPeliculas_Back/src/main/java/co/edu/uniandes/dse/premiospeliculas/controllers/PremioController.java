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
import co.edu.uniandes.dse.premiospeliculas.dto.PremioDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.PremioDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PremioService;

@RestController 
@RequestMapping("/premios")
public class PremioController {

    @Autowired
	private PremioService premioService;

	@Autowired
	private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus( code= HttpStatus.OK)
    public List<PremioDetailDTO> findAll(){
        List<PremioEntity> premios= premioService.getPremios();
        return modelMapper.map(premios, new TypeToken<List<PremioDetailDTO>>() {
		}.getType());
	}

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PremioDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException{
        PremioEntity premio = premioService.getPremio(id);
        return modelMapper.map(premio, PremioDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PremioDTO create(@RequestBody PremioDTO premioDTO) throws IllegalOperationException, EntityNotFoundException{
        PremioEntity premioEntity = premioService.createPremio(modelMapper.map(premioDTO,PremioEntity.class));
        return modelMapper.map(premioEntity, PremioDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PremioDTO update(@PathVariable("id") Long id, @RequestBody PremioDTO premioDTO) throws EntityNotFoundException, IllegalOperationException{
        PremioEntity premioEntity = premioService.UpdatePremio(id,
        modelMapper.map(premioDTO, PremioEntity.class));
        return modelMapper.map(premioEntity, PremioDTO.class);
    }  

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) throws EntityNotFoundException{
        premioService.deletePremio(id);
    }
}