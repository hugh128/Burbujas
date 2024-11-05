# Burbujas - Aplicación de Cálculo de Servicios para Car Wash ServiAuto

**Burbujas** es una aplicación móvil desarrollada en **Kotlin** utilizando **Android Studio**, diseñada para los trabajadores de **ServiAuto**, un car wash que requiere un sistema rápido y eficiente para calcular el costo de los servicios realizados a cada automóvil. Los trabajadores podrán seleccionar los servicios, calcular el total con IVA, determinar la comisión y registrar los cálculos en una base de datos para referencia futura.

## Características

- **Selección de Servicios**: Los trabajadores pueden seleccionar entre servicios predeterminados:
  - Lavado y Secado (Q10.00)
  - Aspirado (Q10.00)
  - Aplicar Silicón (Q5.00)
  - Pulido (Q50.00)
  - Lavado de Motor (Q100.00)

- **Cálculo de Costo con IVA**: Incluye un botón para calcular el costo total con IVA (12%) de los servicios seleccionados.

- **Cálculo de Comisión**: Calcula la comisión del trabajador (10%) con base en el monto total sin IVA.

- **Módulo de Login (Funcionalidad Extra)**: Permite iniciar sesión mediante usuario y contraseña.

- **Registro de Datos**: Almacena los cálculos en una base de datos local (SQLite), incluyendo:
  - Costo con IVA
  - Comisión
  - Fecha de Registro
  - Servicios realizados

- **Historial de Servicios**: Muestra un registro de los cálculos realizados anteriormente, permitiendo que el trabajador consulte la información de servicios pasados.

## Screenshots
- Login
![](/images/login.png)

- Menu
![](/images/menu.png)

- Home
![](/images/home1.png)
![](/images/home2.png)

- Registros
![](/images/registros.png)

- Gestion Usuarios
![](/images/gestionDeUsuarios.png)

-Acerca de
![](/images/acercaDe.png)

## Requisitos

- Android Studio
- Kotlin
- Room Database (para almacenamiento local)
- SQLite para el almacenamiento de registros

## Uso de la Aplicación

1. **Inicio de Sesión**: Ingresa el nombre de usuario y la contraseña para acceder a la aplicación.
2. **Selección de Servicios**: Selecciona los servicios realizados al automóvil.
3. **Cálculo del Total**: Presiona el botón de cálculo para obtener el total con IVA.
4. **Cálculo de Comisión**: Presiona el botón de comisión para ver la comisión correspondiente.
5. **Registrar Transacción**: Guarda la transacción en el historial para futuras referencias.
6. **Ver Historial**: Consulta el historial completo de transacciones almacenadas.

## Créditos

Este proyecto fue desarrollado como parte de un curso de programación con fines educativos.

## Autores

- Pablo Cobon (2690-23-18351)
- Hugo Ordoñez (2690-23-10929)
