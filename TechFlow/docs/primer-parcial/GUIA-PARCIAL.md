# TechFlow — guía para el primer parcial

## 1. Qué hace y qué no hace todavía
Aplicación Java de consola para una empresa que ofrece desarrollo web, automatización de procesos y desarrollo de software. Permite registrar clientes, consultar clientes, crear presupuestos de un servicio y consultar presupuestos. La opción 5 demuestra los tres servicios en un mismo presupuesto.

Las tarifas son ficticias y se expresan en USD; no representan precios de mercado. No ejecuta automatizaciones ni construye sitios: administra su contratación y cotización. No usa frameworks ni base de datos. Los datos viven en memoria y se pierden al cerrar el programa. No hay interfaz gráfica en esta entrega.

## 2. Ejecutar en Windows / VS Code
1. Descomprime TechFlow-POO.zip. Abre la carpeta TechFlow completa en VS Code, no solamente un archivo.
2. Necesitas un **JDK 17 o superior**. En la terminal ejecuta `java -version` y `javac -version`. Ambos deben responder. Si javac no existe, falta el JDK o su configuración de PATH; un JRE por sí solo no alcanza.
3. Si ya tienes las extensiones Java de VS Code, abre `src/techflow/Main.java` y pulsa **Run** sobre `main`. Para usar el menú, escribe en la terminal integrada. Si no aparecen esos controles, utiliza los comandos siguientes; no dependes de extensiones para compilar.
4. Terminal de PowerShell o CMD, dentro de TechFlow:

```sh
javac -encoding UTF-8 -d bin src/techflow/*.java
java -cp bin techflow.Main
```

También puedes abrir `ejecutar.bat` con doble clic. En Linux/macOS: `bash ejecutar.sh`.

## 3. Importar en Eclipse
1. File → Import → General → Existing Projects into Workspace.
2. Select root directory → elige la carpeta TechFlow descomprimida → marca TechFlow → Finish.
3. Verifica que Eclipse tenga configurado un JDK 17 o superior en Preferences → Java → Installed JREs.
4. Abre `src/techflow/Main.java` → clic derecho → Run As → Java Application.
5. Escribe las opciones en la pestaña Console. No ejecutes las clases de servicios: el punto de entrada es Main.

## 4. Cómo se diseñó, paso a paso
1. Identificamos las entidades del problema: Cliente, Servicio y Presupuesto.
2. Servicio reúne nombre, horas y tarifa. No tiene una fórmula única, por eso es abstracto.
3. DesarrolloWeb, Automatizacion y DesarrolloSoftware heredan de Servicio y aportan su fórmula.
4. Cotizable expresa una capacidad compartida: calcularPrecio. Tanto Servicio como Presupuesto implementan ese contrato.
5. Un presupuesto se asocia a un cliente y contiene detalles; cada detalle vincula un servicio con su cantidad.
6. Empresa administra los registros y los IDs. Main administra el teclado, el menú y la presentación. Esta separación permite agregar una interfaz gráfica más adelante sin colocar el teclado dentro de Cliente o Servicio.

## 5. Los cuatro pilares, ubicados en el código
| Pilar | Archivo / lugar | Qué puedes explicar |
|---|---|---|
| Encapsulamiento | Cliente: atributos privados, setNombre y setEmail; Empresa: listas privadas y vistas no modificables | No permito cambiar el estado arbitrariamente. Un nombre vacío o email inválido se rechaza. No expongo listas que permitan agregar elementos sin validar. |
| Abstracción | Servicio: clase abstracta, calcularBase y calcularPrecio abstracto; Cotizable: interfaz | Defino qué es común y qué operación se necesita sin imponer una fórmula a todos los servicios. No se puede instanciar Servicio directamente. |
| Herencia | DesarrolloWeb, Automatizacion y DesarrolloSoftware: extends Servicio y super(...) | Los tres son servicios. Heredan getters y reutilizan calcularBase; el constructor de la base inicializa y valida lo común. Los atributos privados no se acceden directamente desde las subclases. |
| Polimorfismo | Presupuesto.Detalle.calcularSubtotal: servicio.calcularPrecio(); Main.demostrarPOO: Servicio[] | La referencia es de tipo Servicio, pero el objeto real determina la fórmula que se ejecuta. La misma llamada produce resultados distintos sin preguntar su tipo dentro del presupuesto. |

## 6. Otros conceptos relacionados con POO
| Concepto | Ejemplo real | Diferencia que debes entender |
|---|---|---|
| Clase y objeto | Cliente es una clase; new Cliente(...) crea un objeto | La clase define estructura/comportamiento; el objeto tiene identidad y estado concreto. |
| Constructor | Cliente(...), DesarrolloWeb(...) | Inicializa un objeto válido; no tiene tipo de retorno. |
| Interfaz / realización | Servicio implements Cotizable y Presupuesto implements Cotizable | Comparten una capacidad sin necesitar que Presupuesto herede de Servicio. Un presupuesto no es un servicio. |
| Sobrescritura | calcularPrecio en las tres subclases, con @Override | Misma firma, implementación especializada; permite el polimorfismo dinámico. |
| Sobrecarga | Presupuesto.agregarServicio(Servicio) y agregarServicio(Servicio, int) | Mismo nombre, distintos parámetros. La primera delega en la segunda con cantidad 1. Se resuelve por la firma, no por el tipo real del objeto. |
| Asociación | Presupuesto → Cliente; Detalle → Servicio | Un objeto referencia a otro. Un cliente puede existir sin presupuesto. |
| Agregación | Empresa registra una colección de clientes | Relación de conjunto con miembros de vida independiente. UML la representa con rombo vacío; no implica borrado en cascada. |
| Composición | Presupuesto contiene Presupuesto.Detalle | Detalle tiene constructor privado y se crea dentro del presupuesto. Su pertenencia es exclusiva a ese presupuesto. El rombo está en Presupuesto. Java no implementa eliminación automática en cascada: hablamos del diseño del ciclo de vida. |
| Visibilidad | private, public, protected | private protege estado; public expone operaciones; protected permite reutilizar calcularBase desde subclases. |
| this y super | this.nombre en Cliente; super(...) en DesarrolloWeb | this refiere al objeto actual; super invoca el constructor o comportamiento de la base. |
| static y final | Main.main; IDs final; Detalle static final | static pertenece a la clase. final evita reasignar una referencia/valor; no vuelve inmutable automáticamente al objeto referenciado. Detalle static no necesita una referencia implícita al Presupuesto externo. |
| Colecciones / genéricos | List<Cliente>, List<Detalle> | Colecciones con elementos de tipo definido; no son un pilar por sí mismas. |
| Excepciones | IllegalArgumentException; try/catch en Main | Se rechazan datos inválidos y el menú informa el problema. Es manejo de errores, no un quinto pilar. |
| Colaboración | Empresa crea Presupuesto; Presupuesto suma Detalle; Detalle consulta Servicio | Los objetos resuelven partes del problema mediante llamadas a métodos. |

No existe una lista universal de “todos los conceptos POO”. Este proyecto cubre los cuatro pilares y los recursos habituales de un curso introductorio. Si tu profesor exige una lista específica, contrástala con esta tabla. SOLID, patrones y persistencia son temas relacionados, pero no reemplazan los pilares.

## 7. UML sencillo para exponer
Abre `docs/UML.png` o `docs/UML.svg`. Se muestran únicamente las siete clases del núcleo, pocos atributos y sus métodos principales. Detalle es la clase anidada Presupuesto.Detalle del código. La vista omite Main, Empresa, Cotizable, constructores y getters para facilitar la explicación; no significa que esas partes no existan.

- Triángulo hacia Servicio: las tres especialidades heredan de Servicio.
- Flecha Presupuesto → Cliente: cada presupuesto corresponde a un cliente.
- Rombo lleno en Presupuesto: contiene cero o muchos detalles.
- Flecha Detalle → Servicio: cada detalle referencia un servicio.
- `+` significa público y `-` privado.

Para explicar encapsulamiento, señala los atributos privados. Para abstracción, señala Servicio (abstracta). Para herencia, las flechas de las tres especialidades. Para polimorfismo, señala calcularPrecio en las especialidades y muestra en el código la llamada desde Detalle. La interfaz Cotizable se explica desde el código si el profesor pregunta: está omitida en esta vista.

`docs/UML-sencillo.puml` es la versión editable del dibujo. `docs/UML-completo.puml` queda como referencia detallada; para la exposición usa el sencillo.

## 8. Demo del parcial, de 5 a 7 minutos
1. Presenta el problema en una frase y muestra el UML.
2. Ejecuta el programa. Opción 1: registra Ana / ana@empresa.com. Opción 2: muestra el cliente.
3. Opción 3: ID 1, servicio 1 (web), horas 10, páginas 3. Resultado: **USD 245** = 10 × 20 + 3 × 15.
4. Opción 4: muestra el presupuesto registrado.
5. Opción 5: demo polimórfica. Web: 245; automatización: 330; software: 420. Total: **USD 995**. La demo no agrega registros al menú.
6. Abre Servicio y una subclase: señala abstract, extends, super y @Override.
7. Abre Presupuesto.Detalle.calcularSubtotal: aquí ocurre la llamada polimórfica real. El switch de Main solamente elige qué objeto crear: no es el polimorfismo.
8. Abre Cliente.setNombre y muestra que un nombre vacío se rechaza. Si falla una carga, la operación se cancela y puedes volver a intentarla desde el menú.
9. Cierra con qué agregarás en las próximas entregas.

## 9. Guion breve para defenderlo
“TechFlow administra clientes y presupuestos de una empresa de servicios tecnológicos. Primero identifiqué las entidades del negocio y separé la lógica del menú. Uso encapsulamiento para proteger y validar los datos. Servicio es abstracto porque todos comparten nombre, horas y tarifa, pero no calculan igual. Las tres especialidades heredan de esa clase y sobrescriben calcularPrecio. El presupuesto trabaja con referencias de tipo Servicio; al calcular cada detalle se ejecuta la fórmula del objeto real: ahí aparece el polimorfismo. Cotizable permite que servicios y presupuestos compartan el contrato de cotizar. Además, uso asociación con clientes, composición de detalles y sobrecarga al agregar servicios. Hoy la aplicación funciona en memoria; en las siguientes entregas agregaré gestión de proyectos y persistencia.”

## 10. Preguntas probables del profesor
- **¿Por qué Servicio es abstracto?** Porque representa una categoría general y requiere una fórmula concreta definida por cada especialidad.
- **¿Por qué además una interfaz?** Porque Presupuesto también calcula un precio, aunque no es un Servicio. La interfaz modela esa capacidad compartida.
- **¿Dónde está el polimorfismo?** En servicio.calcularPrecio() dentro de Detalle. Main también demuestra una lista de referencias Servicio con objetos diferentes y una referencia Cotizable a un Presupuesto.
- **¿Sobrecarga y sobrescritura son iguales?** No: sobrecarga cambia los parámetros; sobrescritura redefine una operación heredada con la misma firma.
- **¿Por qué no todos los atributos tienen setter?** Porque los IDs y parámetros de una cotización no necesitan cambiar en esta versión. Encapsular no significa crear setters para todo.
- **¿Una lista prueba composición?** No. La composición depende de pertenencia y ciclo de vida. Aquí los detalles tienen constructor privado y se crean desde el presupuesto propietario.
- **¿Puede un servicio tener varios detalles?** Sí: el servicio es una asociación, no una composición. Los detalles exclusivos son de cada presupuesto.
- **¿Qué pasa al cerrar?** Los registros en memoria se pierden. La persistencia se agrega en la tercera etapa.
- **¿Se puede agregar otro servicio?** Se crea otra subclase con su fórmula. Presupuesto no cambia; en esta interfaz sencilla se agrega la opción de creación al menú.
- **¿Esto cumple todos los principios SOLID?** No se afirma eso. Hay separación de responsabilidades y uso de contratos, pero el objetivo de esta entrega es demostrar POO, no un sistema completo con todas las arquitecturas o patrones.

## 11. Plan de las tres entregas
| Etapa | Alcance | Qué permite demostrar |
|---|---|---|
| Primer parcial: entregado | Clientes, tres servicios, presupuestos, menú y UML | Cuatro pilares, interfaz, relaciones, sobrecarga y validaciones. |
| Segundo parcial: pendiente | Agregar varios servicios a un presupuesto desde el menú; aprobar presupuesto; crear Proyecto con estado y responsable; editar clientes | Nuevas colaboraciones, enum de estados y reglas como no iniciar un proyecto sin presupuesto aprobado. Actualizar UML y demo. |
| Final: pendiente | Guardar/recuperar clientes y presupuestos en archivos o BD según consigna; seguimiento de proyectos; reportes por servicio; pruebas de reglas | Persistencia, separación entre almacenamiento y negocio, integridad de datos y flujo completo. Interfaz gráfica solo si se exige o alcanza el tiempo. |

No están implementadas las funcionalidades de las etapas 2 y 3. El primer avance está completo y deja lugar real para evolucionar.

## 12. Detalles técnicos y límites
Los cálculos usan double para mantener la explicación introductoria simple. Para dinero real conviene migrar a BigDecimal con redondeo explícito. La validación de email comprueba un formato básico, no verifica que exista. No se detectan clientes duplicados por email en esta entrega. Los IDs son correlativos durante la sesión. Las colecciones no modificables protegen su estructura, pero Cliente permite cambios mediante setters validados. La opción 3 crea un presupuesto de un servicio; la clase ya admite múltiples detalles, demostrados por la opción 5.
