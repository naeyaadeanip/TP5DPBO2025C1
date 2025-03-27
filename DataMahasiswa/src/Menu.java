/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Import
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Menu extends javax.swing.JFrame {

    // Properties
    private DefaultTableModel dtm;
    private Boolean isUpdate = false;
    private int selectedID= -1;
    private ArrayList<Mahasiswa> listMhs;
    private Database database;

    /**
     * Creates new form Menu
     */
    public Menu() {
        // Constructor
        initComponents();

        listMhs = new ArrayList<>();

        //buat objek database
        database = new Database();

        // Dummy
        // ...
        listMhs.add(new Mahasiswa("2304017", "Naeya Adeani", "B", "Bekasi", "Perempuan"));
        listMhs.add(new Mahasiswa("2306205", "Klara Ollivviera", "A", "Bandung", "Perempuan"));
        listMhs.add(new Mahasiswa("2306827", "Jihan Aqilah", "B", "Makasar", "Perempuan"));
        listMhs.add(new Mahasiswa("2308224", "Datuk Daneswara", "A", "Medan", "Laki-Laki"));
        listMhs.add(new Mahasiswa("2308882", "Naren Ridha", "B", "Tanggerang", "Laki-Laki"));
        // Set Table
        // ...
        mahasiswaTable.setModel(setTable());

        // Hide Delete button
        // ...
        deleteButton.setVisible(false);
    }

    public final DefaultTableModel setTable() {
        Object[] column = {"NIM", "Nama", "Nilai", "Alamat", "Jenis Kelamin"};
        DefaultTableModel dataTabel = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");

            // Loop untuk membaca semua data
            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getString("nim");
                row[1] = resultSet.getString("nama");
                row[2] = resultSet.getString("nilai");
                row[3] = resultSet.getString("alamat");
                row[4] = resultSet.getString("jk");
                dataTabel.addRow(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataTabel;
    }

    public boolean isNimUnique() {
        try {
            // cek apakah NIM sudah ada di database
            String nim = nimField.getText();
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");

            if(resultSet.next()) {
                JOptionPane.showMessageDialog(
                        null,
                        "NIM " + nim + " sudah terdaftar!",
                        "Error: NIM Duplikat",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertData() {
        // Get Data from Form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String nilai = nilaiField.getText();
        String alamat = alamatField.getText();

        // Pastikan jk diisi sebelum query
        String jk = jkL.isSelected() ? jkL.getText() : (jkP.isSelected() ? jkP.getText() : null);

        // Validasi input
        if (nim.isEmpty() || nama.isEmpty() || nilai.isEmpty() || alamat.isEmpty() || jk == null) {
            JOptionPane.showMessageDialog(null, "Harap isi semua data!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cek apakah NIM sudah ada di database
        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa WHERE nim = '" + nim + "'");
            if (resultSet.next()) {  // Jika ada hasil, berarti NIM sudah ada
                JOptionPane.showMessageDialog(
                        null,
                        "NIM " + nim + " sudah terdaftar!",
                        "Error: NIM Duplikat",
                        JOptionPane.ERROR_MESSAGE
                );
                return;  // Hentikan eksekusi insert
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam pengecekan NIM!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Query SQL untuk insert data
        String sql = "INSERT INTO mahasiswa (nim, nama, nilai, alamat, jk) VALUES ('" + nim + "', '" + nama + "', '" + nilai + "', '" + alamat + "', '" + jk + "');";
        database.insertUpdateDeleteQuery(sql);

        // Tambahkan ke listMhs
        listMhs.add(new Mahasiswa(nim, nama, nilai, alamat, jk));

        System.out.println("Insert Success!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");

        // Reset Form
        resetForm();

        // Update Table
        mahasiswaTable.setModel(setTable());
    }

    public void updateData() {
        int selectedRow = mahasiswaTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Ambil data dari form
            String nim = nimField.getText();
            String nama = namaField.getText();
            String nilai = nilaiField.getText();
            String alamat = alamatField.getText();
            String jk = jkL.isSelected() ? jkL.getText() : (jkP.isSelected() ? jkP.getText() : null);

            // Pastikan semua field diisi
            if (nim.isEmpty() || nama.isEmpty() || nilai.isEmpty() || alamat.isEmpty() || jk == null) {
                JOptionPane.showMessageDialog(null, "Harap isi semua data!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Query update berdasarkan NIM, bukan ID
            String sql = "UPDATE mahasiswa SET nama = '" + nama +
                    "', nilai = '" + nilai + "', alamat = '" + alamat +
                    "', jk = '" + jk + "' WHERE nim = '" + nim + "'";

            // Eksekusi query
            database.insertUpdateDeleteQuery(sql);

            // Update listMhs
            for (Mahasiswa mhs : listMhs) {
                if (mhs.getNim().equals(nim)) {
                    mhs.setNama(nama);
                    mhs.setNilai(nilai);
                    mhs.setAlamat(alamat);
                    mhs.setJk(jk);
                    break;
                }
            }

            // Update tabel
            mahasiswaTable.setModel(setTable());

            // Reset form
            resetForm();

            // Notifikasi sukses
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin diubah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        int selectedRow = mahasiswaTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "Hapus Data?", "Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String nim = mahasiswaTable.getValueAt(selectedRow, 0).toString();
                String sql = "DELETE FROM mahasiswa WHERE nim = '" + nim + "'";
                database.insertUpdateDeleteQuery(sql);

                listMhs.clear(); // Kosongkan list agar tidak menyimpan data yang sudah dihapus
                mahasiswaTable.setModel(setTable()); // Refresh tabel
                resetForm();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetForm() {
        // Set All Value Form to Empty
        // ...
        namaField.setText(null);
        nimField.setText(null);
        nilaiField.setText(null);
        alamatField.setText(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jenisKelaminButton = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        addUpdateButton = new javax.swing.JButton();
        nimField = new javax.swing.JTextField();
        nimLabel = new javax.swing.JLabel();
        namaLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        namaField = new javax.swing.JTextField();
        alamatField = new javax.swing.JTextField();
        nilaiLabel = new javax.swing.JLabel();
        nilaiField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        mahasiswaTable = new javax.swing.JTable();
        deleteButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jkL = new javax.swing.JRadioButton();
        jkP = new javax.swing.JRadioButton();
        nilaiLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(173, 216, 230));

        titleLabel.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        titleLabel.setText("Daftar Mahasiswa");

        addUpdateButton.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        addUpdateButton.setText("Add");
        addUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUpdateButtonActionPerformed(evt);
            }
        });

        nimField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nimFieldActionPerformed(evt);
            }
        });

        nimLabel.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        nimLabel.setForeground(new java.awt.Color(51, 0, 51));
        nimLabel.setText("NIM");

        namaLabel.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        namaLabel.setText("Nama");

        namaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaFieldActionPerformed(evt);
            }
        });

        nilaiLabel.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        nilaiLabel.setText("Nilai");

        nilaiField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilaiFieldActionPerformed(evt);
            }
        });

        alamatLabel.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        alamatLabel.setText("Alamat");

        alamatField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamatFieldActionPerformed(evt);
            }
        });

        mahasiswaTable.setAutoCreateRowSorter(true);
        mahasiswaTable.setBackground(new java.awt.Color(240, 240, 240));
        mahasiswaTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mahasiswaTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        mahasiswaTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mahasiswaTable.setGridColor(new java.awt.Color(255, 204, 204));
        mahasiswaTable.setUpdateSelectionOnSort(false);
        mahasiswaTable.setVerifyInputWhenFocusTarget(false);
        mahasiswaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mahasiswaTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(mahasiswaTable);

        deleteButton.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jenisKelaminButton.add(jkL);
        jkL.setSelected(true);
        jkL.setText("Laki-Laki");
        jkL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkLActionPerformed(evt);
            }
        });

        jenisKelaminButton.add(jkP);
        jkP.setText("Perempuan");

        nilaiLabel1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        nilaiLabel1.setText("Jenis Kelamin");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(nimLabel)
                                                        .addComponent(namaLabel)
                                                        .addComponent(nilaiLabel)
                                                        .addComponent(alamatLabel)
                                                        .addComponent(nilaiLabel1)) // nilaiLabel1 untuk jenis kelamin
                                                .addGap(20, 20, 20)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(nimField)
                                                        .addComponent(namaField)
                                                        .addComponent(nilaiField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(alamatField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addComponent(jkL)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jkP)))
                                                .addGap(20, 20, 20)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(addUpdateButton)
                                                        .addComponent(cancelButton)
                                                        .addComponent(deleteButton))))
                                .addContainerGap(30, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, mainPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(titleLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(titleLabel)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nimLabel)
                                        .addComponent(nimField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addUpdateButton))
                                .addGap(10, 10, 10)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(namaLabel)
                                        .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cancelButton))
                                .addGap(10, 10, 10)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nilaiLabel)
                                        .addComponent(nilaiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(deleteButton))
                                .addGap(10, 10, 10)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(alamatLabel)
                                        .addComponent(alamatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nilaiLabel1) // Label untuk jenis kelamin
                                        .addComponent(jkL)
                                        .addComponent(jkP))
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nimFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nimFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nimFieldActionPerformed

    private void namaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaFieldActionPerformed

    private void nilaiFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilaiFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilaiFieldActionPerformed

    private void alamatFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilaiFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamatFieldActionPerformed

    private void addUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUpdateButtonActionPerformed
        // TODO add your handling code here:
        // If Add (data not clicked)
        // ...
        if(isUpdate==false) insertData();
        else{
            updateData();
            addUpdateButton.setText("Add");
            deleteButton.setVisible(false);
            this.isUpdate=false;
        }
    }//GEN-LAST:event_addUpdateButtonActionPerformed

    private void mahasiswaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mahasiswaTableMouseClicked
        // TODO add your handling code here:
        // If data clicked
        // ...
        this.isUpdate=true;

        //Get Selected Data
        // ...
        int row=mahasiswaTable.getSelectedRow();
        String selectedNim=(mahasiswaTable.getModel().getValueAt(row,0).toString());
        String selectedNama=(mahasiswaTable.getModel().getValueAt(row,1).toString());
        String selectedNilai=(mahasiswaTable.getModel().getValueAt(row,2).toString());
        String selectedAlamat=(mahasiswaTable.getModel().getValueAt(row,3).toString());

        // Search Data
        // ...
        for(int i=0;i<listMhs.size();++i){
            if(selectedNim.equals(listMhs.get(i).getNim())){
                selectedID=i;
                break;
            }
        }

        // Set Form Value
        // ...
        nimField.setText(selectedNim);
        namaField.setText(selectedNama);
        nilaiField.setText(selectedNilai);
        alamatField.setText(selectedAlamat);

        addUpdateButton.setText("Update");
        deleteButton.setVisible(true);
    }//GEN-LAST:event_mahasiswaTableMouseClicked

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        // ...
        if(isUpdate==true){
            deleteData();
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        // Cancel Input Form
        // ...
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        this.isUpdate=false;
        // Reset Form
        resetForm();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jkLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jkLActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUpdateButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.ButtonGroup jenisKelaminButton;
    private javax.swing.JTextField namaField;
    private javax.swing.JTextField nilaiField;
    private javax.swing.JTextField nimField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton jkL;
    private javax.swing.JRadioButton jkP;
    private javax.swing.JLabel namaLabel;
    private javax.swing.JLabel nilaiLabel;
    private javax.swing.JLabel jenisKelaminLabel;
    private javax.swing.JLabel nilaiLabel1;
    private javax.swing.JLabel nimLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTable mahasiswaTable;
    private JTextField alamatField;
    private JLabel alamatLabel;
    // End of variables declaration//GEN-END:variables
}