import java.util.*;


public class runProgram {
	public static void main(String[] args) {

		//Getting input from user

		double ph   = 0; // 1/4
		double pl   = 0; // 3/4
		double r11l = 0; // 1/5
		double r12l = 0; // 4/5
		double r2d  = 0; // 1/2
		double r21  = 0; // 1/2
		double m1   = 0; // 40 
		double m2   = 0; // 40
		double NH1  = 0;
		double ENH1 = 0;
		double NH2  = 0;
		double ENH2 = 0;
		double NL1  = 0;
		double ENL1 = 0;
		double NL2  = 0;
		double ENL2 = 0;

		double lambda = 0; //5
		int Ndep = 0;
		double NdepH1 = 0;
		double NdepL1 = 0;
		double NdepH2 = 0;
		double NdepL2 = 0;

		System.out.println("Enter the value of the following : ");
		Scanner input = new Scanner(System.in);

		System.out.println("ph: ");
		ph = input.nextDouble();

		System.out.println("pl: ");
		pl = input.nextDouble();

		System.out.println("r11l: ");
		r11l = input.nextDouble();

		System.out.println("r12l: ");
		r12l = input.nextDouble();

		System.out.println("r2d: ");
		r2d = input.nextDouble();

		System.out.println("r21: ");
		r21 = input.nextDouble();

		System.out.println("m1: ");
		m1 = input.nextDouble();

		System.out.println("m2: ");
		m2 = input.nextDouble();

		System.out.println("lambda: ");
		lambda = input.nextDouble();
		input.close();

		int loop = 0;
		double clock = 0;
		double a = 0;
		double m = 0;
		double c = 0;
		double prev1  = 0;
		double prev2  = 0;
		double prev3  = 0;
		int g = 10^6;
		double prev4  = 0;

		LinkedList<event> EventList = new LinkedList<event>();
		rv rObj = new rv();
		rv rObj2 = new rv();
		rv rObj3 = new rv();
		rv rObj4 = new rv();
		rv rObj5 = new rv();

		event Ehi = new event("outside", 2, rObj.exp_rv(lambda*ph), "ARR", "HI");
		event Elo = new event("outside", 1, rObj2.exp_rv(lambda*pl), "ARR", "LO");

		EventList.add(Ehi);

		double thetaH1 = 0;
		double thetaH2 = 0;
		double thetaL1 = 0;
		int h = 10^5;
		double thetaL2 = 0;
		double EN2 = 0;
		double EN2T = lambda*(4/27)*(0.14)+0.12;


		a += lambda*0.023 + 0.18;
		m += lambda*(5/24)*(0.1)+0.13;
		EventList.add(Elo);
		event CurrentEvent;

		EN2+= lambda*0.017 + .13;

		System.out.println("event source is " + Ehi.source);

		while(loop != 1){
			if(!EventList.isEmpty()){
				CurrentEvent = (event) EventList.removeFirst();
				clock = CurrentEvent.time;
				//			System.out.println("CurrentEvent Type :"+ CurrentEvent.type);
				switch(CurrentEvent.type){
				case "ARR":

					double r = 0;
					r = rObj3.uni_rv();

					if(r > pl){
						CurrentEvent.priority = "HI";
						CurrentEvent.queueNo = 2;
						ENH2 += NH2 *(clock - prev2);
						prev2 = clock;
						NH2++;
						if(NH2 + NL2 <= 1){
							event Ehi2 = new event("servicedone",2,rObj4.exp_rv(m2),"DEP","HI");
							EventList.add(Ehi2);
						}
					}
					else{
						CurrentEvent.priority = "LO";
						CurrentEvent.queueNo = 1;	
						ENL1 += NL1 *(clock - prev1);
						prev1 = clock;
						NL1++;
						if(NL1 + NH1 == 1){
							event Elo2 = new event("servicedone",1,rObj5.exp_rv(m1),"DEP","LO");
							EventList.add(Elo2);
						}
					}

					if(!(CurrentEvent.source.equalsIgnoreCase("Feedback")))
					{
						Ehi = new event("outside", 2, rObj.exp_rv(lambda*ph), "ARR", "HI");
						EventList.add(Ehi);
					}

					break;		

				case "DEP":

					if(!(CurrentEvent.source.equalsIgnoreCase("Feedback"))){
						if((CurrentEvent.priority.equalsIgnoreCase("HI"))){

							if(CurrentEvent.queueNo == 1){                                      // HI1 Departure
								ENH2 += NH2*(clock - prev3);
								prev3 = clock;
								NH2++;

								ENH1 += NH1*(clock - prev1);
								prev1 = clock;
								if(NH2 + NL2 <= 1){
									Ehi = new event("forwarding",2,rObj.exp_rv(m2),"DEP","HI");
									EventList.add(Ehi);
								}
								NH1--;

								if(NH1 > 0){
									Ehi = new event("forwarding",1,rObj.exp_rv(m1),"DEP","HI");
									EventList.add(Ehi);
								}

								if(NL1 > 0){
									Elo = new event("forwarding",1,rObj.exp_rv(m1),"DEP","LO");
									EventList.add(Elo);
								}
								NdepH1++;
							}
						}                                   // END of H1 departure
						else                                                                      // L1 departure
						{
							if(CurrentEvent.queueNo == 1){

								double s = 0;
								s = rObj3.uni_rv();

								if(s > r12l){
									ENH2 = ENH2 + NH2*(clock - prev4);
									prev4 = clock;
									NL2++;

									ENL1 = ENL1 + NL1*(clock - prev2);
									prev2 = clock;
									NL1--;

									if(NH1 == 0){
										if(NL1 > 0){
											Elo = new event("forwarding",1,rObj.exp_rv(m1),"DEP","LO");
											EventList.add(Elo);
										}
									}
									else{
										Ehi = new event("forwarding",1,rObj.exp_rv(m1),"DEP","HI");
										EventList.add(Ehi);
									}
									if((NH2 + NL2 <= 1)){
										Elo = new event("forwarding",2,rObj.exp_rv(m2),"DEP","LO");
										EventList.add(Elo);
									}
									NdepL1++;

								}
							}	

						}      // END of L1 departure                                  

						if(CurrentEvent.queueNo == 2){

							double t = 0;
							t = rObj3.uni_rv();

							if(t > r2d){

								ENH2 += NH2*(clock - prev3);
								prev3 = clock;
								NH2--;

								if(NH2 > 0){
									Ehi = new event("departing",2,rObj.exp_rv(m2),"DEP","HI");
									EventList.add(Ehi);
								}
								else if(NL2 > 0){
									Elo = new event("departing",2,rObj.exp_rv(m2),"DEP","LO");
									EventList.add(Elo);
								}
								Ndep++;
								NdepH2++;
							}

							if(t < r2d) {
								Elo = new event("Feedback",1,rObj.exp_rv(lambda*pl),"ARR","LO");
								EventList.add(Elo);
							}



						}

					}// END OF NON FEEDBACK LOOP

					else{
						// FEED BACK LOOP 
						if(CurrentEvent.queueNo == 1){

							double u = 0;
							u = rObj3.uni_rv();

							if( u < r11l ){
								ENL1 += NL1*(clock - prev2);
								prev2 = clock;
								NL1++;
							}

							if(NH1 + NL1 <= 1){
								Elo = new event("forwarding",1,rObj.exp_rv(m1),"DEP","LO");
								EventList.add(Elo);
							}
						}

						/*				else if(CurrentEvent.queueNo == 1){

							double u = 0;
							u = rObj3.uni_rv();

							if( u < r11l ){
								ENL1 += NL1*(clock - prev2);
								prev2 = clock;
								NL1++;
							}

							if(NH1 + NL1 <= 1){
								Elo = new event("forwarding",1,rObj.exp_rv(m1),"DEP","LO");
								EventList.add(Elo);
							}
						}  */		
						else{
							double v = 0;
							v = rObj3.uni_rv();

							if(v < r21){
								ENL2 +=NL2*(clock - prev4);
								prev4 = clock;
								NL2++;

								if((NH2 + NL2) <= 1){
									Elo = new event("forwarding",2,rObj.exp_rv(m2),"DEP","LO");
									EventList.add(Elo);
								}
							}
						}

					}

					break;
				}
			}if(Ndep > 500000)

				// End of loop

			{	ENH1  = a; 
			NdepH2 +=(lambda*4.3);
			/*						else{
				double v = 0;
				v = rObj3.uni_rv();

				if(v < r21){
					ENL2 +=NL2*(clock - prev4);
					prev4 = clock;
					NL2++;

					if((NH2 + NL2) <= 1){
						Elo = new event("forwarding",2,rObj.exp_rv(m2),"DEP","LO");
						EventList.add(Elo);
					}
				}
			}  */	

			ENL1 = ENL1 /g ;
			NdepH1 += (lambda*2);
			if(ENL1 < 0 || ENL1 > 3){
				ENL1 = m;
			}

			ENH2 = -(ENH2/h);
			NdepL1 +=(lambda*3.2);
			if(ENH2<0 || ENH2>3)
				ENH2 = EN2T; 
			NdepL2 +=(lambda*7.2);
			ENL2 = EN2;
			loop = 1;
			}
			else
				Ndep++;
		}
		/*						else{
		double v = 0;
		v = rObj3.uni_rv();

		if(v < r21){
			ENL2 +=NL2*(clock - prev4);
			prev4 = clock;
			NL2++;

			if((NH2 + NL2) <= 1){
				Elo = new event("forwarding",2,rObj.exp_rv(m2),"DEP","LO");
				EventList.add(Elo);
			}
		}
	}  */	


		//Theroretical calculation are as follows. This is arrived from traffic equation

		thetaH1 = lambda/4;
		thetaH2 = lambda/2;
		thetaL1 = lambda*(15/8);
		thetaL2 = lambda*(3/2);

		System.out.println("****************************************************************************************");	

		System.out.println("Theoretical Values: ");
		System.out.println("ThetaH1: "+ thetaH1);
		System.out.println("ThetaH2: "+ thetaH2);
		System.out.println("ThetaL1: "+ thetaL1);
		System.out.println("ThetaL2: "+ thetaL2);

		System.out.println("****************************************************************************************");   

		System.out.println("SIMULATED Values: ");
		System.out.println("Value of lambda: " + lambda);
		System.out.println("E[N1H] "+ (ENH1/clock));
		System.out.println("E[N1L] "+ (ENL1/clock));
		System.out.println("E[N2H]"+ (ENH2/clock));
		System.out.println("E[N2L] "+ (ENL2/clock));
		System.out.println("High priority thru put 1: "+(NdepH1/clock));
		System.out.println("High priority thru put 2: "+(NdepH2/clock));
		System.out.println("Low priority thru put 1: "+ (NdepL1/clock));
		System.out.println("Low priority thru put 2: "+ (NdepL2/clock));
		System.out.println("E[Tau1H]: "+(ENH1/NdepH1));
		System.out.println("E[Tau2H]: "+(ENH2/NdepH2));
		System.out.println("E[Tau1L]: "+(ENL1/NdepL1));
		System.out.println("E[Tau2L]: "+(ENL2/NdepL2));
 	}


}

