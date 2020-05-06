package cs2321;

public class InPlaceInsertionSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place insertion sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n^2)")
	public void sort(K[] array) {
		for(int i = 1; i < array.length; i++){
			K cur = array[i];

			int j = i - 1;
			while(j >= 0 && array[j].compareTo(cur) > 0){
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = cur;
		}
	}

}
