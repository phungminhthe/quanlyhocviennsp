/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ublity;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.HocVien;

/**
 *
 * @author PC
 */
public class ClassTableModel {

    public DefaultTableModel setTableHocVien(List<HocVien> listItem, String[] listColumn) {
        int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 7 ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        int num = listItem.size();
        HocVien hocVien = null;
        for (int i = 0; i < num; i++) {
            hocVien = listItem.get(i);
            obj = new Object[columns];
            obj[0] = hocVien.getMa_hoc_vien();
            obj[1] = (i + 1);
            obj[2] = hocVien.getHo_ten();
            obj[3] = hocVien.getNgay_sinh();
            obj[4] = hocVien.isGioi_tinh() == true ? "Nam" : "Nữ";
            obj[5] = hocVien.getSo_dien_thoai();
            obj[6] = hocVien.getDia_chi();
            obj[7] = hocVien.isTinh_trang();
            dtm.addRow(obj);
        }
        return dtm;
    }

}
