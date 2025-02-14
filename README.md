# Configuración de MySQL en Docker

## Crear un contenedor de MySQL con Docker

Ejecuta el siguiente comando para crear y ejecutar un contenedor de MySQL con la base de datos `oh_class_db`:

```
docker run -d --name postgres_container \
  -e POSTGRES_USER=user_postgres \
  -e POSTGRES_PASSWORD=123456 \
  -e POSTGRES_DB=oh_class_db \
  -p 5432:5432 \
  postgres:latest
```

## Configuración de puertos para microservicios

Para ejecutar localmente los diferentes microservicios, asegúrate de especificar la propiedad `server.port` en el `application.yml` de cada servicio con el valor del puerto correspondiente:

- **ms-tecnologia**: puerto `8080`
- **ms-capacidad**: puerto `8081`
- **ms-bootcamp**: puerto `8082`

Ejemplo de configuración en `application.yml`:

```yaml
server:
  port: 8080  # Cambia este valor según el microservicio