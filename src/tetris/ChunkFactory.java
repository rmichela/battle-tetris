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
import java.util.*;

public class ChunkFactory 
{
  private static Random r = new Random(System.currentTimeMillis());
	
  public static Chunk createRandomChunk()
  {
    switch (r.nextInt(7))
    {
      case 0:
        return createTall();
      case 1:
        return createEll();
      case 2:
        return createBackell();
      case 3:
        return createTee();
      case 4:
        return createEss();
      case 5:
        return createZee();
      case 6:
        return createSquare();
    }
    return null;
  }
  
  public static Block createRandomBlock()
  {
  	return new Block(randColor());
  }
  
  public static Chunk createTall()
  {
    Chunk ch = new Chunk(1, 4);
    Color c = randColor();
    ch.addBlock(0,0,c);
    ch.addBlock(0,1,c);
    ch.addBlock(0,2,c);
    ch.addBlock(0,3,c);
    return ch;
  }

  public static Chunk createEll()
  {
    Chunk ch = new Chunk(2, 3);
    Color c = randColor();
    ch.addBlock(0,0,c);
    ch.addBlock(0,1,c);
    ch.addBlock(0,2,c);
    ch.addBlock(1,2,c);
    return ch;
  }

  public static Chunk createBackell()
  {
    Chunk ch = new Chunk(2, 3);
    Color c = randColor();
    ch.addBlock(1,0,c);
    ch.addBlock(1,1,c);
    ch.addBlock(1,2,c);
    ch.addBlock(0,2,c);
    return ch;
  }

  public static Chunk createTee()
  {
    Chunk ch = new Chunk(3, 2);
    Color c = randColor();
    ch.addBlock(1,0,c);
    ch.addBlock(0,1,c);
    ch.addBlock(1,1,c);
    ch.addBlock(2,1,c);
    return ch;
  }

  public static Chunk createEss()
  {
    Chunk ch = new Chunk(2, 3);
    Color c = randColor();
    ch.addBlock(0,0,c);
    ch.addBlock(0,1,c);
    ch.addBlock(1,1,c);
    ch.addBlock(1,2,c);
    return ch;
  }

  public static Chunk createZee()
  {
    Chunk ch = new Chunk(2, 3);
    Color c = randColor();
    ch.addBlock(1,0,c);
    ch.addBlock(1,1,c);
    ch.addBlock(0,1,c);
    ch.addBlock(0,2,c);
    return ch;
  }

  public static Chunk createSquare()
  {
    Chunk ch = new Chunk(2, 2);
    Color c = randColor();
    ch.addBlock(0,0,c);
    ch.addBlock(0,1,c);
    ch.addBlock(1,0,c);
    ch.addBlock(1,1,c);
    return ch;
  }

  private static Color randColor()
  {
    switch (r.nextInt(5))
    {
      case 0:
        return Color.red;
      case 1:
        return Color.blue;
      case 2:
        return Color.green;
      case 3:
        return Color.yellow;
      case 4:
        return Color.magenta;
    }
    return null;
  }
}