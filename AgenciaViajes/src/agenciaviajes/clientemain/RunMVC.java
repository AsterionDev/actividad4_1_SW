package agenciaviajes.clientemain;

import agenciaviajes.negocio.GestorClientesPotenciales;
import agenciaviajes.presentacion.GUIClientesPotenciales;
import agenciaviajes.presentacion.GUIClientesPotencialesController;
import java.sql.SQLException;

/**
 * Es el pegamento de la aplición
 *
 * @author Libardo, Julio, Ricardo
 */
public class RunMVC {

    public RunMVC() throws ClassNotFoundException, SQLException {
        GestorClientesPotenciales gestor = new GestorClientesPotenciales();

        //PRIMERA VISTA
        GUIClientesPotenciales view1 = new GUIClientesPotenciales();
        gestor.addView(view1);
        GUIClientesPotencialesController control = new GUIClientesPotencialesController(gestor, view1);
        view1.setVisible(true);

      

        // Enlaza el action controller de los botones al controlador y fija el comando de acción
        view1.getBtnBuscar().addActionListener(control);
        view1.getBtnBuscar().setActionCommand("Buscar");


    }
}
