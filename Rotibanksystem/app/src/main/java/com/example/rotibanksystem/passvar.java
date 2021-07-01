package com.example.rotibanksystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class passvar extends AppCompatActivity {
    EditText editText;
    Button btn;

    String description="nudmmd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passvar);
        editText =findViewById(R.id.ed);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namevalue=editText.getText().toString();
                Intent intent=new Intent(passvar.this,MainActivity.class);
                intent.putExtra("name",namevalue);
                intent.putExtra("descp",description);
                startActivity(intent);


            }
        });

    }
}
