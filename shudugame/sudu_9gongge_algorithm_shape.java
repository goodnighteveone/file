package com.example.shudugame;

import java.util.Random;

public class sudu_9gongge_algorithm_shape {
    static final int SIZE = 9;
    static final int CELL_SIZE = 3;
    static final int LEVEL_MAX = 10;
    static int[][] suduAry;

    /**
     * 生成数独
     *
     * @param level 难度级别 1~10
     * @return
     */
    public static int[][] generate(int level) {
        suduAry=new int[SIZE][SIZE];
        Random random = new Random();
        int n = random.nextInt(9) + 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int p = Math.floorDiv(i, CELL_SIZE);
                int q = Math.floorDiv(j, CELL_SIZE);
                for (int k = 0; k < SIZE; k++) {
                    if (checkColumn(n, j) && checkRow(n, i) && checkNineCells(n, p, q)) {
                        suduAry[i][j] = n;
                        break;
                    } else {
                        n = n % SIZE + 1;
                    }
                }
            }
            n = n % SIZE + 1;
        }
        //upset();
        maskCells(level);

        return suduAry;
    }

    private static void maskCells(int level) {
        int min, max;
        if (level == 1) {
            min = 20;
            max = 15;
        } else if (level == 2) {
            min = 40;
            max = 10;
        } else if (level == 3) {
            min = 50;
            max = 10;
        } else {
            min = 60;
            max = 5;
        }

        Random random = new Random();
        int count = random.nextInt(max) + min;
        for (int i = 0; i < count; i++) {
            do {
                int n = random.nextInt(SIZE);
                int m = random.nextInt(SIZE);
                if (suduAry[n][m] > 0) {
                    suduAry[n][m] = 0;
                    break;
                }
            } while (true);
        }
    }

    /**
     * 随机打乱顺序
     */
    private static void upset() {
        Random random = new Random();
        //按行交换
        for (int i = 0; i < SIZE; i++) {
            int n = random.nextInt(CELL_SIZE);
            int p = random.nextInt(CELL_SIZE) * CELL_SIZE + n;
            for (int j = 0; j < SIZE; j++) {
                int tmp = suduAry[i][j];
                suduAry[i][j] = suduAry[p][j];
                suduAry[p][j] = tmp;
            }
        }
        //按列交换
        for (int i = 0; i < SIZE; i++) {
            int n = random.nextInt(CELL_SIZE);
            int p = random.nextInt(CELL_SIZE) * CELL_SIZE + n;
            for (int j = 0; j < SIZE; j++) {
                int tmp = suduAry[j][i];
                suduAry[j][i] = suduAry[j][p];
                suduAry[j][p] = tmp;
            }
        }
    }

    /**
     * 检查某行
     *
     * @param n
     * @param row
     * @return
     */
    private static boolean checkRow(int n, int row) {
        boolean result = true;

        for (int i = 0; i < SIZE; i++) {
            if (suduAry[row][i] == n) {
                result = false;
                break;
            }
        }

        return result;
    }

    /**
     * 检查某列
     *
     * @param n
     * @param col
     * @return
     */
    private static boolean checkColumn(int n, int col) {
        boolean result = true;
        for (int i = 0; i < SIZE; i++) {
            if (suduAry[i][col] == n) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 检查小九宫格
     *
     * @param n
     * @param x
     * @param y
     * @return
     */
    private static boolean checkNineCells(int n, int x, int y) {
        boolean result = true;
        int sx = x * 3, sy = y * 3;

        for (int i = sx; i < sx + 3; i++) {
            for (int j = sy; j < sy + 3; j++) {
                if (suduAry[i][j] == n) {
                    result = false;
                    break;
                }
            }
            if (!result) break;
        }

        return result;
    }
}

