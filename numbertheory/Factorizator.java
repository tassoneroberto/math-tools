package numbertheory;

import java.util.LinkedList;

public class Factorizator {

	public static LinkedList<Integer> primeFactorsLL(int n) {
		LinkedList<Integer> factors = new LinkedList<Integer>();
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0) {
				factors.add(i);
				n = n / i;
				i--;
			}
		}
		if (n != 1)
			factors.add(n);
		return factors;
	}

	public static int[] primeFactorsArray(int n) {
		LinkedList<Integer> primeFactorsLL = primeFactorsLL(n);
		int[] primeFactors = new int[primeFactorsLL.size()];
		int index = 0;
		for (Integer i : primeFactorsLL) {
			primeFactors[index] = i;
			index++;
		}
		return primeFactors;
	}

	public static void main(String[] args) {
		System.out.println(primeFactorsLL(64));
		int[] f = primeFactorsArray(64);
		for (int i = 0; i < f.length; i++)
			System.out.println(f[i]);
	}
}
