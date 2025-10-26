package co.edu.uniandes.dse.premiospeliculas.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.premiospeliculas.dto.ReseniaDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.ReseniaDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PremioReseniaService;

@RestController
@RequestMapping("/premios")
public class PremioReseniaController {
    @Autowired
    private PremioReseniaService premioReseniaService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(value = "/{premioId}/resenias/{reseniaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReseniaDTO addResenia(@PathVariable("reseniaId") Long reseniaId, @PathVariable("premioId") Long premioId)
			throws EntityNotFoundException {
		ReseniaEntity reseniaEntity = premioReseniaService.addResenia(reseniaId, premioId);
		return modelMapper.map(reseniaEntity, ReseniaDTO.class);
	}


    
    @GetMapping(value = "/{premioId}/resenias/{reseniaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReseniaDetailDTO getResenia(@PathVariable("premioId") Long premioId, @PathVariable("reseniaId") Long reseniaId) throws EntityNotFoundException, IllegalOperationException {
		ReseniaEntity resenia = premioReseniaService.getResenia(premioId, reseniaId);
		return modelMapper.map(resenia, ReseniaDetailDTO.class);
	}

    @GetMapping(value = "/{premioId}/resenias")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ReseniaDetailDTO> getResenias(@PathVariable("premioId") Long premioId) throws EntityNotFoundException {
        List<ReseniaEntity> resenias = premioReseniaService.getResenias(premioId);
        return modelMapper.map(resenias, new TypeToken<List<ReseniaDetailDTO>>() {}.getType());
    }

    @PutMapping(value = "/{premioId}/resenias")
	@ResponseStatus(code = HttpStatus.OK)
    public List<ReseniaDetailDTO> replaceResenias(@PathVariable("premioId") Long premioId, @RequestBody List<ReseniaDetailDTO> resenias)
            throws EntityNotFoundException {
        List<ReseniaEntity> reseniaEntities = modelMapper.map(resenias, new TypeToken<List<ReseniaEntity>>() {}.getType());
        List<ReseniaEntity> result = premioReseniaService.replaceResenias(premioId, reseniaEntities);
        return modelMapper.map(result, new TypeToken<List<ReseniaDetailDTO>>() {}.getType());
    }
}
