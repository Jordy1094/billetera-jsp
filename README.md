# Billetera Digital - Caso 1 (Servlet + JSP)

Proyecto de la T1 del curso Soluciones Web y Aplicaciones Distribuidas (UPN).

Es una billetera sencilla hecha en Java sin frameworks. Muestra el saldo y el historial, y permite registrar recargas y gastos. La página se arma en el servidor y los datos se guardan en la sesión, así que se reinician al cerrar el navegador.

## Versiones

- Java JDK 21 (el proyecto compila para Java 17)
- Apache Maven 3.9.9
- Apache Tomcat 10.1.61
- Jakarta Servlet 6.0 y JSTL 3.0
- Bootstrap 5.3.3 (por CDN)

Tiene que ser Tomcat 10.1 o superior. En Tomcat 9 no corre porque usa el paquete `javax` en lugar de `jakarta`.

## Cómo ejecutarlo

Para descargar el proyecto:

```
git clone https://github.com/Jordy1094/billetera-jsp.git
cd billetera-jsp
```

El archivo listo para Tomcat está en `dist/billetera-jsp.war`. Si no quieres compilar, empieza en el paso 2 con ese archivo.

1. Compilar el proyecto:

   ```
   mvn clean package
   ```

   El `.war` queda en `target/billetera-jsp.war`.

2. Copiar el `.war` a la carpeta `webapps` de Tomcat.

3. Iniciar Tomcat desde su carpeta `bin`:

   ```
   startup.bat
   ```

   En Linux o macOS es `./startup.sh`.

4. Abrir http://localhost:8080/billetera-jsp/

Para apagar Tomcat se usa `shutdown.bat` (o `./shutdown.sh`).

## Estructura

```
src/main/java/pe/edu/upn/billetera/
  model/Billetera.java          saldo, historial y validaciones
  model/Movimiento.java         tipo y monto de cada movimiento
  model/TipoMovimiento.java     RECARGA o GASTO
  servlet/BilleteraServlet.java GET muestra la billetera, POST registra
src/main/webapp/
  index.jsp                     redirige a /billetera
  WEB-INF/views/billetera.jsp   vista con JSTL, sin scriptlets
```

## Pruebas que hicimos

Todas empiezan con saldo de S/ 250.00 y el historial vacío.

1. Recarga de S/ 100.00: el saldo queda en S/ 350.00.
2. Gasto de S/ 50.00: el saldo queda en S/ 300.00 y el gasto sale primero en la lista.
3. Gasto de S/ 400.00: sale "Saldo insuficiente" y el saldo sigue en S/ 300.00.
4. Monto en cero, negativo o vacío: sale el error y no cambia nada.

Después de un registro correcto el servlet hace un redirect (patrón PRG), así que al presionar F5 no se repite el movimiento.
