package co.edu.unal.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;

    private static boolean mGameOver = false;

    private static int count_ties = 0;

    private static int count_human = 0;

    private static int count_android = 0;

    private static int difficulty = 0;
    // Various text displayed
    private TextView mInfoTextView;
    private TextView tieCountView;

    private TextView humanCountView;

    private TextView androidCountView;

    static final int DIALOG_DIFFICULTY_ID = 0;
    private BoardView mBoardView;

    private SharedPreferences mPrefs;
    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;

    MediaPlayer mVictory;

    MediaPlayer mTie;
    MediaPlayer mDefeat;
    private boolean setMove(char player, int location) {
        if (mGame.setMove(player, location)) {
            mBoardView.invalidate();
            return true;
        }
        return false;
    }

    // Listen for touches on the board
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;

            if (!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER, pos)) {
                // Play the sound effect for human's move
                mHumanMediaPlayer.start();

                // Lógica para que la computadora realice su movimiento
                int winner = mGame.checkForWinner();
                if (winner == 0) {

                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    mComputerMediaPlayer.start();
                    winner = mGame.checkForWinner();
                }
                if (winner == 0) {
                mInfoTextView.setText(R.string.turn_human);
                }else if (winner == 1) {
                    count_ties +=1;
                    String cadena = getResources().getString(R.string.count_ties);;
                    mInfoTextView.setText(R.string.result_tie);
                    tieCountView.setText(cadena+" "+ count_ties);
                    mTie.start();
                }else if (winner == 2) {
                    count_human +=1;
                    String cadena = getResources().getString(R.string.count_human);;
                    humanCountView.setText(cadena+" "+ count_human);
                    mInfoTextView.setText(R.string.result_human_wins);
                    mVictory.start();
                    mGameOver = true;
                }else {
                    count_android +=1;
                    String cadena = getResources().getString(R.string.count_android);
                    androidCountView.setText(cadena+" "+ count_android);
                    mInfoTextView.setText(R.string.result_computer_wins);
                    mDefeat.start();
                    mGameOver = true;
                }
            }
            // So we aren't notified of continued events when finger is moved
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // add toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //add boardView
        mBoardView = (BoardView) findViewById(R.id.board);
        mGame = new TicTacToeGame();
        mBoardView.setGame(mGame);
        // Buscar el botón de reinicio por su ID
        Button buttonRestart = findViewById(R.id.buttonRestart);
        // Agregar un evento de clic al botón de reinicio
        buttonRestart.setOnClickListener(v -> {
            // Llama a una función para reiniciar el juego
            mGameOver = false;
            startNewGame();
        });

        mInfoTextView = (TextView) findViewById(R.id.information);
        tieCountView = (TextView) findViewById(R.id.tieCounter);
        humanCountView = (TextView) findViewById(R.id.humanWinsCounter);
        androidCountView = (TextView) findViewById(R.id.androidWinsCounter);
        mBoardView.setOnTouchListener(mTouchListener);

        //add persistence data
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);
        //load data
        count_human = mPrefs.getInt("count_human", 0);
        count_android = mPrefs.getInt("count_android", 0);
        count_ties = mPrefs.getInt("count_ties", 0);
        difficulty = mPrefs.getInt("difficulty", 0);
        startNewGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Save the current scores
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("count_human", count_human);
        ed.putInt("count_android", count_android);
        ed.putInt("count_ties", count_ties);
        ed.putInt("difficulty", difficulty);
        ed.commit();
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mGame.setBoardState(savedInstanceState.getCharArray("board"));
        mGameOver = savedInstanceState.getBoolean("mGameOver");
        mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
        count_human = savedInstanceState.getInt("mHumanWins");
        count_android = savedInstanceState.getInt("mComputerWins");
        count_ties = savedInstanceState.getInt("mTies");
        difficulty = savedInstanceState.getInt("difficulty");
        displayScores();
        restoreDifficult(difficulty);
    }
    private void displayScores() {
        String cadena1 = getResources().getString(R.string.count_human);
        String cadena2 = getResources().getString(R.string.count_android);
        String cadena3 = getResources().getString(R.string.count_ties);
        humanCountView.setText(cadena1+" "+ count_human);
        androidCountView.setText(cadena2+" "+ count_android);
        tieCountView.setText(cadena3+" "+ count_ties);
    }
    private void restoreDifficult(int item) {
        if (item == 0) {
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
        } else if (item == 1) {
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Hard);
        } else if (item == 2) {
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            case R.id.reset_score:
                count_human = 0;
                count_android = 0;
                count_ties = 0;
                difficulty = 0;
                mGameOver = false;
                startNewGame();
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

        switch (id) {
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
                                    difficulty = 0;
                                } else if (item == 1) {
                                    mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Hard);
                                    difficulty = 1;
                                } else if (item == 2) {
                                    mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
                                    difficulty = 2;

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
        humanCountView.setText("Human: "+count_human);
        androidCountView.setText("Android: "+count_android);
        tieCountView.setText("Ties: "+count_ties);
        restoreDifficult(difficulty);
    }
    // Handles clicks on the game board buttons
    @Override
    protected void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bambu);
        mVictory = MediaPlayer.create(getApplicationContext(), R.raw.victory);
        mTie = MediaPlayer.create(getApplicationContext(), R.raw.tie);
        mDefeat = MediaPlayer.create(getApplicationContext(), R.raw.defeat);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
        mVictory.release();
        mTie.release();
        mDefeat.release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharArray("board", mGame.getBoardState());
        outState.putBoolean("mGameOver", mGameOver);
        outState.putInt("mHumanWins", Integer.valueOf(count_human));
        outState.putInt("mComputerWins", Integer.valueOf(count_android));
        outState.putInt("mTies", Integer.valueOf(count_ties));
        outState.putCharSequence("info", mInfoTextView.getText());
        outState.putInt("difficulty", difficulty);
    }
}

