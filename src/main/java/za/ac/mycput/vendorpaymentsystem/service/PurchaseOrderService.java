package za.ac.mycput.vendorpaymentsystem.service;

import za.ac.mycput.vendorpaymentsystem.model.POStatus;
import za.ac.mycput.vendorpaymentsystem.model.PurchaseOrder;
import za.ac.mycput.vendorpaymentsystem.model.Vendor;
import za.ac.mycput.vendorpaymentsystem.repository.PurchaseOrderRepository;
import za.ac.mycput.vendorpaymentsystem.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository poRepository;
    private final VendorRepository vendorRepository;

    @Transactional
    public PurchaseOrder createPO(Long vendorId, String poNumber, BigDecimal amount, LocalDate validUntil) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor ID " + vendorId + " not found"));

        if (poRepository.findByPoNumber(poNumber).isPresent()) {
            throw new IllegalArgumentException("PO Number " + poNumber + " already exists");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("PO Amount must be greater than zero");
        }

        PurchaseOrder po = new PurchaseOrder();
        po.setVendor(vendor);
        po.setPoNumber(poNumber);
        po.setTotalAmount(amount);
        po.setRemainingBalance(amount);
        po.setStatus(POStatus.OPEN);
        po.setValidUntil(validUntil);

        return poRepository.save(po);
    }
}