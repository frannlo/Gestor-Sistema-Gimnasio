package com.gimnasio.reservas.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPlan;

    private String nombre;

    private double precio;

    private int duracionSemanal; // ejemplo: 3 o 5 veces por semana

    
}
