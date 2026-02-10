package za.ac.mycput.vendorpaymentsystem.service;

import za.ac.mycput.vendorpaymentsystem.dto.InvoiceRequest;
import za.ac.mycput.vendorpaymentsystem.model.Invoice;
import za.ac.mycput.vendorpaymentsystem.model.InvoiceStatus;
import za.ac.mycput.vendorpaymentsystem.model.PurchaseOrder;
import za.ac.mycput.vendorpaymentsystem.model.Vendor;
import za.ac.mycput.vendorpaymentsystem.repository.InvoiceRepository;
import za.ac.mycput.vendorpaymentsystem.repository.PurchaseOrderRepository;
import za.ac.mycput.vendorpaymentsystem.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PurchaseOrderRepository poRepository;
    private final VendorRepository vendorRepository;

    @Transactional
    public Invoice submitInvoice(InvoiceRequest request) {
        Vendor vendor = vendorRepository.findById(request.vendorId())
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));

        PurchaseOrder po = poRepository.findByPoNumber(request.poNumber())
                .orElseThrow(() -> new IllegalArgumentException("PO not found"));

        if (!po.getVendor().getId().equals(vendor.getId())) {
            throw new IllegalArgumentException("PO " + request.poNumber() + " does not belong to vendor " + vendor.getName());
        }

        if (invoiceRepository.findByVendorIdAndInvoiceNumber(vendor.getId(), request.invoiceNumber()).isPresent()) {
            throw new IllegalArgumentException("Invoice " + request.invoiceNumber() + " already exists for this vendor");
        }

        Invoice invoice = new Invoice();
        invoice.setVendor(vendor);
        invoice.setPurchaseOrder(po);
        invoice.setInvoiceNumber(request.invoiceNumber());
        invoice.setAmount(request.amount());
        invoice.setDueDate(request.dueDate());

        invoice.setStatus(InvoiceStatus.PENDING);

        return invoiceRepository.save(invoice);
    }
}