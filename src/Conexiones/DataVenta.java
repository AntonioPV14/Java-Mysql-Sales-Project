
package Conexiones;


public class DataVenta {
    
    private int id_venta;
    private String  ci_cliente;
    private float total_venta;

    public DataVenta() {
    }

    public DataVenta(int id_venta, String ci_cliente, float total_venta) {
        this.id_venta = id_venta;
        this.ci_cliente = ci_cliente;
        this.total_venta = total_venta;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public String getCi_cliente() {
        return ci_cliente;
    }

    public void setCi_cliente(String ci_cliente) {
        this.ci_cliente = ci_cliente;
    }

    public float getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(float total_venta) {
        this.total_venta = total_venta;
    }

    

    
}
