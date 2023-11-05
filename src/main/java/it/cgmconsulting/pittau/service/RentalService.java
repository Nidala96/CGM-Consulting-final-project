package it.cgmconsulting.pittau.service;

import it.cgmconsulting.pittau.Entity.*;
import it.cgmconsulting.pittau.exception.ResourceNotFoundException;
import it.cgmconsulting.pittau.payload.request.AddUpdateRentalRequest;
import it.cgmconsulting.pittau.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final StoreRepository storeRepository;
    private final FilmRepository filmRepository;
    private final CustomerRepository customerRepository;

    public ResponseEntity<?> countRentalsInDateRangeByStore(long storeId, LocalDate start, LocalDate end) {
        long rentsCount = rentalRepository.getRentalsBetween(storeId,start,end);
        return new ResponseEntity(rentsCount,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> addUpdateRental(AddUpdateRentalRequest addUpdateRentalRequest) {

        Store store = storeRepository.findById(addUpdateRentalRequest.getStoreId()).orElseThrow(() -> new ResourceNotFoundException("Store", "Id",addUpdateRentalRequest.getStoreId()));
        Film film = filmRepository.findById(addUpdateRentalRequest.getFilmId()).orElseThrow(() -> new ResourceNotFoundException("Film", "Id",addUpdateRentalRequest.getFilmId()));
        Customer customer = customerRepository.findById(addUpdateRentalRequest.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Film", "Id",addUpdateRentalRequest.getFilmId()));
        // Se trovo una rental ancora in corso di quel customer, film e store mi aggiorna la RentalReturn Date
        Rental rental = rentalRepository.findOngoingRentalByFilmCustomerStore(film.getFilmId(), customer.getCustomerId(),store.getStoreId(), LocalDateTime.now());
        if(rental != null) {
            rental.setRentalReturn(LocalDateTime.now());
            return new ResponseEntity<>("Rent ongoing found, updated rental return date", HttpStatus.OK);
        }
        // Se non lo trova cerca se ci sono copie disponibile in quel negozio e se ne trova almeno uno inserisce un rental di quel film e customer_id
        List<Inventory> listaCopieDisponibili = rentalRepository.findAvailableNotRentedCopiesByFilmAndStore(film.getFilmId(),store.getStoreId());
        // se la lista non e' vuota entra nell'if
        if(!listaCopieDisponibili.isEmpty()) {
            Rental rentalFinale = new Rental(new RentalId(customer, listaCopieDisponibili.get(0), LocalDateTime.now()), null);
            rentalRepository.save(rentalFinale);
            return new ResponseEntity<>("New rental added", HttpStatus.OK);
        }
        return new ResponseEntity<>("No film available", HttpStatus.OK);
    }
}
