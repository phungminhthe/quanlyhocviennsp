/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.HocVien;
import service.HocVienService;
import service.HocVienServiceImpl;

/**
 *
 * @author PC
 */
public class HocVienController {
    
    private JButton btnSubmit;
    private JButton btnXoa;
    private JTextField jtfMaHocVien;
    private JTextField jtfHoTen;
    private JDateChooser jdcNgaySinh;
    private JTextField jtfSoDienThoai;
    private JRadioButton jtfGioiTinhNam;
    private JRadioButton jtfGioiTinhNu;
    private JTextArea jtaDiaChi;
    private JCheckBox jcbKichHoat;
    private JLabel jlbMsg;

    private HocVien hocVien = null;

    private HocVienService hocVienService = null;
    
    public HocVienController(JButton btnSubmit, JTextField jtfMaHocVien, JTextField jtfHoTen,
            JDateChooser jdcNgaySinh, JTextField jtfSoDienThoai, JRadioButton jtfGioiTinhNam, JRadioButton jtfGioiTinhNu,
            JTextArea jtaDiaChi, JCheckBox jcbKichHoat, JLabel jlbMsg, JButton btnXoa) {
        this.btnSubmit = btnSubmit;
        this.btnXoa = btnXoa;
        this.jtfMaHocVien = jtfMaHocVien;
        this.jtfHoTen = jtfHoTen;
        this.jdcNgaySinh = jdcNgaySinh;
        this.jtfSoDienThoai = jtfSoDienThoai;
        this.jtfGioiTinhNam = jtfGioiTinhNam;
        this.jtfGioiTinhNu = jtfGioiTinhNu;
        this.jtaDiaChi = jtaDiaChi;
        this.jcbKichHoat = jcbKichHoat;
        this.jlbMsg = jlbMsg;

        this.hocVienService = new HocVienServiceImpl();
    }

    public void setView(HocVien hocVien) {
        this.hocVien = hocVien;
        jtfMaHocVien.setText("#" + hocVien.getMa_hoc_vien());
        jtfHoTen.setText(hocVien.getHo_ten());
        jdcNgaySinh.setDate(hocVien.getNgay_sinh());
        if (hocVien.isGioi_tinh()) {
            jtfGioiTinhNam.setSelected(true);
        } else {
            jtfGioiTinhNu.setSelected(true);
        }
        jtfSoDienThoai.setText(hocVien.getSo_dien_thoai());
        jtaDiaChi.setText(hocVien.getDia_chi());
        jcbKichHoat.setSelected(hocVien.isTinh_trang());

        setEvent();
    }

    public void setEvent() {
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (jtfHoTen.getText().length() == 0 || jdcNgaySinh.getDate() == null) {
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc!");
                    } else {
                        hocVien.setHo_ten(jtfHoTen.getText());
                        hocVien.setNgay_sinh(covertDateToDateSql(jdcNgaySinh.getDate()));
                        hocVien.setSo_dien_thoai(jtfSoDienThoai.getText());
                        hocVien.setDia_chi(jtaDiaChi.getText());
                        hocVien.setGioi_tinh(jtfGioiTinhNam.isSelected());
                        hocVien.setTinh_trang(jcbKichHoat.isSelected());
                        
//                        hocVienService.createOrUpdate(hocVien);
//                        int lastId = hocVienService.createOrUpdate(hocVien);
//                        if(lastId > 0){
//                            hocVien.setMa_hoc_vien(lastId);
//                            jtfMaHocVien.setText("#" + hocVien.getMa_hoc_vien());
//                            jlbMsg.setText("Xử lý cập nhật dữ liệu thành công!");
//                        }
                        if (showDialog()) {
                            int lastId = hocVienService.createOrUpdate(hocVien);
                            if (lastId != 0) {
                                hocVien.setMa_hoc_vien(lastId);
                                jtfMaHocVien.setText("#" + hocVien.getMa_hoc_vien());
                                jlbMsg.setText("Xử lý cập nhật dữ liệu thành công!");
                            } 
                        }
                    }
                } catch (Exception ex) {
                    jlbMsg.setText(ex.toString());
                }
            }
            
            
            
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSubmit.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(100, 221, 23));
            }
        });
        
        
    }

//    private boolean checkNotNull() {
//        return jtfHoTen.getText() != null && !jtfHoTen.getText().equalsIgnoreCase("");
//    }

    private boolean showDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn muốn cập nhật?", "Thông báo", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }
    
    public java.sql.Date covertDateToDateSql(Date d) {
        return new java.sql.Date(d.getTime());
    }
    
}