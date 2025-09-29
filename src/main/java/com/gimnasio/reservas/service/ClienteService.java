package com.gimnasio.reservas.service;

import com.gimnasio.reservas.dto.ClienteDto;
import com.gimnasio.reservas.exception.ClienteExistenteException;
import com.gimnasio.reservas.exception.ClienteNoEncontradoException;
import com.gimnasio.reservas.model.Cliente;
import com.gimnasio.reservas.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorDni(String dni) {
        return clienteRepository.findById(dni)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente con DNI " + dni + " no encontrado"));
    }

    @Transactional
    public Cliente crearCliente(ClienteDto dto) {
        try {
            if (clienteRepository.findByCorreo(dto.correo()) != null) {
                throw new ClienteExistenteException("Ya existe un cliente con el correo: " + dto.correo());
            }

            Cliente cliente = Cliente.builder()
                    .dni(dto.dni())
                    .nombre(dto.nombre())
                    .apellido(dto.apellido())
                    .correo(dto.correo())
                    .telefono(dto.telefono())
                    .pinAcceso(dto.pinAcceso())
                    .nivel(null)
                    .plan(null)
                    .instructor(null)
                    .build();

            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            // si la unicidad la hace la BD, capturamos este conflicto
            throw new ClienteExistenteException("Conflicto de datos (posible correo duplicado): " + e.getMostSpecificCause().getMessage());
        }
    }

    @Transactional
    public void eliminarCliente(String dni) {
        if (!clienteRepository.existsById(dni)) {
            throw new ClienteNoEncontradoException("Cliente con DNI " + dni + " no encontrado");
        }
        clienteRepository.deleteById(dni);
    }
}
