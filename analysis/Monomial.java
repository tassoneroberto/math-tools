package analysis;

public class Monomial {
	private int coefficient;
	private int exponent;

	public Monomial(int exponent, int coefficient) {
		if (coefficient == 0)
			throw new IllegalArgumentException("coefficient = 0");
		if (exponent < 0)
			throw new IllegalArgumentException("exponent < 0");
		this.coefficient = coefficient;
		this.exponent = exponent;
	}

	public Monomial(Monomial m) {
		this(m.getExponent(), m.getCoefficient());
	}

	public int getCoefficient() {
		return coefficient;
	}

	public int getExponent() {
		return exponent;
	}

	public Monomial setCoefficient(int c) {
		this.coefficient = c;
		return this;
	}

	public Monomial setExponent(int e) {
		this.exponent = e;
		return this;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (coefficient > 0)
			sb.append("+");
		if (coefficient == 1 && exponent == 0)
			return "1";
		if (coefficient != 1)
			sb.append(coefficient);
		if (exponent != 0) {
			sb.append("x");
			if (exponent != 1)
				sb.append("^" + exponent);
		}
		return sb.toString();
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
		Monomial m = (Monomial) o;

		if (m.getCoefficient() != this.exponent || m.getExponent() != this.exponent)
			return false;
		return true;
	}

	public Monomial derive() {
		if (exponent == 0)
			coefficient = 0;
		else {
			coefficient *= exponent;
			exponent -= exponent;
		}
		return this;
	}

	public int hashCode() {
		return 13 * exponent * coefficient;
	}
}
