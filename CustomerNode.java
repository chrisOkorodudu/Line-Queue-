
public class CustomerNode {

	private int id;
	private int arrival;
	private int serveTime;
	private int waitTime;


	public CustomerNode next;

	CustomerNode(){}

	CustomerNode(int id, int arrival) {
		this.id = id;
		this.arrival = arrival;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrival() {
		return arrival;
	}

	public void setArrival(int arrival) {
		this.arrival = arrival;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime; 
	}

	public int getServeTime() {
		return serveTime;
	}

	public void setServeTime(int serveTime) {
		this.serveTime = serveTime;
	}

	public CustomerNode getNext() {
		return this.next;
	}

	public void setNext(CustomerNode next) {
		this.next = next;
	}

	public void displayNode() {
		System.out.println("ID: "+id);
		System.out.println("Arrival: "+ printTime(arrival));
		System.out.println("Wait time: "+ printTime(waitTime));
		System.out.println("Serve time: "+printTime(serveTime));
		System.out.println("");
	}

	//convert time from seconds to more readable format
	public String printTime(int seconds) {

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