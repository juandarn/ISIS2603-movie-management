package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.OrganizacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.OrganizacionRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremioRepository;
import lombok.extern.slf4j.Slf4j;

/*
 * Clase que implementa la conexion con la persitencia para la relacion entre la entidad
 * Organizacion y Premio.
 */

@Slf4j // Hace un log de los eventos que ocurren en la aplicación
@Service // Indica que esta clase es un servicio
public class OrganizacionPremioService {
    @Autowired // Inyecta la dependencia de la clase OrganizacionService
    private OrganizacionRepository organizacionRepository;

    @Autowired // Inyecta la dependencia de la clase PremioService
    private PremioRepository premioRepository;

    /*
     * Asocia un premio existente a una organizacion
     * 
     * @param organizacionId Identificador de la instancia de Organizacion
     * 
     * @param premioId Identificador de la instancia de Premio
     * 
     * @return Instancia de PremioEntity que fue asociada a Organizacion
     */

    @Transactional // Indica que es una transacción de la base de datos
    public PremioEntity addPremio(Long organizacionId, Long premioId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociarle un premio a la organizacion con id = {0}", organizacionId);
        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(organizacionId);

        if (organizacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una organizacion con ese Id");
        }

        Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);
        if (premioEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe un premio con ese Id");
        }
        // System.out.println(organizacionEntity);
        premioEntity.get().setOrganizacion(organizacionEntity.get());
        log.info("Termina proceso de asociarle un premio a la organizacion con id = {0}", organizacionId);
        return premioEntity.get();
    }

    /*
     * Obtiene una coleccion de instancias de PremioEntity asociadas a una instancia
     * de Organizacion
     * 
     * @param organizacionId Identificador de la instancia de Organizacion
     * 
     * @return Coleccion de instancias de PremioEntity asociadas a la instancia de
     * Organizacion
     */

    @Transactional
    public List<PremioEntity> getPremios(Long organizacionId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos los premios de la organizacion con id = {0}", organizacionId);
        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(organizacionId);
        if (organizacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una organizacion con ese Id");
        }
        log.info("Termina proceso de consultar todos los premios de la organizacion con id = {0}", organizacionId);
        return organizacionEntity.get().getPremios();
    }

    /*
     * Obtiene una instancia de PremioEntity asociada a una instancia de
     * Organizacion
     * 
     * @param organizacionId Identificador de la instancia de Organizacion
     * 
     * @param premioId Identificador de la instancia de Premio
     * 
     * @return Instancia de PremioEntity asociada a la instancia de Organizacion
     */

    @Transactional
    public PremioEntity getPremio(Long organizacionId, Long premioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar el premio con id = {0} de la organizacion con id = " + premioId,
                organizacionId);
        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(organizacionId);
        Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);

        if (organizacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una organizacion con ese Id");
        }

        if (premioEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe un premio con ese Id");
        }

        log.info("Termina proceso de consultar el premio con id = {0} de la organizacion con id = " + premioId,
                organizacionId);
        if (!organizacionEntity.get().getPremios().contains(premioEntity.get())) {
            throw new IllegalOperationException("El premio no está asociado a la organizacion");
        }

        return premioEntity.get();
    }

    /*
     * Remplaza las instancias de Premio asociadas a una instancia de Organizacion
     * 
     * @param organizacionId Identificador de la instancia de Organizacion
     * 
     * @param list Coleccion de instancias de PremioEntity a asociar a instancia de
     * Organizacion
     * 
     * @return Nueva coleccion de PremioEntity asociada a la instancia de
     * Organizacion
     */
    @Transactional
    public List<PremioEntity> replacePremios(Long organizacionId, List<PremioEntity> premios)
            throws EntityNotFoundException {
        log.info("Inicia el proceso de remplazar los premios asociados a la organizacion con id = {0}", organizacionId);
        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(organizacionId);
        if (organizacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una organizacion con ese Id");
        }

        for (PremioEntity premio : premios) {
            Optional<PremioEntity> premioEntity = premioRepository.findById(premio.getId());
            if (premioEntity.isEmpty()) {
                throw new EntityNotFoundException("No existe un premio con ese Id");
            }
            if (!organizacionEntity.get().getPremios().contains(premioEntity.get())) {
                organizacionEntity.get().getPremios().add(premioEntity.get());
            }
        }
        log.info("Termina el proceso de remplazar los premios asociados a la organizacion con id = {0}",
                organizacionId);
        organizacionEntity.get().setPremios(premios);
        return organizacionEntity.get().getPremios();
    }

    /*
     * Desasocia un Premio existente de un Organizacion existente
     * 
     * @param organizacionId Identificador de la instancia de Organizacion
     * 
     * @param premioId Identificador de la instancia de Premio
     */
    @Transactional
    public void removePremio(Long organizacionId, Long premioId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar un premio de la organizacion con id = {0}", organizacionId);
        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(organizacionId);

        if (organizacionEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe una organizacion con ese Id");
        }
        Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);
        if (premioEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe un premio con ese Id");
        }
        premioEntity.get().setOrganizacion(null);
        organizacionEntity.get().getPremios().remove(premioEntity.get());
        log.info("Termina proceso de borrar un premio de la organizacion con id = {0}", organizacionId);
    }
}
