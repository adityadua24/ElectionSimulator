/**
 * 
 */
package asgn1Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Eddy
 *
 */
public class CandidateIndexTests {

	asgn1Election.CandidateIndex testCandIndex;
	
	@Before @Test
	public void setUp() {
		testCandIndex = new asgn1Election.CandidateIndex(5);
		
	}
	
	/**
	 * Test method for {@link asgn1Election.CandidateIndex#inRange(int)}.
	 */
	@Test
	public void testInRange() {
		boolean inRange = asgn1Election.CandidateIndex.inRange(7);
		assertTrue(inRange);
	}
	@Test
	public void testInRangeBoundryCondition1() {
		boolean inRange = asgn1Election.CandidateIndex.inRange(1);
		assertTrue(inRange);
	}
	@Test
	public void testInRangeBoundryCondition2() {
		boolean inRange = asgn1Election.CandidateIndex.inRange(15);
		assertTrue(inRange);
	}
	@Test
	public void testInRangeInvalidRange() {
		boolean inRange = asgn1Election.CandidateIndex.inRange(-2);
		assertFalse(inRange);
	}
	@Test
	public void testInRangeInvalidRange2() {
		boolean inRange = asgn1Election.CandidateIndex.inRange(20);
		assertFalse(inRange);
	}

	/**
	 * Test method for {@link asgn1Election.CandidateIndex#CandidateIndex(int)}.
	 */
	@Test
	public void testCandidateIndex() {
		assertEquals(5, Integer.parseInt(testCandIndex.toString()));
	}

	/**
	 * Test method for {@link asgn1Election.CandidateIndex#compareTo(asgn1Election.CandidateIndex)}.
	 */
	@Test
	public void testCompareToSame() {
		asgn1Election.CandidateIndex testCandIndex2 = new asgn1Election.CandidateIndex(5);
		assertEquals(0, (testCandIndex2.toString()).compareTo((testCandIndex).toString()));
	}
	@Test
	public void testCompareToDifferentHigher() {
		asgn1Election.CandidateIndex testCandIndex2 = new asgn1Election.CandidateIndex(7);
		assertEquals(1, (testCandIndex2.compareTo(testCandIndex)));
	}
	@Test
	public void testCompareToDifferentLower() {
		asgn1Election.CandidateIndex testCandIndex2 = new asgn1Election.CandidateIndex(2);
		assertEquals(-1, testCandIndex2.compareTo(testCandIndex));
	}
	
	/**
	 * Test method for {@link asgn1Election.CandidateIndex#copy()}.
	 */
	@Test
	public void testCopy() {
		asgn1Election.CandidateIndex cdICopy = testCandIndex.copy();
		assertEquals(Integer.parseInt(cdICopy.toString()), Integer.parseInt(testCandIndex.toString()));
	}

	/**
	 * Test method for {@link asgn1Election.CandidateIndex#incrementIndex()}.
	 */
	@Test
	public void testIncrementIndex() {
		testCandIndex.incrementIndex();
		assertEquals(Integer.parseInt(testCandIndex.toString()), 6);
	}

	/**
	 * Test method for {@link asgn1Election.CandidateIndex#setValue(int)}.
	 */
	@Test
	public void testSetValue() {
		testCandIndex.setValue(8);
		assertEquals(8, Integer.parseInt(testCandIndex.toString()));
	}

	/**
	 * Test method for {@link asgn1Election.CandidateIndex#toString()}.
	 */
	@Test
	public void testToString() {
		String str = "5";
		assertEquals(0, str.compareTo(testCandIndex.toString()));
	}

}
