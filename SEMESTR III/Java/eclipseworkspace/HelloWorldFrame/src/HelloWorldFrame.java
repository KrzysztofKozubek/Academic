import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelloWorldFrame {
	public HelloWorldFrame(){
	    super();
	    JLabel l = new JLabel(new ImageIcon("image.jpg"));
	    this.add(l);
	}
	    private void add(JLabel l) {
		// TODO Auto-generated method stub
		
	}
		public static void main(String[] args){
	        HelloWorldFrame frame = new HelloWorldFrame();
	        frame.setTitle("Pierwsze Okno");
	        frame.pack();
	        frame.setVisible(true);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
		private void pack() {
			// TODO Auto-generated method stub
			
		}
		private void setTitle(String string) {
			// TODO Auto-generated method stub
			
		}
	  }

