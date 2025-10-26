package co.edu.uniandes.dse.premiospeliculas.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {
    
}
