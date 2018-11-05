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

public class Block 
{
  private int x, y;
  private static int size = 20;
  private Color c;

  public static void setBlockSize(int size)
  {
    Block.size = size;
  }
  
  public Block(Color c)
  {
    this.c = c;
  }

  public void setPos(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public void paint(Graphics g)
  {
    g.setColor(c);
    g.fillRect(x * size, y * size, size, size);
    g.setColor(Color.black);
    g.drawRect(x*size + 1, y*size + 1, size-3, size-3);
  }
}