/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author PC
 */
public class LopHocBean {

    private String ngay_dang_ky;
    private int so_luong_hoc_vien;

    public LopHocBean() {
    }

    public LopHocBean(String ngay_dang_ky, int so_luong_hoc_vien) {
        this.ngay_dang_ky = ngay_dang_ky;
        this.so_luong_hoc_vien = so_luong_hoc_vien;
    }

    public String getNgay_dang_ky() {
        return ngay_dang_ky;
    }

    public void setNgay_dang_ky(String ngay_dang_ky) {
        this.ngay_dang_ky = ngay_dang_ky;
    }

    public int getSo_luong_hoc_vien() {
        return so_luong_hoc_vien;
    }

    public void setSo_luong_hoc_vien(int so_luong_hoc_vien) {
        this.so_luong_hoc_vien = so_luong_hoc_vien;
    }

    

}
