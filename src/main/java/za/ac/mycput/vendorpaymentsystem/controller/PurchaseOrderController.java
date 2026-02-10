package za.ac.mycput.vendorpaymentsystem.controller;

import za.ac.mycput.vendorpaymentsystem.dto.PurchaseOrderRequest;
import za.ac.mycput.vendorpaymentsystem.model.PurchaseOrder;
import za.ac.mycput.vendorpaymentsystem.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService poService;

    @PostMapping 
    public ResponseEntity<PurchaseOrder> createPO(@RequestBody PurchaseOrderRequest request) {

        PurchaseOrder newPo = poService.createPO(
                request.vendorId(),
                request.poNumber(),
                request.amount(),
                request.validUntil()
        );

        return ResponseEntity.ok(newPo);
    }
}