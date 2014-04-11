package JPanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import Graphics.Camera;
import Graphics.Mesh;
import Misc.Vector2;
import Misc.Vector3;

public class View3D extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener
{
	private RightClickMenu menu; // Dummy menu for future use
	private final int SENSITIVITY = 150; // How much the mouse movements rotate the model 
	private final float STEP = 0.25f; // Constant used for zooming in (each 'step' zooms in this much)
	private Camera view; // Used to project and display models
	private ArrayList<Mesh> models; // Holds all vertices of models
	/* mouseBegin/mouseCurrent is used to calculate the amount the user is currently dragging.
	 * cameraOffset is a variable that saves all the previous camera rotations, so that
	 * when the user repeatedly drags the mouse, the program will remember its place
	 */
	private Vector2 mouseBegin, mouseCurrent, cameraOffset;
	private boolean lmb, rotating; // Booleans to check for toggleable actions
	private float zoom, time; // Zoom is used to find how far away the user is from the origin, time is used for rotation
	
	public View3D(int width, int height)
	{
		// Do some house cleaning to make the frame work properly
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		
		// Initialize objects/variables and set background color
		menu = new RightClickMenu();
		setBackground(new Color(40, 40, 40));
		view = new Camera(width, height);
		models = new ArrayList<Mesh>();
		mouseBegin = new Vector2();
		mouseCurrent = new Vector2();
		cameraOffset = new Vector2(0, Math.PI / 6 * SENSITIVITY);
		lmb = false;
		rotating = true;
		zoom = 3;
		time = 0;
	}
	
	public void update(double delta)
	{
		// Update screen size (make it responsive!)
		view.setScreen(new Vector2(getWidth(), getHeight()));
		
		// Update rotation animation unless it is disabled or user is rotating manually
		if (!lmb && rotating)
		{
			time += delta;
		
			for (int i = 0; i < models.size(); i++)
			{
				models.get(i).setRotation(new Vector3(0, time / 2, 0));
			}
		}
		
		// Find the amount that the user has dragged and use that to orbit the camera
		// Take into account the user's previous actions with cameraOffset
		// Also scale the amount rotated with SENSITIVITY
		double dx = -(mouseCurrent.x - mouseBegin.x + cameraOffset.x) / SENSITIVITY;
		double dy = -(mouseCurrent.y - mouseBegin.y + cameraOffset.y) / SENSITIVITY;
		
		// Do bounds checking to make sure the user doesn't rotate past +-90 degrees
		if (dy >= Math.PI / 2)
		{
			dy = Math.PI / 2;
		}
		else if (dy <= -Math.PI / 2)
		{
			dy = -Math.PI / 2;
		}
		
		// Update Camera with mouse movements
		view.setPosition(0, 0, zoom);
		view.setRotation((float) dy, (float) dx, 0);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		// Call super, and let it do its thing
		super.paintComponent(g);
		// Set line color
		g.setColor(new Color(230, 180, 150));
		
		// Draw each model
		for (int i = 0; i < models.size(); i++)
		{
			models.get(i).draw(g, view);
		}
		
		// Draw helpful text
		g.setColor(Color.WHITE);
		g.drawString("Soft3D is an experimental program using software based 3D rendering to draw primitive models", 5, 15);
		g.drawString("Left Mouse Button - Pan", 5, 35);
		g.drawString("Scroll Wheel - Zoom", 5, 50);
		g.drawString("Space - Toggle Rotation", 5, 65);
		g.drawString("Author - Michael LaPlante", 5, (int) (view.getScreen().y - 10));
	}
	
	public void addMesh(Mesh mesh)
	{
		models.add(mesh);
	}

	// ----------MOUSE INPUTS----------
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{	
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// If LMB is pressed
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			// Begin to keep track of mouse movement
			lmb = true;
			mouseBegin.x = e.getX();
			mouseBegin.y = e.getY();
			mouseCurrent.x = e.getX();
			mouseCurrent.y = e.getY();
			// Mouse movement is used to orbit the camera
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		// If the LMB is being dragged
		if (lmb)
		{
			// Update the difference between the original mouse position and the current
			// Used to get deltaX and deltaY used to calculate the camera rotation
			mouseCurrent.x = e.getX();
			mouseCurrent.y = e.getY();
		}
		// Mouse movement is used to orbit the camera
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// When LMB is released
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			// Stop keeping track of it
			lmb = false;
			// Save change in movement, so that it doesn't reset the camera position
			// every time the user releases LMB.
			cameraOffset.x += mouseCurrent.x - mouseBegin.x;
			cameraOffset.y += mouseCurrent.y - mouseBegin.y;
			
			// Check bounds (don't rotate past 90 degrees either way vertically)
			if (cameraOffset.y > Math.PI / 2 * SENSITIVITY)
				cameraOffset.y = Math.PI / 2 * SENSITIVITY;
			else if (cameraOffset.y < -Math.PI / 2 * SENSITIVITY)
				cameraOffset.y = -Math.PI / 2 * SENSITIVITY;
			
			// Reset variables for future use
			mouseBegin.x = 0;
			mouseBegin.y = 0;
			mouseCurrent.x = 0;
			mouseCurrent.y = 0;
		}
		else if (e.isPopupTrigger())
		{
			// Show a menu when RMB is released
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		// When the mouse wheel is moved, zoom in and out depending on input
		zoom += STEP * e.getWheelRotation();
		
		// Check bounds, keep the user in line
		if (zoom < 2)
			zoom = 2;
		else if (zoom > 10)
			zoom = 10;
	}

	// ----------KEYBOARD INPUTS----------
	
	@Override
	public void keyPressed(KeyEvent e)
	{
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// If space was released/pressed
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			// Toggle rotation animation
			if (rotating)
				rotating = false;
			else
				rotating = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
}