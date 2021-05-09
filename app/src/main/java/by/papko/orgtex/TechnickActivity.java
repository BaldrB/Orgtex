package by.papko.orgtex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TechnickActivity extends AppCompatActivity {

    private Button btnBack, btnCreate, btnScaner, btnTechnikAddRepair;
    private EditText editInv, editSerial, editName, editgrpoup, editDop;
    private ListView listRepairTech;
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    OfficeEquip officeEquip;
    ArrayList<String> countries = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private final static String TAG = "TechnickActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technick);
        Log.d(TAG, "onCreate");

        btnBack = findViewById(R.id.btnBack);
        btnCreate = findViewById(R.id.btn_create);
        editInv = findViewById(R.id.edInvetr);
        editSerial = findViewById(R.id.edSerialNambers);
        editName = findViewById(R.id.edNameTech);
        editgrpoup = findViewById(R.id.edGroupTech);
        editDop = findViewById(R.id.edDop);
        btnScaner = findViewById(R.id.btnScaner);
        btnTechnikAddRepair = findViewById(R.id.btnTechnikAddRepair);
        listRepairTech = findViewById(R.id.listRepairTech);
        // создаем адаптер
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, countries);

        // устанавливаем для списка адаптер
        listRepairTech.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser cUser = mAuth.getCurrentUser();
        Log.d("MyLog", "TechnickActivity UID : " + cUser.getUid());

        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.ORGTECH);
        mAuth = FirebaseAuth.getInstance();

        btnTechnikAddRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechnickActivity.this, SearchActivity.class);
                intent.putExtra("TECHINK","45");
                startActivityForResult(intent, 45);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechnickActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnScaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quIntent = new Intent(TechnickActivity.this, BaracodeActivity.class);
                startActivityForResult(quIntent, 0);
            }
        });
    }

    public void onClickCreate(View view) {
        String idkey = mDataBase.push().getKey();
        OfficeEquip officeEquip = new OfficeEquip(idkey, editInv.getText().toString(),
                editSerial.getText().toString(), editName.getText().toString(),
                editgrpoup.getText().toString(), editDop.getText().toString(), countries);

        if(!TextUtils.isEmpty(editInv.getText().toString()) && !TextUtils.isEmpty(editSerial.getText().toString())
                && !TextUtils.isEmpty(editName.getText().toString()) && !TextUtils.isEmpty(editgrpoup.getText().toString())) {
            mDataBase.child(idkey).setValue(officeEquip);
            Toast.makeText(this, "Оргтехика добавлена в каталог", Toast.LENGTH_SHORT).show();

            editInv.setText("");
            editSerial.setText("");
            editName.setText("");
            editgrpoup.setText("");
            editDop.setText("");

        } else {
            Toast.makeText(this, "Зполниет все поля", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            editSerial.setText(data.getStringExtra(Constant.SERIAL_NAME));
        }
        if (requestCode == 45) {
            Bundle arguments = data.getExtras();
            if(arguments !=null) {
                officeEquip = (OfficeEquip) arguments.getSerializable(OfficeEquip.class.getSimpleName());
                adapter.add(officeEquip.getId());
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            Log.d("MyLog", "UID : " + currentUser.getUid());
        else
            Log.d("MyLog", "UID : not");
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(TechnickActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e) {

        }
    }
}