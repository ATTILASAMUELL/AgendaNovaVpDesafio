package attila.samuell.agendanovavp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import attila.samuell.agendanovavp.R;
import attila.samuell.agendanovavp.config.ConfiguracaoFirebase;
import attila.samuell.agendanovavp.modelo.Usuario;

public class Login extends AppCompatActivity {
    private EditText editEmaillogin, editSenhaLogin;
    private Button btnLogin;
    private TextView txtCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmaillogin = findViewById(R.id.editEmaillogin);
        editSenhaLogin = findViewById(R.id.editSenhaLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtCadastrar = findViewById(R.id.txtCadastrar);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO IMPLEMENTAR VERIFICIÇÕES DOS CAMPOS , VAZIO OU NÃO.
                usuario = new Usuario();
                usuario.setEmail(editEmaillogin.getText().toString());
                usuario.setSenha(editSenhaLogin.getText().toString());
                validarLogin();


            }
        });


        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO IMPLANTAR EVENTO PARA IR A TELA DE CADASTRO
                Intent irTelaCadastro = new Intent(Login.this, Cadastro.class);
                startActivity(irTelaCadastro);
                finish();
            }
        });


    }

    public void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //TODO IMPLEMENTAR METODO PARA IRMOS A TELA PRINCIPAL.
                    Intent irTelaPrincipal1 = new Intent(Login.this, MainActivity.class);
                    startActivity(irTelaPrincipal1);
                    finish();

                } else {

                    Toast.makeText(Login.this, "deu erro", Toast.LENGTH_LONG).show();

                    //TODO IMPLEMENTAR EXCEÇÕES.

                }
            }
        });

    }
}