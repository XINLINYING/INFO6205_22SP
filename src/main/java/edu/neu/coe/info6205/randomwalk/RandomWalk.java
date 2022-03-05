/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalk {

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        // TO BE IMPLEMENTED
    	x = x + dx;
    	y = y + dy;
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        // TO BE IMPLEMENTED
    	for(int i = 0; i< m;i++)
    		randomMove();
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // TO BE IMPLEMENTED
    	double d = Math.sqrt(Math.pow(0-x, 2) + Math.pow(0-y, 2));
    	if(d < 0)
    		throw new UnsupportedOperationException("Not implemented yet");
    	return d;
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }

    public static void main(String[] args) {
        if (args.length < 0)
            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
//        int m = Integer.parseInt(args[0]);
//        int n = 30;
        List<Double> d_statistic = new ArrayList<>();
        List<Double> n_statistic = new ArrayList<>();
        
        for(int w = 10; w <= 1010; w++) {
        	int m = w;
        	double sumOfDistance = 0;
        	for(int y = 10; y<= 12; y++) {
        		int n = y;
        		double meanDistance = randomWalkMulti(m,n);
        		System.out.println(m + "steps: " + meanDistance + " over " + n + " experiments ");
        		sumOfDistance +=  meanDistance;
        		System.out.println(m + "steps: " + sumOfDistance );
        	}
        	d_statistic.add(sumOfDistance/3);
    		n_statistic.add(Double.valueOf(m));
        	System.out.println(m + "steps: " + sumOfDistance/100);
        }
        System.out.println("d_statistic" + d_statistic);
        System.out.println("n_statistic" + n_statistic);
        
//        if (args.length > 1) n = Integer.parseInt(args[1]);
//        double meanDistance = randomWalkMulti(m, n);
//        System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");
        
    }

}
