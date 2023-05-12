package com.example.task71p;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShowAcBaoGao extends AppCompatActivity {

    ListView listViewLieBiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        //Fidn ID/Cha Zhao ID
        listViewLieBiao = (ListView) findVBIDChaZhaoID(R.id.list_view);

        DatabaseShuJuKuHelper dbHShuJuKu = new DatabaseShuJuKuHelper(ShowAcBaoGao.this);
        SQLiteDatabase dbShujU = dbHShuJuKu.getReadableDatabase();
        Cursor cursorZhiZhen = dbShujU.query(
                DatabaseShuJuKuHelper.ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        //Use Simple Cursor/Shi Yong Zhi Zhen
        SimpleCursorAdapter adapterJieShouQi = new SimpleCursorAdapter(
                ShowAcBaoGao.this,
                R.layout.list_item,
                cursorZhiZhen,
                new String[] {DatabaseShuJuKuHelper.ItemEntry.COLUMN_NAME, DatabaseShuJuKuHelper.ItemEntry.COLUMN_DESCRIPTION},
                new int[] {R.id.name_view, R.id.description_view},
                0
        );
        //Set Adaptr/SheZhe JieShouQi
        listViewLieBiao.setAdapter(adapterJieShouQi);

        //Overwrite the ontimelistenr/ChongXie Shijian
        listViewLieBiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursrZhiZhen = (Cursor) parent.getItemAtPosition(position);
                int itemId = cursrZhiZhen.getInt(cursrZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry._ID));

                Intent intentZhiZhen = new Intent(ShowAcBaoGao.this, DetailXingxi.class);
                intentZhiZhen.putExtra("itemId", itemId);
                startActivity(intentZhiZhen);
            }
        });
    }

    View findVBIDChaZhaoID(int id) {
        View nView = findViewById(id);
        return nView;
    }
}