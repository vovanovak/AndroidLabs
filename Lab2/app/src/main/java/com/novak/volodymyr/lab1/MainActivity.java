package com.novak.volodymyr.lab1;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements Calculate.OnFragmentInteractionListener,
        Parameters.OnFragmentInteractionListener,
        Options.OnFragmentInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public CalculationData onFragmentInteraction() {
        Parameters parametersFragment = (Parameters) getSupportFragmentManager().findFragmentById(R.id.fragment_parameters);
        Options optionsFragment = (Options) getSupportFragmentManager().findFragmentById(R.id.fragment_options);

        return new CalculationData(parametersFragment.getData(), optionsFragment.getData());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
