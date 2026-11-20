package techflow;

// Herencia: es un Servicio y reutiliza sus atributos y comportamiento comun.
public class DesarrolloWeb extends Servicio {
    private final int paginas;
    public DesarrolloWeb(int horas, int paginas) {
        super("Desarrollo web", 20, horas);
        if (paginas <= 0) throw new IllegalArgumentException("Paginas deben ser positivas.");
        this.paginas = paginas;
    }
    public int getPaginas() { return paginas; }
    // Sobrescritura: calculo propio. Valores ficticios expresados en USD.
    @Override
    public double calcularPrecio() { return calcularBase() + paginas * 15; }
}
