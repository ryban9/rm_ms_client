package ec.rmdetalles.clientes.repository;

import ec.rmdetalles.clientes.domain.FrontendNegocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FrontendNegocioRepository extends JpaRepository<FrontendNegocio, UUID> {
    boolean existsByOrigenAndActivoTrue(String origen);

    boolean existsByNegocio_CodigoAndOrigenAndActivoTrue(String negocioCodigo, String origen);
}
