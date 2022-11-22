/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.KhoaHocDAOImpl;
import java.util.List;
import model.KhoaHoc;
import dao.KhoaHocDAO;

/**
 *
 * @author PC
 */
public class KhoaHocServiceImpl implements KhoaHocService{
    private KhoaHocDAO khoaHocDAO = null;
    
    public KhoaHocServiceImpl(){
        khoaHocDAO = new KhoaHocDAOImpl();
    }
    @Override
    public List<KhoaHoc> getList() {
        return khoaHocDAO.getList();
    }

    @Override
    public int createOrUpdate(KhoaHoc khoaHoc) {
        return khoaHocDAO.createOrUpdate(khoaHoc);
    }

    @Override
    public boolean delete(KhoaHoc khoaHoc) {
        return  khoaHocDAO.delete(khoaHoc);
    }
}
