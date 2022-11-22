/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import view.DangNhapJDialog;

/**
 *
 * @author PC
 */
public class Main {
    public static javax.swing.JFrame mainFrame = null;
    public static void main(String[] args) {
        DangNhapJDialog dialog =new DangNhapJDialog(null, true);
        dialog.setTitle("Đăng nhập hệ thống");
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
