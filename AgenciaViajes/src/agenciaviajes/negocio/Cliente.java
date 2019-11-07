package agenciaviajes.negocio;

import static java.lang.Integer.parseInt;

public class Cliente {

    private String id;
    private String nombres;
    private String apellidos;
    private String fechaNac;
    private String email;
    private String sexo;

    public Cliente(String id, String nombres, String apellidos, String fechaNac, String sexo,String email) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNac = fechaNac;
        this.email = email;
        this.sexo = sexo;
    }

    public Cliente() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNac() {
        return this.fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public int calcularEdad(){
        String datos[]= this.fechaNac.split("-");
        int edad    = 2019-parseInt( datos[0]);
        
        return edad;
    }

}
