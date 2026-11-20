package techflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// [P1] Implementación del repositorio. DAO = Data Access Object (objeto de acceso a datos).
public class ClienteDAO implements ClienteRepositorio {
    // [P4][P5] Aquí se ALMACENA el texto SQL. La tabla y sus FILAS viven en el archivo SQLite.
    private static final String SQL_CREAR = "CREATE TABLE IF NOT EXISTS clientes ("
            + "id INTEGER PRIMARY KEY, nombre TEXT NOT NULL, email TEXT NOT NULL)";
    private static final String SQL_INSERTAR = "INSERT INTO clientes (nombre, email) VALUES (?, ?)";
    private static final String SQL_ULTIMO_ID = "SELECT last_insert_rowid() AS id";
    private static final String SQL_LISTAR = "SELECT id, nombre, email FROM clientes ORDER BY id";
    private static final String SQL_BUSCAR = "SELECT id, nombre, email FROM clientes WHERE id = ?";

    // Preparación del esquema al iniciar. No forma parte del contrato del negocio.
    public void inicializar() {
        try (Connection cn = Conexion.abrir();
             PreparedStatement ps = cn.prepareStatement(SQL_CREAR)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RepositorioException("No se pudo preparar la tabla clientes.", e);
        }
    }

    // [P1] Cuerpo de guardar, declarado en ClienteRepositorio.
    @Override
    public Cliente guardar(String nombre, String email) {
        try (Connection cn = Conexion.abrir();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERTAR)) {
            ps.setString(1, nombre); // primer ? (los índices comienzan en 1)
            ps.setString(2, email);  // segundo ?; el valor no se concatena al SQL
            ps.executeUpdate();      // INSERT: modifica filas
            // SQLite: el último ID corresponde a ESTA conexión, no a otro usuario.
            try (PreparedStatement consulta = cn.prepareStatement(SQL_ULTIMO_ID);
                 ResultSet rs = consulta.executeQuery()) {
                if (!rs.next()) throw new SQLException("No se obtuvo el ID del cliente.");
                return new Cliente(rs.getInt("id"), nombre, email);
            }
        } catch (SQLException e) { // [P3] Error JDBC: conservamos la causa original.
            throw new RepositorioException("Error al registrar el cliente.", e);
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>(); // List: contrato; ArrayList: implementación
        try (Connection cn = Conexion.abrir();
             PreparedStatement ps = cn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) { // SELECT: devuelve un cursor
            while (rs.next()) clientes.add(leerCliente(rs)); // una fila -> un objeto
            return clientes; // si no hay filas, devuelve lista vacía
        } catch (SQLException e) {
            throw new RepositorioException("Error al listar clientes.", e);
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        try (Connection cn = Conexion.abrir();
             PreparedStatement ps = cn.prepareStatement(SQL_BUSCAR)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? leerCliente(rs) : null;
            }
        } catch (SQLException e) {
            throw new RepositorioException("Error al buscar el cliente.", e);
        }
    }

    private Cliente leerCliente(ResultSet rs) throws SQLException {
        return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"));
    }
}
