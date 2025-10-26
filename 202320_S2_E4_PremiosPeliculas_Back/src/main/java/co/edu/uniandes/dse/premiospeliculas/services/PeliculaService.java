package co.edu.uniandes.dse.premiospeliculas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.PeliculaRepository;

@Service
public class PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    public List<PeliculaEntity> getPeliculas(){
        return this.peliculaRepository.findAll();
        
    }

    public PeliculaEntity getPelicula(Long id) throws EntityNotFoundException{
        Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(id);
        if (peliculaEntity.isEmpty()){
            throw new EntityNotFoundException(" Pelicula no encontrada");
        }
        return peliculaEntity.get();
        
    }

    public PeliculaEntity createPelicula(PeliculaEntity pelicula) throws IllegalOperationException{
        if(pelicula.getNombre() == null ){
            throw new IllegalOperationException("El nombre de la pelicula no puede ser nulo");
        }
        if (pelicula.getNombre().length() == 0){
            throw new IllegalOperationException("El nombre de la película debe tener al menos un caracter");
        }
        if (pelicula.getPais() == null){
            throw new IllegalOperationException("El nombre del país no puede ser nulo");
        }
        if (pelicula.getPais().length() < 4){
            throw new IllegalOperationException("El nombre del país debe ser mayor o igual a 4 caracteres");
        }
        if (pelicula.getIdiomaOriginal() == null ){
            throw new IllegalOperationException ("El idioma no puede ser nulo");
        }
       
        if  (pelicula.getFechaEstreno() == null){
            throw new IllegalOperationException ("La fecha de estreno no puede ser nula");
        }
        
        if  (pelicula.getLinkTrailer() == null){
            throw new IllegalOperationException ("El trailer no puede ser nulo");
        }


        
        return pelicula = this.peliculaRepository.save(pelicula);
    }
        
    

    public void deletePelicula (Long id) throws EntityNotFoundException{
        Optional<PeliculaEntity> peliculaEntity = this.peliculaRepository.findById(id);
        if (peliculaEntity.isEmpty()){
            throw new EntityNotFoundException ("pelicula no encontrada");
         }

        this.peliculaRepository.deleteById(id);
        
    }

    public PeliculaEntity updatePelicula(Long id, PeliculaEntity pelicula) throws EntityNotFoundException, IllegalOperationException{
        Optional<PeliculaEntity> peliculaEntity = peliculaRepository.findById(id);
        if (peliculaEntity.isEmpty()){
            throw new EntityNotFoundException("Peliucla no encontrada");
        }

        else if (pelicula.getNombre() == null){
            throw new IllegalOperationException("El nombre de la peli no puede ser nulo");
        }

        else if (pelicula.getNombre().length() == 0){
            throw new IllegalOperationException("El nombre de la película debe tener al menos un caracter");
        }
        else if (pelicula.getPais() == null){
            throw new IllegalOperationException("El nombre del país no puede ser nulo");
        }
        else if (pelicula.getPais().length() < 4){
            throw new IllegalOperationException("El nombre del país debe ser mayor o igual a 4 caracteres");
        }
        else if (pelicula.getIdiomaOriginal() == null ){
            throw new IllegalOperationException ("El idioma no puede ser nulo");
        }
       
        else if  (pelicula.getFechaEstreno() == null){
            throw new IllegalOperationException ("La fecha de estreno no puede ser nula");
        }
        
        else if  (pelicula.getLinkTrailer() == null){
            throw new IllegalOperationException ("El trailer no puede ser nulo");
        }
        pelicula.setId(id);

        return this.peliculaRepository.save(pelicula);
    }


}

