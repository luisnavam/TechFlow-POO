package techflow;

public class Cliente {
    // Encapsulamiento: el estado solo se modifica mediante operaciones validadas.
    private final int id;
    private String nombre;
    private String email;

    public Cliente(int id, String nombre, String email) {
        if (id <= 0) throw new IllegalArgumentException("ID debe ser positivo.");
        this.id = id;
        setNombre(nombre);
        setEmail(email);
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre);
    }
    static String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Nombre obligatorio.");
        return nombre.trim();
    }
    public void setEmail(String email) {
        this.email = validarEmail(email);
    }
    static String validarEmail(String email) {
        if (email == null || !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))
            throw new IllegalArgumentException("Email invalido (ejemplo: ana@empresa.com).");
        return email.trim();
    }
    @Override
    public String toString() { return id + " - " + nombre + " (" + email + ")"; }
}
