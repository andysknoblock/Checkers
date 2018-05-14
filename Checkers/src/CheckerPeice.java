import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class CheckerPeice implements Peice
{
	private int row, col, dir;
	private Color c;
	boolean selected=false;
	CheckerPeice(int row, int col, int dir, Color c)
	{
		this.row=row;
		this.col=col;
		this.dir=dir;
		this.c=c;
	}
	public void draw(Graphics2D g2, int x, int y, int width)
	{
		g2.setColor(c);
		g2.fillOval(x, y, width, width);
	}
	//this shows all the points that the checker can move to
	public Point[] possibleMoves() 
	{
		Point p[] = new Point[2];
		p[0] = new Point(row-1, col+dir);
		p[1] = new Point(row+1, col+dir);
		return null;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}

}
