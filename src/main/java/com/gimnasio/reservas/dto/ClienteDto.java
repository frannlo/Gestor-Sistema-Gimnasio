package com.gimnasio.reservas.dto;

import jakarta.validation.constraints.*;


public record ClienteDto(
    @NotBlank(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe contener solo dígitos y entre 7 y 8 caracteres")
    String dni,
    
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    String apellido,
    
    @Email(message = "El correo debe tener un formato válido")
    @NotBlank(message = "El correo es obligatorio")
    String correo,

    String telefono,

    @NotNull(message = "El PIN de acceso es obligatorio")
    String pinAcceso
){}
