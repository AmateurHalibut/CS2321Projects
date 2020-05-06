package cs2321;

/**
 * Alex Hromada
 * Assignment 2
 * This class has a method that receives a string of people and an integer and runs a Josephus game
 * using a CircularArrayQueue
 *
 */
public class Josephus {
	/**
	 * All persons sit in a circle. When we go around the circle, initially starting
	 * from the first person, then the second person, then the third... 
	 * we count 1,2,3,.., k-1. The next person, that is the k-th person is out. 
	 * Then we restart the counting from the next person, go around, the k-th person 
	 * is out. Keep going the same way, when there is only one person left, she/he 
	 * is the winner. 
	 *  
	 * @parameter persons  an array of string which contains all player names.
	 * @parameter k  an integer specifying the k-th person will be kicked out of the game
	 * @return return a doubly linked list in the order when the players were out of the game. 
	 *         the last one in the list is the winner.  
	 */
	public DoublyLinkedList<String> order(String[] persons, int k ) {

		// Throw an exception if k < 1
		if(k < 1){
			throw new IllegalArgumentException("Value k is less than expected, k must be greater than 0");
		}

		CircularArrayQueue<String> personQueue = new CircularArrayQueue<>(persons.length);
		DoublyLinkedList<String> people = new DoublyLinkedList<>();

		// Add all people to queue
		for(int i = 0; i < persons.length; i++){
			personQueue.enqueue(persons[i]);
		}

		// If nobody was added, return empty list
		if(personQueue.isEmpty()){
			return people;
		}

		// while the queue still has people
		while(personQueue.size() > 1){

			// Queue and dequeue until k - 1
			for(int i = 1; i < k; i++){
				String temp = personQueue.dequeue();
				personQueue.enqueue(temp);
			}
			people.addLast(personQueue.dequeue());
		}
		people.addLast(personQueue.dequeue());

		return people;
	}	
}
