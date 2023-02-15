package de.teamtech.moviereservation;

 
import java.sql.Connection;
import java.sql.DriverManager;
 
public class DBConnection {
 public static Connection createConnection()
 {
     Connection con = null; 
     try 
     {
         try 
         {
            Class.forName("org.postgresql.Driver");
            con = DriverManager
               .getConnection("jdbc:postgresql://localhost:5432/testdb",
               "postgres", "123");
         } 
         catch (ClassNotFoundException e)
         {
            e.printStackTrace();
          }
         System.out.println("Printing connection object "+con);
     } 
     catch (Exception e) 
     {
        e.printStackTrace();
     }
     return con; 
 }
}
