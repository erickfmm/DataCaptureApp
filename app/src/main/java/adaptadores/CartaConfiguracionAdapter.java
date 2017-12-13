package adaptadores;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import luynk.appbeta.InsertarIdUsuario;
import luynk.appbeta.R;
import model.Configuracion;

public class CartaConfiguracionAdapter extends RecyclerView.Adapter<CartaConfiguracionAdapter.CartaViewHolder>{

    private final Context mainContext;
    private final List<Configuracion> items;

    public CartaConfiguracionAdapter(Context mainContext, List<Configuracion> items) {
        this.mainContext = mainContext;
        this.items = items;
    }


    static class CartaViewHolder extends RecyclerView.ViewHolder{
        //Campos de cada item
        protected TextView nombre;
        protected TextView figura;
        protected TextView ruta;
        protected TextView desaparece;

        public CartaViewHolder(View view){
            super(view);

            this.nombre = (TextView) view.findViewById(R.id.nombre_config);
            this.figura = (TextView) view.findViewById(R.id.figura_config);
            this.ruta = (TextView) view.findViewById(R.id.ruta_config);
            this.desaparece = (TextView) view.findViewById(R.id.desaparece_config);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();

                    Configuracion config = new Configuracion(null,
                            nombre.getText().toString(),
                            figura.getText().toString(),
                            ruta.getText().toString(),
                            desaparece.getText().toString());

                    Intent intent = new Intent(context, InsertarIdUsuario.class);
                    intent.putExtra("config",config);
                    context.startActivity(intent);
                }
            });
        }
    }

    //Crear la tarjeta de configuracion
    @Override
    public CartaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_configuracion, parent, false);

        return new CartaViewHolder(view);
    }

    //Este metodo actualiza el RecyclerView.ViewHolder
    @Override
    public void onBindViewHolder(CartaViewHolder holder, int position)
    {
        Configuracion item = items.get(position);
        holder.itemView.setTag(item);

        holder.nombre.setText(item.getNombre());
        holder.figura.setText("Figura: "+item.getFigura());
        holder.ruta.setText("Ruta: "+item.getRuta());
        holder.desaparece.setText("Desaparece: "+item.getDesaparece());
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}
