package sicat.sbproject.example01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sicat.sbproject.example01.model.Product;
import sicat.sbproject.example01.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProductById(Integer id) {
		return productRepository.findById(id);
	}

	// Custom query methods
	public boolean existsProductByName(String name) {
		return productRepository.existsByName(name);
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public void deleteProduct(Integer id) {
		productRepository.deleteById(id);
	}

	public Product updateProduct(Integer id, Product updatedProduct) {

		return productRepository.findById(id).map(existingProduct -> {

			// set other fields as needed
			existingProduct.setId(updatedProduct.getId());
			existingProduct.setName(updatedProduct.getName());
			existingProduct.setType(updatedProduct.getType());
			existingProduct.setType(updatedProduct.getDescription());
			existingProduct.setLength(updatedProduct.getLength());
			existingProduct.setWidth(updatedProduct.getWidth());
			existingProduct.setHeight(updatedProduct.getHeight());
			existingProduct.setPrice(updatedProduct.getPrice());

			// triggers update and return updated
			return productRepository.save(existingProduct);

		}).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
	}

}
