package com.chrisgalhur.dicequest.presenter;

public interface MainPresenter {
    int onDiceImageClicked();
    void onRollButtonClicked();
    void loginUser(String username, String password);
    void registerUser(String username, String password);
}
