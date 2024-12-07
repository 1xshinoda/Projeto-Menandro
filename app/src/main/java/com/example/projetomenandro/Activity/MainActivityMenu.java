package com.example.projetomenandro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

        Button buttonJoguinho = findViewById(R.id.joguinho);
        buttonJoguinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button buttonJoguinho2 = findViewById(R.id.joguinho2);
        buttonJoguinho2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityMenu.this, jogo.class);
                startActivity(intent);
            }
        });

        Button buttonJoguinho3 = findViewById(R.id.joguinho3);
        buttonJoguinho3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityMenu.this, quests.class);
                startActivity(intent);
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
                } else if (itemId == R.id.buttonAulas) {
                    // Navegar para a Activity de Aulas
                    Intent intent = new Intent(MainActivityMenu.this, AulasActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.navOrders) {
                    // Navegar para a Activity de Emergência
                    Intent emergencyIntent = new Intent(MainActivityMenu.this, EmergencyActivity.class);
                    startActivity(emergencyIntent);
                } else if (itemId == R.id.navFeedBack) {
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
                } else if (itemId == R.id.joguinho) {
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
