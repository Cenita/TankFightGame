package Element;
import Constant.*;
import GameMain.WallType;
public class WaterWall extends Wall{
	public WaterWall() {
		// TODO Auto-generated constructor stub
		image = Constant.Config.IMAGE_WALL_WATER;
		wallType = WallType.WATERWALL;
	}
}
