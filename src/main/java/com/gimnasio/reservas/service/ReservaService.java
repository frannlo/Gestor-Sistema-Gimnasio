package com.gimnasio.reservas.service;

import com.gimnasio.reservas.Enums.Estado;
import com.gimnasio.reservas.Enums.Nivel;
import com.gimnasio.reservas.model.*;
import com.gimnasio.reservas.repository.ClienteRepository;
import com.gimnasio.reservas.repository.InstructorRepository;
import com.gimnasio.reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.*;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final InstructorRepository instructorRepository;
}
