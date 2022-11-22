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
import model.KhoaHoc;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;
/**
 *
 * @author PC
 */
public class KhoaHocController {
    private JButton btnSubmit;
    private JButton btnXoa;
    private JTextField jtfMaKhoaHoc;
    private JTextField jtfTenKhoaHoc;
    private JTextField jtfMoTa;
    private JDateChooser jdcNgayBatDau;
    private JDateChooser jdcNgayKetthuc;
    private JCheckBox jcbKichHoat;
    private JLabel jlbMsg;
    
    private KhoaHoc khoaHoc = null;

    private KhoaHocService khoaHocService = null;
    
    public KhoaHocController(JButton btnSubmit, JTextField jtfMaKhoaHoc, JTextField jtfTenKhoaHoc,JTextField jtfMoTa,
            JDateChooser jdcNgayBatDau, JDateChooser jdcNgayKetThuc,JCheckBox jcbKichHoat, JLabel jlbMsg, JButton btnXoa) {
        this.btnSubmit = btnSubmit;
        this.btnXoa = btnXoa;
        this.jtfMaKhoaHoc = jtfMaKhoaHoc;
        this.jtfTenKhoaHoc = jtfTenKhoaHoc;
        this.jtfMoTa = jtfMoTa;
        this.jdcNgayBatDau = jdcNgayBatDau;
        this.jdcNgayKetthuc = jdcNgayKetThuc;
        this.jcbKichHoat = jcbKichHoat;
        this.jlbMsg = jlbMsg;

        this.khoaHocService = new KhoaHocServiceImpl();
    }

    public void setView(KhoaHoc khoaHoc) {
        this.khoaHoc = khoaHoc;
        jtfMaKhoaHoc.setText("#" + khoaHoc.getMa_khoa_hoc());
        jtfTenKhoaHoc.setText(khoaHoc.getTen_khoa_hoc());
        jtfMoTa.setText(khoaHoc.getMo_ta());
        jdcNgayBatDau.setDate(khoaHoc.getNgay_bat_dau());
        jdcNgayKetthuc.setDate(khoaHoc.getNgay_ket_thuc());
        jcbKichHoat.setSelected(khoaHoc.isTinh_trang());

        setEvent();
    }
    public void setEvent() {
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (jtfTenKhoaHoc.getText().length() == 0 || jdcNgayBatDau.getDate() == null || jdcNgayKetthuc.getDate() == null) {
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc!");
                    } else {
                        khoaHoc.setTen_khoa_hoc(jtfTenKhoaHoc.getText());
                        khoaHoc.setMo_ta(jtfMoTa.getText());
                        khoaHoc.setNgay_bat_dau(covertDateToDateSql(jdcNgayBatDau.getDate()));
                        khoaHoc.setNgay_ket_thuc(covertDateToDateSql(jdcNgayKetthuc.getDate()));
                        khoaHoc.setTinh_trang(jcbKichHoat.isSelected());
                        
                        if (showDialog()) {
                            int lastId = khoaHocService.createOrUpdate(khoaHoc);
                            if (lastId != 0) {
                                khoaHoc.setMa_khoa_hoc(lastId);
                                jtfMaKhoaHoc.setText("#" + khoaHoc.getMa_khoa_hoc());
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
    
    private boolean showDialog1() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn muốn xóa dữ liệu?", "Thông báo", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }
    
    public java.sql.Date covertDateToDateSql(Date d) {
        return new java.sql.Date(d.getTime());
    }
    
}
