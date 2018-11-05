/*This file is part of BattleTetris
*Copyright (C) 2002  Ryan Michela
*
*This program is free software; you can redistribute it and/or
*modify it under the terms of the GNU General Public License
*as published by the Free Software Foundation; either version 2
*of the License, or (at your option) any later version.
*
*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*GNU General Public License for more details.
*
*You should have received a copy of the GNU General Public License
*along with this program; if not, write to the Free Software
*Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

package tetris;
import java.awt.*;

public class Chunk 
{
  private int gridSizeX, gridSizeY;
  private Block grid[][];
  private int xPos, yPos;

  public static final int LEFT = -1;
  public static final int RIGHT = 1;
  private static final int DOWN = 0;
  
  public Chunk(int gridSizeX, int gridSizeY)
  {
    this.gridSizeX = gridSizeX;
    this.gridSizeY = gridSizeY;
    grid = new Block[gridSizeX][gridSizeY];
  }

  public boolean addBlock(int x, int y, Color c)
  {
     try
     {
        grid[x][y] = new Block(c);
        return true;
     }
     catch(Exception e)
     {
        return false;
     }
  }

  public void setPos(int x, int y)
  {
    xPos = x;
    yPos = y;
    for(int i = 0; i < gridSizeX; i++)
    {
      for(int j = 0; j < gridSizeY; j++)
      {
        if(grid[i][j] != null)
        {
          grid[i][j].setPos(x+i, y+j);
        }
      }
    }
  }

  public void paint(Graphics g)
  {
    for(int i = 0; i < gridSizeX; i++)
    {
      for(int j = 0; j < gridSizeY; j++)
      {
        if(grid[i][j] != null)
        {
          grid[i][j].paint(g);
        }
      }
    }
  }

  public void shift(int minX, int maxX, int dir, Block[][] board)
  {
    if(canShift(maxX, minX, board, dir))
    {
      if(dir == Chunk.LEFT)
      {
        if(xPos > minX)
        {
          setPos(xPos - 1, yPos);
        }
      }
      else if(dir == Chunk.RIGHT)
      {
        if(xPos + gridSizeX < maxX)
        {
          setPos(xPos + 1, yPos);
        }
      }
    }
  }

  private boolean canShift(int maxX, int minX, Block[][] board, int dir)
  {
    for(int i = 0; i < gridSizeX; i++)
    {
      for(int j = 0; j < gridSizeY; j++)
      {
        if(grid[i][j] != null)
        {
          if(i + xPos + dir >= maxX)
          {
            return false;
          }
          else if(i + xPos + dir < minX)
          {
            return false;
          }
          else if(board[i + xPos + dir][j + yPos] != null)
          {
            return false;
          }
        }
      }
    }
    return true;
  }

  private boolean canDrop(Block[][] board)
  {
    for(int i = 0; i < gridSizeX; i++)
    {
      for(int j = 0; j < gridSizeY; j++)
      {
        if(grid[i][j] != null)
        {
          if(yPos + gridSizeY + 1 > board[0].length)
          {
            return false;
          }
          else if(board[i + xPos][j + yPos + 1] != null)
          {
            return false;
          }
        }
      }
    }
    return true;
  }

  private void place(Block[][] board)
  {
    for(int i = 0; i < gridSizeX; i++)
    {
      for(int j = 0; j < gridSizeY; j++)
      {
        if(grid[i][j] != null)
        {
          board[i + xPos][j + yPos] = grid[i][j];
        }
      }
    }
  }

  public boolean drop(Block[][] board)
  {
    boolean dropStatus = canDrop(board);
    if(dropStatus)
    {
      setPos(xPos, yPos + 1);
    }
    else
    {
      place(board);
    }
    return dropStatus;
  }

  public void rotate(int minX, int maxX, Block[][] board)
  {
    Block[][] newGrid = new Block[gridSizeY][gridSizeX];
    int gridSizedY = gridSizeX;
    int gridSizedX = gridSizeY;

    //rotation logic. This should work...i think
    for(int x = 0; x < gridSizeX; x++)
    {
      for(int y = 0; y < gridSizeY; y++)
      {
        newGrid[y][gridSizeX - 1 - x] = grid[x][y];
      }
    }
    if(canRotate(minX, maxX, gridSizedX, gridSizedY, board))
    {
      grid = newGrid;
      gridSizeX = gridSizedX;
      gridSizeY = gridSizedY;
      setPos(xPos, yPos);
    }
  }

  private boolean canRotate(int minX, int maxX, int gridSizeX, int gridSizeY, Block[][] board)
  {
    //check for out of bounds
    if(xPos < minX)
    {
      return false;
    }
    if(xPos + gridSizeX > maxX)
    {
      return false;
    }
    if(yPos + gridSizeY > board[0].length)
    {
      return false;
    }

    //check for tile overlap
    for(int i = 0; i < gridSizeX; i++)
    {
      for(int j = 0; j < gridSizeY; j++)
      {
        if(grid[j][i] != null)
        {
          //off the end of the board
          if(yPos + 1 > board[0].length)
          {
            return false;
          }
          //rotating into a conflicing space
          else if(board[i + xPos][j + yPos] != null)
          {
            return false;
          }
          //blocked on the right
          else if((j + xPos + 1) < maxX && board[j + xPos + 1][i + yPos] != null)
          {
            return false;
          }
          //blocked on the left
          else if((j + xPos - 1) >= minX && board[j + xPos - 1][i + yPos] != null)
          {
            return false;
          }
        }
      }
    }
  return true;
  }
}