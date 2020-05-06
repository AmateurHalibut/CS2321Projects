
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Alex Hromada
 * Warmup Assignment
 * Test class for linked list problems
 */
public class LinkedListProblemsTest {

    ListNode T;
    ListNode head;

    /* The common test data to be used is the list T: 1->1->2->3->3
     */
    @Before
    public void setUp() throws Exception {

        int[] testdata = {1, 1, 2, 3, 3};
        T = new ListNode(testdata[1]);
        head = T;
        for (int i = 1; i < testdata.length; i++) {
            ListNode tmp = new ListNode(testdata[i]);
            T.next = tmp;
            T = T.next;
        }
    }

    /*
    Given a sorted linked list, delete all duplicates such that each element appear only once.

    Example 1:
    Input: 1->1->2->3->3->null
    Output: 1->2->3->null
    */
    @Test
    public void testDeleteDuplicates() {
        int[] expected = {1, 2, 3};
        ListNode newT = LinkedListProblems.deleteDuplicates(head);

        ListNode p = newT;
        int i = 0;
        while (p != null) {
            assertEquals(expected[i], p.val);
            i = i + 1;
            p = p.next;
        }
    }


    /*
     * Reverse a singly linked list.
        Example:
        Input: 1->1->2->3->3->NULL
        Output: 3->3->2->1->1->NULL
     */
    @Test
    public void testReverseList() {
        int[] expected = {3, 3, 2, 1, 1};
        ListNode newT = LinkedListProblems.reverseList(head);

        ListNode p = newT;
        int i = 0;
        while (p != null) {
            assertEquals(expected[i], p.val);
            i = i + 1;
            p = p.next;
        }
    }

}
