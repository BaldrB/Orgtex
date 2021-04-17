package by.papko.orgtex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TechnickActivity extends AppCompatActivity {

    private Button btnBack, btnCreate, btnScaner;
    private EditText editInv, editSerial, editName, editgrpoup, editDop;
    private DatabaseReference mDataBase;

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

        mDataBase = FirebaseDatabase.getInstance().getReference("ORGTECH");

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
        OfficeEquip officeEquip = new OfficeEquip(editInv.getText().toString(),
                editSerial.getText().toString(), editName.getText().toString(),
                editgrpoup.getText().toString(), editDop.getText().toString());

        if(!TextUtils.isEmpty( editInv.getText().toString() )) {
            mDataBase.push().setValue(officeEquip);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            editSerial.setText(data.getStringExtra(Constant.SERIAL_NAME));
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
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