/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorcentral.servicio;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidorcentral.negocio.Cliente;
import servidorcentral.negocio.GestorClientes;
import servidorcentral.negocio.GestorPlanes;
import servidorcentral.negocio.Plan;

public class ServidorCentralServer implements Runnable {

    private final GestorClientes gestorC;
    private final GestorPlanes gestorP;

    private static ServerSocket ssock;
    private static Socket socket;

    private Scanner entradaDecorada;
    private PrintStream salidaDecorada;
    private static final int PUERTO = 5003;

    /**
     * Constructor
     */
    public ServidorCentralServer() {
        gestorC = new GestorClientes();
        gestorP = new GestorPlanes();
    }
    /**
     * Logica completa del servidor
     */
    public void iniciar() {
        abrirPuerto();

        while (true) {
            esperarAlCliente();
            lanzarHilo();
        }
    }

    /**
     * Lanza el hilo
     */
    private static void lanzarHilo() {
        new Thread(new ServidorCentralServer()).start();
    }

    private static void abrirPuerto() {
        try {
            ssock = new ServerSocket(PUERTO);
            System.out.println("Escuchando por el puerto " + PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Espera que el cliente se conecta y le devuelve un socket
     */
    private static void esperarAlCliente() {
        try {
            socket = ssock.accept();
            System.out.println("Cliente conectado");
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cuerpo del hilo
     */
    @Override
    public void run() {
        try {
            crearFlujos();
            leerFlujos();
            cerrarFlujos();

        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServidorCentralServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServidorCentralServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea los flujos con el socket
     *
     * @throws IOException
     */
    private void crearFlujos() throws IOException {
        salidaDecorada = new PrintStream(socket.getOutputStream());
        entradaDecorada = new Scanner(socket.getInputStream());
    }

    /**
     * Lee los flujos del socket
     */
    private void leerFlujos() throws ClassNotFoundException, SQLException {
        if (entradaDecorada.hasNextLine()) {
            // Extrae el flujo que envía el cliente
            String peticion = entradaDecorada.nextLine();
            decodificarPeticion(peticion);

        } else {
            salidaDecorada.flush();
            salidaDecorada.println("NO_ENCONTRADO");
        }
    }

    /**
     * Decodifica la petición, extrayeno la acción y los parámetros
     *
     * @param peticion petición completa al estilo
     * "consultarVehiculosPorConductor,100"
     */
    private void decodificarPeticion(String peticion) throws ClassNotFoundException, SQLException {
        StringTokenizer tokens = new StringTokenizer(peticion, ",");
        String parametros[] = new String[10];

        int i = 0;
        while (tokens.hasMoreTokens()) {
            parametros[i++] = tokens.nextToken();
        }
        String accion = parametros[0];
        procesarAccion(accion, parametros);

    }

    /**
     * Segun el protocolo decide qué accion invocar
     *
     * @param accion acción a procesar
     * @param parametros parámetros de la acción
     */
    private void procesarAccion(String accion, String parametros[]) throws ClassNotFoundException, SQLException {
        switch (accion) {
            case "consultarClientes":
                String id = parametros[1];
                ArrayList<Cliente> lista=gestorC.consultarClientes();
                
                if (lista.isEmpty()) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(serializarClientes(lista));
                }
                break;
            case "consultarPlanes":
                id = parametros[1];
                ArrayList<Plan> listaPlanes=gestorP.consultarPlanes();
                
                if (listaPlanes.isEmpty()) {
                    salidaDecorada.println("NO_ENCONTRADO");
                } else {
                    salidaDecorada.println(serializarPlanes(listaPlanes));
                }
                break;
        }
    }

    /**
     * Cierra los flujos de entrada y salida
     *
     * @throws IOException
     */
    private void cerrarFlujos() throws IOException {
        salidaDecorada.close();
        entradaDecorada.close();
        socket.close();
    }

    
    
    
      private String parseToJSON(Cliente cli) {
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("id", cli.getId());
        jsonobj.addProperty("nombres", cli.getNombres());
        jsonobj.addProperty("apellidos", cli.getApellidos());
        jsonobj.addProperty("fechaNac", cli.getFechaNac());
        jsonobj.addProperty("sexo", cli.getSexo());
        jsonobj.addProperty("email", cli.getEmail());
        return jsonobj.toString();
    }
    
    private String serializarClientes(ArrayList<Cliente> lista){
        String resultado="";
        for(Cliente v : lista){
            resultado+= parseToJSON(v)+";";
        }
        return resultado;
    }
    
    private String parseToJSON(Plan plan) {
        JsonObject jsonobj = new JsonObject();
        jsonobj.addProperty("id", plan.getId());
        jsonobj.addProperty("nombre", plan.getNombre());
        jsonobj.addProperty("descripcion", plan.getDescripcion());
        jsonobj.addProperty("rangoEdad1", plan.getRangoEdad1());
        jsonobj.addProperty("rangoEdad2", plan.getRangoEdad2());
        return jsonobj.toString();
    }
    
    private String serializarPlanes(ArrayList<Plan> lista){
        String resultado="";
        for(Plan v : lista){
            resultado+= parseToJSON(v)+";";
        }
        return resultado;
    }
}
