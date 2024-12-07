package com.example.projetomenandro.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projetomenandro.R;

public class EmergencyActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 100;
    private String emergencyMessage = "Mensagem de emergência: Por favor, preciso de ajuda!";
    private String emergencyPhoneNumber = "48284828423";  // Número de telefone
    private String emergencyEmail = "felipe.boaventura@ba.estudante.senai.br";  // Email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Button btnSendEmergency = findViewById(R.id.btn_send_emergency);
        Button btnSendEmail = findViewById(R.id.btn_send_email);

        // Listener para enviar SMS
        btnSendEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmergencySMS();
            }
        });

        // Listener para enviar E-mail
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmergencyEmail();
            }
        });
    }

    // Método para enviar SMS de emergência
    private void sendEmergencySMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        } else {
            // Permissão já concedida
            sendSMS();
        }
    }

    // Método para efetivamente enviar o SMS
    private void sendSMS() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(emergencyPhoneNumber, null, emergencyMessage, null, null);
            Toast.makeText(this, "Mensagem de emergência enviada!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Falha ao enviar mensagem: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // Método para tratar a resposta da solicitação de permissão de SMS
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida
                sendSMS();
            } else {
                // Permissão negada
                Toast.makeText(this, "Permissão para enviar SMS negada.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // Método para enviar E-mail de emergência
    private void sendEmergencyEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emergencyEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Emergência - Preciso de Ajuda!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emergencyMessage);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar e-mail de emergência..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Não há aplicativos de e-mail instalados.", Toast.LENGTH_SHORT).show();
        }
    }
}
