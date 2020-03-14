package it.transaction.jbossts.entities.schema2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "table1")
public class Entity1 {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "key")
	private String key;
	
	@Column(name = "value")
	private int value;
	
	public Entity1() {
		super();
	}
	
	public Entity1(int id, String key, int value) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
