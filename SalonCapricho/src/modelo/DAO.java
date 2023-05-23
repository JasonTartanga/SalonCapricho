/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

import clases.Cliente;
import java.util.List;

/**
 *
 * @author xDoble_Jx
 */
public interface DAO {

    //*************** INSERTS ***************/
    public void registrarCliente(Cliente cli);

    //*************** SELECT ***************/
    public List<Cliente> listarClientes();

    public Cliente buscarCliente(String id_cliente);

    public String generarIdCliente();

    //*************** UPDATE ***************/
    public void modificarCliente(Cliente cli);

    //*************** DELETE ***************/
    public void eliminarCliente(String id_cliente);
}
