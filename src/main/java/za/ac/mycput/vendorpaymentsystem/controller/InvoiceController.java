package za.ac.mycput.vendorpaymentsystem.controller;

import za.ac.mycput.vendorpaymentsystem.dto.InvoiceDecisionRequest;
import za.ac.mycput.vendorpaymentsystem.dto.InvoiceRequest;
import za.ac.mycput.vendorpaymentsystem.model.Invoice;
import za.ac.mycput.vendorpaymentsystem.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<Invoice> submitInvoice(@RequestBody InvoiceRequest request) {
        Invoice invoice = invoiceService.submitInvoice(request);
        return ResponseEntity.ok(invoice);
    }

    @PatchMapping("/{id}/decision")
    public ResponseEntity<Invoice> reviewInvoice(
            @PathVariable Long id,
            @RequestBody InvoiceDecisionRequest request) {

        Invoice updatedInvoice = invoiceService.reviewInvoice(id, request);
        return ResponseEntity.ok(updatedInvoice);
    }
}