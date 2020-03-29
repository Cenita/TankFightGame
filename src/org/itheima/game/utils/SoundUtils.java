package org.itheima.game.utils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SoundUtils {
	private static Map<String, Audio> map = new LinkedHashMap<>();

	private SoundUtils() {
	}

	/**
	 * 播放音乐，只播放�?�?
	 * 
	 * @param res
	 *            音乐文件路径
	 * @throws IOException
	 *             资源无法找到时的异常
	 */
	public static void play(String res) throws IOException {
		play(res, false);
	}

	/**
	 * 播放音乐
	 * 
	 * @param res
	 *            音乐文件路径
	 * @param repeat
	 *            true �?直循环播放音乐，false 只播放一�?
	 * @throws IOException
	 *             资源无法找到时的异常
	 */
	public static void play(String res, boolean repeat) throws IOException {
		String key = getKey(res);
		Audio audio = map.get(key);
		if (audio == null) {
			String format = getFormat(res);
			audio = AudioLoader.getAudio(format, ResourceLoader.getResourceAsStream(res));
			map.put(key, audio);
		}
		audio.playAsSoundEffect(1.0f, 1.0f, repeat);
	}

	/**
	 * 停止播放音乐
	 * 
	 * @param format
	 *            音乐格式 WAV OGG �?
	 * @param res
	 *            音乐文件路径
	 */
	public static void stop(String res) {
		String key = getKey(res);
		Audio audio = map.get(key);
		if (audio == null) {
			return;
		}
		if (audio.isPlaying()) {
			audio.stop();
		}
	}

	private static String getKey(String res) {
		return res;
	}

	private static String getFormat(String res) {
		if (res == null) {
			return null;
		}
		int index = res.lastIndexOf(".");
		if (index == -1) {
			return null;
		}
		return res.substring(index + 1).toUpperCase();
	}
}
