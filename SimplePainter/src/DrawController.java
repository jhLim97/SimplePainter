import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class DrawController extends JPanel {

	private SimplePainterModel nowData; // ��ü�� ���������� ���� ����
	private ArrayList<SimplePainterModel> savedList; // ��ü���� ������ �����ϱ� ���� arraylist �迭
	private DrawListener drawL; // ������ ����
	private SimplePainterView view; // �� Ŭ������ �����ϱ� ���� ���� ����
	private boolean bDrag; // �巡���� ���� �Ǵ��� ���� ����
	
	// �̹��� �׸���
	ImageIcon imgSanta = new ImageIcon("images/santa-removebg.png");
	Image img = imgSanta.getImage();
	
	public DrawController(SimplePainterView v) {
		
		view = v; // �� Ŭ������ ��ü ���ڷ� �޾ƿ� �� ��� ������ ����
		
		setBackground(Color.white);
		
		drawL = new DrawListener(); // �׸��� ���� ������ ����
		addMouseListener(drawL); // ������ �����ʿ� ����
		addMouseMotionListener(drawL); // ���� ����
		
		nowData = new SimplePainterModel(); // ��ü�� ���������� ���� ���� ����
		savedList = new ArrayList<SimplePainterModel>(); // ��ü���� ������ �����ϱ� ���� arraylist �迭 ����
		
		nowData.nDrawMode = Constants.NONE; // �信�� ��ư ������ ��ο��� �ٲ��
		bDrag = false; // �ʱ⿡ false�� ����
	} 
	
	public void setDrawMode(int mode) {
		nowData.nDrawMode = mode; // ���� ���õ� ��� ����
		if (nowData.nDrawMode == Constants.DOT) view.setTxtSize(10); // DOT �� ��� �� Ŭ������ �ؽ�Ʈ������ �ʵ�� 10
		else view.setTxtSize(1);                                     // �ܴ̿� 1
		
		if (nowData.nDrawMode == Constants.UNDO && savedList.size() != 0) { // �ֱ� �� ���� �ϳ��� �����
			savedList.remove(savedList.size()-1);
			repaint();
			view.setCount(savedList.size()); // ���� �׷��� ������ ���� ���� ������
		}
		else if (nowData.nDrawMode == Constants.CLEAR && savedList.size() != 0) { // ��� �����
			savedList.clear();
			repaint();
			view.setCount(savedList.size()); // ���� �׷��� ������ ���� ���� ������
		}
	}
	
	public void setSelectedColor(Color color) {
		nowData.selectedColor = color; // ���õ� ������ ������ ��������� ����
	}
	
	public void paintComponent(Graphics page) {
		super.paintComponent(page); // ������ ���� ������ �ش� �ڵ� ���� �� ��� �ܻ��� ����
		
		// Graphics2D ��������� Graphics���� ���� width ���� ����
		Graphics2D page2 = (Graphics2D) page; // ���۷��� Ÿ������ ��ü�� ����Ŵ(ĳ����, ȣȯ�Ǵ°��) : page2 ���� �� page�� �ٲ�
		
		if(bDrag) {
			// now data �׷��ֱ�
			switch(nowData.nDrawMode) {	
				case Constants.LINE :
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize)); // BasicStrok�� �̿��� ������ ����
					page.drawLine(nowData.ptOne.x, nowData.ptOne.y, nowData.ptTwo.x, nowData.ptTwo.y); // �� �׸���
					break;
				case Constants.RECT :
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize)); // BasicStrok�� �̿��� ������ ����
					draw4Rect(page, nowData.ptOne, nowData.ptTwo, nowData.bFill); // �簢�� �׸���
					break;
				case Constants.OVAL :
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					draw4Oval(page, nowData.ptOne, nowData.ptTwo, nowData.bFill); // Ÿ�� �׸���
					break;
				case Constants.SANTA :
					page2.setStroke(new BasicStroke(nowData.nSize));
					draw4Img(page, nowData.ptOne, nowData.ptTwo); // �̹��� �׸���
					break;
				case Constants.STRING :
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					draw4String(page, nowData.ptOne, nowData.ptTwo); // ���ڿ� �׸���
					break;
			}
		}
		
		// ����Ǿ��ִ� ���� �׷��ֱ�
		for (SimplePainterModel data : savedList) {
			switch(data.nDrawMode) {
				case Constants.DOT :
					page.setColor(data.selectedColor);
					page.fillOval(data.ptOne.x-data.nSize/2, data.ptOne.y-data.nSize/2, data.nSize, data.nSize); // �� �׸���
					
					break;
				case Constants.LINE :
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					page.drawLine(data.ptOne.x, data.ptOne.y, data.ptTwo.x, data.ptTwo.y); // �� �׸���
					break;
				case Constants.RECT :
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					draw4Rect(page, data.ptOne, data.ptTwo, data.bFill); // �簢�� �׸���
					break;
				case Constants.OVAL :
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					draw4Oval(page, data.ptOne, data.ptTwo, data.bFill); // Ÿ�� �׸���
					break;
				case Constants.SANTA :
					page2.setStroke(new BasicStroke(data.nSize));
					draw4Img(page, data.ptOne, data.ptTwo); // �̹��� �׸���
					break;
				case Constants.STRING :
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					draw4String(page, data.ptOne, data.ptTwo); // ���ڿ� �׸���
					break;
			}
		}
		
	} // paintComponent()
	
	private void draw4Rect (Graphics page, Point pt1, Point pt2, boolean fill) {
		
		// �簢�� �׸���
		int x, y, width, height;
		x = y = width = height = 0;
		
		// ��ǥ�� �������� ������ ���Ͽ� width, height ����
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
		
		if (fill) page.fillRect(x, y, width, height); // fill üũ�ڽ��� ���õǾ� �ִ� ��� ���� ä���� �簢�� �׸���
		else page.drawRect(x, y, width, height); // ���� ä������ ���� �簢�� �׸���
		
	}
	
	private void draw4Oval (Graphics page, Point pt1, Point pt2, boolean fill) {
		
		// Ÿ�� �׸���
		int x, y, width, height;
		x = y = width = height = 0;
		
		// ��ǥ�� �������� ������ ���Ͽ� width, height ����
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
		
		if (fill) page.fillOval(x, y, width, height); // fill üũ�ڽ��� ���õǾ� �ִ� ��� ���� ä���� Ÿ�� �׸���
		else page.drawOval(x, y, width, height); // ���� ä������ ���� Ÿ�� �׸���
		
	}
	
	private void draw4Img (Graphics page, Point pt1, Point pt2) {
		
		// �̹��� �׸���
		int x, y, width, height;
		x = y = width = height = 0;
		
		// ��ǥ�� �������� ������ ���Ͽ� width, height ����
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
		
		page.drawImage(img, x, y, width, height, this); // �̹��� �׸���
		
	}
	
	private void draw4String (Graphics page, Point pt1, Point pt2) {
		
		// ���ڿ� �׸���
		int x, y, width, height;
		x = y = width = height = 0;
		
		// ��ǥ�� �������� ������ ���Ͽ� width, height ����
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
		
		page.setFont(new Font("Verdava", Font.BOLD, width)); // ���ڿ� ũ��� width�� ����
		page.drawString("Merry, Christmas!!", x, y); // ���ڿ� �׸���
		
	}
	
	private class DrawListener implements MouseListener, MouseMotionListener {

		// �� �׸���
		@Override
		public void mouseClicked(MouseEvent e) {
			if (nowData.nDrawMode == Constants.DOT) {
				nowData.ptOne = e.getPoint();
				nowData.nSize = view.getTxtSize();
				savedList.add(new SimplePainterModel(nowData)); // ���� �������� ��ü���� ����
				repaint(); // �ٽ� �׷��ֱ�
			}
			
			view.setCount(savedList.size()); // ���� �׷��� ������ �������� ������
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) { // ���콺�� ���� �� ��ü ���� ����
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
		public void mouseReleased(MouseEvent e) { // ���콺�� ���� ����� �׷��ֱ�
			if (nowData.nDrawMode == Constants.LINE) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // ���� �������� ��ü���� ����
				repaint(); // �ٽ� �׷��ֱ�
			}
			else if (nowData.nDrawMode == Constants.RECT) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // ���� �������� ��ü���� ����
				repaint(); // �ٽ� �׷��ֱ�
			}
			else if (nowData.nDrawMode == Constants.OVAL) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // ���� �������� ��ü���� ����
				repaint(); // �ٽ� �׷��ֱ�
			}
			else if (nowData.nDrawMode == Constants.SANTA) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // ���� �������� ��ü���� ����
				repaint(); // �ٽ� �׷��ֱ�
			}
			else if (nowData.nDrawMode == Constants.STRING) {
				bDrag = false;
				
				nowData.ptTwo = e.getPoint();
				savedList.add(new SimplePainterModel(nowData)); // ���� �������� ��ü���� ����
				repaint(); // �ٽ� �׷��ֱ�
			}
			
			view.setCount(savedList.size()); // ���� �׷��� ������ �������� ������
		}

		@Override
		public void mouseDragged(MouseEvent e) { // �巡���ϴ� ����� �׷��ֱ�
			if (nowData.nDrawMode == Constants.LINE) {
				nowData.ptTwo = e.getPoint();
				repaint(); // �ٽ� �׷��ֱ�
			}
			else if (nowData.nDrawMode == Constants.RECT) {
				nowData.ptTwo = e.getPoint();
				repaint(); // �ٽ� �׷��ֱ�
			}
			else if (nowData.nDrawMode == Constants.OVAL) {	
				nowData.ptTwo = e.getPoint();
				repaint(); // �ٽ� �׷��ֱ�
			}
			else if (nowData.nDrawMode == Constants.SANTA) {	
				nowData.ptTwo = e.getPoint();
				repaint(); // �ٽ� �׷��ֱ�
			}
			else if (nowData.nDrawMode == Constants.STRING) {	
				nowData.ptTwo = e.getPoint();
				repaint(); // �ٽ� �׷��ֱ�
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
