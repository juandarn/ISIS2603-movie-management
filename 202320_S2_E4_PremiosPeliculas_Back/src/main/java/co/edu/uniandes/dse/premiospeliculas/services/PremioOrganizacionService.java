package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.Optional;

// import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.premiospeliculas.entities.OrganizacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.repositories.OrganizacionRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PremioOrganizacionService {

    @Autowired
    private PremioRepository premioRepository;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Transactional
    public PremioEntity replaceOrganizacion(Long premioId, Long OrganizacionId) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar libro con id = {0}", premioId);
        Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);
        if (premioEntity.isEmpty())
            throw new EntityNotFoundException("No existe premio con ese id");

        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository.findById(OrganizacionId);
        if (organizacionEntity.isEmpty())
            throw new EntityNotFoundException("No existe organizacion con ese id");

        premioEntity.get().setOrganizacion(organizacionEntity.get());
        log.info("Termina proceso de actualizar libro con id = {0}", premioId);

        return premioEntity.get();
    }

    @Transactional
    public void removeOrganizacion(Long premioId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar la Editorial del libro con id = {0}", premioId);
        Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);
        if (premioEntity.isEmpty())
            throw new EntityNotFoundException("No existe premio con ese id");

        Optional<OrganizacionEntity> organizacionEntity = organizacionRepository
                .findById(premioEntity.get().getOrganizacion().getId());
        organizacionEntity.ifPresent(organizacion -> organizacion.getPremios().remove(premioEntity.get()));

        premioEntity.get().setOrganizacion(null);
        log.info("Termina proceso de borrar la Editorial del libro con id = {0}", premioId);
    }

}
