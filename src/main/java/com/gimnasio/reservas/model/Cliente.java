package com.gimnasio.reservas.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    private int dni;

    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String pinAcceso;

    @Enumerated(EnumType.STRING)
    private Nivel nivel; // Cambiado a Enum

    @ManyToOne
    @JoinColumn(name = "planElegido")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
