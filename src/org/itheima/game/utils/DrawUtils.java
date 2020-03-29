package org.itheima.game.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class DrawUtils {
	private static Map<String, Texture> map = new LinkedHashMap<>();

	private DrawUtils() {
	}

	/**
	 * 获得图片的大�?
	 * 
	 * @param imagePath
	 *            图片路径
	 * @return 返回长度�?2的数组，0为宽�?1为高
	 * @throws IOException
	 *             图片不存在时的异�?
	 */
	public static int[] getSize(String imagePath) throws IOException {
		String key = getKey(imagePath);
		Texture texture = map.get(key);

		if (texture == null) {
			String format = getFormat(imagePath);
			texture = TextureLoader.getTexture(format, new FileInputStream(new File(imagePath)));
			map.put(key, texture);
		}

		int width = (int) (texture.getImageWidth() + 0.5f);
		int height = (int) (texture.getImageHeight() + 0.5f);

		return new int[] { width, height };
	}

	/**
	 * 绘制图片
	 * 
	 * @param imagePath
	 *            图片路径
	 * @param x
	 *            图片绘制时的x坐标
	 * @param y
	 *            图片绘制时的y坐标
	 * @throws IOException
	 *             图片不存在时的异�?
	 */
	public static void draw(String imagePath, int x, int y) throws IOException {
		String key = getKey(imagePath);
		Texture texture = map.get(key);

		if (texture == null) {
			String format = getFormat(imagePath);
			texture = TextureLoader.getTexture(format, new FileInputStream(new File(imagePath)));
			map.put(key, texture);
		}

		int width = texture.getImageWidth();
		int height = texture.getImageHeight();

		width = texture.getTextureWidth();
		height = texture.getTextureHeight();

		Color.white.bind();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(x, y);

			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(x + width, y);

			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(x + width, y + height);

			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(x, y + height);

		}
		GL11.glEnd();
	}

	private static String getKey(String imagePath) {
		return imagePath;
	}

	private static String getFormat(String imagePath) {
		if (imagePath == null) {
			return null;
		}
		int index = imagePath.lastIndexOf(".");
		if (index == -1) {
			return null;
		}
		return imagePath.substring(index + 1);
	}
}
