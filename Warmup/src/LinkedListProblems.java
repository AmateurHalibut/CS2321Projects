/**
 * Alex Hromada
 * Warmup Assignment
 * Class containing linked list warmup problems
 */
public class LinkedListProblems {

	/*
	Given a sorted linked list, delete all duplicates such that each element appear only once.

	Example 1:
	Input: 1->1->2
	Output: 1->2

	Example 2:
	Input: 1->1->2->3->3
	Output: 1->2->3
	 */

    /**
     * Deletes duplicates from a singly linked list
     *
     * @param head
     * @return head node of list with duplicates removed
     */
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode current = head;
        while (current.next != null) {
            if (current.val == current.next.val && current.next != null) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }


	/*
	 * Reverse a singly linked list.
		Example:
		Input: 1->2->3->4->5->NULL
		Output: 5->4->3->2->1->NULL
	 */

    /**
     * Iteratively reverses a singly linked list
     *
     * @param head
     * @return head node of the reversed list
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode curr = head;
        ListNode prev = null;
        ListNode next = null;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        head = prev;

        return head;
    }
}
