# SDK AWS (V3)

Este es el último SDK que se ha creado. Su funcionalidad principal es hacer de Wrapper al SDK que proporciona el Api Gate Way de Amazon.
Mediante este Wrapper se facilita el uso del SDK de Amazon. 

Podríamos haber creado directamente un SDK que hiciera las llamadas directamente a nuestr API, pero se decidió utilizar el propio SDK
de Amazon ya que este facilita varias cosas como por ejemplo la renovación del token, versionado, etc..

Como ya hemos aclarado más arriba, este repo unicamente hace de Wrapper al SDK de Amazon, por lo que lo primero que tendremos que hacer 
será bajarnos la última versión de este SDK desde la página de Amazon dentro de la AWS. Ya que esta página solo puede ser accesible 
por los del Backend, lo que se ha hecho es automatizar esta tarea mediante un script, que lo que hace es bajarse el último deploy del 
SDK, lo compila, coge el .jar generado y lo copia dentro del wrapper.

El Wrapper está estructurado en varios paquetes:

- API: Aquí estará nuestro objeto principal que será el encargado de guardar el API_KEY y hacer las peticiones y las funciones que usará
el ciente para obetener la información de la API
- Desirializer: Las clases que usaremos para convertir el json que prove el API a los objetos custom que proporciona el Wrapper
- Engine: Es el core de la app. Aquí estará desde la gestión de los errores hasta el mapper de las clases.
- Models: Los custom object creados para facilitar la interacción con los datos proporcionados por la API.

En el gradle existen varias tareas para generar tanto la libreria .aar como para generar el .jar
