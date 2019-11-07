package agenciaviajes.negocio;


import java.util.ArrayList;
import mvcf.AModel;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.Properties;

import agenciaviajes.acceso.ServicioServidorCentralSocket;
import com.google.gson.JsonObject;
import agenciaviajes.acceso.IServidorCentral;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Representa el modelo (Observable) de datos Cuando hay cambios en el estado,
 * notifica a todas sus vistas (observadores)
 *
 * @author Julio, Libardo, Ricardo
 */
public class GestorClientesPotenciales extends AModel {

    
    private final IServidorCentral servidorCentral;

    public GestorClientesPotenciales() {
    
        servidorCentral=new ServicioServidorCentralSocket();
    }

    /**
     * Trae de la base de datos todos los clientes
     *
     * @return
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public ArrayList<Cliente> consultarClientes() throws ClassNotFoundException, SQLException {
        //Obtiene el objeto json serializado al servidor de la registraduria
        String json = servidorCentral.consultarClientesServicio();
        if (!json.equals("NO_ENCONTRADO")) {
            //Lo encontró
            ArrayList<Cliente> lista = deserializarClientes(json);
            return lista;
        }
        return null;
    }
   
    
    public ArrayList<Cliente> deserializarClientes(String arrayJsonSerializado) {
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        //System.out.println(arrayJsonSerializado);
        String array[]=arrayJsonSerializado.split(";");
        for (String elem : array) {
            Cliente cli = new Cliente();
            parseToCliente(cli,elem);
           clientes.add(cli);
        }
        return clientes;
    }
     private void parseToCliente(Cliente cliente, String json) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(json, Properties.class);
        cliente.setId(properties.getProperty("id"));
        cliente.setNombres(properties.getProperty("nombres"));
        cliente.setApellidos(properties.getProperty("apellidos"));
        cliente.setFechaNac(properties.getProperty("fechaNac"));
        cliente.setSexo(properties.getProperty("sexo"));
        cliente.setEmail(properties.getProperty("email"));
    }
    
    public ArrayList<Plan> consultarPlanes() throws ClassNotFoundException, SQLException {
        //Obtiene el objeto json serializado al servidor de la registraduria
        String json = servidorCentral.consultarPlanesServicio();
        if (!json.equals("NO_ENCONTRADO")) {
            //Lo encontró
            ArrayList<Plan> lista = deserializarPlanes(json);
            return lista;
        }
        return null;
    }
    public ArrayList<Plan> deserializarPlanes(String arrayJsonSerializado) {
        ArrayList<Plan> planes = new ArrayList<Plan>();
        //System.out.println(arrayJsonSerializado);
        String array[]=arrayJsonSerializado.split(";");
        for (String elem : array) {
            Plan plan = new Plan();
            parseToPlan(plan,elem);
           planes.add(plan);
        }
        return planes;
    }
     private void parseToPlan(Plan plan, String json) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(json, Properties.class);
        plan.setId(properties.getProperty("id"));
        plan.setDescripcion(properties.getProperty("descripcion"));
        plan.setNombre(properties.getProperty("nombre"));
        plan.setRangoEdad1(properties.getProperty("rangoEdad1"));
        plan.setRangoEdad2(properties.getProperty("rangoEdad2"));
    }

}
