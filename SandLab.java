import java.awt.*;
import java.util.*;

public class SandLab 
{
    public static void main(String[] args)
    {
        SandLab lab = new SandLab(120, 80);
        lab.run();
    }

    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int SAND = 2;
    public static final int WATER = 3;
    public static final int STEAM = 4;
    public static final int CHECKERBOARD = 5;

    //do not add any more fields
    private int[][] grid;
    private SandDisplay display;

    public SandLab(int numRows, int numCols)
    {
        String[] names;
        names = new String[6];
        names[EMPTY] = "Empty";
        names[METAL] = "Metal";
        names[SAND] = "Sand";
        names[WATER] = "Water";
        names[STEAM] = "Steam";
        names[CHECKERBOARD] = "CHECKERBOARD";
        grid = new int[numRows][numCols];
        display = new SandDisplay("Falling Sand", numRows, numCols, names);
    }

    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool)
    {
        grid[row][col] = tool;

    }
    //copies each element of grid into the display
    public void updateDisplay()
    {
        Color color;
        for(int row = 0; row<grid.length; row++)
        {
            for(int col = 0; col<grid[0].length; col++)            
            {
                if(grid[row][col] == EMPTY)
                {
                    display.setColor(row, col, new Color(0, 0, 0));
                }
                else if(grid[row][col] == METAL)
                {
                    display.setColor(row, col, new Color(51, 51, 51));
                }
                else if(grid[row][col] == SAND)
                {
                    display.setColor(row, col, new Color(232, 191, 7));
                }
                else if(grid[row][col] == WATER)
                {
                    display.setColor(row, col, new Color(38, 44, 249));
                }
                else if(grid[row][col] == STEAM)
                {
                    display.setColor(row, col, new Color(255, 255, 255));
                }
                else if(grid[row][col] == CHECKERBOARD)
                {
                    display.setColor(row, col, new Color(0, 255, 51));
                }
            }
        }
    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step()
    {
        int row = (int)(Math.random() * grid.length + 0);
        int col = (int)(Math.random() * grid[0].length + 0);  
        if(grid[row][col] == SAND)
        {
            if((row+1)<grid.length && grid[row+1][col] == EMPTY)
            {
                grid[row+1][col] = grid[row][col];
                grid[row][col] = EMPTY;
            }
            else if((row+1)<grid.length && grid[row+1][col] == WATER)
            {
                grid[row+1][col] = SAND;
                grid[row][col] = WATER;
            }
        }
        else if(grid[row][col] == WATER)
        {
            int choice = (int)(Math.random() * 3 + 1);
            if(choice == 1)
            {
                if((col-1)>=0 && grid[row][col-1] == EMPTY)
                {
                    grid[row][col-1] = grid[row][col];
                    grid[row][col] = EMPTY;
                }
            }
            else if(choice == 2)
            {
                if((row+1)<grid.length && grid[row+1][col] == EMPTY)
                {
                    grid[row+1][col] = grid[row][col];
                    grid[row][col] = EMPTY;
                }
            }
            else if(choice == 3)
            {
                if((col+1)<grid[0].length && grid[row][col+1] == EMPTY)
                {
                    grid[row][col+1] = grid[row][col];
                    grid[row][col] = EMPTY;
                }
            }
        }
        else if(grid[row][col] == STEAM)
        {
            int choice = (int)(Math.random() * 3 + 1);
            if(choice == 1)
            {
                if((col-1)>=0 && grid[row][col-1] == EMPTY)
                {
                    grid[row][col-1] = grid[row][col];
                    grid[row][col] = EMPTY;
                }
            }
            else if(choice == 2)
            {
                if((row-1)>0 && grid[row-1][col] == EMPTY)
                {
                    grid[row-1][col] = grid[row][col];
                    grid[row][col] = EMPTY;
                }
            }
            else if(choice == 3)
            {
                if((col+1)<grid[0].length && grid[row][col+1] == EMPTY)
                {
                    grid[row][col+1] = grid[row][col];
                    grid[row][col] = EMPTY;
                }
            }
        }
        else if(grid[row][col] ==  CHECKERBOARD)
        {
            if((row-1)>0 && (col+1)<grid[0].length && grid[row][col+1] == EMPTY)
            {
                grid[row-1][col+1] = CHECKERBOARD;
                if((row-2)>0 && grid[row-2][col] == EMPTY)
                {
                    grid[row-2][col] = CHECKERBOARD;
                    if((row-3)>0 && (col-1)>0 && grid[row-3][col-1] == CHECKERBOARD)
                    {
                        grid[row-3][col-1] = CHECKERBOARD;
                        if((row-4)>0 && grid[row-4][col] == EMPTY)
                        {
                            grid[row-4][col] = CHECKERBOARD;
                        }
                    }
                }
            }
        }
    }

    //do not modify
    public void run()
    {
        while (true)
        {
            for (int i = 0; i < display.getSpeed(); i++)
                step();
            updateDisplay();
            display.repaint();
            display.pause(1);  //wait for redrawing and for mouse
            int[] mouseLoc = display.getMouseLocation();
            if (mouseLoc != null)  //test if mouse clicked
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
        }
    }
}
