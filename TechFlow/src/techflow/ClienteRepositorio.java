package techflow;

import java.util.List;

// [P2] Contrato que utiliza Empresa. Aquí declaramos operaciones, sin escribir SQL.
public interface ClienteRepositorio {
    Cliente guardar(String nombre, String email);
    List<Cliente> listar();
    // Si no hay una fila con ese ID, devuelve null.
    Cliente buscarPorId(int id);
}
