package attila.samuell.agendanovavp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmaillogin = findViewById(R.id.editEmaillogin);
        editSenhaLogin = findViewById(R.id.editSenhaLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtCadastrar = findViewById(R.id.txtCadastrar);
        progressbar = findViewById(R.id.progressbar);


        getSupportActionBar().hide();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //variavel boleana verifica, se tiver true, salva no objeto e no banco de dados.
                boolean verificado = true;

                //Verificações doa campos digitados...


                if (TextUtils.isEmpty(editEmaillogin.getText()) || editEmaillogin.getText().toString().length() < 8) {
                    verificado = false;
                    editEmaillogin.setError("Digite o email corretamente...");
                    editEmaillogin.requestFocus();
                } else if (TextUtils.isEmpty(editSenhaLogin.getText()) || editSenhaLogin.getText().toString().length() < 6) {
                    verificado = false;
                    editSenhaLogin.setError("Digite a senha corretamente...");
                    editSenhaLogin.requestFocus();
                }
                if (verificado) {


                    usuario = new Usuario();
                    usuario.setEmail(editEmaillogin.getText().toString());
                    usuario.setSenha(editSenhaLogin.getText().toString());
                    validarLogin();


                    Log.i("Information", "Desenvolvedor: ATTILA TABORY - DESAFIO VPEXPENSES");
                    Log.i("Information", "ATTILA - Email: atila.samuell@gmail.com");


                }


                //Esconder Teclado apos terminar de digitar:
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }


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
                    progressbar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent irTelaPrincipal1 = new Intent(Login.this, MainActivity.class);
                            startActivity(irTelaPrincipal1);
                            finish();

                        }
                    }, 3000);


                } else {

                    try {
                        throw task.getException();

                    } catch (Exception e) {
                        Toast.makeText(Login.this, "Erro ao realizar o login", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    @Override
    protected void onStart() {
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if (usuarioAtual != null) {
            Intent irTelaPrincipal1 = new Intent(Login.this, MainActivity.class);
            startActivity(irTelaPrincipal1);
            finish();

        }
        super.onStart();
    }
}