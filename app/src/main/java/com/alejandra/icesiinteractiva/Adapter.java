package com.alejandra.icesiinteractiva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejandra.icesiinteractiva.model.Proyecto;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<Proyecto> listaProyectos;

    public Adapter (Context context, ArrayList<Proyecto> listItems) {
        this.context = context;
        this.listaProyectos = listItems;
    }

    @Override
    public int getCount() {
        return listaProyectos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaProyectos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Proyecto proyecto = (Proyecto) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.proyectos, null);
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo_proyecto_renglon);
        TextView nombre_proyecto = (TextView) convertView.findViewById(R.id.nombre_proyecto);
        TextView palabras_claves_proyecto = (TextView) convertView.findViewById(R.id.palabras_claves_proyecto);

        logo.setImageResource(proyecto.getLogoProyecto());
        nombre_proyecto.setText(proyecto.getName());
        palabras_claves_proyecto.setText(proyecto.getPalabrasClaves());

        return convertView;
    }

}
