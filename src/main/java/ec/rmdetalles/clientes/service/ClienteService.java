package ec.rmdetalles.clientes.service;

import ec.rmdetalles.clientes.api.ClienteRequest;
import ec.rmdetalles.clientes.api.ClienteResponse;
import ec.rmdetalles.clientes.config.NegocioContext;
import ec.rmdetalles.clientes.domain.Cliente;
import ec.rmdetalles.clientes.repository.ClienteRepository;
import ec.rmdetalles.clientes.repository.NegocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ClienteService {
    private final ClienteRepository clientes;
    private final NegocioRepository negocios;

    public ClienteService(ClienteRepository clientes, NegocioRepository negocios) {
        this.clientes = clientes;
        this.negocios = negocios;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar(boolean soloActivos) {
        UUID negocioId = NegocioContext.getRequired();
        List<Cliente> lista = soloActivos
                ? clientes.findByNegocio_IdAndActivoTrueOrderByNombresAsc(negocioId)
                : clientes.findByNegocio_IdOrderByNombresAsc(negocioId);
        return lista.stream().map(ClienteResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtener(UUID id) {
        return ClienteResponse.from(buscar(id));
    }

    public ClienteResponse crear(ClienteRequest request) {
        UUID negocioId = NegocioContext.getRequired();
        Cliente cliente = new Cliente(negocios.getReferenceById(negocioId), limpiar(request.nombres()), limpiarOpcional(request.telefono()),
                limpiarOpcional(request.correo()), limpiarOpcional(request.direccion()),
                limpiarOpcional(request.observaciones()));
        if (Boolean.FALSE.equals(request.activo())) cliente.desactivar();
        return ClienteResponse.from(clientes.saveAndFlush(cliente));
    }

    public ClienteResponse actualizar(UUID id, ClienteRequest request) {
        Cliente cliente = buscar(id);
        cliente.actualizar(limpiar(request.nombres()), limpiarOpcional(request.telefono()),
                limpiarOpcional(request.correo()), limpiarOpcional(request.direccion()),
                limpiarOpcional(request.observaciones()), request.activo());
        return ClienteResponse.from(clientes.saveAndFlush(cliente));
    }

    public void desactivar(UUID id) {
        Cliente cliente = buscar(id);
        cliente.desactivar();
        clientes.save(cliente);
    }

    private Cliente buscar(UUID id) {
        return clientes.findByIdAndNegocio_Id(id, NegocioContext.getRequired())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el cliente."));
    }

    private String limpiar(String valor) { return valor.trim(); }
    private String limpiarOpcional(String valor) {
        if (valor == null || valor.isBlank()) return null;
        return valor.trim();
    }
}
