package ar.com.edu.itba.hci_app.ui.auth;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.NoCopySpan;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.internal.TextWatcherAdapter;

import ar.com.edu.itba.hci_app.R;
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

        binding.buttonLogin.setEnabled(false);
        binding.buttonLogin.setOnClickListener(v -> {
            String username = binding.editTextTextEmailAddress.getText().toString().trim();
            String password = binding.registerPassword.getText().toString().trim();
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
        binding.editTextTextEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty() && !binding.registerPassword.getText().toString().isEmpty())
                    binding.buttonLogin.setEnabled(true);
            }
        });
        binding.registerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty() && !binding.editTextTextEmailAddress.getText().toString().isEmpty())
                    binding.buttonLogin.setEnabled(true);
            }
        });

        binding.textViewRegisterNow.setOnClickListener(v -> {
            String data = binding.textViewRegisterNow.getText().toString();
            SpannableString content = new SpannableString(data);
            content.setSpan(new UnderlineSpan(), 0, data.length(), 0);
            binding.textViewRegisterNow.setText(content);
            binding.textViewRegisterNow.setTextColor(Color.BLUE);
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_fragment_login_to_fragment_register);
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