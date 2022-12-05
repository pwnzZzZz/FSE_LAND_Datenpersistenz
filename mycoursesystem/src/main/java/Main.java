import dataaccess.MysqlDatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            Connection myConnection =
                    MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "");
            System.out.println("Verbindung zur DB erfolgreich aufgebaut");

            //Durch die Singleton-Implementierung wird zur Laufzeit immer die gleiche Connection-Instanz zurückgegeben
            //myConnection = MysqlDatabaseConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
