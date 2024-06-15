/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import model.HoaDonChiTiet;
import Interface.hoaDonInterface;
import Interface.HoaDonChiTietInterface;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HoaDon;
import model.SanPhamChiTiet;
import model.ThuongHieu;
import repository.DangNhapService;
import repository.HoaDonCTService;
import repository.HoaDonService;
import repository.KhachHangService;
import repository.KhuyenMaiService;
import repository.SanPhamCTService;
import repository.TaiKhoanService;
import repository.ThuongHieuService;

public class BanHang extends javax.swing.JPanel {

    private hoaDonInterface hoadonService = new HoaDonService();
    private KhachHangService khachHangService = new KhachHangService();
    private SanPhamCTService chiTietSpService = new SanPhamCTService();
    private HoaDonChiTietInterface hoaDonCTService = new HoaDonCTService();
    private ThuongHieuService thuongHieuService = new ThuongHieuService();
    private KhuyenMaiService khuyenMaiService = new KhuyenMaiService();
    List<HoaDonChiTiet> listHDCT = hoaDonCTService.getAllHDCT();
    private List<model.KhuyenMai> listKhuyenMai = khuyenMaiService.getAll();
    DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private DangNhapService dnsv = new DangNhapService();
    TaiKhoanService tksv = new TaiKhoanService();

    /**
     * Creates new form BanHang
     */
    public BanHang() {
        initComponents();
        loadHoaDon();
        loadTableSpCt();
        loadCategoryThuongHieu();
        DefaultTableModel giohangModel = (DefaultTableModel) tbl_gioHang1.getModel();
        giohangModel.setRowCount(0);

    }

    private void loadCategoryThuongHieu() {
        List<ThuongHieu> listThuongHieu = thuongHieuService.getAll();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo_filterThuongHieu1.getModel();
        if (!listThuongHieu.isEmpty()) {
            for (ThuongHieu thuongHieu : listThuongHieu) {
                model.addElement(thuongHieu.getTen());
            }
        }
        if (!listKhuyenMai.isEmpty()) {
            for (model.KhuyenMai khuyenMai : listKhuyenMai) {
                if (khuyenMai.getSoLuong() > 0) {
                    cbx_khuyenMai1.addItem(khuyenMai.getTenKhuyenMai());
                }
            }

        }
    }

    private boolean thanhToan() {
        try {
            Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn");
            return false;
        }
        try {
            String kh = (tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 2) + "");
            if (kh.equalsIgnoreCase("Trống")) {
                JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng thanh toán");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn khách hàng");
            return false;
        }

        double tongTien;
        double tienPhaiTra;
        double tienGiam = 0;
        try {
            tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
        } catch (Exception e) {
            return false;
        }

        double tienKhachTra;

        try {
            String inputKhuyenMai = cbx_khuyenMai1.getSelectedItem() + "";
            for (model.KhuyenMai khuyenMai : listKhuyenMai) {
                if (khuyenMai.getTenKhuyenMai().equals(inputKhuyenMai)) {
                    if (khuyenMai.getHinhThucKM().equals("VND")) {
                        tienGiam = Double.parseDouble(khuyenMai.getGiaTriGiam());
                    } else if (khuyenMai.getHinhThucKM().equals("%")) {
                        tienGiam = tongTien * (Double.parseDouble(khuyenMai.getGiaTriGiam())) / 100;
                    }

                }
            }
        } catch (Exception e) {
            return false;
        }

        try {
            tienKhachTra = Double.parseDouble(txtTienKhachTra1.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tien Nhap Khong Hop Le");
            return false;
        }
        tienPhaiTra = tongTien - tienGiam;
        Double tienThua = tienKhachTra - tienPhaiTra;

        try {
            Double tongtien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3) + "");
            if (tongtien == 0) {
                JOptionPane.showMessageDialog(this, "Hóa đơn thanh toán trống");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn khách hàng");
            return false;
        }

        try {
            tienKhachTra = Double.parseDouble(txtTienKhachTra1.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tien Nhap Khong Hop Le");
            return false;
        }

        int idHD = Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        if (tienThua >= 0) {
            lblTienThua1.setText(tienThua + "");
            int isPaying = JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán");
            if (isPaying == 0) {
                try {
                    hoadonService.updateThanhToan(idHD);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "thanh toán thất bại(lỗi sql)");
                    return false;
                }

                if (hoadonService.updateThanhToan(idHD)) {
                    JOptionPane.showMessageDialog(this, "Thanh Toán Thành Công");
                    hoadonService.updateTongTien(idHD, tienPhaiTra);
                    updateSoLuongVoucher();
                } else {
                    JOptionPane.showMessageDialog(this, "Thanh Toán Thất bại (1 lí do gì đó)");
                    return false;

                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa đưa đủ tiền cần thanh toán");
            return false;
        }
        return true;
    }

    private void updateSoLuongVoucher() {
        String voucher = cbx_khuyenMai1.getSelectedItem() + "";
        listKhuyenMai = khuyenMaiService.getAll();
        for (model.KhuyenMai khuyenMai : listKhuyenMai) {
            if (khuyenMai.getTenKhuyenMai().equalsIgnoreCase(voucher)) {
                if (khuyenMai.getSoLuong() > 0) {
                    int soLuong = khuyenMai.getSoLuong() - 1;
                    model.KhuyenMai km = new model.KhuyenMai();
                    km.setSoLuong(soLuong);
                    km.setGiaTriGiam(khuyenMai.getGiaTriGiam());
                    km.setHinhThucKM(khuyenMai.getHinhThucKM());
                    km.setTenKhuyenMai(khuyenMai.getTenKhuyenMai());
                    khuyenMaiService.Update(km, khuyenMai.getId());
                } else {
                    return;
                }

            }
        }
    }

    private void loadHoaDon() {
        List<HoaDon> list = hoadonService.getAll();
        DefaultTableModel model = (DefaultTableModel) tbl_hoadon1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < list.size(); i++) {
            HoaDon hoaDon = list.get(i);
            if (hoaDon.getTinhTrang() == 0) {
                model.addRow(new Object[]{
                    hoaDon.getId(),
                    hoaDon.getMa(),
                    hoaDon.getIDKhachHang() == 0 ? "Trống" : getTenKH(hoaDon.getIDKhachHang()),
                    hoaDon.getTongTien(),
                    hoaDon.getTinhTrang() == 0 ? "Chưa Thanh Toán" : "Đã Thanh Toán",
                    tksv.findUserByID(hoaDon.getIdUser()).getTen(),
                    hoaDon.getNgayTao()
                });
            }
        }
    }

    public List<SanPhamChiTiet> loadTableSpCt() {
        DefaultTableModel model = (DefaultTableModel) tbl_sanPham.getModel();

        model.setRowCount(0);

        List<SanPhamChiTiet> list = new ArrayList<>();

        if (txtSpSearch1.getText().trim().isEmpty()) {
            list = chiTietSpService.getAll();
        } else {
            String tenSearch = txtSpSearch1.getText();
            list = chiTietSpService.Search(tenSearch);
        }

        int stt = 1;

        for (SanPhamChiTiet sp : list) {
            if (sp.getSoLuongTon() != 0) {
                model.addRow(new Object[]{
                    sp.getId(),
                    sp.getTenSp().getTen(),
                    sp.getChatLieu(),
                    sp.getKichCo(),
                    sp.getMauSac(),
                    sp.getThuongHieu(),
                    sp.getSoLuongTon(),
                    sp.getGiaBan(),
                    sp.getNhaSx(),});
            }
        }
        return list;
    }

    private void taoHoaDon() {
        Random random = new Random();

        int randomNumber = random.nextInt(899999) + 100000;
        String ma = "HD-" + randomNumber;
        HoaDon hd = new HoaDon();
        if (hoadonService.addHoaDon(ma, dnsv.login(dnsv.getUsername(), dnsv.getPassword()).getId())) {
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thất bại");

        }
    }

    private String getTenKH(int idKH) {
        List<model.KhachHang> listKH = khachHangService.getAll();
        String tenKH = "";
        for (model.KhachHang khachHang : listKH) {
            if (khachHang.getId() == idKH) {
                tenKH = khachHang.getTen();
                return tenKH;
            }
        }
        return null;
    }

    private int getIdKhFromIdHD(int idHD) {
        int idKH = 0;
        List<HoaDon> listHD = hoadonService.getAll();
        for (HoaDon hoaDon : listHD) {
            if (hoaDon.getId() == idHD) {
                idKH = hoaDon.getIDKhachHang();
            }
        }
        return idKH;
    }

    private String getSDTKH(int idKH) {
        List<model.KhachHang> listKH = khachHangService.getAll();
        String SDT = "";
        for (model.KhachHang khachHang : listKH) {
            if (khachHang.getId() == idKH) {
                SDT = khachHang.getSdt();
            }
        }
        return SDT;
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

    private void loadGioHangByIDHD(int idHD) {
        List<SanPhamChiTiet> listSPCTLoad = new ArrayList<>();
        List<Integer> listSoLuong = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : listHDCT) {
            if (hoaDonChiTiet.getHaoDon().getId() == idHD) {
                listSPCTLoad.add(getSPCTbyIDSP(hoaDonChiTiet.getSanPham().getId()));
                listSoLuong.add(hoaDonChiTiet.getSoluong());
            }
        }
        DefaultTableModel model = (DefaultTableModel) tbl_gioHang1.getModel();
        model.setRowCount(0);
        for (int i = 0; i < listSPCTLoad.size(); i++) {
            SanPhamChiTiet sanPhamChiTiet = listSPCTLoad.get(i);
            int soLuong = listSoLuong.get(i);
            model.addRow(new Object[]{
                sanPhamChiTiet.getId(),
                sanPhamChiTiet.getTenSp().getTen(),
                sanPhamChiTiet.getChatLieu(),
                sanPhamChiTiet.getMauSac(),
                sanPhamChiTiet.getKichCo(),
                sanPhamChiTiet.getGiaBan(),
                soLuong,
                sanPhamChiTiet.getGiaBan() * soLuong
            });
        }
    }

    private void loadLableThongTin() {
        int idHD = Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
        lbl_sdtKH1.setText(getSDTKH(getIdKhFromIdHD(idHD)));
        lbl_tenKH1.setText(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 2) + "");
        lbTongTien1.setText(decimalFormat.format(tongTien) + " đ");
        lb_idHD1.setText(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        lbl_tienPhaitra1.setText(decimalFormat.format(tongTien) + " đ");
        lbl_nguoiTao.setText(tksv.findUserByID(hoadonService.getAllHDChuaTT().get(tbl_hoadon1.getSelectedRow()).getIdUser()).getTen());
    }

    private boolean themSpVaoHD() {
        if (tbl_hoadon1.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn ");
            System.out.println(tbl_hoadon1.getSelectedRow());
            return false;
        } else if (tbl_sanPham.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm ");
            System.out.println(tbl_hoadon1.getSelectedRow());
            return false;
        } else {
            int idHD = Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
            int idSP = Integer.parseInt(tbl_sanPham.getValueAt(tbl_sanPham.getSelectedRow(), 0) + "");
            int soLuong;
            try {
                soLuong = Integer.parseInt(JOptionPane.showInputDialog("Nhập số lượng"));
                if (soLuong < 1) {
                    JOptionPane.showMessageDialog(this, "So luong khong hop le");
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "So luong khong hop le");
                return false;
            }

            double donGia = Double.parseDouble(tbl_sanPham.getValueAt(tbl_sanPham.getSelectedRow(), 7) + "");
            HoaDon hd = new HoaDon();
            hd.setId(idHD);
            SanPhamChiTiet spct = new SanPhamChiTiet();
            spct.setId(idSP + "");
            int soLuongTon = Integer.parseInt(tbl_sanPham.getValueAt(tbl_sanPham.getSelectedRow(), 6) + "");
            int SoluongMoi = soLuongTon - soLuong;
            if (SoluongMoi < 0) {
                JOptionPane.showMessageDialog(this, "Sản phẩm trong kho không đủ");
                return false;
            }

            for (HoaDonChiTiet hoaDonChiTiet : listHDCT) {
                if (hoaDonChiTiet.getHaoDon().getId() == idHD && Integer.parseInt(hoaDonChiTiet.getSanPham().getId()) == idSP) {
                    int isBuyMore = JOptionPane.showConfirmDialog(this, "sản phẩm đã có trong giỏ hàng ! Tiếp tục mua thêm ?");
                    System.out.println(isBuyMore);
                    int soLuongDaCo = hoaDonChiTiet.getSoluong();
                    if (isBuyMore == 0) {
                        if (hoaDonCTService.updateSoLuongSPHoaDonCT(idHD, idSP, soLuong + soLuongDaCo)) {
                            JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ thành công ! ");
                            hoaDonCTService.updateSoLuongSP(idSP, SoluongMoi);
                            loadTableSpCt();
                            return true;
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ thất bại ! ");
                            return false;
                        }
                    } else {
                        return false;
                    }

                }
            }
            if (hoaDonCTService.addHDCT(hd, spct, soLuong, donGia)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ thành công !");
                hoaDonCTService.updateSoLuongSP(idSP, SoluongMoi);
                loadTableSpCt();
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Thêm Sản phẩm Thất Bại!");
            }
        }
        return true;
    }

    private void reFreshListHDCT() {
        listHDCT = hoaDonCTService.getAllHDCT();

    }

    private void updateTongTien(int idHD) {
        List<SanPhamChiTiet> listSPCTLoad = new ArrayList<>();
        List<Integer> listSoLuong = new ArrayList<>();
        Double tongTien = 0.0;
        for (HoaDonChiTiet hoaDonChiTiet : listHDCT) {
            if (hoaDonChiTiet.getHaoDon().getId() == idHD) {
                listSPCTLoad.add(getSPCTbyIDSP(hoaDonChiTiet.getSanPham().getId()));
                listSoLuong.add(hoaDonChiTiet.getSoluong());
            }
        }

        for (int i = 0; i < listSPCTLoad.size(); i++) {
            SanPhamChiTiet sanPhamChiTiet = listSPCTLoad.get(i);
            int soLuong = listSoLuong.get(i);

            tongTien += sanPhamChiTiet.getGiaBan() * soLuong;
        }
        hoadonService.updateTongTien(idHD, tongTien);
        loadHoaDon();
    }

    private void applyKhuyenMai() {
        double giaTienGiam = 0;
        try {
            double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hóa đơn áp dụng voucher !");
            return;
        }
        boolean isAvaliable = true;
        double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
        String inputKhuyenMai = cbx_khuyenMai1.getSelectedItem() + "";
        for (model.KhuyenMai khuyenMai : listKhuyenMai) {

            if (khuyenMai.getTenKhuyenMai().equals(inputKhuyenMai)) {

                if (khuyenMai.getSoLuong() <= 0) {
                    isAvaliable = false;
                } else {
                    if (khuyenMai.getHinhThucKM().equals("VND")) {
                        giaTienGiam = Double.parseDouble(khuyenMai.getGiaTriGiam());
                        if (giaTienGiam > tongTien) {
                            JOptionPane.showMessageDialog(this, "Giá trị giảm không được vượt quá giá trị đơn hàng");
                            cbx_khuyenMai1.setSelectedIndex(0);
                            return;
                        } else {
                            lbl_giamVoucher1.setText(decimalFormat.format(giaTienGiam) + " đ");
                            lbl_tienPhaitra1.setText(decimalFormat.format((tongTien - giaTienGiam)) + " đ");
                            if (!txtTienKhachTra1.getText().trim().isEmpty()) {
                                int tienKhachTra = Integer.valueOf(txtTienKhachTra1.getText().trim());
                                lblTienThua1.setText(decimalFormat.format(tienKhachTra - (tongTien - giaTienGiam)) + " đ");
                                isAvaliable = true;
                                return;
                            }
                            isAvaliable = true;
                            return;
                        }

                    } else if (khuyenMai.getHinhThucKM().equals("%")) {
                        giaTienGiam = tongTien * (Double.parseDouble(khuyenMai.getGiaTriGiam())) / 100;
                        lbl_giamVoucher1.setText(decimalFormat.format(giaTienGiam) + " đ");
                        lbl_tienPhaitra1.setText(decimalFormat.format((tongTien - giaTienGiam)) + " đ");
                        if (!txtTienKhachTra1.getText().trim().isEmpty()) {
                            int tienKhachTra = Integer.valueOf(txtTienKhachTra1.getText().trim());
                            lblTienThua1.setText(decimalFormat.format(tienKhachTra - (tongTien - giaTienGiam)) + " đ");
                            isAvaliable = true;
                            return;
                        }
                        isAvaliable = true;
                        return;
                    }
                }

            } else {
                isAvaliable = false;
            }
        }
        if (!isAvaliable) {
            JOptionPane.showMessageDialog(this, "Khuyến mãi không chính xác hoặc đã hết!");
            return;
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

        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_gioHang1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_hoadon1 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_sanPham = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        btnTaoHoaDon1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblTienThua1 = new javax.swing.JLabel();
        lbl_tenKH1 = new javax.swing.JLabel();
        lbl_sdtKH1 = new javax.swing.JLabel();
        lbTongTien1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_giamVoucher1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbl_tienPhaitra1 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtTienKhachTra1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        lb_idHD1 = new javax.swing.JLabel();
        cbx_khuyenMai1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        lbl_nguoiTao = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtSpSearch1 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        cbo_filterThuongHieu1 = new javax.swing.JComboBox<>();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        btnThemSpVaoHD1 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(940, 580));
        jPanel3.setMinimumSize(new java.awt.Dimension(940, 580));
        jPanel3.setName(""); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_gioHang1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tbl_gioHang1);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 580, 147));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Hóa đơn");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 9, 100, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Giỏ hàng");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 70, -1));

        tbl_hoadon1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "MaHD", "Khách Hàng", "Tổng tiền", "Tình trạng", "Người tạo", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_hoadon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_hoadon1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_hoadon1MouseEntered(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_hoadon1);
        if (tbl_hoadon1.getColumnModel().getColumnCount() > 0) {
            tbl_hoadon1.getColumnModel().getColumn(0).setPreferredWidth(4);
            tbl_hoadon1.getColumnModel().getColumn(2).setPreferredWidth(120);
        }

        jPanel3.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 31, 690, 147));

        tbl_sanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên SP", "Chất liệu", "Kích Cỡ", "Màu Sắc", "Thương Hiệu", "Số Lượng ", "Giá Bán", "NSX"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_sanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sanPhamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_sanPhamMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_sanPhamMousePressed(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_sanPham);
        if (tbl_sanPham.getColumnModel().getColumnCount() > 0) {
            tbl_sanPham.getColumnModel().getColumn(0).setMaxWidth(30);
            tbl_sanPham.getColumnModel().getColumn(1).setPreferredWidth(120);
            tbl_sanPham.getColumnModel().getColumn(3).setMaxWidth(50);
            tbl_sanPham.getColumnModel().getColumn(4).setMaxWidth(70);
            tbl_sanPham.getColumnModel().getColumn(6).setMaxWidth(70);
        }

        jPanel3.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 690, 160));

        jPanel4.setBackground(new java.awt.Color(153, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setBackground(new java.awt.Color(0, 153, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Khách hàng");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, 34));

        btnTaoHoaDon1.setBackground(new java.awt.Color(0, 153, 51));
        btnTaoHoaDon1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoHoaDon1.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHoaDon1.setText("Tạo hóa đơn");
        btnTaoHoaDon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDon1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnTaoHoaDon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 35));

        jButton9.setBackground(new java.awt.Color(0, 153, 51));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Thanh toán");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 160, 40));

        jLabel11.setText("SĐT");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 40, -1));

        jLabel12.setText("Tên khách hàng");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel13.setText("Giảm giá voucher");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, 20));

        jLabel15.setText("Tổng tiền");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        lblTienThua1.setText("...");
        jPanel4.add(lblTienThua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, 130, -1));

        lbl_tenKH1.setText("...");
        jPanel4.add(lbl_tenKH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 160, -1));

        lbl_sdtKH1.setText("...");
        jPanel4.add(lbl_sdtKH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 160, -1));

        lbTongTien1.setText("...");
        jPanel4.add(lbTongTien1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 130, -1));

        jLabel18.setText("Tiền phải trả:");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, 20));

        lbl_giamVoucher1.setText("...");
        jPanel4.add(lbl_giamVoucher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 130, -1));

        jLabel20.setText("Chọn Khuyến Mãi:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jLabel23.setText("Tiền Khách Đưa");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, -1));

        lbl_tienPhaitra1.setText("...");
        jPanel4.add(lbl_tienPhaitra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 130, 20));

        jLabel24.setText("Tiền trả lại khách");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        txtTienKhachTra1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtTienKhachTra1InputMethodTextChanged(evt);
            }
        });
        txtTienKhachTra1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachTra1ActionPerformed(evt);
            }
        });
        txtTienKhachTra1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTienKhachTra1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachTra1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTienKhachTra1KeyTyped(evt);
            }
        });
        jPanel4.add(txtTienKhachTra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 110, 30));

        jLabel25.setText("ID hóa đơn:");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        lb_idHD1.setText("...");
        jPanel4.add(lb_idHD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 90, 20));

        cbx_khuyenMai1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn khuyến mãi" }));
        cbx_khuyenMai1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_khuyenMai1ItemStateChanged(evt);
            }
        });
        cbx_khuyenMai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_khuyenMai1ActionPerformed(evt);
            }
        });
        jPanel4.add(cbx_khuyenMai1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 170, 30));

        jLabel1.setText("Người tạo:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        lbl_nguoiTao.setText("...");
        jPanel4.add(lbl_nguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 140, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 250, 640));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("Sản phẩm");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        txtSpSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSpSearch1ActionPerformed(evt);
            }
        });
        jPanel3.add(txtSpSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 150, 30));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_24px.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 430, 40, 30));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setText("Lọc Thương Hiệu");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 410, -1, -1));

        cbo_filterThuongHieu1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        cbo_filterThuongHieu1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_filterThuongHieu1ItemStateChanged(evt);
            }
        });
        cbo_filterThuongHieu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbo_filterThuongHieu1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbo_filterThuongHieu1MouseEntered(evt);
            }
        });
        cbo_filterThuongHieu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_filterThuongHieu1ActionPerformed(evt);
            }
        });
        cbo_filterThuongHieu1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbo_filterThuongHieu1PropertyChange(evt);
            }
        });
        jPanel3.add(cbo_filterThuongHieu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 430, 130, 30));

        jButton11.setBackground(new java.awt.Color(0, 204, 0));
        jButton11.setText("Xóa SP");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 110, 30));

        jButton12.setBackground(new java.awt.Color(0, 204, 0));
        jButton12.setText("Sửa Số Lượng");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 320, 110, 30));

        btnThemSpVaoHD1.setBackground(new java.awt.Color(0, 204, 0));
        btnThemSpVaoHD1.setText("Thêm Sản Phẩm");
        btnThemSpVaoHD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSpVaoHD1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnThemSpVaoHD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 420, 130, 40));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setText("Tìm theo tên");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, -1, -1));

        jButton13.setBackground(new java.awt.Color(0, 204, 0));
        jButton13.setText("Xóa hết");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 240, 110, 30));

        jButton14.setBackground(new java.awt.Color(0, 204, 0));
        jButton14.setText("Hủy hóa đơn");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, 100, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 936, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_hoadon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_hoadon1MouseClicked
        try {
            Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        } catch (Exception e) {
            return;
        }
        int idHD = Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        loadLableThongTin();
        loadGioHangByIDHD(idHD);
        lblTienThua1.setText("");
        txtTienKhachTra1.setText("");
        try {
            Double.parseDouble(txtTienKhachTra1.getText() + "");
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_tbl_hoadon1MouseClicked

    private void tbl_sanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanPhamMouseClicked

    }//GEN-LAST:event_tbl_sanPhamMouseClicked

    private void tbl_sanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanPhamMouseEntered

    }//GEN-LAST:event_tbl_sanPhamMouseEntered

    private void tbl_sanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanPhamMousePressed

    }//GEN-LAST:event_tbl_sanPhamMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int rowHD = tbl_hoadon1.getSelectedRow();
        if (rowHD < 0) {
            JOptionPane.showMessageDialog(this, "chọn hoá đơn bạn muốn thêm khách hàng vào");
            return;
        }
        String currentKhachHang = tbl_hoadon1.getValueAt(rowHD, 2) + "";
        if (!currentKhachHang.equalsIgnoreCase("Trống")) {
            int isChooseAgain = JOptionPane.showConfirmDialog(this, "Thông tin khách hàng đã có trong hóa đơn, bạn có muốn chọn lại khách hàng ?");
            System.out.println(isChooseAgain);
            if (isChooseAgain != 0) {
                return;
            }
        }

        Runnable afterDialogClosed = new Runnable() {
            @Override
            public void run() {
                loadHoaDon();
            }
        };

// Pass the callback to the dialog
        new khachhangdialog(Integer.parseInt(tbl_hoadon1.getValueAt(rowHD, 0).toString()), afterDialogClosed).setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTaoHoaDon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDon1ActionPerformed
        int newHD = JOptionPane.showConfirmDialog(this, "Tạo 1 hóa đơn mới ?");
        if (newHD == 0) {
            taoHoaDon();
            loadHoaDon();
        }
    }//GEN-LAST:event_btnTaoHoaDon1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (!thanhToan()) {
            return;
        } else {

            loadHoaDon();
            lblTienThua1.setText("");
            loadTableSpCt();
            lbl_giamVoucher1.setText("");
            lbl_sdtKH1.setText("");
            lbl_tenKH1.setText("");
            lbTongTien1.setText("");
            lbl_tienPhaitra1.setText("");
            lb_idHD1.setText("");
            cbx_khuyenMai1.setSelectedIndex(0);
            txtTienKhachTra1.setText("");
            lbl_nguoiTao.setText("");
            DefaultTableModel giohangModel = (DefaultTableModel) tbl_gioHang1.getModel();
            giohangModel.setRowCount(0);

        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void txtTienKhachTra1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtTienKhachTra1InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachTra1InputMethodTextChanged

    private void txtTienKhachTra1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachTra1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachTra1ActionPerformed

    private void txtTienKhachTra1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachTra1KeyPressed

    }//GEN-LAST:event_txtTienKhachTra1KeyPressed

    private void txtTienKhachTra1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachTra1KeyReleased
        double tongTien;
        double tienPhaiTra;
        double tienGiam = 0;
        try {
            tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
        } catch (Exception e) {
            return;
        }

        double tienKhachTra;

        try {
            String inputKhuyenMai = cbx_khuyenMai1.getSelectedItem() + "";
            for (model.KhuyenMai khuyenMai : listKhuyenMai) {
                if (khuyenMai.getTenKhuyenMai().equals(inputKhuyenMai)) {
                    if (khuyenMai.getHinhThucKM().equals("VND")) {
                        tienGiam = Double.parseDouble(khuyenMai.getGiaTriGiam());
                    } else if (khuyenMai.getHinhThucKM().equals("%")) {
                        tienGiam = tongTien * (Double.parseDouble(khuyenMai.getGiaTriGiam())) / 100;
                    }

                }
            }
        } catch (Exception e) {
            return;
        }

        try {
            tienKhachTra = Double.parseDouble(txtTienKhachTra1.getText());
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Tiền nhập không hợp lệ");
            lblTienThua1.setText("Chưa Đủ");
            return;
        }
        tienPhaiTra = tongTien - tienGiam;
        Double tienThua = tienKhachTra - tienPhaiTra;

        if (tienThua >= 0) {
            lblTienThua1.setText(decimalFormat.format(tienThua) + " đ");
        } else {
            lblTienThua1.setText("Chưa Đủ");
        }
    }//GEN-LAST:event_txtTienKhachTra1KeyReleased

    private void txtTienKhachTra1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachTra1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachTra1KeyTyped

    private void cbx_khuyenMai1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_khuyenMai1ItemStateChanged
        if (!(cbx_khuyenMai1.getSelectedItem() + "").equalsIgnoreCase("Chọn khuyến mãi")) {
            applyKhuyenMai();
        } else {
            lbl_giamVoucher1.setText("");
            try {
                double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
                if (!txtTienKhachTra1.getText().trim().isEmpty()) {
                    int tienKhachTra = Integer.valueOf(txtTienKhachTra1.getText().trim());
                    lbl_tienPhaitra1.setText(decimalFormat.format(tongTien) + " đ");
                    lblTienThua1.setText(decimalFormat.format(tienKhachTra - tongTien) + " đ");
                    return;
                }
            } catch (Exception e) {
                return;
            }
            double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
            lbl_tienPhaitra1.setText(decimalFormat.format(tongTien) + " đ");
        }
    }//GEN-LAST:event_cbx_khuyenMai1ItemStateChanged

    private void cbx_khuyenMai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_khuyenMai1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbx_khuyenMai1ActionPerformed

    private void txtSpSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSpSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSpSearch1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String tenThuongHieu = cbo_filterThuongHieu1.getSelectedItem().toString();
        if (tenThuongHieu.equals("All")) {
            loadTableSpCt();
        } else {
            DefaultTableModel model = (DefaultTableModel) tbl_sanPham.getModel();
            model.setRowCount(0);
            List<SanPhamChiTiet> listLoc = new ArrayList<>();
            for (SanPhamChiTiet sanPhamChiTiet : loadTableSpCt()) {
                if (sanPhamChiTiet.getThuongHieu().getTen().equals(tenThuongHieu)) {
                    listLoc.add(sanPhamChiTiet);
                }
            }
            model.setRowCount(0);
            for (SanPhamChiTiet sp : listLoc) {
                model.addRow(new Object[]{
                    sp.getId(),
                    sp.getTenSp().getTen(),
                    sp.getChatLieu(),
                    sp.getKichCo(),
                    sp.getMauSac(),
                    sp.getThuongHieu(),
                    sp.getSoLuongTon(),
                    sp.getGiaBan(),
                    sp.getNhaSx()});
            }
            if (listLoc.isEmpty()) {
                model.setRowCount(0);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void cbo_filterThuongHieu1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1ItemStateChanged
        String tenThuongHieu = cbo_filterThuongHieu1.getSelectedItem().toString();
        if (tenThuongHieu.equals("All")) {
            loadTableSpCt();
        } else {
            DefaultTableModel model = (DefaultTableModel) tbl_sanPham.getModel();
            model.setRowCount(0);
            List<SanPhamChiTiet> listLoc = new ArrayList<>();
            for (SanPhamChiTiet sanPhamChiTiet : loadTableSpCt()) {
                if (sanPhamChiTiet.getThuongHieu().getTen().equals(tenThuongHieu)) {
                    listLoc.add(sanPhamChiTiet);
                }
            }
            model.setRowCount(0);
            for (SanPhamChiTiet sp : listLoc) {
                model.addRow(new Object[]{
                    sp.getId(),
                    sp.getTenSp().getTen(),
                    sp.getChatLieu(),
                    sp.getKichCo(),
                    sp.getMauSac(),
                    sp.getThuongHieu(),
                    sp.getSoLuongTon(),
                    sp.getGiaBan(),
                    sp.getNhaSx()});
            }
            if (listLoc.isEmpty()) {
                model.setRowCount(0);
            }
        }

    }//GEN-LAST:event_cbo_filterThuongHieu1ItemStateChanged

    private void cbo_filterThuongHieu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1MouseClicked

    }//GEN-LAST:event_cbo_filterThuongHieu1MouseClicked

    private void cbo_filterThuongHieu1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1MouseEntered

    }//GEN-LAST:event_cbo_filterThuongHieu1MouseEntered

    private void cbo_filterThuongHieu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1ActionPerformed

    }//GEN-LAST:event_cbo_filterThuongHieu1ActionPerformed

    private void cbo_filterThuongHieu1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1PropertyChange

    }//GEN-LAST:event_cbo_filterThuongHieu1PropertyChange

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không?");
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm trong giỏ cần xóa");
                return;
            }
            try {
                Integer.parseInt(tbl_gioHang1.getValueAt(tbl_gioHang1.getSelectedRow(), 0) + "");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm trong giỏ cần xóa");
                return;

            }
            int selectedRow = tbl_hoadon1.getSelectedRow();
            int idHD = Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
            int idSP = Integer.parseInt(tbl_gioHang1.getValueAt(tbl_gioHang1.getSelectedRow(), 0) + "");
            int soLuong = Integer.parseInt(tbl_gioHang1.getValueAt(tbl_gioHang1.getSelectedRow(), 6) + "");
            if (hoaDonCTService.deleteHDCT(idHD, idSP)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                List<SanPhamChiTiet> listSPCT = chiTietSpService.getAll();
                for (SanPhamChiTiet sanPhamChiTiet : listSPCT) {
                    if (Integer.parseInt(sanPhamChiTiet.getId()) == idSP) {
                        int soLuongMoi = sanPhamChiTiet.getSoLuongTon() + soLuong;
                        chiTietSpService.phucHoiSoLuong(idSP, soLuongMoi);
                        reFreshListHDCT();
                        loadTableSpCt();
                    }
                }
                reFreshListHDCT();
                loadGioHangByIDHD(idHD);
                updateTongTien(idHD);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
            if (selectedRow != -1 && selectedRow < tbl_hoadon1.getRowCount()) {
                tbl_hoadon1.setRowSelectionInterval(selectedRow, selectedRow);
            }
            loadLableThongTin();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }

    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int selectedRow = tbl_hoadon1.getSelectedRow();
        try {
            Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm trong giỏ cần sửa");
            return;
        }
        try {
            Integer.parseInt(tbl_gioHang1.getValueAt(tbl_gioHang1.getSelectedRow(), 0) + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm trong giỏ cần sửa");
            return;
        }

        int idSP = Integer.parseInt(tbl_gioHang1.getValueAt(tbl_gioHang1.getSelectedRow(), 0) + "");
        int soLuong = Integer.parseInt(tbl_gioHang1.getValueAt(tbl_gioHang1.getSelectedRow(), 6) + "");
        int idHD = Integer.parseInt(tbl_hoadon1.getValueAt(selectedRow, 0) + "");
        int soLuongUpdate;
        try {
            soLuongUpdate = Integer.parseInt(JOptionPane.showInputDialog("nhập số lượng sửa"));
            if (soLuongUpdate <= 0) {
                JOptionPane.showMessageDialog(this, "số lượng hải lớn hơn 0, số lượng nhập vào không hợp lệ");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "số lượng nhập vào không hợp lệ");
            return;
        }

        int soLuongChenhLech = soLuong - soLuongUpdate;

        SanPhamChiTiet selectedSPCT = null;
        List<SanPhamChiTiet> listSPCT = chiTietSpService.getAll();
        for (SanPhamChiTiet sanPhamChiTiet : listSPCT) {
            if (Integer.parseInt(sanPhamChiTiet.getId()) == idSP) {
                selectedSPCT = sanPhamChiTiet;
                break;
            }
        }

        if (selectedSPCT == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm chi tiết");
            return;
        }

// Validate if soLuongUpdate exceeds soLuongTon
        if (soLuongUpdate > selectedSPCT.getSoLuongTon() + soLuong) {
            JOptionPane.showMessageDialog(this, "Số lượng nhập vào không hợp lệ, không được lớn hơn số lượng còn trong sản phẩm chi tiết");
            return;
        }

        int soLuongMoi = selectedSPCT.getSoLuongTon() + soLuongChenhLech;
        hoaDonCTService.updateSoLuongSPHoaDonCT(idHD, idSP, soLuongUpdate);
        chiTietSpService.phucHoiSoLuong(idSP, soLuongMoi);

        reFreshListHDCT();
        loadTableSpCt();

        JOptionPane.showMessageDialog(this, "Sửa Thành Công");
        reFreshListHDCT();
        loadGioHangByIDHD(idHD);
        updateTongTien(idHD);

        if (selectedRow != -1 && selectedRow < tbl_hoadon1.getRowCount()) {
            tbl_hoadon1.setRowSelectionInterval(selectedRow, selectedRow);
        }

        loadLableThongTin();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnThemSpVaoHD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSpVaoHD1ActionPerformed
        try {
            Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "chưa chọn hóa đơn thêm sản phẩm");
            return;
        }
        try {
            int selectedRow = tbl_hoadon1.getSelectedRow();
        } catch (Exception e) {
            return;
        }

        int selectedRow = tbl_hoadon1.getSelectedRow();

        String currentKhachHang = tbl_hoadon1.getValueAt(selectedRow, 2) + "";
        if (currentKhachHang.equalsIgnoreCase("Trống")) {
            JOptionPane.showMessageDialog(this, "Hóa đơn chưa có thông tin khách hàng");
            return;
        }

        if (!themSpVaoHD()) {
            return;
        }

        reFreshListHDCT();

        int idHD = Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        loadGioHangByIDHD(idHD);
        updateTongTien(idHD);
        if (selectedRow != -1 && selectedRow < tbl_hoadon1.getRowCount()) {
            tbl_hoadon1.setRowSelectionInterval(selectedRow, selectedRow);
        }
        loadLableThongTin();
    }//GEN-LAST:event_btnThemSpVaoHD1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hết giỏ hàng không?");
        if (confirm == JOptionPane.YES_OPTION) {
            boolean check = false;
            int idHD = 0;
            if (tbl_hoadon1.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn");
            } else {
                try {
                    idHD = Integer.valueOf(hoadonService.getAllHDChuaTT().get(tbl_hoadon1.getSelectedRow()).getId());
                    for (int i = 0; i < hoaDonCTService.getAllHDCTByIdHD(idHD).size(); i++) {
                        for (int z = 0; z < chiTietSpService.getAll().size(); z++) {
                            if (hoaDonCTService.getAllHDCTByIdHD(idHD).get(i).getHaoDon().getId().equals(idHD)) {
//                            System.out.println(chiTietSpService.getAll().get(z).getId());
//                            System.out.println(hoaDonCTService.getAllHDCT().get(i).getSanPham().getId());
                                if (hoaDonCTService.getAllHDCTByIdHD(idHD).get(i).getSanPham().getId().equals(chiTietSpService.getAll().get(z).getId())) {
//                            System.out.println("-----");
//                            System.out.println(hoaDonCTService.getAllHDCTByIdHD(idHD).get(i).getSanPham().getId());
//                            System.out.println("-----");
                                    int soLuongMoi = hoaDonCTService.getAllHDCTByIdHD(idHD).get(i).getSoluong() + chiTietSpService.getAll().get(z).getSoLuongTon();
                                    chiTietSpService.updateSoLuongSPCT(soLuongMoi, Integer.valueOf(chiTietSpService.getAll2().get(z).getId()));

//                            System.out.println(hoaDonCTService.getAllHDCTByIdHD(idHD).get(i).getSanPham().getId());
//                            System.out.println(chiTietSpService.getAll2().get(z).getId()));
//                            
//                            System.out.println(hoaDonCTService.getAllHDCTByIdHD(idHD).get(i).getSoluong());
//                            System.out.println(chiTietSpService.getAll2().get(z).getSoLuongTon());
//                            System.out.println(soLuongMoi);
                                    check = true;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Có lỗi sảy ra");
                }
            }
            if (check) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                hoaDonCTService.deleteAllHDCT(idHD);
                reFreshListHDCT();
                loadGioHangByIDHD(idHD);
                updateTongTien(idHD);
                loadTableSpCt();

                lbTongTien1.setText("...");
                lblTienThua1.setText("...");
                lbl_giamVoucher1.setText("...");
                lbl_sdtKH1.setText("...");
                lbl_tenKH1.setText("...");
                lblTienThua1.setText("...");
                lb_idHD1.setText("...");
                lbl_tienPhaitra1.setText("");
                lbl_nguoiTao.setText("");
                lblTienThua1.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại.");
        }


    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn hủy hóa đơn này không");
        if (confirm == JOptionPane.YES_OPTION) {
            int rowHD = tbl_hoadon1.getSelectedRow();
            if (rowHD < 0) {
                JOptionPane.showMessageDialog(this, "chọn hoá đơn bạn muốn hủy ");
                return;
            }
            int chon = JOptionPane.showConfirmDialog(this, "bạn có muốn hủy không");
            if (chon != 0) {
                JOptionPane.showMessageDialog(this, "hủy hóa đơn thất bại");
                return;
            }
            boolean check = false;
            List<HoaDonChiTiet> list = this.hoadonService.getById(Integer.parseInt(this.tbl_hoadon1.getValueAt(rowHD, 0).toString()));
            List<SanPhamChiTiet> listspct = chiTietSpService.getAll();
            for (HoaDonChiTiet hdct : list) {
                for (SanPhamChiTiet sp : listspct) {
                    if (sp.getId().equals(hdct.getSanPham().getId())) {
                        check = this.hoaDonCTService.updateSoLuongSP(Integer.parseInt(hdct.getSanPham().getId()), hdct.getSoluong() + sp.getSoLuongTon());
                    }

                }

            }

            check = this.hoadonService.deleteById(Integer.parseInt(this.tbl_hoadon1.getValueAt(rowHD, 0).toString()));
            if (check == false) {
                JOptionPane.showMessageDialog(this, "hủy hóa đơn thất bại ");

            } else {
                JOptionPane.showMessageDialog(this, "hủy hóa đơn thành công ");
            }

            lbl_giamVoucher1.setText("");
            lbl_sdtKH1.setText("");
            lbl_tenKH1.setText("");
            lbTongTien1.setText("");
            lbl_tienPhaitra1.setText("");
            lb_idHD1.setText("");
            cbx_khuyenMai1.setSelectedIndex(0);
            txtTienKhachTra1.setText("");
            lbl_nguoiTao.setText("");

            loadHoaDon();
            loadTableSpCt();
            DefaultTableModel giohangModel = (DefaultTableModel) tbl_gioHang1.getModel();
            giohangModel.setRowCount(0);
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }

    }//GEN-LAST:event_jButton14ActionPerformed

    private void tbl_hoadon1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_hoadon1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_hoadon1MouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTaoHoaDon1;
    private javax.swing.JButton btnThemSpVaoHD1;
    private javax.swing.JComboBox<String> cbo_filterThuongHieu1;
    private javax.swing.JComboBox<String> cbx_khuyenMai1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbTongTien1;
    private javax.swing.JLabel lb_idHD1;
    private javax.swing.JLabel lblTienThua1;
    private javax.swing.JLabel lbl_giamVoucher1;
    private javax.swing.JLabel lbl_nguoiTao;
    private javax.swing.JLabel lbl_sdtKH1;
    private javax.swing.JLabel lbl_tenKH1;
    private javax.swing.JLabel lbl_tienPhaitra1;
    private javax.swing.JTable tbl_gioHang1;
    private javax.swing.JTable tbl_hoadon1;
    private javax.swing.JTable tbl_sanPham;
    private javax.swing.JTextField txtSpSearch1;
    private javax.swing.JTextField txtTienKhachTra1;
    // End of variables declaration//GEN-END:variables
}
