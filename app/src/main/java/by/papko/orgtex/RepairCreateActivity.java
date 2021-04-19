package by.papko.orgtex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                RepairParts repairParts = new RepairParts(editNameRepairCreate.getText().toString(),
                        editNameRepairCreate.getText().toString(), editSerialRepairCreate.getText().toString(),
                        editDopRepairCreate.getText().toString(), Integer.parseInt(editQuantityRepairCreate.getText().toString()));

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
}