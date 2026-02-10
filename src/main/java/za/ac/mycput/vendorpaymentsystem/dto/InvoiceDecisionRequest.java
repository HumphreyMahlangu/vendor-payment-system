package za.ac.mycput.vendorpaymentsystem.dto;

public record InvoiceDecisionRequest(
        String status,
        String comment
) {

}