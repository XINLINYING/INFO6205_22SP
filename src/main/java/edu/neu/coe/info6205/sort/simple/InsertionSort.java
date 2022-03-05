/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.simple;

import java.util.Random;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.Timer;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }
    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }
    
    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
//        helper.preProcess(xs);
        for(int i = from + 1; i < to; i++) {
        	for(int j = i; j >from ;j--) {
        		if(!helper.swapStableConditional(xs,j))
        			break;
        	}
        	}
//         helper.postProcess(xs);
     }


    public static final String DESCRIPTION = "Insertion sort";
    
    private double repeatInsertionSort(int n, X[] xs) {
        InsertionSort insertionSort =new InsertionSort();
        Timer timer = new Timer();
        for (int i = 0; i < n; i++) {
            insertionSort.sort(xs, 0, xs.length);
            timer.lap();
        }
        timer.pause();
        return timer.meanLapTime();
    }

    public static void main(String[] args) {
        
    }
}
