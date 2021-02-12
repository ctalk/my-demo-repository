
public class ClassOne {
	int id;
	public int getID() {return id; }
	public void setID (int id) {this.id = id;}
	public static void main (String[] args) {
		ClassOne c1 = new ClassOne ();
		c1.setID(25);
		
		System.out.println (c1.getID());
	}
}
