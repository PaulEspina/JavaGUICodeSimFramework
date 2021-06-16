import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CodeSim extends JPanel
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("CodeSim");
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(500, 300));
        CodeSim codeSim = new CodeSim("samplecode");
        frame.add(codeSim);
        frame.setVisible(true);
    }

    private ArrayList<ArrayList<JLabel>> codes;

    private CodeSim()
    {
        codes = new ArrayList<>();
    }

    // Use this constructor for one page codes.
    public  CodeSim(String path)
    {
        this();
        codes.add(parseCode(path));
    }


    // Use this constructor for multiple page of codes.
    public CodeSim(String[] paths)
    {
        this();
        for(String path: paths)
        {
            codes.add(parseCode(path));
        }
    }

    // Parses code from a file to ArrayList of JLabels.
    private ArrayList<JLabel> parseCode(String path)
    {
        File file = new File(path);
        Scanner scanner;
        try
        {
            scanner = new Scanner(file);

            ArrayList<JLabel> lines = new ArrayList<>();
            while(scanner.hasNext())
            {
                String line = scanner.nextLine();
                lines.add(new JLabel(line));
            }
            return lines;
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Path not found: " + path);
        }
        return null;
    }
}
