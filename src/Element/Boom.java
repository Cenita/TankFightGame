package Element;

import java.io.IOException;
import org.itheima.game.utils.DrawUtils;
import Constant.Config;
public class Boom {
	private int x,y;
	private int count = 0;
	private int Boomlevel = 3;
	private boolean isDestory = false;
	private String BoomImage = Config.IMAGE_BLAST[0];
	public Boom(int x,int y,int BoomLevel) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.Boomlevel = BoomLevel;
	}
	public void updateBoom() {
		if(!isDestory)
			count+=1;
		BoomImage = Config.IMAGE_BLAST[count%Config.BOOM_SPEED];
		if(count%Config.BOOM_SPEED==Boomlevel) {
			isDestory=true;
		}
	}
	public boolean isDestory() {
		return isDestory;
	}
	public void Draw() {
		try {
			updateBoom();
			DrawUtils.draw(BoomImage, x-Config.WALL_SIZE/2, y-Config.WALL_SIZE/2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
