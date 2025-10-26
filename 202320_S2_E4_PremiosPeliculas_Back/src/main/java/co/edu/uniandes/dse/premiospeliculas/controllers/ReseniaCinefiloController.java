package co.edu.uniandes.dse.premiospeliculas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.premiospeliculas.dto.CinefiloDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.ReseniaDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.services.ReseniaCinefiloService;

@RestController
@RequestMapping("/resenias")
public class ReseniaCinefiloController {
    @Autowired
	private ReseniaCinefiloService reseniacinefiloservice;
	@Autowired
	private ModelMapper modelMapper;

	@PutMapping(value = "/{reseniaId}/cinefilo")
	@ResponseStatus(code = HttpStatus.OK)
	public ReseniaDetailDTO replaceEditorial(@PathVariable("reseniaId") Long reseniaId, @RequestBody CinefiloDTO cinefiloDTO)
			throws EntityNotFoundException {
		ReseniaEntity reseniaEntity = reseniacinefiloservice.replaceCinefilo(reseniaId, cinefiloDTO.getId());
		return modelMapper.map(reseniaEntity, ReseniaDetailDTO.class);
	} 

}
