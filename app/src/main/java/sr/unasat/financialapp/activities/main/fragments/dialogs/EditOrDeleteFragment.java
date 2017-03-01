package sr.unasat.financialapp.activities.main.fragments.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sr.unasat.financialapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditOrDeleteFragment extends DialogFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit_or_delete, container, false);


    }


}

