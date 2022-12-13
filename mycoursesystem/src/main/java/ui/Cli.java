package ui;

import java.util.Scanner;

public class Cli {

    Scanner scan;
    Cli_Student cli_student;
    Cli_Course cli_course;

    public Cli(Cli_Student cli_student, Cli_Course cli_course) {
        this.scan = new Scanner(System.in);
        this.cli_student = cli_student;
        this.cli_course = cli_course;
    }

    public void start() {
        String input = "-";
        while (!input.equalsIgnoreCase("x")) {
            showMenue();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    startStudent();
                    break;
                case "2":
                    startCourse();
                    break;
                case "x":
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    inputError();
                    break;
            }
        }
        scan.close();
    }

    private void startCourse() {
        this.cli_course.start();
    }

    private void startStudent() {
        this.cli_student.start();
    }

    private void inputError() {
        System.out.println("Bitte nur die Zahlen der Menueauswahl eingeben!");
    }


    private void showMenue() {
        System.out.println("-------------- MANAGEMENT --------------");
        System.out.println("(1) Studenten bearbeiten \t (2) Kurse bearbeiten");
        System.out.println("(x) ENDE");
    }
}
