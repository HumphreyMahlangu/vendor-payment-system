package za.ac.mycput.vendorpaymentsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public record PurchaseOrderRequest(
        Long vendorId,
        String poNumber,
        BigDecimal amount,
        LocalDate validUntil
) {

}