package co.edu.uniandes.dse.premiospeliculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.premiospeliculas.entities.CinefiloEntity;


@Repository
public interface CinefiloRepository extends JpaRepository<CinefiloEntity, Long> {
    
}
