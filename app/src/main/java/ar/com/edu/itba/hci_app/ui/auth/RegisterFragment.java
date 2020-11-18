package ar.com.edu.itba.hci_app.ui.auth;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ListAdapter;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentRegisterBinding;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class RegisterFragment extends BaseFragment<AuthViewModel,FragmentRegisterBinding, UserRepository> {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.buttonRegister.setOnClickListener(v -> {
            String username = binding.registerUsername.getText().toString();
            String password = binding.registerPassword.getText().toString();
            String fullName = binding.editTextTextPersonName.getText().toString();
            String email = binding.editTextTextEmailAddress.getText().toString();
            String gender = binding.spinner.getSelectedItem().toString();
            Date birthdate = new Date();

            viewModel.register(username,password,fullName,gender,birthdate,email).observe(requireActivity(),userResource->{
                if (userResource.getStatus() == Status.SUCCESS)
                {
                    Toast.makeText(getContext(),"User created",Toast.LENGTH_SHORT).show();
                }else if( userResource.getStatus() != Status.LOADING)
                    Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    public FragmentRegisterBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater,container,false);
    }

    @Override
    public UserRepository getFragmentRepository() {
        MyApplication application = (MyApplication)getActivity().getApplication();
        return application.getUserRepository();
    }
}