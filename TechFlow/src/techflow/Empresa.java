package techflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Gestion del negocio; no lee teclado ni imprime el menu. */
public class Empresa {
    private final ClienteRepositorio clientes; // [P2] Depende del contrato del repositorio.
    private final List<Presupuesto> presupuestos = new ArrayList<>();
    public Empresa(ClienteRepositorio clientes) {
        this.clientes = clientes;
    }
    private int siguientePresupuesto = 1;

    public Cliente registrarCliente(String nombre, String email) {
        // Primero se validan los datos del negocio; después se envían al repositorio.
        return clientes.guardar(Cliente.validarNombre(nombre), Cliente.validarEmail(email));
    }
    public List<Cliente> getClientes() { return clientes.listar(); }
    public List<Presupuesto> getPresupuestos() { return Collections.unmodifiableList(presupuestos); }
    public Cliente buscarCliente(int id) {
        Cliente encontrado = clientes.buscarPorId(id);
        if (encontrado != null) return encontrado;
        throw new IllegalArgumentException("Cliente no encontrado.");
    }
    public Presupuesto crearPresupuesto(int clienteId) {
        Presupuesto presupuesto = new Presupuesto(siguientePresupuesto, buscarCliente(clienteId));
        presupuestos.add(presupuesto);
        siguientePresupuesto++;
        return presupuesto;
    }
}
