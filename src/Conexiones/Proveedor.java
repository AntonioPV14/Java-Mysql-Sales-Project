package Conexiones;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Proveedor {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean insertarProveedor(DataProveedor proveedor) {
        con = cn.getConexion();

        try {
            String query = "INSERT INTO Proveedores (nombre_contacto, rif, direccion, ciudad, estado, telefono) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, proveedor.getNombre_contacto());
            ps.setString(2, proveedor.getRif());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getCiudad());
            ps.setString(5, proveedor.getEstado());
            ps.setString(6, proveedor.getTelefono());

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    public List ListarProveedor() {
        List<DataProveedor> Listapr = new ArrayList();
        String sql = "Select * From Proveedores";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DataProveedor pr = new DataProveedor();
                pr.setId_proveedor(rs.getInt("id_proveedor"));
                pr.setNombre_contacto(rs.getString("nombre_contacto"));
                pr.setRif(rs.getString("rif"));
                pr.setDireccion(rs.getString("direccion"));
                pr.setCiudad(rs.getString("ciudad"));
                pr.setEstado(rs.getString("estado"));
                pr.setTelefono(rs.getString("telefono"));
                Listapr.add(pr);
            }

        } catch (Exception e) {
            System.out.println(e.toString());

        }
        return Listapr;
    }

    public boolean borrarProveedor(int proveedorID) {
        con = cn.getConexion();

        try {
            String query = "DELETE FROM Proveedores WHERE id_proveedor = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, proveedorID);

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    public boolean modificarProveedor(DataProveedor pr) {
        con = cn.getConexion();

        try {
            String procedureQuery = "{CALL ModificarProveedor(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cst = con.prepareCall(procedureQuery);
            cst.setInt(1, pr.getId_proveedor());
            cst.setString(2, pr.getNombre_contacto());
            cst.setString(3, pr.getRif());
            cst.setString(4, pr.getDireccion());
            cst.setString(5, pr.getCiudad());
            cst.setString(6, pr.getEstado());
            cst.setString(7, pr.getTelefono());

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
}
