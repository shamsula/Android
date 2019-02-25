package com.shamsul.shamsulexpense;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class AccountFragment extends Fragment { FragmentActivity listener;
    //FragmentActivity listener;
    String specialText;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        //super.onCreateView(inflater, parent, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_layout, parent,
                false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.myfragment_toolbar);
        toolbar.setTitle("My Dynamic Fragment ..... ");
        toolbar.inflateMenu(R.menu.myfragment_menu);

        TextView textView= (TextView) view.findViewById (R.id.textView2);
        textView.setText (specialText);

        return view;//inflater.inflate(R.layout.fragment_1, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etF1 = (EditText) view.findViewById(R.id.etF1);
        // -------
    }

    public String getSpecialText ()
    {
        return specialText;
    }

    public void setSpecialText (String specialText)
    {
        this.specialText = specialText;
    }
}
