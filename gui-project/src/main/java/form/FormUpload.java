package form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.FileUtil;
import implementation.Implementation;
import main.PropertiesFile;
import root.RootDirectory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class FormUpload extends JFrame {

	private JPanel contentPane;
	private File[] files;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormUpload frame = new FormUpload();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormUpload() {
		setTitle("Upload files");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Choose files to upload:");
		label.setBounds(12, 17, 152, 16);
		contentPane.add(label);

		JButton button = new JButton("Choose");
		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					files = jfc.getSelectedFiles();
					JOptionPane.showMessageDialog(null, "Files selected successfully!");
				}
			}
		});
		button.setBounds(176, 13, 97, 25);
		contentPane.add(button);

		JButton button_1 = new JButton("Upload");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File[] newArray = new File[files.length + 1];
				for (int i = 0; i < files.length; i++) {
					newArray[i] = files[i];
				}
				newArray[files.length] = new File("user.properties");

				Properties prop = new Properties();
				try {
					prop.load(new FileInputStream(new File("user.properties")));
					String name = prop.getProperty("firstname") + "_" + prop.getProperty("lastname") + "_"
							+ prop.getProperty("index") + "_" + prop.getProperty("group") + ".zip";
					FileUtil.zip(name, newArray);
					String path = "";
					if (PropertiesFile.getInstance().getProperty("implementationtype").equals("local-implementation")) {

						path = Paths
								.get(PropertiesFile.getInstance().getProperty("root"), prop.getProperty("group"), name)
								.toString();
						System.out.println(path);
					} else {

						path = PropertiesFile.getInstance().getProperty("root") + "/" + prop.getProperty("group") + "/"
								+ name;
						System.out.println(path);
					}
					Implementation.getInstance().getImplementation().uploadFile(new File(name), path);
					JOptionPane.showMessageDialog(null, "Files successfully uploaded!");
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		});
		button_1.setBounds(365, 239, 97, 25);
		contentPane.add(button_1);
	}

}
