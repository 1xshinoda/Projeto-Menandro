package com.example.projetomenandro.Activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetomenandro.R;
import com.bumptech.glide.Glide;

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
    private int level = 1;  // 1 = Fácil, 2 = Médio, 3 = Difícil

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

        // Embaralha os animais e configura o som e as instruções iniciais
        shuffleAnimals();
        playAnimalSound(animals[currentAnimalIndex]);
        updateInstruction();

        // Carregar os GIFs usando Glide
        Glide.with(this).asGif().load(R.drawable.dog_gif).into(ivDog);
        Glide.with(this).asGif().load(R.drawable.cat_gif).into(ivCat);
        Glide.with(this).asGif().load(R.drawable.bird_gif).into(ivBird);
        Glide.with(this).asGif().load(R.drawable.fish_gif).into(ivFish);

        // Definir os listeners dos animais para tornar o jogo interativo
        View.OnClickListener animalClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameActive) {
                    String selectedAnimal = getSelectedAnimal(v.getId());
                    // Reproduzir som do animal instantaneamente ao toque
                    playAnimalSound(selectedAnimal);
                    // Adicionar animação ao toque
                    animateOnTouch(v);
                    // Verificar se a escolha está correta
                    checkAnimal(selectedAnimal, v);
                }
            }
        };

        // Associar os listeners às imagens dos animais
        ivDog.setOnClickListener(animalClickListener);
        ivCat.setOnClickListener(animalClickListener);
        ivBird.setOnClickListener(animalClickListener);
        ivFish.setOnClickListener(animalClickListener);

        // Listener para reiniciar o jogo
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });

        // Listener para voltar ao menu principal
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jogo.this, MainActivityMenu.class);
                startActivity(intent);
            }
        });
    }

    // Método para embaralhar a lista de animais
    private void shuffleAnimals() {
        ArrayList<String> animalList = new ArrayList<>(Arrays.asList(animals));
        Collections.shuffle(animalList);
        animalList.toArray(animals);
    }

    // Método para reproduzir o som de um animal específico
    private void playAnimalSound(String animal) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
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

    // Método para reproduzir som de erro
    private void playErrorSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.wrong_sound);
        mediaPlayer.start();
    }

    // Método para obter o nome do animal baseado no ID da view
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

    // Método para verificar se o animal selecionado está correto
    private void checkAnimal(String selectedAnimal, View view) {
        if (selectedAnimal.equals(animals[currentAnimalIndex])) {
            showToastMessage("Correto! Muito bem!");
            animateCorrectChoice(view);
            if (currentAnimalIndex < animals.length - 1) {
                currentAnimalIndex++;
                playAnimalSound(animals[currentAnimalIndex]);
                updateInstruction();
            } else {
                tvInstruction.setText("Parabéns! Você encontrou todos os animais!");
                btnRestart.setVisibility(View.VISIBLE);
                gameActive = false;
            }
        } else {
            showToastMessage("Tente novamente.");
            playErrorSound();  // Reproduzir som de erro
            animateIncorrectChoice(view);
        }
    }

    // Método para mostrar mensagens de Toast
    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Método para animar uma escolha correta (rotação completa)
    private void animateCorrectChoice(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animator.setDuration(1000);
        animator.start();
    }

    // Método para animar uma escolha incorreta (balançar a imagem)
    private void animateIncorrectChoice(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0f, 50f, -50f, 0f);
        animator.setDuration(500);
        animator.start();
    }

    // Método para animar ao toque (efeito de pulsação)
    private void animateOnTouch(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1f);
        scaleX.setDuration(300);
        scaleY.setDuration(300);
        scaleX.start();
        scaleY.start();
    }

    // Método para reiniciar o jogo
    private void restartGame() {
        shuffleAnimals();
        currentAnimalIndex = 0;
        gameActive = true;
        playAnimalSound(animals[currentAnimalIndex]);
        updateInstruction();
        btnRestart.setVisibility(View.GONE);
    }

    // Método para atualizar as instruções na tela
    private void updateInstruction() {
        tvInstruction.setText("Toque no " + animals[currentAnimalIndex]);
    }
}
