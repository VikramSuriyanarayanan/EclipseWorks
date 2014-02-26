package hashTableImplementation;

public class hashTable<K,V>{

	/* take the capacity as prime number to reduce the collision */
	private static int size = 31;

	/* initialize array to store value */
	private V[] tablevalues = (V[]) new Object[size];

	// ************* PUT METHOD of HASHTABLE ********************* //
	public synchronized V put(K key, V value){
		if(value == null){
			throw(new NullPointerException());
		}

		System.out.println("hashCode(): "+key.hashCode());
		int index = hash(key.hashCode()) % size;
		tablevalues[index] = value;
		return value;
	}
	
	// **************** GET METHOD of HASHTABLE ******************* //
	public synchronized V get(K key){
		int index = hash(key.hashCode()) % size;
		return tablevalues[index];
	}

	public synchronized int hash(int h){
		System.out.println("before hashing: "+ h);
		h ^= (h >>> 20) ^ (h >>> 12);
		System.out.println("after performing h^= (h >>> 20) ^ (h >>> 12): "+ h);
		return h ^ (h >>> 7) ^ (h >>> 4);

	}

	public static void main(String[] args){

		hashTable<String,String> h1 = new hashTable<String, String>();
		h1.put("hi"+1, "val"+3);
		System.out.println(h1.get("hi"+1));
	}
}