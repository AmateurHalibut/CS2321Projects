package cs2321;

import static cs2321.PostfixExpression.evaluate;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PostfixExpressionTest {

	private String testString = "";

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEvaluate1() {
		testString = "4 20 5 + * 6 -";
		assertEquals("evaluate(testString) is 94)", 94, evaluate(testString));
	}
	
	@Test
	public void testEvaluate2() throws Throwable{
		testString = "1 0 /";

		{
			boolean thrown = false;
			try {
				evaluate(testString);
			} catch (Throwable t) {
				thrown = true;
				assertThat("evaluate(testString) throws exception", t, org.hamcrest.CoreMatchers.instanceOf(ArithmeticException.class));
			}
			if (!thrown) {
				org.junit.Assert.fail("evaluate(testString) throws exception: Expected Throwable ArithmeticException");
			}
		}
	}
	
	@Test
	public void testEvaluate3() {
		testString = "1 -1 +";
		assertEquals("evaluate(testString) is 0)", 0, evaluate(testString));
	}
	
	@Test
	public void testEvaluate4() {
		testString = "1 -1 *";
		assertEquals("evaluate(testString) is -1)", -1, evaluate(testString));
	}
	
	@Test
	public void testEvaluate5() {
		testString = "1 -1 /";
		assertEquals("evaluate(testString) is -1)", -1, evaluate(testString));
	}
	
	@Test
	public void testEvaluate6() {
		testString = "4 20 5 + * 6 -";
		assertEquals("evaluate(testString) is 94)", 94, evaluate(testString));
	}
	
	@Test
	public void testEvaluate7() {
		testString = "1 1 1 + + 1 +";
		assertEquals("evaluate(testString) is 4)", 4, evaluate(testString));
	}
	
	@Test
	public void testEvaluate8() {
		testString = "-1 -1 -1 + + -1 +";
		assertEquals("evaluate(testString) is -4)", -4, evaluate(testString));
	}

}
