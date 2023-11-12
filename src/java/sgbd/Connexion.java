/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tafitasoa-P15B-140
 */
public class Connexion {
    public static Connection getConnex() throws Exception{
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/banqueserver", "postgres", "admin");	
            return con;
        }catch (SQLException e) {
            e.getMessage();
        }
	return null;
    }
}
