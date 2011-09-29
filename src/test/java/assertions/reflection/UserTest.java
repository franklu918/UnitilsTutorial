package assertions.reflection;


import org.junit.runner.RunWith;
//import org.testng.annotations.Test;
import org.junit.Test;
import junit.framework.AssertionFailedError;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;

import static org.unitils.reflectionassert.ReflectionComparatorMode.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

/**
 * @author Sergey Ponomarev
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserTest {

    @Test
    public void testUserEquals() {
        User user1 = new User(1, "John", "Doe");
        User user2 = new User(1, "John", "Doe");
        assertReflectionEquals(user1, user2);
    }

    @Test
    public void testPrimitiveEquals() {
        assertReflectionEquals(1, 1L);
        assertReflectionEquals(1, new Float(1.0));
    }

    @Test
    public void testListAssertions() {
        List<Double> myList = new ArrayList<Double>();
        myList.add(1.0);
        myList.add(2.0);
        assertReflectionEquals(Arrays.asList(1, 2), myList);
    }

//    @Test(expectedExceptions = {AssertionFailedError.class})
    @Test(expected = AssertionFailedError.class)
    public void testListAssertionsFailOnIncorrectOrder() {
        List<Double> myList = new ArrayList<Double>();
        myList.add(1.0);
        myList.add(2.0);
        assertReflectionEquals(Arrays.asList(2, 1), myList);
    }

    /**
     * Lenient order
     * A first type of leniency you can specify is ignoring the order of elements in a collection or array. When working with lists, you are often not interested in the actual order of the elements. For example, code for retrieving all bank-accounts with a negative balance will typically return them in an order that is unimportant for the actual processing.
     * To implement this behavior, the ReflectionAssert.assertReflectionEquals method can be configured to ignore ordering by supplying it the ReflectionComparatorMode.LENIENT_ORDER comparator mode:
     */
    @Test
    public void testListLenient() {
        List<Double> myList = new ArrayList<Double>();
        myList.add(1.0);
        myList.add(2.0);
        assertReflectionEquals(Arrays.asList(2, 1), myList, LENIENT_ORDER);
    }

    /**
     * Ignoring defaults
     * A second type of leniency is specified by the ReflectionComparatorMode.IGNORE_DEFAULTS mode.
     * When this mode is set, java default values, like null for objects and 0 or false for values are ignored.
     * In other words, only the fields that you instantiate in your expected objects are used in the comparison.
     */
    @Test()
    public void testIgnoringDefaults() {
        User actualUser = new User(1, "John", "Doe", new Address("First street", 12, "Brussels"));
        User expectedUser = new User(1, "John", null, new Address("First street", 0, null));
        assertReflectionEquals(expectedUser, actualUser, IGNORE_DEFAULTS);
    }

    /**
     * You specify that you want to ignore a field by setting this value to null in the left (=expected) instance.
     * Right-instance fields that have default values will still be compared.
     */
//    @Test(expectedExceptions = {AssertionFailedError.class})
    @Test(expected = AssertionFailedError.class)
    public void testSiding() {
        User actualUser = new User(1, "John", null, new Address("First street", 0, null));
        User expectedUser = new User(1, "John", "Doe", new Address("First street", 12, "Brussels"));
        assertReflectionEquals(expectedUser, actualUser, IGNORE_DEFAULTS);
    }

    /**
     * Lenient dates
     * A third type of leniency is ReflectionComparatorMode.LENIENT_DATES.
     * This will assert that the date field values in both instances are both set or both equal to null;
     * the actual values of the dates are ignored. This can be useful if you want to do strict checking on the fields
     * of objects (without using ReflectionComparatorMode.IGNORE_DEFAULTS), but there are fields in your object set to
     * the current date or time that you want to ignore.
     */
    @Test
    public void testLenientDates() {
        Date actualDate = new Date(44444);
        Date expectedDate = new Date();
        assertReflectionEquals(expectedDate, actualDate, LENIENT_DATES);
    }

//    @Test(expectedExceptions = {AssertionFailedError.class})
    @Test(expected = AssertionFailedError.class)
    public void testAssertLenientEquals() throws Exception {
        assertLenientEquals(null, "any");  // Succeeds
        assertLenientEquals("any", null);  // Fails
    }
    
}
