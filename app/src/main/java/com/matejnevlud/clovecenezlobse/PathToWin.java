package com.matejnevlud.clovecenezlobse;

import java.util.HashMap;
import java.util.Map;

public class PathToWin {

    private int[][][] myPathToWin;

    public void setMyPlayingColor(String myColor) {
        switch (myColor) {
            case "yellow":
                this.myPathToWin = yellowPathToWin;
                break;
            case "red":
                this.myPathToWin = redPathToWin;
                break;
            case "green":
                this.myPathToWin = greenPathToWin;
                break;
            case "blue":
                this.myPathToWin = bluePathToWin;
                break;
        }
    }

    public int[] nextPossibleMove(int x, int y) {

        for (int[][] move : this.myPathToWin) {
            if (move[0][0] == x && move[0][1] == y)
                return move[1];
        }

        return new int[]{x, y};

    }

    private int[][][] redPathToWin = {

            {{0, 4}, {1, 4}},
            {{1, 4}, {2, 4}},
            {{2, 4}, {3, 4}},
            {{3, 4}, {4, 4}},
            {{4, 4}, {4, 3}},
            {{4, 3}, {4, 2}},
            {{4, 2}, {4, 1}},
            {{4, 1}, {4, 0}},

            {{4, 0}, {5, 0}},
            {{5, 0}, {6, 0}},

            {{6, 0}, {6, 1}},
            {{6, 1}, {6, 2}},
            {{6, 2}, {6, 3}},
            {{6, 3}, {6, 4}},
            {{6, 4}, {7, 4}},
            {{7, 4}, {8, 4}},
            {{8, 4}, {9, 4}},
            {{9, 4}, {10, 4}},

            {{10, 4}, {10, 5}},
            {{10, 5}, {10, 6}},

            {{10, 6}, {9, 6}},
            {{9, 6}, {8, 6}},
            {{8, 6}, {7, 6}},
            {{7, 6}, {6, 6}},
            {{6, 6}, {6, 7}},
            {{6, 7}, {6, 8}},
            {{6, 8}, {6, 9}},
            {{6, 9}, {6, 10}},

            {{6, 10}, {5, 10}},
            {{5, 10}, {4, 10}},

            {{4, 10}, {4, 9}},
            {{4, 9}, {4, 8}},
            {{4, 8}, {4, 7}},
            {{4, 7}, {4, 6}},
            {{4, 6}, {3, 6}},
            {{3, 6}, {2, 6}},
            {{2, 6}, {1, 6}},
            {{1, 6}, {0, 6}},

            {{0, 6}, {0, 5}},
            {{0, 5}, {1, 5}},
            {{1, 5}, {2, 5}},
            {{2, 5}, {3, 5}},
            {{3, 5}, {4, 5}},
    };

    private int[][][] bluePathToWin = {

            {{6, 0}, {6, 1}},
            {{6, 1}, {6, 2}},
            {{6, 2}, {6, 3}},
            {{6, 3}, {6, 4}},
            {{6, 4}, {7, 4}},
            {{7, 4}, {8, 4}},
            {{8, 4}, {9, 4}},
            {{9, 4}, {10, 4}},

            {{10, 4}, {10, 5}},
            {{10, 5}, {10, 6}},

            {{10, 6}, {9, 6}},
            {{9, 6}, {8, 6}},
            {{8, 6}, {7, 6}},
            {{7, 6}, {6, 6}},
            {{6, 6}, {6, 7}},
            {{6, 7}, {6, 8}},
            {{6, 8}, {6, 9}},
            {{6, 9}, {6, 10}},

            {{6, 10}, {5, 10}},
            {{5, 10}, {4, 10}},

            {{4, 10}, {4, 9}},
            {{4, 9}, {4, 8}},
            {{4, 8}, {4, 7}},
            {{4, 7}, {4, 6}},
            {{4, 6}, {3, 6}},
            {{3, 6}, {2, 6}},
            {{2, 6}, {1, 6}},
            {{1, 6}, {0, 6}},

            {{0, 6}, {0, 5}},
            {{0, 5}, {0, 4}},

            {{0, 4}, {1, 4}},
            {{1, 4}, {2, 4}},
            {{2, 4}, {3, 4}},
            {{3, 4}, {4, 4}},
            {{4, 4}, {4, 3}},
            {{4, 3}, {4, 2}},
            {{4, 2}, {4, 1}},
            {{4, 1}, {4, 0}},

            {{4, 0}, {5, 0}},
            {{5, 0}, {5, 1}},
            {{5, 1}, {5, 2}},
            {{5, 2}, {5, 3}},
            {{5, 3}, {5, 4}},

    };

    private int[][][] greenPathToWin = {

            {{10, 6}, {9, 6}},
            {{9, 6}, {8, 6}},
            {{8, 6}, {7, 6}},
            {{7, 6}, {6, 6}},
            {{6, 6}, {6, 7}},
            {{6, 7}, {6, 8}},
            {{6, 8}, {6, 9}},
            {{6, 9}, {6, 10}},

            {{6, 10}, {5, 10}},
            {{5, 10}, {4, 10}},

            {{4, 10}, {4, 9}},
            {{4, 9}, {4, 8}},
            {{4, 8}, {4, 7}},
            {{4, 7}, {4, 6}},
            {{4, 6}, {3, 6}},
            {{3, 6}, {2, 6}},
            {{2, 6}, {1, 6}},
            {{1, 6}, {0, 6}},

            {{0, 6}, {0, 5}},
            {{0, 5}, {0, 4}},

            {{0, 4}, {1, 4}},
            {{1, 4}, {2, 4}},
            {{2, 4}, {3, 4}},
            {{3, 4}, {4, 4}},
            {{4, 4}, {4, 3}},
            {{4, 3}, {4, 2}},
            {{4, 2}, {4, 1}},
            {{4, 1}, {4, 0}},

            {{4, 0}, {5, 0}},
            {{5, 0}, {6, 0}},

            {{6, 0}, {6, 1}},
            {{6, 1}, {6, 2}},
            {{6, 2}, {6, 3}},
            {{6, 3}, {6, 4}},
            {{6, 4}, {7, 4}},
            {{7, 4}, {8, 4}},
            {{8, 4}, {9, 4}},
            {{9, 4}, {10, 4}},

            {{10, 4}, {10, 5}},
            {{10, 5}, {9, 5}},
            {{9, 5}, {8, 5}},
            {{8, 5}, {7, 5}},
            {{7, 5}, {6, 5}},

    };

    private int[][][] yellowPathToWin = {

            {{4, 10}, {4, 9}},
            {{4, 9}, {4, 8}},
            {{4, 8}, {4, 7}},
            {{4, 7}, {4, 6}},
            {{4, 6}, {3, 6}},
            {{3, 6}, {2, 6}},
            {{2, 6}, {1, 6}},
            {{1, 6}, {0, 6}},

            {{0, 6}, {0, 5}},
            {{0, 5}, {0, 4}},

            {{0, 4}, {1, 4}},
            {{1, 4}, {2, 4}},
            {{2, 4}, {3, 4}},
            {{3, 4}, {4, 4}},
            {{4, 4}, {4, 3}},
            {{4, 3}, {4, 2}},
            {{4, 2}, {4, 1}},
            {{4, 1}, {4, 0}},

            {{4, 0}, {5, 0}},
            {{5, 0}, {6, 0}},

            {{6, 0}, {6, 1}},
            {{6, 1}, {6, 2}},
            {{6, 2}, {6, 3}},
            {{6, 3}, {6, 4}},
            {{6, 4}, {7, 4}},
            {{7, 4}, {8, 4}},
            {{8, 4}, {9, 4}},
            {{9, 4}, {10, 4}},

            {{10, 4}, {10, 5}},
            {{10, 5}, {10, 6}},

            {{10, 6}, {9, 6}},
            {{9, 6}, {8, 6}},
            {{8, 6}, {7, 6}},
            {{7, 6}, {6, 6}},
            {{6, 6}, {6, 7}},
            {{6, 7}, {6, 8}},
            {{6, 8}, {6, 9}},
            {{6, 9}, {6, 10}},

            {{6, 10}, {5, 10}},
            {{5, 10}, {5, 9}},
            {{5, 9}, {5, 8}},
            {{5, 8}, {5, 7}},
            {{5, 7}, {5, 6}},

    };


    public HashMap<String, String> androidToServerMap = new HashMap<String, String>() {{

        put("4, 10", "pos_0");
        put("4, 9", "pos_1");
        put("4, 8", "pos_2");
        put("4, 7", "pos_3");
        put("4, 6", "pos_4");
        put("3, 6", "pos_5");
        put("2, 6", "pos_6");
        put("1, 6", "pos_7");

        put("0, 6", "pos_8");
        put("0, 5", "pos_9");

        put("0, 4", "pos_10");
        put("1, 4", "pos_11");
        put("2, 4", "pos_12");
        put("3, 4", "pos_13");
        put("4, 4", "pos_14");
        put("4, 3", "pos_15");
        put("4, 2", "pos_16");
        put("4, 1", "pos_17");

        put("4, 0", "pos_18");
        put("5, 0", "pos_19");

        put("6, 0", "pos_20");
        put("6, 1", "pos_21");
        put("6, 2", "pos_22");
        put("6, 3", "pos_23");
        put("6, 4", "pos_24");
        put("7, 4", "pos_25");
        put("8, 4", "pos_26");
        put("9, 4", "pos_27");

        put("10, 4", "pos_28");
        put("10, 5", "pos_29");

        put("10, 6", "pos_30");
        put("9, 6", "pos_31");
        put("8, 6", "pos_32");
        put("7, 6", "pos_33");
        put("6, 6", "pos_34");
        put("6, 7", "pos_35");
        put("6, 8", "pos_36");
        put("6, 9", "pos_37");

        put("6, 10", "pos_38");
        put("5, 10", "pos_39");

        put("5, 9", "win_position_0");
        put("5, 8", "win_position_1");
        put("5, 7", "win_position_2");
        put("5, 6", "win_position_3");

        put("1, 5", "win_position_4");
        put("2, 5", "win_position_5");
        put("3, 5", "win_position_6");
        put("4, 5", "win_position_7");

        put("9, 5", "win_position_8");
        put("8, 5", "win_position_9");
        put("7, 5", "win_position_10");
        put("6, 5", "win_position_11");

        put("5, 1", "win_position_12");
        put("5, 2", "win_position_13");
        put("5, 3", "win_position_14");
        put("5, 4", "win_position_15");

        put("0, 9", "start_position_0");
        put("0, 10", "start_position_1");
        put("1, 9", "start_position_2");
        put("1, 10", "start_position_3");

        put("0, 0", "start_position_4");
        put("0, 1", "start_position_5");
        put("1, 0", "start_position_6");
        put("1, 1", "start_position_7");

        put("9, 9", "start_position_8");
        put("9, 10", "start_position_9");
        put("10, 9", "start_position_10");
        put("10, 10", "start_position_11");

        put("9, 0", "start_position_12");
        put("10, 0", "start_position_13");
        put("9, 1", "start_position_14");
        put("10, 1", "start_position_15");

    }};
    public HashMap<String, int[]> serverToAndroidMap = new HashMap<String, int[]>() {{

        put("pos_0", new int[]{4, 10});
        put("pos_1", new int[]{4, 9});
        put("pos_2", new int[]{4, 8});
        put("pos_3", new int[]{4, 7});
        put("pos_4", new int[]{4, 6});
        put("pos_5", new int[]{3, 6});
        put("pos_6", new int[]{2, 6});
        put("pos_7", new int[]{1, 6});

        put("pos_8", new int[]{0, 6});
        put("pos_9", new int[]{0, 5});

        put("pos_10", new int[]{0, 4});
        put("pos_11", new int[]{1, 4});
        put("pos_12", new int[]{2, 4});
        put("pos_13", new int[]{3, 4});
        put("pos_14", new int[]{4, 4});
        put("pos_15", new int[]{4, 3});
        put("pos_16", new int[]{4, 2});
        put("pos_17", new int[]{4, 1});

        put("pos_18", new int[]{4, 0});
        put("pos_19", new int[]{5, 0});

        put("pos_20", new int[]{6, 0});
        put("pos_21", new int[]{6, 1});
        put("pos_22", new int[]{6, 2});
        put("pos_23", new int[]{6, 3});
        put("pos_24", new int[]{6, 4});
        put("pos_25", new int[]{7, 4});
        put("pos_26", new int[]{8, 4});
        put("pos_27", new int[]{9, 4});

        put("pos_28", new int[]{10, 4});
        put("pos_29", new int[]{10, 5});

        put("pos_30", new int[]{10, 6});
        put("pos_31", new int[]{9, 6});
        put("pos_32", new int[]{8, 6});
        put("pos_33", new int[]{7, 6});
        put("pos_34", new int[]{6, 6});
        put("pos_35", new int[]{6, 7});
        put("pos_36", new int[]{6, 8});
        put("pos_37", new int[]{6, 9});

        put("pos_38", new int[]{6, 10});
        put("pos_39", new int[]{5, 10});

        put("win_position_0", new int[]{5, 10});
        put("win_position_1", new int[]{5, 9});
        put("win_position_2", new int[]{5, 8});
        put("win_position_3", new int[]{5, 7});

        put("win_position_4", new int[]{0, 5});
        put("win_position_5", new int[]{1, 5});
        put("win_position_6", new int[]{2, 5});
        put("win_position_7", new int[]{3, 5});

        put("win_position_8", new int[]{10, 5});
        put("win_position_9", new int[]{9, 5});
        put("win_position_10", new int[]{8, 5});
        put("win_position_11", new int[]{7, 5});

        put("win_position_12", new int[]{5, 0});
        put("win_position_13", new int[]{5, 1});
        put("win_position_14", new int[]{5, 2});
        put("win_position_15", new int[]{5, 3});

        put("start_position_0", new int[]{0, 9});
        put("start_position_1", new int[]{0, 10});
        put("start_position_2", new int[]{1, 9});
        put("start_position_3", new int[]{1, 10});

        put("start_position_4", new int[]{0, 0});
        put("start_position_5", new int[]{0, 1});
        put("start_position_6", new int[]{1, 0});
        put("start_position_7", new int[]{1, 1});

        put("start_position_8", new int[]{9, 9});
        put("start_position_9", new int[]{9, 10});
        put("start_position_10", new int[]{10, 9});
        put("start_position_11", new int[]{10, 10});

        put("start_position_12", new int[]{9, 0});
        put("start_position_13", new int[]{10, 0});
        put("start_position_14", new int[]{9, 1});
        put("start_position_15", new int[]{10, 1});

    }};


    public HashMap<String, int[]> serverToAndroidFigure = new HashMap<String, int[]>() {{

        put("pos_0", new int[]{4, 10});
        put("pos_1", new int[]{4, 9});
        put("pos_2", new int[]{4, 8});
        put("pos_3", new int[]{4, 7});
        put("pos_4", new int[]{4, 6});
        put("pos_5", new int[]{3, 6});
        put("pos_6", new int[]{2, 6});
        put("pos_7", new int[]{1, 6});

        put("pos_8", new int[]{0, 6});
        put("pos_9", new int[]{0, 5});

        put("pos_10", new int[]{0, 4});
        put("pos_11", new int[]{1, 4});
        put("pos_12", new int[]{2, 4});
        put("pos_13", new int[]{3, 4});
        put("pos_14", new int[]{4, 4});
        put("pos_15", new int[]{4, 3});
        put("pos_16", new int[]{4, 2});
        put("pos_17", new int[]{4, 1});

        put("pos_18", new int[]{4, 0});
        put("pos_19", new int[]{5, 0});

        put("pos_20", new int[]{6, 0});
        put("pos_21", new int[]{6, 1});
        put("pos_22", new int[]{6, 2});
        put("pos_23", new int[]{6, 3});
        put("pos_24", new int[]{6, 4});
        put("pos_25", new int[]{7, 4});
        put("pos_26", new int[]{8, 4});
        put("pos_27", new int[]{9, 4});

        put("pos_28", new int[]{10, 4});
        put("pos_29", new int[]{10, 5});

        put("pos_30", new int[]{10, 6});
        put("pos_31", new int[]{9, 6});
        put("pos_32", new int[]{8, 6});
        put("pos_33", new int[]{7, 6});
        put("pos_34", new int[]{6, 6});
        put("pos_35", new int[]{6, 7});
        put("pos_36", new int[]{6, 8});
        put("pos_37", new int[]{6, 9});

        put("pos_38", new int[]{6, 10});
        put("pos_39", new int[]{5, 10});

        put("win_position_0", new int[]{5, 10});
        put("win_position_1", new int[]{5, 9});
        put("win_position_2", new int[]{5, 8});
        put("win_position_3", new int[]{5, 7});

        put("win_position_4", new int[]{0, 5});
        put("win_position_5", new int[]{1, 5});
        put("win_position_6", new int[]{2, 5});
        put("win_position_7", new int[]{3, 5});

        put("win_position_8", new int[]{10, 5});
        put("win_position_9", new int[]{9, 5});
        put("win_position_10", new int[]{8, 5});
        put("win_position_11", new int[]{7, 5});

        put("win_position_12", new int[]{5, 0});
        put("win_position_13", new int[]{5, 1});
        put("win_position_14", new int[]{5, 2});
        put("win_position_15", new int[]{5, 3});

        put("start_position_0", new int[]{0, 9});
        put("start_position_1", new int[]{0, 10});
        put("start_position_2", new int[]{1, 9});
        put("start_position_3", new int[]{1, 10});

        put("start_position_4", new int[]{0, 0});
        put("start_position_5", new int[]{0, 1});
        put("start_position_6", new int[]{1, 0});
        put("start_position_7", new int[]{1, 1});

        put("start_position_8", new int[]{9, 9});
        put("start_position_9", new int[]{9, 10});
        put("start_position_10", new int[]{10, 9});
        put("start_position_11", new int[]{10, 10});

        put("start_position_12", new int[]{9, 0});
        put("start_position_13", new int[]{10, 0});
        put("start_position_14", new int[]{9, 1});
        put("start_position_15", new int[]{10, 1});

    }};

}
