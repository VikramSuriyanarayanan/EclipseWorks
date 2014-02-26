package smallQues;
import java.util.HashSet;
public class CheckOutputQ17
{
	public static void main(String[] args)
	{
		HashSet<String> strSet = new HashSet<String>();
		strSet.add("Hello");
		strSet.add("Wafer");
		strSet.add("Orange");
		strSet.add("Juice");
		strSet.remove("Hello");
		strSet.add("Orange");
		printSet(strSet);
		System.out.println("Set contains Wafer: " + strSet.contains("Wafer"));
		System.out.println("Set contains Hello: " + strSet.contains("Hello"));
	}
	public static void printSet(HashSet<String> strSet)
	{
		System.out.println("The set contains");
		for (String obj: strSet)
		{
			System.out.println(obj);
		}
	}
}
