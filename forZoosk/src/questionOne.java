public class questionOne {

	public static void main(String[] args) {
		// There is a function that scans an array of characters for the character 'e' and prints out each index.
		questionOne objectOne = new questionOne(); 
		char[] arrayOfCharacters = {'H','e','l','l','o'};
		objectOne.printCharacters(arrayOfCharacters);
	}

	public void printCharacters(char[] arrayOfCharacters) {
		System.out.print("Given Input is: ");
		for(int a=0;a<arrayOfCharacters.length;a++)
			System.out.print(arrayOfCharacters[a]);

		System.out.println();

		for(int i = 0;i<arrayOfCharacters.length;i++){
			if(Character.toString(arrayOfCharacters[i]).equals("e"))
				System.out.println("Character Position where String 'e' occurs : "+i);
		}

	}

}
