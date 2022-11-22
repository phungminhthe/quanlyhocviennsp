/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.KhoaHoc;

/**
 *
 * @author PC
 */
public interface KhoaHocService {
    public List<KhoaHoc> getList();
    
    public int createOrUpdate(KhoaHoc khoaHoc);
    public boolean delete(KhoaHoc khoaHoc);
}
