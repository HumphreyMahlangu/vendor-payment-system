package za.ac.mycput.vendorpaymentsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceRequest(
        Long vendorId,
        String poNumber,
        String invoiceNumber,
        BigDecimal amount,
        LocalDate dueDate
) {

}