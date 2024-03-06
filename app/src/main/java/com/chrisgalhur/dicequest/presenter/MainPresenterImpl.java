package com.chrisgalhur.dicequest.presenter;

import com.chrisgalhur.dicequest.R;
import com.chrisgalhur.dicequest.model.MainModelImpl;
import com.chrisgalhur.dicequest.view.MainView;

public class MainPresenterImpl implements MainPresenter{

    private MainView view;
    private MainModelImpl model;
    private int counter = 0;

    public MainPresenterImpl(MainView view, MainModelImpl model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public int onDiceImageClicked() {
        counter++;
        int imageResource;

        switch (counter) {
            case 1:
                imageResource = R.drawable.die_face1;
                break;
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
                imageResource = R.drawable.die_noface;
                break;
            case 3:
                imageResource = R.drawable.die_face2;
                break;
            case 5:
                imageResource = R.drawable.die_face3;
                break;
            case 7:
                imageResource = R.drawable.die_face4;
                break;
            case 9:
                imageResource = R.drawable.die_face5;
                break;
            case 11:
                imageResource = R.drawable.die_face6;
                break;
            case 12:
                imageResource = R.drawable.die_noface;
                counter = 0;
                break;
            default:
                imageResource = R.drawable.die_noface;
                break;
        }
        view.updateDiceImage(imageResource);
        return counter;
    }

    @Override
    public void onRollButtonClicked() {
        view.rollDice();
    }

    @Override
    public void loginUser(String username, String password) {
        model.loginUser(username, password);
        view.onUserLoggedIn();
        view.onErrorLoginUser();
    }

    @Override
    public void registerUser(String username, String password) {
        model.loginUser(username, password);

        view.onUserRegistered();
        view.onErrorRegisterUser();
    }
}
