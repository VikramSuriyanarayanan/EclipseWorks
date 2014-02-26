public class E
{
 private int aNumber;
  private static int anotherNumber;
  public E(int t)
  {
    System.out.println("A's constructor is invoked");
   aNumber = t;
   anotherNumber++;
  }
 
  public static int getAnotherNumber()
  {
  return anotherNumber;
  }
}