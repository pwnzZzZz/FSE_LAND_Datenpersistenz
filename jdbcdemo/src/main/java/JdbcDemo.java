import java.sql.*;

public class JdbcDemo {

    public static void main(String[] args) {
        System.out.println("JDBC DEMO!");

        //INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, 'Marcel Schranz', 'marcel.schranz@myimst.at'), (NULL, 'Sarah Gosch', 'sarah.gosch@myimst.at');

        selectAllDemo();
        insertStudentDemo();
        selectAllDemo();
        updateStudentDemo();
        selectAllDemo();
    }

    public static void updateStudentDemo(){
        System.out.println("Update DEMO mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try(Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)){
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `student` SET `name` = ?, `email` = ?  WHERE `student`.`id` = 4"
            );
            try{
                preparedStatement.setString(1, "Hans Zimmer");
                preparedStatement.setString(2, "h.zi@trx.at");
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Anzahl der aktualisierten Datensätze: " + affectedRows);
            } catch (SQLException ex){
                System.out.println("Fehler im SQL-UPDATE Statement: " + ex.getMessage());
            }

        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void insertStudentDemo(){
        System.out.println("Insert DEMO mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try(Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)){
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, ?, ?)"
            );
            try{
                preparedStatement.setString(1, "Peter Zeck");
                preparedStatement.setString(2, "p.zeck@hotmail.com");
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println(rowAffected + "Datensätze eingefügt");
            } catch (SQLException ex){
            System.out.println("Fehler in der SQL-INSERT Statement: " + ex.getMessage());
            }

        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void selectAllDemo(){
        System.out.println("Select DEMO mit JDBC");
        String sqlSelechtAllPersons = "SELECT * FROM `student`";
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try(Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)){
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `student`");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString("name");
                String email = rs.getString("email");

                System.out.println("Student aus der DB: [ID] " + id + " [Name] " + name + " [Email] " + email);


            }

        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }




}
