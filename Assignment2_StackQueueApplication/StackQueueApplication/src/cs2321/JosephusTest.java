package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JosephusTest {

	private String[] testString1 = {"A"};
	private String[] testString3 = new String[] {};
	private Josephus josephus = new Josephus();

	private java.util.ArrayList testList1 = new java.util.ArrayList();
	private java.util.ArrayList testList2 = new java.util.ArrayList();


	@Before
	public void setUp() throws Exception {
		testList1.add("C");
			testList1.add("A");
			testList1.add("E");
			testList1.add("B");
			testList1.add("D");

		testList2.add("A");

	}

	@Test
	public void testOrder1() throws Throwable{
		String[] testString2 = {"A", "B", "C", "D", "E"};
		DoublyLinkedList<String> ordered = josephus.order(testString2, 3);

		java.util.ArrayList<String> list = new java.util.ArrayList<>(5);
		for (String n :
			ordered ) {
			list.add(n);
		}


		assertArrayEquals("ordered is testList1", testList1.toArray(new String[5]), list.toArray(new String[5]));

	}

	@Test
	public void testOrder2() throws Throwable{
		DoublyLinkedList<String> ordered = josephus.order(testString3, 3);

		assertEquals("ordered list is empty", true, ordered.isEmpty());
	}

	@Test
	public void testOrder3() throws Throwable{

		{
			boolean thrown = false;
			try {
				josephus.order(testString1, -1);
			} catch (Throwable t) {
				thrown = true;
				assertThat("josephus.order(testString1, -1) throws exception", t, org.hamcrest.CoreMatchers.instanceOf(IllegalArgumentException.class));
			}
			if (!thrown) {
				org.junit.Assert.fail("josephus.order(testString1, -1) throws exception: Expected Throwable IllegalArgumentException");
			}
		}

	}

	@Test
	public void testOrder4() throws Throwable{
		String[] testString4 = {"A"};
		DoublyLinkedList<String> ordered = josephus.order(testString4, 2);

		java.util.ArrayList<String> list = new java.util.ArrayList<>(1);
		for (String n :
				ordered ) {
			list.add(n);
		}


		assertArrayEquals("ordered is testList1", testList2.toArray(new String[1]), list.toArray(new String[1]));
	}


	@Test
	public void testOrder5() throws Throwable{

		{
			boolean thrown = false;
			try {
				josephus.order(testString1, 0);
			} catch (Throwable t) {
				thrown = true;
				assertThat("josephus.order(testString1, 0) throws exception", t, org.hamcrest.CoreMatchers.instanceOf(IllegalArgumentException.class));
			}
			if (!thrown) {
				org.junit.Assert.fail("josephus.order(testString1, 0) throws exception: Expected Throwable IllegalArgumentException");
			}
		}

	}
}
