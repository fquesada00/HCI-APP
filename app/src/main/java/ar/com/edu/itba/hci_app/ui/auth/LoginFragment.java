package ar.com.edu.itba.hci_app.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import ar.com.edu.itba.hci_app.ui.main.MainActivity;
import ar.com.edu.itba.hci_app.databinding.FragmentLoginBinding;
import ar.com.edu.itba.hci_app.AppPreferences;

import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class LoginFragment extends BaseFragment<AuthViewModel, FragmentLoginBinding, UserRepository> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.buttonLogin.setOnClickListener(v -> {
            String username = binding.editTextTextEmailAddress.getText().toString().trim();
            String password = binding.editTextTextPassword.getText().toString().trim();
            //TODO add input validations
            Log.d("send", "onActivityCreated: sending data");
            viewModel.login(username, password).observe(getViewLifecycleOwner(), tokenResource -> {
                switch (tokenResource.getStatus()) {
                    case SUCCESS:
                        AppPreferences preferences = new AppPreferences(getContext());
                        preferences.setAuthToken(tokenResource.getData().getToken());
                        Toast.makeText(requireContext(), tokenResource.toString(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Log.d("UI", "onActivityCreated: default");

                }

            });
        });
    }

    @Override
    public Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    public FragmentLoginBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    @Override
    public UserRepository getFragmentRepository() {
        return BaseRepository.getUserRepository(getContext());
    }
}