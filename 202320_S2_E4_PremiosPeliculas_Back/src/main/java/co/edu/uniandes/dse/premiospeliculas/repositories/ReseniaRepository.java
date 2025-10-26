package co.edu.uniandes.dse.premiospeliculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;

@Repository 
public interface ReseniaRepository extends JpaRepository<ReseniaEntity,Long> {
    
}
