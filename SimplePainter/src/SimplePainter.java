import javax.swing.JFrame;

public class SimplePainter {

	public static void main(String[] args) {
		JFrame frame = new JFrame("SIMPLE PAINTER"); // ������ �̸�����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���� �� ������ ����Ƿε� ����
		frame.setResizable(false);
		
		SimplePainterView view = new  SimplePainterView(); // �� �г� ��ü ����
		frame.add(view);
		
		frame.pack();
		frame.setVisible(true);

	}

}
