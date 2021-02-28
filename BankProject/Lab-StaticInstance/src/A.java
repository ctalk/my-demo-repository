
public class A {
	public static int staticCount = 0;
	public int instanceCount = 0;
	
	public A () {
		staticCount ++;
		instanceCount++;
	}
		
	public static int getStaticCount () {
		return staticCount;
	}
	
	public static void setStaticCount (int staticCount) {
		A.staticCount = staticCount;
	}
	
	public int getInstanceCount () {
		return instanceCount;
	}
	
	public void setInstanceCount (int instanceCount) {
		this.instanceCount = instanceCount;
	}
		
		
		
}
