package com.gimnasio.reservas.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import com.gimnasio.reservas.Enums.Nivel;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    private String dni;

    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String correo;
    private String telefono;
    private String pinAcceso;

    @Enumerated(EnumType.STRING)
    private Nivel nivel; 

    @ManyToOne
    @JoinColumn(name = "planElegido")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
