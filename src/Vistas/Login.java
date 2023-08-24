package Vistas;

import Conexiones.LoginI;
import Conexiones.SqlUsuarios;
import Conexiones.usuarios;
import java.awt.Color;
import Vistas.*;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {

    usuarios lg = new usuarios();
    LoginI login = new LoginI();
    public static Registro frmReg;

    int xMouse, yMouse;

    public Login() {
        initComponents();
    }

    public void validar() {
        String NombreUsuarios = txtUsuario.getText();
        String Password = String.valueOf(txtPassword.getPassword());
        if (!"".equals(NombreUsuarios) || !"".equals(Password)) {

            lg = login.log(NombreUsuarios, Password);
            if (lg.getNombreUsuario() != null && lg.getPassword() != null) {
                Sistema sis = new Sistema();
                sis.setVisible(true);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, "El usuario o contaseña es invalido.");
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Central = new javax.swing.JPanel();
        Logo_Login = new javax.swing.JLabel();
        Fondo2 = new javax.swing.JLabel();
        Etiqueta_Sension = new javax.swing.JLabel();
        Fondo1 = new javax.swing.JLabel();
        LabelPassword = new javax.swing.JLabel();
        LabelUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtPassword = new javax.swing.JPasswordField();
        Panel2 = new javax.swing.JPanel();
        JLabelEntrar = new javax.swing.JLabel();
        Panel_Exit = new javax.swing.JPanel();
        Salir = new javax.swing.JLabel();
        btnRegistro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        Central.setBackground(new java.awt.Color(204, 204, 204));
        Central.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Logo_Login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/round-account-button-with-user-inside_icon-icons.com_72596.png"))); // NOI18N
        Central.add(Logo_Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, -1));

        Fondo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo 2.jpg"))); // NOI18N
        Central.add(Fondo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 500));

        Etiqueta_Sension.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        Etiqueta_Sension.setForeground(new java.awt.Color(255, 255, 255));
        Etiqueta_Sension.setText("   Inicio de Sesión");
        Central.add(Etiqueta_Sension, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 220, 30));

        Fondo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo 1.png"))); // NOI18N
        Fondo1.setText("b");
        Central.add(Fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 440, 90));

        LabelPassword.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        LabelPassword.setText("Contraseña:");
        Central.add(LabelPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 300, 100, 30));

        LabelUsuario.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        LabelUsuario.setText("Usuario:");
        Central.add(LabelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 70, 30));

        txtUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txtUsuario.setText(" Ingrese el nombre de usuario");
        txtUsuario.setBorder(null);
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtUsuarioMousePressed(evt);
            }
        });
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });
        Central.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 340, 40));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        Central.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, 340, 20));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        Central.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 390, 340, 20));

        txtPassword.setForeground(new java.awt.Color(204, 204, 204));
        txtPassword.setText("********");
        txtPassword.setBorder(null);
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPasswordMousePressed(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });
        Central.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 340, 40));

        Panel2.setBackground(new java.awt.Color(0, 147, 244));

        JLabelEntrar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        JLabelEntrar.setForeground(new java.awt.Color(255, 255, 255));
        JLabelEntrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabelEntrar.setText("Entrar");
        JLabelEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JLabelEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JLabelEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JLabelEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JLabelEntrarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Panel2Layout = new javax.swing.GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLabelEntrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        Panel2Layout.setVerticalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JLabelEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        Central.add(Panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 440, 110, 40));

        Panel_Exit.setBackground(new java.awt.Color(204, 204, 204));
        Panel_Exit.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Panel_ExitMouseDragged(evt);
            }
        });
        Panel_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Panel_ExitMousePressed(evt);
            }
        });

        Salir.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        Salir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Salir.setText("X");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SalirMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Panel_ExitLayout = new javax.swing.GroupLayout(Panel_Exit);
        Panel_Exit.setLayout(Panel_ExitLayout);
        Panel_ExitLayout.setHorizontalGroup(
            Panel_ExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_ExitLayout.createSequentialGroup()
                .addGap(0, 664, Short.MAX_VALUE)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        Panel_ExitLayout.setVerticalGroup(
            Panel_ExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Salir, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        Central.add(Panel_Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 40));

        btnRegistro.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnRegistro.setForeground(new java.awt.Color(51, 153, 255));
        btnRegistro.setText("Registrar Usuario");
        btnRegistro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistroMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistroMouseExited(evt);
            }
        });
        Central.add(btnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Central, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Central, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void Panel_ExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_ExitMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_Panel_ExitMousePressed

    private void Panel_ExitMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_ExitMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_Panel_ExitMouseDragged

    private void SalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_SalirMouseClicked

    private void SalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMouseEntered
        Salir.setForeground(Color.red);
    }//GEN-LAST:event_SalirMouseEntered

    private void SalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMouseExited
        Salir.setForeground(Color.BLACK);
    }//GEN-LAST:event_SalirMouseExited

    private void txtUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMousePressed
        if (txtUsuario.getText().equals(" Ingrese el nombre de usuario")) {
            txtUsuario.setText("");
            txtUsuario.setForeground(Color.black);
        }
        if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
            txtPassword.setText("********");
            txtPassword.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtUsuarioMousePressed

    private void txtPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMousePressed
        if (String.valueOf(txtPassword.getPassword()).equals("********")) {
            txtPassword.setText("");
            txtPassword.setForeground(Color.black);
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText(" Ingrese el nombre de usuario");
            txtUsuario.setForeground(Color.gray);
        }
    }//GEN-LAST:event_txtPasswordMousePressed

    private void JLabelEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLabelEntrarMouseClicked
        validar();
    }//GEN-LAST:event_JLabelEntrarMouseClicked

    private void btnRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroMouseClicked
        Registro registro = new Registro();
        registro.setVisible(true);
        dispose();


    }//GEN-LAST:event_btnRegistroMouseClicked

    private void btnRegistroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroMouseEntered
        btnRegistro.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnRegistroMouseEntered

    private void btnRegistroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroMouseExited
        Color Celeste = new Color(51, 153, 255);
        btnRegistro.setForeground(Celeste);
    }//GEN-LAST:event_btnRegistroMouseExited

    private void JLabelEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLabelEntrarMouseEntered
        JLabelEntrar.setForeground(Color.GREEN);
    }//GEN-LAST:event_JLabelEntrarMouseEntered

    private void JLabelEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JLabelEntrarMouseExited
        JLabelEntrar.setForeground(Color.WHITE);
    }//GEN-LAST:event_JLabelEntrarMouseExited

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
           validar();
       }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
           validar();
       }
    }//GEN-LAST:event_txtPasswordKeyPressed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Central;
    private javax.swing.JLabel Etiqueta_Sension;
    private javax.swing.JLabel Fondo1;
    private javax.swing.JLabel Fondo2;
    private javax.swing.JLabel JLabelEntrar;
    private javax.swing.JLabel LabelPassword;
    private javax.swing.JLabel LabelUsuario;
    private javax.swing.JLabel Logo_Login;
    private javax.swing.JPanel Panel2;
    private javax.swing.JPanel Panel_Exit;
    private javax.swing.JLabel Salir;
    private javax.swing.JLabel btnRegistro;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
