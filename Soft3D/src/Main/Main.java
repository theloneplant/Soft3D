package Main;

import javax.swing.JFrame;

import FileLoader.ModelLoader;
import Graphics.Mesh;
import JPanels.View3D;
import Misc.Vector3;

public class Main
{
	public static void main(String[] args)
	{
		//Initialize the main window and rendering frame
		View3D game = new View3D(0, 0);
		JFrame frame = new JFrame("Soft3D");
		frame.setSize(1100, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(game);
		frame.setVisible(true);
		
		// Declare variables used to keep time (used for capping out FPS)
		int targetFPS = 60; 
		double prevTime = System.currentTimeMillis() / 1000.0;
		double currentTime = System.currentTimeMillis() / 1000.0;
		double delta = 0;
		
		// Load in an obj
		ModelLoader loader = new ModelLoader();
		Mesh mesh = loader.load("teapot.obj");
		game.addMesh(mesh);
		
		
		// Main loop
		while(true)
		{
			// Calculate delta
			currentTime = System.currentTimeMillis() / 1000.0;
			delta = currentTime - prevTime;
			
			// Only update if at or below max FPS
			if (delta >= 1.0 / targetFPS)
			{
				// Update time
				prevTime = System.currentTimeMillis() / 1000.0;
				
				game.update(delta);
				game.repaint();
			}
		}
	}
}
