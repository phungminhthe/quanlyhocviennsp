/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.HocVienDAOImpl;
import java.util.List;
import model.HocVien;
import dao.HocVienDAO;

/**
 *
 * @author PC
 */
public class HocVienServiceImpl implements HocVienService{
    private HocVienDAO hocVienDao = null;
    
    public HocVienServiceImpl(){
        hocVienDao = new HocVienDAOImpl();
    }
    @Override
    public List<HocVien> getList() {
        return hocVienDao.getList();
    }

    @Override
    public int createOrUpdate(HocVien hocVien) {
        return hocVienDao.createOrUpdate(hocVien);
    }
}
