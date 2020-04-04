package com.example.finalyearprojects.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalyearprojects.R;
import com.example.finalyearprojects.Semester_List;

import java.net.Inet4Address;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    private CardView btech, mca, bca, msc, bsc, mcom, bcom, ma, ba;
    String  serial;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btech = root.findViewById(R.id.btech);
        mca = root.findViewById(R.id.mca);
        bca = root.findViewById(R.id.bca);
        msc = root.findViewById(R.id.msc);
        bsc = root.findViewById(R.id.bsc);
        mcom = root.findViewById(R.id.mcom);
        bcom = root.findViewById(R.id.bcom);
        ma = root.findViewById(R.id.ma);
        ba = root.findViewById(R.id.ba);

        try {

                bca.setOnClickListener(this);
                btech.setOnClickListener(this);
                mca.setOnClickListener(this);
                bca.setOnClickListener(this);
                msc.setOnClickListener(this);
                bsc.setOnClickListener(this);
                mcom.setOnClickListener(this);
                bcom.setOnClickListener(this);
                ma.setOnClickListener(this);
                ba.setOnClickListener(this);
            }
            catch(Exception e)
            {
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

        return root;
    }

    @Override
    public void onClick(View v) {
        createAlertBox();
        Intent intent = new Intent(getActivity(),Semester_List.class);
        switch (v.getId())
        {
            case R.id.btech:
            {
                serial = "btech";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
            case R.id.mca:
            {
                serial = "mca";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
            case R.id.bca:
            {

                serial = "bca";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
            case R.id.msc:
            {
                serial = "msc";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
            case R.id.bsc:
            {
                serial = "bsc";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
            case R.id.mcom:
            {
                serial = "mcom";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
            case R.id.bcom:
            {
                serial = "bcom";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
            case R.id.ma:
            {
                serial = "ma";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
            case R.id.ba:
            {
                serial = "ba";
                intent.putExtra("course",serial);
                startActivity(intent);
                return;
            }
        }
    }
    public void createAlertBox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(layoutParams);
        builder.setView(progressBar);
    }
}
