package it.cgmconsulting.pittau.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class FilmResponse {

    private long filmId;

    private String title;

    private String Description;

    private short releaseYear;

    private String languageName;

    public FilmResponse(long filmId, String title, String description, short releaseYear, String languageName) {
        this.filmId = filmId;
        this.title = title;
        Description = description;
        this.releaseYear = releaseYear;
        this.languageName = languageName;
    }
}
