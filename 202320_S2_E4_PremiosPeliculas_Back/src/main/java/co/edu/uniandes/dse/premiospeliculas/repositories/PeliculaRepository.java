package co.edu.uniandes.dse.premiospeliculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;

@Repository
public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Long>{
    
}
