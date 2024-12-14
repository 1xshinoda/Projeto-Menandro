package com.example.projetomenandro.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.projetomenandro.R;

public class MainActivity extends AppCompatActivity {

    private CardView cardQuestionario, cardMemoria, cardAnimais;
    private ImageButton imageButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu);

        // Inicializar os componentes de acordo com os novos IDs do layout XML
        cardQuestionario = findViewById(R.id.card_questionario);
        cardMemoria = findViewById(R.id.card_memoria);
        cardAnimais = findViewById(R.id.card_animais);
        imageButton = findViewById(R.id.voltarmenu1);

        // Clique no Card do Questionário
        cardQuestionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, quests.class);
                startActivity(intent);
            }
        });

        // Clique no Card do Jogo da Memória
        cardMemoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, fase1.class);
                startActivity(intent);
            }
        });

        // Clique no Card do Encontre os Animais
        cardAnimais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, jogo.class);
                startActivity(intent);
            }
        });

        // Clique no botão de voltar para o menu principal
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityMenu.class);
                startActivity(intent);
            }
        });
    }
}
