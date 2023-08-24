
package Conexiones;

public class DataProveedor {
    
    private int id_proveedor;
    private String nombre_contacto;
    private String rif;
    private String direccion;
    private String ciudad;
    private String estado;
    private String telefono;
    
    public DataProveedor(){
        
    }

    public DataProveedor(int id_proveedor, String nombre_contacto, String rif, String direccion, String ciudad, String estado, String telefono) {
        this.id_proveedor = id_proveedor;
        this.nombre_contacto = nombre_contacto;
        this.rif = rif;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.estado = estado;
        this.telefono = telefono;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
