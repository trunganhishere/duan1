/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import Interface.HoaDonChiTietInterface;
import Interface.KhachHangInterface;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.HoaDon;
import model.SanPhamChiTiet;
import repository.HoaDonCTService;
import repository.HoaDonService;
import repository.KhachHangService;
import repository.SanPhamCTService;

public class HoaDonChiTiet extends javax.swing.JPanel {

    HoaDonService hoaDonService = new HoaDonService();
    List<model.HoaDonChiTiet> listHDCT;
    private SanPhamCTService chiTietSpService = new SanPhamCTService();
    private KhachHangInterface khachHangService = new KhachHangService();
    private HoaDonChiTietInterface hoaDonChiTietService = new HoaDonCTService();

    /**
     * Creates new form HoaDonChiTiet
     */
    public HoaDonChiTiet() {
        initComponents();
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) cbbTinhTrang.getModel();
        List<String> ls = new ArrayList<>();
        ls.add("All");
        ls.add("Chưa thanh toán");
        ls.add("Đã thanh toán");
        dcm.addAll(ls);
        dcm.setSelectedItem("All");
        showData(hoaDonService.getAll());
        reFreshListHDCT();
        DefaultTableModel giohangModel = (DefaultTableModel) tblHoaDonChiTiet.getModel();
    }


    private void loadHoaDonChiTietByIDHD(int idHD) {
        List<SanPhamChiTiet> listSPCTLoad = new ArrayList<>();
        List<Integer> listSoLuong = new ArrayList<>();
        for (model.HoaDonChiTiet hoaDonChiTiet : listHDCT) {
            if (hoaDonChiTiet.getHaoDon().getId() == idHD) {
                listSPCTLoad.add(getSPCTbyIDSP(hoaDonChiTiet.getSanPham().getId()));
                listSoLuong.add(hoaDonChiTiet.getSoluong());
            }
        }

        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0);

        for (int i = 0; i < listSPCTLoad.size(); i++) {
            SanPhamChiTiet sanPhamChiTiet = listSPCTLoad.get(i);
            int soLuong = listSoLuong.get(i); // Retrieve the corresponding quantity
            model.addRow(new Object[]{
                sanPhamChiTiet.getId(),
                sanPhamChiTiet.getTenSp().getTen(),
                sanPhamChiTiet.getChatLieu(),
                sanPhamChiTiet.getMauSac(),
                sanPhamChiTiet.getKichCo(),
                sanPhamChiTiet.getGiaBan(),
                soLuong, // Display the quantity in the table column
                sanPhamChiTiet.getGiaBan() * soLuong
            });

        }

    }

    private SanPhamChiTiet getSPCTbyIDSP(String id) {
        List<SanPhamChiTiet> listSPCT = chiTietSpService.getAll();
        for (SanPhamChiTiet sanPhamChiTiet : listSPCT) {
            if (sanPhamChiTiet.getId().equalsIgnoreCase(id)) {
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setId(sanPhamChiTiet.getId());
                spct.setTenSp(sanPhamChiTiet.getTenSp());
                spct.setSoLuongTon(sanPhamChiTiet.getSoLuongTon());
                spct.setGiaBan(sanPhamChiTiet.getGiaBan());
                spct.setGiaNhap(sanPhamChiTiet.getGiaNhap());
                spct.setNhaSx(sanPhamChiTiet.getNhaSx());
                spct.setChatLieu(sanPhamChiTiet.getChatLieu());
                spct.setKhuyenMai(sanPhamChiTiet.getKhuyenMai());
                spct.setMauSac(sanPhamChiTiet.getMauSac());
                spct.setThuongHieu(sanPhamChiTiet.getThuongHieu());
                spct.setMoTa(sanPhamChiTiet.getMoTa());
                spct.setKichCo(sanPhamChiTiet.getKichCo());
                return spct;
            }

        }
        return null;
    }

    private void showData(List<HoaDon> ls) {
        DefaultTableModel dtm = (DefaultTableModel) tblHoaDon.getModel();
        dtm.setRowCount(0);
        for (HoaDon h : ls) {
            Object[] rowData = {
                h.getId(),
                getKHbyID(h.getIDKhachHang()),
                h.getMa(),
                h.getTinhTrang() == 0 ? "Chưa thanh toán" : "Đã thanh toán",
                h.getNgayThanhToan(),
                h.getTongTien(),
                h.getGhichu()
            };
            dtm.addRow(rowData);
        }
    }

    private String getKHbyID(int id) {
        List<model.KhachHang> list = khachHangService.getAll();
        for (model.KhachHang khachHang : list) {
            if (khachHang.getId() == id) {
                return khachHang.getTen();

            }
        }
        return "";
    }

    private void reFreshListHDCT() {
        listHDCT = hoaDonChiTietService.getAllHDCT();

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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbbTinhTrang = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();

        jPanel1.setMaximumSize(new java.awt.Dimension(940, 580));
        jPanel1.setMinimumSize(new java.awt.Dimension(940, 580));

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Khach Hang", "Mã hóa đơn", "Tình trạng", "Ngày thanh toán", "Tổng tiền", "Ghi chú"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 161, 928, 180));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Hóa Đơn Chi Tiết");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 370, 170, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Hóa Đơn");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, 140, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Customer Name");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Status");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        cbbTinhTrang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbTinhTrangItemStateChanged(evt);
            }
        });
        cbbTinhTrang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbbTinhTrangMouseClicked(evt);
            }
        });
        cbbTinhTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTinhTrangActionPerformed(evt);
            }
        });
        jPanel2.add(cbbTinhTrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 170, 35));
        jPanel2.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 170, 30));

        btnSearch.setBackground(new java.awt.Color(125, 224, 237));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_24px.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel2.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 100, -1));

        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IDSP", "Tên SP", "Chất Liệu", "Màu Sắc", "Kích Cỡ", "Đơn Giá", "Số Lượng", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHoaDonChiTiet);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 920, 190));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 934, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 598, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int idHD = Integer.parseInt(tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0) + "");
        loadHoaDonChiTietByIDHD(idHD);
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void cbbTinhTrangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbTinhTrangItemStateChanged
        String trangThai = cbbTinhTrang.getSelectedItem().toString();
        List<HoaDon> list = hoaDonService.getAll();
        if (trangThai.equalsIgnoreCase("All")) {
            showData(list);
        } else if (trangThai.equalsIgnoreCase("Chưa thanh toán")) {
            List<HoaDon> list2 = new ArrayList<>();
            for (HoaDon hoaDon : list) {
                if (hoaDon.getTinhTrang() == 0) {
                    list2.add(hoaDon);
                }
            }
            showData(list2);
        } else if (trangThai.equalsIgnoreCase("Đã thanh toán")) {
            List<HoaDon> list2 = new ArrayList<>();
            for (HoaDon hoaDon : list) {
                if (hoaDon.getTinhTrang() == 1) {
                    list2.add(hoaDon);
                }
            }
            showData(list2);
        }
    }//GEN-LAST:event_cbbTinhTrangItemStateChanged

    private void cbbTinhTrangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbTinhTrangMouseClicked

    }//GEN-LAST:event_cbbTinhTrangMouseClicked

    private void cbbTinhTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTinhTrangActionPerformed

    }//GEN-LAST:event_cbbTinhTrangActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String nameSearch = txtSearch.getText();
        List<HoaDon> listHD = hoaDonService.getAll();
        DefaultTableModel dtm = (DefaultTableModel) tblHoaDon.getModel();
        dtm.setRowCount(0);
        for (HoaDon hoaDon : listHD) {
            if (getKHbyID(hoaDon.getIDKhachHang()).contains(nameSearch)) {
                Object[] rowData = {
                hoaDon.getId(),
                getKHbyID(hoaDon.getIDKhachHang()),
                hoaDon.getMa(),
                hoaDon.getTinhTrang() == 0 ? "Chưa thanh toán" : "Đã thanh toán",
                hoaDon.getNgayThanhToan(),
                hoaDon.getTongTien(),
                hoaDon.getGhichu()
            };
            dtm.addRow(rowData);
       
                
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbbTinhTrang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
