package com.chrisgalhur.dicequest.view;

public interface MainView {
    void updateDiceImage(int imageResource);
    void rollDice();
    void onUserLoggedIn();
    void onErrorLoginUser();
    void onUserRegistered();
    void onErrorRegisterUser();
}
