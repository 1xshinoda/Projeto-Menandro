package com.example.projetomenandro.Activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetomenandro.R;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class jogo extends AppCompatActivity {

    private TextView tvInstruction;
    private TextView tvTitle;
    private Button btnRestart;
    private ImageButton imageButton;
    private MediaPlayer mediaPlayer;
    private String[] animals = {"cachorro", "gato", "peixe", "pássaro"};
    private int currentAnimalIndex = 0;
    private boolean gameActive = true;
    private int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encontreoanimal);

        tvTitle = findViewById(R.id.tv_title);
        tvInstruction = findViewById(R.id.tv_instruction);
        ImageView ivDog = findViewById(R.id.iv_dog);
        ImageView ivCat = findViewById(R.id.iv_cat);
        ImageView ivBird = findViewById(R.id.iv_bird);
        ImageView ivFish = findViewById(R.id.iv_fish);
        btnRestart = findViewById(R.id.btn_restart);
        imageButton = findViewById(R.id.voltarmenu1);

        shuffleAnimals();
        playAnimalSound(animals[currentAnimalIndex]);
        updateInstruction();

        Glide.with(this).asGif().load(R.drawable.dog_gif).into(ivDog);
        Glide.with(this).asGif().load(R.drawable.cat_gif).into(ivCat);
        Glide.with(this).asGif().load(R.drawable.bird_gif).into(ivBird);
        Glide.with(this).asGif().load(R.drawable.fish_gif).into(ivFish);

        View.OnClickListener animalClickListener = v -> {
            if (gameActive) {
                String selectedAnimal = getSelectedAnimal(v.getId());
                playAnimalSound(selectedAnimal);
                animateOnTouch(v);
                checkAnimal(selectedAnimal, v);
            }
        };

        ivDog.setOnClickListener(animalClickListener);
        ivCat.setOnClickListener(animalClickListener);
        ivBird.setOnClickListener(animalClickListener);
        ivFish.setOnClickListener(animalClickListener);

        btnRestart.setOnClickListener(v -> restartGame());

        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(jogo.this, MainActivityMenu.class);
            startActivity(intent);
        });
    }

    private void shuffleAnimals() {
        ArrayList<String> animalList = new ArrayList<>(Arrays.asList(animals));
        Collections.shuffle(animalList);
        animalList.toArray(animals);
    }

    private void playAnimalSound(String animal) {
        stopCurrentAnimalSound();
        switch (animal) {
            case "cachorro":
                mediaPlayer = MediaPlayer.create(this, R.raw.dog_bark);
                break;
            case "gato":
                mediaPlayer = MediaPlayer.create(this, R.raw.cat_meow);
                break;
            case "pássaro":
                mediaPlayer = MediaPlayer.create(this, R.raw.bird_chirp);
                break;
            case "peixe":
                mediaPlayer = MediaPlayer.create(this, R.raw.bubble_sound);
                break;
        }
        mediaPlayer.start();
    }

    private void playErrorSound() {
        stopCurrentAnimalSound();
        mediaPlayer = MediaPlayer.create(this, R.raw.wrong_sound);
        mediaPlayer.start();
    }

    private String getSelectedAnimal(int viewId) {
        if (viewId == R.id.iv_dog) {
            return "cachorro";
        } else if (viewId == R.id.iv_cat) {
            return "gato";
        } else if (viewId == R.id.iv_bird) {
            return "pássaro";
        } else if (viewId == R.id.iv_fish) {
            return "peixe";
        } else {
            return "";
        }
    }

    private void checkAnimal(String selectedAnimal, View view) {
        if (selectedAnimal.equals(animals[currentAnimalIndex])) {
            showSnackbarMessage("Correto! Muito bem!");
            animateCorrectChoice(view);
            currentAnimalIndex++;
            if (currentAnimalIndex < animals.length) {
                playAnimalSound(animals[currentAnimalIndex]);
                updateInstruction();
            } else {
                tvInstruction.setText("Parabéns! Você encontrou todos os animais!");
                btnRestart.setVisibility(View.VISIBLE);
                gameActive = false;
            }
        } else {
            showSnackbarMessage("Tente novamente.");
            playErrorSound();
            animateIncorrectChoice(view);
        }
    }

    private void showSnackbarMessage(String message) {
        View container = findViewById(R.id.containerlogin);
        Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.width = container.getWidth();
        snackbarView.setLayoutParams(params);
        snackbar.show();
    }

    private void stopCurrentAnimalSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void animateCorrectChoice(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animator.setDuration(1000);
        animator.start();
    }

    private void animateIncorrectChoice(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0f, 50f, -50f, 0f);
        animator.setDuration(500);
        animator.start();
    }

    private void animateOnTouch(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1f);
        scaleX.setDuration(300);
        scaleY.setDuration(300);
        scaleX.start();
        scaleY.start();
    }

    private void restartGame() {
        shuffleAnimals();
        currentAnimalIndex = 0;
        gameActive = true;
        playAnimalSound(animals[currentAnimalIndex]);
        updateInstruction();
        btnRestart.setVisibility(View.GONE);
    }

    private void updateInstruction() {
        tvInstruction.setText("Toque no " + animals[currentAnimalIndex]);
    }
}
