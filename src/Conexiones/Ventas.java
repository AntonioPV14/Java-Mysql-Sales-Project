package Conexiones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Ventas {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public int InsertarVenta(DataVenta v) {
        String sql = "INSERT INTO ventas (ci_cliente, total_venta) VALUES (?, ?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getCi_cliente());
            ps.setFloat(2, v.getTotal_venta());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }

    public int RegistrarDetalle(DataDetalle Dv) {
        String sql = "Insert Into detallesventa (id_venta, id_producto, cantidad, precio_unitario) Values (?,?,?,?)  ";

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_venta());
            ps.setInt(2, Dv.getId_producto());
            ps.setInt(3, Dv.getCantidad());
            ps.setDouble(4, Dv.getPrecio_unitario());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }

    public int IdVenta() {
        int id = 0;
        String sql = "SELECT MAX(id_venta) FROM ventas";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {

                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return id;
    }

    public List ListarVentas(){
        List<DataVenta> listaVenta = new ArrayList<>();
        String sql = "SELECT * FROM ventas";
        
        try{
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                DataVenta vent = new DataVenta();
                vent.setId_venta(rs.getInt("id_venta"));
                vent.setCi_cliente(rs.getString("ci_cliente"));
                vent.setTotal_venta(rs.getFloat("total_venta"));
                listaVenta.add(vent);
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return listaVenta;
   
                
    }

    public String obtenerNombreClientePorCI(String ciCliente) {
        String nombreCliente = null;
        String sql = "SELECT nombre FROM clientes WHERE ci = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, ciCliente);
            rs = ps.executeQuery();
            if (rs.next()) {
                nombreCliente = rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return nombreCliente;
    }
}
