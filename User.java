/**
 * 	The User class represents the various user's when they login to access the program
 * 
 * 	@author		Shawn Chen
 * 	@version	01/20/2023
 */
public class User {
	
	private String username;
	private String password; 
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean compare(User other) {
		if (this.getUsername().equals(other.getUsername()) && this.getPassword().equals(other.getPassword())) {
			return true;
		}
		return false;
	}
}
