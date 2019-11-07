/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorcentral.negocio;



import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa el modelo (Observable) de datos Cuando hay cambios en el estado,
 * notifica a todas sus vistas (observadores)
 *
 * @author Julio, Libardo, Ricardo
 */
public class GestorClientes{

    
    private ConectorJdbc con;

    public GestorClientes() {
        con = new ConectorJdbc();
    }

    public ArrayList<Cliente> consultarClientes() throws ClassNotFoundException, SQLException{
        ArrayList<Cliente> lista=new ArrayList();
        con.conectarse();
        
        con.crearConsulta("SELECT * FROM clientes");
        while(con.getResultado().next()) {
            //(String id, String nombres, String apellidos, String fechaNac, String sexo,String email)
            lista.add(new Cliente(con.getResultado().getString("id"),con.getResultado().getString("nombres"),con.getResultado().getString("apellidos"),con.getResultado().getString("fechaNac"),con.getResultado().getString("sexo"),con.getResultado().getString("email")));
        }
            
        con.desconectarse();
        return lista;  
    }
    
    
    
}
