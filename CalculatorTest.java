import static org.junit.Assert.*;

import org.junit.Test;


public class CalculatorTest {

	@Test
	public void test1() {
		Calculator calcTest = new Calculator();
		assertEquals("4+1 = 5",5, calcTest.add(4, 1));//if it fails the message will be outputted in the failure trace
	}
	@Test
	public void test2() {
		Calculator calcTest = new Calculator();
		assertEquals("2/0 = -999",-999, calcTest.divide(2, 0));
	}
	@Test
	public void test3() {
		Calculator calcTest = new Calculator();
		assertEquals("2*3 = 6",6, calcTest.multiply(2, 3));
	}
	@Test
	public void test4() {
		Calculator calcTest = new Calculator();
		assertEquals("3-1 = 2",2, calcTest.subtract(3, 1));
	}

}
