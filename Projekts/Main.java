package Projekts;
import java.util.Scanner;

import Projekts.lv.rvt.tools.ConsoleColors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieDatabase db = new MovieDatabase();

        System.out.println(ConsoleColors.YELLOW_BOLD + "FILMU INFORMĀCIJAS SISTĒMA" + ConsoleColors.RESET);

        while (true) {
            System.out.println(ConsoleColors.BLUE + "\n1. Pievienot filmu\n2. Skatīt visas filmas\n3. Meklēt pēc nosaukuma\n4. Kārtot pēc gada\n5. Iziet\n6. Meklēt filmu internetā" + ConsoleColors.RESET);
            System.out.print("Izvēlies darbību: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Nosaukums: ");
                    String title = scanner.nextLine();
                    System.out.print("Režisors: ");
                    String director = scanner.nextLine();
                    System.out.print("Gads: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Žanrs: ");
                    String genre = scanner.nextLine();
                    db.addMovie(new Movie(title, director, year, genre));
                    break;
                case 2:
                    db.displayAll();
                    break;
                case 3:
                    System.out.print("Ievadi meklējamo nosaukumu: ");
                    String search = scanner.nextLine();
                    db.searchByTitle(search);
                    break;
                case 4:
                    db.sortByYear();
                    break;
                case 5:
                    System.out.println("Programma tiek izbeigta.");
                    return;
                case 6:
                System.out.print("Filmas nosaukums (meklēt internetā): ");
                String apiQuery = scanner.nextLine();
                db.fetchMovieFromAPI(apiQuery);
                break;
                default:
                    System.out.println("Nepareiza izvēle.");
            }
        }
    }
}
