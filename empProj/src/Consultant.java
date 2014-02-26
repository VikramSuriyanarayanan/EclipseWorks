import java.util.*;
public class Consultant extends Employee
{

    private int ConsultanthoursWorked;
    private int ProjectType;
    private String Address;
	private String Name;

	public Consultant(String n,String m,int CHW, int PT)
		{
		super(n,m);
		Address = m;
		Name  = n;
		ConsultanthoursWorked = CHW;
		ProjectType = PT;
		}

	public double getCompensation()
		{
			if(ProjectType==1)
			{
			double a =  ConsultanthoursWorked*55;
			return a;
			}
			else if(ProjectType==2)
			{
		double a =  ConsultanthoursWorked*70;
		return a;
		    }
		else
		    {
		double a =  ConsultanthoursWorked*85;
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
							out += "Consultant";
							//out += super.toString();
							out += getName();
							out += getAddres();
							out += getCompensation();
							//out += " It is that it is a hardcover book";
							return out;
		}

}