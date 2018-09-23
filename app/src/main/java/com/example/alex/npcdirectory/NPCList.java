package com.example.alex.npcdirectory;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alex.npcdirectory.data.NPC;
import com.example.alex.npcdirectory.util.RecyclerTouchListener;

import java.util.List;

public class NPCList extends AppCompatActivity {

    public static final int NEW_NPC_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_NPC_ACTIVITY_REQUEST_CODE = 2;

    private NPCViewModel npcViewModel;
    private List<NPC> NPCs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        npcViewModel = ViewModelProviders.of(this).get(NPCViewModel.class);

        setContentView(R.layout.activity_npclist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NPCList.this, NewNPCActivity.class);
                startActivityForResult(intent, NEW_NPC_ACTIVITY_REQUEST_CODE);
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final NPCListAdapter adapter = new NPCListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        npcViewModel.getAllNPCs().observe(this, new Observer<List<NPC>>() {
            @Override
            public void onChanged(@Nullable List<NPC> npcs) {
                adapter.setNPCs(npcs);
                NPCs = npcs;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(NPCList.this, recyclerView, new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, int position) {
                //Open NPC for editing
                Intent intent = new Intent(NPCList.this, NewNPCActivity.class);
                NPC selected = NPCs.get(position);
                intent.putExtra(NewNPCActivity.EXTRA_NAME, selected.getName());
                intent.putExtra(NewNPCActivity.EXTRA_DESCRIPTION, selected.getDescription());
                intent.putExtra(NewNPCActivity.EXTRA_ID, selected.getId());
                startActivityForResult(intent, EDIT_NPC_ACTIVITY_REQUEST_CODE);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Rearrange?
            }
        }));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NPC_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                NPC npc = new NPC(data.getStringExtra(NewNPCActivity.EXTRA_NAME), data.getStringExtra(NewNPCActivity.EXTRA_DESCRIPTION));
                npcViewModel.insert(npc);
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == EDIT_NPC_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                NPC npc = new NPC(data.getStringExtra(NewNPCActivity.EXTRA_NAME), data.getStringExtra(NewNPCActivity.EXTRA_DESCRIPTION), data.getIntExtra(NewNPCActivity.EXTRA_ID, 0));
                npcViewModel.update(npc);
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_npclist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
