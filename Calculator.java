
public class Calculator {

	public int add(int a, int b)
	{
		int ans = a + b;
		return ans;
	}
	
	public int subtract(int a, int b)
	{
		return a - b;
	}
	
	public int multiply(int a, int b)
	{
		return a * b;
	}
	
	public int divide(int a, int b)
	{
		if(b == 0)
			return -999;
		else
			return a/b;
	}
	

}
