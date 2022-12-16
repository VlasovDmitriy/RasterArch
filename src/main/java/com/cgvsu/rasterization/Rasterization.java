package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    public static void draw(
            final GraphicsContext graphicsContext,
            final int x0, final int y0, final int rad,
            final float startAngle, final float endAngle,
            final Color color1, final Color color2) {

        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int x = 0, y = rad, sigma = 0, delta = 2 - 2 * rad;

        while (y >= 0) {

            final int redStart = (int) (color1.getRed() * 255);
            final int greenStart = (int) (color1.getGreen() * 255);
            final int blueStart = (int) (color1.getBlue() * 255);

            final int redEnd = (int) (color2.getRed() * 255);
            final int greenEnd = (int) (color2.getGreen() * 255);
            final int blueEnd = (int) (color2.getBlue() * 255);

            final float currentAngle1q = findAngle(x, y);
            final float currentAngle2q = (float) Math.PI - currentAngle1q;
            final float currentAngle3q = (float) Math.PI + currentAngle1q;
            final float currentAngle4q = 2 * (float) Math.PI - currentAngle1q;

            if (startAngle <= currentAngle1q && currentAngle1q <= endAngle) {
                float passedPath = (currentAngle1q - startAngle) / (endAngle - startAngle);

                int re = (int) (redStart + (redEnd - redStart) * passedPath);
                int gr = (int) (greenStart + (greenEnd - greenStart) * passedPath);
                int bl = (int) (blueStart + (blueEnd - blueStart) * passedPath);

                pixelWriter.setColor(x0 + x, y0 - y, Color.rgb( re,  gr,  bl));
            }

            if (startAngle <= currentAngle2q && currentAngle2q <= endAngle) {
                float passedPath = (currentAngle2q - startAngle) / (endAngle - startAngle);

                int re = (int) (redStart + (redEnd - redStart) * passedPath);
                int gr = (int) (greenStart + (greenEnd - greenStart) * passedPath);
                int bl = (int) (blueStart + (blueEnd - blueStart) * passedPath);

                pixelWriter.setColor (x0 - x, y0 - y, Color.rgb( re,  gr,  bl));
            }

            if (startAngle <= currentAngle3q && currentAngle3q <= endAngle) {
                float passedPath = (currentAngle3q - startAngle) / (endAngle - startAngle);

                int re = (int) (redStart + (redEnd - redStart) * passedPath);
                int gr = (int) (greenStart + (greenEnd - greenStart) * passedPath);
                int bl = (int) (blueStart + (blueEnd - blueStart) * passedPath);

                pixelWriter.setColor(x0 - x, y0 + y, Color.rgb( re,  gr,  bl));
            }

            if (startAngle <= currentAngle4q && currentAngle4q <= endAngle) {
                float passedPath = (currentAngle4q - startAngle) / (endAngle - startAngle);

                int re = (int) (redStart + (redEnd - redStart) * passedPath);
                int gr = (int) (greenStart + (greenEnd - greenStart) * passedPath);
                int bl = (int) (blueStart + (blueEnd - blueStart) * passedPath);

                pixelWriter.setColor(x0 + x, y0 + y, Color.rgb( re,  gr,  bl));
            }

            sigma = 2 * (delta + y) - 1;
            if (delta < 0 && sigma <= 0) {
                x++;
                delta += x + 1;
            } else if (delta > 0 && sigma > 0) {
                y--;
                delta -= y + 1;
            } else {
                x++;
                delta += x - y;
                y--;
            }
        }
    }

    private static float findAngle(final int x, final int y) {
        return (float) Math.atan(y / (float) x);
    }
}
