import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends QuickSort {
    private int BLOCK_SIZE = 1000;

    public ParallelQuickSort() {}

    public ParallelQuickSort(int block_size) {
        this.BLOCK_SIZE = block_size;
    }

    public int getBlockSize() {
        return BLOCK_SIZE;
    }

    public void setBlockSize(int block_size) {
        this.BLOCK_SIZE = block_size;
    }

    @Override
    public void sort(List<Integer> data, int left, int right) {
        SortAction sortAction = new SortAction(data, left, right);
        sortAction.fork();
        sortAction.join();
    }

    private class SortAction extends RecursiveAction {
        private final List<Integer> data;
        private final int left;
        private final int right;

        public SortAction(List<Integer> data, int left, int right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (right - left < BLOCK_SIZE) {
                new SequenceQuickSort().sort(data, left, right);
                return;
            }

            int m = partition(data, left, right);
            SortAction leftAction = new SortAction(data, left, m);
            SortAction rightAction = new SortAction(data, m + 1, right);

            leftAction.fork();
            rightAction.fork();

            leftAction.join();
            rightAction.join();
        }
    }
}
