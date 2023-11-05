package it.cgmconsulting.pittau.payload.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AddUpdateRentalRequest {
    @Min(1)
    Long filmId;
    @Min(1)
    Long storeId;
    @Min(1)
    Long customerId;
}
