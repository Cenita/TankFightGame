package GameMain;

import java.io.IOException;

import org.itheima.game.Window;
import org.itheima.game.utils.DrawUtils;
import org.lwjgl.input.Keyboard;

import AbstractElement.*;
import Constant.Config;
import Constant.TankBulletType;
import Element.Bullet;
import Element.Tank;
import GameMain.*;
public class GameWindows extends Window {
	private GameController MainControl = new GameController();
	private Tank myTank,myTank2;
	private long last_Tank1_bullet_time = 0;
	private long last_Tank2_bullet_time = 0;
	public GameWindows(String title, int width, int height, int fps) {
		super(title, width, height, fps);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
	}
	@Override
	protected void onMouseEvent(int key, int x, int y) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onKeyEvent(int key) {
		// TODO Auto-generated method stub
		Tank myTank = MainControl.myTank1;
		Tank myTank2 = MainControl.myTank2;	
		if(key == Keyboard.KEY_W) {
			myTank.moveTank(myTank.MOVE_UP);
		}else if(key == Keyboard.KEY_S) {
			myTank.moveTank(myTank.MOVE_DOWN);
		}else if(key == Keyboard.KEY_A) {
			myTank.moveTank(myTank.MOVE_LEFT);
		}else if(key == Keyboard.KEY_D) {
			myTank.moveTank(myTank.MOVE_RIGHT);
		}else if(key == Keyboard.KEY_UP) {
			myTank2.moveTank(myTank2.MOVE_UP);
		}else if(key == Keyboard.KEY_DOWN) {
			myTank2.moveTank(myTank2.MOVE_DOWN);
		}else if(key == Keyboard.KEY_LEFT) {
			myTank2.moveTank(myTank2.MOVE_LEFT);
		}else if(key == Keyboard.KEY_RIGHT) {
			myTank2.moveTank(myTank2.MOVE_RIGHT);
		}
		if(key == Keyboard.KEY_RETURN) {
			MainControl.EnterSend();
		}
		if(key == Keyboard.KEY_ESCAPE) {
			MainControl.EscStop();
		}
		long thisTime = (long) System.currentTimeMillis();
		if(key == Keyboard.KEY_NUMPAD5 && !myTank2.isDestory()
				&& thisTime - last_Tank2_bullet_time > (long) Config.BULLET_SEND_CD) {
			last_Tank2_bullet_time = thisTime;
			MainControl.addBullet(new Bullet(myTank2,TankBulletType.Friendly));
		}else if(key == Keyboard.KEY_SPACE && !myTank.isDestory()
				&& thisTime - last_Tank1_bullet_time > (long) Config.BULLET_SEND_CD) {
			last_Tank1_bullet_time = thisTime;
			MainControl.addBullet(new Bullet(myTank,TankBulletType.Friendly));
		}
	}
	@Override
	protected void onDisplayUpdate() {
		// TODO Auto-generated method stub
		MainControl.Draw();
	}

}
