package GameMain;
import Constant.Config;
public class StartMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameWindows MainWindows = new GameWindows(Config.TITLE, Config.GAME_WINDOWS_WIDTH, Config.GAME_WINDOWS_HEIGHT, 100);
		MainWindows.start();
	}

}
