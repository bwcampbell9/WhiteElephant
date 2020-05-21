package com.example.white_elephant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.white_elephant.models.ItemModel;
import com.example.white_elephant.util.Database;

public class PostItemFragment extends Fragment {

    EditText nameEditText;
    EditText descEditText;
    EditText valEditText;
    EditText postErrorEditText;

    Button addItemBtn;

    public PostItemFragment() {
        // required empty constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static PostItemFragment newInstance() {
        return new PostItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_post_item, container, false);

        nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        descEditText = (EditText) view.findViewById(R.id.descEditText);
        valEditText = (EditText) view.findViewById(R.id.valEditText);
        //postErrorEditText = (EditText) view.findViewById(R.id.postErrorEditText);
        addItemBtn = (Button) view.findViewById(R.id.addItemBtn);

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String desc = descEditText.getText().toString().trim();
                Double val = Double.valueOf(0);

                ItemModel item = new ItemModel();
                item.setName(name);
                item.setDescription(desc);

                Toast.makeText(getActivity(), "Item Added Successfully", Toast.LENGTH_LONG).show();
                try{
                    val = Double.parseDouble(valEditText.getText().toString().trim());
                    item.setValue(val);
                    if (name.length() <= 0 || val < 0){
                        postErrorEditText.setText("Item Not Added: Invalid Input");
                    } else{
                        Database.getInstance().pushItem(item);
                        Toast.makeText(getActivity(), "Item Added Successfully", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    postErrorEditText.setText("Item Not Added: Invalid Input");
                }

            }
        });

        return view;
    }
}
