import java.awt.Color;
import java.awt.Point;

public class SimplePainterModel {
	
	// DB ���� ��ü�� ������ �����ϱ� ���� Ŭ����
	public int nDrawMode; // ������� ����
	public Point ptOne, ptTwo; // ��ǥ�� �����ϴ� ����Ʈ ����
	public int nSize; // �ؽ�Ʈ ����� ��Ÿ���� ����
	public boolean bFill; // fill üũ�ڽ��� üũ������ ��Ÿ���� ����
	public Color selectedColor; // ���õ� ������ ������ ���� ����
	
	public SimplePainterModel() {
		nDrawMode = Constants.NONE; // ���� ���� �����ȵ�
		ptOne = new Point(); // ��ǥ
		ptTwo = new Point();
		nSize = 1; // ������
		bFill = false; // üũ�� �ȵ����� ����
		selectedColor = Color.black; // �⺻ ������ ����
	}
	
	public SimplePainterModel(SimplePainterModel data) {
		nDrawMode = data.nDrawMode; // data ��ü�� ��带 ����
		ptOne = data.ptOne; // data ��ü�� ��ǥ�� ����
		ptTwo = data.ptTwo;
		nSize = data.nSize; // data ��ü�� ����� ����
		bFill = data.bFill; // data ��ü�� üũ���� ����
		selectedColor = data.selectedColor; // data ��ü�� ���� ����
	}
	
}
