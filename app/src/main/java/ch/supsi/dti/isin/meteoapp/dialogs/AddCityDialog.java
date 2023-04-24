package ch.supsi.dti.isin.meteoapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.IOException;
import java.net.MalformedURLException;

import ch.supsi.dti.isin.meteoapp.R;

public class AddCityDialog extends AppCompatDialogFragment {
    private EditText editTextCity;
    private AddCityDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_city_dialog, null);

        builder.setView(view)
                .setTitle("Add a new city")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String cityName = editTextCity.getText().toString();
                        try {
                            listener.applyCity(cityName);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        editTextCity = view.findViewById(R.id.city_edit_text);

        return builder.create();
    }

    public void setListener(AddCityDialogListener dataListener) {
        this.listener = dataListener;
    }

    public interface AddCityDialogListener{
        void applyCity(String cityName) throws IOException;
    }
}
