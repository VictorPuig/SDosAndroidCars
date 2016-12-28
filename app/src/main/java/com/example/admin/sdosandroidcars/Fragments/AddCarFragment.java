package com.example.admin.sdosandroidcars.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.FilterAvailableListener;
import com.example.admin.sdosandroidcars.R;
import com.example.admin.sdosandroidcars.adapters.ElementSpinnerAdapter;
import com.example.admin.sdosandroidcars.api.cars.Car2;
import com.example.admin.sdosandroidcars.api.cars.Cars;
import com.example.admin.sdosandroidcars.api.cars.CarsResultListener;
import com.example.admin.sdosandroidcars.api.info.Element;
import com.example.admin.sdosandroidcars.api.info.Filter;

import org.json.JSONException;
import org.json.JSONObject;

public class AddCarFragment extends BaseFragment implements View.OnClickListener, FilterAvailableListener {

    Spinner makerSpinner, colorSpinner;
    AddCarFragment self = this;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_addcar, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("AddCar");

        Button btnOk = (Button) getView().findViewById(R.id.buttonAddCarsOk);
        makerSpinner = (Spinner) getView().findViewById(R.id.makerSpinner);
        colorSpinner = (Spinner) getView().findViewById(R.id.colorSpinner);
        TextView textViewNewMaker = (TextView) getView().findViewById(R.id.textViewNewMaker);
        TextView textViewNewColor = (TextView) getView().findViewById(R.id.textViewNewColor);

        ((Drawer) getActivity()).getFilter(this);

        btnOk.setOnClickListener(this);
        textViewNewColor.setOnClickListener(this);
        textViewNewMaker.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.buttonAddCarsOk) {
            EditText editText = (EditText) getView().findViewById(R.id.modelName);

            final String modelName = editText.getText().toString();

            if (modelName.isEmpty()) {
                Toast.makeText(getContext(), "Model name empty", Toast.LENGTH_SHORT).show();
                return;
            }

            final Element color = (Element) ((Spinner) getView().findViewById(R.id.colorSpinner)).getSelectedItem();
            final Element maker = (Element) ((Spinner) getView().findViewById(R.id.makerSpinner)).getSelectedItem();

            new AlertDialog.Builder(getContext())
                    .setTitle("Are you sure?")
                    .setMessage("New car:\n" +
                            "\tName: " + modelName + "\n" +
                            "\tMaker: " + maker.getName() + "\n" +
                            "\tColor: " + color.getName())
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Sending...", Toast.LENGTH_SHORT).show();

                            Car2 newCar = new Car2(modelName, color, maker);

                            Cars.doAddCar(newCar.getJSONObject(), new CarsResultListener() {
                                @Override
                                public void onCarsResult(JSONObject json) {
                                    Toast.makeText(getContext(), "Done: " + json.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();


        } else if (id == R.id.textViewNewMaker) {
            final EditText editTextNewMaker = new EditText(getContext());

            new AlertDialog.Builder(getContext())
                    .setTitle("New maker")
                    .setMessage("Maker name:")
                    .setView(editTextNewMaker)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Sending...", Toast.LENGTH_SHORT).show();

                            String name = editTextNewMaker.getText().toString();

                            if (name.isEmpty()) {
                                Toast.makeText(getContext(), "Name is empty, try again master", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("name", name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Cars.doAddMaker(obj, new CarsResultListener() {
                                @Override
                                public void onCarsResult(JSONObject json) {
                                    Toast.makeText(getContext(), "result:" + json.toString(), Toast.LENGTH_SHORT).show();

                                    ((Drawer) getActivity()).getFilter(true, self);
                                }
                            });
                        }
                    })
                    .show();

        } else if (id == R.id.textViewNewColor) {
            final EditText editTextNewColor = new EditText(getContext());

            new AlertDialog.Builder(getContext())
                    .setTitle("New color")
                    .setMessage("Color name:")
                    .setView(editTextNewColor)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Sending...", Toast.LENGTH_SHORT).show();

                            String name = editTextNewColor.getText().toString();

                            if (name.isEmpty()) {
                                Toast.makeText(getContext(), "Name is empty, try again master", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("name", name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Cars.doAddColor(obj, new CarsResultListener() {
                                @Override
                                public void onCarsResult(JSONObject json) {
                                    Toast.makeText(getContext(), "result:" + json.toString(), Toast.LENGTH_SHORT).show();

                                    ((Drawer) getActivity()).getFilter(true, self);
                                }
                            });
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onFilterAvailable(Filter filter) {
        ElementSpinnerAdapter makerAdapter = new ElementSpinnerAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, filter.getMakers());
        makerSpinner.setAdapter(makerAdapter);

        ElementSpinnerAdapter colorAdapter = new ElementSpinnerAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, filter.getColors());
        colorSpinner.setAdapter(colorAdapter);
    }
}

