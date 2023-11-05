package it.cgmconsulting.pittau.controller;

import it.cgmconsulting.pittau.payload.request.AddUpdateRentalRequest;
import it.cgmconsulting.pittau.payload.request.FilmRequest;
import it.cgmconsulting.pittau.service.FilmService;
import it.cgmconsulting.pittau.service.RentalService;
import it.cgmconsulting.pittau.service.StoreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
public class ApplicationController {

    private final FilmService filmService;
    private final StoreService storeService;
    private final RentalService rentalService;

    // Nel RequestBody metto l'annotazione @Valid per rispettare i valori della request
    // 1
    @PutMapping("/update-film/{filmId}")
    public ResponseEntity<?> updateFilm(@RequestBody @Valid FilmRequest filmRequest, @PathVariable @Min(1) long filmId){
        return filmService.updateFilm(filmRequest,filmId);
    }

    // 2
    @GetMapping("/find-films-by-language/{languageId}")
    public ResponseEntity<?> findFilmsByLanguage(@PathVariable @Min(1) long languageId) {
        return filmService.findFilmsByLanguage(languageId);
    }

    // 3
    @PostMapping("/add-film-to-store/{storeId}/{filmId}")
    public ResponseEntity<?> addFilmToStore(@PathVariable @Min(1) long storeId, @PathVariable @Min(1) long filmId) {
        return filmService.addFilmToStore(storeId, filmId);
    }

    // 4
    @GetMapping("/count-customers-by-store/{storeName}")
    public ResponseEntity<?> countCustomersByStoreName(@PathVariable @NotBlank String storeName){
        return storeService.getCountCustomers(storeName);
    }

    // 5
    @PutMapping("/add-update-rental")
    public ResponseEntity<?> addUpdateRental(@RequestBody @Valid AddUpdateRentalRequest addUpdateRentalRequest) {
        return rentalService.addUpdateRental(addUpdateRentalRequest);
    }

    // 6
    @GetMapping("/count-rentals-in-date-range-by-store/{storeId}")
    public ResponseEntity<?> countRentalsInDateRangeByStore(@PathVariable  long storeId, @RequestParam LocalDate start,@RequestParam LocalDate end){
        return rentalService.countRentalsInDateRangeByStore(storeId, start, end);
    }

    // 7
    @GetMapping("/find-all-films-rent-by-one-customer/{customerId}")
    public ResponseEntity<?> findAllFilmsRentByOneCustome(@PathVariable @Min(1) long customerId){
        return filmService.findAllFilmsRentByOneCustomer(customerId);
    }

    // 8
    @GetMapping("/find-film-with-max-number-of-rent")
    public ResponseEntity<?> findFilmWithMaxNumberOfRent(){
        return filmService.findFilmWithMaxNumberOfRent();
    }

    // 9
    @GetMapping("/find-films-by-actors")
    public ResponseEntity<?> findFilmsByActors(@RequestParam Set<Long> actorsListId){
        return filmService.findFilmsByActors(actorsListId);
    }

    // 10
    @GetMapping("/find-rentable-films")
    public ResponseEntity<?> getRentableFilms(@RequestParam @NotBlank String title) {
        return filmService.getRentableFilms(title);
    }




}
