package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import Back.Matrix;
import Back.Rangos;
import Back.SlavoskyRange;

public class Panel extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JFrame window ;
	private JPanel centralPanel ;
	private JPanel tablaPanel;
	private JPanel infoPanel ;
	private JPanel buttonPanel;
	private JPanel sliderPanel;
	private TablaMatriz table;
	private JTextArea rangoText;
	private JTextField porcentajeText;
	private JLabel  labelRango;
	private JSlider slider;
	private SlavoskyRange slavosky;
	private JScrollPane rangePanel;
	
    public Panel() {
    	initGUI();
    }
	
	private void initGUI() {
		this.window = new JFrame("Tabla Rangos");
	    this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.window.setBounds(0,0,900,600);
	    this.window.setLayout(new BorderLayout());

	    this.centralPanel = new JPanel(new GridLayout(3,1));
	    this.tablaPanel = new JPanel(new GridLayout(13, 13, 2, 2));
	    this.infoPanel = new JPanel(new GridLayout(2,1));
	    this.buttonPanel = new JPanel(new GridLayout(6, 1));
	    this.sliderPanel = new JPanel(new FlowLayout());

	    this.table = new TablaMatriz(new Matrix());
	    this.rangoText = new JTextArea();
	    this.porcentajeText = new JTextField(3);
	    this.labelRango = new JLabel("RANGE");
	    this.labelRango.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
	    this.slider = new JSlider(0, 100, 50);
	    this.slavosky = new SlavoskyRange();
	    this.buttonPanel.setBackground(Color.WHITE);
	    this.infoPanel.setBackground(Color.WHITE);
	    this.tablaPanel.setBackground(Color.WHITE);
	    this.sliderPanel.setBackground(Color.WHITE);
	    
	    this.rangoText.setRows(1);
	    this.rangoText.setColumns(16);
	    this.rangoText.setLineWrap(true);
	    this.rangePanel = new JScrollPane(this.rangoText);
	    
	    this.slider.setMajorTickSpacing(10);
	    this.slider.setMinorTickSpacing(1);
	    this.slider.setPaintLabels(true);
	    this.slider.addChangeListener((ChangeListener) new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	porcentajeText.setText(slider.getValue() + "%");
                table.updateSlider(slavosky.getRange(), slavosky.calculatePercentage(slider.getValue()));
	        }
	    });
	    this.slider.addMouseListener(new MouseInputListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {
				rangoText.setText(Rangos.calculaIntervalos(table.getRange()));
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseMoved(MouseEvent e) {}
	    	
	    });
	    
	    window.add(tablaPanel, BorderLayout.CENTER);
	    centralPanel.add(infoPanel);
	    centralPanel.add(buttonPanel);
	    
	    
	    for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 13; j++) {
				tablaPanel.add((Component) table.getValueAt(i,j));
			}
	    }
        infoPanel.add(this.labelRango);
        infoPanel.add(this.rangePanel);
        
        JButton buttonOkRange = new JButton("OK");
        buttonOkRange.addActionListener((ActionListener) new ActionListener() {
    		@Override
			 public void actionPerformed(ActionEvent e) {
				String intervalo = Rangos.calculaRangos(rangoText.getText().split("\\n")[0]);
				table.pintar(intervalo);
				rangoText.setText(Rangos.calculaIntervalos(table.getRange())); 
			}
		});
        
        JButton buttonAll = new JButton("All");
        buttonAll.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				table.all();
				rangoText.setText(Rangos.calculaIntervalos(table.getRange()));
			}
        });
        
        JButton buttonSuiteds = new JButton("Any Suited");
        buttonSuiteds.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				table.suiteds();
				rangoText.setText(Rangos.calculaIntervalos(table.getRange()));
			}
        });
        
        // Any combination with A-T 
        JButton buttonBroadway = new JButton("Any Broadway");
        buttonBroadway.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				table.broadWays();
				rangoText.setText(Rangos.calculaIntervalos(table.getRange()));
			}
        });
        
        JButton buttonPair = new JButton("Any Pair");
        buttonPair.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				table.anyPair();
				rangoText.setText(Rangos.calculaIntervalos(table.getRange()));
			}
        });

        JButton buttonClear = new JButton("Clear");
        buttonClear.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				table.clear();
				rangoText.setText(Rangos.calculaIntervalos(table.getRange()));
			}
		});
        
        JButton buttonAplly = new JButton("Aplly");
        buttonAplly.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				rangoText.setText(Rangos.calculaIntervalos(table.getRange()));
			}
		});
        
        porcentajeText.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent arg0) {
	            	String h = porcentajeText.getText().substring(0,porcentajeText.getText().length()-1);
	                int valor = Integer.parseInt(h); 
	                table.updateSlider(slavosky.getRange(), slavosky.calculatePercentage(valor));
	                slider.setValue(valor);
	                rangoText.setText(Rangos.calculaIntervalos(table.getRange()));
	            }
        });
        
        JButton openComboPanel = new JButton("Combos");
        openComboPanel.addActionListener((ActionListener) new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
				//window.setVisible(false);
				new comboPanel();
				
			}
        });
        JPanel lowPanel = new JPanel(new GridLayout(2,1));
        lowPanel.setBackground(Color.WHITE);
        centralPanel.add(lowPanel);
        buttonPanel.add(buttonOkRange);
        buttonPanel.add(buttonAll);
        buttonPanel.add(buttonSuiteds);
        buttonPanel.add(buttonBroadway);
        buttonPanel.add(buttonPair);
        buttonPanel.add(buttonAplly);
        lowPanel.add(buttonClear);
        lowPanel.add(openComboPanel);
        slider.setPreferredSize(new Dimension(500, 35));
        sliderPanel.add(slider);
        sliderPanel.add(porcentajeText);
        window.add(sliderPanel, BorderLayout.SOUTH);
	    window.add(centralPanel, BorderLayout.EAST);
	    window.setVisible(true);
	}
}
