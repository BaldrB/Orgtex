package by.papko.orgtex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowOfficeActivity extends AppCompatActivity {

    private TextView textInv, textSer, textNa, textGro, textDops;
    private Button btnBack, btnRedact, btnSave;
    private EditText editTest;
    private boolean flagBtnRedact = true;
    OfficeEquip officeEquip;
    private DatabaseReference mDataBase;

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
        mDataBase = FirebaseDatabase.getInstance().getReference("ORGTECH");

        Bundle arguments = getIntent().getExtras();

        if(arguments !=null) {
            officeEquip = (OfficeEquip) arguments.getSerializable(OfficeEquip.class.getSimpleName());
            textInv.setText(officeEquip.getInv());
            textSer.setText(officeEquip.getSerial());
            textNa.setText(officeEquip.getNameequio());
            textGro.setText(officeEquip.getGropequimp());
            textDops.setText(officeEquip.getAdditional());
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