package by.papko.orgtex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowOfficeActivity extends AppCompatActivity {

    private TextView textInv, textSer, textNa, textGro, textDops;
    private Button btnBack, btnRedact;
    private EditText editTest;
    private boolean flagBtnRedact = true;

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

        Bundle arguments = getIntent().getExtras();
        OfficeEquip officeEquip;

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