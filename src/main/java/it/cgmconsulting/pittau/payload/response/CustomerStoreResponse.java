package it.cgmconsulting.pittau.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CustomerStoreResponse {

    private String storeName;
    private long totalCustomers;

    public CustomerStoreResponse(String storeName, long totalCustomers) {
        this.storeName = storeName;
        this.totalCustomers = totalCustomers;
    }
}
