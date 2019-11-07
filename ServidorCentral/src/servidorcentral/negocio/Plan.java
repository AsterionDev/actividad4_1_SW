/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorcentral.negocio;

/**
 *
 * @author Asus
 */
public class Plan {
    private String id;
    private String nombre;
    private String descripcion;
    private String rangoEdad1;
    private String rangoEdad2;
    
    public String getId(){ return id;}
    public String getNombre(){ return nombre;}
    public String getDescripcion(){ return descripcion;}
    public String getRangoEdad1(){ return rangoEdad1;}
    public String getRangoEdad2(){ return rangoEdad2;}
    
    public void setId(String id){   this.id=id;}
    public void setNombre(String nombre){   this.nombre=nombre;}
    public void setDescripcion(String descripcion){   this.descripcion=descripcion;}
    public void setRangoEdad1(String rangoEdad1){   this.rangoEdad1=rangoEdad1;}
    public void setRangoEdad2(String rangoEdad2){   this.rangoEdad2=rangoEdad2;}
    public Plan(String id, String nombre, String descripcion, String rangoEdad1, String rangoEdad2) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rangoEdad1 = rangoEdad1;
        this.rangoEdad2 = rangoEdad2;
    }

    
}
