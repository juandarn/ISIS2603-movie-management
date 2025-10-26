package co.edu.uniandes.dse.premiospeliculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.premiospeliculas.entities.GanadorEntity;

@Repository 
public interface GanadorRepository extends JpaRepository<GanadorEntity,Long> {
    
}
