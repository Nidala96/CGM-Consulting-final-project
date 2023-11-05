package it.cgmconsulting.pittau.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmMaxRentResponse {

    private Long film_id;

    private String title;

    private Long rentNumber;

    public FilmMaxRentResponse(Long film_id, String title, Long rentNumber) {
        this.film_id = film_id;
        this.title = title;
        this.rentNumber = rentNumber;
    }
}
