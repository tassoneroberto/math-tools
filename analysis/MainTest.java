package analysis;

import java.util.Scanner;

public class MainTest {
	public static void main(String[] args) {
		/*
		 * Monomial m1 = new Monomial(0, 2); Monomial m2 = new Monomial(2, 3); Monomial
		 * m3 = new Monomial(3, 2); Monomial m4 = new Monomial(1, 2); Polynomial p1 =
		 * new Polynomial(m1); p1.add(m2); Polynomial p2 = new Polynomial(m3);
		 * p2.add(m4); System.out.println("p1=" + p1); System.out.println("p1'=" +
		 * p1.derive()); System.out.println("p2=" + p2); System.out.println("p2'=" +
		 * p2.derive()); FractionalPolynomial f1 = new FractionalPolynomial(p1, p2);
		 * System.out.println("f1=" + f1); System.out.println("f1'="+f1.derive());
		 * Polynomial p1=new Polynomial(3,6); p1.add(1,8); Polynomial p2=new
		 * Polynomial(0,20); p2.add(1, 4); System.out.println("p1="+p1+" "+p1.solve());
		 * System.out.println("p2="+p2+" "+p2.solve()); FractionalPolynomial f1=new
		 * FractionalPolynomial(p1); f1.div(p2); System.out.println("f="+f1);
		 * System.out.println("f'="+f1.derive());
		 */
		Scanner sc = new Scanner(System.in);
		String s;
		while (true) {
			System.out.print("Insert expression: ");
			s = sc.nextLine();
			if (FractionalPolynomial.expressionValid(s)) {
				System.out.println("valid");
			} else
				System.out.println("invalid");
		}
	}
}
