package com.gimnasio.reservas.repository;

import com.gimnasio.reservas.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findByTipoEntrenamiento(String tipoEntrenamiento);
}