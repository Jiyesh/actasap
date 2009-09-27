/*
Accounting101
Copyright (C) 2009  Bhupendra Aole

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
*/
/*
 * File: JournalEntry.java
 * Author: Bhupendra Aole
 * Date: Sep 27, 2009: 
 * 
 * Revisions:
 * ----------
 */

package aole.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import aole.db.DBConnection;
import aole.misc.SpringUtilities;
import aole.model.JournalEO;

public class JournalEntry implements ActionListener {
	String[] labels = { "Date: ", "Amount: ", "DR Account: ", "CR Account: ",
			"Description: " };
	int numPairs = labels.length;
	JTextField tf[] = new JTextField[numPairs];

	private void createAndShowGUI () {
		// Create and populate the panel.
		JPanel p = new JPanel(new SpringLayout());
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			p.add(l);
			tf[i] = new JTextField(10);
			l.setLabelFor(tf[i]);
			p.add(tf[i]);
		}
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(this);
		btnApply.setActionCommand("insert");

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		btnExit.setActionCommand("exit");

		p.add(btnExit);
		p.add(btnApply);

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(p, numPairs + 1, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		// Create and set up the window.
		JFrame frame = new JFrame("Journal Entry Form");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container cp = frame.getContentPane();
		cp.add(p);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main (String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				JournalEntry je = new JournalEntry();
				je.createAndShowGUI();
			}
		});
	}

	public void actionPerformed (ActionEvent ae) {
		if ("insert".equals(ae.getActionCommand())) {
			JournalEO.insertRow(tf[0].getText(), tf[1].getText(), tf[2]
					.getText(), tf[3].getText(), tf[4].getText());
		} else if ("exit".equals(ae.getActionCommand())) {
			DBConnection.closeConnection();
			System.exit(0);
		}
	}
}
