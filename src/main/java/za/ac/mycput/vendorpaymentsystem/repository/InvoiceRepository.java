package za.ac.mycput.vendorpaymentsystem.repository;

import za.ac.mycput.vendorpaymentsystem.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByVendorIdAndInvoiceNumber(Long vendorId, String invoiceNumber);

    List<Invoice> findByPurchaseOrderId(Long poId);

    List<Invoice> findByVendorId(Long vendorId);
}