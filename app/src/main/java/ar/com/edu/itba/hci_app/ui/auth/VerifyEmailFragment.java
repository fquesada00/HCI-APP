package ar.com.edu.itba.hci_app.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentVerificarEmailBinding;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;

public class VerifyEmailFragment extends BaseFragment<AuthViewModel, FragmentVerificarEmailBinding, UserRepository> {

    private static VerifyEmailFragment fragment;

    public static VerifyEmailFragment getInstance(){
        if(fragment == null)
            fragment = new VerifyEmailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding.ConfirmBtn.setOnClickListener(v->{
            MyApplication application = (MyApplication) requireActivity().getApplication();
            application.getUserRepository().verifyEmail(binding.emailText.getText().toString(), binding.CodeText.getText().toString()).observe(getViewLifecycleOwner(),(vT)->{
                switch (vT.getStatus()){
                    case SUCCESS:
                        //TODO mandar al login
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, LoginFragment.getInstance()).commit();
                        break;
                    case LOADING:
                        //TODO mostrar loading
                        break;
                    case ERROR:
                        //TODO mostrar error
                        break;
                }
            });
        });
        return binding.getRoot();
    }

    @Override
    public Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    public FragmentVerificarEmailBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentVerificarEmailBinding.inflate(inflater,container,false);
    }

    @Override
    public UserRepository getFragmentRepository() {
        MyApplication application = (MyApplication) requireActivity().getApplication();
        return application.getUserRepository();
    }
}
