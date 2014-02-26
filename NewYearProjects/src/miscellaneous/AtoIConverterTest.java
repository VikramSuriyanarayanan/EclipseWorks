package miscellaneous;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AtoIConverterTest {

	@Test
	public void testAtoi() {
		assertEquals(79, AtoIConverter.atoi("79"));
	}

	@Test
	public void testAtoi_Negative() {
		assertEquals(-79, AtoIConverter.atoi("-79"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAtoi_NullError() {
		AtoIConverter.atoi(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAtoi_EmptyError() {
		AtoIConverter.atoi("");
	}

}
