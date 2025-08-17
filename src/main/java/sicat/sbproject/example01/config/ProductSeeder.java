package sicat.sbproject.example01.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import sicat.sbproject.example01.model.Product;
import sicat.sbproject.example01.repository.ProductRepository;

@Component
public class ProductSeeder implements ApplicationRunner {

//	private final ProductRepository productRepository;
//
//	// Constructor injection
//	public ProductSeeder(ProductRepository productRepository) {
//		this.productRepository = productRepository;
//	}

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub

		seedProducts();

	}

	// Data Seeder
	private void seedProducts() {
		// Prepare seed list
		List<Product> seeds = List.of(

				new Product("Selective Pallet Racking System", "Selective Racking System",
						"The most widely used warehouse pallet racking storage system for storing palletized loads. The racking system can be place against the wall (single deep only), and rows of back-to- back on succeeding floor spaces after the wall.",
						200.0, 200.0, 200.0, 100.0),

				new Product("Double Deep Pallet Racking System", "Selective Racking System",
						"The racking can be place against the wall (double deep), and two rows back-to-back on succeeding floor spaces after the wall.",
						200.0, 200.0, 200.0, 100.0),

				new Product("VNA Racking System", "Selective Racking System",
						"VNA racking system almost similar to Selective Racking System, only that the aisle can be reduced to 1.8 meters. This racking system requires a specialized material handling equipment to load and unload the racks. Since the aisles is narrow, guiderails or electronic guidance systems can be fitted to assist the material handling equipment and protect the structural integrity of the racking.",
						200.0, 200.0, 200.0, 100.0),

				new Product("Drive-In/Drive-Thru Racking System", "Selective Racking System",
						"A most preferred racking solution to conventional block stacking where pallets simply can’t be stacked on top of each other. The system involves the forklift entering the racking from one side to load or retrieve the pallets, which are stored more deeply.",
						200.0, 200.0, 200.0, 100.0),

				new Product("Radio Shuttle Racking System", "Selective Racking System",
						"Radio Shuttle Racking System is a semi-automated high-density storage system, which uses an automatic device to move in back and forth products transportation, and forklift pick-up at the end.",
						200.0, 200.0, 200.0, 100.0)

		);

		for (Product p : seeds) {
			// check existence by unique field (name) before inserting
			if (productRepository.existsByName(p.getName())) {
				System.out.println("⏭️ Product exists, skipping: " + p.getName());
			} else {
				productRepository.save(p);
				System.out.println("✅ Seeded product: " + p.getName());
			}
		}
	}

}