package by.papko.orgtex;

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

public class ShowOfficeActivity extends AppCompatActivity {

    private TextView textInv, textSer, textNa, textGro, textDops;
    private Button btnBack, btnRedact, btnSave;
    private EditText editTest;
    private ListView listShowOffice;
    private boolean flagBtnRedact = true;
    OfficeEquip officeEquip;
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай", "Аргентина", "Колумбия", "Чили", "Уругвай"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_office);
        textInv = findViewById(R.id.textInv1);
        textSer = findViewById(R.id.textSer);
        textNa = findViewById(R.id.textNa);
        textGro = findViewById(R.id.textGrou);
        textDops = findViewById(R.id.textDops);
        btnBack = findViewById(R.id.btnBack1);
        btnRedact = findViewById(R.id.btnRedact);
        editTest = findViewById(R.id.editTest);
        btnSave = findViewById(R.id.btnShowOfficeSave);
        listShowOffice = findViewById(R.id.listShowOffice);
        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.ORGTECH);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, countries);

        // устанавливаем для списка адаптер
        listShowOffice.setAdapter(adapter);

        Bundle arguments = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser cUser = mAuth.getCurrentUser();
        Log.d("MyLog", "ShowOfficeActivity UID : " + cUser.getUid());


        if(arguments !=null) {
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowOfficeActivity.this, SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OfficeEquip officeEquipSave = new OfficeEquip(officeEquip.getId(), textInv.getText().toString(),
                        textSer.getText().toString(), textNa.getText().toString(),
                        textGro.getText().toString(), textDops.getText().toString());

                if(!TextUtils.isEmpty(textInv.getText().toString()) && !TextUtils.isEmpty(textSer.getText().toString())
                        && !TextUtils.isEmpty(textNa.getText().toString()) && !TextUtils.isEmpty(textGro.getText().toString())) {
                    mDataBase.child(officeEquip.getId()).setValue(officeEquipSave);
                    Toast.makeText(getApplicationContext(), "Оргтехика Изменена", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Зполниет все поля", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnRedact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagBtnRedact) {
                    textInv.setEnabled(true);
                    textSer.setEnabled(true);
                    textNa.setEnabled(true);
                    textGro.setEnabled(true);
                    textDops.setEnabled(true);
                    editTest.setEnabled(true);
                    flagBtnRedact = false;
                }
                else {
                    textInv.setEnabled(false);
                    textSer.setEnabled(false);
                    textNa.setEnabled(false);
                    textGro.setEnabled(false);
                    textDops.setEnabled(false);
                    editTest.setEnabled(false);
                    flagBtnRedact = true;

                }
            }
        });
    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(ShowOfficeActivity.this, SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();

        }catch (Exception e) {

        }
    }
}