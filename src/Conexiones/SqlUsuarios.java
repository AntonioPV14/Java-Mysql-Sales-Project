
package Conexiones;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlUsuarios extends Conexion {
    
    public boolean registrar(usuarios usr){
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        
        String sql = "Insert Into usuarios(NombreUsuario, Password, CI, NomApe, Telefono) Values (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getNombreUsuario());
            ps.setString(2, usr.getPassword());
            ps.setString(3, usr.getCI());
            ps.setString(4, usr.getNomApe());
            ps.setString(5, usr.getTelefono());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public boolean login(usuarios usr){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT Id, NombreUsuario, Password, CI, NomApe, Telefono from ventas";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getNombreUsuario());
            rs = ps.executeQuery();
            
            if(rs.next()){
                
                if(usr.getPassword().equals(rs.getString(3))){
                    usr.setId(rs.getInt(1));
                    usr.setNomApe(rs.getString(5));
                    usr.setCI(rs.getString(4));
                    return true;
                } else{
                    return false;
                }
                
            } return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public int existeUsuario(String NombreUsuario){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT Count(id) From usuarios where NombreUsuario = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, NombreUsuario);
            rs = ps.executeQuery();
            
            if(rs.next()){
                
                return rs.getInt(1);
                
            } return 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
        
    }

    public boolean login(String usuario, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
