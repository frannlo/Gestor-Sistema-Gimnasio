package com.gimnasio.reservas.model;

import com.gimnasio.reservas.Enums.Estado;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_dni", nullable = false) //null  porque un cliente puede no tener reserva
    private Cliente cliente;

    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    //Instructor puede ser null si el cliente es avanzado
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

}
