package com.example.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Colors_Picker extends DialogFragment implements OnClickListener {
    View v;
    Sql_Connection con;
    RadioGroup radioGroup;
    Button delete,cancel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.color_picker,container,false);
         radioGroup=(RadioGroup) v.findViewById(R.id.radio_group);
        con= new Sql_Connection(getActivity());
         radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, int i) {

                 if(i==R.id.light_green) {
                     con.edit_color(Integer.parseInt(getTag().toString()),R.color.costume_lightgreen);
//                     Toast.makeText(getActivity(),""+R.color.costume_lightgreen,Toast.LENGTH_LONG).show();
                 }else if(i==R.id.light_orange)
                     con.edit_color(Integer.parseInt(getTag().toString()),R.color.costume_lightorange);
                 else if(i==R.id.yello)
                     con.edit_color(Integer.parseInt(getTag().toString()),R.color.costume_yello);
                 else if(i==R.id.dark_broun)
                     con.edit_color(Integer.parseInt(getTag().toString()),R.color.costume_darkbroun);
                 else if(i==R.id.custome_default)
                     con.edit_color(Integer.parseInt(getTag().toString()),R.color.custome_default);
                 ((MainActivity) getActivity()).fillData();
                 Colors_Picker.this.dismiss();
             }
         });

        return v;
    }

    @Override
    public void onClick(View view) {
//        if(view.getId()==delete.getId()) {
//            con.delete(Integer.parseInt(getTag().toString()));
//            ((MainActivity) getActivity()).fillData();
//            this.dismiss();
//        }else if(view.getId()==cancel.getId())
//            this.dismiss();
    }
}
