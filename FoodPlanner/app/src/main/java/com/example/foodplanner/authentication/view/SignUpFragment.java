package com.example.foodplanner.authentication.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.presenter.AuthConstants;
import com.example.foodplanner.home_screen.view.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.gson.Gson;

import java.util.Objects;

public class SignUpFragment extends Fragment{
    private FirebaseAuth mAuth;
    Button signUpButton;
    TextInputEditText inputName, inputEmail, inputPassword, inputConfrimPassword;
    TextView alreadyHavAcc;
    MaterialButton googleSignup, guestButton;
    GoogleSignInOptions options;
    GoogleSignInClient client;
    SharedPreferences authSharedPreferences;
    Context context;
    private static final int REQ_ONE_TAP = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(requireActivity(),options);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        initUI(view);
        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Objects.requireNonNull(inputName.getText()).toString();
                String email = Objects.requireNonNull(inputEmail.getText()).toString();
                String password = Objects.requireNonNull(inputPassword.getText()).toString();
                String confirmPassword = Objects.requireNonNull(inputConfrimPassword.getText()).toString();
                registerUser(name, email, password, confirmPassword);
            }
        });
        alreadyHavAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });
        googleSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent, REQ_ONE_TAP);
            }
        });
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authSharedPreferences = context.getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
                authSharedPreferences.edit().putBoolean(AuthConstants.IS_LOGGED_IN, false).apply();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }
    private void initUI(View view) {
        signUpButton = view.findViewById(R.id.signUpButton);
        inputName = view.findViewById(R.id.inputNameSignup);
        inputEmail = view.findViewById(R.id.inputEmailSignup);
        inputPassword = view.findViewById(R.id.inputPasswordSignup);
        inputConfrimPassword = view.findViewById(R.id.inputConfirmPassword);
        googleSignup = view.findViewById(R.id.googleSignup);
        guestButton = view.findViewById(R.id.guestButton);
        alreadyHavAcc = view.findViewById(R.id.alreadyHavAccText);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ONE_TAP) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Google Sign in Successful", Toast.LENGTH_SHORT).show();
                                    saveGoogleCredentials(credential);
                                    onSignupSuccess();
                                } else {
                                    Toast.makeText(context, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }
    private void registerUser(String name, String email, String password, String confirmPassword) {
        if(validateInput(name, email, password, confirmPassword)){
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            if(user != null)
                                user.updateProfile(profileUpdates);
                            saveEmailCredentials(email, password);
                            onSignupSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
        else{
            onInputNotValid();
        }
    }
    private boolean validateInput(String name, String email, String password, String confirmPassword) {
        if (email.equals("") || password.equals("")|| name.equals("") ||confirmPassword.equals("") || !(confirmPassword.equals(password))) {
            return false;
        }
        return true;
    }
    private void saveGoogleCredentials(AuthCredential credential){
        authSharedPreferences = requireActivity().getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = authSharedPreferences.edit();
        Gson gson = new Gson();
        String credentialString = gson.toJson(credential);
        editor.putString(AuthConstants.GOOGLE_CREDENTIALS, credentialString);
        editor.putBoolean(AuthConstants.IS_LOGGED_IN, true);
        editor.putString(AuthConstants.AUTH_METHOD, AuthConstants.AUTH_GOOGLE);
        editor.apply();
    }
    private void saveEmailCredentials(String email, String password){
        authSharedPreferences = requireActivity().getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = authSharedPreferences.edit();
        editor.putString(AuthConstants.EMAIL, email);
        editor.putString(AuthConstants.PASSWORD, password);
        editor.putBoolean(AuthConstants.IS_LOGGED_IN, true);
        editor.putString(AuthConstants.AUTH_METHOD, AuthConstants.EMAIL);
        editor.apply();
    }
    public void onSignupSuccess() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    public void onInputNotValid() {
        Toast.makeText(context, "Input Not Valid", Toast.LENGTH_SHORT).show();
    }
    public void onSignupFailed() {
        Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();
    }
}