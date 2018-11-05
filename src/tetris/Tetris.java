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
import java.awt.event.*;
import java.util.*;

public class Tetris extends KeyAdapter implements TetrisNotifications
{
  private JFrame frame = new JFrame("BattleTetris");
  private Container cp;
  private JSlider oppLevel = new JSlider(JSlider.VERTICAL,0, 20, 0);
  private TetrisBoard tetMain;
  private StrobeThread st = new StrobeThread(1000, this);
  private int rowCount = 0;
  private Comm c;
  
  
  public Tetris()
  {
  	c = new Comm(2002);
  	
    cp = frame.getContentPane();
    tetMain = new TetrisBoard(this);
    tetMain.setSize(200, 400);
    tetMain.setBorder(BorderFactory.createTitledBorder("Your Board"));
    oppLevel.setMajorTickSpacing(10);
    oppLevel.setEnabled(false);
    cp.add(tetMain, BorderLayout.CENTER);
    cp.add(oppLevel, BorderLayout.WEST);
    frame.setSize(225, 430);
    frame.setResizable(false);
    frame.addKeyListener(this);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.show();
    
    gameLoop();
  }

  public static void main(String[] args)
  {
    Tetris tetris = new Tetris();
  }

  public void keyPressed(KeyEvent e)
  {
    switch(e.getKeyCode())
    {
      case KeyEvent.VK_DOWN:
        tetMain.strobe();
        break;
      case KeyEvent.VK_LEFT:
        tetMain.leftKey();
        break;
      case KeyEvent.VK_RIGHT:
        tetMain.rightKey();
        break;
      case KeyEvent.VK_UP:
        tetMain.rotateKey();
        break;
      case KeyEvent.VK_J:
      	tetMain.addJunkRows(2);
      	break;
    }
  }

  public void keyReleased(KeyEvent e)
  {
    
  }

  public void TNRowsKilled(int rows)
  {
	rowCount += rows;
	if(rowCount % 10 == 0)
	{
		st.updateMils(st.getMils() - 100);	
	}
	frame.setTitle("BattleTetris: " + rowCount + " Rows");
  	//transmit rows
  	if(rows % 2 == 0)
  	{
  		c.send("junk " + rows);	
  	}
  }

  public void TNReportHeight(int height)
  {
    //transmit height
    c.send("level " +  height);
  }

  public void TNGameLost()
  {
  	c.send("win");
    JOptionPane.showMessageDialog(frame, "GameOver.");
    System.exit(0);
  }
  
  public void TNStrobe()
  {
  	tetMain.strobe();
  }
  
  private void gameLoop()
  {
  	String input;
  	st.start();
  	while(true)
  	{
	  	input = c.rec();
	  	if(input == null)
	  	{
	  		try
	  		{
	  			Thread.sleep(10);	
	  		}
	  		catch(Exception e)
	  		{
	  			
	  		}		
	  	}
	  	else
	  	{
	 		StringTokenizer st = new StringTokenizer(input);
	 		String t = st.nextToken();
	 		
	 		if(t.equals("level"))
	 		{
	 			oppLevel.setValue(Integer.parseInt(st.nextToken()));
	 		}
	 		else if(t.equals("win"))
	 		{
	 			JOptionPane.showMessageDialog(frame, "You Win!");
    			System.exit(0);
	 		}
	 		else if(t.equals("junk"))
	 		{
	 			System.out.println("Junking");
	 			tetMain.addJunkRows(Integer.parseInt(st.nextToken()));
	 		}
	 		else
	 		{
	 			System.out.println("Cant handle: " + input);	
	 		}		
	  	}
  	}
  }
}