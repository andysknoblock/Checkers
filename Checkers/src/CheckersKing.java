import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class CheckersKing implements Peice
{
	private int row, col, dir;
	private Color c;
	boolean selected=false;
	CheckersKing(int row, int col, int dir, Color c)
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
		g2.setColor(Color.BLACK);
		g2.drawOval(x+6, y+6, width-12, width-12);
		g2.setColor(Color.WHITE);
		g2.drawOval(x+5, y+5, width-10, width-10);
		g2.setColor(Color.BLACK);
		g2.drawOval(x+4, y+4, width-8, width-8);
		g2.setColor(Color.WHITE);
		g2.drawOval(x+3, y+3, width-6, width-6);
		g2.setColor(c);
	}
	//this shows all the points that the checker can move to
	public Point[] possibleMoves() 
	{
		Point p[] = new Point[4];
		p[0] = new Point(row-1, col+dir);
		p[1] = new Point(row+1, col+dir);
		p[2] = new Point(row-1, col-dir);
		p[3] = new Point(row+1, col-dir);
		return p;
	}
	public Point[] possibleJumps()
	{
		{
			Point p[] = new Point[4];
			p[0] = new Point(row-2, col+2*dir);
			p[1] = new Point(row+2, col+2*dir);
			p[2] = new Point(row-2, col-2*dir);
			p[3] = new Point(row+2, col-2*dir);
			return p;
		}
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
	public void move(int row, int col)
	{
		this.row=row;
		this.col=col;
	}
	public int getDir() 
	{
		return dir;
	}
	@Override
	public boolean isKing() {
		return true;
	}
	@Override
	public Color getColor() {
		return c;
	}

}
