package ec.rmdetalles.clientes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "clientes", schema = "rm_detalles")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "negocio_id", nullable = false, updatable = false)
    private Negocio negocio;

    @Column(nullable = false, length = 160)
    private String nombres;

    @Column(length = 30)
    private String telefono;

    @Column(length = 254)
    private String correo;

    @Column(columnDefinition = "text")
    private String direccion;

    @Column(columnDefinition = "text")
    private String observaciones;

    @Column(nullable = false)
    private boolean activo = true;

    @CreationTimestamp
    @Column(name = "creado_en", nullable = false, updatable = false)
    private OffsetDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;

    protected Cliente() { }

    public Cliente(Negocio negocio, String nombres, String telefono, String correo, String direccion, String observaciones) {
        this.negocio = negocio;
        this.nombres = nombres;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.observaciones = observaciones;
        this.activo = true;
    }

    public UUID getId() { return id; }
    public Negocio getNegocio() { return negocio; }
    public String getNombres() { return nombres; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
    public String getObservaciones() { return observaciones; }
    public boolean isActivo() { return activo; }
    public OffsetDateTime getCreadoEn() { return creadoEn; }
    public OffsetDateTime getActualizadoEn() { return actualizadoEn; }

    public void actualizar(String nombres, String telefono, String correo, String direccion,
                           String observaciones, Boolean activo) {
        this.nombres = nombres;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.observaciones = observaciones;
        if (activo != null) this.activo = activo;
    }

    public void desactivar() { this.activo = false; }
}
