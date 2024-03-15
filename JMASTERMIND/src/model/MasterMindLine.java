package model;

import java.util.ArrayList;

public class MasterMindLine {

    private int size; //Size of the line
    private ArrayList<GameColor> cells; //Cells of the line
    private ArrayList<CellInfo> cellInfos; //Infos on each cell
    private boolean tabAlreadyGoodPlace[]; // Array to know if we already says that the color is present but not good placed

    public MasterMindLine(int size)
    {
        this.size = size;
        this.cells = new ArrayList<GameColor>();
        this.cellInfos = new ArrayList<CellInfo>();
        this.tabAlreadyGoodPlace=new boolean[this.size];

        for(int i=0; i<size; i++)
        {
            this.cells.add(GameColor.NONE);
            this.cellInfos.add(CellInfo.NONE);
            this.tabAlreadyGoodPlace[i]=false;
        }
    }

    //Getters for the list of colors of the cells
    public ArrayList<GameColor> getCells()
    {
        return cells;
    }
    //Getters for the list of information of the cells
    public ArrayList<CellInfo> getCellInfos()
    {
        return this.cellInfos;
    }

    //Function that return true if the line is equal to the secret combination
    //the function also fills the cellInfos array
    public boolean verify(MasterMindLine secretCombination)
    {
        int wellPlaced = 0;

        //Check the one that are good placed
        for(int i=0; i<this.size; i++)
        {
            if(this.getCellColor(i) == secretCombination.getCellColor(i))
            {
                this.cellInfos.set(i,CellInfo.WELL_PLACED);
                wellPlaced ++;
            }
        }

        //Now the other (GOOD_COLOR and NOT_PRESENT)
        for(int i=0; i<this.size; i++)
        {
            if(this.cellInfos.get(i) != CellInfo.WELL_PLACED)
            {
                //Check if all of this color are already placed (so that we don't have GOOD_COLOR even if they are all already at the good place when we put some more)
                if(!allColorAlreadyWellPlaced(this.cells.get(i),secretCombination))
                {
                    for (int j = 0; j < this.size; j++)
                    {
                        //If we have multiple same color, we put only once the GOOD_COLOR
                        if(!alreadySayGoodPlace(this.cells.get(i),i))
                        {
                            this.cellInfos.set(i, CellInfo.GOOD_COLOR);
                        }
                    }
                }
            }
        }
        //The one not present
        for(int i=0;i<this.size;i++)
        {
            if(this.cellInfos.get(i) != CellInfo.GOOD_COLOR && this.cellInfos.get(i) != CellInfo.WELL_PLACED)
            {
                this.cellInfos.set(i, CellInfo.NOT_PRESENT);
            }
        }

        return wellPlaced == this.size;
    }

    //Method that check if we already said that this color is present but not good placed
    private boolean alreadySayGoodPlace(GameColor c, int index)
    {
        if(this.tabAlreadyGoodPlace[index]==false)
        {
            for(int i=0;i<this.size;i++)
            {
                if(this.cells.get(i)==c)
                    this.tabAlreadyGoodPlace[i]=true;
            }

            return false;
        }
        return true;
    }

    //Method that check if all the color are already well-placed
    private boolean allColorAlreadyWellPlaced(GameColor c,MasterMindLine secretCombination)
    {
        int nbColor=0;//Number of this color in the secret combination
        int nbWellPlaced=0;//Number of well-placed of this color
        for(int i=0;i<this.size;i++)
            if(secretCombination.getCellColor(i)==c)
                nbColor++;
        for(int i=0;i<this.size;i++)
            if(this.cells.get(i)==c && this.cellInfos.get(i)==CellInfo.WELL_PLACED)
                nbWellPlaced++;

        return nbColor==nbWellPlaced;
    }

    //Function that gets the GameColor of a specific cell
    public GameColor getCellColor(int index) throws ArrayIndexOutOfBoundsException
    {
        if(index >= size)
        {
            throw new ArrayIndexOutOfBoundsException("The size of the array is : " + this.size);
        }

        return this.cells.get(index);
    }

    //Function that sets the GameColor of a specific cell
    public void setCellColor(GameColor color, int index) throws ArrayIndexOutOfBoundsException
    {
        if(index >= size)
        {
            throw new ArrayIndexOutOfBoundsException("The size of the array is : " + this.size);
        }

        this.cells.set(index,color);
    }

    //Function that returns the number of well-placed cells
    public int getWellPlaced()
    {
        int nb = 0;

        for(int i=0; i<this.size; i++)
        {
            if(this.cellInfos.get(i) == CellInfo.WELL_PLACED)
            {
                nb++;
            }
        }
        return nb;
    }

    //Function that returns the number of well-chosen cells
    public int getWellChosen()
    {
        int nb = 0;

        for(int i=0; i<this.size; i++)
        {
            if(this.cellInfos.get(i) == CellInfo.GOOD_COLOR)
            {
                nb++;
            }
        }
        return nb;
    }

    //Function that returns the number of wrongly chose cells
    public int getWrongColor()
    {
        int nb = 0;

        for(int i=0; i<this.size; i++)
        {
            if(this.cellInfos.get(i) == CellInfo.NOT_PRESENT)
            {
                nb++;
            }
        }
        return nb;
    }

    //Method that print all informations about a line on the terminal
    public void printAllInformationsAboutTheLine(MasterMindLine secretCombination)
    {
        System.out.print("\t| === Récapitulatif de la ligne ===");
        System.out.print("\n\t| Couleur de la ligne : \n\t| ");
        for(int i=0;i<this.cells.size();i++)
            System.out.print(cells.get(i)+" ");
        System.out.print("\n\t| Combinaison secrète : \n\t| ");
        for(int i=0;i<this.cells.size();i++)
            System.out.print(secretCombination.getCellColor(i)+" ");
        System.out.print("\n\t| Indices : \n\t| ");
        for(int i=0;i<this.cells.size();i++)
            System.out.print(cellInfos.get(i)+" ");
        System.out.print("\n\t| Bien placé : "+this.getWellPlaced());
        System.out.print(" Mal placé : "+this.getWellChosen());
        System.out.println(" Non présent : "+this.getWrongColor());
    }
}
