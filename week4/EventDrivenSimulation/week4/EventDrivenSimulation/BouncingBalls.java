package week4.EventDrivenSimulation;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class BouncingBalls {
    public static void main(String[] args) {
        int N = 50;//Integer.parseInt(args[0]);
        Ball[] balls = new Ball[N];
        for (int i = 0; i < N; i++)
            balls[i] = new Ball();
        while (true) {
            StdDraw.clear();
            for (int i = 0; i < N; i++) {
                balls[i].move(0.005);
                balls[i].draw();
            }
            StdDraw.show(50);
        }
    }
}