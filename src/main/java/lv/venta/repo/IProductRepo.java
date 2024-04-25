package lv.venta.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Product;

public interface IProductRepo extends CrudRepository<Product, Integer>{
	
	//funkcijas atbilstoso kermeni uzprogrammes gpa musu vieta
	Product findByTitleAndDescriptionAndPrice(String title, String description, float price);

	ArrayList<Product> findByPriceLessThan(float threshold);

	ArrayList<Product> findByQuantityLessThan(int threshold);

	ArrayList<Product> findByTitleContainingOrDescriptionContaining(String text, String text2);
	
	
	@Query(nativeQuery = true, name = "SELECT SUM(PRICE * QUANTITY) FROM PRODUCT_TABLE;")
	float calculateTotalValueFromDB();

}
