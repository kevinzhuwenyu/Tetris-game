package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.Event;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;


public class Tetrisboard extends JPanel implements ActionListener, KeyListener, MouseListener, MouseWheelListener {
    JButton start = new JButton("start");
    JButton pause = new JButton("pause");
    piece[] p = new piece[4];
    piece[] p_next1 = new piece[4];
    piece[] p_next2 = new piece[4];
    piece[] p_next3 = new piece[4];
    piece[] p_next4 = new piece[4];
    piece[] p_hold = new piece[4];


    String sequence;
    int current_piece;
    int hold_piece;
    int index;
    int index_next1;
    int index_next2;
    int index_next3;
    int index_next4;
    int level;
    boolean gamebegin;
    boolean hold;
    int[][] board = new int[10][24];
    Timer t;
    double speed;
    int fps;
    int score;
    boolean stop;



    public Tetrisboard(int fps, double speed, String sequence){

        this.sequence = sequence;
        this.fps = fps;
        this.speed = speed;
        stop = false;
        hold = false;
        index = 0;
        index_next1 = 0;
        index_next2 = 0;
        index_next3 = 0;
        index_next3 = 0;
        current_piece = 0;
        hold_piece = 0;
        score = 0;
        level = 1;
        setLayout(new FlowLayout(FlowLayout.TRAILING));
        add(start);
        add(pause);
        start.addActionListener(this);
        pause.addActionListener(this);
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);

        for(int i = 0; i < p.length; i++){
            p[i] = new piece();
            p_next1[i] = new piece();
            p_next2[i] = new piece();
            p_next3[i] =  new piece();
            p_next4[i] = new piece();
            p_hold[i] = new piece();
        }


        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 24; j++){
                board[i][j] = 0;
            }
        }
    }

    public void resetboard(){
        speed += (level-1);
        stop = false;
        hold = false;
        index = 0;
        index_next1 = 0;
        index_next2 = 0;
        index_next3 = 0;
        index_next3 = 0;
        current_piece = 0;
        hold_piece = 0;
        score = 0;
        level = 1;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 24; j++){
                board[i][j] = 0;
            }
        }

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
            Thread.sleep(1000/fps); // set fps
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(stop == false){
            for(int x=0;x<10;x++)
            {
                for(int y=0;y<24;y++)
                {
                    g.drawRect(120+x*20,10+y*20,20,20);
                }
            }

            g.drawString("HOLD", 20, 60);
            g.drawString("NEXT", 390, 60 );
            g.drawString("SCORE: ", 350, 480 );
            g.drawString("LEVEL: ", 20, 480);

            if (gamebegin) {

                if(hold == true){
                    g.setColor(Color.yellow);
                    for (int i = 0; i < 4; i++){
                        g.fillRect(5 + p_hold[i].x*20, 80 + p_hold[i].y * 20, 20, 20);
                    }
                }

                for (int i = 0; i < 4; i++) { // current piece
                    g.setColor(Color.BLUE);
                    g.fillRect(120 + p[i].x * 20, 10 + p[i].y * 20, 20, 20);
                }


                for (int i = 0; i < 4; i++) { // next piece
                    g.setColor(Color.red);
                    g.fillRect(345 + p_next1[i].x * 20, 90 + p_next1[i].y * 20, 20, 20);
                    g.fillRect(345 + p_next2[i].x * 20, 170 + p_next2[i].y * 20, 20, 20);
                    g.fillRect(345 + p_next3[i].x * 20, 250 + p_next3[i].y * 20, 20, 20);
                    g.fillRect(345 + p_next4[i].x * 20, 330 + p_next4[i].y * 20, 20, 20);
                }

                //g.setColor(Color.orange);
                g.setColor(Color.orange);
                for (int i = 0; i < 10; i++) { // draw board
                    for (int j = 0; j < 24; j++) {
                        if (board[i][j] == 1) {
                            g.fillRect(120 + i * 20, 10 + j * 20, 20, 20);
                        }
                    }
                }
                g.drawString(String.valueOf(score), 400, 480);
                g.drawString(String.valueOf(level), 70, 480);
            }
        }else{
            g.drawString("press p to continue.", 200, 230);
        }
    }

    public void getpiece(char shape, piece[] rvl) { // get the data of different shap
        switch (shape) {
            case 'I': //I shap
                rvl[0].x = 1;
                rvl[0].y = 0;
                rvl[1].x = 2;
                rvl[1].y = 0;
                rvl[2].x = 3;
                rvl[2].y = 0;
                rvl[3].x = 4;
                rvl[3].y = 0;
                break;
            case 'O': // box shap
                rvl[0].x = 2;
                rvl[0].y = 0;
                rvl[1].x = 3;
                rvl[1].y = 0;
                rvl[2].x = 2;
                rvl[2].y = 1;
                rvl[3].x = 3;
                rvl[3].y = 1;
                break;
            case 'L': // L shap
                rvl[0].x = 2;
                rvl[0].y = 0;
                rvl[1].x = 2;
                rvl[1].y = 1;
                rvl[2].x = 2;
                rvl[2].y = 2;
                rvl[3].x = 3;
                rvl[3].y = 2;
                break;
            case 'J': // L reverse
                rvl[0].x = 3;
                rvl[0].y = 0;
                rvl[1].x = 3;
                rvl[1].y = 1;
                rvl[2].x = 3;
                rvl[2].y = 2;
                rvl[3].x = 2;
                rvl[3].y = 2;
                break;
            case 'T': // T shap
                rvl[0].x = 3;
                rvl[0].y = 0;
                rvl[1].x = 2;
                rvl[1].y = 1;
                rvl[2].x = 3;
                rvl[2].y = 1;
                rvl[3].x = 4;
                rvl[3].y = 1;
                break;
            case 'S': // z shap
                rvl[0].x = 2;
                rvl[0].y = 0;
                rvl[1].x = 3;
                rvl[1].y = 0;
                rvl[2].x = 3;
                rvl[2].y = 1;
                rvl[3].x = 4;
                rvl[3].y = 1;
                break;
            case 'Z': // z reverse
                rvl[0].x = 3;
                rvl[0].y = 0;
                rvl[1].x = 4;
                rvl[1].y = 0;
                rvl[2].x = 2;
                rvl[2].y = 1;
                rvl[3].x = 3;
                rvl[3].y = 1;
                break;
        }
    }

    public boolean formpiece(piece[] p, int index){ //generate a random shap piece
        switch(sequence.charAt(index)){
            case 'I': //I shap
                getpiece('I', p);
                break;
            case 'O': // box shap
                getpiece('O', p);
                break;
            case 'L': // L shap
                getpiece('L', p);
                break;
            case 'J': // L reverse
                getpiece('J', p);
                break;
            case 'T': // T shap
                getpiece('T', p);
                break;
            case 'S': // z shap
                getpiece('S', p);
                break;
            case 'Z': // z reverse
                getpiece('Z', p);
                break;
        }

        for(int i = 0; i < 4; i++){
            if(out_boundary(p[i].x, p[i].y)){
                return false;
            }
        }
        return true;
    }

    public int newindex(int old_index, int new_index){

        if(old_index+1 >= sequence.length()){
            new_index = 0;
        }else{
            new_index = old_index + 1;
        }

        return new_index;
    }

    public boolean out_boundary(int x, int y){
        for(int i = 0; i < 4; i++){
            int tempx = p[i].x + x;
            int tempy = p[i].y + y;
            if(tempx < 0 || tempx >= 10 || tempy < 0 || tempy >= 24){
                return true;
            }if(board[tempx][tempy] == 1){
                return true;
            }
        }
        return false;
    }

    public void Move(int x, int y){
        if (out_boundary(x, y) == false) {
            for (int i = 0; i < 4; i++) {
                p[i].x += x;
                p[i].y += y;
            }
        }
        repaint();
    }

    public int delete_lines(){
        int num_line = 0;

        for(int i = 0; i < 24; i ++){
            int temp = 0;
            for(int j = 0; j < 10; j++){
                if(board[j][i] == 1){
                    temp += 1;
                }
            }

            if(temp == 10){
                num_line += 1;
                if(i > 0){

                    for(int m = i-1; m > 0; m--){
                        for(int n = 0; n < 10; n++){
                            board[n][m+1] = board[n][m];
                        }
                    }
                }
                repaint();
            }
        }
        return num_line;
    }

    public void fall(){
        if (out_boundary(0, 1) == false){
            for (int i = 0; i < 4; i++) {
                p[i].x += 0;
                p[i].y += 1;
            }
            repaint();

        }else{
            t.stop();
            for(int i = 0; i < 4; i++){
                board[p[i].x][p[i].y] = 1;
            }
            int line = delete_lines();
            if(line > 0){
                score = score + line * 100;
            }

            if(score >= 1000 * level){
                level += 1;
                speed -= 1;
                double time = speed/24 * 1000;
                t = new Timer((int)time , new newTimer());
                t.start();
            }

            t.start();

            if(!formpiece(p, index)){
                System.out.println("game over");
            }else{
                current_piece = index;

                index_next1 = newindex(index, index_next1);
                index_next2 = newindex(index_next1, index_next2);;
                index_next3 = newindex(index_next2, index_next3);
                index_next4 = newindex(index_next3, index_next4);
                formpiece(p_next1, index_next1);
                formpiece(p_next2, index_next2);
                formpiece(p_next3, index_next3);
                formpiece(p_next4, index_next4);
                index = newindex(index, index);

            }
            repaint();
        }
    }

    public void rotation(){
        piece[] p_temp = new piece[4];

        for(int i = 0; i < 4; i++){
            p_temp[i] = new piece();
            p_temp[i].x = p[i].x;
            p_temp[i].y = p[i].y;
        }



        int center_x = (p_temp[1].x + p_temp[2].x + p_temp[3].x + p_temp[0].x)/4;
        int center_y = (p_temp[1].y + p_temp[2].y + p_temp[3].y + p_temp[0].y)/4;

        for(int i = 0; i < 4; i++){
            p_temp[i].x = (center_x + center_y - p[i].y);
            p_temp[i].y = (center_y - center_x + p[i].x);
        }

        for(int i = 0; i < 4; i++){
            int x1 = p_temp[i].x;
            int y1 = p_temp[i].y;
            if(x1 < 0 || x1 >= 10 || y1 < 0 || y1 >= 24 || board[x1][y1] == 1){
                return;
            }
        }

        for(int i = 0; i < 4; i++){
            p[i].x = p_temp[i].x;
            p[i].y = p_temp[i].y;
        }
        repaint();

    }



    public void drop(){
        int y = 0;

        while(!out_boundary(0, y)){
            y = y + 1;
        }
        for(int i = 0; i < 4; i++){
            p[i].y = p[i].y + y - 1;
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start){
            if (e.getActionCommand().equals("start")){
                gamebegin = true;
                start.setText("exit");
                requestFocus();
                //rnum = r.nextInt(7);

                if (!formpiece(p, index)){
                    // game fail
                    return;
                }else{

                    
                    double time = speed/24 * 1000;
                    //System.out.println(speed);

                    t = new Timer((int)time , new newTimer());

                    t.start();
                    current_piece = index;
                    index_next1 = newindex(index, index_next1);
                    index_next2 = newindex(index_next1, index_next2);;
                    index_next3 = newindex(index_next2, index_next3);
                    index_next4 = newindex(index_next3, index_next4);
                    formpiece(p_next1, index_next1);
                    formpiece(p_next2, index_next2);
                    formpiece(p_next3, index_next3);
                    formpiece(p_next4, index_next4);
                    index = newindex(index, index);
                    repaint();
                }
            }else{
                gamebegin = false;
                System.exit(0);
            }

        }if (e.getSource() == pause){
            //System.exit(0);
            if (e.getActionCommand().equals("pause")) {
                stop = true;
                t.stop();
                pause.setText("continue");
            }else{
                stop = false;
                t.start();
                pause.setText("pause");
                requestFocus();
            }
            repaint();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gamebegin){
            if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                fall();
            }if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_1
                    || e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_9
                    || e.getKeyCode() == KeyEvent.VK_X) {
                rotation();
            }if(e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_Z ||
                    e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_7){
                rotation();
            }if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_4) {
                Move(-1, 0);
            }if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_6 ) {
                Move(1, 0);
            }if(e.getKeyCode() == KeyEvent.VK_P) {
                if (stop == false) {
                    stop = true;
                    t.stop();
                    pause.setText("continue");
                } else {
                    stop = false;
                    t.start();
                    pause.setText("pause");
                }
                repaint();
            }if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_8){
                drop();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();

        if(hold == false){
            for(int i = 0; i < 4; i++){
                if(x > 120 + p[i].x*20 && x <= 140 + p[i].x * 20 && y >= 10 + p[i].y*20 && y<= 30 + p[i].y * 20){
                    hold = true;
                }
            }
            if(hold == true){
                hold_piece = current_piece;
                formpiece(p_hold, current_piece);

                index_next1 = newindex(index, index_next1);
                index_next2 = newindex(index_next1, index_next2);;
                index_next3 = newindex(index_next2, index_next3);
                index_next4 = newindex(index_next3, index_next4);
                formpiece(p, index);
                formpiece(p_next1, index_next1);
                formpiece(p_next2, index_next2);
                formpiece(p_next3, index_next3);
                formpiece(p_next4, index_next4);
                index = newindex(index, index);
            }
            repaint();
        }else{
            for(int i = 0; i < 4; i++){
                if(x > 5 + p_hold[i].x*20 && x <= 25+ p_hold[i].x * 20 &&
                        y >= 80 + p_hold[i].y*20 && y <= 100 + p_hold[i].y * 20){
                    hold = false;
                }
                if(hold == false){
                    formpiece(p, hold_piece);
                }
            }
            repaint();

        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        int m = e.getWheelRotation();
        if(m > 0){
            rotation();
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public class newTimer implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(gamebegin){
                fall();
            }

        }

    }
}
