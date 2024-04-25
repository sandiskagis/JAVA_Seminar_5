package lv.venta.service.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Product;
import lv.venta.repo.IProductRepo;
import lv.venta.service.ICRUDProductService;
import lv.venta.service.IFilterProductService;

@Service
public class ProductServiceImpl implements ICRUDProductService, IFilterProductService {

	@Autowired
	private IProductRepo productRepo;
	
	@Override
	public Product create(String title, String description, float price, int quantity) throws Exception {
		// pārbaudam ienākošos parametrus
		if (title == null || description == null || price < 0 || quantity < 0)
			throw new Exception("Problems with input params");

		// noskaidrojam, vai jau tāds produkts neeksistē
		Product foundProduct = productRepo.findByTitleAndDescriptionAndPrice(title,description,price);
		if(foundProduct!=null) 
		{
			foundProduct.setQuantity(foundProduct.getQuantity() + quantity);
			return productRepo.save(foundProduct);
		}
			
		
		// ja tāds produkts neeksistē, tad izveidojam jaunu
		Product newProduct = new Product(title, description, price, quantity);
		productRepo.save(newProduct);
		return newProduct;

	}

	@Override
	public ArrayList<Product> retrieveAll() throws Exception {
		if (productRepo.count()==0)
			throw new Exception("Product list is empty");

		return (ArrayList<Product>) productRepo.findAll();
	}

	@Override
	public Product retrieveById(int id) throws Exception {
		if (id > 0) {
			if(productRepo.existsById(id)) 
			{
				return productRepo.findById(id).get();
			}
			else
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
		productRepo.save(updateProduct);

	}

	@Override
	public void deleteById(int id) throws Exception {
		Product deleteProduct = retrieveById(id);
		productRepo.delete(deleteProduct);
	}

	@Override
	public ArrayList<Product> filterByPriceLessThanThreshold(float threshold) throws Exception {
		if(threshold < 0 || threshold > 10000 ) throw new Exception("The limit of price is wrong");
		
		ArrayList<Product> result = productRepo.findByPriceLessThan(threshold);
		return result;
	}

	@Override
	public ArrayList<Product> filterByQuantityLessThanThreshold(int threshold) throws Exception {
		if(threshold < 0 || threshold > 100 ) throw new Exception("The limit of quantity is wrong");
		
		ArrayList<Product> result = productRepo.findByQuantityLessThan(threshold);
		return result;
	}

	@Override
	public ArrayList<Product> filterByTitleOrDescription(String text) throws Exception {
		if(text == null) throw new Exception("Search text can not be null");
		
		ArrayList<Product> result = productRepo.findByTitleContainingOrDescriptionContaining(text, text);
		return result;
	}

	@Override
	public float calculateProductsTotalValue() throws Exception {
		if (productRepo.count()==0)
			throw new Exception("Product list is empty");
		
		
		float result = productRepo.calculateTotalValueFromDB();
		
		return result;
	}

}