package numbertheory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;

public class PrimeNumbers {
	static LinkedList<Integer> primes;
	static BigInteger maxPrime;

	public static void main(String[] args) {
		greatestLeftTruncatablePrime();
		System.out.println("Greatest left truncatable prime: " + maxPrime);
		greatestRightTruncatablePrime();
		System.out.println("Greatest right truncatable prime: " + maxPrime);

	}

	public static void greatestLeftTruncatablePrime() {
		maxPrime = new BigInteger("" + 0);
		addDigitLeft(new BigInteger("" + 2));
		addDigitLeft(new BigInteger("" + 3));
		addDigitLeft(new BigInteger("" + 5));
		addDigitLeft(new BigInteger("" + 7));
	}

	public static void greatestRightTruncatablePrime() {
		maxPrime = new BigInteger("" + 0);
		addDigitRight(new BigInteger("" + 2));
		addDigitRight(new BigInteger("" + 3));
		addDigitRight(new BigInteger("" + 5));
		addDigitRight(new BigInteger("" + 7));
	}

	private static void addDigitLeft(BigInteger n) {
		if (n.isProbablePrime(1)) {
			for (int i = 1; i < 10; i++) {
				addDigitLeft(new BigInteger("" + i + n));
			}
		} else {
			BigInteger primeFound = new BigInteger((n.toString()).substring(1, (n.toString()).length()));
			if (primeFound.compareTo(maxPrime) > 0) {
				maxPrime = primeFound;
			}
		}
	}

	private static void addDigitRight(BigInteger n) {
		if (n.isProbablePrime(1)) {
			for (int i = 1; i < 10; i++) {
				addDigitRight(new BigInteger("" + n + i));
			}
		} else {
			BigInteger primeFound = new BigInteger((n.toString()).substring(0, (n.toString()).length() - 1));
			if (primeFound.compareTo(maxPrime) > 0) {
				maxPrime = primeFound;
			}
		}
	}

	public static void primesGenerator() {
		BufferedWriter writer = null;
		System.out.println("[Prime Numbers Finder]");
		primes = new LinkedList<Integer>();
		File primesFile = new File("primes.txt");
		if (primesFile.exists()) {
			System.out.print("Reading the file 'primes.txt'... ");
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader("primes.txt"));
				String line;
				while ((line = reader.readLine()) != null) {
					primes.add(Integer.parseInt(line));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(primes.size() + " prime numbers loaded!");
			try {
				writer = new BufferedWriter(new FileWriter("primes.txt", true), 32768);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.print("Creating the file 'primes.txt'... ");
			try {
				writer = new BufferedWriter(new FileWriter("primes.txt", true), 32768);
				writer.write(2 + System.lineSeparator() + 3);
				primes.add(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		int n = primes.getLast() + 2;
		int newPrimesLimit = 1000000;
		Date startTime = new Date();
		System.out.println("Searching for " + newPrimesLimit + " new prime numbers...");
		while (newPrimesLimit > 0) {
			if (isPrimeBasedOnPrecList(n)) {
				try {
					writer.write(System.lineSeparator() + n);
				} catch (IOException e) {
					e.printStackTrace();
				}
				primes.add(n);
				newPrimesLimit--;
				// System.out.println(n);
			}
			n += 2;
		}
		Date endTime = new Date();
		long diff = endTime.getTime() - startTime.getTime();

		System.out.println("Latest prime number found: " + primes.getLast() + ". Execution time: " + diff + "ms");

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isPrimeBasedOnPrecList(int n) {
		int root = (int) Math.sqrt(n);
		for (Integer p : primes) {
			if (p >= root) {
				return true;
			}
			if (n % p == 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean isPrime(int num) {
		if (num == 1 || (num > 2 && num % 2 == 0)) {
			return false;
		}
		int top = (int) Math.sqrt(num) + 1;
		for (int i = 3; i < top; i += 2) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	private static boolean isPrime(long num) {
		if (num == 1 || (num > 2 && num % 2 == 0)) {
			return false;
		}
		int top = (int) Math.sqrt(num) + 1;
		for (int i = 3; i < top; i += 2) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
}
