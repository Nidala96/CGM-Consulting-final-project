package it.cgmconsulting.pittau.service;

import it.cgmconsulting.pittau.Entity.*;
import it.cgmconsulting.pittau.exception.ResourceNotFoundException;
import it.cgmconsulting.pittau.payload.request.FilmRequest;
import it.cgmconsulting.pittau.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.pittau.payload.response.FilmRentResponse;
import it.cgmconsulting.pittau.payload.response.FilmRentableResponse;
import it.cgmconsulting.pittau.payload.response.FilmResponse;
import it.cgmconsulting.pittau.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;
    private final StoreRepository storeRepository;
    private final InventoryRepository inventoryRepository;

    @Transactional
    public ResponseEntity<?> updateFilm(FilmRequest filmRequest,long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("film", "id", filmId));
        film.setTitle(filmRequest.getTitle());
        film.setDescription(filmRequest.getDescription());
        film.setReleaseYear(filmRequest.getReleaseYear());
        // trovo il genere
        Genre genre = genreRepository.findById(filmRequest.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("genre", "id", filmRequest.getGenreId()));
        // cerco la lingua
        Language language = languageRepository.findById(filmRequest.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException("language", "id", filmRequest.getLanguageId()));
        film.setGenreId(genre);
        film.setLanguageId(language);
        return new ResponseEntity<>("Film " + filmId + " updated", HttpStatus.OK);
    }

    public ResponseEntity<?> findFilmsByLanguage(long languageId) {
        List<FilmResponse> listFilms = filmRepository.findFilmsByLanguage(languageId);
        // controllo se la lista e' vuota
        if(listFilms.isEmpty())
            return new ResponseEntity<>("Nessun film trovato", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(listFilms, HttpStatus.OK);
    }

    public ResponseEntity<?> findAllFilmsRentByOneCustomer(long customerId) {
        List<FilmRentResponse> filmRentResponsesList = filmRepository.findAllFilmsRentByOneCustomer(customerId);
        if(filmRentResponsesList.isEmpty())
            return new ResponseEntity<>("No films fount with that customer id", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(filmRentResponsesList, HttpStatus.OK);
    }

    public ResponseEntity<?> addFilmToStore(long storeId, long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new ResourceNotFoundException("film", "id", filmId));
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("store", "id", storeId));
        Inventory inventory = new Inventory(store, film);
        inventoryRepository.save(inventory);
        return new ResponseEntity<>("Film " + film.getTitle() + " added to " + store.getStoreName(), HttpStatus.OK);
    }

    public ResponseEntity<?> findFilmsByActors(Set<Long> actorsListId) {
        // passo la lista di id di attori e la lunghezza della lista di id
        List<FilmResponse> listaFilm = filmRepository.findFilmsByActors(actorsListId, actorsListId.size());
        if(listaFilm.isEmpty())
            return new ResponseEntity("Film non trovati", HttpStatus.NOT_FOUND);
        return new ResponseEntity(listaFilm, HttpStatus.OK);
    }

    public ResponseEntity<?> getRentableFilms(String title) {
        Film film = filmRepository.findByTitle(title);
        List<FilmRentableResponse> filmRentableResponseList = filmRepository.getCopiesRentable(film.getFilmId());
        return new ResponseEntity(filmRentableResponseList, HttpStatus.OK);
    }

    public ResponseEntity<?> findFilmWithMaxNumberOfRent() {
        List<FilmMaxRentResponse> listFilms = filmRepository.findFilmWithMaxNumberOfRent();
        List<FilmMaxRentResponse> listFilmsFinale = new ArrayList<>();
        // Mi prendo il numero di rent maggiore
        long maxNumber = listFilms.get(0).getRentNumber();
        // Ciclo la lista eaggiungo il nella listaFinale
        for(FilmMaxRentResponse film : listFilms) {
            if(film.getRentNumber() == maxNumber)
                listFilmsFinale.add(film);
        }
        return new ResponseEntity(listFilmsFinale, HttpStatus.OK);
    }
}
