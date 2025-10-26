package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.OrganizacionEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.OrganizacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrganizacionService {

    @Autowired
    OrganizacionRepository organizacionRepository;

    public OrganizacionEntity createOrganizacion(OrganizacionEntity organizacion) throws IllegalOperationException {
        log.info("Inicia proceso de creacion de la organizacion");

        if (organizacion.getNombre() == null || organizacion.getNombre().isEmpty()) {
            throw new IllegalOperationException("El nombre no es validó");
        }
        log.info("Termina proceso de creación de la organizacion");
        organizacion = organizacionRepository.save(organizacion);

        return organizacion;
    }

    public OrganizacionEntity getOrganizacion(Long id) throws EntityNotFoundException {
        log.info("Inicia el proceso de consultar una organizacion con id = {0}", id);
        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(id);
        if (organizacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una organizacion con ese Id");
        }
        log.info("Termina proceso de consultar una organizacion con el id = {0}", id);
        return organizacionEntity.get();
    }

    public List<OrganizacionEntity> getOrganizaciones() {
        log.info("Inicia proceso de consultar todas las organizaciones");
        return organizacionRepository.findAll();
    }

    public OrganizacionEntity updateOrganizacion(Long organizacionId, OrganizacionEntity organizacion)
            throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar una organizacion con id = {0}", organizacionId);
        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(organizacionId);
        if (organizacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una organizacion con ese Id");
        }

        organizacion.setId(organizacionId);
        log.info("Termina proceso de actualizar una organizacion con id = {0}", organizacionId);
        return organizacionRepository.save(organizacion);
    }

    public void deleteOrganizacion(Long id) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia el proceso de borrar una organizacion con id = {0}", id);
        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(id);
        if (organizacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una organizacion con ese Id");
        }

        List<PremioEntity> premios = organizacionEntity.get().getPremios();
        if (!premios.isEmpty()) {
            throw new IllegalOperationException("No se puede borrar la organizacion porque tiene premios asociados");
        }

        organizacionRepository.deleteById(id);
        log.info("Termina el proceso de borrar una organizacion con id = {0}", id);
    }
}
