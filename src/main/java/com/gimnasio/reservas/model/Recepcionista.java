package com.gimnasio.reservas.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recepcionista {
    @Id
    private String usuario; //Puede ser correo o usarname
    private String contraseña; //Hasheada
    private String nombre;
    private String apellido;
}
