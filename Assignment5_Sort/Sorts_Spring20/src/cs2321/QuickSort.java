package cs2321;

public class QuickSort<E extends Comparable<E>> implements Sorter<E> {

	/**
	 * sort - Perform a divide and conquer quick sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n^2)")
	@TimeComplexityExpected("O(n lg n)")
	public void sort(E[] array) {
		quickSort(array, 0, array.length - 1);
	}

	/**
	 * Helper method for quick sort
	 * @param array array to be sorted
	 * @param p left index
	 * @param q right index
	 */
	private void quickSort(E[] array, int p, int q){
		if(p < q){
			// Partition the array and return a partition index
			int r = partition(array, p, q);

			// Recursively call quickSort on left and right partitions
			quickSort(array, p, r - 1);
			quickSort(array, r + 1, q);
		}

	}

	/**
	 * Partitions the array based on 2 given indeces
	 * @param array to be sorted
	 * @param p left index
	 * @param q right index
	 * @return partition index
	 */
	private int partition(E[] array, int p, int q){

		E pivot = array[q];
		int i = p;
		int j = q - 1;

		while(i <= j){
			// Loop until the i pointer passes the j pointer or until element is greater than the pivot
			while(i <= j && array[i].compareTo(pivot) < 0){
				i++;
			}
			// Loop until the j pointer passes the i pointer or until element is less than the pivot
			while(j >= i && array[j].compareTo(pivot) > 0){
				j--;
			}

			// if i hasn't passed j, swap the elements
			if(i <= j){
				swap(array, i, j);
				i++;
				j--;
			}
		}

		// Swap the element at i with the pivot
		swap(array, i, q);
		return i;

	}

	/**
	 * Swaps 2 elements of an array
	 * @param array the array
	 * @param i first index
	 * @param j second index
	 */
	private void swap(E[] array, int i, int j){
		E temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
