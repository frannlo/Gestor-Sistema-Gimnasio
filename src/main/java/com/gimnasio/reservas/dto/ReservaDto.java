package com.gimnasio.reservas.dto;

import jakarta.validation.constraints.*;
import java.time.*;

public record ReservaDto (
    @NotBlank 
    String clienteDni,
    @NotNull
    LocalDateTime fechaHora,
    String instructorDni // Opcional
) {}
