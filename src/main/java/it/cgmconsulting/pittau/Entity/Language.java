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
public class Language
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long languageId;

    @Column(length = 20, unique = true, nullable = false)
    private String languageName;

    public Language(String languageName)
    {
        this.languageName = languageName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return languageId == language.languageId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(languageId);
    }
}
