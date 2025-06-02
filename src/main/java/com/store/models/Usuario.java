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
    private int carritoComprasId;

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
        this.carritoComprasId = 0;
        this.estadoActivo = true;
    }

    /**
     * Obtiene el ID del usuario.
     * @return El ID del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     * @param id El ID del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre El nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     * @return El apellido del usuario.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del usuario.
     * @param apellido El apellido del usuario.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el email del usuario.
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * @param email El email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el tipo de documento del usuario.
     * @return El tipo de documento del usuario.
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Establece el tipo de documento del usuario.
     * @param tipoDocumento El tipo de documento del usuario.
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * Obtiene el número de documento del usuario.
     * @return El número de documento del usuario.
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Establece el número de documento del usuario.
     * @param numeroDocumento El número de documento del usuario.
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Obtiene la dirección del usuario.
     * @return La dirección del usuario.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del usuario.
     * @param direccion La dirección del usuario.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el teléfono del usuario.
     * @return El teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del usuario.
     * @param telefono El teléfono del usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el estado de actividad del usuario.
     * @return true si el usuario está activo, false en caso contrario.
     */
    public boolean getEstado() {
        return estadoActivo;
    }

    /**
     * Establece el estado de actividad del usuario.
     * @param estadoActivo true para activar, false para desactivar.
     */
    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password La contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol El rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el ID del carrito de compras asociado al usuario.
     * @return El ID del carrito de compras.
     */
    public int getCarritoComprasId() {
        return carritoComprasId;
    }

    /**
     * Establece el ID del carrito de compras asociado al usuario.
     * @param carritoComprasId El ID del carrito de compras.
     */
    public void setCarritoComprasId(int carritoComprasId) {
        this.carritoComprasId = carritoComprasId;
    }
}