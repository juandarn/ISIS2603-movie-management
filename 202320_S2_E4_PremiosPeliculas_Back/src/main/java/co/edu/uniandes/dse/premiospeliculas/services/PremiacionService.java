package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.GanadorEntity;
//import co.edu.uniandes.dse.premiospeliculas.entities.GanadorEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.NominadoEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremiacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PremiacionService {

    @Autowired
    PremiacionRepository premiacionRepository;

    public PremiacionEntity createPremiacion(PremiacionEntity premiacion) throws IllegalOperationException {
        log.info("Inicia proceso de creacion de la premiacion");

        if (premiacion.getCategoria() == null) {
            throw new IllegalOperationException("La categoria no es valida");
        }

        if (premiacion.getHistoria() == null) {
            throw new IllegalOperationException("La historia no es valida");
        }
        log.info("Termina proceso de creación de la premiacion");
        premiacion = premiacionRepository.save(premiacion);
        return premiacion;
    }

    public List<PremiacionEntity> getPremiaciones() {
        log.info("Inicia proceso de consultar todas las premiaciones");
        return premiacionRepository.findAll();
    }

    /**
     * @param premiacionId
     * @return
     */
    @Transactional
    public PremiacionEntity getPremiacion(Long premiacionId) throws EntityNotFoundException {
        log.info("Inicia el proceso de consultar una premiacion con id = {0}", premiacionId);
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);
        if (premiacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una premiacion con ese Id");
        }
        log.info("Termina proceso de consultar una premiacion con el id = {0}", premiacionId);
        return premiacionEntity.get();
    }

    public PremiacionEntity updatePremiacion(Long premiacionId, PremiacionEntity premiacion)
            throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar la premiacion con id = {0}", premiacionId);
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);
        if (premiacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una premiacion con el id");
        }

        premiacion.setId(premiacionId);
        log.info("Termina procesa de actualizar la premiacion con id = {0}", premiacionId);
        return premiacionRepository.save(premiacion);
    }

    public void deletePremiacion(Long premiacionId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar la premiacion con id = {0}", premiacionId);
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);
        if (premiacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una premiacion con ese id");
        }

        GanadorEntity ganador = premiacionEntity.get().getGanador();
        if (ganador != null) {
            throw new IllegalOperationException("No se puede borrar la premiacion porque tiene un ganador ligado");
        }

        List<NominadoEntity> nominados = premiacionEntity.get().getNominados();
        if (!nominados.isEmpty()) {
            throw new IllegalOperationException("No se puede borrar la premiacion porque tiene unos nominados ligados");
        }

        List<PeliculaEntity> peliculas = premiacionEntity.get().getPeliculas();
        if (!peliculas.isEmpty()) {
            throw new IllegalOperationException("No se puede borrar la premiacion porque tiene unas peliculas ligadas");
        }

        List<PremioEntity> premios = premiacionEntity.get().getPremios();
        if (!premios.isEmpty()) {
            throw new IllegalOperationException("No se puede borrar la premiacion porque tiene unos premios ligados");
        }

        premiacionRepository.deleteById(premiacionId);
        log.info("Termina el proceso de borrar la premiacion con id = {0}", premiacionId);
    }

}
