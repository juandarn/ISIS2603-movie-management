package co.edu.uniandes.dse.premiospeliculas.controllers;


    /*
MIT License

Copyright (c) 2021 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/


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
import co.edu.uniandes.dse.premiospeliculas.services.CinefiloReseniaService;




@RestController
@RequestMapping("/cinefilos")
public class CinefiloReseniaController {

	@Autowired
	private CinefiloReseniaService cinefiloReseniaService;

	@Autowired
	private ModelMapper modelMapper;

	
	@PostMapping(value = "/{cinefiloId}/resenias/{reseniaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReseniaDTO addBook(@PathVariable("cinefiloId") Long cinefiloId, @PathVariable("reseniaId") Long reseniaId)
			throws EntityNotFoundException {
		ReseniaEntity reseniaEntity = this.cinefiloReseniaService.addResenia(cinefiloId, reseniaId);
		return modelMapper.map(reseniaEntity, ReseniaDTO.class);
	}

	
	@GetMapping(value = "/{cinefiloId}/resenias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ReseniaDetailDTO> getResenias(@PathVariable("cinefiloId") Long cinefiloId) throws EntityNotFoundException {
		List<ReseniaEntity> bookList = this.cinefiloReseniaService.getResenias(cinefiloId);
		return modelMapper.map(bookList, new TypeToken<List<ReseniaDetailDTO>>() {
		}.getType());
	}

	
	@GetMapping(value = "/{cinefiloId}/resenias/{reseniaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ReseniaDetailDTO getResenia(@PathVariable("cinefiloId") Long cinefiloId, @PathVariable("reseniaId") Long reseniaId) throws EntityNotFoundException, IllegalOperationException {
		ReseniaEntity reseniaEntity = this.cinefiloReseniaService.getResenia(cinefiloId, reseniaId);
		return modelMapper.map(reseniaEntity, ReseniaDetailDTO.class);
	}

	
	@PutMapping(value = "/{cinefiloId}/resenias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ReseniaDetailDTO> replaceResenias(@PathVariable("cinefiloId") Long cinefilosId, @RequestBody List<ReseniaDetailDTO> resenias) throws EntityNotFoundException {
		List<ReseniaEntity> reseniasList = modelMapper.map(resenias, new TypeToken<List<ReseniaEntity>>() {
		}.getType());
		List<ReseniaEntity> result = this.cinefiloReseniaService.replaceResenias(cinefilosId, reseniasList);
		return modelMapper.map(result, new TypeToken<List<ReseniaDetailDTO>>() {
		}.getType());
	}
}
