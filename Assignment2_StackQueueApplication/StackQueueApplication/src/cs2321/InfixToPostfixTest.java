package cs2321;

import static cs2321.InfixToPostfix.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InfixToPostfixTest {

	private String testString = "";

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testConvert1() throws Throwable{
		testString = "( 1 + 2 * 3 ) * 4";
		assertEquals("testString.convert() converts to postfix"," 1 2 3 * + 4 *", convert(testString));
	}
	
	@Test
	public void testConvert2() {
		testString = "1";
		assertEquals("testString.convert() converts to postfix"," 1", convert(testString));
	}

	@Test
	public void testConvert3() {
		testString = "0 ( 1 )3 29( 2";
		assertEquals("testString.convert() converts to postfix","Invalid", convert(testString));
	}
	
	@Test
	public void testConvert4() {
		testString = "( 0.1 + 0.1 + 0.1 )";
		assertEquals("testString.convert() converts to postfix"," 0.1 0.1 + 0.1 +", convert(testString));
	}
	
	@Test
	public void testConvert5() {
		testString = "( 0.1 * 0.1 + 0.1 )";
		assertEquals("testString.convert() converts to postfix"," 0.1 0.1 * 0.1 +", convert(testString));
	}
	
	@Test
	public void testConvert6() {
		testString = "1 + ( ( 2 * ( 1 + 1 ) - 3 ) - 2 ) + 1";
		assertEquals("testString.convert() converts to postfix"," 1 2 1 1 + * 3 - 2 - + 1 +", convert(testString));
	}
	
	@Test
	public void testConvert7() {
		testString = "-1 + ( ( -2 * ( -1 + -1 ) - -3 ) - -2 ) + -1";
		assertEquals("testString.convert() converts to postfix"," -1 -2 -1 -1 + * -3 - -2 - + -1 +", convert(testString));
	}
	
	@Test
	public void testConvert8() {
		testString = "-0.1 + ( ( -0.2 * ( -0.1 + -0.1 ) - -0.3 ) - -0.2 ) + -0.1";
		assertEquals("testString.convert() converts to postfix"," -0.1 -0.2 -0.1 -0.1 + * -0.3 - -0.2 - + -0.1 +", convert(testString));
	}
}
