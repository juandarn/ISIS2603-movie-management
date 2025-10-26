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
import co.edu.uniandes.dse.premiospeliculas.services.PremiacionService;

@RestController
@RequestMapping("/premiaciones")
public class PremiacionController {
    @Autowired
    private PremiacionService premiacionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PremiacionDetailDTO> findAll() {
        List<PremiacionEntity> premiaciones = premiacionService.getPremiaciones();
        return modelMapper.map(premiaciones, new TypeToken<List<PremiacionDetailDTO>>() {
        }.getType());

    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PremiacionDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        PremiacionEntity premiacion = premiacionService.getPremiacion(id);
        return modelMapper.map(premiacion, PremiacionDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PremiacionDTO create(@RequestBody PremiacionDTO premiacionDTO) throws IllegalOperationException {
        PremiacionEntity premiacionEntity = premiacionService
                .createPremiacion(modelMapper.map(premiacionDTO, PremiacionEntity.class));
        return modelMapper.map(premiacionEntity, PremiacionDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PremiacionDTO update(@PathVariable("id") Long id, @RequestBody PremiacionDTO premiacionDTO)
            throws EntityNotFoundException {
        PremiacionEntity premiacionEntity = premiacionService.updatePremiacion(id,
                modelMapper.map(premiacionDTO, PremiacionEntity.class));
        return modelMapper.map(premiacionEntity, PremiacionDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        premiacionService.deletePremiacion(id);
    }
}
