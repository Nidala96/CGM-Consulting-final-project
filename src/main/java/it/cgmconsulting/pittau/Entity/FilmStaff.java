package it.cgmconsulting.pittau.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "film_staff")
public class FilmStaff
{
    @EmbeddedId
    private FilmStaffId filmStaffId;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmStaff filmStaff = (FilmStaff) o;
        return Objects.equals(filmStaffId, filmStaff.filmStaffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmStaffId);
    }
}
