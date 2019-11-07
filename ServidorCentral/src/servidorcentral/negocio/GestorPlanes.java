/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorcentral.negocio;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class GestorPlanes {
     private ConectorJdbc con;

    public GestorPlanes() {
        con = new ConectorJdbc();
    }

    public ArrayList<Plan> consultarPlanes() throws ClassNotFoundException, SQLException{
        ArrayList<Plan> lista=new ArrayList();
        con.conectarse();
        
        con.crearConsulta("SELECT * FROM planes");
        while(con.getResultado().next()) {
            //(String id, String nombres, String apellidos, String fechaNac, String sexo,String email)
            lista.add(new Plan(con.getResultado().getString("id"),con.getResultado().getString("nombre"),con.getResultado().getString("descripcion"),con.getResultado().getString("rangoedad1"),con.getResultado().getString("rangoedad2")));
        }
            
        con.desconectarse();
        return lista;  
    }
    
    
}
