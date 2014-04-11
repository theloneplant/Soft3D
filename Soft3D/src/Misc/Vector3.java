package Misc;

public class Vector3
{
	public double x, y, z;
	
	/** Default constructor, sets x, y, z to 0
	 */
	public Vector3()
	{
		x = y = z = 0;
	}
	
	/** Constructor to initialize value to all members
	 * @param s - Value applied to x, y, z
	 */
	public Vector3(double s)
	{
		x = y = z = s;
	}
	
	/** Memberwise constructor for x, y, z
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/** Memberwise constructor for x, y, z using another Vector3
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3(Vector3 other)
	{
		x = other.x;
		y = other.y;
		z = other.z;
	}

	
	/** Sets x, y, z for the vector
	 * @param x
	 * @param y
	 * @param z
	 */
	public void set(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
