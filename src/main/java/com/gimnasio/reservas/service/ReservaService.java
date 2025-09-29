package com.gimnasio.reservas.service;

import com.gimnasio.reservas.Enums.Estado;
import com.gimnasio.reservas.Enums.Nivel;
import com.gimnasio.reservas.model.Cliente;
import com.gimnasio.reservas.model.Instructor;
import com.gimnasio.reservas.model.Reserva;
import com.gimnasio.reservas.repository.ClienteRepository;
import com.gimnasio.reservas.repository.InstructorRepository;
import com.gimnasio.reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final InstructorRepository instructorRepository;

    private static final int CUPO_MAXIMO = 20;

    //Método para listar las reservas
    public List<Reserva> listar(){
        return reservaRepository.findAll();
    }
    //Método para listar las reservas por cliente
    public List<Reserva> listarPorCliente(String dni){
        Cliente c = clienteRepository.findById(dni).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return reservaRepository.findByCliente(c);
    }
    //Método para listar las reservas por estado
    public List<Reserva> listarPorEstado(Estado estado){
        return reservaRepository.findByEstado(estado);
    }

    //Método para listar reservas por rango
    public List<Reserva> listarPorRango(LocalDateTime desde, LocalDateTime hasta){
        return reservaRepository.findByFechaHoraBetween(desde, hasta);
    }
    //Método para crear una reserva
    @Transactional
    public Reserva crear(String clienteDni, LocalDateTime fechaHora, String instructorDni){
        //1. Verificar que el cliente existe
        Cliente cliente = clienteRepository.findById(clienteDni).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        //2. Verificar día (no domingo) )
        if(fechaHora.getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new RuntimeException("No se pueden hacer reservas los domingos");
        }

        //3. Verificar que la hora es entre 6 y 22
        int hora = fechaHora.getHour();
        if(hora < 6 || hora > 22){
            throw new RuntimeException("Horario inválido: el gimnasio abre de 06:00 a 22:00.");
        }

        //4. Verificar bloque de 2 horas
        if(hora % 2 != 0){
            throw new RuntimeException("Las reservas deben hacerse en bloques de 2 horas (ej: 06:00, 08:00, 10:00, etc.)");
        }

        //5. Validar instructor
        Instructor instructor = null;
        if(instructorDni != null && !instructorDni.isBlank()){
            if(cliente.getNivel() != Nivel.PRINCIPIANTE){
                throw new IllegalStateException("Solo los clientes principiantes pueden seleccionar instructor");
            }
            instructor = instructorRepository.findById(instructorDni).orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
        }

        //6. Validar el plan del cliente
        if(cliente.getPlan() == null || cliente.getPlan().getFrecuenciaSemanal() <= 0){
            throw new RuntimeException("El cliente no tiene un plan activo con frecuencia semanal.");
        }

        //7. Validar límite semanal
        LocalDate lunes = fechaHora.toLocalDate().with(DayOfWeek.MONDAY);
        LocalDate sabado = lunes.plusDays(5);
        LocalDateTime desdeSemana = lunes.atStartOfDay();
        LocalDateTime hastaSemana = sabado.atTime(23, 59, 59);

        long reservasSemana =reservaRepository
            .findByClienteAndFechaHoraBetween(cliente, desdeSemana, hastaSemana)
            .stream()
            .filter(r -> r.getEstado() == Estado.RESERVADO || r.getEstado() == Estado.ASISTIDO)
            .count();
        
            if(reservasSemana >= cliente.getPlan().getFrecuenciaSemanal()){
                throw new RuntimeException("El cliente ha alcanzado el límite de reservas para esta semana.");
            }
        //8. Validar cupo máximo
        LocalDateTime inicioTurno = fechaHora.withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finTurno = inicioTurno.plusHours(2);

        long reservasTurno = reservaRepository
            .findByFechaHoraBetween(inicioTurno, finTurno)
            .stream()
            .filter(r -> r.getEstado() == Estado.RESERVADO || r.getEstado() == Estado.ASISTIDO)
            .count();

        if(reservasTurno >= CUPO_MAXIMO){
            throw new RuntimeException("Cupo máximo alcanzado para el turno seleccionado.");
        }

        //9. Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setFechaHora(fechaHora);
        reserva.setInstructor(instructor); // Puede ser null
        reserva.setEstado(Estado.RESERVADO);

        return reservaRepository.save(reserva);
        }

        @Transactional
        public Reserva cancelar(Long reservaId){
            Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
            reserva.setEstado(Estado.CANCELADO);
            return reservaRepository.save(reserva);
        }

        @Transactional
        public Reserva marcarAsistencia(Long reservaId){
            Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
            reserva.setEstado(Estado.ASISTIDO);
            return reservaRepository.save(reserva);
        }
        // 10. Check-in por PIN/DNI (método adicional)
        @Transactional
        public Reserva checkIn(String clienteDni, LocalDateTime fechaHora){
            Cliente cliente = clienteRepository.findById(clienteDni)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            
            LocalDateTime inicioTurno = fechaHora.withMinute(0).withSecond(0).withNano(0);
            LocalDateTime finTurno = inicioTurno.plusHours(2);

            Reserva reserva = reservaRepository.findByClienteAndFechaHoraBetween(cliente, inicioTurno, finTurno)
                .stream()
                .filter(r -> r.getEstado() == Estado.RESERVADO)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay reserva activa para este cliente en este turno."));
            reserva.setEstado(Estado.ASISTIDO);
            return reservaRepository.save(reserva);
        } 
}

