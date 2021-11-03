import java.util.*;

public abstract class QuickSort {
    private static final Random random = new Random();

    protected int partition(List<Integer> data, int left, int right) {
        int m = left + random.nextInt(right - left + 1);
        int m_value = data.get(m);

        int i = left;
        int j = right;
        while (i <= j) {
            while (data.get(i) < m_value)
                i++;
            while (data.get(j) > m_value)
                j--;
            if (i >= j)
                break;
            Collections.swap(data, i++, j--);
        }
        return j;
    }

    public void sort(List<Integer> data){
        sort(data, 0, data.size() - 1);
    }

    public abstract void sort(List<Integer> data, int left, int right);
}
