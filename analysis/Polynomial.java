package analysis;

import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeMap;

public class Polynomial {
	private TreeMap<Integer, Integer> monomials = new TreeMap<Integer, Integer>(Collections.reverseOrder());

	public Polynomial(int exp, int coeff) {
		if (coeff == 0)
			throw new IllegalArgumentException("coefficient = 0");
		if (exp < 0)
			throw new IllegalArgumentException("exponent < 0");
		monomials.put(exp, coeff);
	}

	public Polynomial(Monomial m) {
		this(m.getExponent(), m.getCoefficient());
	}

	public Polynomial(Polynomial p) {
		for (Integer exp : p.getMonomials().keySet()) {
			if (monomials.containsKey(exp))
				monomials.put(exp, monomials.get(exp) + p.getMonomials().get(exp));
			else
				monomials.put(exp, p.getMonomials().get(exp));
		}
	}

	public TreeMap<Integer, Integer> getMonomials() {
		return monomials;
	}

	public Polynomial add(Monomial m) {
		if (monomials.containsKey(m.getExponent()))
			monomials.put(m.getExponent(), monomials.get(m.getExponent()) + m.getCoefficient());
		else
			monomials.put(m.getExponent(), m.getCoefficient());
		return this;
	}

	public Polynomial add(int exp, int coeff) {
		return add(new Monomial(exp, coeff));
	}

	public Polynomial add(Polynomial p) {
		for (Integer exp : p.getMonomials().keySet()) {
			this.add(new Monomial(exp, p.getMonomials().get(exp)));
		}
		return this;
	}

	public Polynomial sub(Monomial m) {
		if (monomials.containsKey(m.getExponent())) {
			if (m.getCoefficient() != monomials.get(m.getExponent()))
				monomials.put(m.getExponent(), monomials.get(m.getExponent()) - m.getCoefficient());
			else
				monomials.remove(m.getExponent());
		} else
			monomials.put(m.getExponent(), m.getCoefficient());
		return this;
	}

	public Polynomial sub(int exp, int coeff) {
		return sub(new Monomial(exp, coeff));
	}

	public Polynomial sub(Polynomial p) {
		for (Integer exp : p.getMonomials().keySet()) {
			this.sub(new Monomial(exp, p.getMonomials().get(exp)));
		}
		return this;
	}

	public Polynomial mul(Polynomial p) {
		LinkedList<Polynomial> partialSums = new LinkedList<Polynomial>();
		for (Integer exp : monomials.keySet()) {
			partialSums.add(new Polynomial(p).mul(new Monomial(exp, monomials.get(exp))));
		}
		Polynomial product = new Polynomial(partialSums.removeFirst());
		for (Polynomial ps : partialSums) {
			product.add(ps);
		}
		this.monomials = product.monomials;
		return this;
	}

	public Polynomial mul(Monomial m) {
		TreeMap<Integer, Integer> product = new TreeMap<Integer, Integer>();
		for (Integer exp : monomials.keySet()) {
			Integer newCoeff = monomials.get(exp) * m.getCoefficient();
			Integer newExp = exp + m.getExponent();
			product.put(newExp, newCoeff);
		}
		this.monomials = product;
		return this;
	}

	public Polynomial mul(int exp, int coeff) {
		return mul(new Polynomial(new Monomial(exp, coeff)));
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Integer exp : monomials.keySet()) {
			int coefficient = monomials.get(exp);
			if (coefficient > 0)
				sb.append("+");
			if (coefficient == 1 && exp == 0)
				sb.append("1");
			else {
				if (coefficient != 1)
					sb.append(coefficient);
				if (exp != 0) {
					sb.append("x");
					if (exp != 1)
						sb.append("^" + exp);
				}
			}
		}
		return sb.toString();
	}

	public Polynomial derive() {
		int firstExp = this.getMonomials().lastKey();
		int firstCoeff = this.getMonomials().remove(firstExp);
		Polynomial derived;
		if (firstExp != 0)
			derived = new Polynomial(firstExp - 1, firstCoeff * firstExp);
		else {
			firstExp = this.getMonomials().lastKey();
			firstCoeff = this.getMonomials().remove(firstExp);
			derived = new Polynomial(firstExp - 1, firstCoeff * firstExp);
		}
		for (Integer exp : monomials.keySet()) {
			int coeff = monomials.get(exp);
			derived.add(exp - 1, coeff * exp);
		}
		this.monomials = derived.getMonomials();
		return this;
	}

	public LinkedList<Double> solve() {
		LinkedList<Double> solutions = new LinkedList<Double>();
		int maxExp = monomials.firstKey();
		switch (maxExp) {
		case 1:
			if (monomials.size() == 1)
				solutions.add(new Double(0));
			else
				solutions.add(new Double((monomials.get(0) * (-1)) / monomials.get(1)));
			;
			break;
		case 2:
			double a = 0, b = 0, c = 0;
			a = monomials.get(2);
			if (monomials.containsKey(1))
				b = monomials.get(1);
			if (monomials.containsKey(0))
				c = monomials.get(0);
			double delta = Math.sqrt(b * b - 4 * a * c);
			if (delta == 0)
				solutions.add(-b / (2 * a));
			else if (delta > 0) {
				solutions.add(-b + Math.sqrt(b * b - 4 * a * c) / (2 * a));
				solutions.add(-b - Math.sqrt(b * b - 4 * a * c) / (2 * a));
			}
			;
			break;
		case 3:
			/*
			 * a = 0; b = 0; c = 0; double d = 0; a = monomials.get(3); if
			 * (monomials.containsKey(2)) b = monomials.get(2); if
			 * (monomials.containsKey(1)) c = monomials.get(1); if
			 * (monomials.containsKey(0)) d = monomials.get(0); double r = c / (3 * a);
			 * double p = -b / (3 * a); double q = Math.pow(p, 3) + (b * c - 3 * a * d) / (6
			 * * a * a); double x = Math.pow((q + Math.pow((q * q + Math.pow((r - p * p),
			 * 3)), 1 / 2)), 1 / 3) + Math.pow((q - Math.pow((q * q + Math.pow((r - p * p),
			 * 3)), 1 / 2)), 1 / 3) + p; solutions.add(x); System.out.println("a="+a+
			 * " b="+b+" c="+c+" d="+d); break; default: ;
			 */
			break;

		}
		return solutions;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		Polynomial p = (Polynomial) o;

		if (monomials.size() != p.getMonomials().size())
			return false;
		for (Integer exp : p.getMonomials().keySet()) {
			if (!monomials.containsKey(exp))
				return false;
			else if (monomials.get(exp) != p.getMonomials().get(exp))
				return false;
		}
		return true;
	}
}