/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.HocVien;

/**
 *
 * @author PC
 */
public interface HocVienDAO {
    public List<HocVien> getList();
    
    public int createOrUpdate(HocVien hocVien);
    public int delete(HocVien hocvien);
}
