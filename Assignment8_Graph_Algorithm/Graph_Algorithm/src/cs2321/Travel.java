package cs2321;

import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.Vertex;


/**
 * @author Ruihong Zhang
 * Reference textbook R14.16 P14.81
 *
 *  Alex Hromada
 *  Assignment 8
 *  This classes implements DFS, BFS, and Dijkstra's algorithms to solve travel problems
 *
 */
public class Travel {

	AdjListGraph<String,Integer> graph;
	HashMap<String, Vertex<String>> cityMap = new HashMap<>();
	
	/**
	 * @param routes: Array of routes between cities. 
	 *                routes[i][0] and routes[i][1] represent the city names on both ends of the route. 
	 *                routes[i][2] represents the cost in string type. 
	 *                Hint: In Java, use Integer.valueOf to convert string to integer. 
	 */
	public Travel(String [][] routes) {
		graph = new AdjListGraph<>();
		for(int i = 0; i < routes.length; i++){
			graph.insertEdge(getVertex(routes[i][0]),
					getVertex(routes[i][1]),
					Integer.valueOf(routes[i][2]));
		}

	}

    /**
     * Helper method to check for dupe vertices when adding to graph
     * @param name
     * @return
     */
	private Vertex<String> getVertex(String name){
		if(cityMap.get(name) == null){
			cityMap.put(name, graph.insertVertex(name));
		}
		return cityMap.get(name);
	}



	
	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @return Return the path from departure city to destination using Depth First Search algorithm. 
	 *         The path should be represented as ArrayList or DoublylinkedList of city names. 
	 *         The order of city names in the list should match order of the city names in the path.  
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              	              
	 *              See the method sortedOutgoingEdges below. 
	 */
	public Iterable<String> DFSRoute(String departure, String destination ) {

		ArrayList<String> names = new ArrayList<>();
		HashMap<Vertex<String>, Boolean> visited = new HashMap<>();
		HashMap<Vertex<String>, Edge<Integer>> forest = new HashMap<>();

		DFSRoute(departure, destination, visited, forest);

		Vertex<String> destVert = cityMap.get(destination);
		putToList(destVert, departure, names, forest);
		
		return names;
	}

    /**
     * Recursive helper method for DFS algorithm
     * @param departure
     * @param destination
     * @param visited
     * @param forest
     */
	private void DFSRoute(String departure, String destination,
						  HashMap<Vertex<String>, Boolean> visited,
						  HashMap<Vertex<String>, Edge<Integer>> forest){
		// Grab the starting vertex
		Vertex<String> v = cityMap.get(departure);
		// Set vertex as visited
		visited.put(v, true);
		// Sort edges
		Iterable<Edge<Integer>> edges = sortedOutgoingEdges(v);
		for (Edge<Integer> edge:
				edges) {
			// Grab opposite vertex
			Vertex<String> u = graph.opposite(v, edge);
			// Check if opposite has been visited
			if(visited.get(u) == null){
				// Add to the forest
				forest.put(u, edge);
				// Move to next node
				DFSRoute(u.getElement(), destination, visited, forest);
			}
		}
	}

    /**
     * Adds vertices in the DFS path to a list
     * @param v
     * @param departure
     * @param names
     * @param forest
     */
	private void putToList(Vertex<String> v, String departure, ArrayList<String> names, HashMap<Vertex<String>, Edge<Integer>> forest){
		if(v.getElement() == departure){
			names.addFirst(v.getElement());
			return;
		}
		names.addFirst(v.getElement());
		v = graph.opposite(v, forest.get(v));
		putToList(v, departure, names, forest);

	}


	/**
	 * @param departure: the departure city name
	 * @param destination: the destination city name
     * @return Return the path from departure city to destination using Breadth First Search algorithm.
	 *         The path should be represented as ArrayList or DoublylinkedList of city names.
	 *         The order of city names in the list should match order of the city names in the path.
	 *
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              	             
	 *              See the method sortedOutgoingEdges below. 
	 */
	
	public Iterable<String> BFSRoute(String departure, String destination ) {

		ArrayList<String> names = new ArrayList<>();
		HashMap<Vertex<String>, Boolean> visited = new HashMap<>();
		HashMap<Vertex<String>, Edge<Integer>> forest = new HashMap<>();

		BFSRoute(departure, destination, visited, forest);

		Vertex<String> departVert = cityMap.get(destination);
		putToList(departVert, departure, names, forest);

		return names;
	}

    /**
     * BFS method with main algorithm
     * @param departure
     * @param destination
     * @param visited
     * @param forest
     */
	private void BFSRoute(String departure, String destination,
						  HashMap<Vertex<String>, Boolean> visited,
						  HashMap<Vertex<String>, Edge<Integer>> forest ){
		CircularArrayQueue<Vertex<String>> queue = new CircularArrayQueue<>();
		Vertex<String> a = cityMap.get(departure);
		visited.put(a, true);
		queue.enqueue(a);
		while (!queue.isEmpty()){
			Vertex<String> v = queue.dequeue();
			// Sort edges
			Iterable<Edge<Integer>> edges = sortedOutgoingEdges(v);
			for (Edge<Integer> edge :
				 edges) {
				Vertex<String> w = graph.opposite(v, edge);
				if(visited.get(w) == null){
					visited.put(w, true);
					queue.enqueue(w);
					forest.put(w, edge);
				}
			}
		}
	}
	
	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @param itinerary: an empty DoublylinkedList object will be passed in to the method. 
	 * 	       When a shorted path is found, the city names in the path should be added to the list in the order. 
	 * @return return the cost of the shortest path from departure to destination. 
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              
	 *              See the method sortedOutgoingEdges below. 
	 */

	public int DijkstraRoute(String departure, String destination, DoublyLinkedList<String> itinerary ) {
		
		HashMap<Vertex<String>, Integer> d = new HashMap<>();
		HashMap<Vertex<String>, Integer> cloud = new HashMap<>();

		HeapPQ<Integer, Vertex<String>> pq = new HeapPQ<>();
		HashMap<Vertex<String>, Entry<Integer, Vertex<String>>> pqTokens = new HashMap<>();

		Vertex<String> src = cityMap.get(departure);

		// Put all of the vertices to a map and set the start vertex value to zero and the rest to infinity (int max)
		for(Vertex<String> vertex
				: graph.vertices()){
			if(vertex == src){
				d.put(vertex, 0);
			} else{
				d.put(vertex, Integer.MAX_VALUE);
			}
			pqTokens.put(vertex, pq.insert(d.get(vertex), vertex));
		}


		while(!pq.isEmpty()){
			Entry<Integer, Vertex<String>> entry = pq.removeMin();
			int key = entry.getKey();
			Vertex<String> u = entry.getValue();
			cloud.put(u, key);
			pqTokens.remove(u);
			// Sort edges
			Iterable<Edge<Integer>> edges = sortedOutgoingEdges(u);
			for (Edge<Integer> e:
				 edges ) {
				Vertex<String> v = graph.opposite(u, e);
				if(cloud.get(v) == null){
					// Relaxation Step
					int wgt = e.getElement();
					if(d.get(u) + wgt < d.get(v)) {
						d.put(v, d.get(u) + wgt);
						pq.replaceKey(pqTokens.get(v), d.get(v));
					}
				}
			}
		}

		HashMap<Vertex<String>, Edge<Integer>> forest = spTree(departure, d);
		putToListDijkstra(cityMap.get(destination), departure, itinerary, forest);
		
		return d.get(cityMap.get(destination));
		
	}

    /**
     * Returns a tree containing the vertices in the shortest path
     * @param departure
     * @param d
     * @return
     */
	private HashMap<Vertex<String>, Edge<Integer>> spTree(String departure, HashMap<Vertex<String>, Integer> d ){
		HashMap<Vertex<String>, Edge<Integer>> tree = new HashMap<>();
		Vertex<String> depart = cityMap.get(departure);
		for (Vertex<String> v :
			 d.keySet()) {
			if(v != depart){
				for (Edge<Integer> e :
					 graph.incomingEdges(v)) {
					Vertex<String> u = graph.opposite(v, e);
					int wgt = e.getElement();  //  Value of the edge between the to edge and from edge
					if(d.get(v) == d.get(u) + wgt)
						tree.put(v, e);
				}
			}
		}
		return tree;
	}

    /**
     * Adds all of the vertices in the shortest path to a DLL
     * @param v
     * @param departure
     * @param itinerary
     * @param forest
     */
	private void putToListDijkstra(Vertex<String> v, String departure, DoublyLinkedList<String> itinerary, HashMap<Vertex<String>, Edge<Integer>> forest){
		if(v.getElement() == departure){
			itinerary.addFirst(v.getElement());
			return;
		}
		itinerary.addFirst(v.getElement());
		v = graph.opposite(v, forest.get(v));
		putToListDijkstra(v, departure, itinerary, forest);

	}


	/**
	 * I strongly recommend you to implement this method to return sorted outgoing edges for vertex V
	 * You may use any sorting algorithms, such as insert sort, selection sort, etc.
	 * 
	 * @param v: vertex v
	 * @return a list of edges ordered by edge's name
	 */

	public Iterable<Edge<Integer>> sortedOutgoingEdges(Vertex<String> v)  {

	    // PQ Sort
		HeapPQ<String, Vertex<String>> sortPQ = new HeapPQ<>();
		Iterable<Edge<Integer>> edges = graph.outgoingEdges(v);
		for (Edge<Integer> e:
			 edges) {
			sortPQ.insert(graph.opposite(v, e).getElement(), graph.opposite(v, e));
		}

		ArrayList<Edge<Integer>> edgeList = new ArrayList<>();
		int pqSize = sortPQ.size();

		for(int i = 0; i < pqSize; i++){
			edgeList.addLast(graph.getEdge(v, sortPQ.removeMin().getValue()));
		}

		return edgeList;
	}

}
