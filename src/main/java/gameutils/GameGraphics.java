package gameutils;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

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

}

