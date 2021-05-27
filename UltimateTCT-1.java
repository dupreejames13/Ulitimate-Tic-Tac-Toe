/*/
 * James Dupree, Carson Clymore, Dylan Grafius
 * November 28th, 2019
 * CSC 331
 */

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UltimateTCT extends JFrame {
	
	private ArrayList<JButton> buttonList = new ArrayList<JButton>(); // button list to keep track of buttons 
	private ArrayList<JButton> buttonListInOrderOfPressed = new ArrayList<JButton>();
	private JPanel contentPane;
	private JButton btnRestart;
	private JButton btnUndo;
	private JPanel panel_1;
	private JLabel lblNowPlaying;
	private JLabel lblManageGame;
	private JLabel lblTotalOf;
	private JTextField textField;
	private JLabel lblAverageOf;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblWin;
	private JButton btnPlayingStats;
	private String currentPlayer = "X";
	private JButton btnPlayerX = new JButton("Player X");
	private JButton btnPlayerO = new JButton("Player O");
	private int moves;
	private int games = 0;
	private JButton[][]twoDButtonList = new JButton[9][9];
	private JPanel[]JPanelList = new JPanel[9];
	private String[]PanelList = new String[9];
	private int totalMoves;
	private float xWon;
	private float oWon;
	
	public UltimateTCT() {
		
		// setting up the board and the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 825, 750); 						// dimension is specific, maximizing the window does in fact mess up the board

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JPanel midPanel = new JPanel();
		midPanel.setBackground(Color.gray);
		contentPane.add(midPanel, BorderLayout.CENTER); 
		
		lblNowPlaying = new JLabel("Now Playing:");
		panel.add(lblNowPlaying);
		
		btnPlayerX.setBackground(Color.blue);
		panel.add(btnPlayerX);
		btnPlayerX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentPlayer = "X";
				btnPlayerX.setBackground(Color.blue);
				btnPlayerO.setBackground(Color.white);
				
			}
		});
		
		// the main button listener that listens to 81 buttons and contains the rules
		class buttonListener implements ActionListener{
	        public void actionPerformed(ActionEvent e){
				Border red = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
				Border yellow = BorderFactory.createLineBorder(Color.yellow, 4);
	        	if(buttonListInOrderOfPressed.size()==0) {
    				if(currentPlayer == "O") {
    					((JButton) e.getSource()).setForeground(Color.RED);
    				}
    				if(currentPlayer == "X") {
    					((JButton) e.getSource()).setForeground(Color.blue);
    				}
    				((JButton) e.getSource()).setText(currentPlayer);
    				buttonListInOrderOfPressed.add((JButton) e.getSource());
    				tie((JButton) e.getSource());
    				hasWinner((JButton) e.getSource());
    				togglePlayer();
	    			for(int q = 0; q< 9; q++) {
	    				for(int t = 0; t< 9; t++) {
	    					if(twoDButtonList[q][t]==e.getSource()) {
	    	    				for(int s = 0; s< 9; s++) {
		    						JPanelList[s].setBorder(red);
	    	    				}
	    						JPanelList[t].setBorder(yellow);
	    					}
	    				}
	    			}
	        	}
	        	else {
	    			int yValue = 0;
		        	int xValue2 = 0;
		        	int yValue2 = 0;
	    			for(int x = 0; x< 9; x++) {
	    				for(int y = 0; y< 9; y++) {
	    					if(twoDButtonList[x][y]==buttonListInOrderOfPressed.get(buttonListInOrderOfPressed.size()-1)) {
	    						yValue = y;
	    					}
	    				}
	    			}
	    			for(int q = 0; q< 9; q++) {
	    				for(int t = 0; t< 9; t++) {
	    					if(twoDButtonList[q][t]==e.getSource()) {
	    						xValue2 = q;
	    						yValue2 = t;
	    					}
	    				}
	    			}
	    			
	    			// rule to where you can choose any button after referencing an already won or tied board
	    			if(PanelList[yValue]!="t") {
		    			if(((JButton) e.getSource()).getText().equals("")){
		    				if(currentPlayer == "O") {
		    					((JButton) e.getSource()).setForeground(Color.RED);
		    				}
		    				if(currentPlayer == "X") {
		    					((JButton) e.getSource()).setForeground(Color.blue);
		    				}
		    				((JButton) e.getSource()).setText(currentPlayer);
		    				buttonListInOrderOfPressed.add((JButton) e.getSource());
		    				tie((JButton) e.getSource());
		    				hasWinner((JButton) e.getSource());
		    				togglePlayer();
    	    				for(int s = 0; s< 9; s++) {
	    						JPanelList[s].setBorder(red);
    	    				}
    						JPanelList[yValue2].setBorder(yellow);
		    			}
	    			}
	    			
	    			// rule to where you have to go to the panel the previous button choice referenced
	    			if(PanelList[yValue]=="t") {
						if(xValue2==yValue) {
			    			if(((JButton) e.getSource()).getText().equals("")){
			    				if(currentPlayer == "O") {
			    					((JButton) e.getSource()).setForeground(Color.RED);
			    				}
			    				if(currentPlayer == "X") {
			    					((JButton) e.getSource()).setForeground(Color.blue);
			    				}
			    				((JButton) e.getSource()).setText(currentPlayer);
			    				buttonListInOrderOfPressed.add((JButton) e.getSource());
			    				tie((JButton) e.getSource());
			    				hasWinner((JButton) e.getSource());
			    				togglePlayer();
			    				if(PanelList[yValue2]=="t") {
		    	    				for(int s = 0; s< 9; s++) {
			    						JPanelList[s].setBorder(red);
		    	    				}
		    						JPanelList[yValue2].setBorder(yellow);
			    				}
			    				else {
		    	    				for(int s = 0; s< 9; s++) {
			    						JPanelList[s].setBorder(yellow);
		    	    				}
			    				}
			    			}
						}
						if(xValue2!=yValue) {
							JOptionPane.showMessageDialog(null, "The placement is not within the rules.. Please Try again.");
						}
	    			}
	        	}
	        }
		}
		
		btnPlayerO.setBackground(Color.red);
		panel.add(btnPlayerO);
		btnPlayerO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentPlayer = "O";
				btnPlayerO.setBackground(Color.red);
				btnPlayerX.setBackground(Color.white);
				
			}
		});
		
		lblManageGame = new JLabel("Manage Game:");
		panel.add(lblManageGame);
		
		btnRestart = new JButton("Restart");
		btnRestart.setBackground(Color.white);
		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartGame();
			}
		});
		panel.add(btnRestart);
		
		btnUndo = new JButton("Undo");						// more setting up board
		btnUndo.setBackground(Color.white);
		btnUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				undoMove();
			}
		});	
		panel.add(btnUndo);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		btnPlayingStats = new JButton("Playing Stats");
		btnPlayingStats.setBackground(Color.white);
		btnPlayingStats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayingStats();
			}
		});	
		panel_1.add(btnPlayingStats);
		
		lblWin = new JLabel("Win %:");
		panel_1.add(lblWin);
		
		textField_2 = new JTextField();
		panel_1.add(textField_2);					//setting up panels and buttons
		textField_2.setColumns(10);
		
		lblTotalOf = new JLabel("Total # of games:");
		panel_1.add(lblTotalOf);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		lblAverageOf = new JLabel("Average # of moves per win:");
		panel_1.add(lblAverageOf);
		
		textField_1 = new JTextField();
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		for (int i=0; i<9; i++) {
			PanelList[i] = "t";
			Border k = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
    		JPanel pan = new JPanel();
        	pan.setPreferredSize(new Dimension(200,200));
    		midPanel.add(pan);						// looping through and making all of the buttons and the panels
    		pan.setBorder(k);
    		JPanelList[i]=pan;
    	    for (int m=0; m<9; m++) {
   	        	pan.setLayout(new GridLayout(3, 3));
	        	JButton btn = new JButton();
	        	btn.setBackground(Color.WHITE);
	        	pan.add(btn);
	        	buttonList.add(btn);
	        	twoDButtonList[i][m]=btn;
	        	btn.setFont(new Font("Arial",Font.PLAIN,35));
	        	btn.addActionListener(new buttonListener());
    	    }
		}
	}

	// restart game function
	private void restartGame() {
		currentPlayer = "X";
		moves = 0;
		textField_1.setText(null);
		textField_2.setText(null);
		textField.setText(null);
		buttonListInOrderOfPressed = new ArrayList<JButton>();
		Border red = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
		for(int s = 0; s< 9; s++) {
			JPanelList[s].setBorder(red);
			PanelList[s] = "t";
		}
        for (JButton s : buttonList) {
            s.setText("");
        }
	}

	// does not let you undo from an already won or tied board
	private void undoMove() {
        int n = buttonListInOrderOfPressed.size();
        if(n!=1 && n!= 0){
			for(int y = 0; y< 9; y++) {
				for(int x = 0; x< 9; x++) {
					if(twoDButtonList[x][y]==buttonListInOrderOfPressed.get(buttonListInOrderOfPressed.size()-1)) {
						if(twoDButtonList[x][8].getText()=="C") {
							JOptionPane.showMessageDialog(null, "You can not undo a board that has already been won or tied.");
							return;
						}
						if(currentPlayer == "O") {
							if(twoDButtonList[x][8].getText()=="X") {
								if(twoDButtonList[x][7].getText()=="X") {
									if(twoDButtonList[x][6].getText()=="X") {
										if(twoDButtonList[x][5].getText()=="X") {
											if(twoDButtonList[x][4].getText()=="X") {
												if(twoDButtonList[x][3].getText()=="X") {
													if(twoDButtonList[x][2].getText()=="X") {
														if(twoDButtonList[x][1].getText()=="X") {
															if(twoDButtonList[x][0].getText()=="X") {
																JOptionPane.showMessageDialog(null, "You can not undo a board that has already been won or tied.");
																return;
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if(currentPlayer == "X") {
							if(twoDButtonList[x][8].getText()=="O") {
								if(twoDButtonList[x][7].getText()=="O") {
									if(twoDButtonList[x][6].getText()=="O") {
										if(twoDButtonList[x][5].getText()=="O") {
											if(twoDButtonList[x][4].getText()=="O") {
												if(twoDButtonList[x][3].getText()=="O") {
													if(twoDButtonList[x][2].getText()=="O") {
														if(twoDButtonList[x][1].getText()=="O") {
															if(twoDButtonList[x][0].getText()=="O") {
																JOptionPane.showMessageDialog(null, "You can not undo a board that has already been won or tied.");
																return;
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		Border red = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        if(n!=1&&n!=0) {
			Border yellow = BorderFactory.createLineBorder(Color.yellow, 4);
            int last = n - 1;
            buttonListInOrderOfPressed.get(last).setText("");
			for(int q = 0; q< 9; q++) {
				for(int t = 0; t< 9; t++) {
					if(twoDButtonList[q][t]== buttonListInOrderOfPressed.get(last-1)) { // resets based on last move after undo
			            buttonListInOrderOfPressed.remove(last);
	    				for(int s = 0; s< 9; s++) {
    						JPanelList[s].setBorder(red);
	    				}
						JPanelList[t].setBorder(yellow);
					}
				}
			}
        }
        if(n==1) {
        	int last = n - 1;
            buttonListInOrderOfPressed.get(last).setText("");
            buttonListInOrderOfPressed.remove(last);
			for(int s = 0; s< 9; s++) {
				JPanelList[s].setBorder(red);
			}
        }
        togglePlayer();
	}
	
	// switches players
	private void togglePlayer() {
		if(currentPlayer.equals("X")) {					
			moves++;
			currentPlayer = "O";
			btnPlayerX.setBackground(Color.WHITE);
			btnPlayerO.setBackground(Color.red);
		}
		else {
			moves++;
			currentPlayer = "X";
			btnPlayerO.setBackground(Color.WHITE);
			btnPlayerX.setBackground(Color.blue);
		}
	}

	// win method that checks overall win and individual board wins
	private void hasWinner(JButton btn) {
		for(int x = 0; x< 9; x++) {
			for(int y = 0; y< 9; y++) {
				if(twoDButtonList[x][y]==btn) {
					if(twoDButtonList[x][0].getText()==currentPlayer && twoDButtonList[x][1].getText()==currentPlayer && twoDButtonList[x][2].getText()==currentPlayer) {
						for(int z = 0; z< 9; z++) {	
							if(currentPlayer == "X") {
								twoDButtonList[x][z].setForeground(Color.blue);
							}
							if(currentPlayer == "O") {
								twoDButtonList[x][z].setForeground(Color.red);
							}
							twoDButtonList[x][z].setText(currentPlayer);
						}
						PanelList[x]=currentPlayer;
					}
					if(twoDButtonList[x][3].getText()==currentPlayer && twoDButtonList[x][4].getText()==currentPlayer && twoDButtonList[x][5].getText()==currentPlayer) {
						for(int z = 0; z< 9; z++) {
							if(currentPlayer == "X") {
								twoDButtonList[x][z].setForeground(Color.blue);
							}
							if(currentPlayer == "O") {
								twoDButtonList[x][z].setForeground(Color.red);
							}
							twoDButtonList[x][z].setText(currentPlayer);
						}
						PanelList[x]=currentPlayer;
					}
					if(twoDButtonList[x][6].getText()==currentPlayer && twoDButtonList[x][7].getText()==currentPlayer && twoDButtonList[x][8].getText()==currentPlayer) {
						for(int z = 0; z< 9; z++) {
							if(currentPlayer == "X") {
								twoDButtonList[x][z].setForeground(Color.blue);
							}
							if(currentPlayer == "O") {
								twoDButtonList[x][z].setForeground(Color.red);
							}
							twoDButtonList[x][z].setText(currentPlayer);
						}
						PanelList[x]=currentPlayer;
					}
					if(twoDButtonList[x][0].getText()==currentPlayer && twoDButtonList[x][4].getText()==currentPlayer && twoDButtonList[x][8].getText()==currentPlayer) {
						for(int z = 0; z< 9; z++) {
							if(currentPlayer == "X") {
								twoDButtonList[x][z].setForeground(Color.blue);
							}
							if(currentPlayer == "O") {
								twoDButtonList[x][z].setForeground(Color.red);
							}
							twoDButtonList[x][z].setText(currentPlayer);
						}
						PanelList[x]=currentPlayer;
					}
					if(twoDButtonList[x][2].getText()==currentPlayer && twoDButtonList[x][4].getText()==currentPlayer && twoDButtonList[x][6].getText()==currentPlayer) {
						for(int z = 0; z< 9; z++) {
							if(currentPlayer == "X") {
								twoDButtonList[x][z].setForeground(Color.blue);
							}
							if(currentPlayer == "O") {
								twoDButtonList[x][z].setForeground(Color.red);
							}
							twoDButtonList[x][z].setText(currentPlayer);
						}
						PanelList[x]=currentPlayer;
					}
					if(twoDButtonList[x][0].getText()==currentPlayer && twoDButtonList[x][3].getText()==currentPlayer && twoDButtonList[x][6].getText()==currentPlayer) {
						for(int z = 0; z< 9; z++) {
							if(currentPlayer == "X") {
								twoDButtonList[x][z].setForeground(Color.blue);
							}
							if(currentPlayer == "O") {
								twoDButtonList[x][z].setForeground(Color.red);
							}
							twoDButtonList[x][z].setText(currentPlayer);
						}
						PanelList[x]=currentPlayer;
					}
					if(twoDButtonList[x][1].getText()==currentPlayer && twoDButtonList[x][4].getText()==currentPlayer && twoDButtonList[x][7].getText()==currentPlayer) {
						for(int z = 0; z< 9; z++) {
							if(currentPlayer == "X") {
								twoDButtonList[x][z].setForeground(Color.blue);
							}
							if(currentPlayer == "O") {
								twoDButtonList[x][z].setForeground(Color.red);
							}
							twoDButtonList[x][z].setText(currentPlayer);
						}
						PanelList[x]=currentPlayer;
					}
					if(twoDButtonList[x][2].getText()==currentPlayer && twoDButtonList[x][5].getText()==currentPlayer && twoDButtonList[x][8].getText()==currentPlayer) {
						for(int z = 0; z< 9; z++) {
							if(currentPlayer == "X") {
								twoDButtonList[x][z].setForeground(Color.blue);
							}
							if(currentPlayer == "O") {
								twoDButtonList[x][z].setForeground(Color.red);
							}
							twoDButtonList[x][z].setText(currentPlayer);
						}
						PanelList[x]=currentPlayer;
					}
				}
			}
		}
		if(PanelList[0]==currentPlayer && PanelList[1]==currentPlayer && PanelList[2]==currentPlayer) {
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won!");
			games++;
			if(currentPlayer == "X") {
				xWon +=1;
			}
			if(currentPlayer == "O") {
				oWon +=1;
			}
			totalMoves += moves;
			PlayingStats();
			moves =0;
			for(int x = 0; x<9; x++) {
				PanelList[x]= "t";
			}
		}
		if(PanelList[3]==currentPlayer && PanelList[4]==currentPlayer && PanelList[5]==currentPlayer) {
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won!");
			games++;
			if(currentPlayer == "X") {
				xWon +=1;
			}
			if(currentPlayer == "O") {
				oWon +=1;
			}
			totalMoves += moves;
			PlayingStats();
			moves =0;
			for(int x = 0; x<9; x++) {
				PanelList[x]= "t";
			}		
		}
		if(PanelList[6]==currentPlayer && PanelList[7]==currentPlayer && PanelList[8]==currentPlayer) {
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won!");
			games++;
			if(currentPlayer == "X") {
				xWon +=1;
			}
			if(currentPlayer == "O") {
				oWon +=1;
			}
			totalMoves += moves;
			PlayingStats();
			moves =0;
			for(int x = 0; x<9; x++) {
				PanelList[x]= "t";
			}	
		}
		if(PanelList[0]==currentPlayer && PanelList[4]==currentPlayer && PanelList[8]==currentPlayer) {
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won!");
			games++;
			if(currentPlayer == "X") {
				xWon +=1;
			}
			if(currentPlayer == "O") {
				oWon +=1;
			}
			totalMoves += moves;
			PlayingStats();
			moves =0;
			for(int x = 0; x<9; x++) {
				PanelList[x]= "t";
			}	
		}
		if(PanelList[2]==currentPlayer && PanelList[4]==currentPlayer && PanelList[6]==currentPlayer) {
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won!");
			games++;
			if(currentPlayer == "X") {
				xWon +=1;
			}
			if(currentPlayer == "O") {
				oWon +=1;
			}
			totalMoves += moves;
			PlayingStats();
			moves =0;
			for(int x = 0; x<9; x++) {
				PanelList[x]= "t";
			}	
		}
		if(PanelList[0]==currentPlayer && PanelList[3]==currentPlayer && PanelList[6]==currentPlayer) {
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won!");
			games++;
			if(currentPlayer == "X") {
				xWon +=1;
			}
			if(currentPlayer == "O") {
				oWon +=1;
			}
			totalMoves += moves;
			PlayingStats();
			moves =0;
			for(int x = 0; x<9; x++) {
				PanelList[x]= "t";
			}	
		}
		if(PanelList[1]==currentPlayer && PanelList[4]==currentPlayer && PanelList[7]==currentPlayer) {
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won!");
			games++;
			if(currentPlayer == "X") {
				xWon +=1;
			}
			if(currentPlayer == "O") {
				oWon +=1;
			}
			totalMoves += moves;
			PlayingStats();
			moves =0;
			for(int x = 0; x<9; x++) {
				PanelList[x]= "t";
			}	
		}
		if(PanelList[6]==currentPlayer && PanelList[7]==currentPlayer && PanelList[8]==currentPlayer) {
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " has won!");
			games++;
			if(currentPlayer == "X") {
				xWon +=1;
			}
			if(currentPlayer == "O") {
				oWon +=1;
			}
			totalMoves += moves;
			PlayingStats();
			moves =0;
			for(int x = 0; x<9; x++) {
				PanelList[x]= "t";
			}	
		}
	}
	
	// checks for individual ties and overall tie
	public void tie(JButton btn) {
		for(int x = 0; x< 9; x++) {
			for(int y = 0; y< 9; y++) {
				if(twoDButtonList[x][y]==btn) {
					for(int z = 0; z< 9; z++) {
						if(twoDButtonList[x][z].getText()=="") {
							return;
						}
					}
					for(int t = 0; t< 9; t++) {
						twoDButtonList[x][t].setForeground(Color.black);
						twoDButtonList[x][t].setText("C");
						PanelList[x]="C";
					}
				}
			}
		}
		if(PanelList[0]!="t") {
			if(PanelList[1]!="t") {
				if(PanelList[2]!="t") {
					if(PanelList[3]!="t") {
						if(PanelList[4]!="t") {
							if(PanelList[5]!="t") {
								if(PanelList[6]!="t") {
									if(PanelList[7]!="t") {
										if(PanelList[8]!="t") {
											JOptionPane.showMessageDialog(null, "The game has been tied!");
											games +=1;
											if(xWon == 0) {
												return;
											}
											if(oWon == 0) {
												return;
											}
											xWon-=1; 				// tie function loses both players a games
											oWon-=1;
											PlayingStats();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	// playing statistics method
	private void PlayingStats() {
		String gamesPlayed = null;
		float average = 0;
		if (games==0) {
			textField.setText("0");
			textField_1.setText("No wins yet");
			textField_2.setText("No wins yet");
		}
		else {
			if(currentPlayer == "X") {
				float xWon2 = (xWon / games)*100;
				textField_2.setText(xWon2 + " for player " +currentPlayer);
			}
			if(currentPlayer == "O") {
				float oWon2 = (oWon / games)*100;
				textField_2.setText(oWon2 + " for player " + currentPlayer);
			}
		}
		gamesPlayed = Integer.toString(games);
		if(games!=0) {
			average = ((totalMoves)/2) / games;
			String average2 = Float.toString(average);
			textField_1.setText(average2);
		}
		textField.setText(gamesPlayed);
		togglePlayer();
	}			
}



