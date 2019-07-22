package form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.PropertiesFile;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class FormOptions extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtUsername;
	private JTextField txtAccessToken;
	private JLabel lblAccessToken;
	private JLabel lblUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FormOptions dialog = new FormOptions();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FormOptions() {
		setTitle("Options");
		setBounds(100, 100, 609, 262);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Choose which implementation you want:");
			lblNewLabel.setBounds(12, 13, 266, 16);
			contentPanel.add(lblNewLabel);
		}

		JRadioButton rdbtnRemoteImplementation = new JRadioButton("remote implementation");
		buttonGroup.add(rdbtnRemoteImplementation);
		rdbtnRemoteImplementation.setBounds(8, 38, 223, 25);
		contentPanel.add(rdbtnRemoteImplementation);

		final JRadioButton rdbtnLocalImplementation = new JRadioButton("local implementation");
		buttonGroup.add(rdbtnLocalImplementation);
		rdbtnLocalImplementation.setBounds(8, 146, 170, 25);
		contentPanel.add(rdbtnLocalImplementation);
		rdbtnLocalImplementation.setSelected(true);
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(28, 72, 93, 16);
		contentPanel.add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setBounds(140, 69, 209, 22);
		contentPanel.add(txtUsername);
		txtUsername.setColumns(10);

		lblAccessToken = new JLabel("Access token");
		lblAccessToken.setBounds(28, 101, 93, 16);
		contentPanel.add(lblAccessToken);

		txtAccessToken = new JTextField();
		txtAccessToken.setBounds(140, 98, 211, 22);
		contentPanel.add(txtAccessToken);
		txtAccessToken.setColumns(10);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {

							String implementationType = "";
							if (rdbtnLocalImplementation.isSelected()) {
								implementationType = "local-implementation";

							} else {
								PropertiesFile.getInstance().addProperty("username", txtUsername.getText().trim());
								PropertiesFile.getInstance().addProperty("acces-token",
										txtAccessToken.getText().trim());
								implementationType = "remote-implementation";
							}
							PropertiesFile.getInstance().addProperty("implementationtype", implementationType);
							PropertiesFile.getInstance().save();
							new FormMain().setVisible(true);
							dispose();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
