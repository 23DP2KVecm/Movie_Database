package Projekts;
import Projekts.lv.rvt.tools.ConsoleColors;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class MovieDatabase {
    private List<Movie> movies = new ArrayList<>();

    public void addMovie(String title, int year, String director, String genre) {
        Movie movie = new Movie(title, year, director, genre);
        movies.add(movie);
        System.out.println(ConsoleColors.GREEN + "Filma pievienota!" + ConsoleColors.RESET);
    }

    public void listMovies() {
        if (movies.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Nav pievienotu filmu." + ConsoleColors.RESET);
            return;
        }

        for (Movie m : movies) {
            System.out.println(ConsoleColors.CYAN_BOLD + m + ConsoleColors.RESET);
        }
    }

    public void fetchMovieFromAPI(String title) {
        try {
            String apiKey = "TAVA_API_ATSLEGA"; // ← Ievieto savu API atslēgu šeit
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

            if (json.getString("Response").equals("True")) {
                System.out.println(ConsoleColors.BLUE_BOLD + "\n=== Filmas informācija ===" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW + "Nosaukums: " + ConsoleColors.RESET + json.getString("Title"));
                System.out.println(ConsoleColors.YELLOW + "Gads: " + ConsoleColors.RESET + json.getString("Year"));
                System.out.println(ConsoleColors.YELLOW + "Režisors: " + ConsoleColors.RESET + json.getString("Director"));
                System.out.println(ConsoleColors.YELLOW + "Aktieri: " + ConsoleColors.RESET + json.getString("Actors"));
                System.out.println(ConsoleColors.YELLOW + "Žanrs: " + ConsoleColors.RESET + json.getString("Genre"));
                System.out.println(ConsoleColors.YELLOW + "Ilgums: " + ConsoleColors.RESET + json.getString("Runtime"));
                System.out.println(ConsoleColors.YELLOW + "IMDb vērtējums: " + ConsoleColors.RESET + json.getString("imdbRating"));
                System.out.println(ConsoleColors.YELLOW + "Apraksts: " + ConsoleColors.RESET + json.getString("Plot"));
            } else {
                System.out.println(ConsoleColors.RED + "Filma netika atrasta." + ConsoleColors.RESET);
            }

        } catch (Exception e) {
            System.out.println(ConsoleColors.RED + "Kļūda vaicājot OMDb API: " + e.getMessage() + ConsoleColors.RESET);
        }
    }
}
