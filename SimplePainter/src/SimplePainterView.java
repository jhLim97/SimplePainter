import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;

import javax.swing.*;


public class SimplePainterView extends JPanel {

	private DrawController drawController; // ��Ʈ�ѷ� Ŭ������ �̿��ϱ� ���� ����
	
	private JPanel menuPanel, optionPanel, messagePanel; // �ϴ��� ������ �г�
	private JLabel lblMode, lblColor, lblSize, lblCount; // �޼��� ������ �����ϴ� �󺧵�
	private JButton[] btnMenuArray; // �޴� �гο� ���� ��ư
	private JTextField txtSize; // �ؽ�Ʈ ����� �Է��� �� �ִ� �ʵ�
	private JButton btnColorChooser; // ������ ������ �� �ִ� ��ư
	private JCheckBox chkFill; // üũ�� ���� ����
	
	public SimplePainterView() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(820, 830)); // ��ü ũ��� 820, 830 
		setLayout(null); // ���ϴ� ��� ��ġ�ϱ� ���ؼ�
		
		drawController = new DrawController(this); // ������ Ŭ������ ���ڷ� �Ͽ� ��Ʈ�ѷ� Ŭ������ ����
		drawController.setBounds(10, 10, 800, 600); //�׸��� �׸��� ���� ����
		drawController.setBorder(BorderFactory.createTitledBorder("DRAWING")); // ���� Ÿ��Ʋ ���� ����
		add(drawController);
		
		menuPanel = new JPanel();
		menuPanel.setBounds(10, 610, 300, 200); // �޴���ư�� �� �г� ũ�� ����
		menuPanel.setBackground(Color.white);
		menuPanel.setBorder(BorderFactory.createTitledBorder("MENU"));
		menuPanel.setLayout(new GridLayout(2,4)); // ��ġ�� 2�� 3��
		add(menuPanel);
		
		optionPanel = new JPanel();
		optionPanel.setBounds(310, 610, 200, 200); // �ɼ��� �� �г� ũ�� ����
		optionPanel.setBackground(Color.white);
		optionPanel.setBorder(BorderFactory.createTitledBorder("OPTION"));
		optionPanel.setLayout(new GridLayout(3,1)); // ��ġ�� 3�� 1��
		add(optionPanel);
		
		messagePanel = new JPanel();
		messagePanel.setBounds(510, 610, 300, 200); // �޼����� ����� �г��� ũ�� ����
		messagePanel.setBackground(Color.white);
		messagePanel.setBorder(BorderFactory.createTitledBorder("MESSAGE"));
		messagePanel.setLayout(new GridLayout(4,1)); // ��ġ�� 4�� 1��
		add(messagePanel);
	
		// �޴� ��ҵ�
		btnMenuArray = new JButton[8];
		for (int i=0; i<8; i++) {
			btnMenuArray[i] = new JButton(Constants.MENU[i]); // constants Ŭ������ �̿��Ͽ� ��ư�̸� ����
			btnMenuArray[i].setBackground(Constants.HOVERING[0]); // constants Ŭ������ �̿��Ͽ� ȣ��ȿ�� ����
			btnMenuArray[i].setForeground(Constants.HOVERING[1]); // constants Ŭ������ �̿��Ͽ� ȣ��ȿ�� ����
			btnMenuArray[i].addMouseListener(new HoveringListener()); // ȣ��������  ���� �� ����
			btnMenuArray[i].addActionListener(new MenuListener()); // ��ť������ ���� �� ����
			menuPanel.add(btnMenuArray[i]);
		}
		
		// �ɼ� �гο�ҵ�
		btnColorChooser = new JButton("COLOR CHOOSER");
		btnColorChooser.setBackground(Constants.HOVERING[0]); // constants Ŭ������ �̿��Ͽ� ȣ��ȿ�� ����
		btnColorChooser.setForeground(Constants.HOVERING[1]); // constants Ŭ������ �̿��Ͽ� ȣ��ȿ�� ����
		btnColorChooser.addMouseListener(new HoveringListener()); // ȣ��������  ���� �� ����
		btnColorChooser.addActionListener(new MenuListener()); // ��ť������ ���� �� ����
		optionPanel.add(btnColorChooser);
		
		txtSize = new JTextField();
		txtSize.setFont(new Font("Verdana", Font.BOLD, 20));
		txtSize.setVisible(false);
		txtSize.addKeyListener(new TextSizeListener()); // Ű �����ʸ� �����Ͽ� ������ �Է� �� �̺�Ʈ �߻��ϵ��� ����
		optionPanel.add(txtSize);
		
		chkFill = new JCheckBox("FILL");
		chkFill.setBackground(Color.white);
		chkFill.setFont(new Font("Verdana", Font.BOLD, 20));
		chkFill.setVisible(false);
		optionPanel.add(chkFill);
		
		// �޼��� ���� ��ҵ�
		lblMode = new JLabel("Mode : None"); // ���õ� ��带 ǥ���ϴ� ��
		lblMode.setFont(new Font("Verdana", Font.BOLD, 20));
		lblMode.setHorizontalAlignment(JLabel.CENTER); // ��� ����
		messagePanel.add(lblMode);
		
		lblColor = new JLabel("Color"); // ���õ� ���� ǥ���ϴ� ��
		lblColor.setFont(new Font("Verdana", Font.BOLD, 20));
		lblColor.setHorizontalAlignment(JLabel.CENTER);
		messagePanel.add(lblColor);
		
		lblSize = new JLabel("Size : None"); // ����� ǥ���ϴ� ��
		lblSize.setFont(new Font("Verdana", Font.BOLD, 20));
		lblSize.setHorizontalAlignment(JLabel.CENTER);
		messagePanel.add(lblSize);
		
		lblCount = new JLabel("Count : 0"); // ��ǥ�� ��Ÿ���� ��
		lblCount.setFont(new Font("Verdana", Font.BOLD, 20));
		lblCount.setHorizontalAlignment(JLabel.CENTER);
		messagePanel.add(lblCount);
		
	} // construct
	
	public void setTxtSize(int size) { txtSize.setText(Integer.toString(size)); } // �ؽ�Ʈ �������� ũ�⸦ ����
	public int getTxtSize() { return Integer.parseInt(txtSize.getText()); } // �ؽ�Ʈ �������� ũ�� ��������
	public void setCount(int cnt) { lblCount.setText("Count : " + cnt); } // �׷��� ������ ������ �޼��� ������ ���
	
	public boolean getChkFill() { return chkFill.isSelected(); } // üũ ������ ������ ��������
	
	private class HoveringListener implements MouseListener {

		// ȣ�� ȿ���� �ֱ� ���� ������
		@Override
		public void mouseEntered(MouseEvent e) {
			JButton btnMenu = (JButton) e.getSource(); // ���콺�� �� ������ ��ư�޴� ���� ����
			btnMenu.setBackground(Constants.HOVERING[2]); // constants Ŭ������ �̿��Ͽ� ȣ��ȿ�� ����
			btnMenu.setForeground(Constants.HOVERING[3]); // constants Ŭ������ �̿��Ͽ� ȣ��ȿ�� ����
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JButton btnMenu = (JButton) e.getSource(); // ���콺�� ���Դ� ������ ������ ��ư�޴� ���� ����
			btnMenu.setBackground(Constants.HOVERING[0]); // constants Ŭ������ �̿��Ͽ� ȣ��ȿ�� ����
			btnMenu.setForeground(Constants.HOVERING[1]); // constants Ŭ������ �̿��Ͽ� ȣ��ȿ�� ����
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}	
		
	}
	
	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			
			txtSize.setVisible(true);
			chkFill.setVisible(false);
			
			if (obj == btnColorChooser) {
				Color c =JColorChooser.showDialog(btnColorChooser, "COLOR CHOOSER", Color.black); // ���� ����
				drawController.setSelectedColor(c); // ��Ʋ�η� Ŭ������ ���õ� ���� ���� ����
				lblColor.setForeground(c); // ���õ� ������ ���� ������ ǥ��
			}
			
			for (int i=0; i<8; i++) {
				if (obj == btnMenuArray[i]) {
					
					drawController.setDrawMode(i); // ��Ʈ�ѷ� Ŭ������ ���õ� ��� ���� ����				
					lblMode.setText("Mode : " + Constants.MENU[i]);
					lblSize.setText("Size : " + txtSize.getText()); // ������ ��忡 �ش��ϴ� �ؽ�Ʈ �ʵ� �� �޼��� ȭ�鿡 ���
					
					if (i==Constants.RECT || i==Constants.OVAL) chkFill.setVisible(true);
					break;
				}
			}
			
		}
		
	}
	
	private class TextSizeListener implements KeyListener {

		// �ؽ�Ʈ ������ �ʵ忡 �Է� �� �ش� ������ �� �޼��� ȭ�鿡 ���
		@Override
		public void keyReleased(KeyEvent e) {
			lblSize.setText("Size : " + txtSize.getText());
		}
		
		@Override
		public void keyPressed(KeyEvent e) {}	
		@Override
		public void keyTyped(KeyEvent e) {}
	
		
	}
	
	
}
