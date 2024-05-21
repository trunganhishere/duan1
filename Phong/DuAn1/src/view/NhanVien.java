/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.TaiKhoan;
import repository.TaiKhoanService;


public class NhanVien extends javax.swing.JPanel {
     ArrayList<String> ls = new ArrayList<>();
     TaiKhoanService us = new TaiKhoanService();
    /**
     * Creates new form NhanVien
     */
    public NhanVien() {
        initComponents();
        showData();
        ls.add("Nhân Viên");
        ls.add("Quản Lý");
        DefaultComboBoxModel dcb = (DefaultComboBoxModel) cbbChucVu.getModel();
        dcb.removeAllElements();
        dcb.addAll(ls);
        dcb.setSelectedItem("Nhân Viên");
    }
    
    private String fomatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String newDate = sdf.format(date);
        return newDate;
    }
    
    private void showData() {
        ArrayList<TaiKhoan> ls = new ArrayList<>();
        if(txtSearch.getText().trim().length() == 0){
            ls = us.getAllTaiKhoan();
        }else{
            ls = us.search(txtSearch.getText().trim());
        }
        DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
        dtm.setRowCount(0);
        for (TaiKhoan u : ls) {
            Object[] rowData = {
                u.getTen(),
                fomatDate(u.getNgaySinh()),
                u.getGioiTinh() == true ? "Nam" : "Nữ",
                u.getSdt(),
                u.getTaiKhoan(),
                u.getIdCV() == 1 ? "Quản Lý" : "Nhân Viên",
                u.getEmail(),
                u.getTrangThai() == false ? "Không làm việc":"Làm Việc",
                u.getNgayTao(),
                u.getNgaySua()
            };
            dtm.addRow(rowData);
        }
    }
    
    private void fillData(int row) {
        ArrayList<TaiKhoan> ls = new ArrayList<>();
        if(txtSearch.getText().trim().length() == 0){
            ls = us.getAllTaiKhoan();
        }else{
            ls = us.search(txtSearch.getText().trim());
        }
        TaiKhoan u = ls.get(row);
        txtTen.setText(u.getTen());
        txtNgaySinh.setText(fomatDate(u.getNgaySinh()));
        TxtSDT.setText(u.getSdt());
        txtEmail.setText(u.getEmail());
        txtMatKhau.setText(u.getMatKhau());
        txtTaiKhoan.setText(u.getTaiKhoan());
        if (u.getGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }

        if (u.getTrangThai()) {
            ckbTrangThai.setSelected(true);
        } else {
            ckbTrangThai.setSelected(false);
        }
        if (u.getIdCV() == 1) {
            cbbChucVu.setSelectedItem("Quản Lý");
        } else {
            cbbChucVu.setSelectedItem("Nhân Viên");
        }
    }

    private TaiKhoan getDataAdd() {
        String ten = txtTen.getText();
        String ngaySinhString = txtNgaySinh.getText();
        String sdt = TxtSDT.getText();
        String taiKhoan = txtTaiKhoan.getText();
        String matKhau = txtMatKhau.getText();
        String email = txtEmail.getText();

        if (ten.isEmpty() || sdt.isEmpty() || taiKhoan.isEmpty() || matKhau.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đủ trường");
            return null;
        }

        if (!sdt.matches("^0+[0-9]{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ");
            return null;
        }

        if (ngaySinhString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày sinh");
            return null;
        }

        Date ngaySinh;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            ngaySinh = sdf.parse(ngaySinhString);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngay Sinh chưa đúng định dạng dd/MM/yyyy !");
            return null;
        }

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(ngaySinh.getTime());

        boolean gioiTinh = rdoNam.isSelected();
        boolean trangThai = ckbTrangThai.isSelected();
        String chucVuString = cbbChucVu.getSelectedItem().toString();
        int chucVu = chucVuString.equals("Quản Lý") ? 1 : 2;

        TaiKhoan u = new TaiKhoan();
        u.setTen(ten);
        u.setNgaySinh((java.sql.Date) sqlDate);
        u.setSdt(sdt);
        u.setTaiKhoan(taiKhoan);
        u.setMatKhau(matKhau);
        u.setEmail(email);
        u.setGioiTinh(gioiTinh);
        u.setIdCV(chucVu);
        u.setTrangThai(trangThai);

        boolean checkAccount = true;
        for (int i = 0; i < us.getAllTaiKhoan().size(); i++) {
            if (us.getAllTaiKhoan().get(i).getTaiKhoan().equals(taiKhoan)) {
                checkAccount = false;
                break;
            }
        }

        if (!checkAccount) {
            JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại");
            return null;
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            return u;
        }
    }

    private TaiKhoan getDataUpdate() {
    String ten = txtTen.getText();
    String ngaySinhString = txtNgaySinh.getText();
    Date ngaySinh = null;
    
    // Parse ngaySinhString to Date
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ngaySinh = sdf.parse(ngaySinhString);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng dd/MM/yyyy !");
        return null;
    }
    
    java.sql.Date sqlDate = new java.sql.Date(ngaySinh.getTime());
    
    String sdt = TxtSDT.getText();
    String taiKhoan = txtTaiKhoan.getText();
    String matKhau = txtMatKhau.getText();
    String email = txtEmail.getText();
    
    boolean gioiTinh = rdoNam.isSelected();
    
    boolean trangThai = ckbTrangThai.isSelected();
    
    String chucVuString = cbbChucVu.getSelectedItem().toString();
    int chucVu = chucVuString.equals("Quản Lý") ? 1 : 2;
    
    TaiKhoan u = new TaiKhoan();
    u.setTen(ten);
    u.setNgaySinh(sqlDate);
    u.setSdt(sdt);
    u.setTaiKhoan(taiKhoan);
    u.setMatKhau(matKhau);
    u.setEmail(email);
    u.setGioiTinh(gioiTinh);
    u.setIdCV(chucVu);
    u.setTrangThai(trangThai);
    
    // Validation
    if (ten.isEmpty() || sdt.isEmpty() || taiKhoan.isEmpty() || matKhau.isEmpty() || email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ trường");
        return null;
    } else if (!sdt.matches("^0+[0-9]{9}$")) {
        JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ");
        return null;
    } else {
        JOptionPane.showMessageDialog(this, "Sửa thành công");
        return u;
    }
}
  

    public void clear() {
        TxtSDT.setText("");
        txtEmail.setText("");
        txtMatKhau.setText("");
        txtNgaySinh.setText("");
        txtTaiKhoan.setText("");
        txtSearch.setText("");
        txtTen.setText("");
        cbbChucVu.setSelectedIndex(0);
        rdoNam.setSelected(true);
        ckbTrangThai.setSelected(false);
        tbl.clearSelection();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        txtTen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtSDT = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTaiKhoan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbbChucVu = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        ckbTrangThai = new javax.swing.JCheckBox();
        txtMatKhau = new javax.swing.JPasswordField();
        btnSua1 = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên", "Ngày Sinh", "Giới Tính", "SĐT", "Tài Khoản", "Chức vụ", "Email", "Trạng Thái", "Ngày Tạo", "Ngày Sửa"
            }
        ));
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);

        jLabel2.setText("Tên");

        jLabel3.setText("Ngày Sinh");

        jLabel4.setText("SĐT");

        jLabel5.setText("Tài Khoản");

        jLabel6.setText("Mật Khẩu");

        jLabel7.setText("Email");

        jLabel8.setText("Chức Vụ");

        cbbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Giới Tính");

        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        rdoNu.setText("Nữ");
        rdoNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNuActionPerformed(evt);
            }
        });

        jLabel10.setText("Trạng thái");

        ckbTrangThai.setText("Làm Việc");
        ckbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckbTrangThaiActionPerformed(evt);
            }
        });

        btnSua1.setText("Clear form");
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel11.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addComponent(TxtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtMatKhau)
                                                .addComponent(txtTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addComponent(jLabel6)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnThem)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnSua)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSua1)))))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(129, 129, 129)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel8)
                                            .addComponent(cbbChucVu, 0, 311, Short.MAX_VALUE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(rdoNam)
                                                .addGap(36, 36, 36)
                                                .addComponent(rdoNu))
                                            .addComponent(jLabel10)
                                            .addComponent(ckbTrangThai)
                                            .addComponent(jLabel7)
                                            .addComponent(txtEmail)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(137, 137, 137)
                                        .addComponent(jLabel9))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addGap(27, 27, 27)
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(ckbTrangThai)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnSua1)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        us.them(getDataAdd());
        showData();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int row = tbl.getSelectedRow();
        if(row<0){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người muốn sửa");
        }else{
            ArrayList<TaiKhoan> ls = new ArrayList<>();
        if(txtSearch.getText().trim().length() == 0){
            ls = us.getAllTaiKhoan();
        }else{
            ls = us.search(txtSearch.getText().trim());
        }
            Integer id = ls.get(row).getId();
        us.sua(getDataUpdate(), id);
        showData();
        }
        
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        int row = tbl.getSelectedRow();
        fillData(row);
    }//GEN-LAST:event_tblMouseClicked

    private void rdoNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNuActionPerformed

    private void ckbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckbTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckbTrangThaiActionPerformed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        clear();
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        showData();
    }//GEN-LAST:event_txtSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField TxtSDT;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua1;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cbbChucVu;
    private javax.swing.JCheckBox ckbTrangThai;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTaiKhoan;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
