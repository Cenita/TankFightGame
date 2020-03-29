package Element;
import java.io.IOException;
import org.itheima.game.utils.DrawUtils;
import AbstractElement.*;
import Constant.*;
import GameMain.WallType;
public class Wall extends HIT {
	
	protected String image;
	public WallType wallType=WallType.NONE;
	public boolean isCanPass=false;
	public boolean isCanBreak=false;
	public void Draw() {
		try {
			DrawUtils.draw(image, wallPoint.x, wallPoint.y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
