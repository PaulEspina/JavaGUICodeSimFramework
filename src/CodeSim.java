import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Page
{
    private final JPanel parent;
    private final ArrayList<JPanel> linePanels;
    private final ArrayList<JLabel> lineLabels;

    public Page(JPanel panel, ArrayList<JPanel> linePanels, ArrayList<JLabel> lineLabels)
    {
        this.parent = panel;
        this.linePanels = linePanels;
        this.lineLabels = lineLabels;
    }

    public JPanel getParent()
    {
        return parent;
    }

    public JPanel getPanel(int i)
    {
        return linePanels.get(i);
    }

    public JLabel getLabels(int i)
    {
        return lineLabels.get(i);
    }

    public int size()
    {
        return linePanels.size();
    }
}

public class CodeSim extends JPanel
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("CodeSim");
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 300);
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

    // Display Attributes
    private Font font;
    private Color lineNumberBackground;
    private Color lineNumberForeground;
    private Color background;
    private Color foreground;
    private Color highlightBackground;
    private Color highlightForeGround;

    private CodeSim()
    {
        font = new Font("Consola", Font.PLAIN, 13);
        lineNumberBackground = Color.black;
        lineNumberForeground = Color.gray;
        background = Color.white;
        foreground = Color.black;
        highlightBackground = Color.cyan;
        highlightForeGround = Color.black;
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

            ArrayList<JPanel> panels = new ArrayList<>();
            ArrayList<JLabel> labels = new ArrayList<>();
            for(int i = 0; i < page.size(); i++)
            {
                JPanel number = new JPanel(new FlowLayout((FlowLayout.RIGHT)));
                number.setBackground(lineNumberBackground);
                number.setPreferredSize(new Dimension(20, 25));
                JLabel numberLabel = new JLabel(String.valueOf(i + 1));
                numberLabel.setForeground(lineNumberForeground);
                numberLabel.setFont(font);
                number.add(numberLabel);
                rowHeaderPanel.add(number);

                JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                linePanel.setBackground(background);
                linePanel.setPreferredSize(new Dimension(0, 25));
                JLabel lineLabel = page.get(i);
                lineLabel.setForeground(foreground);
                lineLabel.setFont(font);
                linePanel.add(lineLabel);
                pagePanel.add(linePanel);
                panels.add(linePanel);
                labels.add(lineLabel);
            }
            pages.add(new Page(pagePanel, panels, labels));
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
                JPanel panel = page.getPanel(i);
                JLabel label = page.getLabels(i);
                if(i == index)
                {
                    panel.setBackground(highlightBackground);
                    label.setForeground(highlightForeGround);
                }
                else
                {
                    panel.setBackground(background);
                    label.setForeground(foreground);
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

    // Setters
    public void setFont(String name)
    {
        font = new Font(name, Font.PLAIN, 13);
    }

    public void setLineNumberBackground(Color lineNumberBackground)
    {
        this.lineNumberBackground = lineNumberBackground;
    }

    public void setLineNumberForeground(Color lineNumberForeground)
    {
        this.lineNumberForeground = lineNumberForeground;
    }

    @Override
    public void setBackground(Color background)
    {
        this.background = background;
    }

    @Override
    public void setForeground(Color foreground)
    {
        this.foreground = foreground;
    }

    public void setHighlightBackground(Color highlightBackground)
    {
        this.highlightBackground = highlightBackground;
    }

    public void setHighlightForeGround(Color highlightForeGround)
    {
        this.highlightForeGround = highlightForeGround;
    }

    // Getters

    @Override
    public Font getFont()
    {
        return font;
    }

    public Color getLineNumberBackground()
    {
        return lineNumberBackground;
    }

    public Color getLineNumberForeground()
    {
        return lineNumberForeground;
    }

    @Override
    public Color getBackground()
    {
        return background;
    }

    @Override
    public Color getForeground()
    {
        return foreground;
    }

    public Color getHighlightBackground()
    {
        return highlightBackground;
    }

    public Color getHighlightForeGround()
    {
        return highlightForeGround;
    }
}
