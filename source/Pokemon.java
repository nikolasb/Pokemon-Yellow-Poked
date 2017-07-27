/**
 * This class represents a Pokemon. It holds the Pokemon's name and Pokedex enrtry (taken from Pokemon Yellow).
 * 
 * @author Nikolas Beltran
 * @version 2015.05.30
 */
public class Pokemon extends PokemonTemplate
{
    private String name;
    private String pokedexEntry;

    /**
     * Constructor for objects of class Pokemon
     */
    public Pokemon(String name, String pokedexEntry)
    {
        this.name = name;
        this.pokedexEntry = pokedexEntry;
    }

    /**
     * Returns the Pokedex entry.
     * 
     * @return  The Pokedex entry associated with the Pokemon.
     */
    public String returnPokedexEntry()
    {
        return name + ": " + pokedexEntry;
    }
}
