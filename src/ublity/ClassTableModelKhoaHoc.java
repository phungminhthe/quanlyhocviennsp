/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ublity;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.KhoaHoc;

/**
 *
 * @author PC
 */
public class ClassTableModelKhoaHoc {
    public DefaultTableModel setTableKhoaHoc(List<KhoaHoc> listItem, String[] listColumn) {
        int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 6 ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        int num = listItem.size();
        KhoaHoc khoaHoc = null;
        for (int i = 0; i < num; i++) {
            khoaHoc = listItem.get(i);
            obj = new Object[columns];
            obj[0] = khoaHoc.getMa_khoa_hoc();
            obj[1] = (i + 1);
            obj[2] = khoaHoc.getTen_khoa_hoc();
            obj[3] = khoaHoc.getMo_ta();
            obj[4] = khoaHoc.getNgay_bat_dau();
            obj[5] = khoaHoc.getNgay_ket_thuc();
            obj[6] = khoaHoc.isTinh_trang();
            dtm.addRow(obj);
        }
        return dtm;
    }

}
