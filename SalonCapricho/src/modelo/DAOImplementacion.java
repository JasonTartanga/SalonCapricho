package modelo;

import clases.Cliente;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xDoble_Jx
 */
public class DAOImplementacion implements DAO {

    private Connection con = null;
    private PreparedStatement stmt;

    //*************** INSERTS ***************/
    final private String REGISTRAR_CLIENTE = "INSERT INTO cliente VALUES (?, ?, ?, ?, ?)";

    //*************** SELECTS ***************/
    final private String GENERAR_ID_CLIENTE = "SELECT id_cliente FROM cliente ORDER BY id_cliente desc LIMIT 1";
    final private String LISTAR_CLIENTES = "SELECT * FROM cliente";

    final private String BUSCAR_CLIENTES = "SELECT * FROM cliente WHERE id_cliente = ?";

    //*************** UPDATE ***************/
    final private String MODIFICAR_CLIENTES = "UPDATE cliente SET nombre = ?, apellido = ?, telefono = ? WHERE id_cliente = ?";
    //*************** DELETE ***************/
    final private String ELIMINAR_CLIENTES = "DELETE FROM cliente WHERE id_cliente = ?";

    public void abrirConexion() {

        try {
            Properties configBDA = new Properties();
            String rutaProyecto = System.getProperty("user.dir");
            FileInputStream fis = new FileInputStream(rutaProyecto + "\\src\\modelo\\ConfigBD.properties");
            configBDA.load(fis);

            final String URL = configBDA.getProperty("url");
            final String USER = configBDA.getProperty("user");
            final String PASSWORD = configBDA.getProperty("password");

            con = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void cerrarConexion() {
        try {
            if (con != null) {
                con.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente recuperarCliente(ResultSet rs) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Cliente cli = new Cliente();

        try {
            cli.setId_cliente(rs.getString("id_cliente"));
            cli.setNombre(rs.getString("nombre"));
            cli.setApellido(rs.getString("apellido"));
            cli.setTelefono(rs.getInt("telefono"));
            cli.setFecha_reg(LocalDate.parse(rs.getString("fecha_reg"), formateador));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cli;
    }

    public PreparedStatement prepararCliente(Cliente cli) {
        try {
            stmt.setString(1, cli.getId_cliente());
            stmt.setString(2, cli.getNombre());
            stmt.setString(3, cli.getApellido());
            stmt.setInt(4, cli.getTelefono());
            stmt.setDate(5, Date.valueOf(cli.getFecha_reg()));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stmt;
    }

    @Override

    public void registrarCliente(Cliente cli) {
        this.abrirConexion();

        try {
            stmt = con.prepareStatement(REGISTRAR_CLIENTE);
            stmt = this.prepararCliente(cli);
            System.out.println(stmt);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        this.abrirConexion();

        try {
            stmt = con.prepareStatement(LISTAR_CLIENTES);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cli = new Cliente();
                cli = this.recuperarCliente(rs);
                clientes.add(cli);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.cerrarConexion();
        return clientes;
    }

    @Override
    public Cliente buscarCliente(String id_cliente) {
        Cliente cli = null;

        this.abrirConexion();
        try {
            stmt = con.prepareStatement(BUSCAR_CLIENTES);
            stmt.setString(1, id_cliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cli = new Cliente();
                cli = this.recuperarCliente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.cerrarConexion();
        return cli;
    }

    @Override
    public void modificarCliente(Cliente cli) {
        this.abrirConexion();

        try {
            stmt = con.prepareStatement(MODIFICAR_CLIENTES);
            stmt.setString(1, cli.getNombre());
            stmt.setString(2, cli.getApellido());
            stmt.setInt(3, cli.getTelefono());
            stmt.setString(4, cli.getId_cliente());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.cerrarConexion();
    }

    @Override
    public void eliminarCliente(String id_cliente) {
        this.abrirConexion();

        try {
            stmt = con.prepareStatement(ELIMINAR_CLIENTES);
            stmt.setString(1, id_cliente);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.cerrarConexion();
    }

    @Override
    public String generarIdCliente() {
        String id = null;
        this.abrirConexion();

        try {
            stmt = con.prepareStatement(GENERAR_ID_CLIENTE);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = "U-" + String.format("%04d", Integer.parseInt(rs.getString("id_cliente").substring(2, 6)) + 1);
                System.out.println(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.cerrarConexion();
        return id;
    }

}
