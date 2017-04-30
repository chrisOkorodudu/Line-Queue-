import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class QueueApp {

	public static void main(String[] args) {

		if (args.length != 2) {
			throw new IllegalArgumentException("Please enter two file names in command prompt line.");
		}

		//initialize list of customers
		List<CustomerNode> customers = initializer(args[0]);
		//create queue object to mimic store counter
		CustomerQueue cQueue = new CustomerQueue();

		// for (CustomerNode c : customers) {
		// 	c.displayNode();
		// }

		int time = 30500;
		int customersServed = 0;
		int totalIdle = 0;
		int currentIdle = 0;
		int idleMax = 0;
		int lineLengthMax = 0;

		while (time < 61200) {

			//check if desk is idle
			if (cQueue.isEmpty() && time > 32400) {
				currentIdle++;
			} else {
				totalIdle += currentIdle;
				if (currentIdle > idleMax) {
					idleMax = currentIdle;
				}
				currentIdle = 0;
			}


			//use for each loop to add customers to queue if their arrival time matches
			for (CustomerNode customer : customers) {
				if (customer.getArrival() == time  && time >= 32400) {
					cQueue.enqueue(customer);
				} else if (customer.getArrival() <= time && time < 32400) {
					//if costomer arrives before 9, wait time must be added 
					customer.setWaitTime(customer.getWaitTime() + 1);
					//System.out.println(customer.getId());
				} else if (customer.getArrival() < time && time == 32400) {
					cQueue.enqueue(customer);
				}
			}


			//update max line length 
			if (cQueue.countNodes() > lineLengthMax) {
				lineLengthMax = cQueue.countNodes();
			}



			//dequeue customer if finished being served 
			if (!cQueue.isEmpty() && time > 32400) {
				if (cQueue.getFirst().getServeTime() < 300) {
					cQueue.getFirst().setServeTime(cQueue.getFirst().getServeTime() + 1);
				} else {
					cQueue.dequeue();
					customersServed++;
				}
			}



			//iterate through queue to calculate wait time
			CustomerNode current = cQueue.getFirst();
			while (current != null) {
				if (!current.equals(cQueue.getFirst())) {
					current.setWaitTime(current.getWaitTime() + 1);
				}
				current = current.next;
			}

			//last customer served
			CustomerNode lateArrival = cQueue.getFirst();
			while (lateArrival != null && time == 61200) {
				if (lateArrival.getServeTime() == 300) {
					customersServed++;
					break;	
				}
				if (lateArrival.equals(cQueue.getFirst())) {
					cQueue.getFirst().setServeTime(cQueue.getFirst().getServeTime() +1);
				} 	
			}
			time++;
		}


		// for (CustomerNode customer: customers) {
		// 	customer.displayNode();
		// }


		results(args[1], customersServed, idleMax, totalIdle, lineLengthMax, customers);

		// System.out.println("Idle Max: "+ printTime(idleMax));
		// System.out.println("Total Idle: "+ printTime(totalIdle));
		// System.out.println(customersServed);

		// //line considered as the people waiting (not including who is currently being served)
		// System.out.println("Max Line length: "+(lineLengthMax-1));
	}



	public static List<CustomerNode> initializer(String fileName) {
		List<CustomerNode> customers = new ArrayList<CustomerNode>();
		String line = null;
		int arrival = 0;

		try{
			
			Scanner reader = new Scanner(new File(fileName));
			int id = 1;

			while(reader.hasNextLine()) {

				line = reader.nextLine();
				
				if (line.contains("ARRIVAL")) {
					String a = line.substring(14);
					String[] time = a.split(":");
					arrival += Integer.parseInt(time[2]);
					int minutes = Integer.parseInt(time[1]);
					arrival += minutes*60;
					int hours = Integer.parseInt(time[0]);
					if (hours < 8) {
						hours+=12;
					}
					arrival += hours*3600;

					CustomerNode c = new CustomerNode(id, arrival);
					customers.add(c);
					arrival = 0;
					id++;
				}
			}
				

			


		}
		//The catch block performs a specific action depending on the exception
		catch(FileNotFoundException ex){
			System.out.println( "Unable to open file '" + fileName + "'");
			//the printStackTrace method will print out an error output stream ("What went wrong?" report);
			
			ex.printStackTrace();
		}

		finally {return customers; }
	}


	public static void results(String fileName, int customersServed, int idleMax, int totalIdle, int lineLengthMax, List<CustomerNode> customers) {



		try {
			Scanner reader = new Scanner(new File(fileName));
			String line = null;

			while (reader.hasNextLine()) {
				line = reader.nextLine();

				if (line.contains("SERVED")) {
					System.out.println(line + ": " + (customersServed+1));
				} else if (line.contains("LONGEST")) {
					System.out.println(line + ": " + printTime(idleMax));
				} else if (line.contains("TOTAL")) {
					System.out.println(line + ": " + printTime(totalIdle));
				} else if (line.contains("MAXIMUM")) {
					System.out.println(line + ": " + (lineLengthMax-1));
				} else if (line.contains("1")) {
					System.out.println(line + ": " + printTime(customers.get(0).getWaitTime()));
				} else if (line.contains("2")) {
					System.out.println(line + ": " + printTime(customers.get(1).getWaitTime()));
				} else if (line.contains("3")) {
					System.out.println(line + ": " + printTime(customers.get(2).getWaitTime()));
				} else if (line.contains("7")) {
					System.out.println(line + ": " + printTime(customers.get(6).getWaitTime()));
				}
			}
		} catch (FileNotFoundException ex) {
			System.out.println( "Unable to open file '" + fileName + "'");
			ex.printStackTrace();
		}
	}


	//convert time from seconds to more readable format
	public static String printTime(int seconds) {

		String time_string = "";

		if (seconds / 3600 < 10)
			time_string += "0";
		time_string += String.valueOf((seconds / 3600)) + ":";

		if ((seconds % 3600 / 60) < 10)
			time_string += "0";
		time_string += String.valueOf((seconds % 3600) / 60) + ":";
		
		if (seconds % 60 < 10)
			time_string += "0";
		time_string += String.valueOf(seconds % 60);

		return time_string;
	}

	
	

}