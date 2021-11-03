import java.util.List;

public class SequenceQuickSort extends QuickSort {

    @Override
    public void sort(List<Integer> data, int left, int right) {
        if (left < right) {
            int m = partition(data, left, right);
            sort(data, left, m);
            sort(data, m + 1, right);
        }
    }
}
