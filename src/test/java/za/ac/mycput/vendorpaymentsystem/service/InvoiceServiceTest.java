package za.ac.mycput.vendorpaymentsystem.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ac.mycput.vendorpaymentsystem.dto.InvoiceDecisionRequest;
import za.ac.mycput.vendorpaymentsystem.model.Invoice;
import za.ac.mycput.vendorpaymentsystem.model.InvoiceStatus;
import za.ac.mycput.vendorpaymentsystem.model.PurchaseOrder;
import za.ac.mycput.vendorpaymentsystem.repository.InvoiceRepository;
import za.ac.mycput.vendorpaymentsystem.repository.PurchaseOrderRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private PurchaseOrderRepository poRepository;

    @InjectMocks // Inject the FAKES into the real Service
    private InvoiceService invoiceService;

    @Test
    void shouldDeductBalance_WhenInvoiceIsApproved() {
        PurchaseOrder po = new PurchaseOrder();
        po.setRemainingBalance(new BigDecimal("1000.00"));

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setAmount(new BigDecimal("200.00"));
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setPurchaseOrder(po); // Link them

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(i -> i.getArguments()[0]);
        InvoiceDecisionRequest request = new InvoiceDecisionRequest("APPROVED", "Looks good");
        Invoice result = invoiceService.reviewInvoice(1L, request);

        assertEquals(InvoiceStatus.APPROVED, result.getStatus());

        assertEquals(new BigDecimal("800.00"), po.getRemainingBalance());
        verify(poRepository, times(1)).save(po);
    }
}