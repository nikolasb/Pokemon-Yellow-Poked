import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * This class creates the GUI.
 * 
 * @author Nikolas Beltran
 * @version 2015.05.21
 */
public class GUIBuilder
{
    ArrayList<Pokemon> pokemonList;
    int index;
    JFrame frame;
    Container contentPane;
    JLabel imageLabel;
    JTextArea pokedexText;
    JPanel infoPanel;
    JPanel buttonPanel;
    JPanel radioButtonPanel;
    JRadioButton redColor;
    JRadioButton blueColor;
    JRadioButton greenColor;
    JRadioButton yellowColor;

    /**
     * Constructor for objects of class GUIBuilder
     */
    public GUIBuilder()
    {
        pokemonList = new ArrayList<Pokemon>();
        index = 0;
        addPokemon();
        makeFrame();
        changeColor(Color.RED, Color.WHITE);
    }

    /**
     * Creates the GUI.
     */
    private void makeFrame()
    {
        frame = new JFrame("Pokedex");
        contentPane = frame.getContentPane();
        Dimension dimension = new Dimension(550, 220);
        contentPane.setPreferredSize(dimension);

        makeMenuBar();

        setContentPaneLayout();

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Creates the menu bar.
     */
    private void makeMenuBar()
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu options = new JMenu("Options");
        menubar.add(options);

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    quit();
                }
            });
        options.add(quit);

        JMenuItem help = new JMenuItem("Help");
        help.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    help();
                }
            });
        options.add(help);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    about();
                }
            });
        options.add(about);
    }

    /**
     * Sets the all the layouts and components in the content pane.
     */
    private void setContentPaneLayout()
    {
        contentPane.setLayout(new GridLayout(3, 1));

        infoPanel = new JPanel(new FlowLayout());

        ImageIcon icon = new ImageIcon("Pokemon/Spr_1y_" + (index + 1) + ".png");
        imageLabel = new JLabel(icon);
        imageLabel.setOpaque(true);
        infoPanel.add(imageLabel);

        pokedexText = new JTextArea(pokemonList.get(0).returnPokedexEntry(), 2, 2);
        pokedexText.setEditable(false);
        infoPanel.add(pokedexText);

        buttonPanel = new JPanel(new FlowLayout());

        Dimension buttonSize = new Dimension(127, 70);

        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    index = index - 1;
                    try
                    {
                        getPokemon(index);
                        setImage();
                    }
                    catch(WrongIndexException er)
                    {
                        pokedexText.setText(er.toString());
                        index = -1;
                        imageLabel.setIcon(new ImageIcon("Pokemon/Missingno_RB.png"));
                    }
                }
            });
        previousButton.setPreferredSize(buttonSize);
        buttonPanel.add(previousButton);

        JButton randomButton = new JButton("Random");
        randomButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    setRandomIndex();
                    try
                    {
                        getPokemon(index);
                        setImage();
                    }
                    catch(WrongIndexException er)
                    {
                        pokedexText.setText(er.toString());
                        index = -1;
                        imageLabel.setIcon(new ImageIcon("Pokemon/Missingno_RB.png"));
                    }
                }
            });
        randomButton.setPreferredSize(buttonSize);
        buttonPanel.add(randomButton);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    index = index + 1;
                    try
                    {
                        getPokemon(index);
                        setImage();
                    }
                    catch(WrongIndexException er)
                    {
                        pokedexText.setText(er.toString());
                        index = 151;
                        imageLabel.setIcon(new ImageIcon("Pokemon/Missingno_RB.png"));
                    }
                }
            });
        nextButton.setPreferredSize(buttonSize);
        buttonPanel.add(nextButton);

        JButton printButton = new JButton("Print");
        printButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        FileWriter writer = new FileWriter("Pokdex Entry");
                        writer.write(pokemonList.get(index).returnPokedexEntry());
                        writer.close();
                    }
                    catch(IOException er)
                    {
                        pokedexText.setText("There was an error writing to the file.");
                        imageLabel.setIcon(new ImageIcon("Pokemon/Missingno_RB.png"));
                    }
                }
            });
        printButton.setPreferredSize(buttonSize);
        buttonPanel.add(printButton);

        redColor = new JRadioButton("Red", true);
        blueColor = new JRadioButton("Blue");
        greenColor = new JRadioButton("Green");
        yellowColor = new JRadioButton("Yellow");

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(redColor);
        radioButtonGroup.add(blueColor);
        radioButtonGroup.add(greenColor);
        radioButtonGroup.add(yellowColor);

        radioButtonPanel = new JPanel();
        radioButtonPanel.add(redColor);
        radioButtonPanel.add(blueColor);
        radioButtonPanel.add(greenColor);
        radioButtonPanel.add(yellowColor);

        redColor.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    changeColor(Color.RED, Color.WHITE);
                }
            });

        blueColor.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    changeColor(Color.BLUE, Color.WHITE);
                }
            });

        greenColor.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    changeColor(Color.GREEN, Color.BLACK);
                }
            });

        yellowColor.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    changeColor(Color.YELLOW, Color.BLACK);
                }
            });

        contentPane.add(infoPanel);
        contentPane.add(buttonPanel);
        contentPane.add(radioButtonPanel);

    }

    /**
     * Adds a list of the first 151 Pokemon. The fields for the Pokemon objects are filled via text taken
     * from a text file.
     */
    private void addPokemon()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("Pokemon/PokemonText.txt"));
            for(int value = 1; value < 152; value++)
            {
                pokemonList.add(new Pokemon(reader.readLine(), (reader.readLine() + "\n" + reader.readLine())));
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.print("FILE NOT FOUND");
        }
        catch(IOException e)
        {
            System.out.print("ERROR");
        }
    }

    /**
     * Changes the color of the GUI components.
     */
    private void changeColor(Color backgroundColor, Color textColor)
    {
        contentPane.setBackground(backgroundColor);
        infoPanel.setBackground(backgroundColor);
        imageLabel.setBackground(backgroundColor);
        pokedexText.setBackground(backgroundColor);
        buttonPanel.setBackground(backgroundColor);
        radioButtonPanel.setBackground(backgroundColor);
        redColor.setBackground(backgroundColor);
        blueColor.setBackground(backgroundColor);
        greenColor.setBackground(backgroundColor);
        yellowColor.setBackground(backgroundColor);
        pokedexText.setForeground(textColor);
        redColor.setForeground(textColor);
        blueColor.setForeground(textColor);
        greenColor.setForeground(textColor);
        yellowColor.setForeground(textColor);
    }

    /**
     * Sets the image in the JLabel imageLabel.
     */
    private void setImage()
    {
        imageLabel.setIcon(new ImageIcon("Pokemon/Spr_1y_" + (index + 1) + ".png"));
    }

    /**
     * Gets the Pokemon at the specified index.
     * 
     * @param   index   The index number of the Pokemon in the ArrayList pokemonList.
     */
    private void getPokemon(int index) throws WrongIndexException
    {
        if(index > -1 && index < 151)
        {
            Pokemon holder = pokemonList.get(index);
            pokedexText.setText(holder.returnPokedexEntry());
        }
        else
        {
            throw new WrongIndexException(index);
        }
    }

    /**
     * Sets int index to a radom number from 0 to 150.
     */
    private void setRandomIndex()
    {
        Random random = new Random();
        index = random.nextInt(150);
    }

    /**
     * Closes the program.
     */
    private void quit()
    {
        System.exit(0);
    }

    /**
     * A window containing instructions on how to operate the program is displayed.
     */
    private void help()
    {
        JOptionPane.showMessageDialog(frame, "Click the Previous and Next buttons to choose the previous\n" 
            + "and next Pokemon, respectively. Click the Random button to\n"
            + "choose a random Pokemon. Click Print to save the Pokedex\n"
            + "entry to a text file. Click any of the color buttons to\n"
            + "change the Pokedex to that color.", "Help", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * A window containing some information about the program is displayed.
     */
    private void about()
    {
        JOptionPane.showMessageDialog(frame, "This project was created by Nikolas Beltran for the CS162 final\n"
            + "in the Spring term of 2015.\n\n" + "Pokemon is owned by Nintendo, Game Freak, Creatures Inc.,\n"
            + "and the Pokemon Company.", "About", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Starts the program.
     */
    public static void main(String args[])
    {
        GUIBuilder pokedex = new GUIBuilder();
    }
}
