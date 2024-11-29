package com.example.projetomenandro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetomenandro.Activity.cadastro_activity;
import com.example.projetomenandro.Modelos.Usuario;
import com.example.projetomenandro.R;
import com.example.projetomenandro.Util.configuraBD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import android.widget.TextView;

public class login_activity extends AppCompatActivity {
    EditText CampoEmail, CampoSenha;
    Button botaologin;
    private FirebaseAuth auth;
    TextView BTcadastrar; // Referência para o TextView de cadastro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Defina o layout XML com TextView
        auth = configuraBD.fireauth();

        inicializarCampo();

        // Encontrando o TextView pelo ID
        BTcadastrar = findViewById(R.id.BTcadastrar);

        // Configurando o OnClickListener no TextView para redirecionar para a tela de cadastro
        BTcadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar(v); // Chama o método cadastrar, que vai para a tela de cadastro
            }
        });
    }

    // Método que chama a nova Activity para cadastro
    public void cadastrar(View v) {
        Intent i = new Intent(this, cadastro_activity.class); // Cria um Intent para a nova Activity
        startActivity(i); // Inicia a nova Activity (tela de cadastro)
    }

    // Método para validar campos de login
    public void validarcampos(View view) {
        String email = CampoEmail.getText().toString();
        String senha = CampoSenha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);

                logar(usuario);
            } else {
                Toast.makeText(this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Preencha o Email!", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para fazer login
    private void logar(Usuario usuario) {
        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    abrirMenu();
                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuário não cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Email ou senha inválidos!";
                    } catch (Exception e) {
                        excecao = "Erro ao logar! " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(login_activity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para abrir o menu principal após login
    private void abrirMenu() {
        Intent i = new Intent(login_activity.this, MainActivityMenu.class);
        startActivity(i);
    }

    // Método chamado no início da Activity, para verificar se o usuário já está autenticado
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioauth = auth.getCurrentUser();
        if (usuarioauth != null) {
            abrirMenu();
        }
    }

    // Inicializando os campos da tela de login
    private void inicializarCampo() {
        CampoEmail = findViewById(R.id.EditEmailLogin);
        CampoSenha = findViewById(R.id.EditLoginSenha);
        botaologin = findViewById(R.id.BotaoLogin);
    }
}