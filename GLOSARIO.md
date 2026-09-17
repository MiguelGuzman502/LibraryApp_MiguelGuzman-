1. Clase

Definición formal: Molde que define los atributos y métodos que tendrán los objetos creados a partir de ella.

Definición en mis palabras: La clase define las características y acciones.

Ubicación en el código: Cuando creamos un nuevo paquete nos da la opción de crear una clase para ese paquete.

Ejemplo práctico & problema que resuelve:

Ejemplo: public class Libro

Problema que resuelve: Evita que hagamos las cosas a lo loco y ayuda a organizarnos.

2. Objeto

Definición formal: Instancia particular de una clase que posee un estado propio y un comportamiento definido.

Definición en mis palabras: Es una instancia a partir de una clase.

Ubicación en el código: Cuando ejecutas el programa o cuando creas una entidad usando la palabra new.

Ejemplo práctico & problema que resuelve:

Ejemplo: Libro miLibro = new Libro("Java 21");

Problema que resuelve: Organiza el código agrupando las características de un elemento en un solo lugar.

3. Encapsulamiento


Definición formal: Mecanismo de la POO que oculta el estado interno de los objetos y restringe el acceso directo a los atributos usando modificadores de acceso.

Definición en mis palabras: Meter los datos en un lugar y permitir abrirlos únicamente con los getters y setters que son como los permisos.

Ubicación en el código: En las clases del modelo como Libro o Usuario protegiendo sus características.

Ejemplo práctico & problema que resuelve:

Ejemplo: private String titulo;

Problema que resuelve: Evita que el programa se altere o arruine los datos importantes por error.

4. MVC (Modelo - Vista - Controlador)


Definición formal: Patrón de arquitectura de software que separa los datos, la interfaz de usuario y la lógica de control en tres componentes distintos.

Definición en mis palabras: Dividir el trabajo en equipos: uno guarda los datos, otro diseña la pantalla y otro coordina las acciones.

Ubicación en el código: En la estructura general de paquetes (org.ac.model, org.ac.view, org.ac.controller).

Ejemplo práctico & problema que resuelve:

Ejemplo: El archivo FXML muestra la ventana, el controlador hace que funcione el botón, y el modelo guarda el nombre del libro.

Problema que resuelve: Evita tener todo el código revuelto en un solo archivo facilitando el orden y mantenimiento.

5. DAO (Data Access Object)


Definición formal: Patrón de diseño que separa la lógica de persistencia y conexión a la base de datos del resto de la aplicación.

Definición en mis palabras: Es una clase que maneja la base de datos que realiza cosas como guardar, buscar, actualizar y eliminar datos.

Ubicación en el código: En el paquete org.ac.dao donde programamos las consultas para guardar libros.

Ejemplo práctico & problema que resuelve:

Ejemplo: public boolean insertarLibro(Libro libro)

Problema que resuelve: Evita que las consultas SQL estén regadas por todo el programa, guardándolas en un solo lugar.

6. CRUD (Create, Read, Update, Delete)

Definición formal: Acrónimo que describe las cuatro operaciones fundamentales de almacenamiento persistente: Creación, Lectura, Actualización y Borrado.

Definición en mis palabras: Las 4 acciones básicas que le puedes hacer a cualquier dato: crearlo, verlo, modificarlo o borrarlo.

Ubicación en el código: En los métodos de los DAO y las pantallas de gestión de libros y usuarios.

Ejemplo práctico & problema que resuelve:

Ejemplo: Sentencias SQL como INSERT, SELECT, UPDATE y DELETE.

Problema que resuelve: Cambia la forma en que se interactúa con los registros de la base de datos de manera ordenada.

7. JDBC (Java Database Connectivity)


Definición formal: API de Java que permite la ejecución de operaciones independientes entre una aplicación Java y una base de datos relacional.

Definición en mis palabras: El conector entre Java y MySQL.

Ubicación en el código: En la clase de conexión del proyecto que enlaza el driver de MySQL.

Ejemplo práctico & problema que resuelve:

Ejemplo: Connection cn = DriverManager.getConnection(URL, USER, PASS);

Problema que resuelve: Permite la comunicación entre Java y la base de datos.

8. Connection


Definición formal: Interfaz de Java que representa una sesión de comunicación física con una base de datos específica.

Definición en mis palabras: La conexión de Java con la comunicación de la base de datos.

Ubicación en el código: En las clases encargadas de conectar el programa con la base de datos.

Ejemplo práctico & problema que resuelve:

Ejemplo: El objeto Connection que usamos para iniciar el canal con MySQL.

Problema que resuelve: Evita la ejecución de consultas sin estar conectado a una base de datos real en SQL.

9. ResultSet


Definición formal: Objeto que representa un conjunto de resultados de una consulta tabular, sirviendo como cursor para recorrer fila por fila los datos obtenidos.

Definición en mis palabras: Es el que almacena temporalmente los datos devueltos por una consulta SELECT para que el programa pueda leerlos sin problema.

Ubicación en el código: En los métodos DAO cuando hacemos una consulta de tipo SELECT.

Ejemplo práctico & problema que resuelve:

Ejemplo: ResultSet resultado = sentencia.executeQuery();

Problema que resuelve: Permite leer de forma ordenada y fila por fila la información que nos manda la base de datos.

10. FXML


Definición formal: Lenguaje basado en XML utilizado para definir la estructura de la interfaz de usuario en aplicaciones JavaFX de manera independiente al código fuente.

Definición en mis palabras: Un archivo especial que dibuja dónde van los botones y las letras en la pantalla usando etiquetas.

Ubicación en el código: En las vistas del proyecto (por ejemplo, libro_view.fxml).

Ejemplo práctico & problema que resuelve:

Ejemplo: <Button layoutX="50" layoutY="100" text="Guardar"/>

Problema que resuelve: Separa el diseño visual de la pantalla del código de Java, haciendo todo más ordenado.

11. SceneBuilder


Definición formal: Herramienta visual de diseño gráfico interactivo para la creación rápida de interfaces de usuario en aplicaciones JavaFX.

Definición en mis palabras: Permite diseñar las pantallas visualmente y generar código FXML.

Ubicación en el código: En el programa externo que abres para diseñar las ventanas visuales.

Ejemplo práctico & problema que resuelve:

Ejemplo: Cuando arrastras una tabla (TableView) hacia el centro de la pantalla con el ratón.

Problema que resuelve: Evita tener que calcular coordenadas a mano en archivos de texto para acomodar los elementos visuales.

12. @FXML


Definición formal: Anotación de Java utilizada para vincular los componentes gráficos definidos en un archivo FXML con las variables y métodos de su clase controladora.

Definición en mis palabras: Una etiqueta que conecta el botón de la pantalla con el código de Java para que se hablen entre sí.

Ubicación en el código: En las clases controladoras de las vistas (sobre los atributos que referencian campos de texto o botones).

Ejemplo práctico & problema que resuelve:

Ejemplo: @FXML private TextField txtTitulo;

Problema que resuelve: Evita que el controlador pierda de vista o ignore los elementos visuales de pantalla.

13. TableView

Definición formal: Control de interfaz de usuario en JavaFX para visualizar y editar datos tabulares distribuidos en filas y columnas.

Definición en mis palabras: Una tabla que despliega registros ordenados directamente en la ventana.

Ubicación en el código: En las pantallas de consulta de la aplicación.

Ejemplo práctico & problema que resuelve:

Ejemplo: <TableView fx:id="tblLibros"/>

Problema que resuelve: Facilita mostrar muchos datos al mismo tiempo sin que se vea un relajo en la pantalla.

14. ObservableList


Definición formal: Lista especializada de JavaFX que permite a los escuchas rastrear cambios en sus elementos, notificando automáticamente a la interfaz gráfica.

Definición en mis palabras: Una lista que notifica a la interfaz como las tablas cuando se agregan, modifican o eliminan elementos.

Ubicación en el código: En los controladores cuando llenamos las tablas de JavaFX.

Ejemplo práctico & problema que resuelve:

Ejemplo: ObservableList<Libro> lista = FXCollections.observableArrayList();

Problema que resuelve: Evita tener que cerrar y volver a abrir la ventana para ver reflejados los cambios nuevos en la base de datos.

15. Singleton


Definición formal: Patrón de diseño creacional que garantiza que una clase tenga una única instancia en todo el programa y proporciona un punto de acceso global a ella.

Definición en mis palabras: Hacer que solo pueda existir una sola conexión abierta para no gastar memoria ni armar caos.

Ubicación en el código: En la clase que maneja la única conexión de la base de datos.

Ejemplo práctico & problema que resuelve:

Ejemplo: Un método getInstancia() que revisa si el objeto ya existe antes de crearlo otra vez.

Problema que resuelve: Evita que se creen múltiples conexiones idénticas por accidente que saturen la computadora o la base de datos.

16. Excepción


Definición formal: Evento que ocurre durante la ejecución de un programa y que interrumpe el flujo normal de las instrucciones debido a un error.

Definición en mis palabras: Como un fallo de red al conectar MySQL que altera el funcionamiento.

Ubicación en el código: En bloques de código donde intentamos conectar a MySQL o convertir textos a números.

Ejemplo práctico & problema que resuelve:

Ejemplo: try { ... } catch (Exception e) { e.printStackTrace(); }

Problema que resuelve: Evita que el programa se cierre de golpe; permite atrapar el error y avisarle al usuario qué pasó.

17. Interfaz


Definición formal: Contrato de programación en Java que declara un conjunto de métodos abstractos que una clase está obligada a implementar.

Definición en mis palabras: Una lista de reglas o compromisos que una clase promete cumplir obligatoriamente al momento de programar.

Ubicación en el código: En la capa de datos del proyecto (por ejemplo, en el archivo DAO antes de pasarlo a la implementación).

Ejemplo práctico & problema que resuelve:

Ejemplo: public interface LibroDAO { boolean insertar(Libro libro); List<Libro> listar(); }

Problema que resuelve: Evita que se te olvide programar funciones importantes; te obliga a seguir las reglas exactas para que el programa no falle.

18. Polimorfismo


Definición formal: Principio de la Programación Orientada a Objetos que permite que objetos de distintas clases respondan de manera diferente a una misma invocación de método.

Definición en mis palabras: La capacidad de que sigan una orden que pueda ser ejecutada por diferentes objetos adaptándose a lo que cada uno necesita hacer.

Ubicación en el código: Al sobrescribir métodos (usando @Override) en las clases que implementan interfaces o heredan de otra.

Ejemplo práctico & problema que resuelve:

Ejemplo: Un método general que procesa acciones de guardado, donde cada entidad ejecuta su propia versión del método.

Problema que resuelve: Evita la duplicación de código y la creación de múltiples nombres de funciones para tareas similares.

19. Tipo Primitivo
    Definición formal: Tipo de dato básico predefinido por el lenguaje Java que almacena valores simples directamente en la memoria.

    Definición en mis palabras: un dato basico que guarda  un valor como un numero entero o decimal

     Ubicación en el código: Al declarar variables simples dentro de los atributos de un modelo o métodos.

    jemplo práctico & problema que resuelve:

Ejemplo: int stock = 10; o double precio = 45.50;

Problema que resuelve: Permite almacenar datos numéricos o básicos de manera rápida

20. Clase Wrapper
 Definición formal: Clase que envuelve o encapsula un tipo de dato primitivo en un objeto, permitiendo tratarlo como una referencia y usar métodos adicionales.

Definición en mis palabras: es una clase que convierte un dato por ejemplo un numero entero lo convierte en un objeto 
Ubicación en el código: cuando pasamos parámetros que requieren objetos como en listas o bases de datos

Ejemplo práctico & problema que resuelve:

Ejemplo: Integer idLibro = 5; (en lugar de usar int).
Permite que los datos  acepten valores nulos (null) y puedan ser utilizados en  JavaFX u otras estructuras que solo aceptan objetos.

    
