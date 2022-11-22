/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import bean.KhoaHocBean;
import bean.LopHocBean;
import dao.ThongKeDAO;
import dao.ThongKeDAOImpl;
import java.util.List;

/**
 *
 * @author PC
 */
public class ThongKeSeviceImpl implements ThongKeService{

    ThongKeDAO thongKeDAO = null;

    public ThongKeSeviceImpl() {
        thongKeDAO = new ThongKeDAOImpl();
    }
    
    
    @Override
    public List<LopHocBean> getListByLopHoc() {
        return thongKeDAO.getListByLopHoc();
    }

    @Override
    public List<KhoaHocBean> getListByKhoaHoc() {
        return thongKeDAO.getListByKhoaHoc();
    }
    
}
