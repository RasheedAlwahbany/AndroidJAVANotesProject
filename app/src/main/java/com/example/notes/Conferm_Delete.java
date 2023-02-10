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

public class Conferm_Delete extends DialogFragment implements OnClickListener {
    View v;
    Sql_Connection con;
    Button delete,cancel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.conferm_delete,container,false);
         cancel=(Button)v.findViewById(R.id.Cancel_delete_conferm);
         delete=(Button)v.findViewById(R.id.Delete_Conferm);
         cancel.setOnClickListener(this);
         delete.setOnClickListener(this);
            con= new Sql_Connection(getActivity());
        return v;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==delete.getId()) {
            con.delete(Integer.parseInt(getTag().toString()));
            ((MainActivity) getActivity()).fillData();
            this.dismiss();
        }else if(view.getId()==cancel.getId())
            this.dismiss();
    }
}
