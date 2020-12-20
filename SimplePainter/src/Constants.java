import java.awt.Color;

public class Constants {
	
	// 객체 생성 없이 바로 접근 가능한 constants 클래스(static으로 구현)
	
	// 메뉴를 나타내는 constants
	static final public String MENU[] = {
			"DOT", "LINE", "RECT", "OVAL", "UNDO", "Clear", "Santa", "String"
	};
	
	// 색상 정보를 나타내는 constants
	static final public Color HOVERING[] = {
		Color.white, Color.black, Color.yellow, Color.red
	};
	
	// 모드를 나타내는 constants
	static final public int DOT = 0;
	static final public int LINE = 1;
	static final public int RECT = 2;
	static final public int OVAL = 3;
	static final public int UNDO = 4;
	static final public int CLEAR = 5;
	static final public int SANTA = 6;
	static final public int STRING = 7;
	static final public int NONE = 8;
}
