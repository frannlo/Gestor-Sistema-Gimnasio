package com.gimnasio.reservas.controller;

import com.gimnasio.reservas.model.Recepcionista;
import com.gimnasio.reservas.service.RecepcionistaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.*;
import java.util.List;


@RestController
@RequestMapping("/api/recepcionistas")
@RequiredArgsConstructor
public class RecepcionistaController {
    private final RecepcionistaService recepcionistaService;

    @GetMapping public List<Recepcionista> listar() { return recepcionistaService.listar(); }
    @PostMapping public Recepcionista crear(@RequestBody Recepcionista r) { return recepcionistaService.crear(r); }
    @DeleteMapping("/{usuario}") public ResponseEntity<Void> eliminar(@PathVariable String usuario) { recepcionistaService.eliminarRecepcionista(usuario); return ResponseEntity.noContent().build(); }

    public record LoginDto(String usuario, String password) {}
    @PostMapping("/login")
    public ResponseEntity<Recepcionista> login(@RequestBody LoginDto dto) {
        var r = recepcionistaService.login(dto.usuario(), dto.password());
        return (r == null) ? ResponseEntity.status(401).build() : ResponseEntity.ok(r);
    }
}
