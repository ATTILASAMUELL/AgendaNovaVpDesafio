package attila.samuell.agendanovavp.modelo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import attila.samuell.agendanovavp.config.ConfiguracaoFirebase;

public class Usuario {
    private String idUsuario;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    @Exclude
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvar() {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("usuario").child(this.idUsuario).setValue(this);
    }
}
