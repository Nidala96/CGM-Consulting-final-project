package it.cgmconsulting.pittau.repository;

import it.cgmconsulting.pittau.Entity.Film;
import it.cgmconsulting.pittau.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.pittau.payload.response.FilmRentResponse;
import it.cgmconsulting.pittau.payload.response.FilmRentableResponse;
import it.cgmconsulting.pittau.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT NEW it.cgmconsulting.pittau.payload.response.FilmResponse" +
            "(f.filmId, f.title, f.description, f.releaseYear, f.languageId.languageName) " +
            "FROM Film f " +
            "WHERE f.languageId.languageId = :languageId")
    List<FilmResponse> findFilmsByLanguage(long languageId);

    // Passo un set di id di attori, per prenderli tutti ho usato l'having count per contarmi tutti gli id degli attori
    // Controllo che il roleName is actor per evitare che mi prenda altri ruoli diversi da esso
    @Query("SELECT NEW it.cgmconsulting.pittau.payload.response.FilmResponse" +
            "(f.filmId, f.title, f.description, f.releaseYear, f.languageId.languageName) " +
            "FROM Film f " +
            "WHERE f.filmId IN (" +
            "   SELECT fs.filmStaffId.filmId " +
            "   FROM FilmStaff fs " +
            "   WHERE fs.filmStaffId.staffId.staffId IN :actorsListId " +
            "   AND fs.filmStaffId.roleId.roleName = 'actor' " +
            "   GROUP BY fs.filmStaffId.filmId " +
            "   HAVING COUNT(DISTINCT fs.filmStaffId.staffId.staffId) = :actorCount" +
            ") " +
            "ORDER BY f.title")
    List<FilmResponse> findFilmsByActors(Set<Long> actorsListId, int actorCount);

    //Creo una query per ricavarmi le copie in possesso del negozio
    @Query("SELECT NEW it.cgmconsulting.pittau.payload.response.FilmRentableResponse(" +
            "f.title, " +
            "s.storeName, " +
            "COUNT(i.inventoryId)) " +
            "FROM Film f " +
            "LEFT JOIN Inventory i ON f.filmId = i.filmId.filmId " +
            "LEFT JOIN Store s ON i.storeId.storeId = s.storeId " +
            "WHERE f.title = :title " +
            "GROUP BY f.title, s.storeName")
    List<FilmRentableResponse> findFilmInStore(String title);

    // Questa query conta le copie di quel film e negozio e la subQuery con il sum conta le copie disponibili
    @Query("SELECT NEW it.cgmconsulting.pittau.payload.response.FilmRentableResponse(i.filmId.title, i.storeId.storeName, " +
            "COUNT(i), " +
            // La subquery conta i rental in cui il film specificato è noleggiato
            // e non e' ancora stato restituito rental return is null
            "SUM(CASE WHEN (SELECT COUNT(r) FROM Rental r " +
            "WHERE r.rentalId.inventoryId = i " +
            "AND r.rentalReturn is null) = 0 THEN 1 ELSE 0 END)) " +
            // se la coppia e' disponibile assegna il valore 1
            // Altrimenti, assegna 0 se il film non e' disponibile
            "FROM Inventory i " +
            "WHERE i.filmId.filmId = :filmId " +
            "GROUP BY i.filmId.title, i.storeId.storeName")
    List<FilmRentableResponse> getCopiesRentable(long filmId);

    Film findByTitle(String title);

    @Query("SELECT NEW it.cgmconsulting.pittau.payload.response.FilmMaxRentResponse(f.filmId, f.title, COUNT(r.rentalId)) " +
            "FROM Film f " +
            "JOIN Inventory i ON f = i.filmId " +
            "JOIN Rental r ON i = r.rentalId.inventoryId " +
            "GROUP BY f.filmId, f.title " +
            "ORDER BY COUNT(r.rentalId) DESC")
    List<FilmMaxRentResponse> findFilmWithMaxNumberOfRent();


    @Query("SELECT NEW it.cgmconsulting.pittau.payload.response.FilmRentResponse(f.filmId, f.title, s.storeName) " +
            "FROM Customer c " +
            "JOIN Rental r ON c = r.rentalId.customerId " +
            "JOIN Inventory i ON r.rentalId.inventoryId = i " +
            "JOIN Film f ON i.filmId = f " +
            "JOIN Store s ON i.storeId = s " +
            "WHERE c.customerId = :customerId")
    List<FilmRentResponse> findAllFilmsRentByOneCustomer(long customerId);




}
