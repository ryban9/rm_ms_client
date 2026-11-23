package ec.rmdetalles.clientes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "frontends_negocio", schema = "rm_detalles")
public class FrontendNegocio {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @Column(nullable = false, length = 500)
    private String origen;

    @Column(name = "host_publico", length = 253)
    private String hostPublico;

    @Column(nullable = false)
    private boolean activo;

    protected FrontendNegocio() { }

    public String getOrigen() { return origen; }
}
