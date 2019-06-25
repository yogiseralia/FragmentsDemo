package com.yesyoudreamagain.fragmentsdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int RESID_FRAGMENT_CONTAINER = R.id.frmlayout_fragment_container;
    private static final String SECOND_TRANSACTION = "SECOND_TRANSACTION";
    private static final String FIRST_FRAG_TAG = "FIRST_FRAG_TAG";
    private static final String SECOND_FRAG_TAG = "SECOND_FRAG_TAG";
    private String BASE_TRANSACTION = "BASE";
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment secondFrag = getSupportFragmentManager().findFragmentByTag(SECOND_FRAG_TAG);

//        SET RETAIN INSTANCE STATE
        /*if (secondFrag != null) {
          // if this fragment is not in back stack -- onDestroy & onCreate won't be called
          secondFrag.setRetainInstance(true);
        }*/

                // HIDE/SHOW CODE -- life cycle methods called upto onpause/onreume on config change even when hidden
        /*if (secondFrag != null && secondFrag.isHidden()) {
          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.show(secondFrag);
          fragmentTransaction.commit();
        }else if(secondFrag != null) {
          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.hide(secondFrag);
          fragmentTransaction.commit();
        }*/

                // ATTACH/DETACH CODE -- life cycle methods called upto ondestroyview-ondestroy-ondetach/onattach on config change even when detached
//        if (secondFrag != null && secondFrag.isDetached()) {
//          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//          fragmentTransaction.attach(secondFrag);
//          fragmentTransaction.commit();
//        }else if(secondFrag != null) {
//          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//          fragmentTransaction.detach(secondFrag);
//          fragmentTransaction.commit();
//        }

                // ADD/REMOVE CODE -- life cycle methods called upto ondestroyview-ondestroy-ondetach on config change even when detached and sice
                // this fragment is no longer part of fragManager, onAttach wont be called after config change
                if (secondFrag != null && secondFrag.isAdded()) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.remove(secondFrag);
                    fragmentTransaction.commit();
                } else if (secondFrag != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(RESID_FRAGMENT_CONTAINER, secondFrag);
                    fragmentTransaction.commit();
                }
            }
        });
        if (savedInstanceState == null) {
            FirstFragment firstFragment = FirstFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(RESID_FRAGMENT_CONTAINER, firstFragment, FIRST_FRAG_TAG);
            fragmentTransaction.addToBackStack(BASE_TRANSACTION);
            fragmentTransaction.commit();

            SecondFragment secondFragment = SecondFragment.newInstance();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(RESID_FRAGMENT_CONTAINER, secondFragment, SECOND_FRAG_TAG);
            fragmentTransaction.addToBackStack(SECOND_TRANSACTION);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
