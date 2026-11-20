package techflow;

public class DesarrolloSoftware extends Servicio {
    private final int modulos;
    public DesarrolloSoftware(int horas, int modulos) {
        super("Desarrollo de software", 30, horas);
        if (modulos <= 0) throw new IllegalArgumentException("Modulos deben ser positivos.");
        this.modulos = modulos;
    }
    public int getModulos() { return modulos; }
    @Override
    public double calcularPrecio() { return calcularBase() + modulos * 60; }
}
