/**
/The following is a definition of the Author class.
/This class has two instance variables.
 **/
import java.util.*;
import java.io.Serializable;
public class Vehicle implements Serializable
{
	//private String make;
	//private String model;
	//private int year;
	//private int milege;
	private String make,model;
	private int year,milege;

	public Vehicle(String o,String p,int k,int r)
	{
		//super(n,m,o,p,k,r);
		make   = o;
		model  = p;
		year   = k;
		milege = r;
	}
	/*	public void getInput(String ma,String mo)
	   {
		make   = ma;
		model  = mo;

	    }

		public void getInput(int ye,int mi)
		{
			year   = ye;
		    milege = mi;
	   }
	 */
	public String getmake(){
		String a = make;
		return a;
	}
	public String getmodel(){
		return model;
	}

	public int getyear(){
		return year;
	}
	public int getmilege(){
		return milege;
	}


	/**
	/The toString() method returns a String containing the details of the author
	 **/
	public String toString()
	{
		String out = "";
		out += "Vehicle";
		//out += super.toString();
		out += getmake();
		out += getmodel();
		out += getyear();
		out += getmilege();
		//out += " It is that it is a hardcover book";
		return out;
	}
}