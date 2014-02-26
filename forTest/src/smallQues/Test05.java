package smallQues;
public class Test05
{
	public static void main(String[] args)
	{
		try
		{
			method1(-24);
		}
		catch(Exception e)
		{
			System.out.println("Exception in main method");
		}
		System.out.println("From main method");
	}
	public static void method1(int n) throws Exception
	{
		try
		{
			if (n > 0)
				throw new Exception();
			else if (n < 0)
				throw new Exception();
			else
				System.out.println("No exception");
			System.out.println("Still in method1");
		}
		catch (Exception e)
		{
			System.out.println("Caught in method1");
		}
		finally
		{
			System.out.println("In finally block");
		}
		System.out.println("After finally block");
	}
}
