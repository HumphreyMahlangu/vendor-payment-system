package za.ac.mycput.vendorpaymentsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "vendors") // The name of the tables
@Data //Lombok: I ussd this annotation to generate getter, setters etc automatically
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // No two vendors can have the same email.
    @Column(nullable = false, unique = true)
    private String email;

    // Tax ID (e.g. VAT number) must be unique.
    // This prevents the finance team from creating duplicate vendor profiles.
    @Column(name = "tax_id", nullable = false, unique = true)
    private String taxId;

    // Best Practice: Always track when data was added/modified.
    @Column(name = "created_at", updatable = false)
    private String createdAt;
}