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

//import co.edu.uniandes.dse.premiospeliculas.dto.GanadorDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.ReseniaDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.ReseniaDetailDTO;
//import co.edu.uniandes.dse.premiospeliculas.entities.GanadorEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.ReseniaService;

@RestController
@RequestMapping("/resenias")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ReseniaDetailDTO> findAll() {
        List<ReseniaEntity> resenias = reseniaService.getResenias();
        return modelMapper.map(resenias, new TypeToken<List<ReseniaDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReseniaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ReseniaEntity ganadorEntity = reseniaService.getResenia(id);
        return modelMapper.map(ganadorEntity, ReseniaDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReseniaDTO create(@RequestBody ReseniaDTO resenia)
            throws EntityNotFoundException, IllegalOperationException {
        ReseniaEntity reseniaEntity = reseniaService.CreateResenia(modelMapper.map(resenia, ReseniaEntity.class));
        return modelMapper.map(reseniaEntity, ReseniaDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ReseniaDTO update(@PathVariable("id") Long id, @RequestBody ReseniaDTO reseniaDTO)
            throws EntityNotFoundException, IllegalOperationException {
        ReseniaEntity reseniaEntity = reseniaService.UpdateResenia(id,
                modelMapper.map(reseniaDTO, ReseniaEntity.class));
        return modelMapper.map(reseniaEntity, ReseniaDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        reseniaService.DeleteResenia(id);
    }

}
