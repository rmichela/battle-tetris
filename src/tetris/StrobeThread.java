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

public class StrobeThread extends Thread
{
	private int mils;
	private TetrisNotifications tn;
	
	public StrobeThread(int mils, TetrisNotifications tn)
	{
		this.mils = mils;
		this.tn = tn;	
		this.setDaemon(true);
	}
	
	public int getMils()
	{
		return mils;	
	}
	
	public void updateMils(int mils)
	{
		this.mils = mils;	
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(mils);
				tn.TNStrobe();
			}
			catch(Exception e)
			{
				//do nothing	
			}	
		}
	}
}
