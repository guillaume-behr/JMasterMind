package view.gamePanels;

import javax.swing.*;

//A classic Panel but with a tag in order to identify the number of the line
public class LinePanel extends JPanel
{
    private int tag;
    public LinePanel(int tag)
    {
        this.tag = tag;
    }
    public int getTag()
    {
        return this.tag;
    }
}
