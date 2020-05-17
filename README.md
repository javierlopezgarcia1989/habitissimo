# habitissimo
Prueba técnica Habitissimo

BACK: SpringBoot - SpringJPA - Java 8 - MySQL
FRONT: Angular 9

Para ejecutar esta aplicación en servidor local:
1. Desplegar servidor local de base de datos MySQL
2. Crear base de datos en servidor MySQL
3. Configurar archivo de propiedades de la aplicación application.properties actualizando la URL de conexión:
  spring.datasource.url=jdbc:mysql://192.168.64.2:3306/habitissimo
  #Usuario y contrasena para tu base de datos descrita en la linea anterior
  spring.datasource.username=springuser
  spring.datasource.password=ThePassword

La aplicación creará automáticamente las tablas de bases de datos al arrancar.
