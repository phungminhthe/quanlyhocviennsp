/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import bean.KhoaHocBean;
import bean.LopHocBean;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import service.ThongKeService;
import service.ThongKeSeviceImpl;

/**
 *
 * @author PC
 */
public class QuanLyThongKeController {
    private ThongKeService thongKeService = null;
    
    public  QuanLyThongKeController(){
        thongKeService = new ThongKeSeviceImpl();
    }
    
    public void setDateToChart1(JPanel jpnItem){
        List<LopHocBean> listItem = thongKeService.getListByLopHoc();
        if(listItem != null){   
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for(LopHocBean item : listItem){
                dataset.addValue(item.getSo_luong_hoc_vien(), "Học Viên", item.getNgay_dang_ky());
            }
            JFreeChart chart = ChartFactory.createBarChart("THỐNG KÊ SỐ LƯỢNG HỌC VIÊN ĐĂNG KÝ",
                    "Thời gian", "Số lượng", dataset);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 300));
            
            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }
    public void setDataToChart2(JPanel jpnItem){
        List<KhoaHocBean> listItem = thongKeService.getListByKhoaHoc();
        if(listItem != null){
            TaskSeriesCollection ds = new TaskSeriesCollection();
            TaskSeries taskSeries;
            Task task;
            for(KhoaHocBean item : listItem){
                taskSeries = new TaskSeries(item.getTen_khoa_hoc());
                task = new Task(item.getTen_khoa_hoc(), item.getNgay_bat_dau(), item.getNgay_ket_thuc());
                taskSeries.add(task);
                ds.add(taskSeries);
            }
            JFreeChart chart = ChartFactory.createGanttChart("THỐNG KÊ TÌNH TRẠNG KHÓA HỌC",
                    "Khóa Học", "Thời Gian", ds);
           ChartPanel chartPanel = new ChartPanel(chart);
           chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 300));
           jpnItem.removeAll();
           jpnItem.setLayout(new CardLayout());
           jpnItem.add(chartPanel);
           jpnItem.validate();
           jpnItem.repaint();
        }
    }
}
