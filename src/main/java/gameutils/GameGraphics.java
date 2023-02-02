package gameutils;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GameGraphics {

    public final static TextCharacter linesGraphic = new TextCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK);

    public final static TextCharacter TextCharAllBlack = new TextCharacter(
            ' ',
            TextColor.ANSI.BLACK,
            TextColor.ANSI.BLACK);

    public final static TextCharacter LEFT_SIDE_SPACE = new TextCharacter(
            '/',
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK);

    public final static TextCharacter RIGHT_SIDE_SPACE = new TextCharacter(
            '\\',
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK);

    public final static TextCharacter MIDDLE_LINE_SPACE = new TextCharacter(
            '|',
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK);


    public final static TextCharacter MIDDLE_BOX_SPACE = new TextCharacter(
            '|',
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK);


    public final static TextCharacter MIDDLE_ROW_MINUS = new TextCharacter(
            '-',
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK);

    public final static TextCharacter MIDDLE_LEFT = new TextCharacter(
            '.',
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK);

    public final static TextCharacter MIDDLE_RIGHT = new TextCharacter(
            '.',
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK);

    public static List<String> readFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder sb;
            List<String> listToPrint = new ArrayList<String>();
            int count = 0;
            while ((line = br.readLine()) != null) {
                sb = new StringBuilder();
                sb.append(line);
                listToPrint.add(count, sb.toString());
                count++;
            }
            modifyLength(listToPrint);
            return listToPrint;
        } catch (IOException e) {
            System.out.println("File not found!");
            return null;
        }
    }

    public static void modifyLength(List<String> list) {
        int lineWidth = maxLengthInList(list);
        int size = list.size();
        StringBuilder sb;
        int count = 0;
        for (int i = 0; i < size; i++) {
            sb = new StringBuilder();
            sb.append(list.get(i));
            while (sb.length() < lineWidth) {
                sb.append(" ");
            }
            list.set(i, sb.toString());
        }
    }

    static int maxLengthInList(List<String> list) {
        int max = list.get(0).length();
        int size = list.size();
        for (int i = 1; i < size; i++) {
            if (list.get(i).length() > max) {
                max = list.get(i).length();
            }
        }
        return max;
    }




}

