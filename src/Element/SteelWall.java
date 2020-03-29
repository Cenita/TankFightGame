package Element;
import GameMain.WallType;
public class SteelWall extends Wall {
	public SteelWall() {
		// TODO Auto-generated constructor stub
		image = Constant.Config.IMAGE_WALL_STEEL;
		wallType = WallType.STEELWALL;
		super.HP = 999;
	}
}
