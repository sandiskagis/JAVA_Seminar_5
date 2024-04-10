package lv.venta.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@AllArgsConstructor
public class Product {
	
	@Setter(value = AccessLevel.NONE)//priekš ID nebūs automātiskais set
	private int id;
	
	@NotNull
	@Size(min = 3, max = 20)
	@Pattern(regexp = "[A-Z]{1}[a-z ]+")
	private String title;
	
	@NotNull
	@Size(min = 4, max = 200)
	@Pattern(regexp = "[A-Za-z .:!]+")
	private String description;
	
	@Min(0)
	@Max(10000)
	private float price;
	
	@Min(0)
	@Max(100)
	private int quantity;
	
	
	private static int counter = 1;
	
	public void setId() {
		this.id = counter++;
	}

	public Product(@NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[A-Z]{1}[a-z ]+") String title,
			@NotNull @Size(min = 4, max = 200) @Pattern(regexp = "[A-Za-z .:!]+") String description,
			@Min(0) @Max(10000) float price, @Min(0) @Max(100) int quantity) {
		setId();
		this.title = title;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}


}