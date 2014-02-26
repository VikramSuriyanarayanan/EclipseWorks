/**
 * This program demonstrates how we can write and read an entire array as a single object
 */
import java.io.*;
import java.util.*;
public class EmployeeDriver4
{
public static void main(String[] args)
{
    int EmployeeType;

    /**
     * First declare an array of Book objects. It is important that the Book class has implemented the
     * Serializable interface.  Also, if the Book class has any class type instance variables, those
     *classes must also have implemented the Serializable object.
     */
    //writing
   Employee[] ab = new Employee[15];

	 								ab[0] = new FullTimeEmployee("Chathura","2200Waterview",0);
	 						    	ab[1] = new FullTimeEmployee("Chathura2","2200Waterview",65000);
	 								ab[2] = new FullTimeEmployee("Chathura3","2200Waterview",135000);
	 								ab[3] = new HourlyEmployee("Chathura4","2200Waterview",40,12);
	 								ab[4] = new HourlyEmployee("Chathura5","2200Waterview",60,10);
	 								ab[5] = new HourlyEmployee("Chathura6","2200Waterview",70,10);
	 								ab[6] = new HourlyEmployee("Chathura7","2200Waterview",80,10);
	 								ab[7] = new HourlyEmployee("Chathura8","2200Waterview",90,10);
	 								ab[8] = new HourlyEmployee("Chathura9","2200Waterview",100,10);
	 								ab[9] = new Consultant("Chathura5","2200Waterview",1,10);
								    ab[10] = new Consultant("Chathura6","2200Waterview",2,10);
									ab[11] = new Consultant("Chathura7","2200Waterview",3,10);
									ab[12] = new Consultant("Chathura8","2200Waterview",1,10);
	 								ab[13] = new Consultant("Chathura9","2200Waterview",2,10);
	 								ab[14] = new Consultant("Chathura9","2200Waterview",3,10);
	 								//ab[3] = new FullTimeEmployee(line1,line2,line5);


	 //Write to the file

	 ObjectOutputStream objOut2 = null;
	 ObjectInputStream objIn2 = null;
	 try
	 	{
	    		objOut2 = new ObjectOutputStream(new FileOutputStream("booksarray.dat"));
	   		objOut2.writeObject(ab); //We then write the entire array to the binary file
	   		objOut2.close();
	 	}
	    catch (Exception e)
	    {
	      	System.out.println(e.getMessage());
	     }
// WRtintnit





	String[] EmployeeArray = new String[7];
	EmployeeArray[0] = "============ Menu List========== ";
	EmployeeArray[1] = "1 Accept input for new Employees";
	EmployeeArray[2] = "2 Display employee information for all the employees";
	EmployeeArray[3] = "3 List the name of the Employees along with the compensation";
	EmployeeArray[4] = "4 Display the employee name together with the make, model and mileage of all vehicles whose mileage is greater than 78000 miles";
	EmployeeArray[5] = "5 Exit Program";
	EmployeeArray[6] = "Your Choice:";

//Employee[] name = new FullTimeEmployee[25];
	ObjectOutputStream objOut = null;
	ObjectInputStream objIn = null;

	try
	{
   		objOut = new ObjectOutputStream(new FileOutputStream("booksarray1.dat"));
  		objOut.writeObject(EmployeeArray); //We then write the entire array to the binary file
  		objOut.close();
	}
   catch (Exception e)
   {
     	System.out.println(e.getMessage());
    }
    /*
      Read the binary file
      */
      //for loop for entering for multiple times
for(int j = 1; j<3;j++) {
    try
		{
	   		objIn = new ObjectInputStream(new FileInputStream("booksarray1.dat"));
                        /**
                         * To read back the data, we use the readObject method of the ObjectInputStream                         */
	  		String[] EmployeeArrayIn = (String[]) objIn.readObject();
	  		for (int i = 0; i < EmployeeArrayIn.length; i++)
	  		{
				System.out.println(EmployeeArrayIn[i]);
		    }


			}
	   catch (Exception e)
	   		{
	     	System.out.println(e.getMessage());
    	}


//Entering Data to the File
    Scanner keyboard = new Scanner(System.in);
	System.out.println("\n");
	System.out.println("Please Enter Your Choice?");
	int  OptionType = keyboard.nextInt();

	if (OptionType == 1)
	{
			System.out.println("Type of Employee? (1 -Full Time Employee, 2 -Part Time Employee, 3 -Consultant)");
				EmployeeType = keyboard.nextInt();
				keyboard.nextLine();
					if (EmployeeType == 1)
					{
					System.out.println("Employee Name?");
				String line1 = keyboard.nextLine();
					System.out.println("Employee Address?");
				String line2 = keyboard.nextLine();
					System.out.println("\n");
					System.out.println("Enter Details of the Employee Vehicle");
					System.out.println("Make of Vehicle:");
				String line3 = keyboard.nextLine();
					System.out.println("Model of Vehicle:");
				String line4 = keyboard.nextLine();
					System.out.println("Year of Make:");
				int line5 = keyboard.nextInt();
					System.out.println("Mileage of Vehicle:");
				int line6 = keyboard.nextInt();
            		System.out.println("Salary:");
				double line7 = keyboard.nextDouble();





			       }
				    else if (EmployeeType == 2)
				    {
					System.out.println("Employee Name?");
				String line1 = keyboard.nextLine();
					System.out.println("Employee Address?");
				String line2 = keyboard.nextLine();
					System.out.println("\n");
					System.out.println("Enter Details of the Employee Vehicle");
					System.out.println("Make of Vehicle:");
				String line3 = keyboard.nextLine();
					System.out.println("Model of Vehicle:");
					String line4 = keyboard.nextLine();
					System.out.println("Year of Make:");
				int line5 = keyboard.nextInt();
					System.out.println("Mileage of Vehicle:");
				int line6 = keyboard.nextInt();
					System.out.println("Hours Worked:");
				int line7 = keyboard.nextInt();
					System.out.println("Hours Rate:");
				double line8 = keyboard.nextDouble();
					}
					else if (EmployeeType == 3)
					{
					System.out.println("Employee Name?");
				String line1 = keyboard.nextLine();
					System.out.println("Employee Address?");
				String line2 = keyboard.nextLine();
					System.out.println("\n");
					System.out.println("Enter Details of the Employee Vehicle");
					System.out.println("Make of Vehicle:");
				String line3 = keyboard.nextLine();
					System.out.println("Model of Vehicle:");
					String line4 = keyboard.nextLine();
					System.out.println("Year of Make:");
				int line5 = keyboard.nextInt();
					System.out.println("Mileage of Vehicle:");
				int line6 = keyboard.nextInt();
					System.out.println("Hours Worked:");
				int line7 = keyboard.nextInt();
					System.out.println("Project Type:");
				int line8 = keyboard.nextInt();
	               }

			else
			{
			System.out.println("wrong choice");
		    }
}

	else if (OptionType == 2)
	       {
      for (int k =0; k<ab.length; k++)
          {
              printName(ab[k]);
          }
			System.out.println("123");
	       }
	  else if (OptionType == 3)
	       {
	       }
	  else if (OptionType == 4)
	       {
	       }
	  else if (OptionType == 5)
	      {
		System.exit(0);
	      }
	  else
	      {
		System.exit(0);
	      }
	}//Closing bracket for the for loop

}

//



public static void printName(Employee ab)
    {
        //When  a.getName() is executed, the getName() method implemented in the Animal class is executed
     Employee a = (Employee)ab;
     System.out.println(a.toString());
     //System.out.println(a.getname());
     //System.out.println(a.getAddress());

    }
    }