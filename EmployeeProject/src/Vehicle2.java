/**
/The following is a definition of the Author class.
/This class has two instance variables.
**/
import java.util.*;

public class Vehicle2 
{
	private String make;
	private String model;
	private int year1;
	private int milege1;
	private Employee year,milege;

	public Vehicle2(Vehicle2 o,Vehicle2 p,Vehicle2 k,Vehicle2 r)
		{
//super(n,m,o,p,k,r);
		make   = o;
		model  = p;
		year1   = k;
	    milege1 = r;


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
			return year1;
						}
	public int getmilege(){
			return milege1;
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