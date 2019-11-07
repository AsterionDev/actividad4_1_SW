package agenciaviajes.acceso;

import agenciaviajes.negocio.Cliente;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahurtado, wpantoja, rzambran
 */
public class ServicioServidorCentralSocket implements IServidorCentral {

    private Socket socket = null;
    private Scanner entradaDecorada;
    private PrintStream salidaDecorada;
    private final String IP_SERVIDOR = "localhost";
    private final int PUERTO = 5003;


     private String leerFlujoEntradaSalida(String solicitud) throws IOException {
        String respuesta = "";
        entradaDecorada = new Scanner(socket.getInputStream());
        salidaDecorada = new PrintStream(socket.getOutputStream());
        salidaDecorada.flush();
        // Usando el protocolo de comunicación
        salidaDecorada.println(solicitud);
        if (entradaDecorada.hasNextLine()) {
            respuesta = entradaDecorada.nextLine();
        }
        return respuesta;
    }

    private void cerrarFlujos() {
        salidaDecorada.close();
        entradaDecorada.close();
    }

    private void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void conectar(String address, int port) throws IOException {
        socket = new Socket(address, port);
        System.out.println("Conectado");
    }

    @Override
    public String consultarClientesServicio() {
         String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalida("consultarClientes");
            cerrarFlujos();
            desconectar();

        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    @Override
    public String consultarPlanesServicio() {
         String respuesta = null;
        try {
            conectar(IP_SERVIDOR, PUERTO);
            respuesta = leerFlujoEntradaSalida("consultarPlanes");
            cerrarFlujos();
            desconectar();

        } catch (IOException ex) {
            Logger.getLogger(ServicioServidorCentralSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
}
