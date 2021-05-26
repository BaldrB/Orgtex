package by.papko.orgtex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editEmail, editPassword, editFullName;
    private TextView tvUserEmail;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private boolean flagRegister = false;
//    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Button btnSingOn, btnSingIn, btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();

        if (cUser != null) {
            Toast.makeText(this, "Пользователь присутствует", Toast.LENGTH_SHORT).show();
            btnSingOn.setVisibility(View.GONE);
            btnSingIn.setVisibility(View.GONE);
            editEmail.setVisibility(View.GONE);
            editPassword.setVisibility(View.GONE);
            editFullName.setVisibility(View.GONE);

            btnStart.setVisibility(View.VISIBLE);
            tvUserEmail.setVisibility(View.VISIBLE);
            String userName = "Пользователь: " + cUser.getEmail();
            tvUserEmail.setText(userName);
        }
        else {
            Toast.makeText(this, "Пользователь отсутсвует", Toast.LENGTH_SHORT).show();
            btnSingOn.setVisibility(View.VISIBLE);
            btnSingIn.setVisibility(View.VISIBLE);
            editEmail.setVisibility(View.VISIBLE);
            editPassword.setVisibility(View.VISIBLE);
            editFullName.setVisibility(View.GONE);
            btnStart.setVisibility(View.GONE);
            tvUserEmail.setVisibility(View.GONE);
        }

    }


    private void init() {
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editFullName = findViewById(R.id.editFullName);
        btnSingOn = findViewById(R.id.btnSignOut);
        btnSingIn = findViewById(R.id.btnSingIn);
        btnStart = findViewById(R.id.btnStart);
        tvUserEmail = findViewById(R.id.tvUserEmail);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference(Constant.SECURITY);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnSingOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagRegister) {
                    if (!TextUtils.isEmpty(editEmail.getText().toString()) && !TextUtils.isEmpty(editPassword.getText().toString())
                            && !TextUtils.isEmpty(editFullName.getText().toString())) {

                        mAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            SecurityUser securityUser = new SecurityUser(mAuth.getUid().toString(),
                                                    "vision", editEmail.getText().toString(), editFullName.getText().toString());
                                            mDataBase.child(mAuth.getUid()).setValue(securityUser);
                                            Toast.makeText(getApplicationContext(), "Создан аккаунт удачно", Toast.LENGTH_SHORT).show();
                                            editFullName.setVisibility(View.GONE);
                                            btnSingIn.setText("Вход");
                                            flagRegister = false;
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Создан аккаунт неудачно", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Введите все данные", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    editFullName.setVisibility(View.VISIBLE);
                    btnSingIn.setText("Назад");
                    flagRegister = true;
                }
            }
        });

        btnSingIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!flagRegister) {
                    if (!TextUtils.isEmpty(editEmail.getText().toString()) && !TextUtils.isEmpty(editPassword.getText().toString())) {
                        mAuth.signInWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Пользователь вошел удачно", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Пользователь вошел с ошибкой", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Введите логин пароль", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    editFullName.setVisibility(View.GONE);
                    btnSingIn.setText("Вход");
                    flagRegister = false;
                }
            }
        });
    }
}