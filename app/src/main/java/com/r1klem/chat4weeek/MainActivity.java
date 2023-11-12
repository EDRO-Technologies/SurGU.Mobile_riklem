package com.r1klem.chat4weeek;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userID = fAuth.getCurrentUser().getUid();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.edit().putBoolean("is_start", true).commit();

        ImageButton chatb = findViewById(R.id.chatb);
        ImageButton besedb = findViewById(R.id.besedb);
        ImageButton menub = findViewById(R.id.menub);

        TextView firstname = findViewById(R.id.profilet);
        TextView secondname = findViewById(R.id.settingst);
        TextView thirdname = findViewById(R.id.policyt);
        TextView chattt = findViewById(R.id.chattt);

        View firstchat = findViewById(R.id.profilem);
        View secondchat = findViewById(R.id.settingsm);
        View thirdchat = findViewById(R.id.policy);



        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {



                Query query = fStore.collection("users")
                        .whereNotEqualTo("id", userID);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> dataid = new ArrayList<>();
                            List<String> dataname = new ArrayList<>();
                            List<String> datagroup = new ArrayList<>();
                            for (DocumentSnapshot documentSnapshot1 : task.getResult()){
                                dataid.add(documentSnapshot1.getString("id"));
                                dataname.add(documentSnapshot1.getString("name"));
                                datagroup.add(documentSnapshot1.getString("group"));
                            }

                            try {
                                String[] idArray = dataid.toArray(new String[0]);
                                String idar = implode("\n", idArray);
                                String infoid[] = idar.split("\\s");
                                String idhiss = String.valueOf(infoid[0]);
                                String idhiss1 = String.valueOf(infoid[1]);
                                String idhiss2 = String.valueOf(infoid[2]);


                                String[] nameArray = dataname.toArray(new String[0]);
                                String namear = implode("\n", nameArray);
                                String infoname[] = namear.split("\\|");
                                String namee1 = String.valueOf(infoname[0]);
                                String namee2 = String.valueOf(infoname[1]);
                                String namee3 = String.valueOf(infoname[2]);

                                firstchat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i= new Intent(MainActivity.this, chatt.class);
                                        i.putExtra("key",idhiss);
                                        i.putExtra("name", String.valueOf(infoname[0]));
                                        startActivity(i);
                                    }
                                });
                                secondchat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i= new Intent(MainActivity.this, chatt.class);
                                        i.putExtra("key",idhiss1);
                                        i.putExtra("name", String.valueOf(infoname[1]));
                                        startActivity(i);
                                    }
                                });
                                thirdchat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i= new Intent(MainActivity.this, chatt.class);
                                        i.putExtra("key",idhiss2);
                                        i.putExtra("name", String.valueOf(infoname[2]));
                                        startActivity(i);
                                    }
                                });

                                String[] groupArray = datagroup.toArray(new String[0]);
                                String groupar = implode("\n", groupArray);
                                String infogroup[] = groupar.split("\\|");
                                String group1 = String.valueOf(infogroup[0]);
                                String group2 = String.valueOf(infogroup[1]);
                                String group3 = String.valueOf(infogroup[2]);

                                firstname.setText(namee1);
                                secondname.append(namee2);
                                thirdname.setText(namee3);

                            }catch (ArrayIndexOutOfBoundsException ae){

                            }

                        }

                    }
                });

            }
        });





        besedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, besedaactivity.class));
            }
        });
        menub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, menuActivity.class));
            }
        });


        /*
        chatb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new chatFragment());
            }
        });
        besedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new besedaFragment());
            }
        });
        menub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
                fragmentTransaction.remove(new besedaFragment());
                fragmentTransaction.remove(new chatFragment());
                fragmentTransaction.commit(); // save the changes
                loadFragment(new menuFragment());
            }
        }); */

    }

    /*
    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    } */

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                            | View.SYSTEM_UI_FLAG_FULLSCREEN

                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static String implode(String separator, String... elements) {
        return Arrays.asList(elements).stream().collect(Collectors.joining(separator));
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}