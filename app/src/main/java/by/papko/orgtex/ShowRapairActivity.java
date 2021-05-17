package by.papko.orgtex;

import androidx.annotation.Nullable;
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

public class ShowRapairActivity extends AppCompatActivity {

    private Button btnBack, btnRedact, btnDelete, btnSave;
    private EditText textSerial, textName, textDop, textQuantity;
    private RepairParts repairParts;
    private boolean flagBtnRedact = true;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rapair);
        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.REPAIR);
        init();

        Bundle arguments = getIntent().getExtras();
        if(arguments != null) {
            repairParts = (RepairParts) arguments.getSerializable(RepairParts.class.getSimpleName());
            textSerial.setText(repairParts.getSerial());
            textName.setText(repairParts.getName());
            textDop.setText(repairParts.getDop());
            textQuantity.setText(String.valueOf(repairParts.getQuantity()));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(ShowRapairActivity.this, SearchRapair.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                }catch (Exception e) { }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepairParts repairPartsSave = new RepairParts(repairParts.getId(), textSerial.getText().toString(),
                        textName.getText().toString(), textDop.getText().toString(),
                        Integer.valueOf(textQuantity.getText().toString()));

                if(!TextUtils.isEmpty(textSerial.getText().toString()) && !TextUtils.isEmpty(textName.getText().toString())
                        && !TextUtils.isEmpty(textDop.getText().toString()) && !TextUtils.isEmpty(textQuantity.getText().toString())) {
                    mDataBase.child(repairParts.getId()).setValue(repairPartsSave);
                    Toast.makeText(getApplicationContext(), "Запчасть Изменена", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Зполниет все поля", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBase.child(repairParts.getId()).removeValue();
                Toast.makeText(getApplicationContext(), "Удалино", Toast.LENGTH_SHORT).show();
            }
        });

        btnRedact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagBtnRedact) {
                    textSerial.setEnabled(true);
                    textName.setEnabled(true);
                    textDop.setEnabled(true);
                    textQuantity.setEnabled(true);

                    btnSave.setEnabled(true);
                    btnDelete.setEnabled(true);
                    flagBtnRedact = false;
                }
                else {
                    textSerial.setEnabled(false);
                    textName.setEnabled(false);
                    textDop.setEnabled(false);
                    textQuantity.setEnabled(false);

                    btnSave.setEnabled(false);
                    btnDelete.setEnabled(false);
                    flagBtnRedact = true;
                }
            }
        });
    }

    private void init() {
        btnBack = findViewById(R.id.btnShowRapairBack);
        btnRedact = findViewById(R.id.btnShowRapairRedact);
        btnDelete = findViewById(R.id.btnShowRapairDelete);
        btnSave = findViewById(R.id.btnShowRapairSave);

        textSerial = findViewById(R.id.textShowRapairSerial);
        textName = findViewById(R.id.textShowRapairName);
        textDop = findViewById(R.id.textShowRapairDop);
        textQuantity = findViewById(R.id.textShowRapairQuantity);

    }


    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(ShowRapairActivity.this, SearchRapair.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        }catch (Exception e) { }
    }
}