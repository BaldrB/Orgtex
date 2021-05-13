package by.papko.orgtex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button btnCreate, btnTechnik, btnAdmin, btnExit, btnCreateRepair, btnPush, btnRepair;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase, mDataBaseEvent;
    private String securiy;

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    String id = "id_product";
    NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        btnCreate = (Button)findViewById(R.id.btn_create);
        btnTechnik = (Button)findViewById(R.id.btnTechnik);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnExit = findViewById(R.id.btnExit);
        btnCreateRepair = findViewById(R.id.btnCreateRepair);
        btnPush = findViewById(R.id.btnPush);
        btnRepair = findViewById(R.id.btnRepair);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.SECURITY);
        mDataBaseEvent = FirebaseDatabase.getInstance().getReference(Constant.SECURITY);
        eventDataDB();

        btnRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchRapair.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, TechnickActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) { }
            }
        });

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Title")
                                .setChannelId(id)
                                .setContentText("Notification text");

                Notification notification = builder.build();

                notificationManager.notify(1, notification);
            }
        });

        btnTechnik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) {
                }
            }
        });

        btnCreateRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, RepairCreateActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) {
                }
            }
        });

    }

    private void eventDataDB() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Title")
                                .setChannelId(id)
                                .setContentText("Main add");

                Notification notification = builder.build();

                notificationManager.notify(1, notification);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Title")
                                .setChannelId(id)
                                .setContentText("Main new chandeg");

                Notification notification = builder.build();

                notificationManager.notify(1, notification);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Title")
                                .setChannelId(id)
                                .setContentText("Main new zapis");

                Notification notification = builder.build();

                notificationManager.notify(1, notification);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        mDataBaseEvent.addChildEventListener(childEventListener);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            // The user-visible name of the channel.
            CharSequence name = "Product";
            // The user-visible description of the channel.
            String description = "Notifications regarding our products";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d("MyLog", "UID : " + currentUser.getUid());
            mDataBase.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue(SecurityUser.class)));
                        if (!(null == task.getResult().getValue(SecurityUser.class))) {
                            SecurityUser seUs = task.getResult().getValue(SecurityUser.class);
                            securiy = seUs.getAccess();
                            if (securiy.equals("vision")) {
                                btnCreate.setEnabled(false);
                                btnAdmin.setEnabled(false);
                                btnAdmin.setVisibility(View.INVISIBLE);
                            } else if (securiy.equals("full")) {
                            } else if (securiy.equals("add")) {
                                btnAdmin.setEnabled(false);
                                btnAdmin.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            });
        }
        else
            Log.d("MyLog", "UID : not");
    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }catch (Exception e) {
        }
    }

}