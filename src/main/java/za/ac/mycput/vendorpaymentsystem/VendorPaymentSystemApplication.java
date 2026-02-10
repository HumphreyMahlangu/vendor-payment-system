package za.ac.mycput.vendorpaymentsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import za.ac.mycput.vendorpaymentsystem.model.Vendor;
import za.ac.mycput.vendorpaymentsystem.repository.VendorRepository;

@SpringBootApplication
public class VendorPaymentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendorPaymentSystemApplication.class, args);
    }
    @Bean
    CommandLineRunner init(VendorRepository repo) {
        return args -> {
            if (repo.count() == 0) { // Only run if DB is empty
                Vendor v = new Vendor();
                v.setName("Microsoft SA");
                v.setEmail("accounts@microsoft.com");
                v.setTaxId("VAT-999");
                repo.save(v);
                System.out.println(" Dummy Vendor Created: ID " + v.getId());
            }
        };
    }

}
