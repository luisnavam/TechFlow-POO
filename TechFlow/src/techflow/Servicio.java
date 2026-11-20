package techflow;

/** Abstraccion: concentra lo comun y delega el calculo a cada especialidad. */
public abstract class Servicio implements Cotizable {
    private final String nombre;
    private final double tarifaHora;
    private final int horas;

    protected Servicio(String nombre, double tarifaHora, int horas) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Nombre obligatorio.");
        if (!Double.isFinite(tarifaHora) || tarifaHora <= 0 || horas <= 0)
            throw new IllegalArgumentException("Tarifa y horas deben ser positivas.");
        this.nombre = nombre;
        this.tarifaHora = tarifaHora;
        this.horas = horas;
    }
    public String getNombre() { return nombre; }
    public double getTarifaHora() { return tarifaHora; }
    public int getHoras() { return horas; }
    protected double calcularBase() { return tarifaHora * horas; }
    @Override
    public abstract double calcularPrecio();
}
