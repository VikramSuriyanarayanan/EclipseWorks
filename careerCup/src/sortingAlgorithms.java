public class sortingAlgorithms {

	/**
	 * ALL TYPES OF SORTINGS.... 
	 */
	private static int[] a = new int[50];
	private static int arraySize = 5;

	public static void main(String[] args) {		
		for(int i = 0; i < arraySize; i++){
			a[i]= (int) (Math.random()*10);
		}

		System.out.println("Printing Array Elements before sorting");
		printArray();
		//BUBBLE SORT
	//	bubbleSort();
		// INSERTION SORT
	//	insertionSort();
		// SELECTION SORT
		selectionSort();

		System.out.println("After sorting: ");
		printArray();
	}
	
	public static void selectionSort(){
		for(int i = 0; i< arraySize;i++){
			int minimum = a[i];
			for(int j=i+1;j<arraySize;j++){
				if(a[j] < minimum){
					int temp;
					temp = a[j];
					a[j] = minimum;
					minimum = temp; 
				}
			}
			a[i] = minimum;
		}
	}

	public static void bubbleSort(){
		for(int i = arraySize-1; i > 0;i--){
			for(int j = 0; j < i;j++){
				if(a[j]>a[j+1]){
					swapValues(j,j+1);
				}
			}
		}
	}

	public static void swapValues(int firstIndex, int secondIndex){
		int temp;
		temp  = a[firstIndex];
		a[firstIndex] = a[secondIndex];
		a[secondIndex] = temp;
	}

	public static void insertionSort(){
		for(int i = 1 ; i < arraySize;i++){
			int j = i;
			int valueToInsert = a[i];

			while(j > 0 && a[j-1]> valueToInsert){
				a[j]=a[j-1];
				j--;
			}
			a[j] = valueToInsert;
		}
	}		

	public static void printArray(){
		for(int i = 0; i < arraySize; i++){
			System.out.println("a["+i+"]"+ a[i]);
		}
	}
}