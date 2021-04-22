package by.papko.orgtex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btnCreate, btnTechnik, btnAdmin, btnExit, btnCreateRepair;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private String securiy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = (Button)findViewById(R.id.btn_create);
        btnTechnik = (Button)findViewById(R.id.btnTechnik);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnExit = findViewById(R.id.btnExit);
        btnCreateRepair = findViewById(R.id.btnCreateRepair);


        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("SECURITY");

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, TechnickActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {

                }
            }
        });

        btnTechnik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {
                }
            }
        });

        btnCreateRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, RepairCreateActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDataBase.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue(SecurityUser.class)));
                    if (!(null == task.getResult().getValue(SecurityUser.class))) {
                        SecurityUser seUs = task.getResult().getValue(SecurityUser.class);
                        securiy = seUs.getAccess();
                        if (securiy.equals("vision")) {
                            btnCreate.setEnabled(false);
                            btnAdmin.setEnabled(false);
                            btnAdmin.setVisibility(View.INVISIBLE);
                        }
                        else if (securiy.equals("full")) {
                        }
                        else if (securiy.equals("add")) {
                            btnAdmin.setEnabled(false);
                            btnAdmin.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });
    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e) {
        }
    }

}