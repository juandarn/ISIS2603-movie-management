package co.edu.uniandes.dse.premiospeliculas.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.repositories.ReseniaRepository;


@Service
public class ReseniaService {
    @Autowired
    private ReseniaRepository reseniaRepository;

    public List<ReseniaEntity> getResenias(){
        return reseniaRepository.findAll();
    }

    public ReseniaEntity CreateResenia(ReseniaEntity resenia) throws EntityNotFoundException,IllegalOperationException{
        if(resenia.getTexto().length()>1000){
            throw new IllegalOperationException("Comentario no válido");
        }
        if(resenia.getClasificacion() > 5){
             throw new IllegalOperationException("Comentario no válido");

        }
        resenia = reseniaRepository.save(resenia);
        return resenia;

    }


    public ReseniaEntity getResenia(Long id)throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(id);
        if (reseniaEntity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }
        return reseniaEntity.get();
        }

    public ReseniaEntity UpdateResenia(Long id,ReseniaEntity resenia)throws EntityNotFoundException, IllegalOperationException{
        Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(id);
        if (reseniaEntity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }
        resenia.setId(id);
        return reseniaRepository.save(resenia);

    }

    public void DeleteResenia(Long id)throws EntityNotFoundException{
        Optional<ReseniaEntity> reseniaEntity = reseniaRepository.findById(id);
        if (reseniaEntity.isEmpty()){
            throw new EntityNotFoundException("Resenia no encontrada");
        }
        reseniaRepository.deleteById(id);
    }
}