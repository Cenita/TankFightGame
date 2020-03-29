package Element;

import java.util.Random;

import Constant.Config;
import Constant.TankBulletType;

public class TankAI extends Tank{
	private int AISPEED = 2;
	private long shotlastTime = 0;
	private long movelastTime = System.currentTimeMillis();
	private int AISHOTSPEED = Config.TANKAI_SHOT_TIME;
	private int move_status = MOVE_UP;
	public TankAI(int dirction, int start_x, int start_y, int TankType) {
		super(dirction, start_x, start_y, TankType);
		// TODO Auto-generated constructor stub
		move_status = dirction;
	}
	public int getCurrentSpeed(int move_status) {
		return AISPEED;
	}
	public void upDateGrand() {
		if(!moveTank(move_status)) {
			Random r = new Random();
			move_status = r.nextInt(4)+1;
		}
		if(System.currentTimeMillis()-shotlastTime>=AISHOTSPEED) {
			AISHOTSPEED = new Random().nextInt(Config.TANKAI_SHOT_END_TIME)+Config.TANKAI_SHOT_TIME;
			shotlastTime = System.currentTimeMillis();
			mainController.addBullet(new Bullet(this,TankBulletType.Enemy));
		}
		if(System.currentTimeMillis()-movelastTime>=2000) {
			movelastTime = System.currentTimeMillis();
			Random r = new Random();
			move_status = r.nextInt(4)+1;
		}
	}
}
