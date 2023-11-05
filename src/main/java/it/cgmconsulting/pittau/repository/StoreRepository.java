package it.cgmconsulting.pittau.repository;

import it.cgmconsulting.pittau.Entity.Store;
import it.cgmconsulting.pittau.payload.response.CustomerStoreResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreName(String storeName);

    @Query("SELECT NEW it.cgmconsulting.pittau.payload.response.CustomerStoreResponse(" +
            "s.storeName, " +
            "COUNT(DISTINCT c.customerId)) " +
            "FROM Rental r " +
            "JOIN r.rentalId.customerId c " +
            "JOIN r.rentalId.inventoryId i " +
            "JOIN i.storeId s " +
            "WHERE s.storeId = :storeId " +
            "GROUP BY s.storeId")
    CustomerStoreResponse countCustomersByStore(long storeId);
}
