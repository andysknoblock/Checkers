import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

public class Checkers extends JComponent
{
	/**
	 * 
	 */
	private Timer t1;
	private static final long serialVersionUID = 1L;
	private Board board;
	private final int FPS = 45;
	Checkers()
	{
		//declaring a board with these colors at position (50,100)
		board = new Board(Color.RED, Color.PINK,50,100,400,400);
		initTimer();
		
	}
	private void initTimer()
	{
		class TimerListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				repaint();
				if (board.getFill()>0)
				{
					t1.restart();
				}
				else
				{
					t1.stop();
				}
			}
		}
		t1 = new Timer(1000/FPS,new TimerListener());
	}
	public void paint(Graphics g)
	{
		Graphics2D g2= (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, 525, 700);
		board.draw(g2);
	}
	public void notifyMouseReleased()
	{
		board.notifyMouseReleased();
		t1.start();
	}

}
