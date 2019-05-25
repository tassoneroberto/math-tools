package analysis;

import java.util.Iterator;
import java.util.LinkedList;

public class PolynomialLL implements Iterable<Monomial> {
	private LinkedList<Monomial> monomials = new LinkedList<Monomial>();

	public PolynomialLL(Monomial m) {
		monomials.add(new Monomial(m));
	}

	public PolynomialLL(PolynomialLL p) {
		for (Monomial m : p) {
			monomials.add(new Monomial(m));
		}
	}

	@Override
	public Iterator<Monomial> iterator() {
		return monomials.iterator();
	}

	public void add(Monomial m) {
		boolean added = false;
		for (Monomial m1 : monomials) {
			if (m1.getExponent() == m.getExponent()) {
				Monomial sum = monomials.remove(monomials.indexOf(m1));
				sum.setCoefficient(sum.getCoefficient() + m.getCoefficient());
				monomials.add(sum);
				added = true;
				break;
			}
		}
		if (!added)
			monomials.add(m);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Monomial m : monomials) {
			sb.append(m);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Monomial m1 = new Monomial(2, 2);
		Monomial m2 = new Monomial(4, 5);
		PolynomialLL p1 = new PolynomialLL(m1);
		System.out.println(p1);
		p1.add(m2);
		System.out.println(p1);
	}
}
