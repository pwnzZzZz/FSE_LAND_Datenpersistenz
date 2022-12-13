import dataaccess.MySqlCourseRepository;
import dataaccess.MySqlStudentRepository;
import ui.Cli;
import ui.Cli_Course;
import ui.Cli_Student;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            Cli_Course cli_course = new Cli_Course(new MySqlCourseRepository());
            Cli_Student cli_student = new Cli_Student(new MySqlStudentRepository());
            Cli cli = new Cli(cli_student, cli_course);
            cli.start();
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage() + " SQL State: " + e.getSQLState());
        } catch (ClassNotFoundException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }

}
