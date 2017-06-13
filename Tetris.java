package tetris;

/**
 * Created by bwbecker on 2016-09-19.
 */

import javax.swing.*;

public class Tetris extends JFrame{

    public Tetris(int fps, double speed, String sequence) {
        super("Tetris");
        Tetrisboard TW = new Tetrisboard(fps, speed, sequence);;
        add(TW);
        setSize(500, 520);
        setLocation(200, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


    }
}
