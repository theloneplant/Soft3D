package Graphics;

import java.awt.Graphics;

import Misc.Vector2;
import Misc.Vector3;

public class Mesh
{
	private String name;
	private Vector3 vertices[]; // Array of points for the model
	private Vector3 faces[]; // Array of faces (3 vertices per face) that helps the order of drawing
	private Vector3 position, rotation, scale; // Coordinates to determine its place in the world
	
	public Mesh(String name, int verticesCount, int faceCount)
	{
		// Initialize all variables and populate vertices
		this.name = name;
		vertices = new Vector3[verticesCount];
		faces = new Vector3[faceCount];
		for (int i = 0; i < verticesCount; i++)
		{
			vertices[i] = new Vector3();
		}
		position = new Vector3();
		rotation = new Vector3();
		scale = new Vector3(1, 1, 1);
	}

	public void draw(Graphics g, Camera view)
	{
		Vector2 points[] = new Vector2[vertices.length];
		
		// Project each vertex to the array of 2D points
		for (int i = 0; i < vertices.length; i++)
		{
			points[i] = view.project(vertices[i], position, rotation, scale);
		}
		
		// Draw each triangle face
		for (int i = 0; i < faces.length; i++)
		{
			// Faces store 3 values - each is the index for a vertex
			int p1 = (int) faces[i].x - 1;
			int p2 = (int) faces[i].y - 1;
			int p3 = (int) faces[i].z - 1;
			
			// Draw from P1 -> P2
			g.drawLine((int) points[p1].x, (int) points[p1].y, 
					   (int) points[p2].x, (int) points[p2].y);
			// Draw from P2 -> P3
			g.drawLine((int) points[p2].x, (int) points[p2].y, 
					   (int) points[p3].x, (int) points[p3].y);
			// Draw from P3 -> P1
			g.drawLine((int) points[p3].x, (int) points[p3].y, 
					   (int) points[p1].x, (int) points[p1].y);
		}
	}
	
	public Vector3 getPosition()
	{
		return position;
	}

	public void setPosition(Vector3 position)
	{
		this.position = position;
	}

	public Vector3 getRotation()
	{
		return rotation;
	}

	public void setRotation(Vector3 rotation)
	{
		this.rotation = rotation;
	}
	
	public Vector3 getScale()
	{
		return scale;
	}

	public void setScale(Vector3 scale)
	{
		this.scale = scale;
	}

	public Vector3[] getVertices()
	{
		return vertices;
	}

	public void setVertices(Vector3 vertices[])
	{
		this.vertices = vertices;
	}
	
	public Vector3[] getFaces()
	{
		return faces;
	}

	public void setFaces(Vector3 faces[])
	{
		this.faces = faces;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
