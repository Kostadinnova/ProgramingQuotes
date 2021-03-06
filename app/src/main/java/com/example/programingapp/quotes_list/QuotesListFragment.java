package com.example.programingapp.quotes_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.programingapp.model.Quote;
import com.example.programingapp.R;

import java.util.List;

import static android.view.View.GONE;

public class QuotesListFragment extends Fragment {

    public QuotesListFragment() {
        // Required empty public constructor
    }

    private View rootView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        }

        progressBar = rootView.findViewById(R.id.progressBar);
        recyclerView = rootView.findViewById(R.id.rvRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        QuotesListViewModel viewModel = ViewModelProviders.of(this).get(QuotesListViewModel.class);

        viewModel.quotes.observe(this, new Observer<List<Quote>>() {
            @Override
            public void onChanged(List<Quote> quoteList) {
                QuotesAdapter adapter = new QuotesAdapter(quoteList, getFragmentManager());
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        });
         viewModel.progressBar.observe(this, new Observer<Boolean>() {
             @Override
             public void onChanged(Boolean aBoolean) {
                 progressBar.setVisibility(aBoolean ? View.VISIBLE : GONE);
             }
         });

         viewModel.error.observe(this, new Observer<String>() {
             @Override
             public void onChanged(String s) {
                 Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
             }
         });
        viewModel.loadQuotes();

        // Inflate the layout for this fragment
        return rootView;
    }
}
