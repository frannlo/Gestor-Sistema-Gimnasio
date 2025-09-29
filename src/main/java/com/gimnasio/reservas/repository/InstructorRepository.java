package com.gimnasio.reservas.repository;

import com.gimnasio.reservas.model.Instructor;
import com.gimnasio.reservas.Enums.TipoEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, String> {
    List<Instructor> findByTipoEntrenamiento(TipoEntrenamiento tipoEntrenamiento);
}