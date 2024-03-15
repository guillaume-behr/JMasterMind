package helpersLib;

import model.CluesMode;
import model.GameColor;

import java.awt.*;

//A helpers class
public class Helpers
{
    //Transform our GameColor enum in a Color enum of JavaSwing
    public static Color transformGameColorIntoColor(GameColor c)
    {
        Color color;

        switch (c)
        {
            case BLUE:
                color=Color.BLUE;
                break;
            case RED:
                color=Color.RED;
                break;
            case GREEN:
                color=Color.GREEN;
                break;
            case GREY:
                color=Color.GRAY;
                break;
            case ORANGE:
                color=Color.ORANGE;
                break;
            case PINK:
                color=Color.PINK;
                break;
            case YELLOW:
                color=Color.YELLOW;
                break;
           default:
                color=Color.WHITE;
                break;
        }
        return color;
    }

    //Translate the clues mode into a french String
    public static String translateCluesModeToFrench(CluesMode cluesMode)
    {
        String frenchName;
        if(cluesMode==CluesMode.CLASSIC_MODE)
            frenchName="mode classique";
        else if (cluesMode==CluesMode.EASY_MODE)
            frenchName="mode facile";
        else
            frenchName="mode numérique";
        return frenchName;
    }

    //Translate our GameColor enum into a french String
    public static String translateColorToFrench(GameColor c)
    {
        String frenchColorName;

        switch (c)
        {
            case BLUE:
                frenchColorName="bleu";
                break;
            case RED:
                frenchColorName="rouge";
                break;
            case GREEN:
                frenchColorName="vert";
                break;
            case GREY:
                frenchColorName="gris";
                break;
            case ORANGE:
                frenchColorName="orange";
                break;
            case PINK:
                frenchColorName="rose";
                break;
            case YELLOW:
                frenchColorName="jaune";
                break;
            default:
                frenchColorName="blanc";
                break;
        }
        return frenchColorName;
    }
}
