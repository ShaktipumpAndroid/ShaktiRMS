package com.shaktipumps.shakti_rms.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shaktipumps.shakti_rms.R;


public class FragmentWalkTroughone extends Fragment {

    ImageView imageView;
    public int drawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_walk_troughone, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // imageView = view.findViewById(R.id.iv_walkthrough);
       // Glide.with(this).load(drawable).into(imageView);
       // Picasso.with(getActivity()).load(drawable).into(imageView);
    }


}
