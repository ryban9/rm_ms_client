package ec.rmdetalles.clientes.api;

import ec.rmdetalles.clientes.domain.Cliente;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ClienteResponse(
        UUID id,
        String nombres,
        String telefono,
        String correo,
        String direccion,
        String observaciones,
        boolean activo,
        OffsetDateTime creadoEn,
        OffsetDateTime actualizadoEn
) {
    public static ClienteResponse from(Cliente cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNombres(), cliente.getTelefono(),
                cliente.getCorreo(), cliente.getDireccion(), cliente.getObservaciones(),
                cliente.isActivo(), cliente.getCreadoEn(), cliente.getActualizadoEn());
    }
}
