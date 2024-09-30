package com.example.shudugame;

public class gamelogic_16gongge {
    //数独初始化的数据基础
    private final String str =
            "0F023C0E000G050A" +
            "A800DG00BC09406E" +
            "063091500270CBF0" +
            "0G00064F03502D10" +
            "006000C900843050" +
            "500403D00AC10000" +
            "00C0020BG0300004" +
            "0090040G05026000" +
            "GCE00F0030050006" +
            "10F00A0007900C30" +
            "7000C5B360GF0192" +
            "03297060000BAFG5" +
            "E00000A28G4C00B0" +
            "01DA000506B00007" +
            "2400G000E0D700C9" +
            "000G8D3C090AF041";

    private final String answer =
            "BF423C8E1D6G957A" +
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

    /**
     * 写了三个sudoku数组，可能难理解，介于要给玩家所选数组更替颜色，所以需要把初始数据和玩家所选数据分离，
     * 但是这两个用于界面的更新，不涉及的计算不可用数据，计算不可用数据需要它俩的结合，这个结合只涉及到逻辑计算，不涉及到界面，
     * 因此产生了三个数组。
     */
    //数独初始数据的存储
    protected static int[] sudoku = new int[16 * 16];
    //数独玩家所选数据的存储
    protected int sudoku1[] = new int[16 * 16];
    //数独全部数据的存储 初始+所选
    protected int sudoku2[] = new int[16 * 16];
    //当玩家看答案时，暂时存储玩家所选的数独数据
    protected int sudoku3[] = new int[16 * 16];
    //用于存储每个单元格已经不可用的数据
    private int used[][][] = new int[16][16][];

    //构造方法
    public gamelogic_16gongge() {
        //数据全部数据数组赋初值
        sudoku2 = fromPuzzleString(str);
        //数独初始数据数组赋值
        sudoku = fromPuzzleString(str);
        calculateAllUsedTiles();
    }

    public void getanswer(){
        for(int i=0;i<sudoku1.length;i++)
        {
            sudoku3[i]=sudoku1[i];
            sudoku1[i]=0;
        }
        sudoku=fromPuzzleString(answer);
    }

    public void continuegame()
    {
        for(int i=0;i<sudoku1.length;i++)
        {
            sudoku1[i]=sudoku3[i];
        }
        sudoku=fromPuzzleString(str);
    }

    //根据九宫格当中的坐标，返回该坐标数独中的数据
    private int getTile(int x, int y, int[] sudu) {
        int value = sudu[y * 16 + x];
        return value;
    }

    //给数独数组增加玩家选择的数据
    private void setTile(int x, int y, int value) {
        //把玩家所选数据增加到 数独玩家所选数据
        if (value >= 10) {
            sudoku1[y * 16 + x] = value + 7;
            //把玩家所选数据增加到 数独全部数据
            sudoku2[y * 16 + x] = value + 7;
        } else {
            sudoku1[y * 16 + x] = value;
            //把玩家所选数据增加到 数独全部数据
            sudoku2[y * 16 + x] = value;
        }
    }

    /**
     * 根据X轴和Y轴位置，返回参数中数独库的该位置应填的字符（初始数独和玩家所选数独都需要调用此方法）
     *
     * @param x X轴坐标
     * @param y Y轴坐标
     * @return 该填的数据
     */
    public String getTileString(int x, int y, int[] sudo) {
        int v = getTile(x, y, sudo);
        //如果是0，返回空
        if (v == 0) {
            return "";
        } else if (v >= 17) {
            return String.valueOf((char) (v + 48));
        }
        //如果不是0，绘制其本身数字
        else
            return String.valueOf(v);
    }

    /**
     * 根据一个数独的字符串基础数据，生成一个整形数组作为数独游戏的初始化数据
     *
     * @param str 基础数独字符串
     * @return 一个整形数组
     */
    private int[] fromPuzzleString(String str) {
        //定义一个整形数组，以字符串长度为其长度
        int[] sudo = new int[str.length()];
        //把字符串变为整形，加入到整形数组中
        for (int i = 0; i < sudo.length; i++) {
            sudo[i] = str.charAt(i) - '0';
        }
        return sudo;
    }

    //用于计算所有单元格对应的不可用数据
    public void calculateAllUsedTiles() {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                used[x][y] = calculateUsedTiles(x, y);
            }
        }
    }

    //根据坐标取出单元格不可用数据
    public int[] getUsedTilesByCoor(int x, int y) {
        return used[x][y];
    }

    //计算某一单元格中已经不可用的数据，用到sudoku2，即全部数独数据
    public int[] calculateUsedTiles(int x, int y) {

        //把全部数独数据赋值给sudo
        int[] sudo = sudoku2;
        //记录已经不能用的数字
        int c[] = new int[16];

        //计算所选单元格一列全部用过的数字
        for (int i = 0; i < 16; i++) {
            if (i == y)
                continue;
            int t = getTile(x, i, sudo);
            if (t != 0)
                if (t >= 17) {
                    c[t - 8] = t;
                } else
                    c[t - 1] = t;
        }

        //计算所选单元格一行全部用过的数字
        for (int i = 0; i < 16; i++) {
            if (i == x)
                continue;
            int t = getTile(i, y, sudo);
            if (t != 0)
                if (t >= 17) {
                    c[t - 8] = t;
                } else
                    c[t - 1] = t;
        }

        //计算小九宫格已经用过的数字
        int startX = (x / 4) * 4;
        int startY = (y / 4) * 4;
        for (int i = startX; i < startX + 4; i++) {
            for (int j = startY; j < startY + 4; j++) {
                if (i == x && j == y)
                    continue;
                int t = getTile(i, j, sudo);
                if (t != 0)
                    if (t >= 17) {
                        c[t - 8] = t;
                    } else
                        c[t - 1] = t;
            }
        }

        //压缩数据，把数组c中0去除掉
        int nused = 0;
        //计算c中不为0的数nused
        for (int t : c) {
            if (t != 0)
                nused++;
        }
        int c1[] = new int[nused];
        int a = 0;
        //把c中不为0的数，赋值给c1
        for (int t : c) {
            if (t != 0)
                c1[a++] = t;
        }
        return c1;
    }

    //检测玩家所选数字是否符合游戏规则
    protected boolean setTileIfValid(int x, int y, int value) {

        //取出该单元格不可用数据
        int tiles[] = getUsedTiles(x, y);

        //检验玩家所选数据是否为不可用数据
        if (value != 0) {
            for (int tile : tiles) {
                if (tile == value)
                    return false;
            }
        }
        //给数独数组增加数据
        setTile(x, y, value);
        //重新计算全部单元格不可用数据
        calculateAllUsedTiles();
        return true;
    }

    //获取该单元格不可用的数据
    protected int[] getUsedTiles(int x, int y) {
        return used[x][y];
    }

    //删除数独数组的一个数据
    public void deleteTile(int selectedX, int selectedY) {
        //该单元格设置为0
        setTile(selectedX, selectedY, 0);
        //重新计算各个单元格的不可用数据
        calculateAllUsedTiles();
    }

    //通关判断 全部数独 中有无0，来判断这个数独是否完成
    public boolean ifPassGame() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (sudoku2[j * 16 + i] == 0)
                    return false;
            }
        }
        return true;
    }
}
