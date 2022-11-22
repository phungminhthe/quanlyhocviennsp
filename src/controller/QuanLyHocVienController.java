/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

//import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.util.Date;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.HocVien;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.HocVienService;
import service.HocVienServiceImpl;
import ublity.ClassTableModel;
import view.HocVienJFrame;

/**
 *
 * @author PC
 */
public class QuanLyHocVienController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JButton btnPrint;

    private ClassTableModel classTableModel = null;

    private final String[] COLUMNS = {"Mã học viên", "STT", "Tên học viên", "Ngày sinh",
        "Giới tính", "Số điện thoại", "Địa chỉ", "Trạng thái"};

    private HocVienService hocVienService = null;

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyHocVienController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton btnPrint) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnPrint = btnPrint;

        this.classTableModel = new ClassTableModel();

        this.hocVienService = new HocVienServiceImpl();
    }

    public void setDataToTable() {

        List<HocVien> listItem = hocVienService.getList();
        
        String[] listColumn = {"Mã học viên", "STT", "Tên học viên", "Ngày sinh",
        "Giới tính", "Số điện thoại", "Địa chỉ", "Trạng thái"};
        DefaultTableModel model = classTableModel.setTableHocVien(listItem, listColumn);
        JTable table = new JTable(model);
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
        
        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        table.getColumnModel().getColumn(1).setMinWidth(80);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2 &&table.getSelectedRow()!=-1){
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.getSelectedRow();
                    selectedRowIndex = table.convertColumnIndexToModel(selectedRowIndex);
                    
                    HocVien hocVien = new HocVien();
                    hocVien.setMa_hoc_vien((int)model.getValueAt(selectedRowIndex, 0));
                    hocVien.setHo_ten(model.getValueAt(selectedRowIndex, 2).toString());
                    hocVien.setNgay_sinh((Date) model.getValueAt(selectedRowIndex, 3));
                    hocVien.setGioi_tinh(model.getValueAt(selectedRowIndex, 4).toString().equalsIgnoreCase("Nam"));
                    hocVien.setSo_dien_thoai(model.getValueAt(selectedRowIndex, 5) != null ?
                            model.getValueAt(selectedRowIndex, 5).toString() : "");
                    hocVien.setDia_chi(model.getValueAt(selectedRowIndex, 6) != null ?
                            model.getValueAt(selectedRowIndex, 6).toString() : "");
                    hocVien.setTinh_trang((boolean)model.getValueAt(selectedRowIndex, 7));
                    
                    
                    HocVienJFrame frame = new HocVienJFrame(hocVien);
                    frame.setTitle("Thông Tin Học ");
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }
            
        });
        
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();
        
        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }
    
    public void setEvent(){
        btnAdd.addMouseListener(new MouseAdapter(){
        @Override
            public void mouseClicked(MouseEvent e) {
                HocVienJFrame frame = new HocVienJFrame(new HocVien());
                frame.setTitle("Thông Tin Học viên");
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAdd.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAdd.setBackground(new Color(100, 221, 23));
            }
        });
        
        btnPrint.addMouseListener(new MouseAdapter(){
        @Override
            public void mouseClicked(MouseEvent e) {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Học Viên");
                
                XSSFRow row = null;
                Cell cell = null;
                
                row = sheet.createRow(3);
                
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue("STT");
                
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue("Họ Và Tên");
                
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue("Ngày Sinh");
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue("Giới tính");
                
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue("Số điện thoại");
                
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue("Địa chỉ");
                
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue("Tình Trạng");
                
                List<HocVien> listItem = hocVienService.getList();
                
                if(listItem != null){
                    FileOutputStream fis = null;
                    try {
                        int s = listItem.size();
                        for (int i = 0; i < s; i++) {
                            HocVien hocVien = listItem.get(i);
                            
                            row = sheet.createRow(4 + i);
                            
                            cell = row.createCell(0, CellType.NUMERIC);
                            cell.setCellValue(i + 1);
                            
                            cell = row.createCell(1, CellType.STRING);
                            cell.setCellValue(hocVien.getHo_ten());
                            
                            cell = row.createCell(2, CellType.STRING);
                            cell.setCellValue(hocVien.getNgay_sinh().toString());
                            
                            cell = row.createCell(3, CellType.STRING);
                            cell.setCellValue(hocVien.isGioi_tinh() ? "Nam" : "Nữ");
                            
                            cell = row.createCell(4, CellType.STRING);
                            cell.setCellValue(hocVien.getSo_dien_thoai());
                            
                            cell = row.createCell(5, CellType.STRING);
                            cell.setCellValue(hocVien.getDia_chi());
                            
                            cell = row.createCell(6, CellType.STRING);
                            cell.setCellValue(hocVien.isTinh_trang()? "On" : "Off");
                        }   
                        // save file here
                        File f = new File("C:\\Users\\PC\\OneDrive\\Tài liệu\\NetBeansProjects\\QuanLyHocVien\\excel\\hoc_vien.xlsx");
                        fis = new FileOutputStream(f);
                        workbook.write(fis);
                        fis.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } 
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                btnPrint.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnPrint.setBackground(new Color(100, 221, 23));
            }
        });
    }
}
