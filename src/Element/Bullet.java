package Element;
import Element.*;
import GameMain.WallType;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;

import AbstractElement.*;
import Constant.*;
public class Bullet extends HIT{
	private int TankStatus;
	private Point TankPoint;
	private Point BulletPoint = new Point();
	private String BulletImage;
	private WallType GameMap[][];
	private Boolean isBoom = false;
	private Boolean isDestory = false;
	private TankBulletType bulletType;
	public Bullet(Tank mainTank,TankBulletType bt) {
		// TODO Auto-generated constructor stub
		TankStatus = mainTank.getTankStatus();
		TankPoint = mainTank.getTankPoint();
		setBulletPoint();
		bulletType = bt;
		super.HP = 1;
	}
	public TankBulletType getBulletType() {
		return bulletType;
	}
	public void setGameMap(WallType[][] Map) {
		GameMap = Map;
	}
	public void setBulletPoint() {
		int x = 0,y = 0;
		int xPianyi = 24;
		int yPianyi = 15;
		if(TankStatus == Tank.MOVE_UP) {
			x = TankPoint.x + xPianyi;
			y = TankPoint.y - 20;
			BulletImage = Config.IMAGE_BULLET_UP;
		}else if(TankStatus == Tank.MOVE_LEFT) {
			x = TankPoint.x - 20;
			y = TankPoint.y + yPianyi;
			BulletImage = Config.IMAGE_BULLET_LEFT;
		}else if(TankStatus == Tank.MOVE_RIGHT) {
			x = TankPoint.x + Config.TANK_SIZE;
			y = TankPoint.y + yPianyi;
			BulletImage = Config.IMAGE_BULLET_RIGHT;
		}else if(TankStatus == Tank.MOVE_DOWN) {
			x = TankPoint.x + xPianyi;
			y = TankPoint.y + Config.TANK_SIZE;
			BulletImage = Config.IMAGE_BULLET_DOWN;
		}
		BulletPoint.x = x;
		BulletPoint.y = y;
		super.wallPoint = BulletPoint;
	}
	public boolean isBulletAtWall() {
		if(BulletPoint.x>=0 && BulletPoint.y>=0 && BulletPoint.x<Config.GAME_WINDOWS_WIDTH &&
		BulletPoint.y<Config.GAME_WINDOWS_HEIGHT) {
			if(GameMap[BulletPoint.x][BulletPoint.y] == WallType.BRICKWALL ||
					GameMap[BulletPoint.x][BulletPoint.y] == WallType.STEELWALL ||
					GameMap[BulletPoint.x][BulletPoint.y] == WallType.TankWall ||
					GameMap[BulletPoint.x][BulletPoint.y] == WallType.BulletWall
						) {
				System.out.println(GameMap[BulletPoint.x][BulletPoint.y]);
					return true;
				}
		}
		return false;
	}
	public void updateBulletPoint() {
		if(!isBulletAtWall()) {
			int sp = Config.BULLET_SPEED;
			if(TankStatus == Tank.MOVE_UP) {
				BulletPoint.y-=sp;
			}else if(TankStatus == Tank.MOVE_DOWN) {
				BulletPoint.y+=sp;
			}else if(TankStatus == Tank.MOVE_LEFT) {
				BulletPoint.x-=sp;
			}else if(TankStatus == Tank.MOVE_RIGHT) {
				BulletPoint.x+=sp;
			}
			if(BulletPoint.x < 0 || BulletPoint.x>Config.GAME_WINDOWS_WIDTH) {
				int max_x = Config.GAME_WINDOWS_WIDTH+20;
				BulletPoint.x = BulletPoint.x<-50?-50:BulletPoint.x;
				BulletPoint.x = BulletPoint.x>max_x?max_x:BulletPoint.x;
				isDestory = true;
			}else if(BulletPoint.y < 0 || BulletPoint.y>Config.GAME_WINDOWS_HEIGHT) {
				int max_y = Config.GAME_WINDOWS_HEIGHT+20;
				BulletPoint.y = BulletPoint.y<-50?-50:BulletPoint.y;
				BulletPoint.y = BulletPoint.y>max_y?max_y:BulletPoint.y;
				isDestory = true;
			}
		}else {
			System.out.println("asf");
			isBoom = true;
			isDestory = true;
		}
	}
	public Point getPoint() {
		return BulletPoint;
	}
	public boolean isBoom() {
		return isBoom;
	}
	public boolean isDestory() {
		return isDestory;
	}
	public void Draw() {
		try {
			updateBulletPoint();
			DrawUtils.draw(BulletImage, BulletPoint.x, BulletPoint.y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
