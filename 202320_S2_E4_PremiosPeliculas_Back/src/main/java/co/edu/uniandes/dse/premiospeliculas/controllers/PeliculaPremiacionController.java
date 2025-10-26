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

import co.edu.uniandes.dse.premiospeliculas.dto.PremiacionDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.PremiacionDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PeliculaPremiacionService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaPremiacionController {
    @Autowired
	private PeliculaPremiacionService peliculaPremiacionService;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{peliculaId}/premiaciones/{premiacionId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PremiacionDetailDTO addPremiacion(@PathVariable("premiacionId") Long premiacionId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException { 
		PremiacionEntity premiacionEntity = this.peliculaPremiacionService.addPremiacion(peliculaId, premiacionId);
		return modelMapper.map(premiacionEntity, PremiacionDetailDTO.class);
	}


	@GetMapping(value = "/{peliculaId}/premiaciones/{premiacionId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PremiacionDetailDTO getPremiacion(@PathVariable("premiacionId") Long premiacionId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException, IllegalOperationException {
		PremiacionEntity premiacionEntity = this.peliculaPremiacionService.getPremiacion(peliculaId, premiacionId);
		return modelMapper.map(premiacionEntity, PremiacionDetailDTO.class);
	}


	@PutMapping(value = "/{peliculaId}/premiaciones")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PremiacionDetailDTO> addPremiaciones(@PathVariable("peliculaId") Long peliculaId, @RequestBody List<PremiacionDTO> premiaciones)
			throws EntityNotFoundException {
		List<PremiacionEntity> entities = modelMapper.map(premiaciones, new TypeToken<List<PremiacionEntity>>() {
		}.getType());
		List<PremiacionEntity> premiacionesList = this.peliculaPremiacionService.replacePremiaciones(peliculaId, entities);
		return modelMapper.map(premiacionesList, new TypeToken<List<PremiacionDetailDTO>>() {
		}.getType());
	}


	@GetMapping(value = "/{peliculaId}/premiaciones")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PremiacionDetailDTO> getPremiaciones(@PathVariable("peliculaId") Long peliculaId) throws EntityNotFoundException {
		List<PremiacionEntity> premiacionEntity = this.peliculaPremiacionService.getPremiaciones(peliculaId);
		return modelMapper.map(premiacionEntity, new TypeToken<List<PremiacionDetailDTO>>() {
		}.getType());
	}


	@DeleteMapping(value = "/{peliculaId}/premiaciones/{premiacionId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePremiacion(@PathVariable("premiacionId") Long premiacionId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		this.peliculaPremiacionService.removePremiacion(peliculaId, premiacionId);
	}
    
}
