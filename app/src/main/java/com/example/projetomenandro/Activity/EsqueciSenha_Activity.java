package com.example.projetomenandro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projetomenandro.R;
import com.google.firebase.auth.FirebaseAuth;

public class EsqueciSenha_Activity extends AppCompatActivity {

    private EditText novaSenha, confirmarSenha; // Campos para e-mail e confirmação
    private Button botaoTrocaSenha; // Botão de redefinição
    private FirebaseAuth auth; // Instância do FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esqueciasenha);

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Referenciar os elementos do layout
        novaSenha = findViewById(R.id.EditTextNovSenha);
        confirmarSenha = findViewById(R.id.EditTextConfirmNovSenha);
        botaoTrocaSenha = findViewById(R.id.BtConfirmNovSenha);

        // Configurar o botão para enviar e-mail de redefinição
        botaoTrocaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = novaSenha.getText().toString();

                // Validações
                if (email.isEmpty()) {
                    Toast.makeText(EsqueciSenha_Activity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(EsqueciSenha_Activity.this, "Digite um e-mail válido!", Toast.LENGTH_SHORT).show();
                } else {
                    // Enviar e-mail de redefinição
                    enviarEmailRedefinicao(email);
                }
            }
        });
    }

    // Método para enviar e-mail de redefinição de senha com Firebase
    private void enviarEmailRedefinicao(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sucesso no envio do e-mail
                        Toast.makeText(EsqueciSenha_Activity.this, "E-mail de redefinição enviado!", Toast.LENGTH_SHORT).show();
                        // Redirecionar para a tela de login
                        Intent intent = new Intent(EsqueciSenha_Activity.this, login_activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Falha no envio do e-mail
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Erro desconhecido";
                        Toast.makeText(EsqueciSenha_Activity.this, "Erro: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
