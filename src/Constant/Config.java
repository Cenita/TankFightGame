package Constant;

public interface Config {
	public static final String TITLE = "Ì¹¿Ë´óÕ½";
	public static final int GAME_WINDOWS_WIDTH = 1280;
	public static final int GAME_WINDOWS_HEIGHT = 640;
	public static final int TANK_SPEED = 20;
	public static final int TANK_ADD_STEP = 1;
	public static final int TANK_MAX_SPEED = 20;
	public static final int TANK_SIZE = 64;
	public static final int WALL_SIZE = 64;
	public static final int GAME_WIDTH_BLOCK = GAME_WINDOWS_WIDTH / WALL_SIZE;
	public static final int GAME_HEIGHT_BLOCK = GAME_WINDOWS_HEIGHT / WALL_SIZE;
	public static final int BULLET_SPEED = 20;
	public static final int BULLET_SEND_CD = 150;
	public static final int BOOM_SPEED = 10000;
	public static final int WALL_HP = 5;
	public static final int TANK_HP = 7;
	public static final int TANKAI_SHOT_TIME = 300;
	public static final int TANKAI_SHOT_END_TIME = 200;
	public static final String[] IMAGE_TANK1 = {"res//img//tank_u.gif",
			"res//img//tank_d.gif",
			"res//img//tank_l.gif",
			"res//img//tank_r.gif"
	};
	public static final String IMAGE_TANK2[] = {"res//img//enemy_1_u.gif",
			"res//img//enemy_1_d.gif",
			"res//img//enemy_1_l.gif",
			"res//img//enemy_1_r.gif"
	};
	public static final String IMAGE_TANK3[] = {"res//img//friend_1_u.png",
			"res//img//friend_1_d.png",
			"res//img//friend_1_l.png",
			"res//img//friend_1_r.png"
	};
	public static final String IMAGE_WALL_STEEL = "res//img//steel.gif";
	public static final String IMAGE_WALL_WATER = "res//img//water.gif";
	public static final String IMAGE_WALL_BRICK = "res//img//wall.gif";
	public static final String IMAGE_WALL_GRASS = "res//img//grass.gif";
	public static final String[] IMAGE_BLAST = {
			"res//img//blast_1.gif",
			"res//img//blast_2.gif",
			"res//img//blast_3.gif",
			"res//img//blast_4.gif",
			"res//img//blast_5.gif",
			"res//img//blast_6.gif",
			"res//img//blast_7.gif",
			"res//img//blast_8.gif"
	};
	public static final String IMAGE_BULLET_LEFT = "res//img//bullet_r.gif";
	public static final String IMAGE_BULLET_RIGHT = "res//img//bullet_l.gif";
	public static final String IMAGE_BULLET_UP = "res//img//bullet_d.gif";
	public static final String IMAGE_BULLET_DOWN = "res//img//bullet_u.gif";
	public static final String IMAGE_GAME_START = "res//img//Game_start.png";
	public static final String IMAGE_GAME_OVER = "res//img//Game_over.png";
	public static final String IMAGE_GAME_WIN = "res//img//Game_win.png";
	public static final String IMAGE_GAME_STOP = "res//img//Game_stop.png";
}
