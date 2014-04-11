package Graphics;

import Misc.Vector2;
import Misc.Vector3;

public class Camera
{
	private Vector3 position, rotation;
	private Vector2 screen;
	private double fov, aspectRatio, nearPlane, farPlane;
	
	public Camera(int width, int height)
	{
		position = new Vector3(0, 0, 0);
		rotation = new Vector3(0, 0, 0);
		screen = new Vector2(width, height);
		fov = 55;
		if (height != 0)
			aspectRatio = width / (float) height;
		else
			aspectRatio = 1;
		nearPlane = 0;
		farPlane = 50;
	}
	
	public Vector2 project(Vector3 vertex, Vector3 modelPos, Vector3 modelRot, Vector3 modelScale)
	{
		Vector2 point = new Vector2();
		
		Vector3 vertexLocal = transform(vertex, modelPos, modelRot, modelScale);
		Vector3 cameraTrans = rotateY(vertexLocal, rotation);
		Vector3 cameraRotate = rotate(cameraTrans, new Vector3(rotation.x, 0, rotation.z));
		Vector3 cameraFinal = translate(cameraRotate, position);
		
		double halfX = screen.x / 2;
		double halfY = screen.y / 2;
		
		point.x = ((cameraFinal.x / cameraFinal.z) * halfY * 85 / fov) + halfX;
		point.y = (-(cameraFinal.y / cameraFinal.z) * halfY * 85 / fov) + halfY;
		return point;
	}

	public Vector3 translate(Vector3 vertex, Vector3 translate)
	{
		Vector3 v = new Vector3(vertex);
		
		// Translate
		v.x += translate.x;
		v.y += translate.y;
		v.z += translate.z;
		
		return v;
	}
	
	public Vector3 rotateX(Vector3 vertex, Vector3 rotate)
	{
		Vector3 v = new Vector3(vertex);
		
		// Rotate x-axis
		v.y = Math.cos(rotate.x) * vertex.y - Math.sin(rotate.x) * vertex.z; // Rotate y about x
		v.z = Math.sin(rotate.x) * vertex.y + Math.cos(rotate.x) * vertex.z; // Rotate z about x
		
		return v;
	}
	
	public Vector3 rotateY(Vector3 vertex, Vector3 rotate)
	{
		Vector3 v = new Vector3(vertex);
		
		// Rotate y-axis
		v.x = Math.cos(rotate.y) * vertex.x + Math.sin(rotate.y) * vertex.z;  // Rotate x about y
		v.z = -Math.sin(rotate.y) * vertex.x + Math.cos(rotate.y) * vertex.z; // Rotate z about y
		
		return v;
	}
	
	public Vector3 rotateZ(Vector3 vertex, Vector3 rotate)
	{
		Vector3 v = new Vector3(vertex);
		
		// Rotate z-axis
		v.x = Math.cos(rotate.z) * vertex.x- Math.sin(rotate.z) * vertex.y;   // Rotate x about z
		v.y = Math.sin(rotate.z) * vertex.x + Math.cos(rotate.z) * vertex.y;   // Rotate y about z
		
		return v;
	}
	
	public Vector3 rotate(Vector3 vertex, Vector3 rotate)
	{		
		Vector3 v1 = rotateX(vertex, rotate);
		Vector3 v2 = rotateY(v1, rotate);
		Vector3 v3 = rotateZ(v2, rotate);
		return v3;
	}
	
	public Vector3 scale(Vector3 vertex, Vector3 scale)
	{
		Vector3 v = new Vector3(vertex);
		
		// Scale
		v.x *= scale.x;
		v.y *= scale.y;
		v.z *= scale.z;
		
		return v;
	}
	
	public Vector3 transform(Vector3 vertex, Vector3 translate, Vector3 rotate, Vector3 scale)
	{
		Vector3 v1 = scale(vertex, scale);
		Vector3 v2 = rotate(v1, rotate);
		Vector3 v3 = translate(v2, translate);
		return v3;
	}
	
	public boolean inBounds(Vector2 point)
	{
		return point.x <= screen.x && point.x >= 0 && point.y <= screen.y && point.y >= 0;
	}
	
	public Vector3 getPosition()
	{
		return position;
	}

	public void setPosition(Vector3 position)
	{
		this.position = position;
	}
	
	public void setPosition(float x, float y, float z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public void setRotation(float x, float y, float z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	public double getNearPlane()
	{
		return nearPlane;
	}
	
	public double getFarPlane()
	{
		return farPlane;
	}
	
	public Vector2 getScreen()
	{
		return screen;
	}
	
	public void setScreen(Vector2 scr)
	{
		screen = scr;
	}
}
