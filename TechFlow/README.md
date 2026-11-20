# TechFlow · Segunda entrega
Java de consola para cotizar servicios tecnológicos. abre docs/UML-acceso-datos.png para ver las relaciones nuevas. Los materiales de docs/primer-parcial documentan la versión anterior. Las clases del dominio y la demo de POO siguen disponibles.
 
## Probar el menú
1. Descomprime el ZIP y abre la carpeta TechFlow (la que contiene pom.xml). Usa JDK 17 o superior.
2. En **Eclipse**: File → Import → Maven → Existing Maven Projects → selecciona TechFlow → Finish. Cuando termine de resolver dependencias, abre Main.java → Run As → Java Application.
3. En **VS Code**: abre TechFlow con el soporte de Java y Maven instalado; espera la importación del pom.xml y ejecuta main en src/techflow/Main.java. Admite entrada desde la terminal integrada.
4. Alternativa de terminal: con Maven 3.6.3+ y un JDK en el PATH, ejecuta `mvn compile exec:java` dentro de TechFlow. Los archivos ejecutar.bat y ejecutar.sh lanzan ese comando. Para la primera descarga de dependencias necesitas internet.
5. El driver org.xerial:sqlite-jdbc (versión fijada en el pom) abre o crea techflow.db en el directorio de ejecución y el DAO crea clientes. No se requiere servidor. Si aparece “No suitable driver”, verifica que importaste el pom y que el IDE terminó de resolver la dependencia. Para ejecutar únicamente la demo original sin driver: compila los .java y pasa --demo a techflow.Main.

## Test 1
En una base nueva, opción 2 → lista vacía. Opción 1: registra Ana O'Connor / ana@ejemplo.com. Anota el ID que muestra. Opción 6 → consulta ese ID. Opción 2 → muestra el listado. Registra otro cliente y repite. Opción 3 → elige el ID de Ana, web, 10 horas y 3 páginas: USD 245. Opción 5 → demo independiente: USD 995. Opción 0 → cierra y vuelve a abrir desde el mismo directorio. El listado de clientes se conserva; el listado de presupuestos comienza vacío.

## Alcance de la verificación
Se compiló el código con destino Java 17 y se ejecutó la demo de POO en OpenJDK 21. Las cinco sentencias del DAO se verificaron directamente en SQLite (incluyendo nombres con apóstrofo y datos después de reabrir). La prueba completa del menú con el driver Xerial está pendiente: el entorno de preparación no permitió descargar el JAR.
