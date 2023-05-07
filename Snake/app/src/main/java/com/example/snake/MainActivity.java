package com.example.snake;

package com.tilak.snakegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {


    private final List<SnakePoints> snakePointsList = new ArrayList<>();
    private SurfaceView surfaceView;
    private TextView scoreTV;

    private SurfaceHolder surfaceHolder;

    //snake moving positions can either be right,left, top or bottom
    //By default snake moves right
    private String movingPosition = "right";

    //score
    private int score = 0;

    // max length/size of the snake(can be changed)
    private static final int pointSize = 28;

    //Minimum length of the snake
    private static final int defaultTalePoints = 3;

    //Snake color and speed of the snake.
    private static final int snakeColor = Color.YELLOW;
    private static final int snakeMovingSpeed = 800;

    //random point position
    private int positionX, positionY;

    // timer to move / change snake position after a specific time(snakeMovingSpeed)
    private Timer timer;
    //canvas to draw snake and show on surface view
    private Canvas canvas = null;

    //point color/ single point color of a snake
    private Paint pointColor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surfaceView);
        scoreTV = findViewById(R.id.scoreTV);

        final AppCompatImageButton topBtn = findViewById(R.id.topBtn);
        final AppCompatImageButton leftBtn= findViewById(R.id.leftBtn);
        final AppCompatImageButton rightBtn = findViewById(R.id.rightBtn);
        final AppCompatImageButton bottomBtn = findViewById(R.id.bottomBtn);

        surfaceView.getHolder().addCallback(this);

        topBtn.setOnClickListener(view -> {

            if(!movingPosition.equals("bottom")){
                movingPosition = "top";
            }
        });

        leftBtn.setOnClickListener(view -> {

            if(!movingPosition.equals("right")){
                movingPosition = "left";
            }
        });

        rightBtn.setOnClickListener(view -> {

            if(!movingPosition.equals("left")){
                movingPosition = "right";
            }
        });

        bottomBtn.setOnClickListener(view -> {

            if(!movingPosition.equals("top")){
                movingPosition = "bottom";
            }
        });

    }

    @Override
    protected void onUserLeaveHint() {
        // Home button pressed
        super.onUserLeaveHint();
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        finish();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        this.surfaceHolder = surfaceHolder;

        init();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    private void init(){

        snakePointsList.clear();

        scoreTV.setText("0");
        score = 0;

        movingPosition ="right";

        int startPositionX = (pointSize) * defaultTalePoints;

        for(int i=0;i<defaultTalePoints;i++){

            SnakePoints snakePoints = new SnakePoints(startPositionX, pointSize);
            snakePointsList.add(snakePoints);

            startPositionX = startPositionX - (pointSize * 2);
        }

        addPoints();

        moveSnake();
    }

    private void addPoints(){

        //getting surfaceView width and height to add points on the surface to be eaten by the snake
        int surfaceWidth = surfaceView.getWidth() - (pointSize * 2);
        int surfaceHeight = surfaceView.getHeight() - (pointSize * 2);

        int randomXPosition = new Random().nextInt( surfaceWidth / pointSize);
        int randomYPosition = new Random().nextInt( surfaceHeight / pointSize);
        //check is random point appears on the body of snake if it appears then again add new point
        for(int i=0;i<snakePointsList.size();i++){
            if(snakePointsList.get(i).getPositionX() == randomXPosition &&
                    snakePointsList.get(i).getPositionY() == randomYPosition ){
                randomXPosition = new Random().nextInt( surfaceWidth / pointSize);
                randomYPosition = new Random().nextInt( surfaceHeight / pointSize);
                i = 0;
            }
        }
        //check if randomXPosition/randomYPosition is even or add. We need only even number.
        if((randomXPosition % 2) !=0){
            randomXPosition = randomXPosition + 1;
        }

        if((randomYPosition % 2) !=0){
            randomYPosition = randomYPosition + 1;
        }

        positionX = (pointSize * randomXPosition) + pointSize;
        positionY = (pointSize * randomYPosition) + pointSize;
    }
  //paste here
}