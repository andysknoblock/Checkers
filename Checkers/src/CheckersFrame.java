import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class CheckersFrame {

	public static void main(String[] args) 
	{
		//creating a frame to hold checkers board
		JFrame frame = new JFrame();
		frame.setSize(525, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//create a Checkers to add to the frame - can execute logic and flow of Checkers
		Checkers c = new Checkers();
		class MouseListener1 implements MouseListener
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				c.notifyMouseReleased();
				
			}
			
		}
		c.addMouseListener(new MouseListener1());
		frame.add(c);
		//allow the frame and subcomponents (Checkers) to be seen
		frame.setVisible(true);
	}

}
