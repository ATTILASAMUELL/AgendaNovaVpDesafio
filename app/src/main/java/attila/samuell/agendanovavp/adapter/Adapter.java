package attila.samuell.agendanovavp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import attila.samuell.agendanovavp.R;
import attila.samuell.agendanovavp.modelo.AdicionarAgenda;
import attila.samuell.agendanovavp.view.Pesquisar;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
     Context context;
     ArrayList<AdicionarAgenda> list;

     public Adapter(Context context, ArrayList<AdicionarAgenda> list){
         this.context = context;
         this.list = list;

     }


    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(context).inflate(R.layout.adapter_lista, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.MyViewHolder holder, int position) {

        AdicionarAgenda adicionarAgenda = list.get(position);
        holder.nome.setText(adicionarAgenda.getNome());
        holder.telefone.setText(adicionarAgenda.getTelefone());
        holder.cep.setText(adicionarAgenda.getCep());
        holder.logradouro.setText(adicionarAgenda.getLogradouro());
        holder.complemento.setText(adicionarAgenda.getComplemento());
        holder.estado.setText(adicionarAgenda.getEstado());
        holder.cidade.setText(adicionarAgenda.getCidade());
        holder.infomacoes.setText(adicionarAgenda.getInformacoes());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nome,telefone,cep,logradouro,complemento,estado,cidade,infomacoes;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textnome);
            telefone = itemView.findViewById(R.id.texttelefone);
            cep = itemView.findViewById(R.id.textcep);
            logradouro = itemView.findViewById(R.id.textlogradouro);
            complemento = itemView.findViewById(R.id.textcomplemento);
            estado = itemView.findViewById(R.id.textestado);
            cidade = itemView.findViewById(R.id.textcidade);
            infomacoes = itemView.findViewById(R.id.textinfomarcoes);




        }
    }
}
