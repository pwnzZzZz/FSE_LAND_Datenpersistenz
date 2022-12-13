package ui;

import dataaccess.DatabaseException;
import dataaccess.MyCourseRepository;
import dataaccess.MyStudentRepository;
import domain.Course;
import domain.CourseType;
import domain.InvalidValueException;
import domain.Student;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Cli_Student {
    Scanner scan;
    MyStudentRepository repo;

    public Cli_Student(MyStudentRepository repo){
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start() {
        String input = "-";
        while (!input.equalsIgnoreCase("x")) {
            showMenue();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    showAllStudents();
                    break;
                case "3":
                    showStudentDetails();
                    break;
                case "4":
                    updateStudents();
                    break;
                case "5":
                    deleteStudents();
                    break;
                case "6":
                    searchStudentByFirstName();
                    break;
                case "7":
                    searchStudentByLastName();
                    break;
                case "8":
                    searchStudentByBirthYear();
                    break;
                case "x":
                    break;
                default:
                    inputError();
                    break;
            }
        }
    }

    private void searchStudentByFirstName() {
        System.out.println("Geben Sie den Vornamen des zu suchenden Studenten ein: ");
        String searchString = scan.nextLine();
        List<Student> studentList;
        try{
            studentList = repo.findAllStudentsByFirstName(searchString);
            for(Student student : studentList){
                System.out.println(student);
            }
        }catch(DatabaseException databaseException){
            System.out.println("Datenbankfehler bei der Studentensuche: " + databaseException.getMessage());
        }catch(Exception exception){
            System.out.println("Unbekannter Fehler bei der Studentensuche: " + exception.getMessage());
        }
    }

    private void searchStudentByLastName() {
    }

    private void searchStudentByBirthYear() {
    }

    private void deleteStudents() {
        System.out.println("Welchen Studenten möchten Sie löschen? Bitte ID eingeben: ");
        Long courseIdToDelete = Long.parseLong(scan.nextLine());

        try{
            repo.deleteById(courseIdToDelete);
        }catch(DatabaseException databaseException){
            System.out.println("Datenbankfehler beim Löschen: " + databaseException.getMessage());
        }catch(Exception e){
            System.out.println("Unbekannter Fehler beim Löschen: " + e.getMessage());
        }
    }

    private void showStudentDetails() {
        System.out.println("Für welchen Studenten möchten Sie die Kursdetails anzeigen?");
        try {
            Long studentId = Long.parseLong(scan.nextLine());
            Optional<Student> studentOptional = repo.getById(studentId);
            if (studentOptional.isPresent()) {
                System.out.println(studentOptional.get());
            } else {
                System.out.println("Kurs mit der ID " + studentId + " nicht gefunden!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Studenten-Detailanzeige: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Studenten-Detailanzeige: " + exception.getMessage());
        }
    }

    private void updateStudents() {
        System.out.println("Für welche Studenten-ID möchten Sie die Details ändern?");
        Long studentId = Long.parseLong(scan.nextLine());

        try {
            Optional<Student> studentOptional = repo.getById(studentId);
            if (studentOptional.isEmpty()) {
                System.out.println("Student mit der gegebenen ID nicht in der Datenbank!");
            } else {
                Student student = studentOptional.get();

                System.out.println("Änderungen für folgenden Studenten: ");
                System.out.println(student);

                String vorname, nachname, geburtsdatum;

                System.out.println("Bitte neue Studentendetails angeben (Enter, falls keine Änderung gewünscht ist): ");
                System.out.println("Vorname: ");
                vorname = scan.nextLine();
                System.out.println("Nachname: ");
                nachname = scan.nextLine();
                System.out.println("Geburtsdatum (YYYY-MM-DD): ");
                geburtsdatum = scan.nextLine();

                Optional<Student> studentOptional1 = repo.update(
                        new Student(
                                student.getId(),
                                vorname.equals("") ? student.getVorname() : vorname,
                                nachname.equals("") ? student.getVorname() : nachname,
                                geburtsdatum.equals("") ? student.getGeburtsdatum() : Date.valueOf(geburtsdatum)
                        )
                );

                studentOptional1.ifPresentOrElse(
                        (c) -> System.out.println("Student aktualisiert: " + c),
                        () -> System.out.println("Student konnte nicht aktualisiert werden!")
                );


            }
        }catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Studentendaten nicht korrekt angegeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void showAllStudents() {
        List<Student> list = null;

        try {

            list = repo.getAll();
            if (list.size() > 0) {
                for (Student student : list) {
                    System.out.println(student);
                }
            } else {
                System.out.println("Studentenliste leer!");
            }

            //Unchecked (runtime) exceptions
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Anzeige aller Studenten: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Anzeige aller Studenten: " + exception.getMessage());
        }
    }

    private void addStudent() {
        String vorname, nachname;
        Date geburtsdatum;

        try {
            System.out.println("Bitte alle Studentendaten angeben: ");
            System.out.println("Vorname: ");
            vorname = scan.nextLine();
            if (vorname.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Nachname: ");
            nachname = scan.nextLine();
            if (nachname.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Geburtsdatum (YYYY-MM-DD): ");
            geburtsdatum = Date.valueOf(scan.nextLine());

            Optional<Student> studentOptional = repo.insert(
                    new Student(vorname, nachname, geburtsdatum));
            if (studentOptional.isPresent()) {
                System.out.println("Student angelegt: " + studentOptional.get());
            } else {
                System.out.println("Student konnte nicht angeleget werden!");
            }

        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Studentendaten nicht korrekt angegeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }


    private void inputError() {
        System.out.println("Bitte nur die Zahlen der Menueauswahl eingeben!");
    }


    private void showMenue() {
        System.out.println("-------------- STUDENTENMANAGEMENT --------------");
        System.out.println("(1) Student eingeben \t (2) Alle Studenten anzeigen \t" + "(3) Studentendetails anzeigen");
        System.out.println("(4) Studentendetails ändern \t (5) Studenten löschen \t" + " (6) Studentensuche: (Vorname)");
        System.out.println("(7) Studentensuche: (Nachname) \t (8) Studentensuche: (Geburtsjahr)");
        System.out.println("(x) ENDE");
    }

}
