package com.example.maria.entregable3potettimarianoandroid.view;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maria.entregable3potettimarianoandroid.R;
import com.example.maria.entregable3potettimarianoandroid.model.POJO.Obra;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ObrasAdapter extends RecyclerView.Adapter {
    private NotificadorCeldaClickeada notificadorCeldaClickeada;

    private List<Obra> listadoDeObras;

    public ObrasAdapter(List<Obra> listaDeObras) {
        listadoDeObras = listaDeObras;
        if (listaDeObras == null) {
            listadoDeObras = new ArrayList<>();
        }
    }

    public ObrasAdapter(NotificadorCeldaClickeada notificadorCeldaClickeada) {
        this.notificadorCeldaClickeada = notificadorCeldaClickeada;
        listadoDeObras = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //INFLO LA CELDA
        View view = layoutInflater.inflate(R.layout.celda_obra, parent, false);
        //LE PASO LA CELDA INFLADA  A MI VIEW HOLDER!
        ObraViewHolder viewHolder = new ObraViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Obra ObraQueLeVoyAPasarAlViewHolder = listadoDeObras.get(position);
        ObraViewHolder viewHolderNoticia = (ObraViewHolder) holder;
        viewHolderNoticia.cargarObra(ObraQueLeVoyAPasarAlViewHolder);

    }

    @Override
    public int getItemCount() {
        return listadoDeObras.size();
    }

    public void agregarObras(List<Obra> ObrasQuelePasaElFinishCuandoRecibe) {
        //NO HACER ESTO PORQUE PISARIA EL LISTADO DE NOTICIAS QUE YA TENIAMOS!!!!!
        //listadoNoticias = noticias;
        //OPCION 1 PARA AGREGAR LAS NOTICIAS
        //listadoNoticias.addAll(noticias);

        //OPCION 2 PARA VERIFICAR SI YA EXISTE LA NOTICIA QUE ME PASARON PARA AGREGAR
        for (Obra obraAAgregar : ObrasQuelePasaElFinishCuandoRecibe) {
            if (!listadoDeObras.contains(obraAAgregar)) {
                listadoDeObras.add(obraAAgregar);
            }
        }
        notifyDataSetChanged();

    }


    public class ObraViewHolder extends RecyclerView.ViewHolder {
        private TextView tituloObraTextView;
        private ImageView imageViewCelda;


        public ObraViewHolder(View itemView) {
            super(itemView);
            tituloObraTextView = itemView.findViewById(R.id.text_view_celda_obra);
            imageViewCelda = itemView.findViewById(R.id.imagen_obra_celda);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Obra obraClickeada = listadoDeObras.get(getAdapterPosition());
                    notificadorCeldaClickeada.notificarCeldaClickeada(obraClickeada);
                }
            });

        }

        public void cargarObra(Obra obra) {
            tituloObraTextView.setText(obra.getName());
            if(TextUtils.isEmpty(obra.getRutaImagen())){
                return;
            }
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference reference = storage.getReference();
            reference = reference.child(obra.getRutaImagen());
            //opcion 1

            try {
                final File archivo = File.createTempFile("fotoandroid", "jpg");
                final StorageReference finalReference = reference;
                reference.getFile(archivo).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Glide.with(itemView.getContext())
                                .using(new FirebaseImageLoader())
                                .load(finalReference)
                               // .placeholder(R.drawable.placeholder16_9)
                                .error(R.drawable.error)
                                .into(imageViewCelda);

                        // Picasso.get().load(archivo.getAbsoluteFile()).into(imagenContacto);
                    }

                });
            } catch (Exception e) {


            }



        }
    }

    public interface NotificadorCeldaClickeada {
        void notificarCeldaClickeada(Obra obraClickeada);
    }

}


