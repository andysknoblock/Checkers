import java.awt.Graphics2D;
import java.awt.Point;
//this interface allows multiple peices of different type to be stored in the same array
//for checkers this would be a regular peice and a king
//this is reusable code which could work for other peices too like those in chess
public interface Peice 
{
	public void draw(Graphics2D g2, int x, int y, int width);
	public Point[] possibleMoves();
	public int getRow();
	public int getCol();
}
