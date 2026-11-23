# Bolsvy

Aplicación Android para un negocio de bolsas pintadas y bordadas a mano. Permite consultar el catálogo, buscar y filtrar por medida, y guardar bolsas favoritas en una base de datos local.

## Tecnologías
- Kotlin y Jetpack Compose
- Room (SQLite) para el almacenamiento local
- Navigation Compose
- Material Design 3

## Funciones
- Pantalla de inicio
- Catálogo con buscador y filtros (Todos, 40x40, 20x20)
- Favoritos guardados en la base de datos (agregar y quitar)
- Pantalla de contacto

## Base de datos
Dos tablas en `bolsvy.db`:
- `bolsas`: id, nombreRes, precio, medida, imagenRes
- `favoritos`: bolsaId (llave foránea hacia `bolsas.id`)

## Autora
Aphril Ivonne Celaya Abreo (25300726) · 4DSM-G3
Universidad Tecnológica de Tula-Tepeji · Desarrollo de aplicaciones móviles