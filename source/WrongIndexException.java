/**
 * This is a checked exception class. The exception is thrown when the index for ArrayList pokemonList is
 * not a valid index.
 * 
 * @author Nikolas Beltran
 * @version 2015.05.30
 */
public class WrongIndexException extends Exception
{
    private int index;

    /**
     * Store the details in error.
     */
    public WrongIndexException(int index)
    {
        this.index = index;
    }

    /**
     * @return  index   The index in error.
     */
    public int returnBadIndex()
    {
        return index;
    }
    
    /**
     * @return  String  A diagnostic string containing the index in error.
     */
    public String toString()
    {
        return "There is no Pokemon listed beyond this point.";
    }
}
