package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.NominadoEntity;
//import co.edu.uniandes.dse.premiospeliculas.entities.OrganizacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
//import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.NominadoRepository;
//import co.edu.uniandes.dse.premiospeliculas.repositories.OrganizacionRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremiacionRepository;
//import co.edu.uniandes.dse.premiospeliculas.repositories.PremioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Hace un log de los eventos que ocurren en la aplicación
@Service // Indica que esta clase es un servicio
public class PremiacionNominadoService {

    @Autowired
    private PremiacionRepository premiacionRepository;

    @Autowired
    private NominadoRepository nominadoRepository;

    @Transactional // Indica que es una transacción de la base de datos
    public NominadoEntity addNominado(Long premiacionId, Long nominadoId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociarle un nominado a la premiacion con id = {0}", premiacionId);
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);
        Optional<NominadoEntity> nominadoEntity = nominadoRepository.findById(nominadoId);

        if (premiacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una premiacion con ese Id");
        }

        if (nominadoEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe un nominado con ese Id");
        }

        nominadoEntity.get().setPremiacion(premiacionEntity.get());
        log.info("Termina proceso de asociarle un nominado a la premiacion con id = {0}", premiacionId);
        return nominadoEntity.get();
    }

    @Transactional
    public List<NominadoEntity> getNominados(Long premiacionId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos los nominados de la premiacion con id = {0}", premiacionId);
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);
        if (premiacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una premiacion con ese Id");
        }
        log.info("Termina proceso de consultar todos los nominados de la premiacion con id = {0}", premiacionId);
        return premiacionEntity.get().getNominados();
    }

    @Transactional
    public NominadoEntity getNominado(Long premiacionId, Long nominadoId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar el premio con id = {0} de la organizacion con id = " + nominadoId,
                premiacionId);
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);
        Optional<NominadoEntity> nominadoEntity = nominadoRepository.findById(nominadoId);

        if (premiacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una premiacion con ese Id");
        }

        if (nominadoEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe un nominado con ese Id");
        }

        log.info("Termina proceso de consultar el nominado con id = {0} de la premiacion con id = " + nominadoId,
                premiacionId);
        if (!premiacionEntity.get().getNominados().contains(nominadoEntity.get())) {
            throw new IllegalOperationException("El nominado no está asociado a la premiacion");
        }

        return nominadoEntity.get();
    }

    @Transactional
    public List<NominadoEntity> replaceNominados(Long premiacionId, List<NominadoEntity> nominados)
            throws EntityNotFoundException {
        log.info("Inicia el proceso de remplazar los nominados asociados a la premiacion con id = {0}", premiacionId);
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);
        if (premiacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una premiacion con ese Id");
        }

        for (NominadoEntity nominado : nominados) {
            Optional<NominadoEntity> nominadoEntity = nominadoRepository.findById(nominado.getId());
            if (nominadoEntity.isEmpty()) {
                throw new EntityNotFoundException("No existe un nominado con ese Id");
            }
            if (!premiacionEntity.get().getNominados().contains(nominadoEntity.get())) {
                premiacionEntity.get().getNominados().add(nominadoEntity.get());
            }
        }
        log.info("Termina el proceso de remplazar los nominados asociados a la premiacion con id = {0}",
                premiacionId);
        premiacionEntity.get().setNominados(nominados);
        return premiacionEntity.get().getNominados();
    }

    @Transactional
    public void removeNominado(Long premiacionId, Long nominadoId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar un nominado de la premiacion con id = {0}", premiacionId);
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);

        if (premiacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una premiacion con ese Id");
        }
        Optional<NominadoEntity> nominadoEntity = nominadoRepository.findById(nominadoId);
        if (nominadoEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe un nominado con ese Id");
        }
        nominadoEntity.get().setPremiacion(null);
        premiacionEntity.get().getNominados().remove(nominadoEntity.get());
        log.info("Termina proceso de borrar un nominado de la premiacion con id = {0}", premiacionId);
    }

}
