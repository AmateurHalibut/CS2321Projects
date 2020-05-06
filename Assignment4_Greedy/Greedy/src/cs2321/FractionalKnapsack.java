package cs2321;

import net.datastructures.Entry;

/**
 * Implementation of the Fractional Knapsack problem
 *
 * Course: CS2321 Section ALL
 * Assignment: #4
 * @author Alex Hromada
 */
public class FractionalKnapsack {

   
	/**
	 * Goal: Choose items with maximum total benefit but with weight at most W.
	 *       You are allowed to take fractional amounts from items.
	 *       
	 * @param items items[i][0] is weight for item i
	 *              items[i][1] is benefit for item i
	 * @param knapsackWeight
	 * @return The maximum total benefit. Please use double type operation. For example 5/2 = 2.5
	 * 		 
	 */
	public static double MaximumValue(int[][] items, int knapsackWeight) {

		HeapPQ<Double, Double> pq = new HeapPQ<>(new MaximumComparator<>());

		for(int i = 0; i < items.length; i++){
			pq.insert((double) items[i][1] / (double) items[i][0], (double) items[i][0]);
		}

		double w = 0;
		double unitValue = 0;
		while(w < knapsackWeight){
			if(pq.isEmpty()){							// No items left, break from loop
				break;
			}
			Entry<Double, Double> nextItem = pq.min();	// Get the max item
			w++;										// Update weight in knapsack
			unitValue = unitValue + nextItem.getKey();	// Update unit value
			pq.replaceValue(pq.min(), nextItem.getValue() - 1);	// Update weight for item
			if(pq.min().getValue() == 0){				// Remove item if weight is 0
				pq.removeMin();
			}
		}

		return unitValue;
	}
}
