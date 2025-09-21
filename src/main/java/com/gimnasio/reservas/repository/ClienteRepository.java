package com.gimnasio.reservas.repository;

import com.gimnasio.reservas.model.Cliente;
import com.gimnasio.reservas.Enums.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    //Métodos personalizados no incluidios en JpaRepository
    List<Cliente> findByNivel(Nivel nivel);
    Cliente findByCorreo(String correo);
}
