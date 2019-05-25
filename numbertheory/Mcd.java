package numbertheory;

import java.util.LinkedList;
import java.util.ListIterator;

public class Mcd {

	public static int mcd(int a, int b) {
		if (a == 0 || b == 0)
			return 0;
		LinkedList<Integer> factorA = Factorizator.primeFactorsLL(a);
		LinkedList<Integer> factorB = Factorizator.primeFactorsLL(b);
		int mcd = 1;
		for (Integer f : factorA) {
			if (factorB.contains(new Integer(f))) {
				mcd *= f;
				factorB.remove(new Integer(f));
			}
		}
		return mcd;
	}

	public static int mcdLL(LinkedList<Integer> numbers) {
		LinkedList<Integer> firstFactors = Factorizator.primeFactorsLL(numbers.removeFirst());
		int mcd = 1;
		for (Integer factor : firstFactors) {
			boolean commonAll = true;
			for (Integer n : numbers) {
				if (n % factor != 0) {
					commonAll = false;
				}
			}
			if (commonAll) {
				mcd *= factor;
				for (final ListIterator<Integer> i = numbers.listIterator(); i.hasNext();) {
					final Integer element = i.next();
					i.set(element / factor);
				}
			}
		}
		return mcd;
	}

	public static int mcdArray(int[] numbers) {
		int[] firstFactors = Factorizator.primeFactorsArray(numbers[0]);
		int mcd = 1;
		for (int i = 0; i < firstFactors.length; i++) {
			boolean commonAll = true;
			for (int j = 1; j < numbers.length; j++) {
				if (numbers[j] % firstFactors[i] != 0) {
					commonAll = false;
				}
			}
			if (commonAll) {
				mcd *= firstFactors[i];
				for (int k = 1; k < numbers.length; k++) {
					numbers[k] /= firstFactors[i];
				}
			}
		}
		return mcd;
	}

	public static void main(String[] args) {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.add(8);
		ll.add(64);
		ll.add(16);
		int[] array = { 16, 8, 64 };
		System.out.println(Mcd.mcdLL(ll));
		System.out.print(Mcd.mcdArray(array));
	}
}
