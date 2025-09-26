package com.gimnasio.reservas.service;

import com.gimnasio.reservas.model.Instructor;
import com.gimnasio.reservas.Enums.TipoEntrenamiento;
import com.gimnasio.reservas.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public List<Instructor> listarInstructores(){
        return instructorRepository.findAll();
    }
    public Instructor buscarPorDni(String dni){
        return instructorRepository.findById(dni).orElse(null);
    }
    @Transactional
    public Instructor crearInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }
    @Transactional
    public Instructor editarInstructor(String dni, Instructor cambios){
        Instructor i = instructorRepository.findById(dni).orElseThrow(()-> new RuntimeException("Instructor no encontrado"));
        i.setNombre(cambios.getNombre());
        i.setApellido(cambios.getApellido());
        i.setTipoEntrenamiento(cambios.getTipoEntrenamiento());
        i.setDisponibilidad(cambios.getDisponibilidad());  
        return instructorRepository.save(i);     
    }

    @Transactional
    public void eliminarInstructor(String dni){
        instructorRepository.deleteById(dni);
    }
    @Transactional
    public List<Instructor> listarPorTipo(TipoEntrenamiento tipo){
        return instructorRepository.findByTipoEntrenamiento(tipo);
    }
}
