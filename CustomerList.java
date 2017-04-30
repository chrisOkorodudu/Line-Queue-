
public class CustomerList {
	
	private CustomerNode first;
	private CustomerNode last;

	CustomerList() {
		first = null;
		last = null;
	}

	public boolean isEmpty() {
		return (first == null);
	}

	// public void insertFirst(int id, String arrival) {
	// 	customerNode newcustomer = new customerNode(id, arrival);
	// 	newcustomer.next = first;
	// 	first = newcustomer;	
	// }

	public void enqueue(CustomerNode customer) {
		

		if (last == null) {
			first = customer;
		} else {
			last.next = customer;
		}
		last = customer;
	}

	public void dequeue() {
		if (isEmpty()) {
			System.out.println("Queue Empty");
		}else{
			first = first.next;
			if (first == null) {
				last = null;
			}
		}
	}

	public void displayList() {
		CustomerNode current = first;
		//stops when next references null
		while (current != null) {
			current.displayNode();
			
			//similar to i = i+1
			current = current.next;
		}
		System.out.println("");
	}

	public void waitTimes() {
		CustomerNode current = first;

		while (current != null) {
			if (current.getArrival() < 3240) {
				current.setWaitTime(3240-current.getArrival());
			} else {

			}
		}

	}


}