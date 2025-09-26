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
public class ClienteService{
    private final ClienteRepository clienteRepository;
    //Método para listar todos los clientes
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }
    //Método para obtener un cliente por su DNI
    public Cliente buscarPorDni(String dni){
        return clienteRepository.findById(dni)
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente con el DNI: " + dni + " no encontrado"));
    }
    //Método para crear un nuevo cliente
    @Transactional
    public Cliente crearCliente(ClienteDto dto){
        try{
            if(clienteRepository.findById(dto.correo()).isPresent()){
                throw new ClienteExistenteException("Ya existe un cliente con el correo: " + dto.correo());
            }
            Cliente cliente = Cliente.builder()
                    .dni(dto.dni())
                    .nombre(dto.nombre())
                    .apellido(dto.apellido())
                    .correo(dto.correo())
                    .telefono(dto.telefono())
                    .pinAcceso(dto.pinAcceso())
                    .nivel(null) // nivel, plan, instructor se setean después
                    .plan(null)
                    .instructor(null)
                    .build();

            return clienteRepository.save(cliente);

        } catch (DataIntegrityViolationException e) {
            throw new ClienteExistenteException("Conflicto de datos: " + e.getMessage());
        }   
    }
    // Método para eliminar un cliente
    @Transactional
    public void eliminarCliente(String dni){
        if(!clienteRepository.existsById(dni)){
            throw new ClienteNoEncontradoException("Cliente con el DNI: " + dni + " no encontrado");
        }
        clienteRepository.deleteById(dni);
    }
} 