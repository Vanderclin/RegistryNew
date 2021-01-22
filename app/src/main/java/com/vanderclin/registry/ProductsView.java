package com.vanderclin.registry;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class ProductsView extends ArrayAdapter<ProductsList> {
    private Activity context;
    List<ProductsList> productsList;

    public ProductsView(Activity context, List<ProductsList> productsList) {
        super(context, R.layout.cardview_item, productsList);
        this.context = context;
        this.productsList = productsList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.cardview_item, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
		TextView textViewCode = listViewItem.findViewById(R.id.textViewCode);


        ProductsList proList = productsList.get(position);
        textViewName.setText(proList.getName());
		textViewCode.setText(proList.getCode());

        return listViewItem;
    }
}
