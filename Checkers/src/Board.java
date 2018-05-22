import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Board
{
	final int WIDTH = 525, HEIGHT = 650;
	private Point selected = null;
	private Color c1,c2;
	private int x, y, width, height, rows=8, cols=8, turn,squareWidth, fill=0, xSquare=0,ySquare=0, winner=0;
	private int[][] data = new int[rows][cols];
	private boolean jumped=false;
	ArrayList <Peice> peices =  new  ArrayList<Peice>();
	Board(Color c1, Color c2, int x, int y, int width, int height)
	{
		//board will allow for customizable colors
		this.c1=c1;
		this.c2=c2;
		//positioning of board
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		squareWidth=width/8;
		clearBoard();
		setupCheckers(Color.WHITE,Color.BLACK);
	}
	public void print()
	{
		System.out.println();
		for (int a=0; a<data.length; a++)
		{
			for (int b=0; b<data.length; b++)
			{
				System.out.print(data[a][b]);
			}
			System.out.println();
		}
	}
	//if a mousePress is detected, this will handle it
	public void notifyMousePress()
	{

	}
	//handles the mouseRelease
	public void notifyMouseReleased()
	{
		Point p = MouseInfo.getPointerInfo().getLocation();
		//sends to handler which will tell what square was released on. Won't move once the game there is a winner
		if (winner==0)
		{
			diagnoseSquare(p);
		}
	}
	public void diagnoseSquare(Point p)
	{
		int xCoord = (int) p.getX(), yCoord = (int) p.getY();
		xSquare = (xCoord-x)/squareWidth;
		ySquare = (yCoord-y-squareWidth/2)/squareWidth;
		fill=240;
		if (selected==null)
		{
			if (ySquare>=0 && ySquare<8 && xSquare>=0 && xSquare<8)
			{
				if (data[ySquare][xSquare]==turn+1)
				{
					selected = new Point(xSquare, ySquare);
				}
			}
		}
		else
		{
			Peice temp = findChecker(selected.x,selected.y);
			if (temp!=null)
			{
				boolean removeTemp = false;
				Peice removed = null;
				Point moves[] = temp.possibleMoves();
				for (int i=0; i<moves.length; i++)
				{
					Point move = moves[i];
					boolean continu = true;
					for (Peice unit: peices)
					{
						if (unit.getCol() == move.y && unit.getRow() == move.x)
						{
							continu = false;
						}
						if (unit.getDir() != temp.getDir())
						{
							if (unit.getCol() == move.y && unit.getRow() == move.x)
							{
								Point jumpSpot = temp.possibleJumps()[i];
								if (isUnfilledSquare(jumpSpot.y, jumpSpot.x))
								{
									if (jumpSpot.x==xSquare && jumpSpot.y==ySquare)
									{
										removeTemp = true;
										data[selected.y][selected.x] = 0;
										data[jumpSpot.y][jumpSpot.x] = turn+1;
										temp.move(jumpSpot.x, jumpSpot.y);
										if (!hasJumps(temp))
										{
											doTurn();
										}
										else
										{
											jumped=true;
										}
										removed = findChecker(move.x, move.y);
										data[move.y][move.x]=0;
									}
									continu=false;
								}
							}
						}
					}
					if (move.x==xSquare && move.y==ySquare && continu && !jumped)
					{
						peices.remove(temp);
						data[selected.y][selected.x] = 0;
						data[move.y][move.x] = turn+1;
						temp.move(move.x, move.y);
						peices.add(temp);
						doTurn();
					}
				}
				
				if (removed!=null)
				{
					peices.remove(removed);
					peices.add(temp);
					removed=null;
				}
				if (removeTemp)
				{
					peices.remove(temp);
					removeTemp=false;
				}
				
			}
			selected=null;
		}
		
		checkForKing();
		checkWin();
	}
	boolean hasJumps(Peice temp)
	{
		Point moves[] = temp.possibleMoves();
		for (int i=0; i<moves.length; i++)
		{
			for (Peice unit: peices)
			{
				if (unit.getDir() != temp.getDir())
				{
					if (unit.getCol() == moves[i].y && unit.getRow() == moves[i].x)
					{
						Point jumpSpot = temp.possibleJumps()[i];
						if (isUnfilledSquare(jumpSpot.y, jumpSpot.x))
						{
							System.out.println("YEET");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public void checkWin()
	{
		boolean p1Peices=false;
		boolean p2Peices=false;
		for (int a=0; a<data.length; a++)
		{
			for (int b=0; b<data[a].length; b++)
			{
				if (data[a][b]==1)
					p1Peices=true;
				if (data[a][b]==2)
					p2Peices=true;
			}
		}
		if (!p1Peices)
		{
			winner=2;
		}
		if (!p2Peices)
		{
			winner=1;
		}
	}
	//checks if a peice needs to be turned into a king
	public void checkForKing()
	{
		
		for (int i=0; i<peices.size(); i++)
		{
			Peice temp = peices.get(i);
			if (!temp.isKing())
			{
				if ((temp.getCol()==7 && temp.getDir()==1) ||(temp.getCol()==0 && temp.getDir()==-1))
				{
					peices.remove(temp);
					i--;
					peices.add(new CheckersKing(temp.getRow(), temp.getCol(), temp.getDir(), temp.getColor()));
				}
			}
		}
	}
	//this empties the array 
	public void clearBoard()
	{
		for (int a=0;a<rows;a++)
		{
			for (int b=0; b<cols;b++)
			{
				data[a][b]=0;
			}
		}
	}
	//clears out all of the peices in the array
	public void clearPeices()
	{
		while (peices.size()>0)
		{
			peices.remove(0);
		}
	}
	//sets up the checkers with parameters for the color of each player
	public void setupCheckers(Color c1, Color c2)
	{
		//setup turn randomly
		Random random = new Random();
		turn = random.nextInt(2);
		clearPeices();
		//peices for player 1
		for (int a=0;a<3;a++)
		{
			for (int b=0;b<cols;b+=2)
			{
				Peice c = null,d=null;
				//for alternating rows use modulus division
				if (a%2==0)
				{
					c = new CheckerPeice(b,a,1,c1);
					data[a][b]=1;
					d = new CheckerPeice(b+1,7-a,-1,c2);
					data[7-a][b+1] = 2;
				}
				else
				{
					c = new CheckerPeice(b+1,a,1,c1);
					data[a][b+1]=1;
					d = new CheckerPeice(b,7-a,-1,c2);
					data[7-a][b]=2;
				}
				peices.add(c);
				peices.add(d);
			}
		}
	}
	public Peice findChecker(int row, int col)
	{
		for (Peice p: peices)
		{
			if (row==p.getRow()&&col==p.getCol())
			{
				return p;
			}
		}
		return null;
	}
	public void draw (Graphics2D g2)
	{
		
		//a simple neseted loop to draw each square with a boolean which is changed each 
		boolean color1=true;
		for (int a=0; a<rows;a++)
		{
			for (int b=0; b<cols;b++)
			{
				//color1 determines which color the square is
				if (color1)
					g2.setColor(c1);
				else
					g2.setColor(c2);
				g2.fillRect(b*width/cols+x, a*width/rows+y, width/cols, height/rows);
				//color changed each time
				color1 = !color1;
			}
			//color changed each row to form alternating pattern if the there are an even number of rows
			if (rows%2==0)
			{
				color1 = !color1;
			}
		}
		// loop to draw all of the peices on the board
		for (int a=0;a<peices.size();a++)
		{
			//gets from array list and draws it
			Peice temp = peices.get(a);
			Point location = toCoords(temp.getRow(),temp.getCol());
			temp.draw(g2, location.x,location.y,width/8);
		}

		//draws the colors on the squares if they need colors
		if (fill>0)
		{
			squareColors(g2);
		}
		//draws currently selected square
		if (selected!=null)
		{
			g2.setColor(new Color(100,100,200,100));
			Point selectedOnscreen = toCoords(selected.x,selected.y);
			g2.fillRect(selectedOnscreen.x, selectedOnscreen.y, squareWidth, squareWidth);
		}
		
		//draws who's turn it is
		g2.setColor(Color.WHITE);
		g2.drawString("Player 1", WIDTH/2-15, 80);
		g2.drawString("Player 2", WIDTH/2-15, HEIGHT-120);
		g2.drawString("Player " + (turn+1) + "'s turn", WIDTH/2-30, HEIGHT-100);
		if (winner!=0)
		{
			g2.drawString("CONGRATS PLAYER " + winner + "!! YOU HAVE WON!!", WIDTH/2-80, HEIGHT-50);
		}
	}
	//this function will check if a square is valid - if it's inside the bounds for the board or filled
	public boolean isUnfilledSquare(int row, int col)
	{
		if (row>=0 && col>=0 && row<8 && col<8)
		{
			if (data[row][col]==0)
			{
				return true;
			}
		}
		return false;
	}
	//this function will check if a square is valid but not filled
	public boolean isValidSquare(int row, int col)
	{
		if (row>=0 && row<rows && col>=0 && col>cols)
			return true;
		else
			return false;
	}
	//this function will turn a coordinate in rows and columns into a number of pixels 
	public Point toCoords(int rows, int cols)
	{
		if (rows>7)
			rows=7;
		if (cols>7)
			cols=7;
		int xVal = rows*width/8+x;
		int yVal = cols*height/8+y;

		Point p = new Point(xVal,yVal);
		return p;
	}
	//fills squares if they need fill
	public void squareColors(Graphics2D g2)
	{
		if (fill>0)
			fill-=20;
		g2.setColor(new Color(200,100,100,fill));
		Point p = toCoords(xSquare,ySquare);
		g2.fillRect((int) p.getX(), (int) p.getY(), squareWidth, squareWidth);
		
	}
	public int getFill()
	{
		return fill;
	}
	public void doTurn()
	{
		turn++;
		if (turn>=2)
		{
			turn =0;
		}
	}

}
