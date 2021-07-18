package attila.samuell.agendanovavp.modelo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


import attila.samuell.agendanovavp.config.ConfiguracaoFirebase;
import attila.samuell.agendanovavp.help.Base64Custom;

public class AdicionarAgenda {

    private String key;
    private String nome;
    private String telefone;

    private String cep;
    private String logradouro;
    private String complemento;
    private String estado;
    private String cidade;
    private String informacoes;

    public AdicionarAgenda() {
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void salvaraAdicaoAgenda() {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("adicionar").child(idUsuario).push().setValue(this);
    }


}
