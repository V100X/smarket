package vista;

import  controlador.Controlador;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelo.ArrayProductos;
import modelo.ImgTabla;
import modelo.Producto;

public class MainView extends javax.swing.JFrame {

    int idUser= 0;
    Producto pr = new Producto();
    ArrayProductos apr, apr2, apr3;
    Controlador c ;
    MainView mv;
    

    public MainView(String nombre, int id){
        initComponents();
        this.c = new Controlador();
        this.idUser = id;
        
            this.getContentPane().setBackground(Color.white);
            jScrollPane1.getViewport().setBackground(Color.getHSBColor(0F,  0F,  0.97F));
            jScrollPane2.getViewport().setBackground(Color.getHSBColor(0F,  0F,  0.97F));
            jScrollPane3.getViewport().setBackground(Color.getHSBColor(0F,  0F,  0.97F));
        
            lblUsuLog.setText(nombre);
            lblLogOut.setText("Cerrar sesión");
                       
            apr = c.CargarTablaFav(id);
            llenarTablaFav(apr);
            
            llenarSecciones();      
  
            llenarTituloListas();
            
            tblFav.getTableHeader().setUI(null);
            tblSec.getTableHeader().setUI(null);
            tblList.getTableHeader().setUI(null);
         
            btnAddFav.setEnabled(true);
            btnAddList.setEnabled(true);
            btnRemoveFav.setEnabled(true);
            btnRemoveFromList.setEnabled(true);
            btnFav.setEnabled(true);
            btnGestList.setEnabled(true);
            cBoxList.setEnabled(true);
            cBoxList.setSelectedIndex(0);
            spnUds.setEnabled(true);
            sharedLogo.setVisible(false);
    }
    
    public MainView() {
        initComponents();

        mv = this;
        this.c = new Controlador();
        
        this.getContentPane().setBackground(Color.white);
        jScrollPane1.getViewport().setBackground(Color.getHSBColor(0F,  0F,  0.97F));
        jScrollPane2.getViewport().setBackground(Color.getHSBColor(0F,  0F,  0.97F));
        jScrollPane3.getViewport().setBackground(Color.getHSBColor(0F,  0F,  0.97F));
        
        llenarSecciones();
        
         tblFav.getTableHeader().setUI(null);
         tblSec.getTableHeader().setUI(null);
         tblList.getTableHeader().setUI(null);
           
        btnAddFav.setEnabled(false);
        btnAddList.setEnabled(false);
        btnRemoveFav.setEnabled(false);
        btnRemoveFromList.setEnabled(false);
        btnFav.setEnabled(false);
        btnGestList.setEnabled(false);
        cBoxList.setEnabled(false);
        spnUds.setEnabled(false);
        sharedLogo.setVisible(false);
        
    }
   
    
        public void llenarTablaFav(ArrayProductos apr){
            
         
        tblFav.setDefaultRenderer(Object.class, new ImgTabla());
        tblFav.setDefaultEditor(Object.class, null);
        
        tblFav.setCellSelectionEnabled(false);
        tblFav.setRowSelectionAllowed(true);
       
        String[] columnas= {"img", "nombre", "precio", "id"};
        
        DefaultTableModel dtm = new DefaultTableModel(null, columnas);
        
        for (var p : apr.arrayProd){
        
        dtm.addRow(new Object[]{new JLabel(new ImageIcon(getClass().getResource(p.getImg()))), p.getNombre(), String.valueOf(p.getPrecio()+" €"), p.getId()});
        }
            tblFav.setRowHeight(42);
            tblFav.setModel(dtm);
            tblFav.removeColumn(tblFav.getColumnModel().getColumn(3));
        }
        
        
        public void llenarSecciones(){
        
        ArrayList<String> secc = c.getSecciones();
            
        for ( int i =0; i < secc.size(); i++ ){

            cBoxSec.addItem(secc.get(i));
        }
            
        apr2 = c.CargarTablaProd();
        llenarTablaSec(apr2);
        
        }
        
        public void llenarTituloListas(){
        
        ArrayList<String> listas = c.getUserListsName(idUser);
        DefaultComboBoxModel model = new DefaultComboBoxModel();
            
            for ( int i =0; i < listas.size(); i++ ){

                model.addElement(listas.get(i));
            }
            cBoxList.setModel(model);
            
            apr3 = c.CargarTablaList(0, idUser);
            llenarTablaList(apr3);
        }
        
        public void llenarTablaSec(ArrayProductos ap){
            
         
        tblSec.setDefaultRenderer(Object.class, new ImgTabla());
        tblSec.setDefaultEditor(Object.class, null);
        
        tblSec.setCellSelectionEnabled(false);
        tblSec.setRowSelectionAllowed(true);
       
        String[] columnas= {"img", "nombre", "precio", "id"};
        
        DefaultTableModel dtm = new DefaultTableModel(null, columnas);
        
        for (var p : ap.arrayProd){
        
        dtm.addRow(new Object[]{new JLabel(new ImageIcon(getClass().getResource(p.getImg()))), p.getNombre(), String.valueOf(p.getPrecio()+" €"), p.getId()});
        }      
            tblSec.setRowHeight(42);
            tblSec.setModel(dtm);
            tblSec.removeColumn(tblSec.getColumnModel().getColumn(3));
        }
        
        public void llenarTablaList(ArrayProductos ap){   
         
        tblList.setDefaultRenderer(Object.class, new ImgTabla());
        tblList.setDefaultEditor(Object.class, null);
        
        tblList.setCellSelectionEnabled(false);
        tblList.setRowSelectionAllowed(true);
       
        String[] columnas= {"img", "nombre", "precio", "uds", "id"};
        
        DefaultTableModel dtm = new DefaultTableModel(null, columnas);
        
            for (var p : ap.arrayProd){
            
                dtm.addRow(new Object[]{new JLabel(new ImageIcon(getClass().getResource(p.getImg()))),
                p.getNombre(), String.valueOf(p.getPrecio()+" €"), String.valueOf(p.getUds()+" uds"), p.getId()});
            }

            tblList.setRowHeight(42);
            tblList.setModel(dtm);
            tblList.removeColumn(tblList.getColumnModel().getColumn(4));
        }

        
        public void cargarLista_Euros(){
        
        double total =0;
        int selectedIndex= cBoxList.getSelectedIndex();
    
            apr3 = c.CargarTablaList(selectedIndex, idUser);
            llenarTablaList(apr3);
            
                 for ( int x = 0; x < apr3.arrayProd.size(); x++){
                
                    total += apr3.getProd(x).getPrecio() * apr3.getProd(x).getUds() ;
                }
                DecimalFormat df = new DecimalFormat("####0.00", new DecimalFormatSymbols(Locale.ENGLISH));
                lblCanTotal.setText("Total: "+df.format(total)+" €");
                
                c.UpdateValorLista(idUser, selectedIndex,Double.valueOf(df.format(total)));
                mostrarFechaPedido();
        }
        
        
        public void mostrarFechaPedido(){
        
            int selectedIndex= cBoxList.getSelectedIndex();
            String fechaEnvio = c.getDateList(selectedIndex, idUser);
            
            if (fechaEnvio == null){
               lblFecha.setText("");
            } else {
            
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime fecha = LocalDateTime.parse(fechaEnvio, formatter);
            
                formatter = DateTimeFormatter.ofPattern("dd-MM-yy  HH:mm");
            
                lblFecha.setText("pedido  "+formatter.format(fecha)+"h");
            }
        }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cBoxList = new javax.swing.JComboBox<>();
        btnAddList = new javax.swing.JButton();
        btnAddFav = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSec = new javax.swing.JTable();
        lblSec = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        lblFav = new javax.swing.JLabel();
        lblList = new javax.swing.JLabel();
        cBoxSec = new javax.swing.JComboBox<>();
        btnRemoveFromList = new javax.swing.JButton();
        btnGestList = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        btnFav = new javax.swing.JButton();
        usuIcon = new javax.swing.JLabel();
        lblLogOut = new javax.swing.JLabel();
        lblCanTotal = new javax.swing.JLabel();
        btnRemoveFav = new javax.swing.JButton();
        lblUsuLog = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFav = new javax.swing.JTable();
        spnUds = new javax.swing.JSpinner();
        lblFecha = new javax.swing.JLabel();
        sharedLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SMARKET");
        setBackground(new java.awt.Color(254, 254, 254));

        cBoxList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBoxListActionPerformed(evt);
            }
        });

        btnAddList.setText("Añadir");
        btnAddList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddListActionPerformed(evt);
            }
        });

        btnAddFav.setText("Añadir");
        btnAddFav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFavActionPerformed(evt);
            }
        });

        tblSec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "imagen", "nombre", "precio", ""
            }
        ));
        tblSec.setColumnSelectionAllowed(true);
        tblSec.setGridColor(new java.awt.Color(207, 207, 207));
        tblSec.setSelectionBackground(new java.awt.Color(166, 208, 253));
        tblSec.setSelectionForeground(new java.awt.Color(4, 2, 2));
        tblSec.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSec.getTableHeader().setResizingAllowed(false);
        tblSec.getTableHeader().setReorderingAllowed(false);
        tblSec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblSecFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tblSec);
        tblSec.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        lblSec.setFont(new java.awt.Font("Noto Sans Medium", 0, 16)); // NOI18N
        lblSec.setText("Secciones");

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "nombre", "precio", "uds", ""
            }
        ));
        tblList.setGridColor(new java.awt.Color(207, 207, 207));
        tblList.setSelectionBackground(new java.awt.Color(166, 208, 253));
        tblList.setSelectionForeground(new java.awt.Color(4, 2, 2));
        tblList.getTableHeader().setResizingAllowed(false);
        tblList.getTableHeader().setReorderingAllowed(false);
        tblList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblListFocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(tblList);

        lblFav.setFont(new java.awt.Font("Noto Sans Medium", 0, 16)); // NOI18N
        lblFav.setText("Favoritos");

        lblList.setFont(new java.awt.Font("Noto Sans Medium", 0, 16)); // NOI18N
        lblList.setText("Listas");

        cBoxSec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas" }));
        cBoxSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBoxSecActionPerformed(evt);
            }
        });

        btnRemoveFromList.setText("Quitar");
        btnRemoveFromList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromListActionPerformed(evt);
            }
        });

        btnGestList.setText("Gestionar");
        btnGestList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestListActionPerformed(evt);
            }
        });

        lblNombre.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 28)); // NOI18N
        lblNombre.setText("SMARKET");

        btnFav.setText("Todos");
        btnFav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFavActionPerformed(evt);
            }
        });

        usuIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/usuLogoMini.png"))); // NOI18N
        usuIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usuIconMouseReleased(evt);
            }
        });

        lblLogOut.setBackground(new java.awt.Color(67, 67, 67));
        lblLogOut.setFont(new java.awt.Font("URW Gothic", 0, 16)); // NOI18N
        lblLogOut.setText("Crear cuenta");
        lblLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLogOutMouseReleased(evt);
            }
        });

        lblCanTotal.setFont(new java.awt.Font("URW Gothic", 0, 16)); // NOI18N
        lblCanTotal.setText(" ");

        btnRemoveFav.setText("Quitar");
        btnRemoveFav.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFavActionPerformed(evt);
            }
        });

        lblUsuLog.setFont(new java.awt.Font("URW Gothic", 0, 16)); // NOI18N
        lblUsuLog.setText("Iniciar sesión");
        lblUsuLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblUsuLogMouseReleased(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(197, 197, 197));

        tblFav.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "nombre", "precio", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblFav.setGridColor(new java.awt.Color(207, 207, 207));
        tblFav.setSelectionBackground(new java.awt.Color(166, 208, 253));
        tblFav.setSelectionForeground(new java.awt.Color(4, 2, 2));
        tblFav.getTableHeader().setResizingAllowed(false);
        tblFav.getTableHeader().setReorderingAllowed(false);
        tblFav.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblFavFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(tblFav);

        spnUds.setModel(new javax.swing.SpinnerNumberModel(1, 1, 30, 1));

        sharedLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sharedLogo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddFav, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemoveFav, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFav, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnFav, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSec, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cBoxSec, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sharedLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblList, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(usuIcon))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblUsuLog, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGestList, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cBoxList, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spnUds, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddList, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemoveFromList, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lblCanTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSec)
                            .addComponent(cBoxSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFav)
                            .addComponent(btnFav)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usuIcon)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsuLog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblLogOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBoxList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGestList)
                            .addComponent(lblList))))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnRemoveFav)
                                .addComponent(btnAddFav))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(sharedLogo)
                                .addGap(29, 29, 29)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnUds, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddList)
                            .addComponent(btnRemoveFromList)
                            .addComponent(lblCanTotal))))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddListActionPerformed

    int selectedIndex, idProd, uds = 0;
    
    if ( tblSec.getSelectedRow() >= 0 ){    

        idProd = (int) tblSec.getModel().getValueAt(tblSec.getSelectedRow(), 3);
        selectedIndex = cBoxList.getSelectedIndex();  
        uds = (Integer) spnUds.getValue();

        c.InsertProdList(idUser, selectedIndex, idProd, uds);
        
        cargarLista_Euros();
            
        tblSec.clearSelection();
        spnUds.setValue(1);
            
    } else if ( tblFav.getSelectedRow() >= 0 ){
    
        selectedIndex = cBoxList.getSelectedIndex();

        idProd = (int) tblFav.getModel().getValueAt(tblFav.getSelectedRow(), 3);      
        uds = (Integer) spnUds.getValue();

        c.InsertProdList(idUser, selectedIndex, idProd, uds);
        spnUds.setValue(1);
        
        cargarLista_Euros();

        tblFav.clearSelection();
    }
    }//GEN-LAST:event_btnAddListActionPerformed

    private void cBoxListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cBoxListActionPerformed
       
    int selectedIndex = cBoxList.getSelectedIndex();  
    
    if (c.isSharedList(selectedIndex, idUser)){
    
        sharedLogo.setVisible(true);
    } else {
    
        sharedLogo.setVisible(false);
    }
    
    cargarLista_Euros();
    }//GEN-LAST:event_cBoxListActionPerformed

    private void cBoxSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cBoxSecActionPerformed
        
    String selectedItem = cBoxSec.getItemAt(cBoxSec.getSelectedIndex());
        
    if ( "Todas".equals(selectedItem)){
    
        apr2 = c.CargarTablaProd();
        llenarTablaSec(apr2);
        
        if ("Secciones".equals(btnFav.getText())){
        
            apr = c.CargarTablaFav(idUser);
            llenarTablaFav(apr);
        }
        
    } else {
    
        apr2 = c.CargarTablaSec(selectedItem);
        llenarTablaSec(apr2);
        
        if ("Secciones".equals(btnFav.getText())){
        
            apr = c.CargarTablaFavBySecc(idUser, selectedItem);
            llenarTablaFav(apr);
        }
    }
    
    }//GEN-LAST:event_cBoxSecActionPerformed

    private void btnGestListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestListActionPerformed
        GestList gest = new GestList(this, true, idUser);
        gest.setVisible(true);
        llenarTituloListas();
        mostrarFechaPedido();
    }//GEN-LAST:event_btnGestListActionPerformed

    private void lblUsuLogMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuLogMouseReleased
        
        if ( "Iniciar sesión".equals(lblUsuLog.getText()) ) {
    
        LogIn login = new LogIn(this, true,mv);
        login.setVisible(true);
        
        } else {
        
        Cuenta cu = new Cuenta(this, true, idUser);
        cu.setVisible(true);
        }
    }//GEN-LAST:event_lblUsuLogMouseReleased

    private void lblLogOutMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogOutMouseReleased
        
        if ( "Crear cuenta".equals(lblLogOut.getText()) ){
        
            SignIn signin = new SignIn(this, true);
            signin.setVisible(true);
            
        } else if ( "Cerrar sesión".equals(lblLogOut.getText()) ){
            
            dispose();
            mv = new MainView();
            mv.setVisible(true);
        }
    }//GEN-LAST:event_lblLogOutMouseReleased

    private void btnFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFavActionPerformed
       
    String seccion = cBoxSec.getItemAt(cBoxSec.getSelectedIndex());
    
    if ("Todos".equals(btnFav.getText())) {   
    
            if ("Todas".equals(cBoxSec.getItemAt(cBoxSec.getSelectedIndex()))) {
                
                apr = c.CargarTablaFav(idUser);
                llenarTablaFav(apr);
                
            } else {
                apr = c.CargarTablaFavBySecc(idUser, seccion);
                llenarTablaFav(apr);
            }
            
            btnFav.setText("Secciones");
             
    } else if ("Secciones".equals(btnFav.getText())){
    
        apr = c.CargarTablaFav(idUser);
        llenarTablaFav(apr);
        
        btnFav.setText("Todos");
    }
        
    }//GEN-LAST:event_btnFavActionPerformed

private void btnAddFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFavActionPerformed
    
    int idProd = 0;
    
    if ( tblSec.getSelectedRow() >= 0 ){    

        idProd = (int) tblSec.getModel().getValueAt(tblSec.getSelectedRow(), 3);
        c.InsertFav(idProd, idUser);
        
        apr = c.CargarTablaFav(idUser);
        llenarTablaFav(apr);
        
        tblSec.clearSelection();
    
    } else if ( tblList.getSelectedRow() >= 0 ){
    
        idProd = (int) tblList.getModel().getValueAt(tblList.getSelectedRow(), 4);
        c.InsertFav(idProd, idUser);
        
        apr = c.CargarTablaFav(idUser);
        llenarTablaFav(apr);
        
        tblList.clearSelection();
    }
    
}//GEN-LAST:event_btnAddFavActionPerformed

private void btnRemoveFavActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFavActionPerformed
    int idProd = 0;

    if ( tblFav.getSelectedRow() >= 0 ){    

        idProd = (int) tblFav.getModel().getValueAt(tblFav.getSelectedRow(), 3);
        c.DeleteProdFav(idProd, idUser);
        
        apr = c.CargarTablaFav(idUser);
        llenarTablaFav(apr);
    
        tblFav.clearSelection();
    }

}//GEN-LAST:event_btnRemoveFavActionPerformed

private void btnRemoveFromListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromListActionPerformed

    int idProd, idList = 0;

    if ( tblList.getSelectedRow() >= 0 ){    

        int selectedIndex = cBoxList.getSelectedIndex();
        idProd = (int) tblList.getModel().getValueAt(tblList.getSelectedRow(), 4);
        
        c.DeleteProdList(idUser, selectedIndex, idProd);
        
        // Recarga la lista y el valor total
        cargarLista_Euros();
    
        tblList.clearSelection();
    }
}//GEN-LAST:event_btnRemoveFromListActionPerformed

private void tblFavFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblFavFocusGained

    tblSec.clearSelection();
    tblList.clearSelection();
}//GEN-LAST:event_tblFavFocusGained

private void tblSecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblSecFocusGained

    tblFav.clearSelection();
    tblList.clearSelection();
}//GEN-LAST:event_tblSecFocusGained

private void tblListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblListFocusGained

    tblSec.clearSelection();
    tblFav.clearSelection();
}//GEN-LAST:event_tblListFocusGained

private void usuIconMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usuIconMouseReleased

    if ( !"Iniciar sesión".equals(lblUsuLog.getText()) ) {
    
    Cuenta cu = new Cuenta(this, true, idUser);
    cu.setVisible(true);
    }
}//GEN-LAST:event_usuIconMouseReleased

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
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFav;
    private javax.swing.JButton btnAddList;
    private javax.swing.JButton btnFav;
    private javax.swing.JButton btnGestList;
    private javax.swing.JButton btnRemoveFav;
    private javax.swing.JButton btnRemoveFromList;
    private javax.swing.JComboBox<String> cBoxList;
    private javax.swing.JComboBox<String> cBoxSec;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCanTotal;
    private javax.swing.JLabel lblFav;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblLogOut;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblSec;
    private javax.swing.JLabel lblUsuLog;
    private javax.swing.JLabel sharedLogo;
    private javax.swing.JSpinner spnUds;
    private javax.swing.JTable tblFav;
    private javax.swing.JTable tblList;
    private javax.swing.JTable tblSec;
    private javax.swing.JLabel usuIcon;
    // End of variables declaration//GEN-END:variables
}
