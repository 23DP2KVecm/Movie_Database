package Projekts;
import java.util.Scanner;

import Projekts.lv.rvt.tools.ConsoleColors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieDatabase db = new MovieDatabase();

        while (true) {
            printMovieMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String title = scanner.nextLine();
                    System.out.print("Director: ");
                    String director = scanner.nextLine();
                    System.out.print("Year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    db.addMovie(new Movie(title, director, year, genre));
                    break;
                case 2:
                    db.displayAll();
                    break;
                case 3:
                    System.out.print("Input the name of the movie: ");
                    String search = scanner.nextLine();
                    db.searchByTitle(search);
                    break;
                case 4:
                    db.sortByYear();
                    break;
                case 5:
                    System.out.print("Movies name (Search on the internet): ");
                    String apiQuery = scanner.nextLine();
                    db.fetchMovieFromAPI(apiQuery);
                    break;
                case 6:
                    System.out.println("Programm ending.");
                    return;
                
                default:
                    System.out.println("Wrong input, please try again.");
            }
        }
    }
    public static void printMovieMenu() {
        System.out.println(ConsoleColors.YELLOW_BOLD + "FILM INFORMATION SYSTEM" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "+----+------------------------------------------+");
        System.out.println("| Nr | Action                                   |");
        System.out.println("+----+------------------------------------------+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN +
            "| 1  | Add Movie                                |\n" +
            "+----+------------------------------------------+\n" +
            "| 2  | Look at all of the added movies          |\n" +
            "+----+------------------------------------------+\n" +
            "| 3  | Search by name                           |\n" +
            "+----+------------------------------------------+\n" +
            "| 4  | Order by year ascending                  |\n" +
            "+----+------------------------------------------+\n" +
            "| 5  | Search for a movie on the internet       |\n" +
            "+----+------------------------------------------+\n" +
            "| 6  | Leave                                    |\n" + 
            "+----+------------------------------------------+\n" +ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD + "Choose action: " + ConsoleColors.RESET);
    }
}
