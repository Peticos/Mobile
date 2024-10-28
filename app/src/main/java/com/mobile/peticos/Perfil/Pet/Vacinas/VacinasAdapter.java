package com.mobile.peticos.Perfil.Pet.Vacinas;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VacinasAdapter extends RecyclerView.Adapter<VacinasAdapter.VacinaViewHolder> {

    private List<ModelVacina> VacinasList;

    public VacinasAdapter(List<ModelVacina> VacinasList) {
        this.VacinasList = VacinasList;
    }

    @NonNull
    @Override
    public VacinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_vacina, parent, false);
        return new VacinaViewHolder(view);
    }

    APIPets apiPets;

    private void setupRetrofitFeed() {
        String API = "https://apipeticos-ltwk.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPets = retrofit.create(APIPets.class);
    }



    public  void criarDose ( VacinaViewHolder holder, View view, String data, ModelVacina vacina) {

        String dataFormatada = "";

        try {
            // Formato que o usuário digita, por exemplo, "dd/MM/yyyy"
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            Date data1 = formatoEntrada.parse(data);

            // Formato desejado para a saída, por exemplo, "yyyy-MM-dd"
            SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd");
            dataFormatada = formatoSaida.format(data1);

        } catch (ParseException e) {
            e.printStackTrace();
            // Lidar com o erro, por exemplo, avisar que o formato de data está incorreto
        }



        int id = vacina.getIdVaccine();
        int num = vacina.getDosesTaked() + 1;
        if(vacina.getNumDoses() == 0){
            num = 1;
        }

        ModelDose dose = new ModelDose(
                id,
                dataFormatada,
                num
        );
        Call<ModelRetorno> call = apiPets.insertDose(dose);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(holder.tudo.getContext(), "Cadastrado com sucesso!",  Toast.LENGTH_SHORT). show();
                    if (holder.tudo.getContext() instanceof Activity) {
                        ((Activity) holder.tudo.getContext()).recreate();
                    }
                } else {

                    Log.e("Cadastrar dose", "Erro: " + response.errorBody().toString());
                    Log.e("Cadastrar dose", "Erro: " + response.code());


                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Log.e("Cadastrar dose", "Erro: " + throwable.getMessage());
            }
        });
    }

    List<ModelDose> dosesList = new ArrayList<>();
    public void chamarDoses(VacinaViewHolder holder, ModelVacina vacina) {

    }

    public void chamarCadastrarDose (VacinaViewHolder holder, View view, ModelVacina vacina) {
        // Crie um inflater para o layout do card
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View dialogView = inflater.inflate(R.layout.activity_cadastrar_dose, null);

        // Crie o AlertDialog com o layout customizado
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(dialogView);

        // Referências aos botões e EditText no layout do dialog
        Button btnEnviar = dialogView.findViewById(R.id.btn_salvar_dose);
        Button btnFechar = dialogView.findViewById(R.id.btn_sair_dose);
        EditText data = dialogView.findViewById(R.id.data_dose); // Exemplo de EditText


        // Exibe o dialog
        AlertDialog dialog = builder.create();
        dialog.show();





        // Lógica para o botão de enviar
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dose = data.getText().toString();
                // Aqui você faz a requisição à API
                setupRetrofitFeed();
                criarDose(holder, view, dose, vacina); // Chame a função criardose(dose);
                // Fechar o dialog após a requisição
                dialog.dismiss();
            }
        });

        // Lógica para o botão de fechar
        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Fecha o dialog
            }
        });

        // Exibe o dialog
        dialog.show();
    }



    @Override
    public void onBindViewHolder(@NonNull VacinaViewHolder holder, int position) {
        ModelVacina vacina = VacinasList.get(position);

        holder.tudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamarCadastrarDose(holder, view, vacina);
            }
        });
        setupRetrofitFeed();

        holder.nomeVacina.setText(vacina.getName());
        int doses_tomadas = vacina.getDosesTaked();
        int id = vacina.getIdVaccine();

        ;
        Call<List<ModelDose>> call = apiPets.getDoses(id);
        call.enqueue(new Callback<List<ModelDose>>() {
            @Override
            public void onResponse(Call<List<ModelDose>> call, Response<List<ModelDose>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    dosesList = response.body();
                    itemDose(holder, vacina, doses_tomadas);


                } else {
                    Log.e("Cadastrar Vacina", "Erro: " + response.errorBody().toString());
                    Log.e("Cadastrar Vacina", "Erro: " + response.code());


                }
            }

            @Override
            public void onFailure(Call<List<ModelDose>> call, Throwable throwable) {
                Log.e("Cadastrar vacina", "Erro: " + throwable.getMessage());
            }
        });




    }

    public void itemDose( VacinaViewHolder holder, ModelVacina vacina, int doses_tomadas) {
        if(vacina.getNumDoses() == 1){
            if(doses_tomadas != 1){
                holder.circulo5.setImageResource(R.drawable.ic_doze_n_tomada);
            }else{
                if (dosesList != null && !dosesList.isEmpty()) {
                // Formate a data corretamente
                    holder.data5.setText(dosesList.get(0).getDateDose());
                    holder.data5.setVisibility(View.VISIBLE);
            } else {
                // Caso a lista esteja vazia, esconda o TextView ou defina um texto padrão
                holder.data5.setVisibility(View.GONE); // Ou use holder.data5.setText("Sem dados");
            }

            }
            holder.circulo5.setVisibility(View.VISIBLE);

        }
        else if(vacina.getNumDoses() == 2){
            holder.circulo3.setVisibility(View.VISIBLE);
            holder.circulo7.setVisibility(View.VISIBLE);
            if(doses_tomadas < 1){
                holder.circulo7.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo3.setImageResource(R.drawable.ic_doze_n_tomada);
            }else if(doses_tomadas == 1){
                holder.circulo7.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data3.setText(dosesList.get(0).getDateDose());
                holder.data3.setVisibility(View.VISIBLE);

            }else{

                holder.data3.setText(dosesList.get(0).getDateDose());
                holder.data7.setText(dosesList.get(1).getDateDose());
                holder.data3.setVisibility(View.VISIBLE);
                holder.data7.setVisibility(View.VISIBLE);

            }

        }
        else if(vacina.getNumDoses() == 3){
            holder.circulo1.setVisibility(View.VISIBLE);
            holder.circulo5.setVisibility(View.VISIBLE);
            holder.circulo9.setVisibility(View.VISIBLE);
            if(doses_tomadas < 1){
                holder.circulo1.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo5.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo9.setImageResource(R.drawable.ic_doze_n_tomada);
            }else if(doses_tomadas == 1){
                holder.circulo5.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo9.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data1.setText(dosesList.get(0).getDateDose());
                holder.data1.setVisibility(View.VISIBLE);
            } else if (doses_tomadas == 2) {
                holder.circulo9.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data1.setVisibility(View.VISIBLE);
                holder.data5.setVisibility(View.VISIBLE);
                holder.data1.setText(dosesList.get(0).getDateDose());
                holder.data5.setText(dosesList.get(1).getDateDose());

            }else{
                holder.data1.setText(dosesList.get(0).getDateDose());
                holder.data5.setText(dosesList.get(1).getDateDose());
                holder.data9.setText(dosesList.get(2).getDateDose());
                holder.data1.setVisibility(View.VISIBLE);
                holder.data5.setVisibility(View.VISIBLE);
                holder.data9.setVisibility(View.VISIBLE);
            }


        }
        else if(vacina.getNumDoses() == 4){
            if(doses_tomadas < 1){
                holder.circulo2.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo4.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo6.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo8.setImageResource(R.drawable.ic_doze_n_tomada);
            }else if(doses_tomadas == 1){
                holder.circulo4.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo6.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo8.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data2.setText(dosesList.get(0).getDateDose());
                holder.data2.setVisibility(View.VISIBLE);
            } else if (doses_tomadas == 2) {
                holder.circulo6.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo8.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data2.setText(dosesList.get(0).getDateDose());
                holder.data2.setVisibility(View.VISIBLE);
                holder.data4.setText(dosesList.get(1).getDateDose());
                holder.data4.setVisibility(View.VISIBLE);
            } else if (doses_tomadas == 3) {
                holder.circulo8.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data2.setText(dosesList.get(0).getDateDose());
                holder.data2.setVisibility(View.VISIBLE);
                holder.data4.setText(dosesList.get(1).getDateDose());
                holder.data4.setVisibility(View.VISIBLE);
                holder.data6.setText(dosesList.get(2).getDateDose());
                holder.data6.setVisibility(View.VISIBLE);
            }else{
                holder.data2.setText(dosesList.get(0).getDateDose());
                holder.data2.setVisibility(View.VISIBLE);
                holder.data4.setText(dosesList.get(1).getDateDose());
                holder.data4.setVisibility(View.VISIBLE);
                holder.data6.setText(dosesList.get(2).getDateDose());
                holder.data6.setVisibility(View.VISIBLE);
                holder.data8.setText(dosesList.get(3).getDateDose());
                holder.data8.setVisibility(View.VISIBLE);
            }

            holder.circulo2.setVisibility(View.VISIBLE);
            holder.circulo4.setVisibility(View.VISIBLE);
            holder.circulo6.setVisibility(View.VISIBLE);
            holder.circulo8.setVisibility(View.VISIBLE);

        }
        else if(vacina.getNumDoses() == 5){
            if(doses_tomadas < 1){
                holder.circulo1.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo3.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo5.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo7.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo9.setImageResource(R.drawable.ic_doze_n_tomada);
            }else if(doses_tomadas == 1){
                holder.circulo3.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo5.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo7.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo9.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data1.setText(dosesList.get(0).getDateDose());
                holder.data1.setVisibility(View.VISIBLE);
            } else if (doses_tomadas == 2) {
                holder.circulo5.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo7.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo9.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data1.setText(dosesList.get(0).getDateDose());
                holder.data1.setVisibility(View.VISIBLE);
                holder.data3.setText(dosesList.get(1).getDateDose());
                holder.data3.setVisibility(View.VISIBLE);
            } else if (doses_tomadas == 3) {
                holder.circulo7.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.circulo9.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data1.setText(dosesList.get(0).getDateDose());
                holder.data1.setVisibility(View.VISIBLE);
                holder.data3.setText(dosesList.get(1).getDateDose());
                holder.data3.setVisibility(View.VISIBLE);
                holder.data5.setText(dosesList.get(2).getDateDose());
                holder.data5.setVisibility(View.VISIBLE);
            } else if (doses_tomadas == 4) {
                holder.circulo9.setImageResource(R.drawable.ic_doze_n_tomada);
                holder.data1.setText(dosesList.get(0).getDateDose());
                holder.data1.setVisibility(View.VISIBLE);
                holder.data3.setText(dosesList.get(1).getDateDose());
                holder.data3.setVisibility(View.VISIBLE);
                holder.data5.setText(dosesList.get(2).getDateDose());
                holder.data5.setVisibility(View.VISIBLE);
                holder.data7.setText(dosesList.get(3).getDateDose());
                holder.data7.setVisibility(View.VISIBLE);
            }else{
                holder.data1.setText(dosesList.get(0).getDateDose());
                holder.data1.setVisibility(View.VISIBLE);
                holder.data3.setText(dosesList.get(1).getDateDose());
                holder.data3.setVisibility(View.VISIBLE);
                holder.data5.setText(dosesList.get(2).getDateDose());
                holder.data5.setVisibility(View.VISIBLE);
                holder.data7.setText(dosesList.get(3).getDateDose());
                holder.data7.setVisibility(View.VISIBLE);
                holder.data9.setText(dosesList.get(4).getDateDose());
                holder.data9.setVisibility(View.VISIBLE);
            }
            holder.circulo1.setVisibility(View.VISIBLE);
            holder.circulo3.setVisibility(View.VISIBLE);
            holder.circulo5.setVisibility(View.VISIBLE);
            holder.circulo7.setVisibility(View.VISIBLE);
            holder.circulo9.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return VacinasList.size();
    }

    public static class VacinaViewHolder extends RecyclerView.ViewHolder {
        TextView nomeVacina;
        ImageView circulo1, circulo2, circulo3, circulo4,circulo5, circulo6, circulo7, circulo8, circulo9;
        TextView data1,data2, data3, data4, data5, data6, data7,data8, data9;
        List<ModelDose> doses = new ArrayList<>();
        CardView tudo;
        int id;




        public VacinaViewHolder(@NonNull View itemView) {
            super(itemView);
            tudo = itemView.findViewById(R.id.tudo);
            nomeVacina = itemView.findViewById(R.id.nomeVacina);
            circulo1 = itemView.findViewById(R.id.circulo1);
            circulo2 = itemView.findViewById(R.id.circulo2);
            circulo3 = itemView.findViewById(R.id.circulo3);
            circulo4 = itemView.findViewById(R.id.circulo4);
            circulo5 = itemView.findViewById(R.id.circulo5);
            circulo6 = itemView.findViewById(R.id.circulo6);
            circulo7 = itemView.findViewById(R.id.circulo7);
            circulo8 = itemView.findViewById(R.id.circulo8);
            circulo9 = itemView.findViewById(R.id.circulo9);
            data1 = itemView.findViewById(R.id.data1);
            data2 = itemView.findViewById(R.id.data2);
            data3 = itemView.findViewById(R.id.data3);
            data4 = itemView.findViewById(R.id.data4);
            data5 = itemView.findViewById(R.id.data5);
            data6 = itemView.findViewById(R.id.data6);
            data7 = itemView.findViewById(R.id.data7);
            data8 = itemView.findViewById(R.id.data8);
            data9 = itemView.findViewById(R.id.data9);




        }
    }
}
