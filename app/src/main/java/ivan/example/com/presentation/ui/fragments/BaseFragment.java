package ivan.example.com.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import ivan.example.com.presentation.FragmentRequestListener;
import ivan.example.com.presentation.PhotoActionListener;
import ivan.example.com.presentation.ui.activities.MainActivity;

/**
 * Created by ivan on 12.11.17.
 */

public class BaseFragment extends Fragment {

    protected FragmentRequestListener fragmentRequestListener;
    protected PhotoActionListener photoActionListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentRequestListener = (MainActivity)getActivity();
        photoActionListener = (MainActivity)getActivity();
    }
}
