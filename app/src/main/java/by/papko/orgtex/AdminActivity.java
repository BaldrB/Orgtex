package by.papko.orgtex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private ListView userList;
    private DatabaseReference mDataBase;
    private ArrayList<String> users = new ArrayList<String>();
    private ArrayAdapter<String> adapters;
    private ArrayList<SecurityUser> securityUsers = new ArrayList<SecurityUser>();
    private SecurityUserAdapter stateAdapter;
    private RadioGroup radioGroup;
    private RadioButton radioButtonFull, radioButtonVision, radioButtonCreate;
    private TextView textSelectUser;
    private SecurityUser secuUser;
    private Button btnSava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        userList = (ListView) findViewById(R.id.listViewAdmin);
        radioGroup = findViewById(R.id.radioGroupAdmin);
        radioButtonFull = findViewById(R.id.radioButtonFull);
        radioButtonVision = findViewById(R.id.radioButtonVision);
        radioButtonCreate = findViewById(R.id.radioButtonCreate);
        textSelectUser = findViewById(R.id.textSelectUserAdmin);
        btnSava = findViewById(R.id.btnAdminSaveStatus);

        // создаем адаптер
        stateAdapter = new SecurityUserAdapter(this, R.layout.list_view_user_admin, securityUsers);
        // устанавливаем для списка адаптер
        userList.setAdapter(stateAdapter);

//        adapters = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, users);
        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.SECURITY);
        mDataBase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        SecurityUser sec = ds.getValue(SecurityUser.class);
                        assert  sec != null;
                        System.out.println(sec.getFullName());
                        securityUsers.add(sec);
                    }
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    stateAdapter.notifyDataSetChanged();
                }

            }
        });

        btnSava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton myRadioButton = findViewById(checkedRadioButtonId);


                switch(myRadioButton.getId()) {
                    case R.id.radioButtonFull:
                        secuUser.setAccess("full");
                        break;
                    case R.id.radioButtonVision:
                        secuUser.setAccess("vision");
                        break;
                    case R.id.radioButtonCreate:
                        secuUser.setAccess("add");
                        break;
                    default:
                        break;
                }

                mDataBase.child(secuUser.getUid()).setValue(secuUser);
                Toast.makeText(getApplicationContext(),
                        "Сохранено", Toast.LENGTH_SHORT).show();
            }
        });

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId) {
//                    case R.id.radioButtonFull:
//                        Toast.makeText(getApplicationContext(), "Полный",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.radioButtonVision:
//                        Toast.makeText(getApplicationContext(), "Визио",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.radioButtonCreate:
//                        Toast.makeText(getApplicationContext(), "креаете",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

//        userList.setAdapter(adapters);=

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // получаем выбранный пункт
                SecurityUser selectedState = (SecurityUser)parent.getItemAtPosition(position);
                if(selectedState.getAccess().equals("full"))
                    radioButtonFull.setChecked(true);
                else if (selectedState.getAccess().equals("vision"))
                    radioButtonVision.setChecked(true);
                else
                    radioButtonCreate.setChecked(true);
                secuUser = selectedState;
                textSelectUser.setText(selectedState.getFullName());
            }
        };
        userList.setOnItemClickListener(itemListener);

    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(AdminActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e) {
        }
    }

}