/**
 * This program demonstrates how we can write and read an entire array as a single object
 */
import java.io.*;
import java.util.*;
public class EmployeeDriver
{
	private int i;
	public static void main(String[] args)
	{
//		EmployeeDriver E = new EmployeeDriver();
//		i =345;
//		System.out.println(E.i);
		
		System.out.println(5.3 % 2);
/*		int i = 0;
		int EmployeeType;

		String line3 = null;
		String line4 = null;

		int line5 = 0; 
		int line6 = 0;

		/**
		 * First declare an array of Book objects. It is important that the Book class has implemented the
		 * Serializable interface.  Also, if the Book class has any class type instance variables, those
		 *classes must also have implemented the Serializable object.
		 */
		//writing
		/*		Employee[] ab = new Employee[15];
		Vehicle[] vh = new Vehicle[15];

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
		 */
/*		boolean value = true;
		do
		{
			if (i < 15){

				System.out.println("============ Menu List========== ");
				System.out.println("1 Accept input for new Employees");
				System.out.println("2 Display employee information for all the employees");
				System.out.println("3 List the name of the Employees along with the compensation");
				System.out.println("4 Display the employee name together with the make, model and mileage of all vehicles whose mileage is greater than 78000 miles");
				System.out.println("5 Exit Program");
				System.out.println("Your Choice:");

				//Entering Data to the File
				Scanner keyboard = new Scanner(System.in);
				System.out.println("");
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
						System.out.println("");
						System.out.println("Enter Details of the Employee Vehicle");
						System.out.println("Make of Vehicle:");
						line3 = keyboard.nextLine();
						System.out.println("Model of Vehicle:");
						line4 = keyboard.nextLine();
						System.out.println("Year of Make:");
						line5 = keyboard.nextInt();
						System.out.println("Mileage of Vehicle:");
						line6 = keyboard.nextInt();
						System.out.println("Salary:");
						double line7 = keyboard.nextDouble();
						ab[i] = new FullTimeEmployee(line1,line2,line7);
						vh[i] = new Vehicle(line3,line4,line5,line6);
					}
					else if (EmployeeType == 2)
					{
						System.out.println("Employee Name?");
						String line1 = keyboard.nextLine();
						System.out.println("Employee Address?");
						String line2 = keyboard.nextLine();
						System.out.println("");
						System.out.println("Enter Details of the Employee Vehicle");
						System.out.println("Make of Vehicle:");
						line3 = keyboard.nextLine();
						System.out.println("Model of Vehicle:");
						line4 = keyboard.nextLine();
						System.out.println("Year of Make:");
						line5 = keyboard.nextInt();
						System.out.println("Mileage of Vehicle:");
						line6 = keyboard.nextInt();
						System.out.println("Hours Worked:");
						int line7 = keyboard.nextInt();
						System.out.println("Hours Rate:");
						double line8 = keyboard.nextDouble();
						ab[i] = new HourlyEmployee(line1,line2,line7,line8);
						vh[i] = new Vehicle(line3,line4,line5,line6);
					}
					else if (EmployeeType == 3)
					{
						System.out.println("Employee Name?");
						String line1 = keyboard.nextLine();
						System.out.println("Employee Address?");
						String line2 = keyboard.nextLine();
						System.out.println("");
						System.out.println("Enter Details of the Employee Vehicle");
						System.out.println("Make of Vehicle:");
						line3 = keyboard.nextLine();
						System.out.println("Model of Vehicle:");
						line4 = keyboard.nextLine();
						System.out.println("Year of Make:");
						line5 = keyboard.nextInt();
						System.out.println("Mileage of Vehicle:");
						line6 = keyboard.nextInt();
						System.out.println("Hours Worked:");
						int line7 = keyboard.nextInt();
						System.out.println("Project Type:");
						int line8 = keyboard.nextInt();
						ab[i] = new Consultant(line1,line2,line7,line8);
						vh[i] = new Vehicle(line3,line4,line5,line6);
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
						if(ab[k]!= null){
	//						System.out.println(ab[k]);
							printName(ab[k]);
							System.out.println(vh[k]);
						}
					}
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
					keyboard.close();
					System.exit(0);
				}
				System.out.println("********************************************");
				System.out.println("Do you want to exit??");
				System.out.println("If Yes, press 1, else press any other number to return to main menu ");

				Scanner keyboard1 = new Scanner(System.in);
				
				try{

					int valueForContinue = keyboard1.nextInt();
					if(valueForContinue == 1){
						keyboard1.close();
						value = false;
						System.out.println("Thank you. The program will now end");
					}
				}
				catch(InputMismatchException e){
					System.out.println("Enter either 1 or any other valid Number. Donot enter alphabets");
				}


				i++;
			}	
		}// Closing bracket for do-While loop
		while(value);             
	*/	
	}

	//



/*	public static void printName(Employee ab)
	{
		//When  a.getName() is executed, the getName() method implemented in the Animal class is executed
		Employee a = (Employee)ab;
		System.out.println(a.toString());
		//System.out.println(a.getname());
		//System.out.println(a.getAddress());

	}
*/
}