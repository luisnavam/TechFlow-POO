package techflow;

// [P3] El negocio puede informar un fallo de persistencia sin depender de java.sql.
public class RepositorioException extends RuntimeException {
    public RepositorioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
