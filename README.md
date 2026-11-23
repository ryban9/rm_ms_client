# Microservicio de clientes

Servicio Spring Boot para administrar clientes por negocio en PostgreSQL/Supabase, esquema `rm_detalles`. Cada solicitud de API incluye `X-Negocio-Codigo`; el servicio valida que el negocio esté activo, que el usuario autenticado tenga membresía y que el rol tenga el permiso requerido. Todas las consultas y modificaciones de clientes se filtran por el negocio resuelto en el backend.

## Tecnologías

- Java 21 y Spring Boot 4.1.2
- Spring MVC, Spring Data JPA y PostgreSQL
- Spring Security Resource Server con JWT de Supabase Auth (ES256)
- CORS basado en los orígenes activos de `rm_detalles.frontends_negocio`

## Endpoints

Todas las rutas de clientes requieren un JWT válido, `X-Negocio-Codigo` y acceso activo del usuario al negocio. GET requiere `CLIENTES_CONSULTAR`; POST, PUT y DELETE requieren `CLIENTES_GESTIONAR`.

| Método | Ruta | Uso |
|---|---|---|
| GET | `/api/v1/clientes?soloActivos=true` | Lista clientes del negocio; por defecto solo activos. `false` incluye inactivos. |
| GET | `/api/v1/clientes/{id}` | Consulta un cliente del negocio por UUID. |
| POST | `/api/v1/clientes` | Registra cliente en el negocio actual. |
| PUT | `/api/v1/clientes/{id}` | Actualiza un cliente del negocio actual. |
| DELETE | `/api/v1/clientes/{id}` | Desactiva lógicamente un cliente del negocio actual. |
| GET | `/actuator/health` | Health check público. |

Headers para las solicitudes protegidas:

```http
Authorization: Bearer <access_token>
X-Negocio-Codigo: RMD
Content-Type: application/json
```

Ejemplo de JSON para crear:

```json
{
  "nombres": "María Pérez",
  "telefono": "0991234567",
  "correo": "maria@example.com",
  "direccion": "Quito, Ecuador",
  "observaciones": "Prefiere contacto por WhatsApp"
}
```

El historial de pedidos y ventas se integrará cuando esos módulos estén implementados. No se realiza borrado físico para conservar referencias e historial.

## Multiempresa y varios frontends

- El negocio se resuelve mediante `X-Negocio-Codigo`; el cliente no puede enviar un `negocio_id` en el cuerpo para asignarse a otro negocio.
- Las operaciones de lectura, actualización y desactivación siempre incluyen `negocio_id` en sus consultas.
- Los permisos se cargan desde `usuario_negocios`, `roles`, `rol_permisos` y `permisos` para el usuario y negocio actuales.
- CORS permite orígenes activos de `frontends_negocio`; el filtro comprueba además que el origen pertenezca al negocio indicado.
- CORS restringe llamadas desde navegadores, pero la autorización efectiva se valida con JWT, membresía y permisos en el backend.
- Un usuario puede pertenecer a varios negocios y tener un rol distinto en cada uno.

## Configuración local en IntelliJ IDEA

1. Abre esta carpeta como proyecto Maven.
2. Selecciona JDK 21 para el proyecto y la configuración de ejecución.
3. Configura las variables de `.env.example` en **Run > Edit Configurations > Environment variables**. Spring Boot no carga automáticamente un archivo `.env`.
4. Obtén la conexión PostgreSQL desde Supabase **Connect** y reemplaza `PROJECT_REF` y la contraseña. No publiques credenciales, claves privadas ni tokens.
5. Ejecuta `ClientesServiceApplication`. El servicio escucha en `http://localhost:8082`.

`ddl-auto: validate` compara el mapeo JPA con las tablas existentes; no crea ni altera tablas. El esquema debe incluir `clientes.negocio_id`, `negocios`, `usuario_negocios`, `roles`, `rol_permisos`, `permisos` y `frontends_negocio`, según la estructura multiempresa del proyecto.

## Docker

```bash
mvn clean package
docker build -t clientes-service .
```

El puerto del contenedor es `8082`. Configura las mismas variables de entorno al iniciar el contenedor.
