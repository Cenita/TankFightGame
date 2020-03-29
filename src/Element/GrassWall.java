package Element;

import GameMain.WallType;

public class GrassWall extends Wall{
	public GrassWall() {
		// TODO Auto-generated constructor stub
		image = Constant.Config.IMAGE_WALL_GRASS;
		isCanPass=true;
		wallType = WallType.GRASSWALL;
	}
}
