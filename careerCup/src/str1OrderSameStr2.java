
public class str1OrderSameStr2 {

	/**
	 * @is order of str1 = "abc" same as str2 = "xaybzc" ? return true 
	 */
	public static void main(String[] args) {

		String s1 = "abc";
		String s2 = "axybzc";
		System.out.println(compare(s1,s2));
		int[] array = {1,2,3,4,4,5};
		array = dupRem(array);
		for(int i = 0; i < array.length;i++)
		System.out.println(array[i]);

	}

	public static int[] dupRem(int[] array) {
		int[] arr =new int[6];
		boolean flag = false;
		int k = 0;
		for(int i = 0; i< array.length;i++){
			for(int j =i+1;j<array.length;j++){
				if(array[i]==array[j]){
					flag = false;
					break;
				}
				else
					flag = true;
			}
			if(flag){
				arr[k]=array[i];
				k++;
			}

		}
		return arr;
	}

	public static boolean compare(String s1, String s2) {

		int begIndex = 0;
		for(int i=0; i<s2.length(); ++i){
			int pos = s1.indexOf(s2.charAt(i), begIndex);
			System.out.println("POSITION at "+s2.charAt(i)+":"+pos);
			if(pos == -1)	return false;
			begIndex = pos;
		}

		return true;
	}

}
