package com.example.shudugame;

import android.util.Log;

import java.util.Random;

public class sudu_16gongge_algorithm_shape {


    static final int SIZE = 16;
    static final int CELL_SIZE = 4;
    static final int LEVEL_MAX = 10;
    static int[][] suduAry;
    static int[][] suduAry2 ;

    /**
     * 生成数独
     *
     * @param level 难度级别 1~10
     * @return
     */
    public static int[][] generate(int level) {
        //生成数独，存在suduAry数组中
        suduAry = new int[SIZE][SIZE];
        suduAry2= new int[SIZE][SIZE];
        String str="BF423C8E1D6G957A" +
                "A815DG27BCF9436E" +
                "D63E915A4278CBFG" +
                "9G7CB64FA35E2D18" +
                "F261AEC97B843G5D" +
                "5EG463D89AC1B72F" +
                "8ACD527BGF3619E4" +
                "3B97F41GD5E268AC" +
                "GCEB2F9138A574D6" +
                "15F6EAG4279D8C3B" +
                "7DA8C5B364GFE192" +
                "4329786DCE1BAFG5" +
                "E95F17A28G4CD6B3" +
                "C1DA49E5F6B3G287" +
                "2483GBF6E1D75AC9" +
                "67BG8D3C592AFE41";
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                int a=str.charAt(i*SIZE+j)-'0';
                if(a>=17)
                {
                    suduAry[i][j]=a-7;
                }
                else{
                    suduAry[i][j]=a;
                }
            }

//        Random random = new Random();
//        int n = random.nextInt(16) + 1;
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                int p = Math.floorDiv(i, CELL_SIZE);
//                int q = Math.floorDiv(j, CELL_SIZE);
//                for (int k = 0; k < SIZE; k++) {
//                    if (checkColumn(n, j) && checkRow(n, i) && checkNineCells(n, p, q)) {
//                        suduAry[i][j] = n;
//                        break;
//                    } else {
//                        n = n % SIZE + 1;
//                    }
//                }
//            }
//            n = n % SIZE + 1;
//        }

        upset();
        for (int i = 0; i < suduAry.length; i++)
            for(int j=0;j<suduAry.length; j++)
            {
                suduAry2[i][j] = suduAry[i][j];
            }

        maskCells(level);
        return suduAry;
    }

    private static void maskCells(int level) {
        int min, max;
        Log.d(":sssssssssssss", String.valueOf(level));
        if (level == 1) {
            min = 60;
            max = 15;
        } else if (level == 2) {
            min = 80;
            max = 10;
        } else if (level == 3) {
            min = 100;
            max = 10;
        } else if(level==4){
            min = 120;
            max = 5;
        }else {
            min = 1;
            max = 0;
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
        int i = 0;
        //按行交换
        for (i = 0; i < SIZE*2; i++) {
            int n = random.nextInt(CELL_SIZE);
            int q= random.nextInt(SIZE);
            int p =Math.floorDiv(q, CELL_SIZE)*CELL_SIZE + n;
            if(q==p){
                i=i-1;
                break;
            }
            else {
                for (int j = 0; j < SIZE; j++) {
                    int tmp = suduAry[q][j];
                    suduAry[q][j] = suduAry[p][j];
                    suduAry[p][j] = tmp;
                }
            }
        }
        //按列交换
        for ( i = 0; i < SIZE*2; i++) {
            int n = random.nextInt(CELL_SIZE);
            int q= random.nextInt(SIZE);
            int p =Math.floorDiv(q, CELL_SIZE)*CELL_SIZE+ n;
            for (int j = 0; j < SIZE; j++) {
                int tmp = suduAry[j][q];
                suduAry[j][q] = suduAry[j][p];
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
        int sx = x * 4, sy = y * 4;

        for (int i = sx; i < sx + 4; i++) {
            for (int j = sy; j < sy + 4; j++) {
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
