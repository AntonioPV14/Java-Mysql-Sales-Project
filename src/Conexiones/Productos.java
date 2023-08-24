package Conexiones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class Productos {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public boolean insertarProducto(DataProductos producto) {
        con = cn.getConexion();

        try {
            String procedureQuery = "{CALL InsertarProducto(?, ?, ?, ?, ?)}";
            CallableStatement cst = con.prepareCall(procedureQuery);
            cst.setString(1, producto.getDescripcion());
            cst.setDouble(2, producto.getPrecio());
            cst.setInt(3, producto.getStock());
            cst.setString(4, producto.getProveedor());
            cst.setString(5, producto.getCategoria());

            int resultado = cst.executeUpdate();
            return resultado > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public void ConsultarProveedor(JComboBox proveedor) {
        String sql = "Select nombre_contacto from proveedores";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                proveedor.addItem(rs.getString("nombre_contacto"));
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void ConsultarCategoria(JComboBox categoria) {
        String sql = "Select nombre_categoria from categorias";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoria.addItem(rs.getString("nombre_categoria"));
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public boolean modificarProducto(DataProductos producto) {
        Connection con = null;
        try {
            con = cn.getConexion();
            String procedureQuery = "{CALL ModificarProducto(?, ?, ?, ?, ?, ?)}";
            CallableStatement cst = con.prepareCall(procedureQuery);
            cst.setInt(1, producto.getId_producto());
            cst.setString(2, producto.getDescripcion());
            cst.setDouble(3, producto.getPrecio());
            cst.setInt(4, producto.getStock());
            cst.setString(5, producto.getProveedor());
            cst.setString(6, producto.getCategoria());

            int resultado = cst.executeUpdate();
            return resultado > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean eliminarProducto(int idProducto) {
        Connection con = null;
        try {
            con = cn.getConexion();
            String procedureQuery = "{CALL EliminarProducto(?)}";
            CallableStatement cst = con.prepareCall(procedureQuery);
            cst.setInt(1, idProducto);
            int resultado = cst.executeUpdate();
            return resultado > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public List<DataProductos> ListarProductos() {
        List<DataProductos> Listapro = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DataProductos produc = new DataProductos();
                produc.setId_producto(rs.getInt("id_producto"));
                produc.setDescripcion(rs.getString("descripcion"));
                produc.setPrecio(rs.getDouble("precio"));
                produc.setStock(rs.getInt("stock"));
                produc.setProveedor(rs.getString("proveedor"));
                produc.setCategoria(rs.getString("categoria"));
                Listapro.add(produc);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Listapro;
    }

    public DataProductos BuscarPro(String cod) {
        DataProductos producto = new DataProductos();
        String sql = "Select * From productos Where id_producto = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setDescripcion(rs.getString("descripcion")); 
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;

    }

}
