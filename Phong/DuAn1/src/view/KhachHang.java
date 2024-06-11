/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import JDBCUtil.ConenctionProvider;
import com.google.zxing.Result;
import com.sun.jdi.connect.spi.Connection;
import java.awt.Desktop;
import java.awt.Image;
import java.beans.Statement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.TaiKhoan;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repository.KhachHangService;
import repository.TaiKhoanService;

public class KhachHang extends javax.swing.JPanel {

    private KhachHangService khachHangService = new KhachHangService();
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel modelspkd = new DefaultTableModel();
    int row;

    ArrayList<String> ls = new ArrayList<>();

    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

    long count, soTrang, trang = 1;
    private java.sql.Connection con = ConenctionProvider.getConnection();
    private TableModel tbm;

    /**
     * Creates new form KhachHang
     */
    public KhachHang() {

        initComponents();

        filltable();

        countDB();

        if (count % 5 == 0) {
            soTrang = count / 5;
        } else {
            soTrang = count / 5 + 1;
        }
//        lbTrang.setText("1");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void countDB() {
        try {
            String query = " select count(*) from KhachHang ";
            con = ConenctionProvider.getConnection();

            java.sql.Statement pstm = con.createStatement();
            ResultSet rs = pstm.executeQuery(query);
            while (rs.next()) {
                count = rs.getLong(1);
            }
            rs.close();
            pstm.close();
            con.close();
        } catch (SQLException e) {

        }
    }

    ////////////////////////////////////////////////////////////////////
    public void loadData(long trang) {
        filltable();

        try {
            String query = " select top 5 * from KhachHang where Id not in (select top " + (trang * 5 - 5) + " Id from KhachHang)";
            con = ConenctionProvider.getConnection();

            java.sql.Statement pstm = con.createStatement();
            ResultSet rs = pstm.executeQuery(query);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                int id = rs.getInt(1);
                String ten = rs.getString(2);
                Date ngaySinh = rs.getDate(3);
                String gioiTinh = rs.getString(4);
                String sdt = rs.getString(5);
                String email = rs.getString(6);

            }
            rs.close();
            pstm.close();
            con.close();
        } catch (SQLException e) {

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    void loadData1(long trang) {

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
    private KhachHang addKhachHang() {
        String tenKh = txtTen1.getText().trim();
        String ngaySinhkh = txtNgaySinh.getText();
        String Sdt = txtSdt.getText();
        String Email = txtEmail.getText();

        if (tenKh.isEmpty() || ngaySinhkh.isEmpty() || Sdt.isEmpty() || Email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date ngaySinh;
        try {
            ngaySinh = sdf.parse(ngaySinhkh);
            if (!String.valueOf(ngaySinh).matches("\\d{2}/\\d{2}/\\d{4}")) {
        JOptionPane.showMessageDialog(this,"Năm sinh phải có 4 số");
        return null;
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng dd/MM/yyyy.");
            txtNgaySinh.requestFocus();
            return null;
        }
        
        if (!tenKh.matches("[\\p{L}\\p{N}\\s]+")) {
            JOptionPane.showMessageDialog(this, "tên khách hàng không hợp lệ.");
            return null;
        }
        
        if (!Sdt.matches("^0+[0-9]{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ.");
            txtSdt.requestFocus();
            return null;
        }

        if (!Email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ.");
            txtEmail.requestFocus();
            return null;
        }

        model.KhachHang kh = new model.KhachHang();
        boolean gt = rdoNam.isSelected();

        boolean checkEmail = true;
        for (int z = 0; z < khachHangService.getAllEmailUpdate(khachHangService.getAll().get(row).getEmail()).size(); z++) {
            if (Email.equals(khachHangService.getAllEmailUpdate(khachHangService.getAll().get(row).getEmail()).get(z).getEmail())) {
                checkEmail = false;
                break;
            }
        }
        if (!checkEmail) {
            JOptionPane.showMessageDialog(this, "Email đã tồn tại");
            return null;
        }

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
        return null;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private KhachHang updateKhachHang() {

        int selectedRow = tblThongtinkhachhang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để cập nhật.");
            return null;
        }

        model.KhachHang kh = new model.KhachHang();
        String tenKh = txtTen1.getText().trim();
        String ngaySinhkh = txtNgaySinh.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date ngaySinh;
        try {
            ngaySinh = sdf.parse(ngaySinhkh);
            if (!String.valueOf(ngaySinh).matches("\\d{2}/\\d{2}/\\d{4}")) {
        JOptionPane.showMessageDialog(this,"Năm sinh phải có 4 số");
        return null;
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng dd/MM/yyyy.");
            return null;
        }

        boolean gt = rdoNam.isSelected();

        String Sdt = txtSdt.getText();
        String Email = txtEmail.getText();

        if (tenKh.isEmpty() || ngaySinhkh.isEmpty() || Sdt.isEmpty() || Email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return null;
        }

        if (!tenKh.matches("[\\p{L}\\p{N}\\s]+")) {
            JOptionPane.showMessageDialog(this, "tên khách hàng không hợp lệ.");
            return null;
        }

        if (!Sdt.matches("^0+[0-9]{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ.");
            return null;
        }

        if (!Email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ.");
            return null;
        }

        String id = tblThongtinkhachhang.getValueAt(selectedRow, 0).toString();

        kh.setTen(tenKh);
        kh.setNgaysinh(ngaySinh);
        kh.setGioitinh(gt);
        kh.setSdt(Sdt);
        kh.setEmail(Email);

        khachHangService.update(kh, Integer.valueOf(id));
        if (checkValidateKhachHang()) {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin khách hàng thành công");
            this.filltable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin khách hàng thất bại");
        }
        return null;
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
        } else {
            return true;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void LamMoi() {
        txtTen1.setText("");
        txtNgaySinh.setText("");
        txtEmail.setText("");
        txtSdt.setText("");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void showData() {
        List<model.KhachHang> lstk = new ArrayList<>();
        if (txtTimKiem.getText().trim().length() == 0) {
            lstk = khachHangService.getAll();
        } else {
            lstk = khachHangService.search(txtTimKiem.getText().trim());
        }
        DefaultTableModel dtm = (DefaultTableModel) tblThongtinkhachhang.getModel();
        dtm.setRowCount(0);
        for (model.KhachHang u : lstk) {
            Object[] rowData = {
                u.getId(),
                u.getTen(),
                fomatDate(u.getNgaysinh()),
                u.isGioitinh() == true ? "Nam" : "Nữ",
                u.getSdt(),
                u.getEmail(),};
            dtm.addRow(rowData);
        }
    }

    private String fomatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String newDate = sdf.format(date);
        return newDate;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void fillData(int row) {
        ArrayList<model.KhachHang> lstk = new ArrayList<>();
        if (txtTimKiem.getText().trim().length() == 0) {
            lstk = khachHangService.getAll();
        } else {
            lstk = khachHangService.search(txtTimKiem.getText().trim());
        }
        model.KhachHang u = lstk.get(row);
        txtTen1.setText(u.getTen());
        txtNgaySinh.setText(fomatDate((Date) u.getNgaysinh()));
        txtSdt.setText(u.getSdt());
        txtEmail.setText(u.getEmail());
        if (u.isGioitinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
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
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongtinkhachhang = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tên khách hàng");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Ngày sinh");

        txtTen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTen1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Số điện thoại");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Email");

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapnhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapnhat.setText("Cập nhật");
        btnCapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapnhatActionPerformed(evt);
            }
        });

        btnLammoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });

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

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Giới Tính");

        jPanel4.setBackground(new java.awt.Color(153, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        txtTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtTimKiem.setDisabledTextColor(new java.awt.Color(204, 255, 255));
        txtTimKiem.setName(""); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
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
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThongtinkhachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongtinkhachhangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThongtinkhachhang);
        if (tblThongtinkhachhang.getColumnModel().getColumnCount() > 0) {
            tblThongtinkhachhang.getColumnModel().getColumn(0).setResizable(false);
            tblThongtinkhachhang.getColumnModel().getColumn(0).setPreferredWidth(2);
            tblThongtinkhachhang.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblThongtinkhachhang.getColumnModel().getColumn(2).setResizable(false);
            tblThongtinkhachhang.getColumnModel().getColumn(2).setPreferredWidth(6);
            tblThongtinkhachhang.getColumnModel().getColumn(3).setResizable(false);
            tblThongtinkhachhang.getColumnModel().getColumn(3).setPreferredWidth(3);
            tblThongtinkhachhang.getColumnModel().getColumn(4).setResizable(false);
            tblThongtinkhachhang.getColumnModel().getColumn(4).setPreferredWidth(5);
        }

        jTabbedPane1.addTab("Danh sách khách hàng", jScrollPane1);

        jButton1.setText("Xuất Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(61, 61, 61)
                                .addComponent(rdoNu))
                            .addComponent(jLabel4)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(txtTen1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(227, 227, 227)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtEmail)
                                        .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnLammoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCapnhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(38, 38, 38))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTen1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                                .addComponent(btnLammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblThongtinkhachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongtinkhachhangMouseClicked
        // TODO add your handling code here:
        int selectedIndex = tblThongtinkhachhang.getSelectedRow();
        if (selectedIndex >= 0) {
            this.fillData(selectedIndex);
        }
    }//GEN-LAST:event_tblThongtinkhachhangMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        //        search();
        showData();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        // TODO add your handling code here:
        showData();

    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void rdoNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNuActionPerformed

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        // TODO add your handling code here:
        LamMoi();
    }//GEN-LAST:event_btnLammoiActionPerformed

    private void btnCapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapnhatActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa người này không?");
        if(confirm == JOptionPane.YES_OPTION){
        updateKhachHang();
        filltable();
        LamMoi();
        txtTimKiem.setText("");   
        }else{
            JOptionPane.showMessageDialog(this, "Sửa thất bại");
        }
        
    }//GEN-LAST:event_btnCapnhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        addKhachHang();
        filltable();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtTen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTen1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTen1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ArrayList<model.KhachHang> lstk = new ArrayList<>();
        if (txtTimKiem.getText().trim().length() == 0) {
            lstk = khachHangService.getAll();
        } else {
            lstk = khachHangService.search(txtTimKiem.getText().trim());
        }
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách khách hàng");
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFRow row = null;
            XSSFCell cell = null;
            row = sheet.createRow(1);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");
            cell.setCellStyle(style);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Họ và tên");
            cell.setCellStyle(style);

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Ngày sinh");
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Giới tính");
            cell.setCellStyle(style);
            
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Số điện thoại");
            cell.setCellStyle(style);

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Email");
            cell.setCellStyle(style);

            for (int i = 0; i < lstk.size(); i++) {
                model.KhachHang tk = lstk.get(i);
                row = sheet.createRow(2 + i);

                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(i + 1);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(tk.getTen());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(tk.getNgaysinh());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(tk.isGioitinh()? "Nam" : "Nữ");

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(tk.getSdt());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(tk.getEmail());
            }
            String f = "C:\\Users\\duong\\Downloads\\DanhSachKhachHang\\danhsachkhachhang";
            String filePath = f + ".xlsx";
            int fileIndex = 1;
            while (new File(filePath).exists()) {
                filePath = f + "(" + fileIndex++ + ").xlsx";
            }
            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                workbook.write(fos);
                JOptionPane.showMessageDialog(this, "In thành công");
                File file = new File(filePath);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapnhat;
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblThongtinkhachhang;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTen1;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
