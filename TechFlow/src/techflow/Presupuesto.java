package techflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Presupuesto implements Cotizable {
    private final int id;
    // Asociacion: el cliente existe independientemente del presupuesto.
    private final Cliente cliente;
    // Composicion: cada detalle se crea dentro de este presupuesto.
    private final List<Detalle> detalles = new ArrayList<>();

    public Presupuesto(int id, Cliente cliente) {
        if (id <= 0 || cliente == null)
            throw new IllegalArgumentException("ID y cliente obligatorios.");
        this.id = id;
        this.cliente = cliente;
    }
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<Detalle> getDetalles() { return Collections.unmodifiableList(detalles); }
    // Sobrecarga: mismo nombre, distinta lista de parametros.
    public void agregarServicio(Servicio servicio) { agregarServicio(servicio, 1); }
    public void agregarServicio(Servicio servicio, int cantidad) {
        detalles.add(new Detalle(servicio, cantidad));
    }
    @Override
    public double calcularPrecio() {
        double total = 0;
        for (Detalle detalle : detalles) total += detalle.calcularSubtotal();
        return total;
    }
    public static final class Detalle {
        private final Servicio servicio;
        private final int cantidad;
        private Detalle(Servicio servicio, int cantidad) {
            if (servicio == null || cantidad <= 0)
                throw new IllegalArgumentException("Servicio y cantidad validos obligatorios.");
            this.servicio = servicio;
            this.cantidad = cantidad;
        }
        public Servicio getServicio() { return servicio; }
        public int getCantidad() { return cantidad; }
        public double calcularSubtotal() {
            // Polimorfismo: se ejecuta la version del tipo real de servicio.
            return servicio.calcularPrecio() * cantidad;
        }
    }
}
