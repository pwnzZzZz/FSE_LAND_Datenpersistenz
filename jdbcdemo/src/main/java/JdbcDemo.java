import java.sql.*;

public class JdbcDemo {

    public static void main(String[] args) {
        System.out.println("JDBC DEMO!");

        selectAllDemo();
        insertStudentDemo("Name des Studenten", "email@prov.at");
        selectAllDemo();
        updateStudentDemo(4, "Neuer Name", "neueemail@provider.at");
        selectAllDemo();
        deleteStudentDemo(9);
        findAllByNameLike("zeck");
    }

    public static void findAllByNameLike(String pattern){
        System.out.println("Find all by Name DEMO mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try(Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)){
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `student` WHERE `student`.`name` LIKE ?");
            preparedStatement.setString(1,"%" + pattern + "%");
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

    public static void deleteStudentDemo(int studentID){
        System.out.println("Delete DEMO mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try(Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)){
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM `student` WHERE `student`.`id` = ?"
            );
            try{
                preparedStatement.setInt(1, studentID);
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println("Anzahl der gelöschten Datensätze: " + rowAffected);
            } catch (SQLException ex){
                System.out.println("Fehler im SQL-DELETE Statement: " + ex.getMessage());
            }

        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void updateStudentDemo(int id, String neuerName, String neueEmail){
        System.out.println("Update DEMO mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try(Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)){
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `student` SET `name` = ?, `email` = ?  WHERE `student`.`id` = ?"
            );
            try{
                preparedStatement.setString(1, neuerName);
                preparedStatement.setString(2, neueEmail);
                preparedStatement.setInt(3, id);
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Anzahl der aktualisierten Datensätze: " + affectedRows);
            } catch (SQLException ex){
                System.out.println("Fehler im SQL-UPDATE Statement: " + ex.getMessage());
            }

        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void insertStudentDemo(String name, String email){
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
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
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
