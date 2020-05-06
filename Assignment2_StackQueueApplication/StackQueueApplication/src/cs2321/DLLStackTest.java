package cs2321;

import jug.SuiteName;
import jug.TestName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuiteName("Test DLLStack {\"One\", \"Two\", \"Three\", \"Four\", \"Five\", \"Seven\", \"Eight\"}")
public class DLLStackTest {

    private DLLStack<String> TARGET = init();
    private DLLStack<String> T = init();

    public DLLStack<String> init(){
        return new DLLStack<String>();
    }

    @Before
    public void setUp() throws Throwable {
        /* Push data to the stack */
        T.push("One");
        T.push("Two");
        T.push("Three");
        T.push("Four");
        T.push("Five");

    }

    @Test(timeout=12000)
    @TestName("size() is 5")
    public void test1() throws Throwable {
        assertEquals("size() is 5", (Object) (5), (Object)(T.size()));
    }

    @Test
    @TestName("size() is 0")
    public void test2() throws Throwable {
        T.pop();
        T.pop();
        T.pop();
        T.pop();
        T.pop();

        assertEquals("size() is 0", (Object) (0), (Object)(T.size()));
    }

    @Test
    @TestName("size() is 0")
    public void test3() throws Throwable {
        T.pop();
        T.pop();
        T.pop();
        T.pop();
        T.pop();
        T.pop();

        assertEquals("size() is 0", (Object) (0), (Object)(T.size()));
    }

    @Test
    @TestName("top() is \"Five\"")
    public void test4() throws Throwable {
        T.top();

        assertEquals("top() is \"Five\"", (Object) ("Five"), (Object)(T.top()));
    }

    @Test
    @TestName("top() is \"Five\"")
    public void test5() throws Throwable {
        T.pop();
        T.pop();
        T.push("Seven");
        T.push("Eight");
        T.top();

        assertEquals("top() is \"Eight\"", (Object) ("Eight"), (Object)(T.top()));
    }

    @Test
    @TestName("top() is \"Seven\"")
    public void test6() throws Throwable {
        T.pop();
        T.pop();
        T.push("Seven");
        T.push("Eight");
        T.pop();
        T.top();

        assertEquals("top() is \"Seven\"", (Object) ("Seven"), (Object)(T.top()));
    }

}