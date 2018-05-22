package com.novak.volodymyr.lab1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Calculate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Calculate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calculate extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView textViewResultHeader;
    private OnFragmentInteractionListener mListener;

    public Calculate() {
    }

    private double performCalculation(CalculationData calculationData) throws ArithmeticException {
        double res = Double.MIN_VALUE;

        switch (calculationData.operation) {
            case '+':
                res = calculationData.parametersData.a + calculationData.parametersData.b;
                break;
            case '-':
                res = calculationData.parametersData.a - calculationData.parametersData.b;
                break;
            case '*':
                res = calculationData.parametersData.a * calculationData.parametersData.b;
                break;
            case '/':
                if (calculationData.parametersData.b == 0)
                    throw new ArithmeticException("Division on zero is illegal!");
                res = calculationData.parametersData.a / calculationData.parametersData.b;
                break;
        }

        return res;
    }

    private void printResult(String textViewResultHeaderText) {
        textViewResultHeader.setText(textViewResultHeaderText);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calculate.
     */
    // TODO: Rename and change types and number of parameters
    public static Calculate newInstance(String param1, String param2) {
        Calculate fragment = new Calculate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate, container, false);
        Button buttonCalc = view.findViewById(R.id.buttonCalc);
        Button buttonGoToHistory = view.findViewById(R.id.buttonGoToHistory);
        buttonCalc.setOnClickListener(this);
        buttonGoToHistory.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCalc:
                try {
                    onCalculate(v);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.buttonGoToHistory:
                mListener.navigateToHistory();
                break;
            default:
                break;
        }
    }

    public void onCalculate(View view) throws FileNotFoundException {
        if (mListener != null) {
            this.textViewResultHeader = getView().findViewById(R.id.textViewResultHeader);

            CalculationData data = mListener.onFragmentInteraction();

            String textViewResultHeaderText;

            try {
                double res = performCalculation(data);
                textViewResultHeaderText = "Result: " + res;
                mListener.writeDataToFile(new CalculationResult(data, res, new Date()));
            } catch (ArithmeticException e) {
                textViewResultHeaderText = e.getMessage();
            }

            printResult(textViewResultHeaderText);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        CalculationData onFragmentInteraction();
        void writeDataToFile(CalculationResult result) throws FileNotFoundException;
        void navigateToHistory();
    }
}
