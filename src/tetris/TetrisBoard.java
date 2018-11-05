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
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TetrisBoard extends JPanel
{
  private Block[][] board = new Block[10][20];
  private Chunk fallingChunk;
  private TetrisNotifications parent;
  private boolean fullUpdate = false;
  private static Random r = new Random(System.currentTimeMillis());
  
  public TetrisBoard(TetrisNotifications parent)
  {
    this.parent = parent;
    this.setPreferredSize(new Dimension(200, 400));
    this.setMinimumSize(new Dimension(200, 400));
    this.setMaximumSize(new Dimension(200, 400));
    resetFallingChunk();
  }

  public void leftKey()
  {
    fallingChunk.shift(0, 10, Chunk.LEFT, board);
    repaint();
  }

  public void rightKey()
  {
    fallingChunk.shift(0, 10, Chunk.RIGHT, board);
    repaint();
  }

  public void rotateKey()
  {
    fallingChunk.rotate(0, 10, board);
    repaint();
  }

  public void dropKey()
  {
    strobe();
  }

  public void strobe()
  {
    if(!fallingChunk.drop(board))
    {
      resetFallingChunk();
    }
    repaint();
  }
  
  public void addJunkRows(int rows)
  {
  	for(int i = 0; i < rows; i++)
  	{
  		addJunkRow();	
  	}	
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    {
      for(int i = 0; i < 10; i++)
      {
        for(int j = 0; j < 20; j++)
        {
          if(board[i][j] != null)
          {
            board[i][j].paint(g);
          }
        }
      }
    }
    fallingChunk.paint(g);
  }

  private void resetFallingChunk()
  {
    collapseFullRows();
    fallingChunk = ChunkFactory.createRandomChunk();
    fallingChunk.setPos(4, 0);
    calcPileHeight();
    if(gameOver())
    {
      parent.TNGameLost();
    }
  }

  private void collapseFullRows()
  {
    boolean rowFull;
    int rowsEliminated = 0;
    for(int row = 0; row < 20; row++)
    {
      rowFull = true;
      for(int col = 0; col < 10; col++)
      {
        if(board[col][row] == null)
        {
          rowFull = false;
        }
      }
      if(rowFull)
      {
        eliminateRow(row);
        rowsEliminated++;
      }
    }
    if(rowsEliminated > 0)
    {
      parent.TNRowsKilled(rowsEliminated);
    }
  }

  private void eliminateRow(int killRow)
  {
    for(int row = killRow; row > 0; row--)
    {
      for(int col = 0; col < 10; col++)
      {
        board[col][row] = board[col][row-1];
        if(board[col][row] != null)
        {
          board[col][row].setPos(col, row);
        }
      }
    }
    for(int col = 0; col < 10; col++)
    {
       board[0][col] =null;
    }
  }

  private boolean gameOver()
  {
    boolean blockFound = false;
    for(int i = 0; i < 10; i++)
    {
      if(board[i][0] != null)
      {
        blockFound = true;
      }
    }
    return blockFound;
  }

  private void calcPileHeight()
  {
    boolean locked = false;
    int height = 20;
    for(int j = 0; j < 20; j++)
    {
      for(int i = 0; i < 10; i++)
      {
        if(board[i][j] != null)
        {
          if(!locked)
          {
            height = j;
            locked = true;
          }
        }
      }
    }
    parent.TNReportHeight(20 - height);
  }
  
  private void addJunkRow()
  {
  	//shift board up one
  	for(int r = 0; r < 19; r++)
  	{
		for(int c = 0; c < 10; c++)
		{
			board[c][r] = board[c][r+1];
			if(board[c][r] != null)
			{
				board[c][r].setPos(c, r);
			}
					
		}
  	} 
  	int gap1 = r.nextInt(10);
  	int gap2 = r.nextInt(10);
  	//add junk to bottom row 	
  	for(int c = 0; c < 10; c++)
  	{
  		if(c != gap1 && c != gap2)
  		{
  			board[c][19] = ChunkFactory.createRandomBlock();
  			board[c][19].setPos(c, 19);
  		}
  		else
  		{
  			board[c][19] = null;	
  		}
  	}
  	repaint();	
  }
}
