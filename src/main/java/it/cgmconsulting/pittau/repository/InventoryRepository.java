package it.cgmconsulting.pittau.repository;

import it.cgmconsulting.pittau.Entity.Film;
import it.cgmconsulting.pittau.Entity.Inventory;
import it.cgmconsulting.pittau.Entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByFilmIdAndStoreId(Film film, Store store);
}
