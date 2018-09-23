package com.example.alex.npcdirectory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNPCActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.example.alex.npcdirectory.NPC_NAME";
    public static final String EXTRA_DESCRIPTION = "com.example.alex.npcdirectory.NPC_DESCRIPTION";
    public static final String EXTRA_ID = "com.example.alex.npcdirectory.NPC_ID";


    private EditText mNameView;

    private EditText mDescriptionView;

    private int npcID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_npc);
        mNameView = findViewById(R.id.edit_name);
        mDescriptionView = findViewById(R.id.edit_description);

        Intent intent = getIntent();
        //Then we're editing an existing NPC, not creating a new one.
        if(intent.hasExtra(EXTRA_ID))
        {
            mNameView.setText(intent.getStringExtra(EXTRA_NAME));
            mDescriptionView.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            npcID = intent.getIntExtra(EXTRA_ID,0);
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mNameView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra(EXTRA_NAME, mNameView.getText().toString());
                    replyIntent.putExtra(EXTRA_DESCRIPTION, mDescriptionView.getText().toString());
                    replyIntent.putExtra(EXTRA_ID, npcID);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}