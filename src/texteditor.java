import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
//TEXT_EDITOR
public class texteditor extends JFrame implements ActionListener{
	JTextArea textArea;
	JScrollPane scrollPane;
	JLabel fontlabel;
	JSpinner fontSizeSpinner;
	JButton fontColorButton;
	JComboBox fontbox;
	JMenuBar menubar;
	JMenu filemenu;
	JMenuItem openitem,saveitem,exititem;
	texteditor()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CodeClause TEXT EDITOR");
		this.setSize(600,720);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		textArea = new JTextArea(10,11);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial",Font.PLAIN,50));
		
		scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		fontlabel =  new JLabel("Font:");
		
		fontSizeSpinner  = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue()));
				
			}
			
		});
		
		fontColorButton  = new JButton("Color");
		fontColorButton.addActionListener(this);
		
		String[] fonts  = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//This line is used to take all the available fonts into the array of strings 
		fontbox = new JComboBox(fonts);
		fontbox.addActionListener(this);
		fontbox.setSelectedItem("Arial");
		
		menubar =   new JMenuBar();
		filemenu = new JMenu("File");
		openitem = new JMenuItem("Open");
		saveitem = new JMenuItem("Save");
		exititem = new JMenuItem("Exit");
		
		openitem.addActionListener(this);
		saveitem.addActionListener(this);
		exititem.addActionListener(this);
		
		filemenu.add(openitem);
		filemenu.add(saveitem);
		filemenu.add(exititem);
		menubar.add(filemenu);
		
		this.setJMenuBar(menubar);
		this.add(fontlabel);
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(fontbox);
		this.add(scrollPane);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new texteditor();


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==fontColorButton)
		{
			JColorChooser colorChooser = new JColorChooser();
			Color color  = colorChooser.showDialog(null,"Choose a color",Color.black);
			textArea.setForeground(color);
		}
		if(e.getSource()==fontbox)
		{
			textArea.setFont(new Font((String) fontbox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
		}
		if(e.getSource()==openitem)
		{
			JFileChooser fileChooser  = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
			
			if(response==JFileChooser.APPROVE_OPTION)
			{
				File file   = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
		}
		if(e.getSource()==saveitem)
		{
			JFileChooser fileChooser  = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			int response = fileChooser.showSaveDialog(null);
			if(response == JFileChooser.APPROVE_OPTION)
			{
				File file;
				PrintWriter fileOut = null;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
				fileOut = new PrintWriter(file);
				fileOut.println(textArea.getText());
				}
				catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
		if(e.getSource()==exititem)
		{
			System.exit(0);
		}
		
	}

}
