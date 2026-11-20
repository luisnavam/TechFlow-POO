package techflow;

public class Automatizacion extends Servicio {
    private final int procesos;
    public Automatizacion(int horas, int procesos) {
        super("Automatizacion", 25, horas);
        if (procesos <= 0) throw new IllegalArgumentException("Procesos deben ser positivos.");
        this.procesos = procesos;
    }
    public int getProcesos() { return procesos; }
    @Override
    public double calcularPrecio() { return calcularBase() + procesos * 40; }
}
