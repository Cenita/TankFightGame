package org.itheima.game;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public abstract class Window {
	private final String title;// 标题
	private final int width;// 屏幕宽度
	private final int height;// 屏幕的高�?
	private final int fps;// 每秒钟刷新的帧率

	private boolean running;
	private ScheduledExecutorService pool;
	private Map<Integer, ScheduledFuture<?>> tasks = new LinkedHashMap<>();

	public Window(String title, int width, int height, int fps) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = fps;
	}

	/**
	 * �?始游�?
	 */
	public final void start() {
		if (running) {
			return;
		}
		running = true;

		pool = Executors.newScheduledThreadPool(16);

		// 初始化窗�?
		initGL();

		// 创建时的回调
		onCreate();

		while (running && !Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			// 处理用户交互输入
			processInput();

			// 处理UI刷新显示
			processDisplay();

			Display.update();
			Display.sync(fps);
		}

		pool.shutdownNow();
		Display.destroy();
	}

	/**
	 * �?出游�?
	 */
	public final void stop() {
		if (!running) {
			return;
		}
		running = false;
	}

	private void initGL() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Display.setTitle(title);
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	private void processInput() {
		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				onMouseEvent(Mouse.getEventButton(), Mouse.getX(), Mouse.getY());
			}
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				// 按下
				int key = Keyboard.getEventKey();
				onKeyEvent(key);

				addLongPress(key);
			} else {
				// 释放
				int key = Keyboard.getEventKey();
				removeLongPress(key);
			}
		}
	}

	private void addLongPress(int key) {
		ScheduledFuture<?> future = tasks.get(key);
		if (future != null) {
			future.cancel(true);
			future = null;
		}

		future = pool.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				onKeyEvent(key);
			}
		}, 100, 100, TimeUnit.MILLISECONDS);

		tasks.put(key, future);
	}

	private void removeLongPress(int key) {
		ScheduledFuture<?> future = tasks.get(key);
		if (future != null) {
			future.cancel(true);
			future = null;
		}
	}

	private void processDisplay() {
		onDisplayUpdate();
	}

	/**
	 * 窗体创建时的回调
	 */
	protected abstract void onCreate();

	/**
	 * 鼠标down事件
	 * 
	 * @param key
	 *            0为左�? 1为右�?
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	protected abstract void onMouseEvent(int key, int x, int y);

	/**
	 * 按键事件
	 * 
	 * @param key
	 *            按键，{@code Keyboard.key_xx}
	 */
	protected abstract void onKeyEvent(int key);

	/**
	 * 当屏幕刷新时的回�?
	 */
	protected abstract void onDisplayUpdate();
}
