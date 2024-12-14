package com.example.projetomenandro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projetomenandro.R;
import com.example.projetomenandro.Util.configuraBD;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityMenu extends AppCompatActivity {
    private FirebaseAuth auth;
    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = configuraBD.fireauth();

        drawerLayout = findViewById(R.id.drawerLayout);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        // Substituindo o botão "Jogar" pela lógica do CardView
        CardView cardJogar = findViewById(R.id.card_jogar); // CardView com o ID "card_jogar"
        cardJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quando o card "Jogar" for clicado, abre a tela de jogo (GameMenuActivity)
                Intent intent = new Intent(MainActivityMenu.this, MainActivity.class); // Substitua "GameMenuActivity" pelo nome correto da sua Activity de jogo
                startActivity(intent);
            }
        });

        // Substituindo o botão "Aulas" pela lógica do CardView
        CardView cardAulas = findViewById(R.id.card_estudar); // CardView com o ID "card_aulas"
        cardAulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quando o card "Aulas" for clicado, abre a tela de AulasActivity
                Intent intent = new Intent(MainActivityMenu.this, AulasActivity.class);
                startActivity(intent);
            }
        });
        // Outras lógicas para os outros botões, como o de Emergência
        CardView cardemergencia = findViewById(R.id.card_emergencia); // CardView com o ID "card_aulas"
        cardemergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emergencyIntent = new Intent(MainActivityMenu.this, EmergencyActivity.class);
                startActivity(emergencyIntent);
            }
        });

        // Configure a navegação
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navMenu) {
                    Toast.makeText(MainActivityMenu.this, "Perfil Clicado", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.navCart) {
                    Toast.makeText(MainActivityMenu.this, "Sobre Clicado", Toast.LENGTH_SHORT).show();
                }   else if (itemId == R.id.navFeedBack) {
                    Toast.makeText(MainActivityMenu.this, "FeedBack Clicado", Toast.LENGTH_SHORT).show();
                    // Navegar para a Activity de Feedback
                    Intent intent = new Intent(MainActivityMenu.this, Feedback.class);
                    startActivity(intent);
                } else if (itemId == R.id.navTerms) {
                    Toast.makeText(MainActivityMenu.this, "Termos Clicado", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.navContact) {
                    Toast.makeText(MainActivityMenu.this, "Contatos Clicado", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.navShare) {
                    deslogar();
                } else if (itemId == R.id.card_jogar) {
                    Intent intent = new Intent(MainActivityMenu.this, MainActivity.class);
                    startActivity(intent);
                }

                // Fechar o Drawer após selecionar um item
                drawerLayout.close();
                return true;
            }
        });
    }

    // Método para deslogar
    public void deslogar() {
        Toast.makeText(this, "Deslogar", Toast.LENGTH_SHORT).show();
        try {
            auth.signOut();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
