package by.papko.orgtex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RepairCreateActivity extends AppCompatActivity {

    private EditText editNameRepairCreate, editSerialRepairCreate, editQuantityRepairCreate, editDopRepairCreate;
    private Button btnBackRepairCreate, btnCreateRepairCreate;
    private DatabaseReference mDataBase;

    private final static String TAG = "RepairCreateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_create);
        init();

        btnCreateRepairCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idkey = mDataBase.push().getKey();
                RepairParts repairParts = new RepairParts(idkey, editNameRepairCreate.getText().toString(),
                        editSerialRepairCreate.getText().toString(), editDopRepairCreate.getText().toString(),
                        Integer.parseInt(editQuantityRepairCreate.getText().toString()));

                if(!TextUtils.isEmpty(editNameRepairCreate.getText().toString()) &&
                        !TextUtils.isEmpty(editSerialRepairCreate.getText().toString())) {

                    mDataBase.child(idkey).setValue(repairParts);

                    Toast.makeText(getApplicationContext(), "Repair добавлена в каталог", Toast.LENGTH_SHORT).show();

                    editNameRepairCreate.setText("");
                    editSerialRepairCreate.setText("");
                    editQuantityRepairCreate.setText("");
                    editDopRepairCreate.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Зполниет все поля", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void init() {
        mDataBase = FirebaseDatabase.getInstance().getReference("REPAIR");
        editNameRepairCreate = findViewById(R.id.editNameRepairCreate);
        editSerialRepairCreate = findViewById(R.id.editSerialRepairCreate);
        editQuantityRepairCreate = findViewById(R.id.editQuantityRepairCreate);
        editDopRepairCreate = findViewById(R.id.editDopRepairCreate);

        btnBackRepairCreate = findViewById(R.id.btnBackRepairCreate);
        btnCreateRepairCreate = findViewById(R.id.btnCreateRepairCreate);
    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(RepairCreateActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e) {
        }
    }
}