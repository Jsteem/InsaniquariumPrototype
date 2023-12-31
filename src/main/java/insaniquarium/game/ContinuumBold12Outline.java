package insaniquarium.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContinuumBold12Outline {
    private GameImage fontSprite;


    private int[][] recList =
            {{   0,  0, 16, 22}, {  18,  0, 15, 22}, {  35,  0, 14, 22}, {  51,  0, 15, 22},
            {  68,  0, 14, 22}, {  84,  0, 14, 22}, { 100,  0, 15, 22}, { 117,  0, 15, 22},
            { 134,  0, 10, 22}, { 146,  0, 11, 22}, { 159,  0, 15, 22}, { 176,  0, 14, 22},
            { 192,  0, 18, 22}, { 212,  0, 15, 22}, { 229,  0, 15, 22}, { 246,  0, 15, 22},
            { 263,  0, 15, 22}, { 280,  0, 15, 22}, { 297,  0, 14, 22}, { 313,  0, 14, 22},
            { 329,  0, 15, 22}, { 346,  0, 16, 22}, { 364,  0, 19, 22}, { 385,  0, 16, 22},
            { 403,  0, 16, 22}, { 421,  0, 14, 22}, { 437,  0, 14, 22}, { 453,  0, 14, 22},
            { 469,  0, 13, 22}, { 484,  0, 14, 22}, { 500,  0, 14, 22}, { 516,  0, 11, 22},
            { 529,  0, 14, 22}, { 545,  0, 14, 22}, { 561,  0,  9, 22}, { 572,  0, 11, 22},
            { 585,  0, 14, 22}, { 601,  0,  9, 22}, { 612,  0, 18, 22}, { 632,  0, 14, 22},
            { 648,  0, 14, 22}, { 664,  0, 14, 22}, { 680,  0, 14, 22}, { 696,  0, 11, 22},
            { 709,  0, 13, 22}, { 724,  0, 11, 22}, { 737,  0, 14, 22}, { 753,  0, 15, 22},
            { 770,  0, 17, 22}, { 789,  0, 14, 22}, { 805,  0, 14, 22}, { 821,  0, 14, 22},
            { 837,  0, 15, 22}, { 854,  0,  9, 22}, { 865,  0, 14, 22}, { 881,  0, 14, 22},
            { 897,  0, 15, 22}, { 914,  0, 14, 22}, { 930,  0, 15, 22}, { 947,  0, 14, 22},
            { 963,  0, 15, 22}, { 980,  0, 15, 22}, { 997,  0, 11, 22}, {1010,  0, 10, 22},
            {1022,  0, 17, 22}, {1041,  0, 17, 22}, {1060,  0, 14, 22}, {1076,  0, 20, 22},
            {1098,  0, 14, 22}, {1114,  0, 17, 22}, {1133,  0, 13, 22}, {1148,  0, 11, 22},
            {1161,  0, 11, 22}, {1174,  0, 11, 22}, {1187,  0, 15, 22}, {1204,  0, 16, 22},
            {1222,  0, 15, 22}, {1239,  0, 10, 22}, {1251,  0, 10, 22}, {1263,  0, 10, 22},
            {1275,  0, 10, 22}, {1287,  0,  9, 22}, {1298,  0, 11, 22}, {1311,  0,  9, 22},
            {1322,  0, 15, 22}, {1339,  0,  9, 22}, {1350,  0, 15, 22}, {1367,  0, 15, 22},
            {1384,  0, 14, 22}};

    private char[] charList = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '`', '!',
            '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '[', ']', ';',
            ':','\'', '"', ',', '<', '.', '>', '/', '?'};

    private int[] widthList = {
            10,  10,   9,  10,   9,   8,   9,  10,   4,   5,   9,   8,  13,  10,   9,   9,
            9,   9,   8,   8,  10,  10,  13,  10,  10,   9,   8,   9,   7,   9,   8,   4,
            9,   8,   4,   4,   8,   4,  12,   8,   9,   8,   8,   4,   8,   4,   8,   8,
            11,   8,   8,   8,   9,

            4,

            9,   9,   9,   9,   9,   9,   9,   9,   6,   4,
            12,  11,   8,  14,   9,  11,   7,   5,   5,   5,   9,  11,   9,   4,   4,   4,
            4,   3,   6,   3,   9,   3,   9,   9,   8
    };

    private Map<Character, int[]> characterToData;
    public ContinuumBold12Outline(){
        fontSprite = new GameImage("ContinuumBold12outline.gif");

        characterToData = new HashMap<Character, int[]>();
        for(int i = 0; i < charList.length; i++){
            char c = charList[i];
            int[] boundingBox = recList[i];
            int width = widthList[i];
            int[] data = new int[5];
            for(int j = 0; j < 4; j++){
                data[j] = boundingBox[j];
            }
            data[4] = width;
            characterToData.put(c, data);
            //System.out.println("Character: " + c + " Data: " + Arrays.toString(data) + "pos: " + (i+1));
        }

    }

    public void drawString(GraphicsContext gc, String str, double x, double y, float percentage){
        char[] charArray = str.toCharArray();
        double dx = x;
        for (int i = 0; i < charArray.length; i++) {

            char c = charArray[i];
            if(characterToData.containsKey(c)){
                int[] data = characterToData.get(c);
                //System.out.println("Character: " + c + " Data: " + Arrays.toString(data));
                gc.drawImage(fontSprite.getImage(), data[0], data[1], data[2], data[3], dx, y, data[2] * percentage, data[3] * percentage);
                dx += data[4] * percentage;
            }
            else{
                dx += 4;
            }
        }
    }
    public void drawStringCenteredX(GraphicsContext gc, String str, double x, double y, float percentage){
        int width = 0;
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if(characterToData.containsKey(c)) {
                width += characterToData.get(c)[4];
            }
            else{
                width += 4;
            }
        }


        double dx = x - width/2;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if(characterToData.containsKey(c)){
                int[] data = characterToData.get(c);
                //System.out.println("Character: " + c + " Data: " + Arrays.toString(data));
                gc.drawImage(fontSprite.getImage(), data[0], data[1], data[2], data[3], dx, y, data[2] * percentage, data[3] * percentage);
                dx += data[4] * percentage;
            }
            else{
                dx += 4;
            }
        }
    }

    public void drawStringCenteredLeftX(GraphicsContext gc, String str, double x, double y, float percentage) {
        int width = 0;
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if(characterToData.containsKey(c)) {
                width += characterToData.get(c)[4];
            }
            else{
                width += 4;
            }
        }

    }
}
