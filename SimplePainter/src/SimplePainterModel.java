import java.awt.Color;
import java.awt.Point;

public class SimplePainterModel {
	
	// DB 같이 객체의 정보를 저장하기 위한 클래스
	public int nDrawMode; // 모드정보 변수
	public Point ptOne, ptTwo; // 좌표를 구성하는 포인트 변수
	public int nSize; // 텍스트 사이즈를 나타내는 변수
	public boolean bFill; // fill 체크박스의 체크유무를 나타내는 변수
	public Color selectedColor; // 선택된 색상의 정보를 갖는 변수
	
	public SimplePainterModel() {
		nDrawMode = Constants.NONE; // 모드는 아직 설정안됨
		ptOne = new Point(); // 좌표
		ptTwo = new Point();
		nSize = 1; // 사이즈
		bFill = false; // 체크가 안됐음을 설정
		selectedColor = Color.black; // 기본 색상은 검정
	}
	
	public SimplePainterModel(SimplePainterModel data) {
		nDrawMode = data.nDrawMode; // data 객체의 모드를 저장
		ptOne = data.ptOne; // data 객체의 좌표를 저장
		ptTwo = data.ptTwo;
		nSize = data.nSize; // data 객체의 사이즈를 저장
		bFill = data.bFill; // data 객체의 체크유뮤 저장
		selectedColor = data.selectedColor; // data 객체의 색상 저장
	}
	
}
