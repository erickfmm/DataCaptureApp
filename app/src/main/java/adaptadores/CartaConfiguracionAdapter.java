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
import luynk.appbeta.SeleccionaEntrenamientoVelocidad;
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
        protected TextView intentos;
        protected TextView segundosVelocidad;
        protected TextView desapareceInicio;
        protected TextView desapareceFinal;

        public CartaViewHolder(View view){
            super(view);

            this.nombre = (TextView) view.findViewById(R.id.nombre_config);
            this.intentos = (TextView) view.findViewById(R.id.intentos_config);
            this.segundosVelocidad = (TextView) view.findViewById(R.id.segundosVelocidad_config);
            this.desapareceInicio = (TextView) view.findViewById(R.id.desapareceInicio_config);
            this.desapareceFinal = (TextView) view.findViewById(R.id.desapareceFinal);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();

                    Configuracion config = new Configuracion(null,
                            nombre.getText().toString(),
                            intentos.getText().toString().replaceFirst("^(.)*: ", ""),
                            segundosVelocidad.getText().toString().replaceFirst("^(.)*: ", ""),
                            desapareceInicio.getText().toString().replaceFirst("^(.)*: ", ""),
                            desapareceFinal.getText().toString().replaceFirst("^(.)*: ", ""));

                    //Intent intent = new Intent(context, SeleccionaEntrenamientoVelocidad.class);
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
        holder.intentos.setText("Intentos: "+item.getIntentos());
        holder.segundosVelocidad.setText("Velocidad: "+item.getSegundosVelocidad());
        holder.desapareceInicio.setText("% desaparece inicio: "+item.getDesapareceInicio());
        holder.desapareceFinal.setText("% desaparece final: "+item.getDesapareceFinal());
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}
