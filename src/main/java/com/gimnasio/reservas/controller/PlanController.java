package com.gimnasio.reservas.controller;

import com.gimnasio.reservas.model.Plan;
import com.gimnasio.reservas.service.PlanService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import lombok.*;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @GetMapping
    public List<Plan> listar() {
        return planService.listarPlanes();
    }

    @GetMapping("/{id}")
    public Plan buscar(@PathVariable Long id) {
        return planService.buscarPorId(id);
    }
    @PostMapping
    public ResponseEntity<Plan> crear(@RequestBody Plan plan){
        var nuevoPlan = planService.crearPlan(plan);
        return ResponseEntity.created(URI.create("/api/planes/" + nuevoPlan.getIdPlan())).body(nuevoPlan);
    }
    @PutMapping("/{id}")
    public Plan editar (@PathVariable Long id, @RequestBody Plan cambios) {
        return planService.editarPlan(id, cambios);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminarPlan(id);
        return ResponseEntity.noContent().build();
    }

}
