/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import repository.KhachHangService;


public class KhachHang extends javax.swing.JPanel {
    
    private KhachHangService khachHangService = new KhachHangService();
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel modelspkd = new DefaultTableModel();
    int row;

    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");


    /**
     * Creates new form KhachHang
     */
    public KhachHang() {
        initComponents();
        filltable();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    void filltable() {

        model = (DefaultTableModel) tblThongtinkhachhang.getModel();
        model.setRowCount(0);
        for (model.KhachHang kh : khachHangService.getAll()) {
            Object rowData[] = new Object[6];
            rowData[0] = kh.getId();
            rowData[1] = kh.getTen();
            rowData[2] = date.format(kh.getNgaysinh());
            rowData[3] = kh.isGioitinh() ? "Nam" : "Nữ";
            rowData[4] = kh.getSdt();
            rowData[5] = kh.getEmail();
            model.addRow(rowData);
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void showDT(int index) {
        List<model.KhachHang> List = khachHangService.getAll();
        model.KhachHang kh = List.get(index);
        txtTen1.setText(kh.getTen());
        txtNgaySinh.setText(date.format(kh.getNgaysinh()));
        rdoNam.setSelected(kh.isGioitinh());
        rdoNu.setSelected(!kh.isGioitinh());
        txtSdt.setText(kh.getSdt());
        txtEmail.setText(kh.getEmail());
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void addKhachHang() {
    String tenKh = txtTen1.getText();
    String ngaySinhkh = txtNgaySinh.getText();
    String Sdt = txtSdt.getText();
    String Email = txtEmail.getText();

    // Kiểm tra các trường thông tin
    if (tenKh.isEmpty() || ngaySinhkh.isEmpty() || Sdt.isEmpty() || Email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
        return;
    }
    
    // Kiểm tra tên không chưa ký tự đặc biệt
    if (!tenKh.matches(txtTen1.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Tên không để trống và chứa ký tụ đặc biệt.");
            txtTen1.requestFocus();
           return;
        }
    
    // Kiểm tra định dạng ngày sinh
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date ngaySinh;
    try {
        ngaySinh = sdf.parse(ngaySinhkh);
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng dd/MM/yyyy.");
        txtNgaySinh.requestFocus();
        return;
    }
    
    // Kiểm tra định dạng SĐT
    if (!Sdt.matches("\\d{10,11}")) {
        JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ.");
        txtSdt.requestFocus();
        return;
    }
    
    // Kiểm tra định dạng Email
    if (!Email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        JOptionPane.showMessageDialog(this, "Email không hợp lệ.");
        txtEmail.requestFocus();
        return;
    }

    model.KhachHang kh = new model.KhachHang();
    boolean gt = rdoNam.isSelected(); // Nếu rdoNam được chọn, giới tính là true, ngược lại là false
    
    kh.setTen(tenKh);
    kh.setNgaysinh(ngaySinh);
    kh.setGioitinh(gt);
    kh.setSdt(Sdt);
    kh.setEmail(Email);
    
    boolean addResult = khachHangService.add(kh);
        if (addResult) {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công.");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng không thành công.");
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void updateKhachHang() {
    // Kiểm tra xem người dùng đã chọn dòng nào trong bảng chưa
    int selectedRow = tblThongtinkhachhang.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để cập nhật.");
        return;
    }
    
    model.KhachHang kh = new model.KhachHang();
    String tenKh = txtTen1.getText();
    String ngaySinhkh = txtNgaySinh.getText();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date ngaySinh;
    try {
        ngaySinh = sdf.parse(ngaySinhkh);
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng dd/MM/yyyy.");
        return; 
    }
    boolean gt = rdoNam.isSelected(); // Nếu rdoNam được chọn, giới tính là true, ngược lại là false
    String Sdt = txtSdt.getText();
    String Email = txtEmail.getText();

    // Kiểm tra các trường thông tin
    if (tenKh.isEmpty() || ngaySinhkh.isEmpty() || Sdt.isEmpty() || Email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
        return;
    }
    
    // Kiểm tra tên không chưa ký tự đặc biệt
    if (!tenKh.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Tên không chứa ký tụ đặc biệt.");
            return;
        }

    // Kiểm tra định dạng SĐT
    if (!Sdt.matches("\\d{10,11}")) {
        JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ.");
        return;
    }

    // Kiểm tra định dạng Email
    if (!Email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        JOptionPane.showMessageDialog(this, "Email không hợp lệ.");
        return;
    }

    String id = tblThongtinkhachhang.getValueAt(selectedRow, 0).toString();

    kh.setTen(tenKh);
    kh.setNgaysinh(ngaySinh);
    kh.setGioitinh(gt);
    kh.setSdt(Sdt);
    kh.setEmail(Email);

    // Thực hiện cập nhật và kiểm tra kết quả
    khachHangService.update(kh, Integer.valueOf(id));
        if (checkValidateKhachHang()) {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin khách hàng thành công");
            this.filltable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin khách hàng thất bại");
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    boolean checkValidateKhachHang() {
        if (txtTen1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng đang trống !");
            return false;
        } else if (txtNgaySinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày sinh khách hàng đang trống !");
            return false;
        } else if (txtSdt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "SDT khách hàng đang để trống !");
            return false;
        } else if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email khách hàng đang trống !");

            return false;
        }
        return true;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void LamMoi() {
        txtTen1.setText("");
        txtNgaySinh.setText("");
        txtEmail.setText("");
        txtSdt.setText("");
    }
    
//    private void search() {
//        if (tabs.getSelectedIndex() == 0) {
//            String timkiem = txtTimKiem.getText();
//            TableRowSorter<DefaultTableModel> sanpham = new TableRowSorter<>(model);
//            tblThongtinkhachhang.setRowSorter(sanpham);
//            sanpham.setRowFilter(javax.swing.RowFilter.regexFilter(timkiem));
//            tabs.setSelectedIndex(0);
//        } else {
//            String timkiem = txtTimKiem.getText();
//            TableRowSorter<DefaultTableModel> sanpham = new TableRowSorter<>(modelspkd);
//            tblThongtinkhachhang.setRowSorter(sanpham);
//            sanpham.setRowFilter(javax.swing.RowFilter.regexFilter(timkiem));
//            tabs.setSelectedIndex(1);
//        }
//    }
    

    
        private void search() {
        String timkiem = txtTimKiem.getText();
        TableRowSorter<DefaultTableModel> sorter;

        if (tabs.getSelectedIndex() == 0) {
            sorter = new TableRowSorter<>(model);
            tblThongtinkhachhang.setRowSorter(sorter);
            tabs.setSelectedIndex(0);
        } else {
            sorter = new TableRowSorter<>(modelspkd);
            tblThongtinkhachhang.setRowSorter(sorter);
            tabs.setSelectedIndex(1);
        }

        // Use a row filter with Unicode case folding for Vietnamese characters
        sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?iu)" + timkiem));
        }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTen1 = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnCapnhat = new javax.swing.JButton();
        btnLammoi = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongtinkhachhang = new javax.swing.JTable();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnnext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel3.setText("Tên");

        jLabel4.setText("Ngày sinh");

        txtTen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTen1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Số điện thoại");

        jLabel6.setText("Email");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapnhat.setText("Cập nhật");
        btnCapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapnhatActionPerformed(evt);
            }
        });

        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });

        tblThongtinkhachhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Họ Tên", "Ngày sinh", "Giới tính", "Số điện thoại", "Email"
            }
        ));
        tblThongtinkhachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongtinkhachhangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongtinkhachhang);

        tabs.addTab("Thông tin khách hàng", jScrollPane1);

        buttonGroup1.add(rdoNam);
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");
        rdoNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNuActionPerformed(evt);
            }
        });

        jLabel7.setText("Giới Tính");

        jPanel4.setBackground(new java.awt.Color(153, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        txtTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtTimKiem.setDisabledTextColor(new java.awt.Color(204, 255, 255));
        txtTimKiem.setName(""); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Khách Hàng");

        btnStart.setText("|<");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnnext.setText(">>");
        btnnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(335, 335, 335)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTen1)
                                .addComponent(jLabel3)
                                .addComponent(jLabel7)
                                .addComponent(jLabel4)
                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(rdoNam)
                                    .addGap(33, 33, 33)
                                    .addComponent(rdoNu)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96)))
                        .addGap(92, 92, 92)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLammoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCapnhat))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnnext, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTen1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnext)
                        .addComponent(btnLast)
                        .addComponent(btnPrev)
                        .addComponent(btnStart)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTen1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTen1ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        addKhachHang();
        filltable();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapnhatActionPerformed
        // TODO add your handling code here:
        updateKhachHang();
        filltable();
    }//GEN-LAST:event_btnCapnhatActionPerformed

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
        LamMoi();
    }//GEN-LAST:event_btnLammoiActionPerformed

    private void tblThongtinkhachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongtinkhachhangMouseClicked
        // TODO add your handling code here:
        int selectedIndex = tblThongtinkhachhang.getSelectedRow();
        if (selectedIndex >= 0) {
            this.showDT(selectedIndex);
        }
    }//GEN-LAST:event_tblThongtinkhachhangMouseClicked

    private void rdoNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNuActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        row = 0;
        if (tabs.getSelectedIndex() == 0) {
            tblThongtinkhachhang.setRowSelectionInterval(row, row);
            
        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        if (tabs.getSelectedIndex() == 0) {
            if (row > 0) {
                row--;
                tblThongtinkhachhang.setRowSelectionInterval(row, row);               
            }
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextActionPerformed
        // TODO add your handling code here:
        if (tabs.getSelectedIndex() == 0) {
            if (row < tblThongtinkhachhang.getRowCount() - 1) {
                row++;
                tblThongtinkhachhang.setRowSelectionInterval(row, row);               
            }
        }
    }//GEN-LAST:event_btnnextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        if (tabs.getSelectedIndex() == 0) {
            row = tblThongtinkhachhang.getRowCount() - 1;

            tblThongtinkhachhang.setRowSelectionInterval(row, row);
        }
    }//GEN-LAST:event_btnLastActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapnhat;
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnnext;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblThongtinkhachhang;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTen1;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
