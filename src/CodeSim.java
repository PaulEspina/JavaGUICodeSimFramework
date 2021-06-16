import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Page
{
    private JPanel panel;
    private ArrayList<JPanel> lines;

    public Page(JPanel panel, ArrayList<JPanel> lines)
    {
        this.panel = panel;
        this.lines = lines;
    }

    public JPanel getPanel()
    {
        return panel;
    }

    public JPanel getLine(int i)
    {
        return lines.get(i);
    }

    public int size()
    {
        return lines.size();
    }
}

public class CodeSim extends JPanel
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("CodeSim");
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 300);
        frame.setMinimumSize(frame.getSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CodeSim codeSim = new CodeSim(new String[]{"samplecode", "samplecode1"});
        codeSim.init();
        codeSim.setLine(2);
        frame.add(codeSim);
        frame.setVisible(true);
    }

    private final ArrayList<ArrayList<JLabel>> codes;
    private final ArrayList<Page> pages;
    private final CardLayout cardLayout;

    private CodeSim()
    {
        codes = new ArrayList<>();
        pages = new ArrayList<>();
        cardLayout = new CardLayout();
        setLayout(cardLayout);
    }

    // Use this constructor for one page codes.
    public CodeSim(String path)
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

    // Creates arrays of JPanel as the pages of codes. (MUST CALL THIS METHOD BEFORE DOING ANYTHING ELSE)
    public void init()
    {
        revalidate();
        repaint();
        removeAll();

        for(ArrayList<JLabel> page : codes)
        {
            JPanel pagePanel = new JPanel();
            pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(pagePanel);

            // ROW HEADER
            JViewport rowViewport = new JViewport();
            JPanel rowHeaderPanel = new JPanel();
            rowHeaderPanel.setLayout(new BoxLayout(rowHeaderPanel, BoxLayout.Y_AXIS));
            rowViewport.add(rowHeaderPanel);
            scrollPane.setRowHeader(rowViewport);

            ArrayList<JPanel> lines = new ArrayList<>();
            for(int i = 0; i < page.size(); i++)
            {
                JPanel number = new JPanel(new FlowLayout((FlowLayout.RIGHT)));
                number.setBackground(Color.black);
                number.setPreferredSize(new Dimension(20, 20));
                JLabel numberLabel = new JLabel(String.valueOf(i + 1));
                numberLabel.setForeground(Color.GRAY);
                numberLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
                number.add(numberLabel);
                rowHeaderPanel.add(number);

                JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                linePanel.setPreferredSize(new Dimension(0, 20));
                JLabel lineLabel = page.get(i);
                lineLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
                linePanel.add(lineLabel);
                pagePanel.add(linePanel);
                lines.add(linePanel);
            }
            pages.add(new Page(pagePanel, lines));
            add(scrollPane);
        }
    }

    public void nextPage()
    {
        cardLayout.next(this);
    }

    public void prevPage()
    {
        cardLayout.previous(this);
    }

    public void setLine(int index)
    {
        for(Page page : pages)
        {
            for(int i = 0; i < page.size(); i++)
            {
                JPanel line = page.getLine(i);
                if(i == index)
                {
                    line.setBackground(Color.CYAN);
                    line.setForeground(Color.WHITE);
                }
                else
                {
                    line.setBackground(Color.WHITE);
                    line.setForeground(Color.BLACK);
                }
            }
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

            ArrayList<JLabel> page = new ArrayList<>();
            while(scanner.hasNext())
            {
                page.add(new JLabel(scanner.nextLine()));
            }
            return page;
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Path not found: " + path);
        }
        return null;
    }
}
