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
        System.out.println(ConsoleColors.GREEN + "Filma pievienota!" + ConsoleColors.RESET);
    }

    public void displayAll() {
        if (movies.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Nav pievienotu filmu." + ConsoleColors.RESET);
            return;
        }
        for (Movie m : movies) {
            System.out.println(ConsoleColors.CYAN_BOLD + m + ConsoleColors.RESET);
        }
    }

    public void searchByTitle(String title) {
        boolean found = false;
        for (Movie m : movies) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println(ConsoleColors.CYAN_BOLD + m + ConsoleColors.RESET);
                found = true;
            }
        }
        if (!found) {
            System.out.println(ConsoleColors.RED + "Filma netika atrasta." + ConsoleColors.RESET);
        }
    }

    public void sortByYear() {
        movies.sort(Comparator.comparingInt(Movie::getYear));
        System.out.println(ConsoleColors.GREEN + "Filmas sakārtotas pēc gada!" + ConsoleColors.RESET);
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
                System.out.println(ConsoleColors.BLUE_BOLD + "\n=== Filmas informācija ===" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW + "Nosaukums: " + ConsoleColors.RESET + json.optString("Title", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "Gads: " + ConsoleColors.RESET + json.optString("Year", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "Režisors: " + ConsoleColors.RESET + json.optString("Director", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "Aktieri: " + ConsoleColors.RESET + json.optString("Actors", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "Žanrs: " + ConsoleColors.RESET + json.optString("Genre", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "Ilgums: " + ConsoleColors.RESET + json.optString("Runtime", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "IMDb vērtējums: " + ConsoleColors.RESET + json.optString("imdbRating", "Nav datu"));
                System.out.println(ConsoleColors.YELLOW + "Apraksts: " + ConsoleColors.RESET + json.optString("Plot", "Nav datu"));
            } else {
                System.out.println(ConsoleColors.RED + "Filma netika atrasta." + ConsoleColors.RESET);
            }

        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Kļūda vaicājot OMDb API: " + e.getMessage() + ConsoleColors.RESET);
        }
    }
}