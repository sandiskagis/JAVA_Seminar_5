package lv.venta.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "ProductTable")
@Entity
public class Product {
	
	@Setter(value = AccessLevel.NONE)//priekš ID nebūs automātiskais set
	@Column(name = "Id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Size(min = 3, max = 20)
	@Pattern(regexp = "[A-Z]{1}[a-z ]+", message = "Only letters allowed")
	@Column(name = "Title")
	private String title;
	
	@NotNull
	@Size(min = 4, max = 200)
	@Pattern(regexp = "[A-Za-z .:!]+")
	@Column(name = "Description")
	private String description;
	
	@Min(0)
	@Max(10000)
	@Column(name="Price")
	private float price;
	
	@Min(0)
	@Max(100)
	@Column(name="Quantity")
	private int quantity;
	

	public Product(@NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[A-Z]{1}[a-z ]+") String title,
			@NotNull @Size(min = 4, max = 200) @Pattern(regexp = "[A-Za-z .:!]+") String description,
			@Min(0) @Max(10000) float price, @Min(0) @Max(100) int quantity) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}


}