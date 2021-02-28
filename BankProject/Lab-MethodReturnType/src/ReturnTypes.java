
public class ReturnTypes {
	public static void main (String[] args) { 
		ReturnTypes rt = new ReturnTypes ();
	
		rt.returnNothing ();
		
		boolean b = rt.returnBoolean ();
		
		System.out.println ("The value of b is: " + b);
		
	}
	
	public void returnNothing () {
		System.out.println ("Inside a void method.");
	}
	
	public boolean returnBoolean () {
		return true;
	}
	
	
}
