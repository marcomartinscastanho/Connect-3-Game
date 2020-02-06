package com.martinscastanho.marco.connect3game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Boolean isGreen = true;
    Boolean isGameActive = true;
    //0: green, 1: yellow, 2: empty
    Integer[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    Integer[][] winningCases = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        Log.i("Tag", counter.getTag().toString());
        Integer tappedCounter = Integer.parseInt(counter.getTag().toString());
        if(gameState[tappedCounter] != 2 || !isGameActive){
            return;
        }
        gameState[tappedCounter] = isGreen ? 0 : 1;
        counter.setTranslationY(-1500);
        if(isGreen){
            counter.setImageResource(R.drawable.chip_green);
        }
        else {
            counter.setImageResource(R.drawable.chip_yellow);
        }

        counter.animate().translationYBy(1500).rotation(180).setDuration(500);

        for(Integer[] winningCase : winningCases){
            if(gameState[winningCase[0]] == (isGreen ? 0 : 1) && gameState[winningCase[1]] == (isGreen ? 0 : 1) && gameState[winningCase[2]] == (isGreen ? 0 : 1)){
                // The last player who played has won
                isGameActive = false;
                String winner = isGreen ? "Green" : "Yellow";
                Button playAgainButton = findViewById(R.id.playAgainButton);
                TextView winnerTextView = findViewById(R.id.winnerTextView);
                winnerTextView.setText(winner + " has won!");
                winnerTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }
        isGreen = !isGreen;
    }

    public void playAgain(View view){
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++ ){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        isGreen = true;
        isGameActive = true;
        for(int i = 0; i < gameState.length; i++){
            gameState[i]=2;
        }
    }
}
