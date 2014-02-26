package part3;
import java.util.*;
public class ErrorDemoI implements java.io.Serializable
{
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		try
		{
			String str = "";
			str = keyboard.next();
			int number = Integer.parseInt(str);
			System.out.println(number);
		}
		catch (NumberFormatException e)
		{
//			System.out.println( + "is not a number");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
