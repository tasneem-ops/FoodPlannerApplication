package com.example.foodplanner.splash_screen.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.authentication.view.AuthenticationActivity;
import com.example.foodplanner.home_screen.view.MainActivity;


public class SplashFragment extends Fragment {
    Animation animationLogo, animationSlogan;
    ImageView logoImage;
    TextView textSlogan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logoImage = view.findViewById(R.id.logo);
        textSlogan = view.findViewById(R.id.textSlogan);
        animationLogo = AnimationUtils.loadAnimation(getContext(), R.anim.logo_enter);
        animationSlogan = AnimationUtils.loadAnimation(getContext(), R.anim.slogan_show);
        logoImage.startAnimation(animationLogo);
        textSlogan.startAnimation(animationSlogan);
        animationLogo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_viewPagerFragment);
                Intent intent = new Intent(getContext(), AuthenticationActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}