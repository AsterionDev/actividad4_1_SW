/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenciaviajes.presentacion;

import agenciaviajes.negocio.Cliente;
import agenciaviajes.negocio.GestorClientesPotenciales;
import agenciaviajes.negocio.Plan;
import java.awt.event.ActionEvent;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvcf.AActionController;
import mvcf.AModel;
import mvcf.AView;

/**
 *
 * @author Asus
 */
public class GUIClientesPotencialesController extends AActionController{

    private final GestorClientesPotenciales gestor; // Modelo
    private final GUIClientesPotenciales vista; //Vista
    public GUIClientesPotencialesController(AModel myModel, AView myView) throws ClassNotFoundException, SQLException {
        super(myModel, myView);
        this.gestor = (GestorClientesPotenciales) myModel;
        this.vista = (GUIClientesPotenciales) myView;
        this.vista.setOpcionesComboBox(this.planes());
    }

    @Override
    public void actualizar(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Buscar":
                
        {
            try {
                ArrayList<Plan> planes=this.gestor.consultarPlanes();
                ArrayList<Cliente> lis=this.gestor.consultarClientes();
                ArrayList<String> listaTabla=new ArrayList<String>();
                int r1=0;
                int r2=0;
                for(Plan p:planes){
                    if(p.getNombre().equals(vista.getOpcionComboBox())){
                        r1=parseInt(p.getRangoEdad1());
                        r2=parseInt(p.getRangoEdad2());
                    }
                }
                for(Cliente c:lis){
                    if(c.calcularEdad()>=r1 && c.calcularEdad()<=r2){
                        
                        listaTabla.add(c.getId()+";"+c.getNombres()+";"+c.getApellidos()+";"+c.getEmail()+";"+c.getSexo()+";"+c.calcularEdad());
                    }                
                }
                vista.setTextResultado(vista.getOpcionComboBox()+": Edades entre "+r1+" y "+r2);
                vista.setTabla(listaTabla);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUIClientesPotencialesController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(GUIClientesPotencialesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
    }
      
    }
   private ArrayList<String> planes() throws ClassNotFoundException, SQLException{
       ArrayList<String> lista=new ArrayList<String>();
        for(Plan p:this.gestor.consultarPlanes()){
            lista.add(p.getNombre());
        }
        return lista;
    }

}
