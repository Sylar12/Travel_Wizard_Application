package model;

import java.io.Serializable;

/*The class is used to verify a user. The userid combined with the password is sent from the app*/
public class IdPasswordCombo implements Serializable{
    private static final long serialVersionUID = -1205132708455806937L;

	private String id;
	private String password;
	
	public IdPasswordCombo(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
}
