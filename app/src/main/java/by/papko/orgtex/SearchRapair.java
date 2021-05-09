package by.papko.orgtex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchRapair extends AppCompatActivity {

    private ArrayList<RepairParts> repairPartse = new ArrayList<RepairParts>();
    private ArrayList<RepairParts> repairPartsArray = new ArrayList<RepairParts>();

    private EditText editSearchRapir;
    private Button btnSearchRapirSearch, btnSearchRapirBack;
    private RecyclerView recyclerSearchRapirView;

    private RepairAdapter adapter;
    private DatabaseReference mDataBase;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rapair);
        init();

        RepairAdapter.OnRepairPartsClickListener repairPartsClickListener = new RepairAdapter.OnRepairPartsClickListener() {
            @Override
            public void onRepairPartsClick(RepairParts repairParts, int position) {
                Intent intent = new Intent(SearchRapair.this, ShowOfficeActivity.class);
                intent.putExtra(RepairParts.class.getSimpleName(), repairParts);
                startActivity(intent);
            }
        };

        btnSearchRapirBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(SearchRapair.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();

                }catch (Exception ignored) { }
            }
        });
        adapter = new RepairAdapter(this, repairPartsArray, repairPartsClickListener);
        recyclerSearchRapirView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.REPAIR);
        getDataFromDB();
        btnSearchRapirSearch.setOnClickListener(v -> searchList());

    }

    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (repairPartse.size() > 0)
                    repairPartse.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    RepairParts repairParts = ds.getValue(RepairParts.class);
                    assert  repairParts != null;
                    repairPartse.add(repairParts);
                }
                repairPartsArray.addAll(repairPartse);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        mDataBase.addValueEventListener(vListener);
    }

    private void init() {
        recyclerSearchRapirView = findViewById(R.id.listSearchRapair);
        btnSearchRapirSearch = findViewById(R.id.btnSearchRapairSearch);
        btnSearchRapirBack = findViewById(R.id.btnSearchRapairBack);
    }

    private void searchList() {

        if(!TextUtils.isEmpty(editSearchRapir.getText().toString())) {
            String rn = ".*("+ editSearchRapir.getText().toString() +").*";
            System.out.println(rn);
            ArrayList<RepairParts> oE = new ArrayList<>();

            for(RepairParts e : repairPartse) {
                if (e.getSerial().matches(rn)) {
                    oE.add(e);
                }
            }
            repairPartsArray.clear();
            repairPartsArray.addAll(oE);

        }
        else {
            repairPartsArray.clear();
            repairPartsArray.addAll(repairPartse);
        }
        adapter.notifyDataSetChanged();
    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(SearchRapair.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();

        }catch (Exception ignored) { }

    }
}