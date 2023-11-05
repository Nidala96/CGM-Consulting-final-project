package it.cgmconsulting.pittau.service;

import it.cgmconsulting.pittau.Entity.Store;
import it.cgmconsulting.pittau.exception.ResourceNotFoundException;
import it.cgmconsulting.pittau.payload.response.CustomerStoreResponse;
import it.cgmconsulting.pittau.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public ResponseEntity<?> getCountCustomers(String storeName)
    {
        //Verifico che lo store com quel nome esista
        Store store = storeRepository.findByStoreName(storeName)
                .orElseThrow(()-> new ResourceNotFoundException("Store", "storeName", storeName));

        //Eseguo la query JPQL
        CustomerStoreResponse customerStoreResponse = storeRepository.countCustomersByStore(store.getStoreId());

        return new ResponseEntity<>(customerStoreResponse, HttpStatus.OK);
    }
}
