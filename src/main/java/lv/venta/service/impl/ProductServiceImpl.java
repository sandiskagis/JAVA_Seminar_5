package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import lv.venta.model.Product;
import lv.venta.service.ICRUDProductService;
import lv.venta.service.IFilterProductService;

@Service
public class ProductServiceImpl implements ICRUDProductService, IFilterProductService {

	private ArrayList<Product> allProducts = new ArrayList<>(Arrays.asList(new Product("Abols", "Sarkans", 0.99f, 5),
			new Product("Zemene", "Salda", 1.99f, 50), new Product("Burkans", "Oranžš", 0.39f, 500)));

	@Override
	public Product create(String title, String description, float price, int quantity) throws Exception {
		// pārbaudam ienākošos parametrus
		if (title == null || description == null || price < 0 || quantity < 0)
			throw new Exception("Problems with input params");

		// noskaidrojam, vai jau tāds produkts neeksistē
		for (Product tempP : allProducts) {
			if (tempP.getTitle().equals(title) && tempP.getDescription().equals(description)
					&& tempP.getPrice() == price) {
				tempP.setQuantity(tempP.getQuantity() + quantity);
				return tempP;
			}
		}
		// ja tāds produkts neeksistē, tad izveidojam jaunu
		Product newProduct = new Product(title, description, price, quantity);
		allProducts.add(newProduct);
		return newProduct;

	}

	@Override
	public ArrayList<Product> retrieveAll() throws Exception {
		if (allProducts.isEmpty())
			throw new Exception("Product list is empty");

		return allProducts;
	}

	@Override
	public Product retrieveById(int id) throws Exception {
		if (id > 0) {
			for (Product tempP : allProducts) {
				if (tempP.getId() == id) {
					return tempP;
				}
			}

			throw new Exception("Product with " + id + " is not found");

		} else {
			throw new Exception("Id should be positive");
		}
	}

	@Override
	public void updateById(int id, String title, String description, float price, int quantity) throws Exception {
		Product updateProduct = retrieveById(id);
		if(title != null) updateProduct.setTitle(title);
		if(description !=null) updateProduct.setDescription(description);
		if(price >= 0 && price <= 10000) updateProduct.setPrice(price);
		if(quantity >= 0 && quantity <= 100 ) updateProduct.setQuantity(quantity);

	}

	@Override
	public void deleteById(int id) throws Exception {
		Product deleteProduct = retrieveById(id);
		allProducts.remove(deleteProduct);

	}

	@Override
	public ArrayList<Product> filterByPriceLessThanThreshold(float threshold) throws Exception {
		if(threshold < 0 || threshold > 10000 ) throw new Exception("The limit of price is wrong");
		
		ArrayList<Product> result = new ArrayList<>();
		
		for(Product tempP: allProducts) {
			if(tempP.getPrice() < threshold)
			{
				result.add(tempP);
			}
		}
		
		return result;
	}

	@Override
	public ArrayList<Product> filterByQuantityLessThanThreshold(int threshold) throws Exception {
		if(threshold < 0 || threshold > 100 ) throw new Exception("The limit of quantity is wrong");
		
		ArrayList<Product> result = new ArrayList<>();
		
		for(Product tempP: allProducts) {
			if(tempP.getQuantity() < threshold)
			{
				result.add(tempP);
			}
		}
		
		return result;
	}

	@Override
	public ArrayList<Product> filterByTitleOrDescription(String text) throws Exception {
		if(text == null) throw new Exception("Search text can not be null");
		
		ArrayList<Product> result = new ArrayList<>();
		
		for(Product tempP: allProducts) {
			if(tempP.getTitle().toLowerCase().contains(text.toLowerCase()) || tempP.getDescription().toLowerCase().contains(text.toLowerCase()))
			{
				result.add(tempP);
			}
		}
		
		return result;
		
	}

	@Override
	public float calculateProductsTotalValue() throws Exception {
		if (allProducts.isEmpty())
			throw new Exception("Product list is empty");
		
		
		float result = 0;
		for(Product tempP: allProducts) {
			result += tempP.getPrice()*tempP.getQuantity();
		}
		
		return result;
	}

}