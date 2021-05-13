package by.papko.orgtex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<OfficeEquip> officeEquips = new ArrayList<OfficeEquip>();
    private ArrayList<OfficeEquip> officeEquipsArray = new ArrayList<OfficeEquip>();
    private DatabaseReference mDataBase;
    private OfficeAdapter adapter;
    private RecyclerView recyclerView;

    private EditText edSearch;
    private Button btnSearch, btnSearchBack;
    // Идентификатор канала
    String id = "id_products";
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edSearch = findViewById(R.id.editInvert);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearchBack = findViewById(R.id.btnSearchBack);
//        createNotificationChannel();

        recyclerView = (RecyclerView) findViewById(R.id.listSearchTechnick);

        OfficeAdapter.OnOfficeEquipClickListener officeEquipClickListener = new OfficeAdapter.OnOfficeEquipClickListener() {
            @Override
            public void onOfficeEquipClick(OfficeEquip officeEquip, int position) {
                    Intent intent = new Intent(SearchActivity.this, ShowOfficeActivity.class);
                    intent.putExtra(OfficeEquip.class.getSimpleName(), officeEquip);
                    startActivity(intent);
            }
        };

        adapter = new OfficeAdapter(this, officeEquipsArray, officeEquipClickListener);

        recyclerView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.ORGTECH);

        getDataFromDB();

        btnSearch.setOnClickListener(v -> searchList());

        btnSearchBack.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void getDataFromDB() {

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                NotificationCompat.Builder builder =
//                        new NotificationCompat.Builder(SearchActivity.this)
//                                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                                .setContentTitle("Title")
//                                .setChannelId(id)
//                                .setContentText("Notification text");
//
//                Notification notification = builder.build();
//
//                notificationManager.notify(2, notification);

                if (officeEquips.size() > 0)
                    officeEquips.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OfficeEquip officeEquip = ds.getValue(OfficeEquip.class);
                    assert  officeEquip != null;
                    officeEquips.add(officeEquip);
                }
                officeEquipsArray.addAll(officeEquips);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        mDataBase.addValueEventListener(vListener);
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

    private void searchList() {

        if(!TextUtils.isEmpty(edSearch.getText().toString())) {
            String rn = ".*("+ edSearch.getText().toString() +").*";
            System.out.println(rn);
            ArrayList<OfficeEquip> oE = new ArrayList<>();

            for(OfficeEquip e : officeEquips) {
                if (e.getInv().matches(rn)) {
                    oE.add(e);
                }
            }
            officeEquipsArray.clear();
            officeEquipsArray.addAll(oE);

        }
        else {
            officeEquipsArray.clear();
            officeEquipsArray.addAll(officeEquips);
        }
        adapter.notifyDataSetChanged();

    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(SearchActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception ignored) {
        }
    }
}