package cs2321;

import java.util.Comparator;

public class MaximumComparator<K> implements Comparator<K> {
    public int compare(K a, K b) throws ClassCastException {
        return ((Comparable <K>) a).compareTo(b) * -1;
    }
}
