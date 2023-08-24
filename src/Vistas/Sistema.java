package Vistas;

import Conexiones.Clientes;
import Conexiones.DataClientes;
import Conexiones.DataDetalle;
import Conexiones.DataProveedor;
import Conexiones.Productos;
import Conexiones.Proveedor;
import Conexiones.DataProductos;
import Conexiones.DataVenta;
import Conexiones.Eventos;
import Conexiones.Ventas;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sistema extends javax.swing.JFrame {

    DataClientes cl = new DataClientes();
    Clientes client = new Clientes();
    DataProveedor pr = new DataProveedor();
    Proveedor pro = new Proveedor();
    DefaultTableModel modelo = new DefaultTableModel();
    Productos pron = new Productos();
    DataProductos Dao = new DataProductos();
    DataVenta v = new DataVenta();
    Ventas ventas = new Ventas();
    DataDetalle Dv = new DataDetalle();
    Eventos event = new Eventos();

    double Totalpagar = 0.00;

    int item;

    Tiempo time = new Tiempo();
    int xMouse, yMouse;

    public void listarProveedor() {
        List<DataProveedor> Listapr = pro.ListarProveedor();
        modelo = (DefaultTableModel) TableProveedor.getModel();
        Object[] ob = new Object[7]; // Ajustar la longitud del array a 7
        for (int i = 0; i < Listapr.size(); i++) {
            ob[0] = Listapr.get(i).getId_proveedor();
            ob[1] = Listapr.get(i).getNombre_contacto();
            ob[2] = Listapr.get(i).getRif();
            ob[3] = Listapr.get(i).getDireccion();
            ob[4] = Listapr.get(i).getCiudad();
            ob[5] = Listapr.get(i).getEstado();
            ob[6] = Listapr.get(i).getTelefono();
            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);
    }

    public void ListarCliente() {
        List<DataClientes> ListaCl = client.ListarCliente();
        modelo = (DefaultTableModel) TableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListaCl.size(); i++) {
            ob[0] = ListaCl.get(i).getId_cliente();
            ob[1] = ListaCl.get(i).getNombre();
            ob[2] = ListaCl.get(i).getApellido();
            ob[3] = ListaCl.get(i).getCi();
            ob[4] = ListaCl.get(i).getDireccion();
            ob[5] = ListaCl.get(i).getTelefono();
            modelo.addRow(ob);

        }
        TableCliente.setModel(modelo);
    }

    public void ListarReportesClientes() {
        List<DataClientes> ListaCl = client.ListarCliente();
        modelo = (DefaultTableModel) TableReportes.getModel();

        modelo.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Apellido", "CI", "Direccion", "Telefono"});
        modelo.setRowCount(0);

        Object[] ob = new Object[6];
        for (int i = 0; i < ListaCl.size(); i++) {
            ob[0] = ListaCl.get(i).getId_cliente();
            ob[1] = ListaCl.get(i).getNombre();
            ob[2] = ListaCl.get(i).getApellido();
            ob[3] = ListaCl.get(i).getCi();
            ob[4] = ListaCl.get(i).getDireccion();
            ob[5] = ListaCl.get(i).getTelefono();
            modelo.addRow(ob);
        }

        TableReportes.setModel(modelo);
    }

    public void listarReportesProveedor() {

        List<DataProveedor> Listapr = pro.ListarProveedor();
        modelo = (DefaultTableModel) TableReportes.getModel();

        modelo.setColumnIdentifiers(new Object[]{"ID", "Contacto", "Rif", "Direccion", "Ciudad", "Estado", "Telefono"});
        modelo.setRowCount(0);

        Object[] ob = new Object[7];
        for (int i = 0; i < Listapr.size(); i++) {
            ob[0] = Listapr.get(i).getId_proveedor();
            ob[1] = Listapr.get(i).getNombre_contacto();
            ob[2] = Listapr.get(i).getRif();
            ob[3] = Listapr.get(i).getDireccion();
            ob[4] = Listapr.get(i).getCiudad();
            ob[5] = Listapr.get(i).getEstado();
            ob[6] = Listapr.get(i).getTelefono();
            modelo.addRow(ob);
        }
        TableReportes.setModel(modelo);
    }

    public void listarProductos() {
        List<DataProductos> ListaPro = pron.ListarProductos();
        modelo = (DefaultTableModel) TableProductos.getModel();
        Object[] ob = new Object[6]; // A 
        for (int i = 0; i < ListaPro.size(); i++) {
            ob[0] = ListaPro.get(i).getId_producto();
            ob[1] = ListaPro.get(i).getDescripcion();
            ob[2] = ListaPro.get(i).getPrecio();
            ob[3] = ListaPro.get(i).getStock();
            ob[4] = ListaPro.get(i).getProveedor();
            ob[5] = ListaPro.get(i).getCategoria();

            modelo.addRow(ob);
        }
        TableProductos.setModel(modelo);
    }

    public void listarVentas() {
        List<DataVenta> listarVenta = ventas.ListarVentas();
        modelo = (DefaultTableModel) TableReportes.getModel();
        modelo.setColumnIdentifiers(new Object[]{"ID", "CI Cliente", "Total Pagado"});
        modelo.setRowCount(0);
        Object[] ob = new Object[3];
        for (int i = 0; i < listarVenta.size(); i++) {
            ob[0] = listarVenta.get(i).getId_venta();
            ob[1] = listarVenta.get(i).getCi_cliente();
            ob[2] = listarVenta.get(i).getTotal_venta();

            modelo.addRow(ob);
        }
        TableReportes.setModel(modelo);
    }

    public void listarReportesProductos() {
        List<DataProductos> ListaPro = pron.ListarProductos();
        modelo = (DefaultTableModel) TableReportes.getModel();
        modelo.setColumnIdentifiers(new Object[]{"ID", "Descripcion", "Precio", "Stock", "Proveedor", "Categoria"});
        modelo.setRowCount(0);

        Object[] ob = new Object[6];
        for (int i = 0; i < ListaPro.size(); i++) {
            ob[0] = ListaPro.get(i).getId_producto();
            ob[1] = ListaPro.get(i).getDescripcion();
            ob[2] = ListaPro.get(i).getPrecio();
            ob[3] = ListaPro.get(i).getStock();
            ob[4] = ListaPro.get(i).getProveedor();
            ob[5] = ListaPro.get(i).getCategoria();

            modelo.addRow(ob);
        }
        TableReportes.setModel(modelo);
    }

    public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
            LabelTotal.setText("");
        }
    }

    public Sistema() {
        initComponents();
        MostrarTiempo();

        pron.ConsultarProveedor(boxProveedor);
        pron.ConsultarCategoria(boxCategoria);

    }

    public void MostrarTiempo() {

        labelFecha.setText(time.FechaCompleta);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        Cerrar = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Clientes = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        NewVenta = new javax.swing.JLabel();
        Proveedores = new javax.swing.JLabel();
        Productos = new javax.swing.JLabel();
        Reportes = new javax.swing.JLabel();
        PuntoVenta = new javax.swing.JLabel();
        Fondo1 = new javax.swing.JLabel();
        Bienvenido = new javax.swing.JLabel();
        Fondo2 = new javax.swing.JLabel();
        Panel_Exit = new javax.swing.JPanel();
        Salir = new javax.swing.JLabel();
        ScrollPanel = new javax.swing.JTabbedPane();
        PanelVentas = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        LID = new javax.swing.JLabel();
        Ldescripcion = new javax.swing.JLabel();
        Lcantidad = new javax.swing.JLabel();
        Lprecio = new javax.swing.JLabel();
        LStock = new javax.swing.JLabel();
        LCI = new javax.swing.JLabel();
        Lnombre = new javax.swing.JLabel();
        Ltotal = new javax.swing.JLabel();
        txtCallDescripcion = new javax.swing.JTextField();
        txtCallCantidad = new javax.swing.JTextField();
        txtCallPrecio = new javax.swing.JTextField();
        txtCallStock = new javax.swing.JTextField();
        txtCallCI = new javax.swing.JTextField();
        txtCallNombre = new javax.swing.JTextField();
        txtCallIdProducto = new javax.swing.JTextField();
        LabelTotal = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        F1 = new javax.swing.JLabel();
        F2 = new javax.swing.JLabel();
        PanelClientes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        LBNombre = new javax.swing.JLabel();
        LBApellido = new javax.swing.JLabel();
        LBCI = new javax.swing.JLabel();
        LBDireccion = new javax.swing.JLabel();
        LBTelefono = new javax.swing.JLabel();
        txtNombreClientes = new javax.swing.JTextField();
        txtApellidoClientes = new javax.swing.JTextField();
        txtCIClientes = new javax.swing.JTextField();
        txtDireccionClientes = new javax.swing.JTextField();
        txtTelefonoClientes = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtRegistrarClientes = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        bntModificarClientes = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnEliminarClientes = new javax.swing.JLabel();
        txtId_cliente = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        PanelProveedores = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        BContacto = new javax.swing.JLabel();
        BRif = new javax.swing.JLabel();
        BDireccion = new javax.swing.JLabel();
        BCiudad = new javax.swing.JLabel();
        txtContactoProveedor = new javax.swing.JTextField();
        txtRifProveedor = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtCiudadProveedor = new javax.swing.JTextField();
        BEstado = new javax.swing.JLabel();
        BTelefono = new javax.swing.JLabel();
        txtEstadoProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnRegistrarProveedor = new javax.swing.JLabel();
        txtId_proveedor = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnEliminarProveedores = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btnModificarProveedores = new javax.swing.JLabel();
        f1 = new javax.swing.JLabel();
        f2 = new javax.swing.JLabel();
        PanelProductos = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProductos = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Prov = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtDescripcionPro = new javax.swing.JTextField();
        txtPrecioPro = new javax.swing.JTextField();
        txtStockPro = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        btnRegistrarProductos = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnModificarProductos = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnEliminarProductos = new javax.swing.JLabel();
        boxProveedor = new javax.swing.JComboBox<>();
        boxCategoria = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        txtIdProducto = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        PanelReportes = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnCallVentas = new javax.swing.JLabel();
        btnCallClientes = new javax.swing.JLabel();
        btnCallProveedores = new javax.swing.JLabel();
        btnCallProductos = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnOpen = new javax.swing.JLabel();
        txtIdVentas = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableReportes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Cerrar.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        Cerrar.setForeground(new java.awt.Color(255, 255, 255));
        Cerrar.setText("Cerrar Sesión");
        Cerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CerrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                CerrarMouseExited(evt);
            }
        });
        jPanel2.add(Cerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 560, -1, -1));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 140, 20));

        Clientes.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        Clientes.setForeground(new java.awt.Color(255, 255, 255));
        Clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clientes.png"))); // NOI18N
        Clientes.setText("      Clientes");
        Clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ClientesMouseExited(evt);
            }
        });
        jPanel2.add(Clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 130, -1));

        labelFecha.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        labelFecha.setForeground(new java.awt.Color(255, 255, 255));
        labelFecha.setText("Hoy es {dia} de {mes} del {año}");
        jPanel2.add(labelFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 350, 40));

        NewVenta.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        NewVenta.setForeground(new java.awt.Color(255, 255, 255));
        NewVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cash.png"))); // NOI18N
        NewVenta.setText("     Nueva Venta");
        NewVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NewVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NewVentaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                NewVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                NewVentaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                NewVentaMousePressed(evt);
            }
        });
        jPanel2.add(NewVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 140, 30));

        Proveedores.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Proveedores.setForeground(new java.awt.Color(255, 255, 255));
        Proveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Proveedor.png"))); // NOI18N
        Proveedores.setText("        Proveedores");
        Proveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Proveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProveedoresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ProveedoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ProveedoresMouseExited(evt);
            }
        });
        jPanel2.add(Proveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 150, -1));

        Productos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Productos.setForeground(new java.awt.Color(255, 255, 255));
        Productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Productos.png"))); // NOI18N
        Productos.setText("      Productos");
        Productos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ProductosMouseExited(evt);
            }
        });
        jPanel2.add(Productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 140, -1));

        Reportes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Reportes.setForeground(new java.awt.Color(255, 255, 255));
        Reportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Reporte.png"))); // NOI18N
        Reportes.setText("        Reportes");
        Reportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Reportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReportesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ReportesMouseExited(evt);
            }
        });
        jPanel2.add(Reportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, 150, -1));

        PuntoVenta.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        PuntoVenta.setForeground(new java.awt.Color(255, 255, 255));
        PuntoVenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PuntoVenta.setText("Tienda C.A.");
        jPanel2.add(PuntoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 140, -1));

        Fondo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo 1.png"))); // NOI18N
        jPanel2.add(Fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, -1));

        Bienvenido.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        Bienvenido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Bienvenido.setText("Bienvenid@");
        jPanel2.add(Bienvenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 180, 40));

        Fondo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo 1.png"))); // NOI18N
        jPanel2.add(Fondo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 640, 100));

        Panel_Exit.setBackground(new java.awt.Color(255, 255, 255));
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SalirMousePressed(evt);
            }
        });

        javax.swing.GroupLayout Panel_ExitLayout = new javax.swing.GroupLayout(Panel_Exit);
        Panel_Exit.setLayout(Panel_ExitLayout);
        Panel_ExitLayout.setHorizontalGroup(
            Panel_ExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_ExitLayout.createSequentialGroup()
                .addGap(0, 854, Short.MAX_VALUE)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        Panel_ExitLayout.setVerticalGroup(
            Panel_ExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Salir, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel2.add(Panel_Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 40));

        ScrollPanel.setBackground(new java.awt.Color(204, 204, 204));
        ScrollPanel.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        ScrollPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        PanelVentas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descripcion", "Cantidad", "Precio U", "Precio Total"
            }
        ));
        jScrollPane4.setViewportView(TableVenta);

        PanelVentas.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 180));

        LID.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        LID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LID.setText("ID Producto");
        PanelVentas.add(LID, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 80, 20));

        Ldescripcion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Ldescripcion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ldescripcion.setText("Descripcion");
        PanelVentas.add(Ldescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 80, 20));

        Lcantidad.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lcantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lcantidad.setText("Cantidad");
        PanelVentas.add(Lcantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 70, 20));

        Lprecio.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lprecio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lprecio.setText("Precio");
        PanelVentas.add(Lprecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 60, 20));

        LStock.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        LStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LStock.setText("Stock");
        PanelVentas.add(LStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 190, 70, 20));

        LCI.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        LCI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LCI.setText("CI");
        PanelVentas.add(LCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 60, 20));

        Lnombre.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Lnombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lnombre.setText("Nombre");
        PanelVentas.add(Lnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 340, 90, 20));

        Ltotal.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Ltotal.setText("Total a Pagar:");
        PanelVentas.add(Ltotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, -1, 20));

        txtCallDescripcion.setEditable(false);
        txtCallDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCallDescripcionKeyTyped(evt);
            }
        });
        PanelVentas.add(txtCallDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 80, -1));

        txtCallCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCallCantidadKeyPressed(evt);
            }
        });
        PanelVentas.add(txtCallCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 70, -1));

        txtCallPrecio.setEditable(false);
        PanelVentas.add(txtCallPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, 60, -1));

        txtCallStock.setEditable(false);
        PanelVentas.add(txtCallStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, 70, -1));

        txtCallCI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCallCIKeyPressed(evt);
            }
        });
        PanelVentas.add(txtCallCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 60, -1));

        txtCallNombre.setEditable(false);
        PanelVentas.add(txtCallNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, 90, -1));

        txtCallIdProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCallIdProductoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCallIdProductoKeyTyped(evt);
            }
        });
        PanelVentas.add(txtCallIdProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 80, -1));

        LabelTotal.setEditable(false);
        LabelTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelVentas.add(LabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 80, 20));

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aceptar.png"))); // NOI18N
        btnAceptar.setToolTipText("");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        PanelVentas.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 323, 50, 50));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cancelar.png"))); // NOI18N
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        PanelVentas.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 323, 50, 50));

        F1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Borde2.png"))); // NOI18N
        PanelVentas.add(F1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 280, 240));

        F2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoBlack.png"))); // NOI18N
        PanelVentas.add(F2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 360, 240));

        ScrollPanel.addTab("tab1", PanelVentas);

        PanelClientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id_Cliente", "Nombre", "Apellido", "CI", "Direccion", "Telefono"
            }
        ));
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableCliente);

        PanelClientes.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 180));

        LBNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LBNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBNombre.setText("Nombre");
        PanelClientes.add(LBNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 100, 20));

        LBApellido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LBApellido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBApellido.setText("Apellido");
        PanelClientes.add(LBApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 90, 20));

        LBCI.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LBCI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBCI.setText("CI");
        PanelClientes.add(LBCI, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 80, 20));

        LBDireccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LBDireccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBDireccion.setText("Direccion");
        PanelClientes.add(LBDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, 110, 20));

        LBTelefono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LBTelefono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBTelefono.setText("Telefono");
        PanelClientes.add(LBTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 220, 110, 20));

        txtNombreClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNombreClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClientesKeyTyped(evt);
            }
        });
        PanelClientes.add(txtNombreClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 100, 20));

        txtApellidoClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelClientes.add(txtApellidoClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 90, 20));

        txtCIClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCIClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCIClientesActionPerformed(evt);
            }
        });
        PanelClientes.add(txtCIClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 80, 20));

        txtDireccionClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelClientes.add(txtDireccionClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, 110, 20));

        txtTelefonoClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelClientes.add(txtTelefonoClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 240, 110, 20));

        jPanel3.setBackground(new java.awt.Color(31, 134, 195));

        txtRegistrarClientes.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        txtRegistrarClientes.setForeground(new java.awt.Color(255, 255, 255));
        txtRegistrarClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtRegistrarClientes.setText("Registrar");
        txtRegistrarClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtRegistrarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRegistrarClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtRegistrarClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtRegistrarClientesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtRegistrarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtRegistrarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelClientes.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 90, 30));

        jPanel4.setBackground(new java.awt.Color(31, 134, 195));

        bntModificarClientes.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        bntModificarClientes.setForeground(new java.awt.Color(255, 255, 255));
        bntModificarClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bntModificarClientes.setText("Modificar");
        bntModificarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bntModificarClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bntModificarClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bntModificarClientesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bntModificarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bntModificarClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelClientes.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 90, 30));

        jPanel5.setBackground(new java.awt.Color(31, 134, 195));

        btnEliminarClientes.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        btnEliminarClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminarClientes.setText("Eliminar");
        btnEliminarClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarClientesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelClientes.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 90, 30));
        PanelClientes.add(txtId_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 0, 0));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Borde2.png"))); // NOI18N
        PanelClientes.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 280, 240));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoBlack.png"))); // NOI18N
        PanelClientes.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 370, 240));

        ScrollPanel.addTab("tab2", PanelClientes);

        PanelProveedores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id_Proveedor", "Contacto", "Rif", "Direccion", "Ciudad", "Estado", "Telefono"
            }
        ));
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setHeaderValue("Id_Proveedor");
            TableProveedor.getColumnModel().getColumn(1).setHeaderValue("Contacto");
            TableProveedor.getColumnModel().getColumn(2).setHeaderValue("Rif");
            TableProveedor.getColumnModel().getColumn(3).setHeaderValue("Direccion");
            TableProveedor.getColumnModel().getColumn(4).setHeaderValue("Ciudad");
            TableProveedor.getColumnModel().getColumn(5).setHeaderValue("Estado");
            TableProveedor.getColumnModel().getColumn(6).setHeaderValue("Telefono");
        }

        PanelProveedores.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 180));

        BContacto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BContacto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BContacto.setText("Contacto");
        PanelProveedores.add(BContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 110, 20));

        BRif.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BRif.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BRif.setText("Rif");
        PanelProveedores.add(BRif, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 60, 20));

        BDireccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BDireccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BDireccion.setText("Direccion");
        PanelProveedores.add(BDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 110, 20));

        BCiudad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BCiudad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BCiudad.setText("Ciudad");
        PanelProveedores.add(BCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 80, 20));

        txtContactoProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProveedores.add(txtContactoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 110, 20));

        txtRifProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProveedores.add(txtRifProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 60, 20));

        txtDireccionProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProveedores.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 110, 20));

        txtCiudadProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProveedores.add(txtCiudadProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 240, 80, 20));

        BEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BEstado.setText("Estado");
        PanelProveedores.add(BEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 90, 20));

        BTelefono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BTelefono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BTelefono.setText("Telefono");
        PanelProveedores.add(BTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 220, 100, 20));

        txtEstadoProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProveedores.add(txtEstadoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, 90, 20));

        txtTelefonoProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProveedores.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 240, 100, 20));

        jPanel6.setBackground(new java.awt.Color(31, 134, 195));

        btnRegistrarProveedor.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        btnRegistrarProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRegistrarProveedor.setText("Registrar");
        btnRegistrarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistrarProveedorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrarProveedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrarProveedorMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegistrarProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegistrarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelProveedores.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 90, 30));
        PanelProveedores.add(txtId_proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 0, 10));

        jPanel8.setBackground(new java.awt.Color(31, 134, 195));

        btnEliminarProveedores.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        btnEliminarProveedores.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarProveedores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminarProveedores.setText("Eliminar");
        btnEliminarProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarProveedoresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarProveedoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarProveedoresMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminarProveedores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminarProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelProveedores.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 90, 30));

        jPanel9.setBackground(new java.awt.Color(31, 134, 195));

        btnModificarProveedores.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        btnModificarProveedores.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarProveedores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnModificarProveedores.setText("Modificar");
        btnModificarProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarProveedoresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarProveedoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarProveedoresMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModificarProveedores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModificarProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelProveedores.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 90, 30));

        f1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Borde2.png"))); // NOI18N
        PanelProveedores.add(f1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 280, 240));

        f2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoBlack.png"))); // NOI18N
        PanelProveedores.add(f2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 360, 240));

        ScrollPanel.addTab("tab3", PanelProveedores);

        PanelProductos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id_Producto", "Descripcion", "Precio", "Stock", "Proveedor", "Categoria"
            }
        ));
        TableProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProductos);

        PanelProductos.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 180));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Descripcion");
        PanelProductos.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 100, 20));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Precio");
        PanelProductos.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 80, 20));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Stock");
        PanelProductos.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 80, 20));

        Prov.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Prov.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Prov.setText("Proveedor");
        PanelProductos.add(Prov, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, 150, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Categoria");
        PanelProductos.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, 170, 20));

        txtDescripcionPro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProductos.add(txtDescripcionPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 100, 20));

        txtPrecioPro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProductos.add(txtPrecioPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 80, 20));

        txtStockPro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProductos.add(txtStockPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 80, 20));

        jPanel10.setBackground(new java.awt.Color(31, 134, 195));

        btnRegistrarProductos.setBackground(new java.awt.Color(255, 255, 255));
        btnRegistrarProductos.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        btnRegistrarProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarProductos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRegistrarProductos.setText("Registrar");
        btnRegistrarProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegistrarProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrarProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrarProductosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegistrarProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegistrarProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelProductos.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 90, 30));

        jPanel11.setBackground(new java.awt.Color(31, 134, 195));

        btnModificarProductos.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        btnModificarProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarProductos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnModificarProductos.setText("Modificar");
        btnModificarProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificarProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarProductosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModificarProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModificarProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelProductos.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 90, 30));

        jPanel12.setBackground(new java.awt.Color(31, 134, 195));

        btnEliminarProductos.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        btnEliminarProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarProductos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminarProductos.setText("Eliminar");
        btnEliminarProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarProductosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminarProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminarProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        PanelProductos.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 90, 30));

        boxProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {""}));
        boxProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProductos.add(boxProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 150, -1));

        boxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {""}));
        boxCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelProductos.add(boxCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 240, 170, -1));

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Borde2.png"))); // NOI18N
        PanelProductos.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 280, 240));

        txtIdProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProductoActionPerformed(evt);
            }
        });
        PanelProductos.add(txtIdProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 210, -1, 0));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoBlack.png"))); // NOI18N
        PanelProductos.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 370, 240));

        ScrollPanel.addTab("tab4", PanelProductos);

        PanelReportes.setBackground(new java.awt.Color(204, 204, 204));
        PanelReportes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        PanelReportes.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 640, 10));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        PanelReportes.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 640, 10));

        btnCallVentas.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCallVentas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCallVentas.setText("Ventas");
        btnCallVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCallVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCallVentasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCallVentasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCallVentasMouseExited(evt);
            }
        });
        PanelReportes.add(btnCallVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 130, 30));

        btnCallClientes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCallClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCallClientes.setText("Clientes");
        btnCallClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCallClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCallClientesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCallClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCallClientesMouseExited(evt);
            }
        });
        PanelReportes.add(btnCallClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 130, 30));

        btnCallProveedores.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCallProveedores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCallProveedores.setText("Proveedores");
        btnCallProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCallProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCallProveedoresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCallProveedoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCallProveedoresMouseExited(evt);
            }
        });
        PanelReportes.add(btnCallProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 130, 30));

        btnCallProductos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCallProductos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCallProductos.setText("Productos");
        btnCallProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCallProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCallProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCallProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCallProductosMouseExited(evt);
            }
        });
        PanelReportes.add(btnCallProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 140, 30));

        jPanel15.setBackground(new java.awt.Color(31, 134, 195));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        PanelReportes.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, -1, -1));

        jPanel16.setBackground(new java.awt.Color(31, 134, 195));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        PanelReportes.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 140, -1));

        jPanel14.setBackground(new java.awt.Color(31, 134, 195));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        PanelReportes.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 130, 30));

        jPanel13.setBackground(new java.awt.Color(31, 134, 195));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        PanelReportes.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 130, 30));

        btnOpen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Imprimir.png"))); // NOI18N
        btnOpen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOpenMouseClicked(evt);
            }
        });
        PanelReportes.add(btnOpen, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 50, 50));
        PanelReportes.add(txtIdVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 0, 0));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Borde.png"))); // NOI18N
        PanelReportes.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 280, 240));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoBlack.png"))); // NOI18N
        PanelReportes.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 370, 240));

        TableReportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TableReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableReportesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableReportes);

        PanelReportes.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 180));

        ScrollPanel.addTab("tab5", PanelReportes);

        jPanel2.add(ScrollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 640, 440));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void NewVentaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewVentaMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewVentaMousePressed

    private void NewVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewVentaMouseEntered
        NewVenta.setForeground(Color.BLACK);
    }//GEN-LAST:event_NewVentaMouseEntered

    private void NewVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewVentaMouseExited
        NewVenta.setForeground(Color.WHITE);
    }//GEN-LAST:event_NewVentaMouseExited

    private void ClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientesMouseEntered
        Clientes.setForeground(Color.BLACK);
    }//GEN-LAST:event_ClientesMouseEntered

    private void ClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientesMouseExited
        Clientes.setForeground(Color.WHITE);
    }//GEN-LAST:event_ClientesMouseExited

    private void ProveedoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProveedoresMouseEntered
        Proveedores.setForeground(Color.BLACK);
    }//GEN-LAST:event_ProveedoresMouseEntered

    private void ProveedoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProveedoresMouseExited
        Proveedores.setForeground(Color.WHITE);
    }//GEN-LAST:event_ProveedoresMouseExited

    private void ProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductosMouseEntered
        Productos.setForeground(Color.BLACK);
    }//GEN-LAST:event_ProductosMouseEntered

    private void ProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductosMouseExited
        Productos.setForeground(Color.WHITE);
    }//GEN-LAST:event_ProductosMouseExited

    private void ReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportesMouseEntered
        Reportes.setForeground(Color.BLACK);
    }//GEN-LAST:event_ReportesMouseEntered

    private void ReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportesMouseExited
        Reportes.setForeground(Color.WHITE);
    }//GEN-LAST:event_ReportesMouseExited

    private void SalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_SalirMouseClicked

    private void SalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMouseEntered
        Salir.setForeground(Color.red);
    }//GEN-LAST:event_SalirMouseEntered

    private void SalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMouseExited
        Salir.setForeground(Color.black);
    }//GEN-LAST:event_SalirMouseExited

    private void Panel_ExitMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_ExitMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_Panel_ExitMouseDragged

    private void Panel_ExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_ExitMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_Panel_ExitMousePressed

    private void NewVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewVentaMouseClicked
        ScrollPanel.setSelectedComponent(PanelVentas);
        LimpiarTabla();
    }//GEN-LAST:event_NewVentaMouseClicked

    private void SalirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMousePressed
        Salir.setBackground(Color.WHITE);
    }//GEN-LAST:event_SalirMousePressed

    private void CerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CerrarMouseClicked
        int opcion = JOptionPane.showOptionDialog(
                this,
                "¿Quieres cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Si", "No"},
                "Si"
        );

        if (opcion == JOptionPane.YES_OPTION) {

            Login ox = new Login();
            ox.setVisible(true);
            dispose();
        }


    }//GEN-LAST:event_CerrarMouseClicked

    private void CerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CerrarMouseEntered
        Cerrar.setForeground(Color.BLACK);
    }//GEN-LAST:event_CerrarMouseEntered

    private void CerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CerrarMouseExited
        Cerrar.setForeground(Color.WHITE);
    }//GEN-LAST:event_CerrarMouseExited

    private void ClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClientesMouseClicked

        ScrollPanel.setSelectedComponent(PanelClientes);
        LimpiarTabla();
        ListarCliente();
        Clientes.setForeground(Color.black);

    }//GEN-LAST:event_ClientesMouseClicked

    private void ProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProveedoresMouseClicked
        ScrollPanel.setSelectedComponent(PanelProveedores);
        LimpiarTabla();
        listarProveedor();
    }//GEN-LAST:event_ProveedoresMouseClicked

    private void ProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductosMouseClicked

        ScrollPanel.setSelectedComponent(PanelProductos);
        LimpiarTabla();
        listarProductos();


    }//GEN-LAST:event_ProductosMouseClicked

    private void ReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportesMouseClicked
        ScrollPanel.setSelectedComponent(PanelReportes);
    }//GEN-LAST:event_ReportesMouseClicked

    private void txtCIClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCIClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCIClientesActionPerformed

    private void txtRegistrarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegistrarClientesMouseClicked
        if (!"".equals(txtNombreClientes.getText()) || !"".equals(txtApellidoClientes.getText()) || !"".equals(txtCIClientes.getText()) || !"".equals(txtDireccionClientes.getText()) || !"".equals(txtTelefonoClientes.getText())) {
            cl.setNombre(txtNombreClientes.getText());
            cl.setApellido(txtApellidoClientes.getText());
            cl.setCi(txtCIClientes.getText());
            cl.setDireccion(txtDireccionClientes.getText());
            cl.setTelefono(txtTelefonoClientes.getText());
            client.insertarCliente(cl);
            LimpiarTabla();
            ListarCliente();
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
        } else {
            JOptionPane.showMessageDialog(null, "Error al Registrar");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegistrarClientesMouseClicked

    private void txtRegistrarClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegistrarClientesMouseEntered
        txtRegistrarClientes.setForeground(Color.black);
    }//GEN-LAST:event_txtRegistrarClientesMouseEntered

    private void txtRegistrarClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegistrarClientesMouseExited
        txtRegistrarClientes.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtRegistrarClientesMouseExited

    private void bntModificarClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntModificarClientesMouseEntered
        bntModificarClientes.setForeground(Color.BLACK);
    }//GEN-LAST:event_bntModificarClientesMouseEntered

    private void bntModificarClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntModificarClientesMouseExited
        bntModificarClientes.setForeground(Color.WHITE);
    }//GEN-LAST:event_bntModificarClientesMouseExited

    private void bntModificarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bntModificarClientesMouseClicked
        if (!"".equals(txtId_cliente.getText())) {
            int clienteID = Integer.parseInt(txtId_cliente.getText());
            String nombre = txtNombreClientes.getText();
            String apellido = txtApellidoClientes.getText();
            String ci = txtCIClientes.getText();
            String direccion = txtDireccionClientes.getText();
            String telefono = txtTelefonoClientes.getText();

            DataClientes clienteModificado = new DataClientes(clienteID, nombre, apellido, ci, direccion, telefono);
            boolean modificado = client.modificarCliente(clienteID, clienteModificado);

            if (modificado) {
                JOptionPane.showMessageDialog(null, "Cliente modificado correctamente");

                LimpiarTabla();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el ID del cliente que desea modificar");
        }
    }//GEN-LAST:event_bntModificarClientesMouseClicked

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        int fila = TableCliente.rowAtPoint(evt.getPoint());
        txtId_cliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtNombreClientes.setText(TableCliente.getValueAt(fila, 1).toString());
        txtApellidoClientes.setText(TableCliente.getValueAt(fila, 2).toString());
        txtCIClientes.setText(TableCliente.getValueAt(fila, 3).toString());
        txtDireccionClientes.setText(TableCliente.getValueAt(fila, 4).toString());
        txtTelefonoClientes.setText(TableCliente.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btnEliminarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarClientesMouseClicked
        if (!"".equals(txtId_cliente.getText())) {
            if (!"".equals(txtId_cliente.getText())) {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este Registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (pregunta == JOptionPane.YES_OPTION) {
                    int clienteID = Integer.parseInt(txtId_cliente.getText());
                    boolean eliminado = client.borrarCliente(clienteID);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
                        LimpiarTabla();
                        ListarCliente();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese el ID del cliente que desea eliminar");
            }
        }
    }//GEN-LAST:event_btnEliminarClientesMouseClicked

    private void btnEliminarClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarClientesMouseEntered
        btnEliminarClientes.setForeground(Color.black);
    }//GEN-LAST:event_btnEliminarClientesMouseEntered

    private void btnEliminarClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarClientesMouseExited
        btnEliminarClientes.setForeground(Color.white);
    }//GEN-LAST:event_btnEliminarClientesMouseExited

    private void btnRegistrarProveedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarProveedorMouseExited
        btnRegistrarProveedor.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnRegistrarProveedorMouseExited

    private void btnRegistrarProveedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarProveedorMouseEntered
        btnRegistrarProveedor.setForeground(Color.black);
    }//GEN-LAST:event_btnRegistrarProveedorMouseEntered

    private void btnRegistrarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarProveedorMouseClicked
        if (!"".equals(txtContactoProveedor.getText()) || !"".equals(txtRifProveedor.getText()) || !"".equals(txtDireccionProveedor.getText()) || !"".equals(txtCiudadProveedor.getText()) || !"".equals(txtEstadoProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText())) {
            pr.setNombre_contacto(txtContactoProveedor.getText());
            pr.setRif(txtRifProveedor.getText());
            pr.setDireccion(txtDireccionProveedor.getText());
            pr.setCiudad(txtCiudadProveedor.getText());
            pr.setEstado(txtEstadoProveedor.getText());
            pr.setTelefono(txtTelefonoProveedor.getText());
            pro.insertarProveedor(pr);
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");

        } else {
            JOptionPane.showMessageDialog(null, "Error al Registar");
        }
    }//GEN-LAST:event_btnRegistrarProveedorMouseClicked

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txtId_proveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtContactoProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtRifProveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtDireccionProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtCiudadProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
        txtEstadoProveedor.setText(TableProveedor.getValueAt(fila, 5).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 6).toString());

    }//GEN-LAST:event_TableProveedorMouseClicked

    private void btnEliminarProveedoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProveedoresMouseExited
        btnEliminarProveedores.setForeground(Color.white);
    }//GEN-LAST:event_btnEliminarProveedoresMouseExited

    private void btnEliminarProveedoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProveedoresMouseEntered
        btnEliminarProveedores.setForeground(Color.black);
    }//GEN-LAST:event_btnEliminarProveedoresMouseEntered

    private void btnEliminarProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProveedoresMouseClicked
        if (!"".equals(txtId_proveedor.getText())) {
            if (!"".equals(txtId_proveedor.getText())) {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este Registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (pregunta == JOptionPane.YES_OPTION) {
                    int proveedorID = Integer.parseInt(txtId_proveedor.getText());
                    boolean eliminado = pro.borrarProveedor(proveedorID);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Proveedor eliminado correctamente");
                        LimpiarTabla();
                        listarProveedor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el Proveedor");
                    }
                }
            }
        }

    }//GEN-LAST:event_btnEliminarProveedoresMouseClicked

    private void btnModificarProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedoresMouseClicked
        if (!"".equals(txtId_proveedor.getText())) {
            int proveedorID = Integer.parseInt(txtId_proveedor.getText());
            String nombreContacto = txtContactoProveedor.getText();
            String rif = txtRifProveedor.getText();
            String direccion = txtDireccionProveedor.getText();
            String ciudad = txtCiudadProveedor.getText();
            String estado = txtEstadoProveedor.getText();
            String telefono = txtTelefonoProveedor.getText();

            DataProveedor proveedorModificado = new DataProveedor(proveedorID, nombreContacto, rif, direccion, ciudad, estado, telefono);
            boolean modificado = pro.modificarProveedor(proveedorModificado);

            if (modificado) {
                JOptionPane.showMessageDialog(null, "Proveedor modificado correctamente");
                LimpiarTabla();
                listarProveedor();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el Proveedor");
            }
        }
    }//GEN-LAST:event_btnModificarProveedoresMouseClicked

    private void btnModificarProveedoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedoresMouseEntered
        btnModificarProveedores.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnModificarProveedoresMouseEntered

    private void btnModificarProveedoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProveedoresMouseExited
        btnModificarProveedores.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnModificarProveedoresMouseExited

    private void txtIdProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProductoActionPerformed

    private void btnRegistrarProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarProductosMouseClicked
        if (!"".equals(txtDescripcionPro.getText())
                || !"".equals(txtPrecioPro.getText())
                || !"".equals(txtStockPro.getText())
                || !"".equals(boxProveedor.getSelectedItem())
                || !"".equals(boxCategoria.getSelectedItem())) {

            Dao.setDescripcion(txtDescripcionPro.getText());
            Dao.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
            Dao.setStock(Integer.parseInt(txtStockPro.getText()));
            Dao.setProveedor(boxProveedor.getSelectedItem().toString());
            Dao.setCategoria(boxCategoria.getSelectedItem().toString());
            pron.insertarProducto(Dao);
            JOptionPane.showMessageDialog(null, "Registro exitoso");
            LimpiarTabla();
            listarProductos();

        } else {
            JOptionPane.showMessageDialog(null, "Error al Registrar");
        }
    }//GEN-LAST:event_btnRegistrarProductosMouseClicked


    private void btnRegistrarProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarProductosMouseEntered
        btnRegistrarProductos.setForeground(Color.black);
    }//GEN-LAST:event_btnRegistrarProductosMouseEntered

    private void btnRegistrarProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarProductosMouseExited
        btnRegistrarProductos.setForeground(Color.white);
    }//GEN-LAST:event_btnRegistrarProductosMouseExited

    private void btnModificarProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProductosMouseClicked
        int row = TableProductos.getSelectedRow();
        if (row != -1) {
            String descripcion = txtDescripcionPro.getText();
            String precioStr = txtPrecioPro.getText();
            String stockStr = txtStockPro.getText();
            String proveedor = boxProveedor.getSelectedItem().toString();
            String categoria = boxCategoria.getSelectedItem().toString();

            if (!descripcion.isEmpty() && !precioStr.isEmpty() && !stockStr.isEmpty() && !proveedor.isEmpty() && !categoria.isEmpty()) {
                int productoId = Integer.parseInt(TableProductos.getValueAt(row, 0).toString());
                double precio = Double.parseDouble(precioStr);
                int stock = Integer.parseInt(stockStr);

                DataProductos productoModificado = new DataProductos(productoId, descripcion, precio, stock, proveedor, categoria);
                boolean modificado = pron.modificarProducto(productoModificado);
                JOptionPane.showMessageDialog(null, "Producto modificado correctamente");
                LimpiarTabla();
                listarProductos();
            } else {
                JOptionPane.showMessageDialog(null, " Error al modificar el Producto");
            }
        }
    }//GEN-LAST:event_btnModificarProductosMouseClicked

    private void btnModificarProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProductosMouseEntered
        btnModificarProductos.setForeground(Color.black);
    }//GEN-LAST:event_btnModificarProductosMouseEntered

    private void btnModificarProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarProductosMouseExited
        btnModificarProductos.setForeground(Color.white);
    }//GEN-LAST:event_btnModificarProductosMouseExited

    private void btnEliminarProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductosMouseClicked
        if (!"".equals(txtIdProducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este Registro?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
            if (pregunta == JOptionPane.YES_OPTION) {
                int productoId = Integer.parseInt(txtIdProducto.getText());
                boolean eliminado = pron.eliminarProducto(productoId);
                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                    LimpiarTabla();
                    listarProductos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el Producto");
                }
            }
        }

    }//GEN-LAST:event_btnEliminarProductosMouseClicked

    private void btnEliminarProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductosMouseEntered
        btnEliminarProductos.setForeground(Color.black);
    }//GEN-LAST:event_btnEliminarProductosMouseEntered

    private void btnEliminarProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductosMouseExited
        btnEliminarProductos.setForeground(Color.white);
    }//GEN-LAST:event_btnEliminarProductosMouseExited

    private void TableProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductosMouseClicked
        int fila = TableProductos.rowAtPoint(evt.getPoint());
        txtIdProducto.setText(TableProductos.getValueAt(fila, 0).toString());
        txtDescripcionPro.setText(TableProductos.getValueAt(fila, 1).toString());
        txtPrecioPro.setText(TableProductos.getValueAt(fila, 2).toString());
        txtStockPro.setText(TableProductos.getValueAt(fila, 3).toString());
        boxProveedor.setSelectedItem(TableProductos.getValueAt(fila, 4).toString());
        boxCategoria.setSelectedItem(TableProductos.getValueAt(fila, 5).toString());

    }//GEN-LAST:event_TableProductosMouseClicked

    private void btnCallVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallVentasMouseClicked
        LimpiarTabla();
        listarVentas();

    }//GEN-LAST:event_btnCallVentasMouseClicked

    private void btnCallVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallVentasMouseEntered
        btnCallVentas.setForeground(Color.white);
    }//GEN-LAST:event_btnCallVentasMouseEntered

    private void btnCallVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallVentasMouseExited
        btnCallVentas.setForeground(Color.black);
    }//GEN-LAST:event_btnCallVentasMouseExited

    private void btnCallClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallClientesMouseClicked
        LimpiarTabla();
        ListarReportesClientes();

        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea generar el PDF?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            generarPDFClientes();
        }

    }//GEN-LAST:event_btnCallClientesMouseClicked

    private void btnCallClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallClientesMouseEntered
        btnCallClientes.setForeground(Color.white);
    }//GEN-LAST:event_btnCallClientesMouseEntered

    private void btnCallClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallClientesMouseExited
        btnCallClientes.setForeground(Color.black);
    }//GEN-LAST:event_btnCallClientesMouseExited

    private void btnCallProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallProveedoresMouseClicked
        LimpiarTabla();
        listarReportesProveedor();

        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea generar el PDF?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            generarPDFProveedores();
        }
    }//GEN-LAST:event_btnCallProveedoresMouseClicked

    private void btnCallProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallProductosMouseClicked
        LimpiarTabla();
        listarReportesProductos();
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea generar el PDF?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            generarPDFProductos();
        }
    }//GEN-LAST:event_btnCallProductosMouseClicked

    private void btnCallProveedoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallProveedoresMouseEntered
        btnCallProveedores.setForeground(Color.white);
    }//GEN-LAST:event_btnCallProveedoresMouseEntered

    private void btnCallProveedoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallProveedoresMouseExited
        btnCallProveedores.setForeground(Color.black);
    }//GEN-LAST:event_btnCallProveedoresMouseExited

    private void btnCallProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallProductosMouseEntered
        btnCallProductos.setForeground(Color.white);
    }//GEN-LAST:event_btnCallProductosMouseEntered

    private void btnCallProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCallProductosMouseExited
        btnCallProductos.setForeground(Color.black);
    }//GEN-LAST:event_btnCallProductosMouseExited

    private void txtCallIdProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCallIdProductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCallIdProducto.getText())) {
                String cod = txtCallIdProducto.getText();
                Dao = pron.BuscarPro(cod);
                if (Dao.getDescripcion() != null) {
                    txtCallDescripcion.setText("" + Dao.getDescripcion());
                    txtCallPrecio.setText("" + Dao.getPrecio());
                    txtCallStock.setText("" + Dao.getStock());
                    txtCallCantidad.requestFocus();
                } else {
                    LimpiarVenta();
                    txtIdProducto.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el ID del producto");
                txtIdProducto.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCallIdProductoKeyPressed

    private void txtCallCIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCallCIKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCallCI.getText())) {
                String ci = txtCallCI.getText();
                String nombreCliente = client.BuscarNombreClientePorCI(ci);
                if (nombreCliente != null) {
                    txtCallNombre.setText(nombreCliente);
                } else {

                    txtCallNombre.setText("");
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {

                JOptionPane.showMessageDialog(this, "Ingrese el CI del cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtCallCIKeyPressed

    private void txtCallCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCallCantidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCallCantidad.getText())) {
                String cod = txtCallIdProducto.getText();
                String descripcion = txtCallDescripcion.getText();
                int cant = Integer.parseInt(txtCallCantidad.getText());
                double precio = Double.parseDouble(txtCallPrecio.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txtCallStock.getText());
                if (stock >= cant) {
                    item = item + 1;
                    modelo = (DefaultTableModel) TableVenta.getModel();
                    for (int i = 0; i < TableVenta.getRowCount(); i++) {
                        if (TableVenta.getValueAt(i, 1).equals(txtCallDescripcion.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya está registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(cod);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[6];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    modelo.addRow(O);
                    TableVenta.setModel(modelo);
                    TotalPagar();
                    LimpiarVenta();
                    txtCallIdProducto.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese la cantidad");
            }
        }
    }//GEN-LAST:event_txtCallCantidadKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        modelo = (DefaultTableModel) TableVenta.getModel();
        modelo.removeRow(TableVenta.getSelectedRow());
        TotalPagar();
        txtIdProducto.requestFocus();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {
            RegistrarVenta();
            RegistrarDetalle();
            JOptionPane.showMessageDialog(this, "Venta realizada exitosamente");
            pdf();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la Venta ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtCallIdProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCallIdProductoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCallIdProductoKeyTyped

    private void txtCallDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCallDescripcionKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtCallDescripcionKeyTyped

    private void txtNombreClientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClientesKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreClientesKeyTyped

    private void TableReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableReportesMouseClicked
        int fila = TableReportes.rowAtPoint(evt.getPoint());
        txtIdVentas.setText(TableReportes.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableReportesMouseClicked

    private void btnOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOpenMouseClicked

        try {
            int id = Integer.parseInt(txtIdVentas.getText());
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File file = new File(desktopPath + "venta" + id + ".pdf");
            Desktop.getDesktop().open(file);

        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnOpenMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BCiudad;
    private javax.swing.JLabel BContacto;
    private javax.swing.JLabel BDireccion;
    private javax.swing.JLabel BEstado;
    private javax.swing.JLabel BRif;
    private javax.swing.JLabel BTelefono;
    private javax.swing.JLabel Bienvenido;
    private javax.swing.JLabel Cerrar;
    private javax.swing.JLabel Clientes;
    private javax.swing.JLabel F1;
    private javax.swing.JLabel F2;
    private javax.swing.JLabel Fondo1;
    private javax.swing.JLabel Fondo2;
    private javax.swing.JLabel LBApellido;
    private javax.swing.JLabel LBCI;
    private javax.swing.JLabel LBDireccion;
    private javax.swing.JLabel LBNombre;
    private javax.swing.JLabel LBTelefono;
    private javax.swing.JLabel LCI;
    private javax.swing.JLabel LID;
    private javax.swing.JLabel LStock;
    private javax.swing.JTextField LabelTotal;
    private javax.swing.JLabel Lcantidad;
    private javax.swing.JLabel Ldescripcion;
    private javax.swing.JLabel Lnombre;
    private javax.swing.JLabel Lprecio;
    private javax.swing.JLabel Ltotal;
    private javax.swing.JLabel NewVenta;
    private javax.swing.JPanel PanelClientes;
    private javax.swing.JPanel PanelProductos;
    private javax.swing.JPanel PanelProveedores;
    private javax.swing.JPanel PanelReportes;
    private javax.swing.JPanel PanelVentas;
    private javax.swing.JPanel Panel_Exit;
    private javax.swing.JLabel Productos;
    private javax.swing.JLabel Prov;
    private javax.swing.JLabel Proveedores;
    private javax.swing.JLabel PuntoVenta;
    private javax.swing.JLabel Reportes;
    private javax.swing.JLabel Salir;
    private javax.swing.JTabbedPane ScrollPanel;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableProductos;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableReportes;
    private javax.swing.JTable TableVenta;
    private javax.swing.JLabel bntModificarClientes;
    private javax.swing.JComboBox<String> boxCategoria;
    private javax.swing.JComboBox<String> boxProveedor;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JLabel btnCallClientes;
    private javax.swing.JLabel btnCallProductos;
    private javax.swing.JLabel btnCallProveedores;
    private javax.swing.JLabel btnCallVentas;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel btnEliminarClientes;
    private javax.swing.JLabel btnEliminarProductos;
    private javax.swing.JLabel btnEliminarProveedores;
    private javax.swing.JLabel btnModificarProductos;
    private javax.swing.JLabel btnModificarProveedores;
    private javax.swing.JLabel btnOpen;
    private javax.swing.JLabel btnRegistrarProductos;
    private javax.swing.JLabel btnRegistrarProveedor;
    private javax.swing.JLabel f1;
    private javax.swing.JLabel f2;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JTextField txtApellidoClientes;
    private javax.swing.JTextField txtCIClientes;
    private javax.swing.JTextField txtCallCI;
    private javax.swing.JTextField txtCallCantidad;
    private javax.swing.JTextField txtCallDescripcion;
    private javax.swing.JTextField txtCallIdProducto;
    private javax.swing.JTextField txtCallNombre;
    private javax.swing.JTextField txtCallPrecio;
    private javax.swing.JTextField txtCallStock;
    private javax.swing.JTextField txtCiudadProveedor;
    private javax.swing.JTextField txtContactoProveedor;
    private javax.swing.JTextField txtDescripcionPro;
    private javax.swing.JTextField txtDireccionClientes;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtEstadoProveedor;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtIdVentas;
    private javax.swing.JTextField txtId_cliente;
    private javax.swing.JTextField txtId_proveedor;
    private javax.swing.JTextField txtNombreClientes;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JLabel txtRegistrarClientes;
    private javax.swing.JTextField txtRifProveedor;
    private javax.swing.JTextField txtStockPro;
    private javax.swing.JTextField txtTelefonoClientes;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }

    private void LimpiarVenta() {
        txtCallIdProducto.setText("");
        txtCallDescripcion.setText("");
        txtCallPrecio.setText("");
        txtCallCantidad.setText("");
        txtCallStock.setText("");
    }

    private void RegistrarVenta() {
        String cliente = txtCallCI.getText();
        String montoStr = LabelTotal.getText().replace(",", ".");
        float monto = Float.parseFloat(montoStr);

        v.setCi_cliente(cliente);
        v.setTotal_venta(monto);
        ventas.InsertarVenta(v);

    }

    private void RegistrarDetalle() {
        int id = ventas.IdVenta();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int cod = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());

            Dv.setId_producto(cod);
            Dv.setCantidad(cant);
            Dv.setPrecio_unitario(precio);
            Dv.setId_venta(id);
            ventas.RegistrarDetalle(Dv);

        }
    }

    private void pdf() {
        try {
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            String baseFileName = "venta.pdf";
            File file = new File(desktopPath + baseFileName);

            int count = 1;
            while (file.exists()) {

                String newFileName = String.format("venta%d.pdf", count);
                file = new File(desktopPath + newFileName);
                count++;
            }

            FileOutputStream archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Image img = Image.getInstance("C:/Users/Pc/Desktop/Tienda.png");
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.GRAY);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura" + "\nFecha: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);

            String Rif = "12345678";
            String nombre = "Tienda C.A.";
            String Direccion = "Av Las Delicias, Maracaibo, Edo Zulia";
            String Telefono = "0212 1234567";

            Encabezado.addCell("");
            Encabezado.addCell("Rif: J-" + Rif + "\nNombre: " + nombre + "\nDireccion: " + Direccion + "\nTelefono: " + Telefono);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            Paragraph Cli = new Paragraph();
            Cli.add(Chunk.NEWLINE);
            Cli.add("Datos de los Clientes" + "\n\n");
            doc.add(Cli);

            PdfPTable tableCli = new PdfPTable(2);
            tableCli.setWidthPercentage(100);
            tableCli.getDefaultCell().setBorder(0);
            float[] ColumnaCli = new float[]{20f, 50f};
            tableCli.setWidths(ColumnaCli);
            tableCli.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell Cl1 = new PdfPCell(new Phrase("CI", negrita));
            PdfPCell Cl2 = new PdfPCell(new Phrase("Nombre", negrita));

            Cl1.setBorder(0);
            Cl2.setBorder(0);

            Cl1.setBackgroundColor(BaseColor.DARK_GRAY);
            Cl2.setBackgroundColor(BaseColor.DARK_GRAY);

            tableCli.addCell(Cl1);
            tableCli.addCell(Cl2);

            tableCli.addCell(txtCallCI.getText());
            tableCli.addCell(txtCallNombre.getText());

            doc.add(tableCli);

            PdfPTable tablePro = new PdfPTable(4);
            tablePro.setWidthPercentage(100);
            tablePro.getDefaultCell().setBorder(0);
            float[] ColumnaPro = new float[]{30f, 50f, 15f, 20f};
            tablePro.setWidths(ColumnaPro);
            tablePro.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell Pro1 = new PdfPCell(new Phrase("Descripcion", negrita));
            PdfPCell Pro2 = new PdfPCell(new Phrase("Cantidad", negrita));
            PdfPCell Pro3 = new PdfPCell(new Phrase("Precio U", negrita));
            PdfPCell Pro4 = new PdfPCell(new Phrase("Total", negrita));

            Pro1.setBorder(0);
            Pro2.setBorder(0);
            Pro3.setBorder(0);
            Pro4.setBorder(0);

            Pro1.setBackgroundColor(BaseColor.DARK_GRAY);
            Pro2.setBackgroundColor(BaseColor.DARK_GRAY);
            Pro3.setBackgroundColor(BaseColor.DARK_GRAY);
            Pro4.setBackgroundColor(BaseColor.DARK_GRAY);

            tablePro.addCell(Pro1);
            tablePro.addCell(Pro2);
            tablePro.addCell(Pro3);
            tablePro.addCell(Pro4);

            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                String desc = TableVenta.getValueAt(i, 1).toString();
                String cant = TableVenta.getValueAt(i, 2).toString();
                String Precio = TableVenta.getValueAt(i, 3).toString();
                String Tot = TableVenta.getValueAt(i, 4).toString();

                tablePro.addCell(desc);
                tablePro.addCell(cant);
                tablePro.addCell(Precio);
                tablePro.addCell(Tot);
            }

            doc.add(tablePro);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a Pagar: " + LabelTotal.getText());
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Firma: \n\n ");
            firma.add("-------------------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su compra :3");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

    private void generarPDFClientes() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reporte Clientes.pdf"));
            documento.open();

            Paragraph title = new Paragraph("Reporte de Clientes" + "\n\n", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            documento.add(title);

            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);

            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Apellido");
            tabla.addCell("CI");
            tabla.addCell("Direccion");
            tabla.addCell("Telefono");

            DefaultTableModel modelo = (DefaultTableModel) TableReportes.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                tabla.addCell(modelo.getValueAt(i, 0).toString());
                tabla.addCell(modelo.getValueAt(i, 1).toString());
                tabla.addCell(modelo.getValueAt(i, 2).toString());
                tabla.addCell(modelo.getValueAt(i, 3).toString());
                tabla.addCell(modelo.getValueAt(i, 4).toString());
                tabla.addCell(modelo.getValueAt(i, 5).toString());
            }

            documento.add(tabla);
            documento.close();

            JOptionPane.showMessageDialog(null, "Reporte Clientes creado");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void generarPDFProveedores() {
        try {
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File file = new File(desktopPath + "reporte proveedores.pdf");
            int count = 1;
            while (file.exists()) {
                file = new File(desktopPath + "reporte proveedores" + count + ".pdf");
                count++;
            }

            FileOutputStream archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Paragraph title = new Paragraph("Reporte de Proveedores" + "\n\n", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);

            tabla.addCell("ID");
            tabla.addCell("Contacto");
            tabla.addCell("Rif");
            tabla.addCell("Direccion");
            tabla.addCell("Ciudad");
            tabla.addCell("Estado");
            tabla.addCell("Telefono");

            for (int i = 0; i < TableReportes.getRowCount(); i++) {
                String id = TableReportes.getValueAt(i, 0).toString();
                String contacto = TableReportes.getValueAt(i, 1).toString();
                String rif = TableReportes.getValueAt(i, 2).toString();
                String direccion = TableReportes.getValueAt(i, 3).toString();
                String ciudad = TableReportes.getValueAt(i, 4).toString();
                String estado = TableReportes.getValueAt(i, 5).toString();
                String telefono = TableReportes.getValueAt(i, 6).toString();

                tabla.addCell(id);
                tabla.addCell(contacto);
                tabla.addCell(rif);
                tabla.addCell(direccion);
                tabla.addCell(ciudad);
                tabla.addCell(estado);
                tabla.addCell(telefono);
            }

            doc.add(tabla);

            doc.close();
            archivo.close();
            JOptionPane.showMessageDialog(null, "Reporte Proveedores creado");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void generarPDFProductos() {
        Document documento = new Document();
        try {
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File file = new File(desktopPath + "reporte productos.pdf");
            int count = 1;
            while (file.exists()) {
                file = new File(desktopPath + "reporte productos" + count + ".pdf");
                count++;
            }

            FileOutputStream archivo = new FileOutputStream(file);
            PdfWriter.getInstance(documento, archivo);
            documento.open();

            Paragraph title = new Paragraph("Reporte de Productos" + "\n\n", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            documento.add(title);

            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);

            tabla.addCell("ID");
            tabla.addCell("Descripcion");
            tabla.addCell("Precio");
            tabla.addCell("Stock");
            tabla.addCell("Proveedor");
            tabla.addCell("Categoria");

            DefaultTableModel modelo = (DefaultTableModel) TableReportes.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                tabla.addCell(modelo.getValueAt(i, 0).toString());
                tabla.addCell(modelo.getValueAt(i, 1).toString());
                tabla.addCell(modelo.getValueAt(i, 2).toString());
                tabla.addCell(modelo.getValueAt(i, 3).toString());
                tabla.addCell(modelo.getValueAt(i, 4).toString());
                tabla.addCell(modelo.getValueAt(i, 5).toString());
            }

            documento.add(tabla);
            documento.close();
            archivo.close();

            JOptionPane.showMessageDialog(null, "Reporte Productos creado");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}
