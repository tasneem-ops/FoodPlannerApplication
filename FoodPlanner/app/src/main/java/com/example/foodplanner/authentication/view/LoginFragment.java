package com.example.foodplanner.authentication.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.foodplanner.authentication.presenter.AuthPresenter;
import com.example.foodplanner.authentication.presenter.IAuthPresenter;
import com.example.foodplanner.home_screen.view.MainActivity;
import com.example.foodplanner.model.database.LocalDataSource;
import com.example.foodplanner.model.dto.Meal;
import com.example.foodplanner.model.dto.PlanMeal;
import com.example.foodplanner.model.network.APIRemoteDataSource;
import com.example.foodplanner.model.repository.Repository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Type;
import java.util.List;

public class LoginFragment extends Fragment{
    TextView newUser;
    Button loginButton;
    ImageView googleLogin;
    TextInputEditText inputEmail, inputPassword;
    private static final int REQUEST_AUTH = 2;
    GoogleSignInOptions options;
    GoogleSignInClient client;
    FirebaseAuth mAuth;
    SharedPreferences authSharedPreferences;
    IAuthPresenter presenter;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
         client = GoogleSignIn.getClient(getActivity(),options);
         mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        presenter = new AuthPresenter(Repository.getInstance(LocalDataSource.getInstance(context), APIRemoteDataSource.getInstance()));
        if(mAuth.getCurrentUser() != null){
            onLoginSuccess();
        }
        else {
            autoLogin();
        }
        initUI(view);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                loginUser(email, password);
            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent, REQUEST_AUTH);
            }
        });
    }
    private void initUI(View view) {
        newUser = view.findViewById(R.id.newUserText);
        loginButton = view.findViewById(R.id.loginButton);
        googleLogin = view.findViewById(R.id.googleLogin);
        inputEmail = view.findViewById(R.id.inputEmailLogin);
        inputPassword = view.findViewById(R.id.inputPasswordLogin);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_AUTH) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    saveGoogleCredentials(credential);
                                    onLoginSuccess();
                                } else {
                                    onLoginFailed();
                                }
                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
    private void loginUser(String email, String password) {
        if(validateInput(email, password)){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                saveEmailCredentials(email, password);
                                onLoginSuccess();
                                Log.i("TAG", "onComplete: " + user.getIdToken(false));

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                onLoginFailed();
                            }
                        }
                    });
        }
        else{
            onInputNotValid();
        }
    }

    private boolean validateInput(String email, String password) {
        if (email.equals("") || password.equals("")) {
            return false;
        }
        return true;
    }

    public void onLoginSuccess() {
        presenter.loadDataFromFirebase();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void onInputNotValid() {
        Toast.makeText(getContext(), "Input Not Valid", Toast.LENGTH_SHORT).show();
    }
    public void onLoginFailed() {
        Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
    }
    private void saveGoogleCredentials(AuthCredential credential){
        authSharedPreferences = context.getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String credentialString = gson.toJson(credential);
        authSharedPreferences.edit().putString(AuthConstants.GOOGLE_CREDENTIALS, credentialString);
        authSharedPreferences.edit().putBoolean(AuthConstants.IS_LOGGED_IN, true);
        authSharedPreferences.edit().putString(AuthConstants.AUTH_METHOD, AuthConstants.AUTH_GOOGLE);
    }
    private void saveEmailCredentials(String email, String password){
        authSharedPreferences = context.getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
        authSharedPreferences.edit().putString(AuthConstants.EMAIL, email);
        authSharedPreferences.edit().putString(AuthConstants.PASSWORD, password);
        authSharedPreferences.edit().putBoolean(AuthConstants.IS_LOGGED_IN, true);
        authSharedPreferences.edit().putString(AuthConstants.AUTH_METHOD, AuthConstants.EMAIL);
    }
    private void autoLogin(){
        authSharedPreferences = context.getSharedPreferences(AuthConstants.AUTHENTICATION, Context.MODE_PRIVATE);
        boolean isLoggedIn = authSharedPreferences.getBoolean(AuthConstants.IS_LOGGED_IN, false);
        if(isLoggedIn){
            String method = authSharedPreferences.getString(AuthConstants.AUTH_METHOD, "");
            if(method.equals(""))
                return;
            else if(method.equals(AuthConstants.AUTH_EMAIL)){
                String email = authSharedPreferences.getString(AuthConstants.EMAIL, "");
                String password = authSharedPreferences.getString(AuthConstants.PASSWORD, "");
                loginUser(email, password);
            }
            else if(method.equals(AuthConstants.AUTH_GOOGLE)){
                Gson gson = new Gson();
                String credentialString = authSharedPreferences.getString(AuthConstants.GOOGLE_CREDENTIALS, "");
                AuthCredential credential = gson.fromJson(credentialString, AuthCredential.class);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    saveGoogleCredentials(credential);
                                    onLoginSuccess();
                                } else {
                                    onLoginFailed();
                                }
                            }
                        });
            }
        }
    }
}