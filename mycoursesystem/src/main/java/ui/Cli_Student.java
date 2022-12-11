package ui;

import dataaccess.MyCourseRepository;
import dataaccess.MyStudentRepository;

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
                    System.out.println("Kurseingabe");
                    break;
                case "2":
                    System.out.println("Kurseingabe");
                    break;
                case "3":
                    System.out.println("Kurseingabe");
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


    private void inputError() {
        System.out.println("Bitte nur die Zahlen der Menueauswahl eingeben!");
    }


    private void showMenue() {
        System.out.println("-------------- KURSMANAGEMENT --------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t" + "(3) Kursdetails anzeigen");
        System.out.println("(4) Kursdetails ändern \t (5) Kurs löschen \t" + " (6) Kurssuche: (Name und Beschreibung)");
        System.out.println("(7) Laufende Kurse \t (8) Kurssuche: (Name) \t" + " (9) Kurssuche: (Beschreibung)");
        System.out.println("(10) Kurssuche: (Kurstyp) \t (11) Kurssuche: (Startdatum) \t" + " (12) XXX");
        System.out.println("(x) ENDE");
    }

}
