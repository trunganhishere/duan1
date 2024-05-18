/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author duong
 */
public class SanPhamChiTiet {
    private Integer id;
    private Integer idNSX;
    private Integer idMauSac;
    private Integer idDanhMuc;
    private Integer idKC;
    private Integer idCL;
    private Integer idTH;
    private String moTa;
    private Integer soLuongTon;
    private Integer giaNhap;
    private Integer giaBan;
    private String qrCode;
    private Integer idSP;
    private Integer idKM;

    public SanPhamChiTiet() {
    }

    public SanPhamChiTiet(Integer id, Integer idNSX, Integer idMauSac, Integer idDanhMuc, Integer idKC, Integer idCL, Integer idTH, String moTa, Integer soLuongTon, Integer giaNhap, Integer giaBan, String qrCode, Integer idSP, Integer idKM) {
        this.id = id;
        this.idNSX = idNSX;
        this.idMauSac = idMauSac;
        this.idDanhMuc = idDanhMuc;
        this.idKC = idKC;
        this.idCL = idCL;
        this.idTH = idTH;
        this.moTa = moTa;
        this.soLuongTon = soLuongTon;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.qrCode = qrCode;
        this.idSP = idSP;
        this.idKM = idKM;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdNSX() {
        return idNSX;
    }

    public void setIdNSX(Integer idNSX) {
        this.idNSX = idNSX;
    }

    public Integer getIdMauSac() {
        return idMauSac;
    }

    public void setIdMauSac(Integer idMauSac) {
        this.idMauSac = idMauSac;
    }

    public Integer getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(Integer idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public Integer getIdKC() {
        return idKC;
    }

    public void setIdKC(Integer idKC) {
        this.idKC = idKC;
    }

    public Integer getIdCL() {
        return idCL;
    }

    public void setIdCL(Integer idCL) {
        this.idCL = idCL;
    }

    public Integer getIdTH() {
        return idTH;
    }

    public void setIdTH(Integer idTH) {
        this.idTH = idTH;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(Integer soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public Integer getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Integer giaNhap) {
        this.giaNhap = giaNhap;
    }

    public Integer getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Integer giaBan) {
        this.giaBan = giaBan;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Integer getIdSP() {
        return idSP;
    }

    public void setIdSP(Integer idSP) {
        this.idSP = idSP;
    }

    public Integer getIdKM() {
        return idKM;
    }

    public void setIdKM(Integer idKM) {
        this.idKM = idKM;
    }
    
    
}
