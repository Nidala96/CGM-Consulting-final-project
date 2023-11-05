package it.cgmconsulting.pittau.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class FilmStaffId implements Serializable
{
    @ManyToOne
    @JoinColumn(name="film_id", nullable = false)
    private Film filmId;

    @ManyToOne
    @JoinColumn(name="staff_id", nullable = false)
    private Staff staffId;

    @ManyToOne
    @JoinColumn(name="role_id", nullable = false)
    private Role roleId;
}
