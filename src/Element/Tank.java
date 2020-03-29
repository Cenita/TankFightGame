package Element;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.itheima.game.utils.DrawUtils;
import AbstractElement.Point;
import Constant.*;
import GameMain.GameController;
import GameMain.WallType;
public class Tank extends HIT{
	private Point TankPoint = new Point();
	protected int speed = Config.TANK_SPEED;
	private int last_dirction;
	private int default_dirction;
	private int Tank_Size;
	private int count_step=0;
	private int w_length = Config.GAME_WINDOWS_WIDTH;
	private int w_height = Config.GAME_WINDOWS_HEIGHT;
	private String Tank_Image;
	private int newDirection;
	private int TankType;
	private int TankTempX=0;
	private int TankTempY=0;
	protected GameController mainController;
	private CopyOnWriteArrayList<Tank> TankList = new CopyOnWriteArrayList<Tank>();
	public WallType GameMap[][] = new WallType[Config.GAME_WINDOWS_WIDTH][Config.GAME_WINDOWS_HEIGHT];
	public static int MOVE_UP = 1;
	public static int MOVE_DOWN = 2;
	public static int MOVE_LEFT = 3;
	public static int MOVE_RIGHT = 4;
	public static int TANK_TYPE_YELLOW = 1;
	public static int TANK_TYPE_WHITE = 2;
	public static int TANK_TYPE_RED = 3;
	public Tank(int dirction,int start_x,int start_y,int TankType) {
		this.Tank_Size = Config.TANK_SIZE;
		this.TankType = TankType;
		default_dirction = dirction;
		last_dirction = default_dirction;
		newDirection = default_dirction;
		ChangeTankStatus(dirction);
		TankPoint.x = start_x;
		TankPoint.y = start_y;
		wallPoint = TankPoint;
		super.HP = Config.TANK_HP;
	}
	public void setGameControl(GameController g) {
		mainController = g;
	}
	//改变坦克状态
	public void ChangeTankStatus(int move_status) {
		newDirection = move_status;
		if(TankType == TANK_TYPE_YELLOW) {
			Tank_Image = Config.IMAGE_TANK1[move_status-1];
		}else if(TankType == TANK_TYPE_WHITE) {
			Tank_Image = Config.IMAGE_TANK2[move_status-1];
		}else if(TankType == TANK_TYPE_RED) {
			Tank_Image = Config.IMAGE_TANK3[move_status-1];
		}
		
	}
	//获取坦克的状态
	public int getTankStatus() {
		return newDirection;
	}
	//得到坦克点
	public Point getTankPoint() {
		return TankPoint;
	}
	public void DrawMap() {
		for(int i=0;i<Config.GAME_WINDOWS_WIDTH;i++) {
			for(int j=0;j<Config.GAME_WINDOWS_HEIGHT;j++) {
				if(GameMap[i][j]!=WallType.NONE) {
					try {
						DrawUtils.draw("res//img//02.jpg", i, j);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void setGameMap(WallType[][] gp) {
		for(int i=0;i<Config.GAME_WINDOWS_WIDTH;i++) {
			for(int j=0;j<Config.GAME_WINDOWS_HEIGHT;j++) {
				GameMap[i][j] = gp[i][j];
			}
		}
	}
	//绘图事件
	public void Draw() {
		try {
			DrawUtils.draw(Tank_Image, TankPoint.x, TankPoint.y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获取渐进速度
	public int getCurrentSpeed(int move_status) {
		int sp = speed;
		if (move_status == last_dirction) {
			count_step+=1;
			if(count_step==Config.TANK_ADD_STEP) {
				count_step=0;
				sp+=1;
			}
		}
		else {
			sp=Config.TANK_SPEED;
			count_step=0;
			last_dirction = move_status;
		}
		sp = sp>Config.TANK_MAX_SPEED?Config.TANK_MAX_SPEED:sp;
		return sp;
	}
	//移动坦克函数
	public boolean moveTank(int move_status){
		int tx = TankPoint.x;
		int ty = TankPoint.y;
		ChangeTankStatus(move_status);
		speed = getCurrentSpeed(move_status);
		for(int i=0;i<speed;i++) {
			if(move_status==MOVE_UP) {
				ty-=1;
			}else if (move_status == MOVE_DOWN) {
				ty+=1;
			}else if (move_status == MOVE_LEFT) {
				tx-=1;
			}else if (move_status == MOVE_RIGHT) {
				tx+=1;
			}
			if(tx<0 || ty<0 || tx>=w_length-Tank_Size || ty>=w_height-Tank_Size) {
				tx = tx<0?0:tx;
				tx = tx>=w_length-Tank_Size?w_length-Tank_Size-1:tx;
				ty = ty<0?0:ty;
				ty = ty>=w_height-Tank_Size?w_height-Tank_Size-1:ty;
				TankPoint.x = tx;
				TankPoint.y = ty;
				return false;
			}
			else if (checkPass(tx,ty))
			{
				return false;
			}
			else {
				TankPoint.x = tx;
				TankPoint.y = ty;
			}
		}
		return true;
	}
	private boolean checkPass(int x,int y) {
		boolean flag = true;
		int tx = x;
		int ty = y;
		int inlong = 5;
		if (checkMap(tx+inlong,ty+inlong)) {
			flag=false;
		}else if(checkMap(tx+Config.TANK_SIZE-inlong,ty+inlong)) {
			flag=false;
		}else if(checkMap(tx+inlong, ty+Config.TANK_SIZE-inlong)) {
			flag=false;
		}else if(checkMap(tx+Config.TANK_SIZE-inlong, ty+Config.TANK_SIZE-inlong)) {
			flag=false;
		}
		return !flag;
	}
	private void deleteTankMap() {
		for(int i=TankPoint.x;i<TankPoint.x+Tank_Size;i++) {
			for(int j=TankPoint.y;j<TankPoint.y+Tank_Size;j++) {
				if(GameMap[i][j]==WallType.TankWall) {
					GameMap[i][j]=WallType.NONE;
				}
			}
		}
	}
	public void setTankList(CopyOnWriteArrayList<Tank> t) {
		TankList = (CopyOnWriteArrayList<Tank>) t.clone();
	}
	private void elementFill(WallType key,int x,int y) {
		for(int i=0;i<Config.WALL_SIZE;i++) {
			for (int k=0;k<Config.WALL_SIZE;k++) {
				int index_x = i+x;
				int index_y = k+y;
				if(index_x>=Config.GAME_WINDOWS_WIDTH || index_y>=Config.GAME_WINDOWS_HEIGHT) {
					return;
				}
				GameMap[index_x][index_y] = key;
			}
		}
	}
	private void RrefashMap() {
		for (Tank tank:TankList) {
			elementFill(WallType.TankWall, tank.getTankPoint().x,tank.getTankPoint().y);
		}
	}
	private boolean checkMap(int x,int y) {
		RrefashMap();
		WallType[] canPass = {WallType.NONE,WallType.GRASSWALL};
		boolean flag = false;
		for (WallType cp : canPass) {
			if(GameMap[x][y]==cp) {
				TankTempX = x;
				TankTempY = y;
				flag=true;
			}
		}
		return !flag;
	}
}
