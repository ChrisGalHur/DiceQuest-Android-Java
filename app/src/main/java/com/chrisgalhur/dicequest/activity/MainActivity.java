package com.chrisgalhur.dicequest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chrisgalhur.dicequest.R;
import com.chrisgalhur.dicequest.model.MainModelImpl;
import com.chrisgalhur.dicequest.presenter.MainPresenter;
import com.chrisgalhur.dicequest.presenter.MainPresenterImpl;
import com.chrisgalhur.dicequest.utility.DiceAnimator;
import com.chrisgalhur.dicequest.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    //region UI ELEMENTS
    private LinearLayout LinLayMain;
    private ConstraintLayout conLayMain;
    private ImageView ivDice;
    private ImageView ivWantRegister;
    private EditText etUsername;
    private EditText etPassword;
    //endregion UI ELEMENTS

    //region VARIABLES
    private MainPresenter presenter;
    private MainModelImpl model;
    private int counter = 0;
    private float initYPosition;
    private float initXPosition;
    //endregion VARIABLES

    //region ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivDice = findViewById(R.id.ivDiceMain);
        ivWantRegister = findViewById(R.id.ivRegisterMain);
        LinLayMain = findViewById(R.id.linLayVerMain);
        conLayMain = findViewById(R.id.consLayMain);
        etUsername = findViewById(R.id.etUsernameMain);
        etPassword = findViewById(R.id.etPasswordMain);

        presenter = new MainPresenterImpl(this, model);//Inicializa el presentador

        //region IMAGEVIEW WANT REGISTER
        ivWantRegister.setOnClickListener(v -> {
            //Cambiar la imagen del dado cada vez que se haga click en la imagen para que se vean todos los lados
            counter = presenter.onDiceImageClicked();
            });
        //endregion IMAGEVIEW WANT REGISTER

        //region BUTTON ROLL
        LinLayMain.setOnClickListener(v -> presenter.onRollButtonClicked());
        //endregion BUTTON ROLL
    }
    //endregion ON CREATE

    //region UPDATE DICE IMAGE
    @Override
    public void updateDiceImage(int imageResource) {
        ivWantRegister.setImageResource(imageResource);
    }
    //endregion UPDATE DICE IMAGE

    //region ROLL DICE
    @Override
    public void rollDice(){
        //Desactivar el botón para evitar que se haga click mientras se está realizando la animación
        LinLayMain.setEnabled(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        initXPosition = ivDice.getX();
        initYPosition = ivDice.getY();

        DiceAnimator diceAnimator = new DiceAnimator(ivDice, screenWidth, screenHeight, initXPosition, initYPosition, LinLayMain);
        diceAnimator.rollMain();

        //Activar el botón una vez que la animación haya terminado
        if (counter == 0 || counter == 2 || counter == 4 || counter == 6 || counter == 8 || counter == 10 || counter == 12) {
            //Si el usuario tiene desactivado el botón de registro haremos login
            presenter.loginUser(etUsername.getText().toString(), etPassword.getText().toString());
        } else if (counter == 1 || counter == 3 || counter == 5 || counter == 7 || counter == 9 || counter == 11) {
            //Si el usuario tiene activado el botón de registro haremos registro
            presenter.registerUser(etUsername.getText().toString(), etPassword.getText().toString());
        } else {
            Toast.makeText(this, "Error,contact support", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion ROLL DICE

    //region LOGIN USER
    @Override
    public void onUserLoggedIn() {
        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorLoginUser() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
    //endregion LOGIN USER

    //region REGISTER USER
    @Override
    public void onUserRegistered() {
        Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorRegisterUser() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
    //endregion REGISTER USER
}