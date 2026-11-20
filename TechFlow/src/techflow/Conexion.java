package techflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {
    // [P5] Constante usada al abrir cada conexión. JDBC identifica el driver por el prefijo.
    private static final String URL_PREDETERMINADA = "jdbc:sqlite:techflow.db";

    private Conexion() { }

    public static Connection abrir() throws SQLException {
        // Permite usar otro archivo con -Dtechflow.url=jdbc:sqlite:C:/ruta/techflow.db
        String url = System.getProperty("techflow.url", URL_PREDETERMINADA);
        return DriverManager.getConnection(url);
    }
}
