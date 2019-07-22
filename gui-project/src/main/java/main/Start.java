package main;

import java.io.FileNotFoundException;

import javax.swing.JDialog;

import form.FormOptions;

public class Start {

	public static void main(String[] args) throws FileNotFoundException {
		JDialog formLogin = new FormOptions();
		formLogin.setVisible(true);

	}

}
