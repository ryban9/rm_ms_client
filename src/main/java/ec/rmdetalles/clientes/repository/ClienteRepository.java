package ec.rmdetalles.clientes.repository;

import ec.rmdetalles.clientes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findByNegocio_IdOrderByNombresAsc(UUID negocioId);
    List<Cliente> findByNegocio_IdAndActivoTrueOrderByNombresAsc(UUID negocioId);
    java.util.Optional<Cliente> findByIdAndNegocio_Id(UUID id, UUID negocioId);
}
