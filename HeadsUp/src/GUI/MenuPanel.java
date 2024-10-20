package GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class MenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private PlayerPanel player;
	private BoardPanel board;
	private JButton betButton;
	private JButton callButton;
	private JTextField betText;
	
	public MenuPanel(PlayerPanel plyr, BoardPanel brd){
		this.player = plyr;
		this.board = brd;
		this.init();
	}
	
	private void init() {
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		
		this.betText = new JTextField(8);
		this.betText.setEditable(true);
		this.betText.setBorder(new EmptyBorder(0,0,0,0));
		this.betText.setOpaque(true);
		this.betText.setForeground(Color.BLACK);
		this.betText.setHorizontalAlignment(JTextField.CENTER);
		this.betText.setFont(new Font("Roboto Mono",Font.PLAIN, 18));
		this.add(this.betText);
		
		this.betButton = createBetButton();
		this.add(this.betButton);
		this.callButton = createCallButton();
		this.add(this.callButton);
	}
	
	private JButton createBetButton() {
		JButton button = new JButton("Bet");
		button.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				if(Integer.valueOf(betText.getText())<= player.getChips()) {
					player.setChips(player.getChips() - Integer.valueOf(betText.getText()));
					board.addRoundBet(Integer.valueOf(betText.getText()));
					board.addPot(Integer.valueOf(betText.getText()));
					betText.setText("");
				}
			}
		});
		return button;
	}
	
	private JButton createCallButton() {
		JButton button = new JButton("Call");
		button.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
			}
		});
		return button;
	}
}

	