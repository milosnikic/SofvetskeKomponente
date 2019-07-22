package form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import file.FileBasicOperation;
import file.FileBasicOperationLocalImplementation;
import file.FileBasicOperationRemoteImplementation;
import implementation.Implementation;
import main.PropertiesFile;
import root.RootDirectory;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class FormMain extends JFrame {

	private JPanel contentPane;
	private JTextField txtRootName;
	private File file;
	private File folder;
	private File[] files;

	/**
	 * Create the frame.
	 * 
	 * @throws FileNotFoundException
	 */
	public FormMain() throws FileNotFoundException {

		setTitle("Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 26, 488, 329);
		contentPane.add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Initilization", null, panel, null);
		panel.setLayout(null);

		JLabel label = new JLabel("Choose root directory path");
		label.setBounds(14, 9, 152, 16);
		panel.add(label);

		JLabel label_1 = new JLabel("Root directory name:");
		label_1.setBounds(14, 71, 121, 16);
		panel.add(label_1);

		txtRootName = new JTextField();
		txtRootName.setBounds(147, 68, 116, 22);
		txtRootName.setColumns(10);
		panel.add(txtRootName);
		JButton button = new JButton("Init");
		JLabel lblError = new JLabel("");

		JFileChooser jfc = new JFileChooser(FileSystems.getDefault().getPath(".").toString());
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtRootName.getText().trim();
				String path = "/" + name;
				String folderPath1 = path;
				String folderPath2 = path;
				try {
					if (PropertiesFile.getInstance().getProperty("implementationtype").equals("local-implementation")) {
						path = Paths.get(RootDirectory.getInstance().getPath(), name).toString();
						folderPath1 = Paths.get(path, "grupa1").toString();
						folderPath2 = Paths.get(path, "grupa2").toString();
					} else {
						folderPath1 += "/grupa1";
						folderPath2 += "/grupa2";
					}
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				try {
					RootDirectory.getInstance().setPath(path);
					PropertiesFile.getInstance().addProperty("root", RootDirectory.getInstance().getPath());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println(path);
				Implementation.getInstance().getImplementation().createDirectory(path);
				Implementation.getInstance().getImplementation().createDirectory(folderPath1);
				Implementation.getInstance().getImplementation().createDirectory(folderPath2);
				Implementation.getInstance().getImplementation().createFile("prva.txt", folderPath1);
				Implementation.getInstance().getImplementation().createFile("druga.txt", folderPath2);
				lblError.setForeground(Color.GREEN);
				lblError.setText(RootDirectory.getInstance().getPath());
				button.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Initialization successfully done!");
				dispose();
				new FormUserInfo().setVisible(true);
				}
		});
		button.setBounds(313, 67, 51, 25);
		panel.add(button);

		JButton button_1 = new JButton("Choose");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFIle = jfc.getSelectedFile();
					RootDirectory.getInstance().setPath(selectedFIle.getAbsolutePath());
					lblError.setForeground(Color.GREEN);
					lblError.setText(RootDirectory.getInstance().getPath());
				} else {
					lblError.setForeground(Color.RED);
					lblError.setText("Directory already exists!");
				}
			}
		});
		button_1.setBounds(289, 5, 89, 25);
		panel.add(button_1);

		lblError.setBounds(14, 124, 350, 16);
		panel.add(lblError);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 488, 26);
		contentPane.add(menuBar);

		JMenuItem mntmFilesPreview = new JMenuItem("Files Preview");
		mntmFilesPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FormFilesPreview().setVisible(true);
			}
		});
		menuBar.add(mntmFilesPreview);
		if (PropertiesFile.getInstance().getProperty("implementationtype").equals("local-implementation")) {
			button_1.setVisible(true);
			label.setVisible(true);
			Implementation.getInstance().setImplementation(new FileBasicOperationLocalImplementation());
			System.out.println("Using local implementation");
		} else {
			button_1.setVisible(false);
			label.setVisible(false);
			System.out.println("Using remote implementation");
			String accessToken = PropertiesFile.getInstance().getProperty("acces-token");
			String config = PropertiesFile.getInstance().getProperty("username");
			Implementation.getInstance().setImplementation(new FileBasicOperationRemoteImplementation(accessToken, config));
		}
	}
}
