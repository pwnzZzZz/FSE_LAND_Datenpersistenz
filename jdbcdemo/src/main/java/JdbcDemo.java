import java.sql.*;

public class JdbcDemo {

    public static void main(String[] args) {
        System.out.println("JDBC DEMO!");

        //INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, 'Marcel Schranz', 'marcel.schranz@myimst.at'), (NULL, 'Sarah Gosch', 'sarah.gosch@myimst.at');

        selectAllDemo();
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

            }

        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }




}
