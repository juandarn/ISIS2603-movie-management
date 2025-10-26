package co.edu.uniandes.dse.premiospeliculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;

@Repository
public interface GeneroRepository extends JpaRepository<GeneroEntity,Long>{
    
}
