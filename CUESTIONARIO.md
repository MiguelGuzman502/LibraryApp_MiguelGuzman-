1. Diferencia entre clase y objeto
Pregunta N° 1

Respuesta investigada: Una clase es una plantilla abstracta que define las propiedades (atributos) y comportamientos (métodos) comunes a un grupo de entidades, 
mientras que el objeto es una instancia concreta creada a partir de esa clase con valores propios.

Explicación con propias palabras: La clase define características y acciones y el objeto es una instancia a partir de una clase.

Ejemplo de aplicación en LibraryApp: 
La clase Libro define la estructura general (título, autor, editorial), y cada registro específico que agregas (por ejemplo, "Cien años de soledad") es un objeto de esa clase

2. ¿Qué es un atributo?
Pregunta N° 2
Respuesta investigada: Es una variable declarada dentro de una clase que representa una característica o propiedad individual de los objetos pertenecientes a ella.

Explicación con propias palabras: La característica de un objeto en especial.

Ejemplo de aplicación en LibraryApp: Los atributos de la clase Libro son idLibro, titulo, autor y stock.

3. ¿Qué es un método?
Pregunta N° 3

Respuesta investigada: Es una rutina o conjunto de instrucciones asociadas a una clase que describe el comportamiento o las operaciones que los objetos pueden realizar.
xplicación: El método es una acción que puede realizar un objeto.

LibraryApp: prestarLibro().

N° 4. Constructor

Respuesta: Método especial para inicializar atributos al crear con new.

Explicación: Sirve para inicializar un objeto cuando se crea con la palabra new, dándole valores iniciales a sus atributos.

LibraryApp: public Libro(int id, String titulo).

N° 5. Private, public, protected

Respuesta: Niveles de acceso y visibilidad en Java.

Explicación: El privado solo se puede usar en una clase, el público se puede usar en cualquier lugar y el protected se puede usar dentro de la misma clase, subclases y clases del mismo paquete.

LibraryApp: Atributos private, métodos públicos.

N° 6. Get y Set

Respuesta: Métodos para consultar o cambiar atributos privados.

Explicación: El get obtiene un dato y el set cambia un dato.

LibraryApp: getTitulo(), setStock().

N° 7. Encapsulamiento

Respuesta: Ocultar datos internos y exponerlos de forma segura.

Explicación: Es ocultar los datos privados de una clase para protegerlos, permitiendo modificarlos o consultarlos únicamente de forma segura usando los métodos get y set.

LibraryApp: Atributos protegidos con get/set.

N° 8. Clase vs Interfaz

Respuesta: Clase tiene código; interfaz es un contrato de métodos vacíos.

Explicación: La clase puede obtener datos y acciones ya programadas, mientras que la interfaz es solo un contrato o lista de reglas con métodos vacíos que la clase está obligada a programar.

LibraryApp: InterfaceDAO vs LibroDAO.

N° 9. Implements

Respuesta: Conecta una clase con una interfaz obligándola a programar sus métodos.

Explicación: Es la palabra que se usa en Java para conectar una clase con una interfaz, obligándola a escribir el código de los métodos que la interfaz pide.

LibraryApp: class LibroDAO implements InterfaceDAO.

N° 10. Static y final

Respuesta: Static es de clase; final es constante o inalterable.

Explicación: static significa que un atributo o método pertenece a la clase completa y no a un objeto en específico; final indica que un valor es constante y ya no se puede cambiar, o que una clase no se puede heredar.

LibraryApp: Conexión estática de base de datos.

N° 11. Tabla relacional

Respuesta: Estructura de filas y columnas para datos.

Explicación: Es una tabla donde se guardan datos organizados entre filas y columnas.

LibraryApp: Tabla libros.

N° 12. Clave primaria

Respuesta: Identificador único de cada registro en la tabla.

Explicación: Es un dato que identifica de manera única cada registro.

LibraryApp: idLibro.

N° 13. Clave foránea

Respuesta: Campo que vincula una tabla con la clave primaria de otra.

Explicación: Es un dato que conecta una tabla con la otra.

LibraryApp: idLibro en tabla de préstamos.

N° 14. CRUD

Respuesta: Create, Read, Update, Delete (Operaciones básicas).

Explicación: Significa Crear, Leer, Actualizar y Borrar (Create, Read, Update, Delete), que son las 4 operaciones básicas para manejar información en la base de datos.

LibraryApp: Registrar, listar, modificar o borrar libros.

N° 15. JDBC

Respuesta: API de conexión entre Java y bases de datos relacionales.

Explicación: Es donde conectamos nuestro proyecto con la base de datos de MySQL.

LibraryApp: Conexión a tutorialjavafx.

N° 16. Procedimiento almacenado

Respuesta: Código SQL guardado directamente en el servidor.

Explicación: Es un bloque de código con consultas SQL que se guarda directamente dentro de la base de datos para ejecutarse rápido cuando se necesite.

LibraryApp: CallableStatement para inserciones.

N° 17. DAO

Respuesta: Patrón para aislar la lógica de acceso a la base de datos.

Explicación: Es una clase que maneja la base de datos que realiza cosas como guardar, buscar, actualizar y eliminar datos.

LibraryApp: LibroDAO.

N° 18. Separar SQL del Controller

Respuesta: Para mantener orden y evitar mezclar vistas con base de datos.

Explicación: Para mantener el código ordenado y evitar que las pantallas o controladores tengan instrucciones de base de datos, dejando esa tarea exclusivamente a los archivos DAO.

LibraryApp: Cero SQL en LibroController.

N° 19. JavaFX

Respuesta: Framework de Java para interfaces gráficas de escritorio.

Explicación: Es una herramienta que nos sirve para crear ventanas, botones y pantallas en Java.

LibraryApp: Entorno visual del sistema.

N° 20. FXML

Respuesta: Archivo basado en XML para diseñar vistas en JavaFX.

Explicación: Es un archivo que sirve para ver una vista de dónde están los botones y el texto usando etiquetas.

LibraryApp: libro_view.fxml.

N° 21. SceneBuilder

Respuesta: Herramienta visual drag-and-drop para interfaces FXML.

Explicación: Sirve para diseñar las interfaces de JavaFX de forma visual arrastrando los elementos.

LibraryApp: Diseño visual de la ventana de biblioteca.

N° 22. Controller

Respuesta: Clase intermediaria entre la vista y el modelo.

Explicación: Es la clase que interactúa con la vista y el modelo.

LibraryApp: LibroController.

N° 23. @FXML

Respuesta: Anotación para unir componentes FXML con el código Java.

Explicación: Es una indicación de que esos elementos del código están conectados con el archivo FXML de la pantalla.

LibraryApp: @FXML private TextField txtTitulo;.

N° 24. MVC

Respuesta: Patrón Modelo-Vista-Controlador.

Explicación: Significa Modelo, Vista, Controlador.

LibraryApp: Separación en capas del proyecto.

N° 25. Responsabilidad del modelo

Respuesta: Representar y estructurar los datos del negocio.

Explicación: Representar y guardar la estructura de los datos del negocio (como la clase Libro con sus atributos).

LibraryApp: Clase Libro.

N° 26. Responsabilidad de la vista

Respuesta: Mostrar elementos visuales y capturar clics del usuario.

Explicación: Mostrar los botones, textos, ventanas y formularios.

LibraryApp: Tablas y formularios FXML.

N° 27. Responsabilidad del controlador

Respuesta: Coordinar interacciones del usuario entre vista y modelo.

Explicación: Recibir las acciones que hace el usuario en la pantalla (como hacer clic en un botón) y coordinar qué hacer con el modelo y la base de datos.

LibraryApp: Manejo de eventos en botones.

N° 28. Recorrido de un dato hasta MySQL

Respuesta: Flujo completo de extremo a extremo.

Explicación: USUARIO -> VISTA (JavaFX / FXML) --> controller --> modelo --> dao --> jdbc --> MySQL.

LibraryApp: Ruta de un libro guardado.