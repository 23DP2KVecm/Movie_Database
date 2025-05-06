package Projekts;

import Projekts.lv.rvt.tools.ConsoleColors;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class MovieDatabase {
    private List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie) {
        movies.add(movie);
        System.out.println(ConsoleColors.GREEN + "Film added!" + ConsoleColors.RESET);
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
        System.out.println(ConsoleColors.BLUE + "+------------------------------+----------------------+--------+-----------------+" + ConsoleColors.RESET);
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
            System.out.println(ConsoleColors.BLUE + "+------------------------------+----------------------+--------+-----------------+" + ConsoleColors.RESET);
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
            
                System.out.println(ConsoleColors.YELLOW + "Actors: " + ConsoleColors.RESET + json.optString("Actors", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "Lenght: " + ConsoleColors.RESET + json.optString("Runtime", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "IMDb rating: " + ConsoleColors.RESET + json.optString("imdbRating", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "Description: " + ConsoleColors.RESET + json.optString("Plot", "Nav datu"));
            }

        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Error asking OMDb API: " + e.getMessage() + ConsoleColors.RESET);
        }
    }
}