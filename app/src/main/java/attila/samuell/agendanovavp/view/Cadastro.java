package attila.samuell.agendanovavp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

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

        //Esconde Action bar:
        getSupportActionBar().hide();

        btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //variavel boleana verifica, se tiver true, salva no objeto e no banco de dados.
                boolean verificado = true;

                //Verificações doa campos digitados...


                if (TextUtils.isEmpty(editNomeCadastro.getText()) || editNomeCadastro.getText().toString().length() < 8) {
                    verificado = false;
                    editNomeCadastro.setError("Digite o nome corretamente...");
                    editNomeCadastro.requestFocus();
                } else if (TextUtils.isEmpty(editEmailCadastro.getText()) || editEmailCadastro.getText().toString().length() < 8) {
                    verificado = false;
                    editEmailCadastro.setError("Digite o email corretamente...");
                    editEmailCadastro.requestFocus();
                } else if (TextUtils.isEmpty(editSenhaCadastro.getText()) || editSenhaCadastro.getText().toString().length() < 6) {
                    verificado = false;
                    editSenhaCadastro.setError("Digite uma senha corretamente, no minimo 8 numeros...");
                    editSenhaCadastro.requestFocus();
                }
                if (verificado) {
                    usuario = new Usuario();
                    usuario.setNome(editNomeCadastro.getText().toString());
                    usuario.setEmail(editEmailCadastro.getText().toString());
                    usuario.setSenha(editSenhaCadastro.getText().toString());
                    cadastrarUsuario();

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

                            //intent direciona para tela principal
                            Intent irTelaPrincipal = new Intent(Cadastro.this, MainActivity.class);
                            startActivity(irTelaPrincipal);
                            finish();

                            String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                            usuario.setIdUsuario(idUsuario);
                            usuario.salvar();


                        } else {
                            //Tratando as exceções (erros)...
                            try {
                                //throw tenta obter uma exceção...
                                throw task.getException();

                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(Cadastro.this, "Digite uma senha com no mínimo 6 caracteres.",
                                        Toast.LENGTH_LONG).show();

                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(Cadastro.this, "Esta conta já esta cadastrada.",
                                        Toast.LENGTH_LONG).show();

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(Cadastro.this, "E-mail inválido, verifique se contem o @.",
                                        Toast.LENGTH_LONG).show();


                            } catch (Exception e) {
                                Toast.makeText(Cadastro.this, "Erro",
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });

    }

}