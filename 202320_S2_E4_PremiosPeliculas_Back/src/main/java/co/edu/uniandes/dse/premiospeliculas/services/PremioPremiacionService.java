package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PremiacionEntity;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremiacionRepository;
import co.edu.uniandes.dse.premiospeliculas.repositories.PremioRepository;

@Service
public class PremioPremiacionService {
    @Autowired
	private PremiacionRepository premiacionRepository;

	@Autowired
	private PremioRepository premioRepository;

    @Transactional
	public PremioEntity replacePremiacion(Long premioId, Long premiacionId) throws EntityNotFoundException{
        Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);
        if(premioEntity.isEmpty()){
            throw new EntityNotFoundException("Premio no encontrado");
        }
        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premiacionId);
        if(premiacionEntity.isEmpty()){
            throw new EntityNotFoundException("Premiacion no encontrada");
        }
        premioEntity.get().setPremiacion(premiacionEntity.get());
        
        return premioEntity.get();
    }

    public void removePremiacion(Long premioId) throws EntityNotFoundException{
        Optional<PremioEntity> premioEntity = premioRepository.findById(premioId);

        if(premioEntity.isEmpty()){
            throw new EntityNotFoundException("Premio no encontrado");
        }

        Optional<PremiacionEntity> premiacionEntity = premiacionRepository.findById(premioEntity.get().getPremiacion().getId());
        premiacionEntity.ifPresent(premiacion -> premiacion.getPremios().remove(premioEntity.get()));

        premioEntity.get().setPremiacion(null);
    }
    
}
