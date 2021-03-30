# Descripción

Este servicio expone 3 funcionalidades que permiten realizar lo siguiente:

* Crear clientes
* Listar clientes
* Visualizar los KPI de los clientes

Creacion de clientes
--------------------
Esta funcionalidad tiene 2 reglas de validación al momento de crear un cliente:

* Valida que el campo "age" sea un número positivo mayor o igual a 1
* Valida que el campo "dateOfBirth" tenga el formatio dd/MM/yyyy

Tambien se ejecutan 2 reglas de negocio durante este proceso
: 
* Se calcula la probable fecha de muerte basandonos en el valor de la esperanza de vida en Perú
* Se actualizan los KPI de forma asincrona (media y desviación estandar)

# Swagger

Para acceder a la documentación de servicio podemos utilizar la siguiente url

>
> http://microservice-intercorp-client.eba-f5e8zbnw.us-east-1.elasticbeanstalk.com/api/v1/doc
>

En la documentación de swagger encontraran las rutas disponibles para utilizar el API o tambien podran probarlas directamente desde swagger.

# Pruebas Unitarias

Se adicionaron las siguientes pruebas a nivel de caso de uso:

* Creación de clientes con data valida
* Creación de clientes con el campo "dateOfBirth" incorrecto
* Creación de clientes con el campo "age" incorrecto

Para trabajar de manera correcta las pruebas nos apoyamos de Mockito

# Arquitectura

Aplicacion:

La aplicación internamente esta construida siguiendo el patron "clean architecture" teniendo las siguientes capas:

* Presentation (acá se encuentran los controladores y logica de presentación)
* Core (acá se encuentra toda la logica del negocio)
* Infrastructure (acá se encuentra la logica para conexi+on a la base de datos)


Infraestructura:
 
La aplicación esta desplegada en AWS utilizando el servicio beanStalk, para fines prácticos el almacenamiento de la data se esta realizando en una base de datos en memoria llamada H2

![Arquitectura](https://s3-ap-northeast-1.amazonaws.com/devlusaja.com/springboot.jpg)

Para realizar el despliegue nos apoyamos de:

* aws cli
* cloudformation
* makefile

Para desplegar la aplicación se ejecuta:
~~~~
$ make deploy
~~~~

Para eliminar la aplicación se ejecuta:
~~~~
$ make delete
~~~~ 