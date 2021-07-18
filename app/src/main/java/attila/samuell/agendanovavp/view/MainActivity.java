package attila.samuell.agendanovavp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import attila.samuell.agendanovavp.R;
import attila.samuell.agendanovavp.config.ConfiguracaoFirebase;
import attila.samuell.agendanovavp.modelo.AdicionarAgenda;
import attila.samuell.agendanovavp.modelo.CEP;
import attila.samuell.agendanovavp.modelo.Usuario;
import attila.samuell.agendanovavp.service.HttpService;

public class MainActivity extends AppCompatActivity {

    private EditText editNomeAdicionar, editTelefoneAdicionar, editCepAdicionar, editLogradouroAdicionar,
            editComplementoAdicionar, editEstadoAdicionar, editCidadeAdicionar, editInformacoesAdicionar;
    private Button btnAdd, btnVerlista, btnBuscarCepAdicionar;
    private AdicionarAgenda adicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Carregar os EditText e Button
        editNomeAdicionar = findViewById(R.id.editNomeAdicionar);
        editTelefoneAdicionar = findViewById(R.id.editTelefoneAdicionar);
        editCepAdicionar = findViewById(R.id.editCepAdicionar);
        editLogradouroAdicionar = findViewById(R.id.editLogradouroAdicionar);
        editComplementoAdicionar = findViewById(R.id.editComplementoAdicionar);
        editEstadoAdicionar = findViewById(R.id.editEstadoAdicionar);
        editCidadeAdicionar = findViewById(R.id.editCidadeAdicionar);
        editInformacoesAdicionar = findViewById(R.id.editInformacoesAdicionar);
        btnVerlista = findViewById(R.id.btnVerlista);
        btnAdd = findViewById(R.id.btnAdd);

        //Para fazer a conexão com web services - buscar cep online
        btnBuscarCepAdicionar = findViewById(R.id.btnBuscarCepAdicionar);


        //Adciona os dados digitados pelo usuario ao objeto Adicionar Agenda e conectado ao banco de dados Firebase.
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //variavel boleana verifica, se tiver true, salva no objeto e no banco de dados.
                boolean verificado = true;

                //Verificações doa campos digitados...
/*

                if(TextUtils.isEmpty(editCepAdicionar.getText()) || editCepAdicionar.getText().toString().length() != 8){
                    verificado = false;
                    editNomeAdicionar.setError("Digite o cep corretamente...");
                    editNomeAdicionar.requestFocus();
                }
                if(TextUtils.isEmpty(editNomeAdicionar.getText()) || editNomeAdicionar.getText().toString().length() != 8){
                    verificado = false;
                    editNomeAdicionar.setError("Digite o nome completo...");
                    editNomeAdicionar.requestFocus();
                }
                if(TextUtils.isEmpty(editTelefoneAdicionar.getText()) || editTelefoneAdicionar.getText().toString().length() != 9){
                    verificado = false;
                    editTelefoneAdicionar.setError("Digite o telefone corretamente...");
                    editTelefoneAdicionar.requestFocus();
                }
                if(TextUtils.isEmpty(editLogradouroAdicionar.getText()) || editLogradouroAdicionar.getText().toString().length() != 5){
                    verificado = false;
                    editLogradouroAdicionar.setError("Digite o logradouro ou bairro corretamente...");
                    editLogradouroAdicionar.requestFocus();
                }
                if(TextUtils.isEmpty(editComplementoAdicionar.getText()) || editComplementoAdicionar.getText().toString().length() != 5){
                    verificado = false;
                    editComplementoAdicionar.setError("Digite o complemento ou bairro corretamente...");
                    editComplementoAdicionar.requestFocus();
                }
                if(!TextUtils.isEmpty(editEstadoAdicionar.getText()) || editEstadoAdicionar.getText().toString().length() != 4){
                    verificado = false;
                    editEstadoAdicionar.setError("Digite o estado corretamente...");
                    editEstadoAdicionar.requestFocus();
                }
                if(!TextUtils.isEmpty(editCidadeAdicionar.getText()) || editEstadoAdicionar.getText().toString().length() != 4){
                    verificado = false;
                    editCidadeAdicionar.setError("Digite a cidade corretamente...");
                    editCidadeAdicionar.requestFocus();
                }
                if(!TextUtils.isEmpty(editCidadeAdicionar.getText()) || editEstadoAdicionar.getText().toString().length() != 4){
                    verificado = false;
                    editCidadeAdicionar.setError("Digite a cidade corretamente...");
                    editCidadeAdicionar.requestFocus();
                }
                if(!TextUtils.isEmpty(editInformacoesAdicionar.getText()) || editInformacoesAdicionar.getText().toString().length() != 4){
                    verificado = false;
                    editInformacoesAdicionar.setError("Digite as infomações adicionais corretamente...");
                    editInformacoesAdicionar.requestFocus();
                }


*/


                // se for true , salva.
                if (verificado) {

                    adicionar = new AdicionarAgenda();
                    adicionar.setNome(editNomeAdicionar.getText().toString());
                    adicionar.setTelefone(editTelefoneAdicionar.getText().toString());
                    adicionar.setCep(editCepAdicionar.getText().toString());

                    adicionar.setLogradouro(editLogradouroAdicionar.getText().toString());
                    adicionar.setComplemento(editComplementoAdicionar.getText().toString());
                    adicionar.setEstado(editEstadoAdicionar.getText().toString());
                    adicionar.setCidade(editCidadeAdicionar.getText().toString());
                    adicionar.setInformacoes(editInformacoesAdicionar.getText().toString());


                    adicionar.salvaraAdicaoAgenda();

                }


            }
        });

        btnVerlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Envia para ver a lista de clientes salvo
                Intent irTelaPesquisar = new Intent(MainActivity.this, Pesquisar.class);
                startActivity(irTelaPesquisar);
                finish();
            }
        });

        btnBuscarCepAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CEP retorno = new HttpService(editCepAdicionar.getText().toString()).execute().get();
                    editLogradouroAdicionar.setText(retorno.getLogradouro());
                    editComplementoAdicionar.setText(retorno.getBairro());
                    editCidadeAdicionar.setText(retorno.getLocalidade());
                    editEstadoAdicionar.setText(retorno.getUf());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}