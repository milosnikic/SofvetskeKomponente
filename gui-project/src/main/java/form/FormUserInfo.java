package form;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import implementation.Implementation;
import root.RootDirectory;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormUserInfo extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstname;
	private JTextField txtLastname;
	private JTextField txtIndex;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormUserInfo frame = new FormUserInfo();
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
	public FormUserInfo() {
		setTitle("User info");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(172, 110, 299, 22);
		contentPane.add(comboBox);

		LinkedList<String> groups = (LinkedList<String>) Implementation.getInstance().getImplementation()
				.getDirectories(RootDirectory.getInstance().getPath());
		comboBox.setBounds(172, 110, 299, 22);

		comboBox.removeAllItems();
		for (String group : groups) {
			comboBox.addItem(group);
		}
		JLabel label = new JLabel("Firstname:");
		label.setBounds(12, 13, 107, 16);
		contentPane.add(label);

		JLabel label_1 = new JLabel("Lastname:");
		label_1.setBounds(12, 42, 107, 16);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("Index:");
		label_2.setBounds(12, 71, 56, 16);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("Group:");
		label_3.setBounds(12, 110, 56, 16);
		contentPane.add(label_3);

		txtFirstname = new JTextField();
		txtFirstname.setColumns(10);
		txtFirstname.setBounds(172, 13, 299, 22);
		contentPane.add(txtFirstname);

		txtLastname = new JTextField();
		txtLastname.setColumns(10);
		txtLastname.setBounds(172, 39, 299, 22);
		contentPane.add(txtLastname);

		txtIndex = new JTextField();
		txtIndex.setColumns(10);
		txtIndex.setBounds(172, 71, 299, 22);
		contentPane.add(txtIndex);

		JButton button = new JButton("Confirm");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Properties user = new Properties();
				try {
					OutputStream out = new FileOutputStream("user.properties");
					String firstname = txtFirstname.getText().trim();
					String lastname = txtLastname.getText().trim();
					String index = txtIndex.getText().trim();
					String group = (String) comboBox.getSelectedItem();
					user.setProperty("firstname", firstname);
					user.setProperty("lastname", lastname);
					user.setProperty("index", index);
					user.setProperty("group", group);
					user.store(out, "User information");
					JOptionPane.showMessageDialog(null, "Information successfully saved!");
					dispose();
					new FormUpload().setVisible(true);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		button.setBounds(393, 223, 97, 25);
		contentPane.add(button);
	}

}
