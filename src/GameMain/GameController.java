package GameMain;
import Constant.*;
import Element.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.lang.model.type.TypeKind;

import org.itheima.game.utils.DrawUtils;
import org.lwjgl.Sys;

import AbstractElement.*;
public class GameController {
	private int map_width = Config.GAME_WINDOWS_WIDTH;
	private int map_height = Config.GAME_WINDOWS_HEIGHT;
	private CopyOnWriteArrayList<Wall> WallList;
	private Point TankPoint = new Point();
	private CopyOnWriteArrayList<HIT> ALLElememt = new CopyOnWriteArrayList<HIT>();
	public Tank myTank1;
	public Tank myTank2;
	public CopyOnWriteArrayList<Tank> TankList = new CopyOnWriteArrayList<Tank>();
	private CopyOnWriteArrayList<Bullet> BulletList = new CopyOnWriteArrayList<Bullet>();
	private CopyOnWriteArrayList<Boom> BoomList = new CopyOnWriteArrayList<Boom>();
	public WallType GameMap[][] = new WallType[Config.GAME_WINDOWS_WIDTH][Config.GAME_WINDOWS_HEIGHT];
	public WallType TankMap[][] = new WallType[Config.GAME_WINDOWS_WIDTH][Config.GAME_WINDOWS_HEIGHT];
	public static int GAME_START = 0;
	public static int GAME_GAMING = 1;
	public static int GAME_WIN = 2;
	public static int GAME_LOSS = 3;
	public static int GAME_STOP = 4;
	public int GameStutus = 0;
	public void CreateTank() {
		Tank mt1 = new Tank(Tank.MOVE_UP,8*Config.TANK_SIZE,Config.GAME_WINDOWS_HEIGHT-Config.TANK_SIZE,Tank.TANK_TYPE_YELLOW);
		Tank mt2 = new Tank(Tank.MOVE_UP,11*Config.TANK_SIZE,Config.GAME_WINDOWS_HEIGHT-Config.TANK_SIZE,Tank.TANK_TYPE_RED);
		myTank1 = mt1;
		myTank2 = mt2;
		TankList.add(mt1);
		TankList.add(mt2);
//		addTankAI(Tank.MOVE_RIGHT, 0, 0);
//		addTankAI(Tank.MOVE_RIGHT, Config.GAME_WINDOWS_WIDTH-Config.TANK_SIZE,0);
		
		addTankAI(Tank.MOVE_RIGHT, 18, 1);
		addTankAI(Tank.MOVE_RIGHT, 14, 1);
		addTankAI(Tank.MOVE_RIGHT, 10, 1);
		addTankAI(Tank.MOVE_RIGHT, 5, 1);
		addTankAI(Tank.MOVE_RIGHT, 1, 1);
		addTankAI(Tank.MOVE_RIGHT, 1, Config.GAME_HEIGHT_BLOCK-2);
		addTankAI(Tank.MOVE_RIGHT, 18, Config.GAME_HEIGHT_BLOCK-2);
		for (HIT tank : TankList) {
			ALLElememt.add(tank);
		}
	}
	public GameController() {
		for(int i=0;i<Config.GAME_WINDOWS_WIDTH;i++) {
			for(int j=0;j<Config.GAME_WINDOWS_HEIGHT;j++) {
				GameMap[i][j]=WallType.NONE;
			}
		}
		CreateMap();
	}
	private void addWall(WallType wt,int x,int y) {
		x = x*Config.WALL_SIZE;
		y = y*Config.WALL_SIZE;
		Wall temp = new Wall();
		if(wt == WallType.GRASSWALL) {
			temp = new GrassWall();
		}else if(wt == WallType.STEELWALL) {
			temp = new SteelWall();
		}else if(wt == WallType.WATERWALL) {
			temp = new WaterWall();
		}else if(wt == WallType.BRICKWALL) {
			temp = new BrickWall();
		}
		temp.setPoint(x,y);
		WallList.add(temp);
	}
	private void addTankAI(int dir,int x,int y) {
		x = x*Config.WALL_SIZE;
		y = y*Config.WALL_SIZE;
		TankAI AI = new TankAI(dir,x,y,Tank.TANK_TYPE_WHITE);
		TankList.add(AI);
	}
	private void CreateMap() {
		WallList = new CopyOnWriteArrayList<Wall>();
		//生成砖墙
//		int CreateWallNumber = Config.GAME_WINDOWS_WIDTH/Config.WALL_SIZE-1;
//		for(int i=0;i<CreateWallNumber;i++) {
//			//砖墙
//			int Draw_y=Config.WALL_SIZE;
//			addWall(WallType.BRICKWALL,i*Config.WALL_SIZE+1,Draw_y);
//			//石墙
//			Draw_y+=Config.WALL_SIZE*2;
//			addWall(WallType.STEELWALL,Config.GAME_WINDOWS_WIDTH-i*Config.WALL_SIZE+1,Draw_y);
//			//水墙
//			Draw_y+=Config.WALL_SIZE*2;
//			addWall(WallType.WATERWALL,i*Config.WALL_SIZE+1,Draw_y);
//			//草墙
//		}
		CreateTank();
		for(int i=0;i<Config.GAME_WIDTH_BLOCK;i++) {
			addWall(WallType.BRICKWALL, i, 0);
			if(i!=8 && i!=11)
				addWall(WallType.BRICKWALL, i, Config.GAME_HEIGHT_BLOCK-1);
		}
		for(int i=0;i<Config.GAME_WINDOWS_HEIGHT;i++) {
			addWall(WallType.BRICKWALL, 0, i+1);
			addWall(WallType.BRICKWALL, 19, i+1);
		}
		for(int i=0;i<6;i++) {
			addWall(WallType.BRICKWALL, i+7, Config.GAME_HEIGHT_BLOCK-2);
		}
		for(int i=1;i<3;i++) {
			addWall(WallType.BRICKWALL, 3, i);
			addWall(WallType.BRICKWALL, 7, i);
			addWall(WallType.BRICKWALL, 15, i);
			addWall(WallType.BRICKWALL, 11, i);
		}
		
		//草
		for(int i=0;i<4;i++) {
			addWall(WallType.GRASSWALL, 8+i, 6);
			addWall(WallType.GRASSWALL, 8+i, 7);
		}
		//水
		for(int i=0;i<5;i++) {
			addWall(WallType.WATERWALL, 3+i, 6);
			addWall(WallType.WATERWALL, 3+i, 7);
		}
		for(int i=0;i<5;i++) {
			addWall(WallType.WATERWALL, 12+i, 6);
			addWall(WallType.WATERWALL, 12+i, 7);
		}
		//石头墙
		for(int i=0;i<16;i++) {
			addWall(WallType.STEELWALL, 3+i, 4);
		}
		for(int i=0;i<1;i++) {
			addWall(WallType.STEELWALL, 11, 3+i);
			addWall(WallType.STEELWALL, 3, 3+i);
			addWall(WallType.STEELWALL, 7, 3+i);
			addWall(WallType.STEELWALL, 15, 3+i);
		}
		for (Wall ww : WallList) {
			ALLElememt.add((HIT)ww);
			elementFill(GameMap,ww.wallType, ww.wallPoint.x, ww.wallPoint.y);
			elementFill(TankMap,ww.wallType, ww.wallPoint.x, ww.wallPoint.y);
		}
	}
	public void addBullet(Bullet b) {
			BulletList.add(b);
			ALLElememt.add(b);
	}
	public Point attack(Point aim,Bullet sendBullect) {
		Point WallPoint = new Point();
		int x = aim.x,y = aim.y;
		Wall reWall = new Wall();
		for (HIT wall : ALLElememt) {
			if(wall.getPoint().x == aim.x&&wall.getPoint().y == aim.y) {
				continue;
			}
			Point twp = wall.getPoint();
			if((x>=twp.x && y>=twp.y && x<=twp.x+Config.WALL_SIZE && y<=twp.y+Config.WALL_SIZE && (wall instanceof Bullet))) {
				WallPoint.x = twp.x-10;
				WallPoint.y = twp.y-10;
				BulletList.remove(wall);
			}
			if((x>=twp.x && y>=twp.y && x<twp.x+Config.WALL_SIZE && y<twp.y+Config.WALL_SIZE && !(wall instanceof Bullet))) {
				WallPoint.x = twp.x;
				WallPoint.y = twp.y;
				if(wall instanceof Tank) {
					if(sendBullect.getBulletType() == TankBulletType.Enemy && wall instanceof TankAI) {
						return WallPoint;
					}
					if(sendBullect.getBulletType() == TankBulletType.Friendly && !(wall instanceof TankAI)) {
						return WallPoint;
					}
				}
				if(wall.beAttack()) {
					if(wall instanceof Wall) {
						WallType wt = ((Wall) wall).wallType;
						if(wt == WallType.BRICKWALL || wt == WallType.STEELWALL)
							WallList.remove(wall);
					}
					TankList.remove(wall);
					BoomList.remove(sendBullect);
					if(!(wall instanceof Bullet)) {
						BoomList.add(new Boom(wall.getPoint().x,wall.getPoint().y,6));
					}
					RefreshWall();
				}
			}
		}
		return WallPoint;
	}
	public void RefreshWall() {
		for(int i=0;i<Config.GAME_WINDOWS_WIDTH;i++) {
			for(int j=0;j<Config.GAME_WINDOWS_HEIGHT;j++) {
				GameMap[i][j]=WallType.NONE;
				TankMap[i][j]=WallType.NONE;
			}
		}
		for (Wall ww : WallList) {
			elementFill(GameMap,ww.wallType, ww.wallPoint.x, ww.wallPoint.y);
			elementFill(TankMap,ww.wallType, ww.wallPoint.x, ww.wallPoint.y);
		}
	}
	public void RefreshTank() {
		for(int i=0;i<Config.GAME_WINDOWS_WIDTH;i++) {
			for(int j=0;j<Config.GAME_WINDOWS_HEIGHT;j++) {
				if(TankMap[i][j]==WallType.TankWall
					||TankMap[i][j]==WallType.BulletWall)
				{
					TankMap[i][j]=WallType.NONE;
				}
			}
		}
		for (Tank tank : TankList) {
			elementFill(TankMap, WallType.TankWall, tank.getTankPoint().x, tank.getTankPoint().y);
		}
		for (Bullet bullet : BulletList) {
			elementFill(TankMap, WallType.BulletWall, bullet.getPoint().x, bullet.getPoint().y);
		}
	}
	public void DrawMap() {
		for(int i=0;i<Config.GAME_WINDOWS_WIDTH;i++) {
			for(int j=0;j<Config.GAME_WINDOWS_HEIGHT;j++) {
				if(TankMap[i][j]!=WallType.NONE) {
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
	public void CheckStatus() {
		int flag = 0;
		for(Tank tank : TankList) {
			if(tank instanceof TankAI) {
				flag = 1;
			}
		}
		for(Tank tank : TankList) {
			if(!(tank instanceof TankAI)) {
				if(flag==1) {
					flag=3;
				}else if(flag==0){
					flag=2;
				}
			}
		}
		if(flag==1) {
			GameStutus = GAME_LOSS;
		}else if(flag == 2) {
			GameStutus = GAME_WIN;
		}
	}
	public void EnterSend() {
		if(GameStutus==GAME_START) {
			GameStutus=GAME_GAMING;
		}else if(GameStutus!=GAME_GAMING) {
			
			ALLElememt.clear();
			TankList.clear();
			WallList.clear();
			BulletList.clear();
			CreateMap();
			GameStutus=GAME_GAMING;
		}
	}
	public void EscStop() {
		if(GameStutus==GAME_GAMING) {
			GameStutus=GAME_STOP;
		}else if(GameStutus==GAME_STOP) {
			GameStutus=GAME_GAMING;
		}
	}
	public void Draw() {
		if(GameStutus == GAME_GAMING) {
			RefreshTank();
			CheckStatus();
			//DrawMap();
			for (Wall wall : WallList) {
				if(wall.wallType != WallType.GRASSWALL)
					wall.Draw();
			}
			for (Tank tank : TankList) {
				tank.setGameMap(GameMap);
				CopyOnWriteArrayList<Tank> t =  (CopyOnWriteArrayList<Tank>) TankList.clone();
				t.remove(tank);
				tank.setTankList(t);
				tank.Draw();
				tank.setGameControl(this);
				if(tank instanceof TankAI) {
					((TankAI) tank).upDateGrand();
				}
			}
			for (Bullet bull : BulletList) {
				WallType[][] wt = new WallType[Config.GAME_WINDOWS_WIDTH][Config.GAME_WINDOWS_HEIGHT];
				wt = TankMap.clone();
				bull.setGameMap(wt);
				bull.Draw();
				if(bull.isDestory()) {
					if (bull.isBoom()) {
						Point bullPoint = bull.getPoint();
						Point WallPoint = attack(bullPoint,bull);
						BoomList.add(new Boom(WallPoint.x,WallPoint.y,3));
					}
					BulletList.remove(bull);
				}
			}
			for (Boom boom : BoomList) {
				boom.Draw();
				if(boom.isDestory()) {
					BoomList.remove(boom);
				}
			}
			for (Wall wall : WallList) {
				if(wall.wallType == WallType.GRASSWALL)
					wall.Draw();
			}
		}else if(GameStutus == GAME_STOP) {
			for (Wall wall : WallList) {
				if(wall.wallType != WallType.GRASSWALL)
					wall.Draw();
			}
			for (Tank tank : TankList) {
				tank.Draw();
			}
			for (Wall wall : WallList) {
				if(wall.wallType == WallType.GRASSWALL)
					wall.Draw();
			}
			
			try {
				DrawUtils.draw(Config.IMAGE_GAME_STOP, 300, 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(GameStutus == GAME_START) {
			
			for (Wall wall : WallList) {
				if(wall.wallType == WallType.BRICKWALL)
					wall.Draw();
			}
			try {
				DrawUtils.draw(Config.IMAGE_GAME_START, 300, 50);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(GameStutus == GAME_WIN) {
			try {
				DrawUtils.draw(Config.IMAGE_GAME_WIN, 300, 50);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(GameStutus == GAME_LOSS) {
			try {
				DrawUtils.draw(Config.IMAGE_GAME_OVER, 300, 50);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//调试
	}
	private void elementFill(WallType[][] mm,WallType key,int x,int y) {
		for(int i=1;i<Config.WALL_SIZE;i++) {
			for (int k=1;k<Config.WALL_SIZE;k++) {
				int index_x = i+x;
				int index_y = k+y;
				if(index_x>=Config.GAME_WINDOWS_WIDTH || index_y>=Config.GAME_WINDOWS_HEIGHT) {
					return;
				}
				mm[index_x][index_y] = key;
			}
		}
	}
}
