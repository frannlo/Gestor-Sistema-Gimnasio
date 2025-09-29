package com.gimnasio.reservas.controller;

import com.gimnasio.reservas.model.Instructor;
import com.gimnasio.reservas.service.InstructorService;
import com.gimnasio.reservas.Enums.TipoEntrenamiento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.*;
import java.util.List;

@RestController
@RequestMapping("/api/instructores")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @GetMapping 
    public List<Instructor> listar(){
        return instructorService.listarInstructores();
    }
    @GetMapping("/{dni}")
    public Instructor buscar(@PathVariable String dni){
        return instructorService.buscarPorDni(dni);
    }
    @PostMapping 
    public Instructor crear(@RequestBody Instructor instructor){
        return instructorService.crearInstructor(instructor);
    }
    @PutMapping("/{dni}")
    public Instructor editar(@PathVariable String dni, @RequestBody Instructor cambios){
        return instructorService.editarInstructor(dni, cambios);
    }
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminar(@PathVariable String dni){
        instructorService.eliminarInstructor(dni);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/tipo/{tipo}")
    public List<Instructor> listarPorTipo(@PathVariable TipoEntrenamiento tipo){
        return instructorService.listarPorTipo(tipo);
    }
}
