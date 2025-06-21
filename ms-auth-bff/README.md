# MS Auth BFF (Backend for Frontend)

Sistema de autenticación BFF con frontend moderno que se comunica con microservicios de autenticación.

## 🚀 Características

- **Frontend Moderno**: Interfaz de usuario atractiva y responsive
- **Autenticación Completa**: Registro e inicio de sesión
- **BFF Pattern**: Backend for Frontend que orquesta microservicios
- **Validaciones**: Validaciones tanto en frontend como backend
- **Diseño Responsive**: Funciona en dispositivos móviles y desktop

## 🏗️ Arquitectura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   BFF           │    │   Microservicios│
│   (HTML/CSS/JS) │◄──►│   (Spring Boot) │◄──►│   (Auth-DB)     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 📋 Prerrequisitos

- Java 17 o superior
- Gradle 7.x o superior
- Microservicio `ms-auth-db` ejecutándose en puerto 8080

## 🛠️ Instalación y Ejecución

### 1. Clonar y configurar el proyecto

```bash
# Navegar al directorio del proyecto
cd ms-auth-bff

# Limpiar y compilar
./gradlew clean build
```

### 2. Ejecutar el BFF

```bash
# Ejecutar la aplicación
./gradlew bootRun
```

El BFF estará disponible en: `http://localhost:8081`

### 3. Acceder al frontend

Abre tu navegador y ve a: `http://localhost:8081`

## 🔧 Configuración

### Puertos por defecto:
- **BFF**: 8081
- **Auth-DB**: 8080 (debe estar ejecutándose)

### Configuración en `application.properties`:
```properties
server.port=8081
spring.application.name=ms-auth-bff
```

## 📡 Endpoints API

### Autenticación
- `POST /api/auth/register` - Registro de usuarios
- `POST /api/auth/login` - Inicio de sesión
- `GET /api/auth/health` - Estado del servicio

### Frontend
- `GET /` - Página principal de autenticación
- `GET /login` - Página de login
- `GET /register` - Página de registro

## 🎨 Frontend Features

- **Diseño Moderno**: Gradientes y efectos visuales atractivos
- **Tabs Interactivos**: Cambio suave entre login y registro
- **Validaciones en Tiempo Real**: Feedback inmediato al usuario
- **Animaciones**: Transiciones suaves y efectos hover
- **Responsive**: Adaptable a diferentes tamaños de pantalla
- **Iconos**: Font Awesome para mejor UX

## 🔐 Funcionalidades de Seguridad

- **Validación de Contraseñas**: Mínimo 6 caracteres
- **Validación de Email**: Formato correcto de email
- **Confirmación de Contraseña**: Verificación en registro
- **Manejo de Errores**: Mensajes claros y descriptivos
- **Tokens**: Generación de tokens de autenticación

## 📱 Características del Frontend

### Login
- Campo de usuario
- Campo de contraseña con toggle de visibilidad
- Validaciones de campos requeridos

### Registro
- Campo de usuario
- Campo de email con validación
- Campo de contraseña con toggle de visibilidad
- Campo de confirmación de contraseña
- Validaciones completas

### UX/UI
- Mensajes de éxito/error con colores distintivos
- Estados de carga con spinners
- Animaciones de transición
- Efectos hover en botones
- Diseño glassmorphism moderno

## 🧪 Testing

```bash
# Ejecutar tests
./gradlew test

# Ejecutar tests con coverage
./gradlew test jacocoTestReport
```

## 📊 Monitoreo

- **Health Check**: `http://localhost:8081/actuator/health`
- **Info**: `http://localhost:8081/actuator/info`

## 🔄 Flujo de Trabajo

1. **Registro**:
   - Usuario llena formulario de registro
   - BFF valida datos
   - BFF verifica si usuario/email ya existe
   - BFF crea usuario en microservicio
   - Retorna token y datos del usuario

2. **Login**:
   - Usuario llena formulario de login
   - BFF valida credenciales
   - BFF autentica con microservicio
   - Retorna token y datos del usuario

## 🚨 Troubleshooting

### Error de conexión con microservicio
- Verificar que `ms-auth-db` esté ejecutándose en puerto 8080
- Revisar logs del BFF para errores de conexión

### Error de compilación
- Verificar versión de Java (requiere Java 17+)
- Limpiar y recompilar: `./gradlew clean build`

### Frontend no carga
- Verificar que el BFF esté ejecutándose en puerto 8081
- Revisar consola del navegador para errores JavaScript

## 📝 Notas de Desarrollo

- El BFF actúa como intermediario entre el frontend y los microservicios
- Las validaciones se realizan tanto en frontend como backend
- Los tokens se almacenan en localStorage del navegador
- El diseño es completamente responsive y moderno

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles. 