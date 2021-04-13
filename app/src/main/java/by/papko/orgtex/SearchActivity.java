package by.papko.orgtex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<OfficeEquip> officeEquips = new ArrayList<OfficeEquip>();
    private DatabaseReference mDataBase;
    OfficeAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        adapter = new OfficeAdapter(this, officeEquips);

        recyclerView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference("ORGTECH");

        getDataFromDB();
    }

    private void getDataFromDB() {

        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (officeEquips.size() > 0)
                    officeEquips.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OfficeEquip officeEquip = ds.getValue(OfficeEquip.class);
                    assert  officeEquip != null;
                    officeEquips.add(officeEquip);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        mDataBase.addValueEventListener(vListener);
    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e) {

        }
    }
}