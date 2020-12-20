import javax.swing.JFrame;

public class SimplePainter {

	public static void main(String[] args) {
		JFrame frame = new JFrame("SIMPLE PAINTER"); // 프레임 이름설정
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 시 완전히 종료되로독 설정
		frame.setResizable(false);
		
		SimplePainterView view = new  SimplePainterView(); // 뷰 패널 객체 생성
		frame.add(view);
		
		frame.pack();
		frame.setVisible(true);

	}

}
