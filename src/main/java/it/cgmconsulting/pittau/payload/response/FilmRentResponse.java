package it.cgmconsulting.pittau.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FilmRentResponse {

    private long filmId;

    private String title;

    private String storeName;

    public FilmRentResponse(long filmId, String title, String storeName) {
        this.filmId = filmId;
        this.title = title;
        this.storeName = storeName;
    }
}
