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

public class TechnickActivity extends AppCompatActivity {

    private Button btnBack, btnCreate;
    private EditText editInv, editSerial, editName, editgrpoup, editDop;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technick);

        btnBack = findViewById(R.id.btnBack);
        btnCreate = findViewById(R.id.btn_create);
        editInv = findViewById(R.id.edInvetr);
        editSerial = findViewById(R.id.edSerialNamber);
        editName = findViewById(R.id.edNameTech);
        editgrpoup = findViewById(R.id.edGroupTech);
        editDop = findViewById(R.id.edDop);

        mDataBase = FirebaseDatabase.getInstance().getReference("ORGTECH");


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechnickActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClickCreate(View view) {
        OfficeEquip officeEquip = new OfficeEquip(editInv.getText().toString(),
                editSerial.getText().toString(), editName.getText().toString(),
                editgrpoup.getText().toString(), editDop.getText().toString());

        if(!TextUtils.isEmpty( editInv.getText().toString() )) {
            mDataBase.push().setValue(officeEquip);
        }

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