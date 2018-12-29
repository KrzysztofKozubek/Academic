import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class Notatnik extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]){
        Notatnik notatnik = new Notatnik();
        notatnik.setVisible(true);
	}
	
	private MenuBar mb = new MenuBar();
		private Menu fl = new Menu();
			private MenuItem of = new MenuItem();
			private MenuItem sf = new MenuItem();
			private MenuItem fd = new MenuItem();
			
	private TextArea ta = new TextArea("",0,0, TextArea.SCROLLBARS_BOTH);
	
	public Notatnik(){
		this.setTitle("JNotatnik");
		this.setSize(700, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(ta);
		
		this.setMenuBar(this.mb);			//	Menu Bar
		this.mb.add(this.fl);
		
		this.fl.setLabel("Plik");			// Menu
		
		//***** Menu items
		this.of.setLabel("Otwórz");			//
		this.of.addActionListener(this);	//	Open
		this.fl.add(this.of);				//
		
		this.sf.setLabel("Zapisz");			//
		this.sf.addActionListener(this);	//	Save	Dodatkowy komentarz: Zapisać można tylko do utworzonego wcześniej pliku lub do domyślnego katalogu
		this.fl.add(this.sf);				//
		
		this.fd.setLabel("Wyszukaj");		//
		this.fd.addActionListener(this);	//	Find
		this.fl.add(this.fd);				//
		//***** end Menu items
}
		public void actionPerformed (ActionEvent e){
			if (e.getSource() == this.of){
				JFileChooser open = new JFileChooser();
				int o = open.showOpenDialog(this);
				
				if (o == JFileChooser.APPROVE_OPTION){
					this.ta.setText("");
					try{
							Scanner sc = new Scanner(new FileReader(open.getSelectedFile().getPath()));
							while (sc.hasNext())
							this.ta.append(sc.nextLine() + "\n");
					} catch (Exception ex){
						System.out.println("Błąd podczas otwierania pliku");
					}
				}
			}
			
			
			else if (e.getSource() == this.sf){
				JFileChooser save = new JFileChooser(); 
				int s = save.showSaveDialog(this);
				if (s == JFileChooser.APPROVE_OPTION){
					try{
						
						BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
						out.write(this.ta.getText()); 
						out.close();
					} catch (Exception ex){
						System.out.println("Błąd podczas zapisywania pliku. Można zapisać text tylko do uprzednio utworzonego pliku w danym katalogu lub do katalogu głównego");
					}
				}
			}
		}
		 //brakło czasu na resztę :(
	}

