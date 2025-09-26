package com.gimnasio.reservas.controller;

import com.gimnasio.reservas.dto.ClienteDto;
import com.gimnasio.reservas.model.Cliente;
import com.gimnasio.reservas.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    //Endpoint para listar todos los clientes
    @GetMapping
    public List<Cliente> listar(){
        return clienteService.listarClientes();
    }

    //Endpoint para obtener un cliente por su DNI
    @GetMapping("/{dni}")
    public Cliente buscar(@PathVariable String dni){
        return clienteService.buscarPorDni(dni);
    }
    //Endpoint para crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody ClienteDto dto){
        Cliente creado = clienteService.crearCliente(dto); // Llamada al servicio para crear el cliente
        return ResponseEntity
                .created(URI.create("/api/clientes/" + creado.getDni()))
                .body(creado);
    }
    //Endpoint para borrar un cliente por su DNI
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminar(@PathVariable String dni){
        clienteService.eliminarCliente(dni);
        return ResponseEntity.noContent().build();
    }
}
