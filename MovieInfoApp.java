import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class MovieInfoApp {
    private static final String API_KEY = "a6284f2d";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieInfoApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Movie Info App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JTabbedPane tabbedPane = new JTabbedPane();
        JTextArea detailsArea = new JTextArea();
        JTextArea plotArea = new JTextArea();
        JTextArea ratingArea = new JTextArea();

        detailsArea.setEditable(false);
        plotArea.setEditable(false);
        ratingArea.setEditable(false);

        tabbedPane.addTab("Details", new JScrollPane(detailsArea));
        tabbedPane.addTab("Plot", new JScrollPane(plotArea));
        tabbedPane.addTab("Ratings", new JScrollPane(ratingArea));

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        frame.add(panel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String movieTitle = searchField.getText().trim();
                if (!movieTitle.isEmpty()) {
                    fetchMovieData(movieTitle, detailsArea, plotArea, ratingArea);
                }
            }
        });

        frame.setVisible(true);
    }

    private static void fetchMovieData(String movieTitle, JTextArea detailsArea, JTextArea plotArea, JTextArea ratingArea) {
        try {
            String apiUrl = "https://www.omdbapi.com/?t=" + movieTitle.replace(" ", "+") + "&apikey=" + API_KEY;
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                
                JSONObject jsonResponse = new JSONObject(response.toString());
                
                if (jsonResponse.has("Title")) {
                    detailsArea.setText("Title: " + jsonResponse.getString("Title") + "\n"
                            + "Year: " + jsonResponse.getString("Year") + "\n"
                            + "Genre: " + jsonResponse.getString("Genre") + "\n"
                            + "Director: " + jsonResponse.getString("Director") + "\n"
                            + "Actors: " + jsonResponse.getString("Actors"));
                    
                    plotArea.setText("Plot: " + jsonResponse.getString("Plot"));
                    ratingArea.setText("Ratings are not available in this version.");
                } else {
                    detailsArea.setText("Movie not found.");
                    plotArea.setText("");
                    ratingArea.setText("");
                }
            } else {
                detailsArea.setText("Error: Unable to fetch data.");
            }
        } catch (IOException e) {
            detailsArea.setText("Error: " + e.getMessage());
        }
    }
}
