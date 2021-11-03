import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    private static int BLOCK_SIZE = 1000;
    private static int LIMIT = (int) 1e8;
    private static int ATTEMPTS_COUNT = 5;

    private static String timesToString(List<Long> times){
        String timesString = times.stream().map(String::valueOf).collect(Collectors.joining(" ms, "));
        return timesString + " ms";
    }

    public static void main(String[] args) {
        long startTime;
        long endTime;
        long allParTime = 0;
        long allSeqTime = 0;

        System.out.println("Block size: " + BLOCK_SIZE);
        System.out.println("Array size: " + LIMIT);
        System.out.println("Attempts count: " + ATTEMPTS_COUNT + "\n\n");

        QuickSort parallelQuickSort = new ParallelQuickSort(BLOCK_SIZE);
        QuickSort sequenceQuickSort = new SequenceQuickSort();

        List<Long> parTimes = new ArrayList<>();
        List<Long> seqTimes = new ArrayList<>();

        for (int i = 0; i < ATTEMPTS_COUNT; i++) {
            List<Integer> parList = new Random().ints().limit(LIMIT).boxed().collect(Collectors.toList());
            List<Integer> seqList = new ArrayList<>(parList);

            startTime = System.nanoTime();
            parallelQuickSort.sort(parList);
            endTime = System.nanoTime();
            long parTime = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            parTimes.add(parTime);
            allParTime += parTime;

            startTime = System.nanoTime();
            sequenceQuickSort.sort(seqList);
            endTime = System.nanoTime();
            long seqTime = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            seqTimes.add(seqTime);
            allSeqTime += seqTime;
        }

        long aveParTime = allParTime / ATTEMPTS_COUNT;
        long aveSeqTime = allSeqTime / ATTEMPTS_COUNT;

        System.out.println("Parallel sorting times: " + timesToString(parTimes) + ".");
        System.out.println("Sequence sorting times: " + timesToString(seqTimes) + ".");

        System.out.println("\nAverage parallel sorting time: " + aveParTime + " ms.");
        System.out.println("Average sequence sorting time: " + aveSeqTime + " ms.");
        System.out.println("\nParallel sort " + ((double) aveSeqTime / (double) aveParTime) + " times faster than Sequence sort.");
    }
}
