import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    private static int LIMIT = (int) 1e8;
    private static int ATTEMPTS_COUNT = 5;

    public static void main(String[] args) {
        long startTime;
        long endTime;
        long allParTime = 0;
        long allSeqTime = 0;

        QuickSort parallelQuickSort = new ParallelQuickSort();
        QuickSort sequenceQuickSort = new SequenceQuickSort();


        System.out.println("Block size: " + ((ParallelQuickSort) parallelQuickSort).getBlockSize());
        System.out.println("Array size: " + LIMIT);
        System.out.println("Attempts count: " + ATTEMPTS_COUNT + "\n\n");

        for (int i = 0; i < ATTEMPTS_COUNT; i++) {
            System.out.println("Sort number: " + i);
            List<Integer> parList = new Random().ints().limit(LIMIT).boxed().collect(Collectors.toList());
            List<Integer> seqList = new ArrayList<>(parList);

            startTime = System.nanoTime();
            parallelQuickSort.sort(parList);
            endTime = System.nanoTime();
            System.out.println("Parallel sorting time: " + (endTime - startTime) + " nanoseconds.");
            allParTime += endTime - startTime;

            startTime = System.nanoTime();
            sequenceQuickSort.sort(seqList);
            endTime = System.nanoTime();
            System.out.println("Sequence sorting time: " + (endTime - startTime) + " nanoseconds.\n");
            allSeqTime += endTime - startTime;
        }

        long aveParTime = allParTime / ATTEMPTS_COUNT;
        long aveSeqTime = allSeqTime / ATTEMPTS_COUNT;

        System.out.println("\nAverage parallel sorting time: " + aveParTime + " nanoseconds.");
        System.out.println("Average sequence sorting time: " + aveSeqTime + " nanoseconds.");
        System.out.println("\nParallel sort " + ((double) aveSeqTime / (double) allParTime) + " times faster than Sequence sort.");
    }
}
