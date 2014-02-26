
public class isUniq {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("IS It so ??" +isUniqueChars("PERIYA THALA"));
	}
	
	public static boolean isUniqueChars(String str) {
	    int checker = 0;
	    for (int i = 0; i < str.length(); ++i) {
	        int val = str.charAt(i) - 'a';
	        if ((checker & (1 << val)) > 0) return false;
	        checker |= (1 << val);
	    }
	    return true;
	}

}
