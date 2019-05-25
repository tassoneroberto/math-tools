package analysis;

import java.util.LinkedList;

import numbertheory.Factorizator;

public class FractionalPolynomial {
	Polynomial numerator;
	Polynomial denominator;

	public FractionalPolynomial(Polynomial n, Polynomial d) {
		this.numerator = new Polynomial(n);
		this.denominator = new Polynomial(d);
		simplify();
	}

	public FractionalPolynomial(FractionalPolynomial f) {
		this(f.getNumerator(), f.getDenominator());
	}

	public FractionalPolynomial(Polynomial n) {
		this(n, new Polynomial(0, 1));
	}

	public FractionalPolynomial(Monomial m) {
		this(new Polynomial(m));
	}

	public FractionalPolynomial(int exp, int coeff) {
		this(new Monomial(exp, coeff));
	}

	public FractionalPolynomial add(FractionalPolynomial f) {
		this.denominator = this.denominator.mul(f.getDenominator());
		Polynomial newNum1 = this.numerator.mul(f.getDenominator());
		Polynomial newNum2 = f.getNumerator().mul(this.getDenominator());
		this.numerator = newNum1.add(newNum2);
		simplify();
		return this;
	}

	public FractionalPolynomial sub(FractionalPolynomial f) {
		this.denominator = this.denominator.mul(f.getDenominator());
		Polynomial newNum1 = this.numerator.mul(f.getDenominator());
		Polynomial newNum2 = f.getNumerator().mul(this.getDenominator()).mul(0, -1);
		this.numerator = newNum1.add(newNum2);
		simplify();
		return this;
	}

	public FractionalPolynomial add(Polynomial p) {
		return add(new FractionalPolynomial(p));
	}

	public FractionalPolynomial sub(Polynomial p) {
		return add(new FractionalPolynomial(p.mul(0, 1)));
	}

	public FractionalPolynomial add(Monomial m) {
		return add(new FractionalPolynomial(m));
	}

	public FractionalPolynomial sub(Monomial m) {
		Monomial negMon = new Monomial(m);
		negMon.setCoefficient(m.getCoefficient() * -1);
		return add(new FractionalPolynomial(negMon));
	}

	public FractionalPolynomial add(int exp, int coeff) {
		return add(new FractionalPolynomial(exp, coeff));
	}

	public FractionalPolynomial sub(int exp, int coeff) {
		return add(new FractionalPolynomial(exp, coeff * (-1)));
	}

	public FractionalPolynomial mul(FractionalPolynomial f) {
		this.numerator = this.getNumerator().mul(f.getNumerator());
		this.denominator = this.getDenominator().mul(f.getDenominator());
		simplify();
		return this;
	}

	public FractionalPolynomial mul(Polynomial p) {
		this.mul(new FractionalPolynomial(p));
		return this;
	}

	public FractionalPolynomial mul(Monomial m) {
		this.mul(new FractionalPolynomial(m));
		return this;
	}

	public FractionalPolynomial mul(int exp, int coeff) {
		this.mul(new FractionalPolynomial(exp, coeff));
		return this;
	}

	public FractionalPolynomial div(FractionalPolynomial f) {
		this.numerator = this.getNumerator().mul(f.getDenominator());
		this.denominator = this.getDenominator().mul(f.getNumerator());
		simplify();
		return this;
	}

	public FractionalPolynomial div(Polynomial p) {
		this.div(new FractionalPolynomial(p));
		return this;
	}

	public FractionalPolynomial div(Monomial m) {
		this.div(new FractionalPolynomial(m));
		return this;
	}

	public FractionalPolynomial div(int exp, int coeff) {
		this.div(new FractionalPolynomial(exp, coeff));
		return this;
	}

	public Polynomial getNumerator() {
		return numerator;
	}

	public Polynomial getDenominator() {
		return denominator;
	}

	public FractionalPolynomial simplify() {
		// Simplify exponents
		int lowerNum = this.numerator.getMonomials().lastKey();
		int lowerDen = this.denominator.getMonomials().lastKey();
		int min = lowerNum > lowerDen ? lowerDen : lowerNum;
		int coeffMinNum = numerator.getMonomials().remove(lowerNum);
		Polynomial newNum = new Polynomial(lowerNum - min, coeffMinNum);
		int coeffMinDen = denominator.getMonomials().remove(lowerDen);
		Polynomial newDen = new Polynomial(lowerDen - min, coeffMinDen);
		for (Integer expNum : numerator.getMonomials().keySet()) {
			newNum.add(expNum - min, numerator.getMonomials().get(expNum));
		}
		for (Integer expDen : denominator.getMonomials().keySet()) {
			newDen.add(expDen - min, denominator.getMonomials().get(expDen));
		}
		// Simplify coefficients
		LinkedList<LinkedList<Integer>> commonTerms = new LinkedList<LinkedList<Integer>>();
		for (Integer exp : newNum.getMonomials().keySet()) {
			commonTerms.add(Factorizator.primeFactorsLL(newNum.getMonomials().get(exp)));
		}
		for (Integer exp : newDen.getMonomials().keySet()) {
			commonTerms.add(Factorizator.primeFactorsLL(newDen.getMonomials().get(exp)));
		}
		LinkedList<Integer> firstFactors = commonTerms.removeFirst();
		LinkedList<Integer> realCommonFactors = new LinkedList<Integer>();
		for (Integer factor : firstFactors) {
			boolean commonAll = true;
			for (LinkedList<Integer> ct : commonTerms) {
				if (!ct.contains(factor)) {
					commonAll = false;
				}
			}
			if (commonAll) {
				realCommonFactors.add(factor);
				for (LinkedList<Integer> ct : commonTerms) {
					ct.remove(factor);
				}
			}
		}
		int common = 1;
		for (Integer n : realCommonFactors) {
			common *= n;
		}
		for (Integer exp : newNum.getMonomials().keySet()) {
			newNum.getMonomials().put(exp, newNum.getMonomials().get(exp) / common);
		}
		for (Integer exp : newDen.getMonomials().keySet()) {
			newDen.getMonomials().put(exp, newDen.getMonomials().get(exp) / common);
		}
		this.numerator = newNum;
		this.denominator = newDen;
		return this;

	}

	public FractionalPolynomial derive() {
		if (numerator.getMonomials().size() == 1 && numerator.getMonomials().containsKey(0)) {
			throw new ArithmeticException("Numerator = 0");
		}
		// Copies of numerator and denominator are needed to avoid aliasing
		Polynomial copy1numerator = new Polynomial(this.numerator);
		Polynomial copy2numerator = new Polynomial(this.numerator);
		Polynomial copy1denominator = new Polynomial(this.denominator);
		Polynomial copy2denominator = new Polynomial(this.denominator);
		Polynomial newNum1 = copy1numerator.derive().mul(copy1denominator);
		Polynomial newNum2 = copy2denominator.derive().mul(copy2numerator);
		Polynomial newNum = newNum1.sub(newNum2);
		Polynomial newDen = denominator.mul(denominator);
		numerator = newNum;
		denominator = newDen;
		simplify();
		return this;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!denominator.equals(new Polynomial(new Monomial(0, 1))))
			sb.append("(" + numerator + ")/(" + denominator + ")");
		else
			sb.append(numerator);
		return sb.toString();
	}

	// TODO
	public static boolean expressionValid(String s) {
		if (s == null || s.equals(""))
			return false;
		String regex = "((\\-|\\+)(\\d)+(x\\^(\\d)))*";
		if (s.matches(regex)) {
			return true;
		}
		return false;
	}

}
