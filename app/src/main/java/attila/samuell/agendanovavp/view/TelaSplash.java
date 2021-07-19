package attila.samuell.agendanovavp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import attila.samuell.agendanovavp.R;

public class TelaSplash extends AppCompatActivity {

    // tempo em milisegundos, usando no metodo trocar de tela
    int tempo = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);

        //Esconder Action bar
        getSupportActionBar().hide();


        //chama o metodo para trocar de tela.
        trocarDeTela();
    }

    // metodo trocar de tela
    private void trocarDeTela() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent trocardeTela = new Intent(TelaSplash.this, Login.class);
                startActivity(trocardeTela);
                finish();

            }
        }, tempo);
    }
}