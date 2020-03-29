package Element;

import AbstractElement.Point;
import Constant.Config;

public class HIT {
	public Point wallPoint;
	protected int HP = Config.WALL_HP;
	public void setPoint(int x,int y) {
		wallPoint = new Point();
		wallPoint.x = x;
		wallPoint.y = y;
	}
	public Point getPoint() {
		return wallPoint;
	}
	public boolean beAttack() {
		HP = HP<0?0:HP-1;
		if(HP <= 0) {
			return true;
		}
		return false;
	}
	public boolean isDestory() {
		if (HP <=0)
			return true;
		return false;
	}
	public int getHP() {
		return HP;
	}
}
