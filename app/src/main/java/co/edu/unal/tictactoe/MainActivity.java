package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;

    // Buttons making up the board
    private Button mBoardButtons[];
    // Various text displayed
    private TextView mInfoTextView;
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;

    private BoardView mBoardView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // add toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //add boardView
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGame);
        // Buscar el botón de reinicio por su ID
        Button buttonRestart = findViewById(R.id.buttonRestart);
        // Agregar un evento de clic al botón de reinicio
        buttonRestart.setOnClickListener(v -> {
            // Llama a una función para reiniciar el juego
            startNewGame();
        });

        mInfoTextView = (TextView) findViewById(R.id.information);
        mGame = new TicTacToeGame();

        startNewGame();

        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
            return true;
            case R.id.quit:
                // Declarar un objeto AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // Configurar el diálogo de confirmación para salir del juego
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Código para salir de la aplicación
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Cerrar el diálogo y continuar con el juego
                                dialog.dismiss();
                            }
                        });

                // Crear el diálogo
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            

        }
        return true;
    }
    int selected = -1;
    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch(id) {
            case DIALOG_DIFFICULTY_ID:
                builder.setTitle(R.string.difficulty_choose);
                final CharSequence[] levels = {
                        getResources().getString(R.string.difficulty_easy),
                        getResources().getString(R.string.difficulty_harder),
                        getResources().getString(R.string.difficulty_expert)
                };

                builder.setSingleChoiceItems(levels, selected,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                // Guardar la selección del usuario en la variable 'selected'
                                selected = item;

                                // Configurar el nivel de dificultad en mGame según la selección
                                if (item == 0) {
                                    mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
                                } else if (item == 1) {
                                    mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Hard);
                                } else if (item == 2) {
                                    mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
                                }

                                dialog.dismiss(); // Cerrar el diálogo

                                // Mostrar un mensaje para confirmar la selección de dificultad
                                Toast.makeText(getApplicationContext(), levels[item], Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog = builder.create();
                break;
        }

        return dialog;
    }
    // Set up the game board.
    private void startNewGame() {
        mGame.clearBoard();
        mBoardView.invalidate();// Redraw the board
        mInfoTextView.setText("You go first.");

    }
    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            // Verificar si el juego ya ha terminado
            if (mGame.checkForWinner() == 0 && mGame.getBoardOccupant(location) == TicTacToeGame.OPEN_SPOT) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.turn_computer);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0)
                        mInfoTextView.setText(R.string.turn_human);
                    else if (winner == 1)
                        mInfoTextView.setText(R.string.result_tie);
                    else if (winner == 2)
                        mInfoTextView.setText(R.string.result_human_wins);
                    else
                        mInfoTextView.setText(R.string.result_computer_wins);
                }
            }
        }

        private void setMove(char player, int location) {
            mGame.setMove(player, location);
            mBoardButtons[location].setEnabled(false);
            mBoardButtons[location].setText(String.valueOf(player));
            if (player == TicTacToeGame.HUMAN_PLAYER)
                mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
            else
                mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
        }
    }

}

