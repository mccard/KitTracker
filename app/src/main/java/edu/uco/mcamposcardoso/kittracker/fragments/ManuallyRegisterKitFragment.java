package edu.uco.mcamposcardoso.kittracker.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import edu.uco.mcamposcardoso.kittracker.R;
import edu.uco.mcamposcardoso.kittracker.interfaces.AlunoConfirmationListener;
import edu.uco.mcamposcardoso.kittracker.interfaces.ManualRegistrationListener;
import edu.uco.mcamposcardoso.kittracker.types.KitTypeArray;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlunoConfirmationListener} interface
 * to handle interaction events.
 * Use the {@link ManuallyRegisterKitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManuallyRegisterKitFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View view;
    private boolean destroyed = false;
    Button btnKitConfirmation;
    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    EditText edtKitId;
    Spinner kit_types;
    KitTypeArray new_kit_type;

    private ManualRegistrationListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ManuallyRegisterKitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManuallyRegisterKitFragment newInstance() {
        ManuallyRegisterKitFragment fragment = new ManuallyRegisterKitFragment();

        return fragment;
    }

    public ManuallyRegisterKitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (ManualRegistrationListener) getActivity(); //getActivity()
            // returns the Activity this fragment is currently associated with.
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement ManualRegistrationListener");
        }
        new_kit_type = new KitTypeArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Selecione o Tipo de Kit");

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_manually_register_kit, container, false);

        kit_types = (Spinner) view.findViewById(R.id.spnKitTypes);
        Collections.sort(new_kit_type.kits);
        ArrayAdapter<String> adaptator = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, new_kit_type.kits);

        kit_types.setAdapter(adaptator);
        getDialog().setCanceledOnTouchOutside(false);

//        edtKitId = (EditText) view.findViewById(R.id.edtKitId);

        btnKitConfirmation = (Button) view.findViewById(R.id.btnKitCadastroConfirmar);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnKitConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onManualRegistration(";" + new_kit_type.name_to_id.getFirst(kit_types.getSelectedItem().toString()));
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ManualRegistrationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        this.destroyed = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.destroyed = true;
    }

}
