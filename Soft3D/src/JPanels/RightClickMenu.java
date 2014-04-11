package JPanels;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class RightClickMenu extends JPopupMenu
{
	JMenuItem item;
	
	public RightClickMenu()
	{
		// STUB
		item = new JMenuItem("Click Me!");
		add(item);
	}
}
