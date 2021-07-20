package attila.samuell.agendanovavp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import attila.samuell.agendanovavp.R;

public class TelaPerfil extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        //TODO Falta implementar a tela perfil, capturando nome, email do usuario e melhorando a parte das instruções.



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_add_agenda:
                Intent irTelaPesquisarDoperfil = new Intent(TelaPerfil.this, MainActivity.class);
                startActivity(irTelaPesquisarDoperfil);
                finish();


                break;
            case R.id.item_ver_lista:
                Intent irTelaVerlista = new Intent(TelaPerfil.this, Pesquisar.class);
                startActivity(irTelaVerlista);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


}