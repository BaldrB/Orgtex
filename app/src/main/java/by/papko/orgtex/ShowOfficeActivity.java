package by.papko.orgtex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShowOfficeActivity extends AppCompatActivity {

    private TextView textInv, textSer, textNa, textGro, textDops;
    private Button btnBack, btnRedact, btnSave, btnTechnikShowRepairAdd, btnTechnikShowRepairDelete;
    private Button btnShowOfficeTechDelete;
    private ListView listShowOffice;
    private boolean flagBtnRedact = true;
    private OfficeEquip officeEquip;
    private DatabaseReference mDataBase;
    private DatabaseReference mDataBaseUser;
    private FirebaseAuth mAuth;
    private ArrayList<String> listParts = new ArrayList<>();
    private ArrayList<String> listSelectParts = new ArrayList<>();
    private ArrayAdapter<String> adapter;

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
        btnSave = findViewById(R.id.btnShowOfficeSave);
        btnShowOfficeTechDelete = findViewById(R.id.btnShowOfficeTechDelete);
        listShowOffice = findViewById(R.id.listShowOffice);
        btnTechnikShowRepairAdd = findViewById(R.id.btnTechnikShowRepairAdd);
        btnTechnikShowRepairDelete = findViewById(R.id.btnTechnikShowRepairDelete);
        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.ORGTECH);
        mDataBaseUser = FirebaseDatabase.getInstance().getReference(Constant.SECURITY);

        // создаем адаптер
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, listParts);

        // устанавливаем для списка адаптер
        listShowOffice.setAdapter(adapter);

        Bundle arguments = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser cUser = mAuth.getCurrentUser();
        Log.d("MyLog", "ShowOfficeActivity UID : " + cUser.getUid());

        if(arguments != null) {
            officeEquip = (OfficeEquip) arguments.getSerializable(OfficeEquip.class.getSimpleName());
            textInv.setText(officeEquip.getInv());
            textSer.setText(officeEquip.getSerial());
            textNa.setText(officeEquip.getNameequio());
            textGro.setText(officeEquip.getGropequimp());
            textDops.setText(officeEquip.getAdditional());
            listParts.addAll(officeEquip.getRepairPartsId());
        }

        btnShowOfficeTechDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBase.child(officeEquip.getId()).removeValue();
                Toast.makeText(getApplicationContext(), "Удалино", Toast.LENGTH_SHORT).show();

            }
        });

        btnTechnikShowRepairAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowOfficeActivity.this, SearchRapair.class);
                intent.putExtra("TECHINK","55");
                startActivityForResult(intent, 55);
            }
        });

        btnTechnikShowRepairDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // получаем и удаляем выделенные элементы
                for(int i=0; i< listSelectParts.size();i++){
                    adapter.remove(listSelectParts.get(i));
                }
                // снимаем все ранее установленные отметки
                listShowOffice.clearChoices();
                // очищаем массив выбраных объектов
                listSelectParts.clear();
                adapter.notifyDataSetChanged();
            }
        });

        listShowOffice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // получаем нажатый элемент
                String user = adapter.getItem(position);
                if(listShowOffice.isItemChecked(position))
                    listSelectParts.add(user);
                else
                    listSelectParts.remove(user);
            }
        });

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
                        textGro.getText().toString(), textDops.getText().toString(), listParts);

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
                    btnSave.setEnabled(true);
                    btnShowOfficeTechDelete.setEnabled(true);
                    btnTechnikShowRepairAdd.setEnabled(true);
                    btnTechnikShowRepairDelete.setEnabled(true);
                    flagBtnRedact = false;
                }
                else {
                    textInv.setEnabled(false);
                    textSer.setEnabled(false);
                    textNa.setEnabled(false);
                    textGro.setEnabled(false);
                    textDops.setEnabled(false);
                    btnSave.setEnabled(false);
                    btnShowOfficeTechDelete.setEnabled(false);
                    btnTechnikShowRepairAdd.setEnabled(false);
                    btnTechnikShowRepairDelete.setEnabled(false);
                    flagBtnRedact = true;

                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d("MyLog", "UID : " + currentUser.getUid());
            mDataBaseUser.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue(SecurityUser.class)));
                        if (!(null == task.getResult().getValue(SecurityUser.class))) {
                            SecurityUser seUs = task.getResult().getValue(SecurityUser.class);
                            String securiy = seUs.getAccess();
                            if (securiy.equals("vision")) {
                                btnSave.setVisibility(View.INVISIBLE);
                                btnShowOfficeTechDelete.setVisibility(View.INVISIBLE);
                                btnTechnikShowRepairAdd.setVisibility(View.INVISIBLE);
                                btnTechnikShowRepairDelete.setVisibility(View.INVISIBLE);
                                btnRedact.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            });
        }
        else
            Log.d("MyLog", "UID : not");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            textSer.setText(data.getStringExtra(Constant.SERIAL_NAME));
        }
        if (requestCode == 55) {
            Bundle arguments = data.getExtras();
            if(arguments !=null) {
                RepairParts repairParts = (RepairParts) arguments.getSerializable(RepairParts.class.getSimpleName());
                adapter.add(repairParts.getName());
            }
            adapter.notifyDataSetChanged();
        }
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