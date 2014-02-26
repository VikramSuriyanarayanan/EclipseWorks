import java.util.*;
public class HourlyEmployee extends Employee
{

    private int HourlyEmployeehoursWorked;
    private double HourlyEmployeehourlyRate;
    private String Address;
	private String Name;

    public HourlyEmployee(String n,String m, int HEW,double HER)
        {
		super(n,m);
		Address = m;
		Name  = n;
		HourlyEmployeehoursWorked = HEW;
		HourlyEmployeehourlyRate = HER;

		}

		 public void setHourlyEmployeehoursWorked(int salarya)
	{
		        HourlyEmployeehoursWorked = salarya;
    }

    		 public void setHourlyEmployeehourlyRate(double salaryaaa)
	{
			        HourlyEmployeehourlyRate = salaryaaa;
    }

	public double getCompensation()
	{
	if(HourlyEmployeehoursWorked<=40)
		{
		double a = HourlyEmployeehoursWorked*HourlyEmployeehourlyRate;
		return a;
		}
	else
		{
		double a = 40*HourlyEmployeehourlyRate+(HourlyEmployeehoursWorked-40)*1.8 ;
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
					out += "Hourly Employee";
					//out += super.toString();
					out += getName();
					out += getAddres();
					out += getCompensation();
					//out += " It is that it is a hardcover book";
					return out;
		}
}