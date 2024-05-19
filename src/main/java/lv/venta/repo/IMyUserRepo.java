package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.MyUser;

public interface IMyUserRepo extends CrudRepository<MyUser, Integer>{

	//public abstract pēc noklusējuma
	//SQL: SELECT * FROM USER_TABLE WHERE USERNAME = <username>;
	MyUser findByUsername(String username);

}
