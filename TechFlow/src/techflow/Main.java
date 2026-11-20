package techflow;

import java.util.Scanner;

/** Interfaz de consola. Ejecutar esta clase en Eclipse o VS Code. */
public class Main {
    private final Empresa empresa;
    private Main(Empresa empresa) { this.empresa = empresa; }
    private final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0 && "--demo".equals(args[0])) { demostrarPOO(); return; }
        try {
            ClienteDAO dao = new ClienteDAO();
            dao.inicializar();
            new Main(new Empresa(dao)).ejecutar(); // DAO concreto -> parámetro de tipo interfaz
        } catch (RepositorioException e) {
            informarFallo(e);
        }
    }

    private static void informarFallo(RepositorioException e) {
        System.err.println(e.getMessage() + " Detalle: " + e.getCause().getMessage());
    }

    private void ejecutar() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== TECHFLOW | Servicios tecnologicos ===");
            System.out.println("1. Registrar cliente\n2. Ver clientes\n3. Crear presupuesto");
            System.out.println("4. Ver presupuestos\n5. Demostracion de POO\n6. Buscar cliente por ID\n0. Salir");
            try {
                switch (leerEntero("Opcion: ")) {
                    case 1:
                        Cliente cliente = empresa.registrarCliente(leerTexto("Nombre: "), leerTexto("Email: "));
                        System.out.println("Cliente registrado: " + cliente);
                        break;
                    case 2: mostrarClientes(); break;
                    case 3: crearPresupuesto(); break;
                    case 4:
                        if (empresa.getPresupuestos().isEmpty()) System.out.println("No hay presupuestos.");
                        for (Presupuesto p : empresa.getPresupuestos()) mostrarPresupuesto(p);
                        break;
                    case 5: demostrarPOO(); break;
                    case 6: System.out.println(empresa.buscarCliente(leerEntero("ID: "))); break;
                    case 0: continuar = false; break;
                    default: System.out.println("Opcion inexistente.");
                }
            } catch (RepositorioException e) { // [P3] Fallo de acceso a datos durante una opción.
                informarFallo(e);
            } catch (IllegalArgumentException e) { // [P3] Dato inválido o cliente inexistente.
                System.out.println("Aviso: " + e.getMessage());
            } catch (java.util.NoSuchElementException e) {
                continuar = false; // Cierre limpio si termina la entrada.
            }
        }
        entrada.close();
        System.out.println("Hasta luego.");
    }
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }
    private int leerEntero(String mensaje) {
        try { return Integer.parseInt(leerTexto(mensaje)); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Ingrese un numero entero."); }
    }
    private boolean mostrarClientes() {
        java.util.List<Cliente> clientes = empresa.getClientes();
        if (clientes.isEmpty()) System.out.println("No hay clientes registrados.");
        for (Cliente cliente : clientes) System.out.println(cliente);
        return !clientes.isEmpty();
    }
    private void crearPresupuesto() {
        if (!mostrarClientes()) {
            System.out.println("Primero registre un cliente.");
            return;
        }
        int clienteId = leerEntero("ID del cliente: ");
        System.out.println("1. Web (20 USD/h + 15/pagina)");
        System.out.println("2. Automatizacion (25 USD/h + 40/proceso)");
        System.out.println("3. Software (30 USD/h + 60/modulo)");
        int tipo = leerEntero("Servicio: ");
        if (tipo < 1 || tipo > 3) throw new IllegalArgumentException("Servicio inexistente.");
        int horas = leerEntero("Horas estimadas: ");
        Servicio servicio;
        switch (tipo) {
            case 1: servicio = new DesarrolloWeb(horas, leerEntero("Paginas: ")); break;
            case 2: servicio = new Automatizacion(horas, leerEntero("Procesos: ")); break;
            default: servicio = new DesarrolloSoftware(horas, leerEntero("Modulos: "));
        }
        Presupuesto presupuesto = empresa.crearPresupuesto(clienteId);
        presupuesto.agregarServicio(servicio);
        mostrarPresupuesto(presupuesto);
    }
    private static void mostrarPresupuesto(Presupuesto presupuesto) {
        System.out.println("\nPresupuesto #" + presupuesto.getId() + " | " + presupuesto.getCliente().getNombre());
        for (Presupuesto.Detalle detalle : presupuesto.getDetalles()) {
            System.out.printf(java.util.Locale.US, "- %s | cantidad: %d | subtotal: USD %.2f%n",
                detalle.getServicio().getNombre(), detalle.getCantidad(), detalle.calcularSubtotal());
        }
        // Programar contra una interfaz: el presupuesto tambien es cotizable.
        Cotizable cotizable = presupuesto;
        System.out.printf(java.util.Locale.US, "TOTAL: USD %.2f%n", cotizable.calcularPrecio());
    }
    private static void demostrarPOO() {
        // Mismo tipo de referencia, tres objetos concretos diferentes.
        Servicio[] servicios = {new DesarrolloWeb(10, 3), new Automatizacion(10, 2), new DesarrolloSoftware(10, 2)};
        Cliente cliente = new Cliente(1, "Cliente demo", "demo@empresa.com");
        Presupuesto presupuesto = new Presupuesto(1, cliente);
        for (Servicio servicio : servicios) presupuesto.agregarServicio(servicio);
        mostrarPresupuesto(presupuesto); // 245 + 330 + 420 = 995 USD.
        System.out.println("Misma llamada calcularPrecio(); cada servicio usa su propia formula.");
        System.out.println("La demo es independiente: no modifica los registros del menu.");
    }
}
