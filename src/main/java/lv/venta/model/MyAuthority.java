package lv.venta.model;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "AuthTable")
@Entity
public class MyAuthority {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Ida")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ida;
	
	@NotNull
	@Pattern(regexp = "[A-Z]{3,6}", message = "Only letters allowed")
	@Column(name = "Title")
	private String title;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UserAuthorityTable",
	joinColumns = @JoinColumn(name="Ida"),
	inverseJoinColumns = @JoinColumn(name="Idu"))
	@ToString.Exclude
	private Collection<MyUser> users = new ArrayList<>();
	
	
	
	public MyAuthority(String title) {
		setTitle(title);
	}
	
	
	public void addUser(MyUser user) {
		if(!users.contains(user))
			users.add(user);
	}
	
	
	public void removeUser(MyUser user) {
		if(users.contains(user))
			users.remove(user);
	}
	
	
	
}