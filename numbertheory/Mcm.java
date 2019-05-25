package numbertheory;

import java.util.HashMap;
import java.util.LinkedList;

public class Mcm {

	public static int mcm(int a, int b) {
		LinkedList<Integer> factorsA = Factorizator.primeFactorsLL(a);
		LinkedList<Integer> factorsB = Factorizator.primeFactorsLL(b);
		HashMap<Integer, Integer> maxFactors = new HashMap<Integer, Integer>();
		for (Integer fa : factorsA) {
			if (maxFactors.containsKey(fa))
				maxFactors.put(fa, maxFactors.get(fa) + 1);
			else
				maxFactors.put(fa, 1);
		}
		for (Integer fb : factorsB) {
			if (maxFactors.containsKey(fb))
				maxFactors.put(fb, maxFactors.get(fb) + 1);
			else
				maxFactors.put(fb, 1);
		}
		int mcm = 1;
		for (Integer key : maxFactors.keySet()) {
			mcm *= Math.pow(key, maxFactors.get(key));
		}
		return mcm;
	}

	public static void main(String[] args) {
		System.out.println(mcm(8, 3));
	}

}
