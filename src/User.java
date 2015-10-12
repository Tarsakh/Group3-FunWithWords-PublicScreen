
public class User implements Comparable<User>{
	private String id; 
	private String lastLetter;
	
	public String getLastLetter() {
		return lastLetter;
	}

	public void setLastLetter(String lastLetter) {
		this.lastLetter = lastLetter;
	}

	public User(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public int compareTo(User o) {
		return id.compareTo(o.getId());
	}

}
