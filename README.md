# Campus Virtual

Plataforma de gestión de cursos educativos. Backend desarrollado en Java con TDD, Maven y Spring Boot.

## Tecnologías
- Java 25
- Maven
- JUnit 5
- Mockito

## Estructura del proyecto

```code
src/
├── main/java/com/campusvirtual/ # Código de producción
└── test/java/com/campusvirtual/ # Pruebas unitarias
```

## Cómo ejecutar
```bash
# Compilar
javac -d target/classes src/main/java/com/campusvirtual/App.java

# Ejecutar
java -cp target/classes com.campusvirtual.App
