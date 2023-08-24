
package Conexiones;

import java.sql.*;
public class LoginI extends Conexion {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public usuarios log(String NombreUsuario, String Password){
        usuarios l = new usuarios();
        String sql = "Select * FROM usuarios WHERE NombreUsuario = ? AND Password = ?";
        try{
            
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, NombreUsuario);
            ps.setString(2, Password);
            rs = ps.executeQuery();
            if(rs.next()){
                l.setId(rs.getInt("Id"));
                l.setNombreUsuario(rs.getString("NombreUsuario"));
                l.setPassword(rs.getString("Password"));
                l.setCI(rs.getString("CI"));
                l.setNomApe(rs.getString("NomApe"));
                l.setTelefono(rs.getString("Telefono"));
            }
            
            
        }catch (SQLException e){
            System.out.println(e.toString());
            
        } 
        return l;
    }
    
}
