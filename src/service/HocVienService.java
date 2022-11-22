/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.HocVien;

/**
 *
 * @author PC
 */
public interface HocVienService {
    public List<HocVien> getList();
    
    public int createOrUpdate(HocVien hocVien);
}
