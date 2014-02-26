/**
/The following is a definition of the LibraryBook class.
/This class has four instance variables and one static (or class) variable.
/One of the instance variables is a type Author.
**/
import java.util.*;
public abstract class Employee 
{

	private String EmployeeName;
	private String EmployeeAddress;
	private String Vehicle;
	//private int EmployeeType;
	private EmployeeDriver4 EmployeeType;
	//This instance variable is added to describe the book type (textbook or fiction)


public Employee(String n,String m)
{
	//EmployeeType = type;
	EmployeeName = n;
	EmployeeAddress =m;
}

	public String getname()
	{
	 return EmployeeName;
	}

	public String getAddress()
	{
		 return EmployeeAddress;
	}

	//Accessor and mutator methods as well as any other
	//methods as needed by the problem definition included here.

    /**
    /The toString() method returns a String containing the details of the book
    /including the details of the author.  To get the author information
    /this toString() method calls the toString() method of the Author class
    **/
//	public String toString()
//	{
		//code to print out values of the instance variables
//	}

	////public abstract double computeRevenue();
	//{

	//	}

	//This abstract method will be overridden in the derived classes



}