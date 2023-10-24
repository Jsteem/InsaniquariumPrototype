package insaniquarium.game;

import javafx.embed.swing.SwingFXUtils;
import insaniquarium.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameImage {
    private javafx.scene.image.Image image;
    private static String path = "../images/";
    public GameImage(String fileName){

        String path = this.path + fileName;
        String pathMask = this.path + "_" + fileName;
        InputStream imageFile = Main.class.getResourceAsStream(path);
        InputStream maskFile = Main.class.getResourceAsStream(pathMask);
        if (maskFile == null) {
            //try other path
            String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
            pathMask = this.path + tokens[0] + "_." + tokens[1];
            maskFile = Main.class.getResourceAsStream(pathMask);
        }

        try {
            BufferedImage imageOriginal = ImageIO.read(imageFile);
            BufferedImage mask = ImageIO.read(maskFile);
            BufferedImage combined = new BufferedImage(mask.getWidth(), mask.getHeight(), Transparency.TRANSLUCENT);


            for (int x = 0; x < combined.getWidth(); x++) {
                for (int y = 0; y < combined.getHeight(); y++) {
                    int masked = (imageOriginal.getRGB(x, y) & 0x00FFFFFF ) | ((mask.getRGB(x, y)) << 24);
                    combined.setRGB(x, y, masked);
                }
            }
            this.image = SwingFXUtils.toFXImage(combined, null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public javafx.scene.image.Image getImage(){
        return this.image;
    }
}
