# Configuración de MySQL en Docker

## Crear un contenedor de MySQL con Docker

Ejecuta el siguiente comando para crear y ejecutar un contenedor de MySQL con la base de datos `oh_class_db`:

```bash
docker run -d --name postgres_container \
  -e POSTGRES_USER=user_postgres \
  -e POSTGRES_PASSWORD=123456 \
  -e POSTGRES_DB=oh_class_db \
  -p 5432:5432 \
  postgres:latest
```

