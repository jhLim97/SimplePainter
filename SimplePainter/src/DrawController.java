import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class DrawController extends JPanel {

	private SimplePainterModel nowData; // 객체의 정보저장을 위한 변수
	private ArrayList<SimplePainterModel> savedList; // 객체들의 정보를 관리하기 위한 arraylist 배열
	private DrawListener drawL; // 리스너 선언
	private SimplePainterView view; // 뷰 클래스에 접근하기 위한 변수 선언
	private boolean bDrag; // 드래그의 유무 판단을 위한 변수
	
	// 이미지 그리기
	ImageIcon imgSanta = new ImageIcon("images/santa-removebg.png");
	Image img = imgSanta.getImage();
	
	public DrawController(SimplePainterView v) {
		
		view = v; // 뷰 클래스의 객체 인자로 받아와 서 멤버 변수에 저장
		
		setBackground(Color.white);
		
		drawL = new DrawListener(); // 그리기 위한 리스너 생성
		addMouseListener(drawL); // 각각의 리스너에 연결
		addMouseMotionListener(drawL); // 위와 동일
		
		nowData = new SimplePainterModel(); // 객체의 정보저장을 위한 변수 생성
		savedList = new ArrayList<SimplePainterModel>(); // 객체들의 정보를 관리하기 위한 arraylist 배열 생성
		
		nowData.nDrawMode = Constants.NONE; // 뷰에서 버튼 누르면 드로우모드 바뀌도록
		bDrag = false; // 초기에 false로 설정
	} 
	
	public void setDrawMode(int mode) {
		nowData.nDrawMode = mode; // 현재 선택된 모드 저장
		if (nowData.nDrawMode == Constants.DOT) view.setTxtSize(10); // DOT 일 경우 뷰 클래스의 텍스트사이즈 필드는 10
		else view.setTxtSize(1);                                     // 이외는 1
		
		if (nowData.nDrawMode == Constants.UNDO && savedList.size() != 0) { // 최근 거 부터 하나씩 지우기
			savedList.remove(savedList.size()-1);
			repaint();
			view.setCount(savedList.size()); // 현재 그려진 도형의 개수 정보 보내기
		}
		else if (nowData.nDrawMode == Constants.CLEAR && savedList.size() != 0) { // 모두 지우기
			savedList.clear();
			repaint();
			view.setCount(savedList.size()); // 현재 그려진 도형의 개수 정보 보내기
		}
	}
	
	public void setSelectedColor(Color color) {
		nowData.selectedColor = color; // 선택된 색상의 정보를 멤버변수에 저장
	}
	
	public void paintComponent(Graphics page) {
		super.paintComponent(page); // 권한을 얻어온 것으로 해당 코드 없을 시 계속 잔상이 남음
		
		// Graphics2D 사용이유는 Graphics에는 선의 width 변경 못함
		Graphics2D page2 = (Graphics2D) page; // 래퍼런스 타입으로 객체를 가리킴(캐스팅, 호환되는경우) : page2 변경 시 page도 바뀜
		
		if(bDrag) {
			// now data 그려주기
			switch(nowData.nDrawMode) {	
				case Constants.LINE :
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize)); // BasicStrok를 이용해 사이즈 설정
					page.drawLine(nowData.ptOne.x, nowData.ptOne.y, nowData.ptTwo.x, nowData.ptTwo.y); // 선 그리기
					break;
				case Constants.RECT :
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize)); // BasicStrok를 이용해 사이즈 설정
					draw4Rect(page, nowData.ptOne, nowData.ptTwo, nowData.bFill); // 사각형 그리기
					break;
				case Constants.OVAL :
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					draw4Oval(page, nowData.ptOne, nowData.ptTwo, nowData.bFill); // 타원 그리기
					break;
				case Constants.SANTA :
					page2.setStroke(new BasicStroke(nowData.nSize));
					draw4Img(page, nowData.ptOne, nowData.ptTwo); // 이미지 그리기
					break;
				case Constants.STRING :
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					draw4String(page, nowData.ptOne, nowData.ptTwo); // 문자열 그리기
					break;
			}
		}
		
		// 저장되어있는 정보 그려주기
		for (SimplePainterModel data : savedList) {
			switch(data.nDrawMode) {
				case Constants.DOT :
					page.setColor(data.selectedColor);
					page.fillOval(data.ptOne.x-data.nSize/2, data.ptOne.y-data.nSize/2, data.nSize, data.nSize); // 점 그리기
					
					break;
				case Constants.LINE :
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					page.drawLine(data.ptOne.x, data.ptOne.y, data.ptTwo.x, data.ptTwo.y); // 선 그리기
					break;
				case Constants.RECT :
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					draw4Rect(page, data.ptOne, data.ptTwo, data.bFill); // 사각형 그리기
					break;
				case Constants.OVAL :
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					draw4Oval(page, data.ptOne, data.ptTwo, data.bFill); // 타원 그리기
					break;
				case Constants.SANTA :
					page2.setStroke(new BasicStroke(data.nSize));
					draw4Img(page, data.ptOne, data.ptTwo); // 이미지 그리기
					break;
				case Constants.STRING :
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					draw4String(page, data.ptOne, data.ptTwo); // 문자열 그리기
					break;
			}
		}
		
	} // paintComponent()
	
	private void draw4Rect (Graphics page, Point pt1, Point pt2, boolean fill) {
		
		// 사각형 그리기
		int x, y, width, height;
		x = y = width = height = 0;
		
		// 좌표의 시작점과 끝점을 비교하여 width, height 결정
		if (pt1.x < pt2.x && pt1.y < pt2.y) {
			x = pt1.x;
			y = pt1.y;
			width = pt2.x - pt1.x;
			height = pt2.y - pt1.y;
		} else if (pt1.x < pt2.x && pt1.y > pt2.y) {
			x = pt1.x;
			y = pt2.y;
			width = pt2.x - pt1.x;
			height = pt1.y - pt2.y;
		} else if (pt1.x > pt2.x && pt1.y < pt2.y) {
			x = pt2.x;
			y = pt1.y;
			width = pt1.x - pt2.x;
			height = pt2.y - pt1.y;
		} else if (pt1.x > pt2.x && pt1.y > pt2.y) {
			x = pt2.x;
			y = pt2.y;
			width = pt1.x - pt2.x;
			height = pt1.y - pt2.y;
		}
		
		if (fill) page.fillRect(x, y, width, height); // fill 체크박스가 선택되어 있는 경우 안이 채워진 사각형 그리기
		else page.drawRect(x, y, width, height); // 안이 채워지지 않은 사각형 그리기
		
	}
	
	private void draw4Oval (Graphics page, Point pt1, Point pt2, boolean fill) {
		
		// 타원 그리기
		int x, y, width, height;
		x = y = width = height = 0;
		
		// 좌표의 시작점과 끝점을 비교하여 width, height 결정
		if (pt1.x < pt2.x && pt1.y < pt2.y) {
			x = pt1.x;
			y = pt1.y;
			width = pt2.x - pt1.x;
			height = pt2.y - pt1.y;
		} else if (pt1.x < pt2.x && pt1.y > pt2.y) {
			x = pt1.x;
			y = pt2.y;
			width = pt2.x - pt1.x;
			height = pt1.y - pt2.y;
		} else if (pt1.x > pt2.x && pt1.y < pt2.y) {
			x = pt2.x;
			y = pt1.y;
			width = pt1.x - pt2.x;
			height = pt2.y - pt1.y;
		} else if (pt1.x > pt2.x && pt1.y > pt2.y) {
			x = pt2.x;
			y = pt2.y;
			width = pt1.x - pt2.x;
			height = pt1.y - pt2.y;
		}
		
		if (fill) page.fillOval(x, y, width, height); // fill 체크박스가 선택되어 있는 경우 안이 채워진 타원 그리기
		else page.drawOval(x, y, width, height); // 안이 채워지지 않은 타원 그리기
		
	}
	
	private void draw4Img (Graphics page, Point pt1, Point pt2) {
		
		// 이미지 그리기
		int x, y, width, height;
		x = y = width = height = 0;
		
		// 좌표의 시작점과 끝점을 비교하여 width, height 결정
		if (pt1.x < pt2.x && pt1.y < pt2.y) {
			x = pt1.x;
			y = pt1.y;
			width = pt2.x - pt1.x;
			height = pt2.y - pt1.y;
		} else if (pt1.x < pt2.x && pt1.y > pt2.y) {
			x = pt1.x;
			y = pt2.y;
			width = pt2.x - pt1.x;
			height = pt1.y - pt2.y;
		} else if (pt1.x > pt2.x && pt1.y < pt2.y) {
			x = pt2.x;
			y = pt1.y;
			width = pt1.x - pt2.x;
			height = pt2.y - pt1.y;
		} else if (pt1.x > pt2.x && pt1.y > pt2.y) {
			x = pt2.x;
			y = pt2.y;
			width = pt1.x - pt2.x;
			height = pt1.y - pt2.y;
		}
		
		page.drawImage(img, x, y, width, height, this); // 이미지 그리기
		
	}
	
	private void draw4String (Graphics page, Point pt1, Point pt2) {
		
		// 문자열 그리기
		int x, y, width, height;
		x = y = width = height = 0;
		
		// 좌표의 시작점과 끝점을 비교하여 width, height 결정
		if (pt1.x < pt2.x && pt1.y < pt2.y) {
			x = pt1.x;
			y = pt1.y;
			width = pt2.x - pt1.x;
			height = pt2.y - pt1.y;
		} else if (pt1.x < pt2.x && pt1.y > pt2.y) {
			x = pt1.x;
			y = pt2.y;
			width = pt2.x - pt1.x;
			height = pt1.y - pt2.y;
		} else if (pt1.x > pt2.x && pt1.y < pt2.y) {
			x = pt2.x;
			y = pt1.y;
			width = pt1.x - pt2.x;
			height = pt2.y - pt1.y;
		} else if (pt1.x > pt2.x && pt1.y > pt2.y) {
			x = pt2.x;
			y = pt2.y;
			width = pt1.x - pt2.x;
			height = pt1.y - pt2.y;
		}
		
		page.setFont(new Font("Verdava", Font.BOLD, width)); // 문자열 크기는 width로 설정
		page.drawString("Merry, Christmas!!", x, y); // 문자열 그리기
		
	}
	
	private class DrawListener implements MouseListener, MouseMotionListener {

		// 점 그리기
		@Override
		public void mouseClicked(MouseEvent e) {
			if (nowData.nDrawMode == Constants.DOT) {
				nowData.ptOne = e.getPoint();
				nowData.nSize = view.getTxtSize();
				savedList.add(new SimplePainterModel(nowData)); // 현재 데이터의 객체정보 저장
				repaint(); // 다시 그려주기
			}
			
			view.setCount(savedList.size()); // 현재 그려진 도형의 개수정보 보내기
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) { // 마우스를 누를 때 객체 정보 저장
			if (nowData.nDrawMode == Constants.LINE) {
				bDrag = true;
				
				nowData.ptOne = e.getPoint();
				nowData.nSize = view.getTxtSize();
			}
			else if (nowData.nDrawMode == Constants.RECT) {
				bDrag = true;
				
				nowData.ptOne = e.getPoint();
				nowData.nSize = view.getTxtSize();
				nowData.bFill = view.getChkFill();
			}
			else if (nowData.nDrawMode == Constants.OVAL) {
				bDrag = true;
				
				nowData.ptOne = e.getPoint();
				nowData.nSize = view.getTxtSize();
				nowData.bFill = view.getChkFill();
			}
			else if (nowData.nDrawMode == Constants.SANTA) {
				bDrag = true;
				
				nowData.ptOne = e.getPoint();
				nowData.nSize = view.getTxtSize();
			}
			else if (nowData.nDrawMode == Constants.STRING) {
				bDrag = true;
				
				nowData.ptOne = e.getPoint();
				nowData.nSize = view.getTxtSize();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) { // 마우스를 때는 경우의 그려주기
			if (nowData.nDrawMode == Constants.LINE) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // 현재 데이터의 객체정보 저장
				repaint(); // 다시 그려주기
			}
			else if (nowData.nDrawMode == Constants.RECT) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // 현재 데이터의 객체정보 저장
				repaint(); // 다시 그려주기
			}
			else if (nowData.nDrawMode == Constants.OVAL) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // 현재 데이터의 객체정보 저장
				repaint(); // 다시 그려주기
			}
			else if (nowData.nDrawMode == Constants.SANTA) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // 현재 데이터의 객체정보 저장
				repaint(); // 다시 그려주기
			}
			else if (nowData.nDrawMode == Constants.STRING) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // 현재 데이터의 객체정보 저장
				repaint(); // 다시 그려주기
			}
			
			view.setCount(savedList.size()); // 현재 그려진 도형의 개수정보 보내기
		}

		@Override
		public void mouseDragged(MouseEvent e) { // 드래그하는 경우의 그려주기
			if (nowData.nDrawMode == Constants.LINE) {
				nowData.ptTwo = e.getPoint();
				repaint(); // 다시 그려주기
			}
			else if (nowData.nDrawMode == Constants.RECT) {
				nowData.ptTwo = e.getPoint();
				repaint(); // 다시 그려주기
			}
			else if (nowData.nDrawMode == Constants.OVAL) {	
				nowData.ptTwo = e.getPoint();
				repaint(); // 다시 그려주기
			}
			else if (nowData.nDrawMode == Constants.SANTA) {	
				nowData.ptTwo = e.getPoint();
				repaint(); // 다시 그려주기
			}
			else if (nowData.nDrawMode == Constants.STRING) {	
				nowData.ptTwo = e.getPoint();
				repaint(); // 다시 그려주기
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
} // DrawController class
