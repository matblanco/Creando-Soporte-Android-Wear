package cursos.com.petagramendpoint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cursos.com.petagramendpoint.R;
import cursos.com.petagramendpoint.pojo.Mascota;


public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.FotoHolder> {


    private static final String TAG = FotoAdapter.class.getName();
    private List<Mascota> fotos;
    private Context context;

    public FotoAdapter(List<Mascota> fotos, Context context) {
        this.fotos = fotos;
        this.context = context;
    }

    @Override
    public FotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i(TAG,"Creando la vista");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_mascota,parent,false);

        return new FotoHolder(view);
    }

    @Override
    public void onBindViewHolder(FotoHolder holder, int position) {

        Mascota foto = fotos.get(position);
        Log.i(TAG,"foto: "+foto.getImagen());
        Log.i(TAG,"likes: "+foto.getLikes());

        holder.cantidad.setText(String.valueOf(foto.getLikes()));
        Picasso.with(context)
                .load(foto.getImagen())
                .placeholder(R.drawable.pata).into(holder.imagen);


    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }

    public static class FotoHolder  extends RecyclerView.ViewHolder{

        private ImageView imagen;
        private TextView cantidad;

        public FotoHolder(View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagenFoto);
            cantidad = (TextView) itemView.findViewById(R.id.textoCantidad);
        }
    }
}
