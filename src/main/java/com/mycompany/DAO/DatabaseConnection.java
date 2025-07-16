/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
// SQL
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Collection
import java.util.ArrayList;
import java.util.List;

// Swing UI
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

// AWT
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

/**
 *
 * @author vipgl
 */
public class DatabaseConnection {

    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=Coach_Me_AI;encrypt=true;trustServerCertificate=true";
    private static final String user = "sa";  
    private static final String password = "gacon"; 
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
