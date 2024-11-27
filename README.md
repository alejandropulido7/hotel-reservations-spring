# Descripción del Proyecto

Desarrolla una API RESTful para un sistema de reserva de habitaciones de hotel.
Los usuarios deben poder buscar habitaciones disponibles en función de la fecha de entrada, la fecha de salida y
otros criterios como el tipo de habitación y la cantidad de huéspedes.

Se debe de poder registrar habitaciones en el hotel y eliminar las mismas o ponerlas no disponibles. Se debe tener
en cuenta que cada habitación tiene un valor por noche, y el valor depende de la habitación. El id de la ubicación es
la composición del piso unido a el número de la ubicación, y solo hay 10 habitaciones por piso.
Debo de poder ver habitaciones disponibles y habitaciones ocupadas, también todas las habitaciones.

📍 El uso de arquitectura limpia, programación funcional, programación reactiva, la
implementación de autenticación y autorización mejorará tu calificación en la prueba.

🔔 Por favor, después de completar una funcionalidad, realiza las pruebas unitarias
correspondientes a esa funcionalidad. No dejes las pruebas unitarias para el final, ya que son una
parte importante de tu prueba técnica.

## Para capturar información de la reserva:
- Los parámetros obligatorios del sistema para una reserva son, la identificación del usuario, nombre, fechas, habitación
- Los campos de nombres deben ser campos de máximo 30 caracteres (Únicamente texto. No se permiten números)
- Los campos identificación son numéricos de máximo 12 dígitos, y debe tener el tipo de documento
- Los campos de fechas deben ser manejados formateados de la siguiente forma: dd/mm/yyyy HH:mm:ss
  
## Requerimientos técnicos:
- Loggear cada una de las transacciones que ejecute el api. OJO no es escribir salidas de consola ej: system.out.print() o console.log() o console.write() o print() .
- Escribir pruebas unitarias de cada una de las funcionalidades en las diferentes capas. Debes alcanzar al menos un 50% de cobertura en pruebas.
- Configuración de la API: configurar para se ejecute en el puerto 8085 . Configura la API para que tenga la ruta ** base /api/hotel **
- Implementación correcta del patrón de diseño y de clean code.
- La estructura de response del api debe ser:
```
{
data:
status:
message:
}
```
- Proteger el api contra SQL injection
- Implementar un correcto manejo de errores y control de excepciones personalizadas (No es buena práctica capturar excepciones genéricas)
- Crear el dockerfile del proyecto para la debida creación del docker image
  
## Configuración de la Base de Datos:
- Utiliza ORM para conectarte a una base de datos bien sea NoSQL o SQL.
- Utiliza pool de conexiones para configurar conexión a base de datos
