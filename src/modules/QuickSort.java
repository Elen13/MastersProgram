package modules;

import java.util.ArrayList;
import java.util.Random;

public class QuickSort {
	
	static private double average = 0.0;
	
	static public double getMid(){
		return average;
	}
	
	public ArrayList<Double> quicksort(ArrayList<Double> input){
		
		if(input.size() <= 1){
			return input;
		}
		
		int middle = (int) Math.ceil((double)input.size() / 2);
		double pivot = input.get(middle);

		ArrayList<Double> less = new ArrayList<Double>();
		ArrayList<Double> greater = new ArrayList<Double>();
		
		double sum = 0.0;
		
		for (int i = 0; i < input.size(); i++) {
			
			sum = sum + input.get(i);
			
			if(input.get(i) <= pivot){
				if(i == middle){
					continue;
				}
				less.add(input.get(i));
			}
			else{
				greater.add(input.get(i));
			}
		}
		
		average = sum/input.size();
		
		return concatenate(quicksort(less), pivot, quicksort(greater));
	}
	
	/**
	 * Join the less array, pivot integer, and greater array
	 * to single array.
	 * @param less integer ArrayList with values less than pivot.
	 * @param pivot the pivot integer.
	 * @param greater integer ArrayList with values greater than pivot.
	 * @return the integer ArrayList after join.
	 */
	public ArrayList<Double> concatenate(ArrayList<Double> less, double pivot, ArrayList<Double> greater){
		
		ArrayList<Double> list = new ArrayList<Double>();
		
		for (int i = 0; i < less.size(); i++) {
			list.add(less.get(i));
		}
		
		list.add(pivot);
		
		for (int i = 0; i < greater.size(); i++) {
			list.add(greater.get(i));
		}
		
		return list;
	}
	
	public ArrayList<Double> generateRandomNumbers(int n){
		
	    ArrayList<Double> list = new ArrayList<Double>(n);
	    Random random = new Random();
		
	    for (int i = 0; i < n; i++) {
	    	double el = random.nextInt(n * 10)/7.2;
		    list.add(el);
	    }
	
	    return list;
	}

}
