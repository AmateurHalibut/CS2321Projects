package cs2321;

public class InPlaceSelectionSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place selection sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n^2)")
	public void sort(K[] array) {

		for(int i = 0; i < array.length - 1; i++){

			int minIndex = i;
			for(int j = i + 1; j < array.length; j++){
				if(array[j].compareTo(array[minIndex]) < 0){
					minIndex = j;
				}
			}

			if(minIndex != i){
				swap(array, i, minIndex);
			}
		}

	}

	/**
	 * Swaps 2 elements of an array
	 * @param array the array
	 * @param i first index
	 * @param j second index
	 */
	private void swap(K[] array, int i, int j){
		K temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

}
