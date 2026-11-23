package ec.rmdetalles.clientes.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotBlank @Size(max = 160) String nombres,
        @Size(max = 30) String telefono,
        @Email @Size(max = 254) String correo,
        @Size(max = 5000) String direccion,
        @Size(max = 5000) String observaciones,
        Boolean activo
) { }
