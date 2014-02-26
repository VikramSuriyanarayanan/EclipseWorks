import java.util.*;
public class FullTimeEmployee extends Employee
{
	private double salary;
	private String Address;
	private String Name;

    private double FullTimeEmployeeSalary;

    public FullTimeEmployee(String n,String m, int sa)
    			{
		super(n,m);
		Address = m;
		Name  = n;

		FullTimeEmployeeSalary = sa;
				}


   public void setSalary(double salary)
    {
        FullTimeEmployeeSalary = salary;
    }

	public double getCompensation()
		 {
		 if (FullTimeEmployeeSalary<45000)
		 	{
		 double a = FullTimeEmployeeSalary - (FullTimeEmployeeSalary*0.18);
		 return a;
		 	}
		 else if (45000 < FullTimeEmployeeSalary && FullTimeEmployeeSalary < 82000)
		 	{
		 double a =  FullTimeEmployeeSalary - (45000*0.18 + (FullTimeEmployeeSalary-45000)*0.28);
		 return a;
		 	}
		else
			{
		 double a =  FullTimeEmployeeSalary - (45000*0.18 + (82000-45000)*0.28 + (FullTimeEmployeeSalary-82000)*0.33);
		 return a;
			}
	    }


public void getInput(String a, String b){
 		Address = a;
 		Name = b;

	}

	public String getName(){
		return Name;
				}
		public String getAddres(){
		return Address;
				}

//code to print out values of the instance variables


 public String toString()
		{

				String out = "";
				out += "Full Time Emplyee";
				//out += super.toString();
				out += getName();
				out += getAddres();
				out += getCompensation();
				//out += " It is that it is a hardcover book";
				return out;
		}



}