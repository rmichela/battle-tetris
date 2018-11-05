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

import java.net.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class Comm extends Thread
{
	private LinkedList inQ = new LinkedList();
	
	private int port;
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	
	public Comm(int port)
	{
		this.port = port;
		this.setDaemon(true);
		this.init();
	}
	
	private void init()
	{
		try
		{
			switch (JOptionPane.showConfirmDialog(null,"Run as server?", "Connection Setttings", JOptionPane.YES_NO_OPTION))
			{
				case JOptionPane.YES_OPTION:
					s = getSockAsServer();
					break;
				case JOptionPane.NO_OPTION:
					s = getSockAsClient();
					break;	
			}
			
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		
			this.start();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Connection error!", "ERROR!", JOptionPane.ERROR_MESSAGE);	
			System.exit(1);
		}
	}
	
	public void send(String s)
	{
		try
		{
			System.out.println("Sent: " + s);
			out.println(s);
			out.flush();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Communication error!", "ERROR!", JOptionPane.ERROR_MESSAGE);	
			System.exit(1);
		}
	}
	
	public String rec()
	{
		if(inQ.size() > 0)
		{
			String data;
			synchronized(inQ)
			{
				data = (String)inQ.removeFirst();
			}
			return data;
		}
		else
		{
			return null;	
		}
	}
	
	private Socket getSockAsServer()
	{
		try
		{
			ServerSocket ss = new ServerSocket(this.port);
			
			JLabel message = new JLabel("<html>Your IP is: <font color=red>" + ss.getInetAddress().getLocalHost().getHostAddress() + "</font><br>Waiting for client.</html>");
			JFrame d = new JFrame("Please hold...");
			message.setHorizontalAlignment(JLabel.CENTER);
			d.getContentPane().add(message);
			d.setSize(300, 100);
			d.setVisible(true);
			
			
			Socket s = ss.accept();
			
			d.setVisible(false);
			return s;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Connection error!", "ERROR!", JOptionPane.ERROR_MESSAGE);	
			System.exit(1);
			return null;
		}
	}
	
	private Socket getSockAsClient()
	{
		try
		{
			String host = JOptionPane.showInputDialog(null, "Enter host:", "Select a host.", JOptionPane.QUESTION_MESSAGE);
			Socket s = new Socket(InetAddress.getByName(host), this.port);
			return s;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Connection error!", "ERROR!", JOptionPane.ERROR_MESSAGE);	
			System.exit(1);
			return null;
		}
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				String data;
				data = in.readLine();
				System.out.println("Got: " + data);
				synchronized(inQ)
				{
					inQ.addLast(data);	
				}
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Communication error!", "ERROR!", JOptionPane.ERROR_MESSAGE);	
			System.exit(1);
		}	
	}
}
