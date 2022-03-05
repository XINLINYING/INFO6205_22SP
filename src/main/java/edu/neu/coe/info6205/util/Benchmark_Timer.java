/*
 * Copyright (c) 2018. Phasmid Software
 */

package edu.neu.coe.info6205.util;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import edu.neu.coe.info6205.sort.simple.InsertionSort;

import static edu.neu.coe.info6205.util.Utilities.formatWhole;

/**
 * This class implements a simple Benchmark utility for measuring the running time of algorithms.
 * It is part of the repository for the INFO6205 class, taught by Prof. Robin Hillyard
 * <p>
 * It requires Java 8 as it uses function types, in particular, UnaryOperator&lt;T&gt; (a function of T => T),
 * Consumer&lt;T&gt; (essentially a function of T => Void) and Supplier&lt;T&gt; (essentially a function of Void => T).
 * <p>
 * In general, the benchmark class handles three phases of a "run:"
 * <ol>
 *     <li>The pre-function which prepares the input to the study function (field fPre) (may be null);</li>
 *     <li>The study function itself (field fRun) -- assumed to be a mutating function since it does not return a result;</li>
 *     <li>The post-function which cleans up and/or checks the results of the study function (field fPost) (may be null).</li>
 * </ol>
 * <p>
 * Note that the clock does not run during invocations of the pre-function and the post-function (if any).
 *
 * @param <T> The generic type T is that of the input to the function f which you will pass in to the constructor.
 */
public class Benchmark_Timer<T> implements Benchmark<T> {

    /**
     * Calculate the appropriate number of warmup runs.
     *
     * @param m the number of runs.
     * @return at least 2 and at most m/10.
     */
    static int getWarmupRuns(int m) {
        return Integer.max(2, Integer.min(10, m / 10));
    }

    /**
     * Run function f m times and return the average time in milliseconds.
     *
     * @param supplier a Supplier of a T
     * @param m        the number of times the function f will be called.
     * @return the average number of milliseconds taken for each run of function f.
     */
    @Override
    public double runFromSupplier(Supplier<T> supplier, int m) {
        logger.info("Begin run: " + description + " with " + formatWhole(m) + " runs");
        // Warmup phase
        final Function<T, T> function = t -> {
            fRun.accept(t);
            return t;
        };
        new Timer().repeat(getWarmupRuns(m), supplier, function, fPre, null);

        // Timed phase
        return new Timer().repeat(m, supplier, function, fPre, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun, Consumer<T> fPost) {
        this.description = description;
        this.fPre = fPre;
        this.fRun = fRun;
        this.fPost = fPost;
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun) {
        this(description, fPre, fRun, null);
    }

    /**
     * Constructor for a Benchmark_Timer with only fRun and fPost Consumer parameters.
     *
     * @param description the description of the benchmark.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, Consumer<T> fRun, Consumer<T> fPost) {
        this(description, null, fRun, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer where only the (timed) run function is specified.
     *
     * @param description the description of the benchmark.
     * @param f           a Consumer function (i.e. a function of T => Void).
     *                    Function f is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, Consumer<T> f) {
        this(description, null, f, null);
    }

    private final String description;
    private final UnaryOperator<T> fPre;
    private final Consumer<T> fRun;
    private final Consumer<T> fPost;

    final static LazyLogger logger = new LazyLogger(Benchmark_Timer.class);
    /**
     * get the RandomArray
     *
     * @param size the size of randomArray
     */
    private static Integer[] getRandomArray(int size){
        Random random = new Random();
        Integer[] randomArray = new Integer[size];
        for(int i = 0; i < size; i++){
            randomArray[i] = random.nextInt();
        }
        return randomArray;
    }

    /**
     * get the orderedArray
     *
     * @param size the size of orderedArray
     */
    private static Integer[] getOrderedArray(int size){
        Integer[] orderedArray = new Integer[size];
        for(int i = 0; i < size; i++){
            orderedArray[i] = i;
        }
        return orderedArray;
    }

    /**
     * get the partially-ordered Array
     *
     * @param size the size of partially-ordered
     */
    private static Integer[] getPartiallyOrderedArray(int size){
        Integer[] partiallyOrderedArray = new Integer[size];
        Random random = new Random();
        for(int i = 0; i < size; i++){
            if(i < size / 2){
                partiallyOrderedArray[i] = random.nextInt();
            }else{
                partiallyOrderedArray[i] = i;
            }
        }
        return partiallyOrderedArray;
    }

    /**
     * get the reverseOrderedArray Array
     *
     * @param size the size of ReverseOrderedArray
     */
    private static Integer[] getReverseOrderedArray(int size){
        Integer[] reverseOrderedArray = new Integer[size];
        for(int i = 0; i < size; i++){
            reverseOrderedArray[i] = size - i;
        }
        return reverseOrderedArray;
    }
    
    public static void main(String[] args) {
    	
    	System.out.println("Benchmark_Timer task starts!");
    	System.out.println("size of array, random, ordered, partially-ordered, reverse-ordered:");

        for(int m = 100 ;m <= 100000; m += 100){
            Integer[] randomArray = getRandomArray(m);
            Integer[] orderedArray = getOrderedArray(m);
            Integer[] partiallyOrderedArray = getPartiallyOrderedArray(m);
            Integer[] reverseOrderedArray = getReverseOrderedArray(m);

            String description = "Insertion Sort";
        	UnaryOperator<Integer[]> fPre = array -> {return array.clone();};
        	Consumer<Integer[]> fRun = array -> new InsertionSort<Integer>().sort(array,0,array.length);
        	Consumer<Integer[]> fPost = array -> {};
        	Benchmark_Timer<Integer[]> bt = new Benchmark_Timer<>(description, fPre, fRun, fPost);
        	
            double time1 = bt.run(randomArray, m);
            double time2 = bt.run(orderedArray, m);
            double time3 = bt.run(partiallyOrderedArray, m);
            double time4 = bt.run(reverseOrderedArray, m);
            
            System.out.println(m+" randomArray:"+time1+" orderedArray:"+time2+" partiallyOrderedArray:"+time3+" reverseOrderedArray:"+time4);
        }
    	
		System.out.println("Benchmark_Timer task complete!");
	}
}
