import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class MovieInfoApp {

    private static final String API_KEY = "a6284f2d";

    public static void main(String[] args) {
        // Check if we're in a headless environment
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("This environment doesn't support GUI. Exiting...");
            return;  // Exit the program if it's a headless environment
        }

        // If not headless, proceed with GUI creation
        SwingUtilities.invokeLater(() -> new MovieInfoApp().createGUI());
    }

    public void createGUI() {
        try {
            JFrame frame = new JFrame("Movie Info Finder");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JTextField searchField = new JTextField(20);
            JButton searchButton = new JButton("Search");
            JPanel searchPanel = new JPanel();
            searchPanel.add(new JLabel("Enter Movie Name: "));
            searchPanel.add(searchField);
            searchPanel.add(searchButton);

            JTabbedPane tabbedPane = new JTabbedPane();
            JTextArea infoArea = new JTextArea();
            JTextArea plotArea = new JTextArea();
            JTextArea ratingsArea = new JTextArea();

            infoArea.setEditable(false);
            plotArea.setEditable(false);
            ratingsArea.setEditable(false);

            tabbedPane.addTab("Details", new JScrollPane(infoArea));
            tabbedPane.addTab("Plot", new JScrollPane(plotArea));
            tabbedPane.addTab("Ratings", new JScrollPane(ratingsArea));

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String movieName = searchField.getText().trim();
                    if (!movieName.isEmpty()) {
                        String jsonResponse = fetchMovieData(movieName);
                        if (jsonResponse != null) {
                            parseAndDisplay(jsonResponse, infoArea, plotArea, ratingsArea);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Movie not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            panel.add(searchPanel, BorderLayout.NORTH);
            panel.add(tabbedPane, BorderLayout.CENTER);

            frame.add(panel);
            frame.setVisible(true);

        } catch (HeadlessException e) {
            System.out.println("HeadlessException: No GUI support in this environment.");
            e.printStackTrace();
        }
    }

    private String fetchMovieData(String movieName) {
        try {
            String formattedName = movieName.replace(" ", "+");
            String apiUrl = "http://www.omdbapi.com/?t=" + formattedName + "&apikey=" + API_KEY;

            URI uri = new URI(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder jsonResult = new StringBuilder();
            while (scanner.hasNext()) {
                jsonResult.append(scanner.nextLine());
            }
            scanner.close();

            return jsonResult.toString();
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseAndDisplay(String jsonResponse, JTextArea infoArea, JTextArea plotArea, JTextArea ratingsArea) {
        if (jsonResponse.contains("\"Response\":\"False\"")) {
            infoArea.setText("Movie not found.");
            plotArea.setText("");
            ratingsArea.setText("");
            return;
        }

        String title = extractValue(jsonResponse, "Title");
        String year = extractValue(jsonResponse, "Year");
        String genre = extractValue(jsonResponse, "Genre");
        String director = extractValue(jsonResponse, "Director");
        String actors = extractValue(jsonResponse, "Actors");
        String plot = extractValue(jsonResponse, "Plot");

        String details = "Title: " + title + "\n" +
                         "Year: " + year + "\n" +
                         "Genre: " + genre + "\n" +
                         "Director: " + director + "\n" +
                         "Actors: " + actors;

        infoArea.setText(details);
        plotArea.setText(plot);
        ratingsArea.setText("Ratings data not available in this version.");
    }

    private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return "N/A";
        startIndex += searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
