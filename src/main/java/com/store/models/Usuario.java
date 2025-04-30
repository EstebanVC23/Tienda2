package com.store.models;
/**
 * Clase que representa a un usuario del sistema.
 * Contiene información personal y métodos para gestionar su estado.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String tipoDocumento;
    private String numeroDocumento;
    private String direccion;
    private String telefono;
    private String password;
    private String rol;
    private boolean estadoActivo;

    /**
     * Constructor por defecto de la clase Usuario.
     * Inicializa los atributos del usuario con valores predeterminados.
     */
    public Usuario() {
        this.id = 0;
        this.nombre = "";
        this.apellido = "";
        this.email = "";
        this.tipoDocumento = "";
        this.numeroDocumento = "";
        this.direccion = "";
        this.telefono = "";
        this.password = "";
        this.rol = "USER";
        this.estadoActivo = true;
    }

    // Métodos Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
