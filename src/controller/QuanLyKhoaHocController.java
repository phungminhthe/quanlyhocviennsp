/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.KhoaHoc;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;
import ublity.ClassTableModelKhoaHoc;
import view.KhoaHocJFrame1;

/**
 *
 * @author PC
 */
public class QuanLyKhoaHocController {
    
    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JButton btnPrint;

    private ClassTableModelKhoaHoc classTableModelKhoaHoc = null;

    private final String[] COLUMNS = {"Mã khóa học", "STT", "Tên khóa học", "mô tả",
        "ngày bắt đầu", "ngày kết thúc","Trạng thái"};

    private KhoaHocService khoaHocService = null;

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyKhoaHocController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton btnPrint) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnPrint = btnPrint;

        this.classTableModelKhoaHoc = new ClassTableModelKhoaHoc();

        this.khoaHocService = new KhoaHocServiceImpl();
    }

    public void setDataToTable() {
        
        List<KhoaHoc> listItem = khoaHocService.getList();
        
        String[] listColumn = {"Mã khóa học", "STT", "Tên khóa học", "mô tả",
        "ngày bắt đầu", "ngày kết thúc","Trạng thái"};
        DefaultTableModel model = classTableModelKhoaHoc.setTableKhoaHoc(listItem, listColumn);
        JTable table = new JTable(model);
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
        
        

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
                    
                    KhoaHoc khoaHoc = new KhoaHoc();
                    khoaHoc.setMa_khoa_hoc((int)model.getValueAt(selectedRowIndex, 0));
                    
                    khoaHoc.setTen_khoa_hoc(model.getValueAt(selectedRowIndex, 2).toString());
                    
                    khoaHoc.setMo_ta(model.getValueAt(selectedRowIndex, 3).toString());
                    
                    khoaHoc.setNgay_bat_dau((Date) model.getValueAt(selectedRowIndex, 4));
                    
                    khoaHoc.setNgay_ket_thuc((Date) model.getValueAt(selectedRowIndex, 5));
                    
                    khoaHoc.setTinh_trang((boolean)model.getValueAt(selectedRowIndex, 6));
                    
                    
                    KhoaHocJFrame1 frame1 = new KhoaHocJFrame1(khoaHoc);
                    frame1.setTitle("Thông Tin Khóa Học ");
                    frame1.setResizable(false);
                    frame1.setLocationRelativeTo(null);
                    frame1.setVisible(true);
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }

                //row index is found...
                int rowindex = table.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    JPopupMenu popup = createDeletePopUp(rowindex, table);
                    popup.show(e.getComponent(), e.getX(), e.getY());
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
    
    public static JPopupMenu createDeletePopUp(int rowindex, JTable table) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Xoá");

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(main.Main.mainFrame,
                        "Bạn muốn xoá hoá đơn này không?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);              
                if (dialogResult == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());

                    // Lay thong tin tren bang

                    JOptionPane.showMessageDialog(main.Main.mainFrame, "Delete scucessfuly");

                        

                }
            }
        });
        popup.add(delete);
        return popup;
    }
    
    public void setEvent(){
        btnAdd.addMouseListener(new MouseAdapter(){
        @Override
            public void mouseClicked(MouseEvent e) {
                KhoaHocJFrame1 frame1 = new KhoaHocJFrame1(new KhoaHoc());
                frame1.setTitle("Thông tin Khóa học");
                frame1.setLocationRelativeTo(null);
                frame1.setResizable(false);
                frame1.setVisible(true);
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
                XSSFSheet sheet = workbook.createSheet("Khóa Học");
                
                XSSFRow row = null;
                Cell cell = null;
                
                row = sheet.createRow(3);
                
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue("STT");
                
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue("Tên Khóa Học");
                
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue("Mô tả");
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue("Ngày bắt đầu");
                
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue("gày kết thúc");
                
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue("Tình Trạng");
                
                List<KhoaHoc> listItem = khoaHocService.getList();
                
                if(listItem != null){
                    FileOutputStream fis = null;
                    try {
                        int s = listItem.size();
                        for (int i = 0; i < s; i++) {
                            KhoaHoc khoaHoc = listItem.get(i);
                            
                            row = sheet.createRow(4 + i);
                            
                            cell = row.createCell(0, CellType.NUMERIC);
                            cell.setCellValue(i + 1);
                            
                            cell = row.createCell(1, CellType.STRING);
                            cell.setCellValue(khoaHoc.getTen_khoa_hoc());
                            
                            cell = row.createCell(2, CellType.STRING);
                            cell.setCellValue(khoaHoc.getMo_ta());
                            
                            cell = row.createCell(3, CellType.STRING);
                            cell.setCellValue(khoaHoc.getNgay_bat_dau().toString());
                            
                            cell = row.createCell(4, CellType.STRING);
                            cell.setCellValue(khoaHoc.getNgay_ket_thuc().toString());
                            
                            cell = row.createCell(5, CellType.STRING);
                            cell.setCellValue(khoaHoc.isTinh_trang()? "On" : "Off");
                        }   
                        // save file here
                        File f = new File("C:\\Users\\PC\\OneDrive\\Tài liệu\\NetBeansProjects\\QuanLyHocVien\\excel\\khoa_hoc.xlsx");
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
