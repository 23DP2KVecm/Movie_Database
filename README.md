# MovieDatabase Application

### To run the code open Visual Studio Code press File in the top left, Open Folder, and choose the folder containing the project.
### Then download Microsofts Extension Pack for Java and in the top left press Run and then Start Debugging.

### A simple Java console application to manage a personal movie collection. It supports adding, removing, searching, sorting, and viewing movies, as well as fetching movie information from the OMDb API.

## Features
### Add new movies manually or fetch from the internet (OMDb API)

### Display all movies in a formatted table

### Search movies by title (case-insensitive)

### Sort movies by release year (ascending or descending)

### Remove movies by title

### Persistent storage using movies.txt

# User Manual
## 1. Add a Movie
### Adds a movie to your collection with its title, director, year, and genre. The movie is saved to movies.txt and confirmed with a success message.

## 2.Display All Movies
### Prints your entire movie collection in a formatted table. If no movies are present, the app notifies you.

## 3. Search Movies by Title
### Search your collection by providing part or all of a movie title. Matching results are shown in a formatted table.

## 4./5. Sort Movies by Year
### You can sort your movie list:

### In ascending order (oldest to newest)

### In descending order (newest to oldest)

## 6. Fetch Movie from OMDb API
### Enter the movie title, and the app will retrieve data from the OMDb API. You will be prompted to confirm whether to add the fetched movie to your list.

## 7. Remove Movie by Title
### Remove a movie by specifying its title. If found, it is deleted from the list and the storage file is updated.

## OMDb API
### This application fetches online movie data from the OMDb API.

### API key used: ca9f8105 

### Note: For production or public use, store your API key securely and make users use their own keys

## Console Colors
### The application uses a ConsoleColors utility class to display color-coded messages in the terminal, improving readability and user experience.

## Support
### This is a simple educational/demo project. Feel free to modify or extend it as needed. Contributions, issues, and suggestions are always welcome.