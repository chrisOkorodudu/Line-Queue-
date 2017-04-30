
public class CustomerNode {

	private int id;
	private String arrival;

	public CustomerNode next;

	Customer{}

	Customer(int id, String arrival) {
		this.id = id;
		this.arrival = arrival;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public CustomerNode getNext() {
		return this.next;
	}

	public void setNext(CustomerNode next) {
		this.next = next;
	}
}