package it.cgmconsulting.pittau.repository;

import it.cgmconsulting.pittau.Entity.Inventory;
import it.cgmconsulting.pittau.Entity.Rental;
import it.cgmconsulting.pittau.Entity.RentalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface RentalRepository extends JpaRepository<Rental, RentalId> {

    // Trova i rental fra due date
    @Query(value = "SELECT COUNT(r.rentalId.inventoryId.inventoryId) " +
            "FROM Rental r  " +
            "JOIN r.rentalId.inventoryId i  " +
            "JOIN i.storeId s  " +
            "WHERE s.storeId = :storeId  " +
            "AND DATE(r.rentalId.rentalDate) BETWEEN :dateStart AND :dateEnd")
    long getRentalsBetween(long storeId, LocalDate dateStart, LocalDate dateEnd);

    // Questa query cerca un Rental in corso di un detterminato film, customer e store, cerca quelli con la rentalReturn con valore null
    // Ho messo un oggetto Rental al posto di una lista di Rental pensando che un customer possa avere solo un noleggio in corso dello stesso film nello stesso store
    @Query("SELECT r FROM Rental r " +
            "WHERE r.rentalId.customerId.customerId = :customerId " +
            "AND r.rentalId.inventoryId.storeId.storeId = :storeId " +
            "AND r.rentalReturn IS NULL " +
            "AND r.rentalId.inventoryId.filmId.filmId = :filmId " +
            "AND r.rentalId.rentalDate <= :now " +
            "ORDER BY r.rentalId.rentalDate DESC")
    Rental findOngoingRentalByFilmCustomerStore(Long filmId, Long customerId, Long storeId, LocalDateTime now);

    // Questa query restituisce tutti gli inventory che non sono attualmente in noleggio cioe' senza un collegamento alla tabella rental o che hanno la rentalReturn non settata a null con un detterminato film e store
    @Query("SELECT i FROM Inventory i " +
            "WHERE i.filmId.filmId = :filmId " +
            "AND i.storeId.storeId = :storeId " +
            "AND (i NOT IN (SELECT r.rentalId.inventoryId FROM Rental r) " +
            "OR (SELECT COUNT(r) FROM Rental r " +
            "      WHERE r.rentalId.inventoryId = i " +
            "      AND r.rentalReturn is null) = 0)")
    List<Inventory> findAvailableNotRentedCopiesByFilmAndStore(Long filmId,Long storeId);





}
