package com.gimnasio.reservas.repository;

import com.gimnasio.reservas.model.Reserva;
import com.gimnasio.reservas.model.Cliente;
import com.gimnasio.reservas.Enums.Estado;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCliente(Cliente cliente);
    List<Reserva> findByEstado(Estado estado);
    List<Reserva> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}

