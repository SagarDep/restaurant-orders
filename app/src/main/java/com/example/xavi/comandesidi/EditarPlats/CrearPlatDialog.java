package com.example.xavi.comandesidi.EditarPlats;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.opengl.ETC1;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xavi.comandesidi.R;

/**
 * Created by xavi on 26/12/15.
 */
public class CrearPlatDialog extends DialogFragment {

    //TODO: falta fer control d'errors al introduir dades

    private TextView acceptTV, cancelTv;
    private EditText nameEt, priceEt, stockEt;
    private static int PICK_PHOTO = 11;
    private OnCreatePlatDialogResultListener onCreatePlatDialogResultListener;
    private Uri imageUri;
    private ImageView imageView;
    private FloatingActionButton fab;

    public CrearPlatDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_plat_dialog, container);
        acceptTV = (TextView) view.findViewById(R.id.textViewAccept);
        cancelTv = (TextView) view.findViewById(R.id.textViewCancel);

        nameEt = (EditText) view.findViewById(R.id.editTextName);
        priceEt = (EditText) view.findViewById(R.id.editTextPrice);
        stockEt = (EditText) view.findViewById(R.id.editTextStock);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreatePlatDialogResultListener.onNegativeResult();
                dismiss();
            }
        });
        acceptTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEt.getText().toString();
                double price = Double.parseDouble(priceEt.getText().toString());
                int stock = Integer.parseInt(stockEt.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putDouble("price", price);
                bundle.putInt("stock", stock);
                bundle.putString("imgUri", imageUri.toString());
                onCreatePlatDialogResultListener.onPositiveResult(bundle);
                dismiss();
            }
        });

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Escull una imatge"), PICK_PHOTO);
            }
        });

        getDialog().setCancelable(true);
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(getActivity().getApplicationContext(), "Error al carregar la imatge", Toast.LENGTH_LONG).show();
                return;
            }
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            imageView.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        }
    }

    public interface OnCreatePlatDialogResultListener {
        public abstract void onPositiveResult(Bundle bundle);
        public abstract void onNegativeResult();
    }

    public void setOnCreatePlatDialogResultListener(OnCreatePlatDialogResultListener listener) {
        this.onCreatePlatDialogResultListener = listener;
    }
}
