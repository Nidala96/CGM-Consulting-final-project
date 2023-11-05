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
public class Inventory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inventoryId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store storeId;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film filmId;

    public Inventory(Store storeId, Film filmId)
    {
        this.storeId = storeId;
        this.filmId = filmId;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return inventoryId == inventory.inventoryId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(inventoryId);
    }
}
