package attila.samuell.agendanovavp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import attila.samuell.agendanovavp.R;
import attila.samuell.agendanovavp.config.ConfiguracaoFirebase;
import attila.samuell.agendanovavp.help.Base64Custom;
import attila.samuell.agendanovavp.modelo.Usuario;

public class Cadastro extends AppCompatActivity {

    private EditText editEmailCadastro, editSenhaCadastro, editNomeCadastro;
    private Button btnCadastrarUsuario, btnVoltarLogin;
    private FirebaseAuth autenticacao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        editEmailCadastro = findViewById(R.id.editEmailCadastro);
        editSenhaCadastro = findViewById(R.id.editSenhaCadastro);
        editNomeCadastro = findViewById(R.id.editNomeCadastro);
        btnCadastrarUsuario = findViewById(R.id.btnCadastrarUsuario);
        btnVoltarLogin = findViewById(R.id.btnVoltarLogin);


        btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO FALTA  VERIFICAÇÕES SE OS CAMPOS ESTÃO VAZIOS

                usuario = new Usuario();
                usuario.setNome(editNomeCadastro.getText().toString());
                usuario.setEmail(editEmailCadastro.getText().toString());
                usuario.setSenha(editSenhaCadastro.getText().toString());

                cadastrarUsuario();

            }
        });

        btnVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irTelaLogin = new Intent(Cadastro.this, Login.class);
                startActivity(irTelaLogin);
                finish();

            }
        });


    }

    private void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //TODO DIRECIONAR PARA A ACTIVIT PRINCIPAL
                            Intent irTelaPrincipal = new Intent(Cadastro.this, MainActivity.class);
                            startActivity(irTelaPrincipal);
                            finish();

                            String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                            usuario.setIdUsuario(idUsuario);
                            usuario.salvar();


                        } else {
                            // TODO FALTA TRATAR EXCEÇÕES
                        }
                    }
                });

    }

}