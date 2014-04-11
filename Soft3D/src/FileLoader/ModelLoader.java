package FileLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Graphics.Mesh;
import Misc.Vector3;

public class ModelLoader
{
	private File file;
	private Scanner reader;
	private ArrayList<Vector3> vertices, faces;
	
	public ModelLoader()
	{
		vertices = new ArrayList<Vector3>();
		faces = new ArrayList<Vector3>();
	}
	
	public Mesh load(String name)
	{
		int vCount = 0, fCount = 0;
		
		try
		{
			file = new File(name);
			reader = new Scanner(file);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		while (reader.hasNext())
		{
			String line = reader.nextLine();
			String[] tokens = line.split(" ");
			
			if (tokens[0].equals("v"))
			{
				vCount++;
				float v1 = Float.parseFloat(tokens[1]);
				float v2 = Float.parseFloat(tokens[2]);
				float v3 = Float.parseFloat(tokens[3]);
				vertices.add(new Vector3(v1, v2, v3));
			}
			else if (tokens[0].equals("f"))
			{
				
				fCount++;
				if (tokens.length == 4)
				{
					String[] fToken1 = tokens[1].split("/");
					String[] fToken2 = tokens[2].split("/");
					String[] fToken3 = tokens[3].split("/");
					float f1 = Float.parseFloat(fToken1[0]);
					float f2 = Float.parseFloat(fToken2[0]);
					float f3 = Float.parseFloat(fToken3[0]);
					faces.add(new Vector3(f1, f2, f3));
				}
				else if (tokens.length == 5)
				{
					fCount++;
					String[] fToken1 = tokens[1].split("/");
					String[] fToken2 = tokens[2].split("/");
					String[] fToken3 = tokens[3].split("/");
					String[] fToken4 = tokens[4].split("/");
					float f1 = Float.parseFloat(fToken1[0]);
					float f2 = Float.parseFloat(fToken2[0]);
					float f3 = Float.parseFloat(fToken3[0]);
					float f4 = Float.parseFloat(fToken4[0]);
					faces.add(new Vector3(f1, f2, f3));
					faces.add(new Vector3(f1, f3, f4));
				}
				else
				{
					System.out.println("Incorrect File Type");
					return null;
				}
			}
		}
		
		// Build the mesh
		
		Mesh mesh = new Mesh(name, vCount, fCount);
		Vector3[] vert = normalize(vertices);
		Vector3[] face = new Vector3[faces.size()];
		
		for (int i = 0; i < faces.size(); i++)
		{
			face[i] = faces.get(i);
		}
		
		mesh.setVertices(vert);
		mesh.setFaces(face);
		vertices.clear();
		faces.clear();
		reader.close();
		
		return mesh;
	}
	
	private Vector3[] normalize(ArrayList<Vector3> list)
	{
		Vector3[] vertices = new Vector3[list.size()];
		double max, xCount, yCount, zCount;
		max = xCount = yCount = zCount = 0;
		
		for (int i = 0; i < list.size(); i++)
		{
			double v1 = list.get(i).x;
			double v2 = list.get(i).y;
			double v3 = list.get(i).z;

			if (v3 > max)
				max = v3;
			
			xCount += v1;
			yCount += v2;
			zCount += v3;
		}
		
		for (int i = 0; i < list.size(); i++)
		{
			vertices[i] = list.get(i);
			vertices[i].x -= xCount / list.size();
			vertices[i].y -= yCount / list.size();
			vertices[i].z -= zCount / list.size();
		}
		
		for (int i = 0; i < list.size(); i++)
		{
			vertices[i].x /= max;
			vertices[i].y /= max;
			vertices[i].z /= max;
		}
		
		return vertices;
	}
}
