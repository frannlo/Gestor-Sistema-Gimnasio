package com.gimnasio.reservas.controller;

import com.gimnasio.reservas.model.Reserva;
import com.gimnasio.reservas.dto.ReservaDto;
import com.gimnasio.reservas.service.ReservaService;
import com.gimnasio.reservas.Enums.Estado;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import lombok.*;
import java.util.List;
import java.time.*;


@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {    
    private final ReservaService reservaService;

    @GetMapping
    public List<Reserva> listar(){
        return reservaService.listar();
    }
    @GetMapping("/cliente/{dni}")
    public List<Reserva> listarPorCliente(@PathVariable String dni){
        return reservaService.listarPorCliente(dni);
    }
    @GetMapping("/estado/{estado}")
    public List<Reserva> listarPorEstado(@PathVariable Estado estado){
        return reservaService.listarPorEstado(estado);
    }
    @GetMapping("/fechas")
    public List<Reserva> listarPorRango(@RequestParam String desde, @RequestParam String hasta){
        return reservaService.listarPorRango(LocalDateTime.parse(desde), LocalDateTime.parse(hasta));
    }
    @PostMapping
    public ResponseEntity<Reserva> crear(@Valid @RequestBody ReservaDto dto){
        var nuevaReserva = reservaService.crear(dto.clienteDni(), dto.fechaHora(), dto.instructorDni());
        return ResponseEntity.created(URI.create("/api/reservas/" + nuevaReserva.getId())).body(nuevaReserva);
    }
    @PutMapping("/{id}/cancelar")
    public Reserva cancelar(@PathVariable Long id){
        return reservaService.cancelar(id);
    }
    @PutMapping("/{id}/asistencia")
    public Reserva marcarAsistencia(@PathVariable Long id){
        return reservaService.marcarAsistencia(id);
    }

}

