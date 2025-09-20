package com.gimnasio.reservas.model;

import jakarta.persistence.*;
import lombok.*;
import com.gimnasio.reservas.Enums.TipoEntrenamiento;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    private String dni;
    private String nombre;
    private String apellido;
    private TipoEntrenamiento tipoEntrenamiento;
    
    private String disponibilidad;
}
