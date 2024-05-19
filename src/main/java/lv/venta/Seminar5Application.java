package lv.venta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lv.venta.model.MyAuthority;
import lv.venta.model.MyUser;
import lv.venta.model.Product;
import lv.venta.repo.IMyAuthorityRepo;
import lv.venta.repo.IMyUserRepo;
import lv.venta.repo.IProductRepo;

@SpringBootApplication
public class Seminar5Application {

	public static void main(String[] args) {
		SpringApplication.run(Seminar5Application.class, args);
	}
	
	

	@Bean //sī funkcija stratēsies automātiski, pēc programmas palaišanas
	public CommandLineRunner testModelLayer(IProductRepo productRepo,
			IMyAuthorityRepo authRepo, IMyUserRepo userRepo) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				Product p1 = new Product("Abols", "Zali", 0.99f, 10);
				Product p2 = new Product("Arbuzs", "Roza", 3.99f, 15);
				Product p3 = new Product("Vinogas", "Zali", 5.99f, 50);
				productRepo.save(p1);
				productRepo.save(p2);
				productRepo.save(p3);
				System.out.println("How Many:" + productRepo.count());//3
				System.out.println("Get:" + productRepo.findById(2).get());//Arbuzam
				Product updatedPr = productRepo.findById(2).get();//Arbuzs
				updatedPr.setQuantity(20);
				productRepo.save(updatedPr);//šeit jau būs 20 Arbuzam
				
				
				MyAuthority a1 = new MyAuthority("ADMIN");
				MyAuthority a2 = new MyAuthority("USER");
				authRepo.save(a1);
				authRepo.save(a2);
				
				PasswordEncoder encoder =
					    PasswordEncoderFactories.createDelegatingPasswordEncoder();
				MyUser u1 = new MyUser("admin", encoder.encode("admin"), a1);
				MyUser u2 = new MyUser("karina", encoder.encode("123"), a2);
				MyUser u3 = new MyUser("janis", encoder.encode("qwerty"), a1, a2);
				userRepo.save(u1);
				userRepo.save(u2);
				userRepo.save(u3);
				
				
				a1.addUser(u1);
				a1.addUser(u3);
				authRepo.save(a1);
				a2.addUser(u3);
				a2.addUser(u2);
				authRepo.save(a2);
				
				
				
				
				
				
				
			}
		};
	}

}