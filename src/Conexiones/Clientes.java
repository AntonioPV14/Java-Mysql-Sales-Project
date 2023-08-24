package Conexiones;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Clientes {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean borrarCliente(int clienteID) {
        String sql = "Delete FROM clientes Where id_cliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, clienteID);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public boolean insertarCliente(DataClientes cl) {
        con = cn.getConexion();

        try {
            String procedureQuery = "{CALL InsertarCliente(?, ?, ?, ?, ?)}";
            CallableStatement cst = con.prepareCall(procedureQuery);
            cst.setString(1, cl.getNombre());
            cst.setString(2, cl.getApellido());
            cst.setString(3, cl.getCi());
            cst.setString(4, cl.getDireccion());
            cst.setString(5, cl.getTelefono());

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

    public boolean modificarCliente(int clienteID, DataClientes cl) {
        con = cn.getConexion();

        try {
            String procedureQuery = "{CALL ModificarCliente(?, ?, ?, ?, ?, ?)}";
            CallableStatement cst = con.prepareCall(procedureQuery);
            cst.setInt(1, clienteID);
            cst.setString(2, cl.getNombre());
            cst.setString(3, cl.getApellido());
            cst.setString(4, cl.getCi());
            cst.setString(5, cl.getDireccion());
            cst.setString(6, cl.getTelefono());

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

    public List<DataClientes> ListarCliente() {
        List<DataClientes> ListaCl = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTES";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DataClientes cl = new DataClientes();
                cl.setId_cliente(rs.getInt("id_cliente"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setCi(rs.getString("ci"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                ListaCl.add(cl);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ListaCl;
    }

    public String BuscarNombreClientePorCI(String ci) {
        String nombreCliente = null;
        String sql = "SELECT nombre FROM clientes WHERE ci = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, ci); 
            rs = ps.executeQuery();
            if (rs.next()) {
                nombreCliente = rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
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
