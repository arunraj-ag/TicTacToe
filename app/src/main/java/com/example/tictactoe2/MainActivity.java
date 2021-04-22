package com.example.tictactoe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe2.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title_str,player_one,player_two;
    private ImageView intro_pic;
    private Button enter_game,reset_btn;
    private LinearLayout top_row,middle_row,bottom_row;

    private Button[][] buttons = new Button[3][3];
    private boolean playerOneTurn = true;
    private int roundCount;
    private int playerOnePoints,playerTwoPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title_str = findViewById(R.id.title_str);
        intro_pic = findViewById(R.id.intro_pic);
        enter_game = findViewById(R.id.enter_game);

        player_one = findViewById(R.id.player_one_);
        player_two = findViewById(R.id.player_two);
        reset_btn = findViewById(R.id.reset_btn);

        top_row = findViewById(R.id.top_row);
        middle_row = findViewById(R.id.middle_row);
        bottom_row = findViewById(R.id.bottom_row);

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    public void enter_game_method(View v){
        title_str.setVisibility(View.GONE);
        intro_pic.setVisibility(View.GONE);
        enter_game.setVisibility(View.GONE);

        player_one.setVisibility(View.VISIBLE);
        player_two.setVisibility(View.VISIBLE);
        reset_btn.setVisibility(View.VISIBLE);

        top_row.setVisibility(View.VISIBLE);
        middle_row.setVisibility(View.VISIBLE);
        bottom_row.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")){
            return;
        }
        if (playerOneTurn){
            ((Button)v).setText("X");
        }else {
            ((Button)v).setText("O");
        }

        roundCount++;
        if (checkForWin()){
            if (playerOneTurn){
                playerOneWins();
            }
            else
                playerTwoWins();
        }
        else if(roundCount==9){
            draw();
        }
        else
            playerOneTurn = !playerOneTurn;
    }
    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0; i<3; i++){
            if (field[i][0].equals(field[i][1])&&(field[i][0]==field[i][2])&&(!field[i][0].equals(""))){
                return true;
            }
        }
        for (int i=0; i<3; i++){
            if (field[0][i].equals(field[1][i])&&(field[0][i]==field[2][i])&&(!field[0][i].equals(""))){
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])&&(field[0][0]==field[2][2])&&(!field[0][0].equals(""))){
            return true;
        }

        if (field[0][2].equals(field[1][1])&&(field[0][2]==field[2][0])&&(!field[0][2].equals(""))){
            return true;
        }
        return false;
    }

    private void playerOneWins(){
        playerOnePoints++;
        Toast.makeText(this, "Player One Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void playerTwoWins(){
        playerTwoPoints++;
        Toast.makeText(this, "Player Two Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        player_one.setText("Player 1: "+playerOnePoints);
        player_two.setText("Player 2: "+playerTwoPoints);
    }

    private void resetBoard() {
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount=0;
        playerOneTurn = true;
    }
    private void resetGame(){
        playerOnePoints = 0;
        playerTwoPoints = 0;
        updatePointsText();
        resetBoard();
    }
}