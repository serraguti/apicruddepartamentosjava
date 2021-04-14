package models;

public class Departamento {

    private int idDepartamento;
    private String nombre;
    private String localidad;

    public Departamento() {
    }

    public Departamento(int idDepartamento, String nombre, String localidad) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
        this.localidad = localidad;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
}
