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

	private DrawController drawController; // 컨트롤러 클래스를 이용하기 위해 선언
	
	private JPanel menuPanel, optionPanel, messagePanel; // 하단의 세가지 패널
	private JLabel lblMode, lblColor, lblSize, lblCount; // 메세지 영역을 구성하는 라벨들
	private JButton[] btnMenuArray; // 메뉴 패널에 들어가는 버튼
	private JTextField txtSize; // 텍스트 사이즈를 입력할 수 있는 필드
	private JButton btnColorChooser; // 색상을 선택할 수 있는 버튼
	private JCheckBox chkFill; // 체크를 위한 도구
	
	public SimplePainterView() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(820, 830)); // 전체 크기는 820, 830 
		setLayout(null); // 원하는 대로 배치하기 위해서
		
		drawController = new DrawController(this); // 현재의 클래스를 인자로 하여 컨트롤러 클래스를 생성
		drawController.setBounds(10, 10, 800, 600); //그림을 그리는 구간 설정
		drawController.setBorder(BorderFactory.createTitledBorder("DRAWING")); // 보더 타이틀 네임 설정
		add(drawController);
		
		menuPanel = new JPanel();
		menuPanel.setBounds(10, 610, 300, 200); // 메뉴버튼이 들어갈 패널 크기 설정
		menuPanel.setBackground(Color.white);
		menuPanel.setBorder(BorderFactory.createTitledBorder("MENU"));
		menuPanel.setLayout(new GridLayout(2,4)); // 배치는 2행 3열
		add(menuPanel);
		
		optionPanel = new JPanel();
		optionPanel.setBounds(310, 610, 200, 200); // 옵션이 들어갈 패널 크기 설정
		optionPanel.setBackground(Color.white);
		optionPanel.setBorder(BorderFactory.createTitledBorder("OPTION"));
		optionPanel.setLayout(new GridLayout(3,1)); // 배치는 3행 1열
		add(optionPanel);
		
		messagePanel = new JPanel();
		messagePanel.setBounds(510, 610, 300, 200); // 메세지를 출력할 패널의 크기 설정
		messagePanel.setBackground(Color.white);
		messagePanel.setBorder(BorderFactory.createTitledBorder("MESSAGE"));
		messagePanel.setLayout(new GridLayout(4,1)); // 배치는 4행 1열
		add(messagePanel);
	
		// 메뉴 요소들
		btnMenuArray = new JButton[8];
		for (int i=0; i<8; i++) {
			btnMenuArray[i] = new JButton(Constants.MENU[i]); // constants 클래스를 이용하여 버튼이름 설정
			btnMenuArray[i].setBackground(Constants.HOVERING[0]); // constants 클래스를 이용하여 호버효과 설정
			btnMenuArray[i].setForeground(Constants.HOVERING[1]); // constants 클래스를 이용하여 호버효과 설정
			btnMenuArray[i].addMouseListener(new HoveringListener()); // 호버리스닝  생성 및 연결
			btnMenuArray[i].addActionListener(new MenuListener()); // 메큐리스너 생성 및 연결
			menuPanel.add(btnMenuArray[i]);
		}
		
		// 옵션 패널요소들
		btnColorChooser = new JButton("COLOR CHOOSER");
		btnColorChooser.setBackground(Constants.HOVERING[0]); // constants 클래스를 이용하여 호버효과 설정
		btnColorChooser.setForeground(Constants.HOVERING[1]); // constants 클래스를 이용하여 호버효과 설정
		btnColorChooser.addMouseListener(new HoveringListener()); // 호버리스닝  생성 및 연결
		btnColorChooser.addActionListener(new MenuListener()); // 메큐리스너 생성 및 연결
		optionPanel.add(btnColorChooser);
		
		txtSize = new JTextField();
		txtSize.setFont(new Font("Verdana", Font.BOLD, 20));
		txtSize.setVisible(false);
		txtSize.addKeyListener(new TextSizeListener()); // 키 리스너를 연결하여 사이즈 입력 시 이벤트 발생하도록 구현
		optionPanel.add(txtSize);
		
		chkFill = new JCheckBox("FILL");
		chkFill.setBackground(Color.white);
		chkFill.setFont(new Font("Verdana", Font.BOLD, 20));
		chkFill.setVisible(false);
		optionPanel.add(chkFill);
		
		// 메세지 영역 요소들
		lblMode = new JLabel("Mode : None"); // 선택된 모드를 표시하는 라벨
		lblMode.setFont(new Font("Verdana", Font.BOLD, 20));
		lblMode.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		messagePanel.add(lblMode);
		
		lblColor = new JLabel("Color"); // 선택된 색을 표시하는 라벨
		lblColor.setFont(new Font("Verdana", Font.BOLD, 20));
		lblColor.setHorizontalAlignment(JLabel.CENTER);
		messagePanel.add(lblColor);
		
		lblSize = new JLabel("Size : None"); // 사이즈를 표시하는 라벨
		lblSize.setFont(new Font("Verdana", Font.BOLD, 20));
		lblSize.setHorizontalAlignment(JLabel.CENTER);
		messagePanel.add(lblSize);
		
		lblCount = new JLabel("Count : 0"); // 좌표를 나타내는 라벨
		lblCount.setFont(new Font("Verdana", Font.BOLD, 20));
		lblCount.setHorizontalAlignment(JLabel.CENTER);
		messagePanel.add(lblCount);
		
	} // construct
	
	public void setTxtSize(int size) { txtSize.setText(Integer.toString(size)); } // 텍스트 사이즈의 크기를 설정
	public int getTxtSize() { return Integer.parseInt(txtSize.getText()); } // 텍스트 사이즈의 크기 가져오기
	public void setCount(int cnt) { lblCount.setText("Count : " + cnt); } // 그려진 도형의 개수를 메세지 영역의 출력
	
	public boolean getChkFill() { return chkFill.isSelected(); } // 체크 유무의 정보를 가져오기
	
	private class HoveringListener implements MouseListener {

		// 호버 효과를 주기 위한 리스너
		@Override
		public void mouseEntered(MouseEvent e) {
			JButton btnMenu = (JButton) e.getSource(); // 마우스가 들어간 영역의 버튼메뉴 정보 저장
			btnMenu.setBackground(Constants.HOVERING[2]); // constants 클래스를 이용하여 호버효과 설정
			btnMenu.setForeground(Constants.HOVERING[3]); // constants 클래스를 이용하여 호버효과 설정
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JButton btnMenu = (JButton) e.getSource(); // 마우스가 들어왔다 나가는 영역의 버튼메뉴 정보 저장
			btnMenu.setBackground(Constants.HOVERING[0]); // constants 클래스를 이용하여 호버효과 설정
			btnMenu.setForeground(Constants.HOVERING[1]); // constants 클래스를 이용하여 호버효과 설정
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
				Color c =JColorChooser.showDialog(btnColorChooser, "COLOR CHOOSER", Color.black); // 색상 선택
				drawController.setSelectedColor(c); // 콘틀로러 클래스에 선택된 색상 정보 전달
				lblColor.setForeground(c); // 선택된 색상을 글자 색으로 표시
			}
			
			for (int i=0; i<8; i++) {
				if (obj == btnMenuArray[i]) {
					
					drawController.setDrawMode(i); // 콘트롤러 클래스에 선택된 모드 정보 전달				
					lblMode.setText("Mode : " + Constants.MENU[i]);
					lblSize.setText("Size : " + txtSize.getText()); // 선택한 모드에 해당하는 텍스트 필드 값 메세지 화면에 출력
					
					if (i==Constants.RECT || i==Constants.OVAL) chkFill.setVisible(true);
					break;
				}
			}
			
		}
		
	}
	
	private class TextSizeListener implements KeyListener {

		// 텍스트 사이즈 필드에 입력 시 해당 사이즈 값 메세지 화면에 출력
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
