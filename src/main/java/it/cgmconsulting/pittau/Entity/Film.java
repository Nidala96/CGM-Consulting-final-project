package it.cgmconsulting.pittau.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Film
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long filmId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private short releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language languageId;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genreId;

    public Film(String title, String description, short releaseYear, Language languageId, Genre genreId)
    {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.languageId = languageId;
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return filmId == film.filmId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(filmId);
    }
}
