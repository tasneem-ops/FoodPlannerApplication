package com.example.foodplanner.splash_screen.view;

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

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SplashFragment extends Fragment {
    Animation animationLogo, animationSlogan;
    ImageView logoImage;
    TextView textSlogan;
    Disposable timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                timer = Completable.timer(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(()-> {
                                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_viewPagerFragment);
                                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer != null && !timer.isDisposed()){
            timer.dispose();
        }
    }
}