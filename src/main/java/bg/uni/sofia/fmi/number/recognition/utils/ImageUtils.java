package bg.uni.sofia.fmi.number.recognition.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public final class ImageUtils {

	public static final Color white = Color.white;
	public static final int COLOR_RESTRICTION = 225;
	
	private static BufferedImage normalize(BufferedImage image) {
		image = removeWhiteTopRows(image);
		image = removeWhiteBottomRows(image);
		image = removeWhiteLeftCols(image);
		image = removeWhiteRightCol(image);
		return image;
	}

	public static List<BufferedImage> splitToDigits(BufferedImage image) {
		image = normalize(image);
		int col;
		List<BufferedImage> digits = new ArrayList<>();
		while ((col = getFirstWhiteCol(image)) != -1) {
			BufferedImage newImage = new BufferedImage(col, image.getHeight(), BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < image.getHeight(); i++) {
				for (int j = 0; j < col; j++) {
					newImage.setRGB(j, i, image.getRGB(j, i));
				}
			}
			digits.add(newImage);
			newImage = new BufferedImage(image.getWidth() - col, image.getHeight(), BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < image.getHeight(); i++) {
				for (int j = col; j < image.getWidth(); j++) {
					newImage.setRGB(j - col, i, image.getRGB(j, i));
				}
			}
			image = newImage;
			image = normalize(image);
		}
		digits.add(image);
		return digits;
	}

	public static void save(BufferedImage image, String imageExtension, File outputFile) {
		try {
			ImageIO.write(image, imageExtension, outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static BufferedImage removeWhiteTopRows(BufferedImage image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int numberOfTopWhiteRows = getNumberOfTopWhiteRows(image);
		BufferedImage newImage = new BufferedImage(imageWidth, imageHeight - numberOfTopWhiteRows,
				BufferedImage.TYPE_INT_RGB);
		for (int i = numberOfTopWhiteRows; i < imageHeight; i++) {
			for (int j = 0; j < imageWidth; j++) {
				newImage.setRGB(j, i - numberOfTopWhiteRows, image.getRGB(j, i));
			}
		}
		return newImage;
	}

	private static int getNumberOfTopWhiteRows(BufferedImage image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int numberOfTopWhiteRows = 0;
		for (int j = 0; j < imageHeight; j++) {
			for (int i = 0; i < imageWidth; i++) {
				int pixelColor = image.getRGB(i, j);
				if (new Color(pixelColor).getBlue() < 225 && new Color(pixelColor).getRed() < 225
						&& new Color(pixelColor).getGreen() < 225) {
					return numberOfTopWhiteRows;
				}
			}
			numberOfTopWhiteRows++;
		}
		return numberOfTopWhiteRows;
	}

	private static BufferedImage removeWhiteBottomRows(BufferedImage image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int numberOfBottomWhiteRows = getNumberOfBottomWhiteRows(image);
		BufferedImage newImage = new BufferedImage(imageWidth, imageHeight - numberOfBottomWhiteRows,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < imageHeight - numberOfBottomWhiteRows; i++) {
			for (int j = 0; j < imageWidth; j++) {
				newImage.setRGB(j, i, image.getRGB(j, i));
			}
		}
		return newImage;
	}

	private static int getNumberOfBottomWhiteRows(BufferedImage image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int numberOfBottomWhiteRows = 0;
		for (int j = imageHeight - 1; j > 0; j--) {
			for (int i = 0; i < imageWidth; i++) {
				int pixelColor = image.getRGB(i, j);
				if (new Color(pixelColor).getBlue() < 225 && new Color(pixelColor).getRed() < 225
						&& new Color(pixelColor).getGreen() < 225) {
					return numberOfBottomWhiteRows;
				}
			}
			numberOfBottomWhiteRows++;
		}
		return numberOfBottomWhiteRows;
	}

	private static BufferedImage removeWhiteLeftCols(BufferedImage image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int numberOfLeftWhiteCols = getNumberOfLeftWhiteCols(image);
		BufferedImage newImage = new BufferedImage(imageWidth - numberOfLeftWhiteCols, imageHeight,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < imageHeight; i++) {
			for (int j = numberOfLeftWhiteCols; j < imageWidth; j++) {
				newImage.setRGB(j - numberOfLeftWhiteCols, i, image.getRGB(j, i));
			}
		}
		return newImage;
	}

	private static int getNumberOfLeftWhiteCols(BufferedImage image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int numberOfLeftWhiteCols = 0;
		for (int j = 0; j < imageWidth; j++) {
			for (int i = 0; i < imageHeight; i++) {
				int pixelColor = image.getRGB(j, i);
				if (new Color(pixelColor).getBlue() < 225 && new Color(pixelColor).getRed() < 225
						&& new Color(pixelColor).getGreen() < 225) {
					return numberOfLeftWhiteCols;
				}
			}
			numberOfLeftWhiteCols++;
		}
		return numberOfLeftWhiteCols;
	}

	public static BufferedImage removeWhiteRightCol(BufferedImage image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int numberOfRightWhiteCols = getNumberOfRightWhiteCols(image);
		BufferedImage newImage = new BufferedImage(imageWidth - numberOfRightWhiteCols, imageHeight,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < imageHeight; i++) {
			for (int j = 0; j < imageWidth - numberOfRightWhiteCols; j++) {
				newImage.setRGB(j, i, image.getRGB(j, i));
			}
		}
		return newImage;
	}

	private static int getNumberOfRightWhiteCols(BufferedImage image) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int numberOfWhiteRightCols = 0;
		for (int j = imageWidth - 1; j >= 0; j--) {
			for (int i = 0; i < imageHeight; i++) {
				int pixelColor = image.getRGB(j, i);
				if (new Color(pixelColor).getBlue() < 225 && new Color(pixelColor).getRed() < 225
						&& new Color(pixelColor).getGreen() < 225) {
					return numberOfWhiteRightCols;
				}
			}
			numberOfWhiteRightCols++;
		}
		return numberOfWhiteRightCols;
	}

	private static boolean isWhiteCol(BufferedImage image, int col) {
		int imageHeight = image.getHeight();
		for (int i = 0; i < imageHeight; i++) {
			int pixelColor = image.getRGB(col, i);
			if (!isWhitePixel(pixelColor)) {
				return false;
			}
		}
		return true;
	}

	private static int getFirstWhiteCol(BufferedImage image) {
		int imageWidth = image.getWidth();
		for (int i = 0; i < imageWidth; i++) {
			if (isWhiteCol(image, i)) {
				return i;
			}
		}
		return -1;
	}
	
	private static boolean isWhitePixel(int color){
		Color pixelColor = new Color(color);
		if (pixelColor.getBlue() < 225 && pixelColor.getRed() < 225
				&& pixelColor.getGreen() < 225) {
			return false;
		}
		return true;
	}
}