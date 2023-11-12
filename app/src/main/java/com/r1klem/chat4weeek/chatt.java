package com.r1klem.chat4weeek;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class chatt extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser user;
    String userID;
    String idhis, namehis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatt);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userID = fAuth.getCurrentUser().getUid();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String hisid = extras.getString("key");
            String hisname = extras.getString("name");
            idhis = hisid;
            namehis = hisname;
            extras.remove("name");
            extras.remove("key");
        }

        TextView nameold = findViewById(R.id.nameold);
        TextView mymsg = findViewById(R.id.mymessage);
        TextView hismsg = findViewById(R.id.hismessage);
        ImageButton sendmessageb = findViewById(R.id.sendmessageb);
        View writev = findViewById(R.id.writev);
        EditText messagewr = findViewById(R.id.messagewr);
        View back = findViewById(R.id.back);

        nameold.setText(namehis);

        DocumentReference docrefff = fStore.collection("messages").document(idhis);
        docrefff.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() || task != null){
                    if (task.equals(userID)){
                        String mymessage = task.getResult().get(userID).toString();
                        mymsg.setText(mymessage);
                        ObjectAnimator mymsganim = ObjectAnimator.ofFloat(mymsg, "translationX", 0f);
                        mymsganim.setDuration(1000);
                        mymsganim.start();
                        ObjectAnimator hismsganim = ObjectAnimator.ofFloat(hismsg, "translationY", -300f);
                        hismsganim.setDuration(500);
                        hismsganim.start();
                    }
                }
            }
        });

        Task<DocumentSnapshot> collectionReference = fStore.collection("messages").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() || task.equals(idhis)) {
                    DocumentReference documentReference = fStore.collection("messages").document(userID);
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String hismessage = value.getData().get(idhis).toString();
                            hismsg.setText(hismessage);
                        }
                    });
                } else {

                }
            }
        });

        if (fStore.collection("messages").document(idhis) != null && fStore.collection("messages").document(idhis).get().equals(userID)) {

            Task<DocumentSnapshot> collectionReference1 = fStore.collection("messages").document(idhis).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful() || task.equals(userID)) {
                        DocumentReference documentReference = fStore.collection("messages").document(idhis);
                        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String mymessage = value.getData().get(userID).toString();
                                mymsg.setText(mymessage);

                                ObjectAnimator mymsganim = ObjectAnimator.ofFloat(mymsg, "translationX", 0f);
                                mymsganim.setDuration(1000);
                                mymsganim.start();
                            }
                        });
                    } else {

                    }
                }
            });

        }


        sendmessageb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messagestri = messagewr.getText().toString();
                mymsg.setText(messagestri);

                DocumentReference docRef = fStore.collection("messages").document(idhis);
                Map<String, Object> edited = new HashMap<>();
                edited.put(userID, messagestri);
                docRef.set(edited);
                docRef.update(edited);

                messagewr.setText("");

                ObjectAnimator hismsganim = ObjectAnimator.ofFloat(hismsg, "translationY", -300f);
                hismsganim.setDuration(500);
                hismsganim.start();

                ObjectAnimator mymsganim = ObjectAnimator.ofFloat(mymsg, "translationX", 0f);
                mymsganim.setDuration(1000);
                mymsganim.start();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }

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

}