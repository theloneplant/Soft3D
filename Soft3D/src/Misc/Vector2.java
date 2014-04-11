package Misc;

public class Vector2
{
	public double x, y;
	
	/** Default constructor, sets x, y to 0
	 */
	public Vector2()
	{
		x = y = 0;
	}
	
	/** Constructor to initialize value to all members
	 * @param s - Value applied to x, y
	 */
	public Vector2(double s)
	{
		x = y = s;
	}
	
	/** Memberwise constructor for x, y
	 * @param x - X value
	 * @param y - Y value
	 */
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}
