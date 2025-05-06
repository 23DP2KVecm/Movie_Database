package Projekts;

import Projekts.lv.rvt.tools.ConsoleColors;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import org.json.JSONObject;

public class MovieDatabase {
    private List<Movie> movies = new ArrayList<>();
    private static final String FILE_NAME = "movies.txt";

    public MovieDatabase() {
        loadFromFile();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        saveToFile();
        System.out.println(ConsoleColors.GREEN + "Film added!" + ConsoleColors.RESET);
    }

    public void sortByYearDescending() {
        movies.sort((m1, m2) -> Integer.compare(m2.getYear(), m1.getYear()));
        System.out.println(ConsoleColors.GREEN + "Films in order by year descending!" + ConsoleColors.RESET);
    }
    
    public boolean removeMovieByTitle(String title) {
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Movie m = iterator.next();
            if (m.getTitle().equalsIgnoreCase(title)) {
                iterator.remove();
                saveToFile();
                System.out.println(ConsoleColors.GREEN + "Movie removed!" + ConsoleColors.RESET);
                return true;
            }
        }
        System.out.println(ConsoleColors.RED + "Movie not found." + ConsoleColors.RESET);
        return false;
    }

    private void printTableHeader() {
        System.out.println(ConsoleColors.BLUE + "+------------------------------+--------------------------------+--------+------------------------------------+");
        System.out.println("| Name                         | Director                       | Year   | Genre                              |");
        System.out.println("+------------------------------+--------------------------------+--------+------------------------------------+" + ConsoleColors.RESET);
    }

    private void printMovieRow(Movie m) {
        System.out.println(String.format("| %-28s | %-30s | %-6d | %-34s |",
            m.getTitle(), m.getDirector(), m.getYear(), m.getGenre()));
    }

    public void displayAll() {
        if (movies.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No added movies" + ConsoleColors.RESET);
            return;
        }
        printTableHeader();
        for (Movie m : movies) {
            printMovieRow(m);
        }
        System.out.println(ConsoleColors.BLUE + "+------------------------------+--------------------------------+--------+------------------------------------+" + ConsoleColors.RESET);
    }

    public void searchByTitle(String title) {
        List<Movie> results = new ArrayList<>();
        for (Movie m : movies) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(m);
            }
        }

        if (results.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Film wasn't found." + ConsoleColors.RESET);
        } else {
            printTableHeader();
            for (Movie m : results) {
                printMovieRow(m);
            }
            System.out.println(ConsoleColors.BLUE + "+------------------------------+--------------------------------+--------+------------------------------------+" + ConsoleColors.RESET);
        }
    }

    public void sortByYear() {
        movies.sort(Comparator.comparingInt(Movie::getYear));
        System.out.println(ConsoleColors.GREEN + "Films in order by year ascending!" + ConsoleColors.RESET);
    }

    public void fetchMovieFromAPI(String title) {
        try {
            String apiKey = "ca9f8105";
            String urlTitle = title.replace(" ", "+");
            String urlStr = "https://www.omdbapi.com/?t=" + urlTitle + "&apikey=" + apiKey;
    
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
    
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
    
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
    
            JSONObject json = new JSONObject(response.toString());
    
            if (json.getString("Response").equalsIgnoreCase("True")) {
                System.out.println(ConsoleColors.BLUE_BOLD + "\nFilm info from the internet." + ConsoleColors.RESET);
                printTableHeader();
                System.out.println(String.format("| %-28s | %-30s | %-6s | %-34s |",
                    json.optString("Title", "No data"),
                    json.optString("Director", "No data"),
                    json.optString("Year", "No data"),
                    json.optString("Genre", "No data")));
                System.out.println(ConsoleColors.BLUE + "+------------------------------+--------------------------------+--------+------------------------------------+" + ConsoleColors.RESET);
    
                System.out.println(ConsoleColors.YELLOW + "Actors: " + ConsoleColors.RESET + json.optString("Actors", "No data"));
                System.out.println(ConsoleColors.YELLOW + "Length: " + ConsoleColors.RESET + json.optString("Runtime", "No data"));
                System.out.println(ConsoleColors.YELLOW + "IMDb rating: " + ConsoleColors.RESET + json.optString("imdbRating", "No data"));
                System.out.println(ConsoleColors.YELLOW + "Description: " + ConsoleColors.RESET + json.optString("Plot", "No data"));
    
                Scanner scanner = new Scanner(System.in);
                System.out.print(ConsoleColors.YELLOW + "Do you want to add this movie to your list? (y/n): " + ConsoleColors.RESET);
                String input = scanner.nextLine().trim().toLowerCase();
    
                if (input.equals("y")) {
                    String fetchedTitle = json.optString("Title", "No data");
                    String fetchedDirector = json.optString("Director", "No data");
                    int fetchedYear = Integer.parseInt(json.optString("Year", "0"));
                    String fetchedGenre = json.optString("Genre", "No data");
    
                    Movie movie = new Movie(fetchedTitle, fetchedDirector, fetchedYear, fetchedGenre);
                    addMovie(movie);
    
                    System.out.println(ConsoleColors.CYAN + "What would you like to do next?" + ConsoleColors.RESET);
                    System.out.println("1. Return to main menu");
                    System.out.println("2. View movie list");
                    System.out.print("Your choice: ");
                    String next = scanner.nextLine().trim();
    
                    if (next.equals("2")) {
                        displayAll();
                    }
                }
            } else {
                System.out.println(ConsoleColors.RED + "Movie not found online." + ConsoleColors.RESET);
            }
    
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Error asking OMDb API: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Movie m : movies) {
                writer.println(m.getTitle() + ";" + m.getDirector() + ";" + m.getYear() + ";" + m.getGenre());
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error saving movies to file." + ConsoleColors.RESET);
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(";");
                if (parts.length == 4) {
                    movies.add(new Movie(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]));
                }
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Error reading movie file." + ConsoleColors.RESET);
        }
    }
}
