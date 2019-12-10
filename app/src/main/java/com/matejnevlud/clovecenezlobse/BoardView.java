package com.matejnevlud.clovecenezlobse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


enum BP {
    VOID,
    POS,
    HOLD,
    START,
    WIN,
    FIG,
    Y_HOLD,
    R_HOLD,
    G_HOLD,
    B_HOLD,
    Y_START,
    R_START,
    G_START,
    B_START,
    Y_WIN,
    R_WIN,
    G_WIN,
    B_WIN,
    Y_FIG,
    R_FIG,
    G_FIG,
    B_FIG,
}

public class BoardView extends View {

    MediaPlayer player;
    private OnBoardChangedListener listener;

    private PathToWin pathToWin;

    private static final float RADIUS = 20;
    private float x = 30;
    private float y = 30;
    private float initialX;
    private float initialY;
    private float offsetX;
    private float offsetY;
    private Paint myPaint;
    private Paint backgroundPaint;

    int size_delta;
    private Bitmap normal_position, yellow_winning, red_winning, green_winning, blue_winning, yellow_starting, red_starting, green_starting, blue_starting, yellow_figure, red_figure, green_figure, blue_figure, dice_1, dice_2, dice_3, dice_4, dice_5, dice_6;
    private Bitmap[] dices;
    private Bitmap currentDice;
    private int currentDiceNumber;

    private boolean canDrawAnimateDice = false;


    BP myColoredFigure, myColoredHolder, myColoredStart, myColoredWin;

    boolean canTossDice = false;
    boolean canDragFigure = false;
    BP clickedEntity;
    int clickedX, clickedY;

    int touchX, touchY;

    private ArrayList<Paint> positions = new ArrayList<Paint>();


    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLUE);

        myPaint = new Paint();
        myPaint.setColor(Color.WHITE);
        myPaint.setAntiAlias(true);

        Paint position = new Paint();
        position.setColor(Color.WHITE);
        position.setStrokeWidth(4);

        positions.add(position);


        this.pathToWin = new PathToWin();
        //initResources();
    }

    public void restartBoard() {
        for (int x = 0; x < 11; x++)
            for (int y = 0; y < 11; y++)
                this.figuresPositions[y][x] = startingFiguresPositions[y][x];

        invalidate();
    }

    public void addListenerForBoardEvents(OnBoardChangedListener listener) {
        this.listener = listener;
    }

    public void setMyPlayingColor(String myColor) {

        switch (myColor) {
            case "yellow":
                myColoredFigure = BP.Y_FIG;
                myColoredHolder = BP.Y_HOLD;
                myColoredStart = BP.Y_START;
                myColoredWin = BP.Y_WIN;
                break;
            case "red":
                myColoredFigure = BP.R_FIG;
                myColoredHolder = BP.R_HOLD;
                myColoredStart = BP.R_START;
                myColoredWin = BP.R_WIN;
                break;
            case "green":
                myColoredFigure = BP.G_FIG;
                myColoredHolder = BP.G_HOLD;
                myColoredStart = BP.G_START;
                myColoredWin = BP.G_WIN;
                break;
            case "blue":
                myColoredFigure = BP.B_FIG;
                myColoredHolder = BP.B_HOLD;
                myColoredStart = BP.B_START;
                myColoredWin = BP.B_WIN;
                break;
        }

        this.pathToWin.setMyPlayingColor(myColor);
    }


    private void initResources() {
        int width = getWidth();
        int height = getHeight();

        size_delta = (int) Math.floor(width / 11 * 1);

        this.normal_position = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.normal_position), size_delta, size_delta, true);

        this.yellow_winning = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yellow_winning), size_delta, size_delta, true);
        this.red_winning = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.red_winning), size_delta, size_delta, true);
        this.green_winning = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.green_winning), size_delta, size_delta, true);
        this.blue_winning = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.blue_winning), size_delta, size_delta, true);

        this.yellow_starting = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yellow_starting), size_delta, size_delta, true);
        this.red_starting = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.red_starting), size_delta, size_delta, true);
        this.green_starting = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.green_starting), size_delta, size_delta, true);
        this.blue_starting = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.blue_starting), size_delta, size_delta, true);

        this.yellow_figure = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.figure_yellow), size_delta, size_delta, true);
        this.red_figure = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.figure_red), size_delta, size_delta, true);
        this.green_figure = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.figure_green), size_delta, size_delta, true);
        this.blue_figure = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.figure_blue), size_delta, size_delta, true);

        this.dice_1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dice_1), size_delta, size_delta, true);
        this.dice_2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dice_2), size_delta, size_delta, true);
        this.dice_3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dice_3), size_delta, size_delta, true);
        this.dice_4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dice_4), size_delta, size_delta, true);
        this.dice_5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dice_5), size_delta, size_delta, true);
        this.dice_6 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dice_6), size_delta, size_delta, true);

        this.dices = new Bitmap[]{dice_1, dice_2, dice_3, dice_4, dice_5, dice_6};
        currentDice = dice_1;
    }


    public void setFiguresPositions(BP[][] f) {
        this.figuresPositions = f;
        invalidate();
    }


    private void drawBoard(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();


        Paint boardPaint = new Paint();
        boardPaint.setColor(Color.parseColor("#80FFFFFF"));
        //Color c = new Color(1, 1, 1, 0);
        //boardPaint.setColor();
        canvas.drawRect(0, 0, width, height, boardPaint);


        int position_delta = width / 11;
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                switch (this.logicBoard[y][x]) {
                    case POS:
                        canvas.drawBitmap(this.normal_position, x * position_delta, y * position_delta, myPaint);
                        break;
                    case Y_HOLD:
                    case Y_START:
                        canvas.drawBitmap(this.yellow_starting, x * position_delta, y * position_delta, myPaint);
                        break;
                    case R_HOLD:
                    case R_START:
                        canvas.drawBitmap(this.red_starting, x * position_delta, y * position_delta, myPaint);
                        break;
                    case G_HOLD:
                    case G_START:
                        canvas.drawBitmap(this.green_starting, x * position_delta, y * position_delta, myPaint);
                        break;
                    case B_HOLD:
                    case B_START:
                        canvas.drawBitmap(this.blue_starting, x * position_delta, y * position_delta, myPaint);
                        break;
                    case Y_WIN:
                        canvas.drawBitmap(this.yellow_winning, x * position_delta, y * position_delta, myPaint);
                        break;
                    case R_WIN:
                        canvas.drawBitmap(this.red_winning, x * position_delta, y * position_delta, myPaint);
                        break;
                    case G_WIN:
                        canvas.drawBitmap(this.green_winning, x * position_delta, y * position_delta, myPaint);
                        break;
                    case B_WIN:
                        canvas.drawBitmap(this.blue_winning, x * position_delta, y * position_delta, myPaint);
                        break;
                    default:
                        break;
                }
            }
        }

        canvas.drawBitmap(currentDice, 5 * position_delta, 5 * position_delta, myPaint);

    }

    private void drawFigures(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();


        int position_delta = width / 11;
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                switch (this.figuresPositions[y][x]) {
                    case Y_FIG:
                        canvas.drawBitmap(this.yellow_figure, x * position_delta, y * position_delta, myPaint);
                        break;
                    case R_FIG:
                        canvas.drawBitmap(this.red_figure, x * position_delta, y * position_delta, myPaint);
                        break;
                    case G_FIG:
                        canvas.drawBitmap(this.green_figure, x * position_delta, y * position_delta, myPaint);
                        break;
                    case B_FIG:
                        canvas.drawBitmap(this.blue_figure, x * position_delta, y * position_delta, myPaint);
                        break;
                    default:
                        break;
                }
            }
        }


    }


    public void draw(Canvas canvas) {
        super.draw(canvas);


        if (normal_position == null)
            this.initResources();

        int width = getWidth();
        int height = getHeight();
        //canvas.drawCircle(x, y, RADIUS, myPaint);


        //canvas.drawCircle(50, 50, 100, positions.get(0));
        this.drawBoard(canvas);
        this.drawFigures(canvas);

        if (clickedEntity != null)
            drawDraggedFigure(canvas);

        if (canDrawAnimateDice)
            drawAnimatedDice(canvas);

        invalidate();
    }


    public void startDrawingAnimatedDice() {
        canDrawAnimateDice = true;
    }

    public void drawAnimatedDice(Canvas canvas) {
        Random rn = new Random();
        int opa = rn.nextInt(6);

        int position_delta = getWidth() / 11;
        canvas.drawBitmap(dices[opa], position_delta * 5, position_delta * 5, myPaint);
    }

    public void stopDrawingAnimatedDice(int diceNumber) {
        canDrawAnimateDice = false;
        currentDice = dices[diceNumber - 1];
        currentDiceNumber = diceNumber;
    }


    public boolean isMyPieceOutOfHolder() {
        int countx = 0, county = 0;
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (figuresPositions[y][x] == myColoredFigure) {
                    if (uncoloredBoard[y][x] == BP.START || uncoloredBoard[y][x] == BP.POS || uncoloredBoard[y][x] == BP.WIN)
                        return true;
                }
            }
        }

        return false;
    }

    public boolean isMyFigureOnStart() {
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (figuresPositions[y][x] == myColoredFigure) {
                    if (logicBoard[y][x] == myColoredStart)
                        return true;
                }
            }
        }

        return false;
    }


    public void canMoveFigure() {
        this.canDragFigure = true;
    }

    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int xPos, yPos;

        if (this.canDrawAnimateDice) {
            xPos = (int) Math.floor(event.getX() / getWidth() * 11);
            yPos = (int) Math.floor(event.getY() / getWidth() * 11);
            if (xPos == 5 && yPos == 5)
                listener.onDiceClicked();
        }


        if (this.canDragFigure)
            switch (action) {
                case MotionEvent.ACTION_DOWN:


                    xPos = (int) Math.floor(event.getX() / getWidth() * 11);
                    yPos = (int) Math.floor(event.getY() / getWidth() * 11);
                    this.clickedEntity = figuresPositions[yPos][xPos];
                    if (this.clickedEntity == BP.VOID) {
                        this.clickedEntity = null;
                        break;
                    }

                    this.clickedX = xPos;
                    this.clickedY = yPos;


                    this.touchX = (int) event.getX();
                    this.touchY = (int) event.getY();

                    player = MediaPlayer.create(getContext(), R.raw.blop);
                    player.start();
                    //Toast.makeText(getContext(), clickedEntity.toString(), Toast.LENGTH_SHORT).show();

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (this.clickedEntity == null) break;
                    this.touchX = (int) event.getX();
                    this.touchY = (int) event.getY();

                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (this.clickedEntity == null) break;

                    xPos = (int) Math.floor(event.getX() / getWidth() * 11);
                    yPos = (int) Math.floor(event.getY() / getWidth() * 11);
                    moveFigure(this.clickedX, this.clickedY, xPos, yPos);
                    this.clickedEntity = null;


                    player = MediaPlayer.create(getContext(), R.raw.woosh);
                    player.start();
                    //Toast.makeText(getContext(), "Dropped at x: " + xPos + " y: " + yPos, Toast.LENGTH_SHORT).show();
                    break;
            }
        return (true);
    }

    public boolean moveFigure(int fromX, int fromY, int destX, int destY) {
        BP clickedPosition = logicBoard[fromY][fromX];
        BP uncoloredClickedPosition = uncoloredBoard[fromY][fromX];
        BP clickedEntity = figuresPositions[fromY][fromX];

        BP destPosition = logicBoard[destY][destX];
        BP uncoloredDestPosition = uncoloredBoard[destY][destX];
        BP destEntity = figuresPositions[destY][destX];

        if (clickedEntity == BP.VOID || destEntity == myColoredFigure) return false;

        // Move my figure from my holder to my start
        if (clickedPosition == myColoredHolder && destPosition == myColoredStart && currentDiceNumber == 6) {
            figuresPositions[destY][destX] = clickedEntity;
            figuresPositions[fromY][fromX] = BP.VOID;


            listener.onBoardFigureMoved();
        }

        // Advance my figure in board game forward
        if ((uncoloredClickedPosition == BP.POS || uncoloredClickedPosition == BP.START) && destEntity == BP.VOID) {

            int[] nextXY = nextAvailablePositionForFigure(clickedX, clickedY, this.currentDiceNumber);
            Log.d("GAME", "Next move X: " + nextXY[0] + " Y: " + nextXY[1]);

            if (nextXY[0] == destX && nextXY[1] == destY) {

                figuresPositions[destY][destX] = clickedEntity;
                figuresPositions[fromY][fromX] = BP.VOID;
                listener.onBoardFigureMoved();
            } else {
                return false;
            }

        }

        // Send other figure to its holder, if is destination entity of my figure
        if ((uncoloredClickedPosition == BP.POS || uncoloredClickedPosition == BP.START) && destEntity != BP.VOID && destEntity != myColoredFigure) {

            int[] nextXY = nextAvailablePositionForFigure(clickedX, clickedY, this.currentDiceNumber);
            Log.d("GAME", "Next move X: " + nextXY[0] + " Y: " + nextXY[1]);

            if (nextXY[0] == destX && nextXY[1] == destY) {

                BP enemyFigure = figuresPositions[destY][destX];
                switch (enemyFigure) {
                    case Y_FIG:
                        if (figuresPositions[9][0] == BP.VOID)
                            figuresPositions[9][0] = enemyFigure;
                        else if (figuresPositions[9][1] == BP.VOID)
                            figuresPositions[9][1] = enemyFigure;
                        else if (figuresPositions[10][0] == BP.VOID)
                            figuresPositions[10][0] = enemyFigure;
                        else if (figuresPositions[10][1] == BP.VOID)
                            figuresPositions[10][1] = enemyFigure;
                        break;
                    case R_FIG:
                        if (figuresPositions[0][0] == BP.VOID)
                            figuresPositions[0][0] = enemyFigure;
                        else if (figuresPositions[0][1] == BP.VOID)
                            figuresPositions[0][1] = enemyFigure;
                        else if (figuresPositions[1][0] == BP.VOID)
                            figuresPositions[1][0] = enemyFigure;
                        else if (figuresPositions[1][1] == BP.VOID)
                            figuresPositions[1][1] = enemyFigure;
                        break;
                    case B_FIG:
                        if (figuresPositions[0][9] == BP.VOID)
                            figuresPositions[0][9] = enemyFigure;
                        else if (figuresPositions[1][9] == BP.VOID)
                            figuresPositions[1][9] = enemyFigure;
                        else if (figuresPositions[0][10] == BP.VOID)
                            figuresPositions[0][10] = enemyFigure;
                        else if (figuresPositions[1][10] == BP.VOID)
                            figuresPositions[1][10] = enemyFigure;
                        break;
                    case G_FIG:
                        if (figuresPositions[9][9] == BP.VOID)
                            figuresPositions[9][9] = enemyFigure;
                        else if (figuresPositions[10][9] == BP.VOID)
                            figuresPositions[10][9] = enemyFigure;
                        else if (figuresPositions[9][10] == BP.VOID)
                            figuresPositions[9][10] = enemyFigure;
                        else if (figuresPositions[10][10] == BP.VOID)
                            figuresPositions[10][10] = enemyFigure;
                        break;
                }

                figuresPositions[destY][destX] = clickedEntity;
                figuresPositions[fromY][fromX] = BP.VOID;
                listener.onBoardFigureMoved();
            } else {
                return false;
            }
        }

        return true;
    }


    private int[] nextAvailablePositionForFigure(int x, int y, int diceNumber) {
        int[] nextPosition = {x, y};

        for (int i = 0; i < diceNumber; i++) {
            nextPosition = pathToWin.nextPossibleMove(nextPosition[0], nextPosition[1]);
        }

        return nextPosition;
    }


    private void drawDraggedFigure(Canvas canvas) {

        switch (this.clickedEntity) {
            case Y_FIG:
                canvas.drawBitmap(this.yellow_figure, touchX - size_delta / 2, touchY - size_delta / 2, myPaint);
                break;
            case R_FIG:
                canvas.drawBitmap(this.red_figure, touchX - size_delta / 2, touchY - size_delta / 2, myPaint);
                break;
            case G_FIG:
                canvas.drawBitmap(this.green_figure, touchX - size_delta / 2, touchY - size_delta / 2, myPaint);
                break;
            case B_FIG:
                canvas.drawBitmap(this.blue_figure, touchX - size_delta / 2, touchY - size_delta / 2, myPaint);
                break;
        }


    }


    public String exportToJSONMovedFigures() {
        int yellowCounter = 0, redCounter = 4, greenCounter = 8, blueCounter = 12;

        ArrayList<FigureMovement> allFiguresPostionsAfterMovements = new ArrayList<>();

        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                String svgPos;
                switch (this.figuresPositions[y][x]) {
                    case Y_FIG:
                        svgPos = pathToWin.androidToServerMap.get(x + ", " + y);
                        allFiguresPostionsAfterMovements.add(new FigureMovement("figure_yellow_" + yellowCounter, svgPos));
                        yellowCounter++;
                        break;
                    case R_FIG:
                        svgPos = pathToWin.androidToServerMap.get(x + ", " + y);
                        allFiguresPostionsAfterMovements.add(new FigureMovement("figure_red_" + redCounter, svgPos));
                        redCounter++;
                        break;
                    case G_FIG:
                        svgPos = pathToWin.androidToServerMap.get(x + ", " + y);
                        allFiguresPostionsAfterMovements.add(new FigureMovement("figure_green_" + greenCounter, svgPos));
                        greenCounter++;
                        break;
                    case B_FIG:
                        svgPos = pathToWin.androidToServerMap.get(x + ", " + y);
                        allFiguresPostionsAfterMovements.add(new FigureMovement("figure_blue_" + blueCounter, svgPos));
                        blueCounter++;
                        break;
                    default:
                        break;
                }
            }
        }

        Gson gson = new Gson();
        return gson.toJson(allFiguresPostionsAfterMovements);

    }

    public void parseJSONFromServer(String boardMapResponse) {

        Gson gson = new Gson();
        FigureMovement[] figureMovements = gson.fromJson(boardMapResponse, FigureMovement[].class);

        for (int x = 0; x < 11; x++)
            for (int y = 0; y < 11; y++)
                this.figuresPositions[y][x] = BP.VOID;


        for (FigureMovement move : figureMovements) {

            int[] posXY = pathToWin.serverToAndroidMap.get(move.movedToId);

            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 11; y++) {
                    if (posXY[0] == x && posXY[1] == y) {
                        if (move.figureId.contains("yellow"))
                            this.figuresPositions[y][x] = BP.Y_FIG;
                        else if (move.figureId.contains("red"))
                            this.figuresPositions[y][x] = BP.R_FIG;
                        else if (move.figureId.contains("green"))
                            this.figuresPositions[y][x] = BP.G_FIG;
                        else if (move.figureId.contains("blue"))
                            this.figuresPositions[y][x] = BP.B_FIG;
                    }
                }
            }

        }

        invalidate();

    }

    private BP[][] uncoloredBoard = {
            {BP.HOLD, BP.HOLD, BP.VOID, BP.VOID, BP.POS, BP.POS, BP.START, BP.VOID, BP.VOID, BP.HOLD, BP.HOLD},
            {BP.HOLD, BP.HOLD, BP.VOID, BP.VOID, BP.POS, BP.WIN, BP.POS, BP.VOID, BP.VOID, BP.HOLD, BP.HOLD},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.POS, BP.WIN, BP.POS, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.POS, BP.WIN, BP.POS, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.START, BP.POS, BP.POS, BP.POS, BP.POS, BP.WIN, BP.POS, BP.POS, BP.POS, BP.POS, BP.POS},
            {BP.POS, BP.WIN, BP.WIN, BP.WIN, BP.WIN, BP.VOID, BP.WIN, BP.WIN, BP.WIN, BP.WIN, BP.POS},
            {BP.POS, BP.POS, BP.POS, BP.POS, BP.POS, BP.WIN, BP.POS, BP.POS, BP.POS, BP.POS, BP.START},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.POS, BP.WIN, BP.POS, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.POS, BP.WIN, BP.POS, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.HOLD, BP.HOLD, BP.VOID, BP.VOID, BP.POS, BP.WIN, BP.POS, BP.VOID, BP.VOID, BP.HOLD, BP.HOLD},
            {BP.HOLD, BP.HOLD, BP.VOID, BP.VOID, BP.START, BP.POS, BP.POS, BP.VOID, BP.VOID, BP.HOLD, BP.HOLD},
    };

    private BP[][] logicBoard = {
            {BP.R_HOLD, BP.R_HOLD, BP.VOID, BP.VOID, BP.POS, BP.POS, BP.B_START, BP.VOID, BP.VOID, BP.B_HOLD, BP.B_HOLD},
            {BP.R_HOLD, BP.R_HOLD, BP.VOID, BP.VOID, BP.POS, BP.B_WIN, BP.POS, BP.VOID, BP.VOID, BP.B_HOLD, BP.B_HOLD},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.POS, BP.B_WIN, BP.POS, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.POS, BP.B_WIN, BP.POS, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.R_START, BP.POS, BP.POS, BP.POS, BP.POS, BP.B_WIN, BP.POS, BP.POS, BP.POS, BP.POS, BP.POS},
            {BP.POS, BP.R_WIN, BP.R_WIN, BP.R_WIN, BP.R_WIN, BP.VOID, BP.G_WIN, BP.G_WIN, BP.G_WIN, BP.G_WIN, BP.POS},
            {BP.POS, BP.POS, BP.POS, BP.POS, BP.POS, BP.Y_WIN, BP.POS, BP.POS, BP.POS, BP.POS, BP.G_START},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.POS, BP.Y_WIN, BP.POS, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.POS, BP.Y_WIN, BP.POS, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.Y_HOLD, BP.Y_HOLD, BP.VOID, BP.VOID, BP.POS, BP.Y_WIN, BP.POS, BP.VOID, BP.VOID, BP.G_HOLD, BP.G_HOLD},
            {BP.Y_HOLD, BP.Y_HOLD, BP.VOID, BP.VOID, BP.Y_START, BP.POS, BP.POS, BP.VOID, BP.VOID, BP.G_HOLD, BP.G_HOLD},
    };

    private BP[][] figuresPositions = {
            {BP.R_FIG, BP.R_FIG, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.B_FIG, BP.B_FIG},
            {BP.R_FIG, BP.R_FIG, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.B_FIG, BP.B_FIG},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.Y_FIG, BP.Y_FIG, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.G_FIG, BP.G_FIG},
            {BP.Y_FIG, BP.Y_FIG, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.G_FIG, BP.G_FIG},
    };

    private BP[][] startingFiguresPositions = {
            {BP.R_FIG, BP.R_FIG, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.B_FIG, BP.B_FIG},
            {BP.R_FIG, BP.R_FIG, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.B_FIG, BP.B_FIG},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID},
            {BP.Y_FIG, BP.Y_FIG, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.G_FIG, BP.G_FIG},
            {BP.Y_FIG, BP.Y_FIG, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.VOID, BP.G_FIG, BP.G_FIG},
    };

}

class FigureMovement {
    public String figureId;
    public String movedToId;

    public FigureMovement(String figureId, String movedToId) {
        this.figureId = figureId;
        this.movedToId = movedToId;
    }
}
