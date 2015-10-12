import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JPanel;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class DrawPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Firebase myFirebaseRef;
	Font font = new Font("Verdana", Font.BOLD, 20);
	
	private Vector<User> users = new Vector<User>();
	
	public DrawPanel() {
		myFirebaseRef = new Firebase("https://funwithwords.firebaseio.com/");
		myFirebaseRef.removeValue(); //Cleans out everything
		myFirebaseRef.child("ScreenNbr").setValue(Constants.screenNbr);
		myFirebaseRef.addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
				Iterable<DataSnapshot> dsList= arg0.getChildren();
				Collections.sort(users);
				System.out.println(arg0.getKey());
				System.out.println(arg0.getChildrenCount());
				int place = Collections.binarySearch(users, new User(arg0.getKey())); //Find the user usernama has to be unique uses the method compareTo in User
				 for (DataSnapshot dataSnapshot : dsList) {					 
					 if (dataSnapshot.getKey().equals("Letter")){
						 users.get(place).setLastLetter((String) dataSnapshot.getValue());
					 }
					 
				 }
				repaint();
			}
			
			@Override
			public void onChildAdded(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				if (arg0.hasChildren()){
					System.out.println(arg0.getKey());
					User user = new User(arg0.getKey());
					if (!users.contains(user)){
						users.add(user);
			 		}
				}
				
			}
			
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//Called when the screen needs a repaint.
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2= (Graphics2D) g;
			g2.setFont(font);
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(0, 0, getSize().width, getSize().height);
			g2.setColor(Color.BLACK);
			g.drawString("ScreenNbr: "+Constants.screenNbr, 10,  20);
			//Test
			int offset = 50;
			for (User user : users) {
				g2.setColor(Color.BLACK);
				g.drawString("User: "+user.getId()+" : "+ user.getLastLetter(), 10,  20 + offset);
				offset += 30;
			}
			
		}

}
