package attila.samuell.agendanovavp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import attila.samuell.agendanovavp.R;
import attila.samuell.agendanovavp.adapter.Adapter;
import attila.samuell.agendanovavp.adapter.RecyclerItemClickListener;
import attila.samuell.agendanovavp.config.ConfiguracaoFirebase;
import attila.samuell.agendanovavp.help.Base64Custom;
import attila.samuell.agendanovavp.modelo.AdicionarAgenda;

public class Pesquisar extends AppCompatActivity {


    private AlertDialog dialogMod;


    RecyclerView recyclemovimento;
    DatabaseReference database;

    Adapter myAdapter;
    ArrayList<AdicionarAgenda> list;
    private AdicionarAgenda exclui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);

        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());

        recyclemovimento = findViewById(R.id.recyclemovimento);
        database = FirebaseDatabase.getInstance().getReference("adicionar").child(idUsuario);

        recyclemovimento.setHasFixedSize(true);
        recyclemovimento.setLayoutManager(new LinearLayoutManager(this));
        recyclemovimento.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclemovimento.invalidate();

        list = new ArrayList<>();

        swipe();




        //evento click  em cima de algum objeto da lista->
        recyclemovimento.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclemovimento, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }

            @Override
            public void onLongItemClick(View view, int position) {
                exclui = list.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(Pesquisar.this);
                builder.setTitle("Alterar agenda");

                // INSERIR O custom_dialog.xml no alert dialog
                View views = getLayoutInflater().inflate(R.layout.alert_dialog_novo, null);
                EditText editNomeDiolog, editTelefoneDialog, editCepDialog, editLogradouroDialog,
                        editComplementoDialog, editEstadoDialog, editCidadeDialog, editinformacoesDialog;


                editNomeDiolog = views.findViewById(R.id.editNomeDiolog);
                editTelefoneDialog = views.findViewById(R.id.editTelefoneDialog);


                editCepDialog = views.findViewById(R.id.editCepDialog);
                editLogradouroDialog = views.findViewById(R.id.editLogradouroDialog);
                editEstadoDialog = views.findViewById(R.id.editEstadoDialog);
                editCidadeDialog = views.findViewById(R.id.editCidadeDialog);
                editComplementoDialog = views.findViewById(R.id.editCidadeDialog);
                editinformacoesDialog = views.findViewById(R.id.editinformacoesDialog);

                Button btnEnviarDialog, btnCancelarDialog;

                btnEnviarDialog = views.findViewById(R.id.btnEnviarDialog);
                btnCancelarDialog = views.findViewById(R.id.btnCancelarDialog);

                editNomeDiolog.setText("" + exclui.getNome());
                editTelefoneDialog.setText("" + exclui.getTelefone());

                editCepDialog.setText("" + exclui.getCep());
                editLogradouroDialog.setText("" + exclui.getLogradouro());
                editEstadoDialog.setText("" + exclui.getEstado());
                editCidadeDialog.setText("" + exclui.getCidade());
                editComplementoDialog.setText("" + exclui.getComplemento());
                editinformacoesDialog.setText("" + exclui.getInformacoes());


                btnEnviarDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        database.child(exclui.getKey()).child("nome").setValue(editNomeDiolog.getText().toString());
                        database.child(exclui.getKey()).child("telefone").setValue(editTelefoneDialog.getText().toString());

                        database.child(exclui.getKey()).child("cep").setValue(editCepDialog.getText().toString());
                        database.child(exclui.getKey()).child("logradouro").setValue(editLogradouroDialog.getText().toString());
                        database.child(exclui.getKey()).child("estado").setValue(editEstadoDialog.getText().toString());
                        database.child(exclui.getKey()).child("cidade").setValue(editCidadeDialog.getText().toString());
                        database.child(exclui.getKey()).child("complemento").setValue(editComplementoDialog.getText().toString());
                        database.child(exclui.getKey()).child("complemento").setValue(editinformacoesDialog.getText().toString());





                        myAdapter.notifyDataSetChanged();
                        list.clear();

                        dialogMod.dismiss();


                    }
                });

                btnCancelarDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogMod.dismiss();

                    }
                });

                builder.setView(views);
                dialogMod = builder.create();

                dialogMod.show();


            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }
        ));



    }

    //TODO FALTA IMPLEMENTAR O UPDTADE(ALTERAR) E DEPOIS IMPLEMENTAR MAIS DADOS NO ADCIONAR AGENDA E DEPOIS ESTILIZAR E DEPOIS O WEB SERVICE CORREIOS.


    public void swipe() {

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                int draFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;


                return makeMovementFlags(draFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirAgenda(viewHolder);

            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclemovimento);

    }

    public void excluirAgenda(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Excluir dados da sua agenda.");
        alertDialog.setMessage("você tem certeza que deseja Excluir?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int position = viewHolder.getAdapterPosition();

                exclui = list.get(position);

                database.child(exclui.getKey()).removeValue();

                Toast.makeText(Pesquisar.this, "Excluido com sucesso!!!", Toast.LENGTH_LONG).show();


                list.remove(position);
                myAdapter.notifyDataSetChanged();



            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                myAdapter.notifyDataSetChanged();

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);

        MenuItem busca = menu.findItem(R.id.busca);

        SearchView editDebusca = (SearchView)busca.getActionView();




        editDebusca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {




                if( newText != null && !newText.isEmpty()){
                    pesquisarConversa(newText);
                }

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void pesquisarConversa(String texto){



        Query query;
        if(texto.equals("")){
            query = database.orderByChild("nome");

        }else{
            query = database.orderByChild("nome").startAt(texto).endAt(texto+"\uf8ff");
        }

        list.clear();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AdicionarAgenda adicionarAgenda = dataSnapshot.getValue(AdicionarAgenda.class);
                    adicionarAgenda.setKey(dataSnapshot.getKey());

                    list.add(adicionarAgenda);

                }

                myAdapter = new Adapter(Pesquisar.this, list);
                recyclemovimento.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        pesquisarConversa("");
    }
}

