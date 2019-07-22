package form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import file.FileBasicOperation;
import file.FileBasicOperationLocalImplementation;
import root.RootDirectory;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class FormFilesPreview extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private FileBasicOperation implementation = new FileBasicOperationLocalImplementation();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FormFilesPreview dialog = new FormFilesPreview();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FormFilesPreview() {
		setTitle("Hierarchy preview");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblFilesInsideFolder = new JLabel("Files inside folder:");
			lblFilesInsideFolder.setBounds(12, 13, 171, 16);
			contentPanel.add(lblFilesInsideFolder);
		}

		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 32, 398, 198);
		contentPanel.add(textArea);
		String str = implementation.filePreview(new File(RootDirectory.getInstance().getPath()));
		System.out.println(str);
		textArea.setText(str);
		textArea.setEditable(false);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
}
