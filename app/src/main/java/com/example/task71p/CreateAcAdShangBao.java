package com.example.task71p;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAcAdShangBao extends AppCompatActivity {

    EditText nameTMingZi, phoneTDianHua, descriptionTMiaoShu, dateTRiQi, locationTDiDian;
    Button saveBChuCunAnNiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //find ID/Cha Zhao ID
        nameTMingZi = (EditText) findVBIDChaZhaoID(R.id.name_text);
        phoneTDianHua = (EditText) findVBIDChaZhaoID(R.id.phone_text);
        descriptionTMiaoShu = (EditText) findVBIDChaZhaoID(R.id.description_text);
        dateTRiQi = (EditText) findVBIDChaZhaoID(R.id.date_text);
        locationTDiDian = (EditText) findVBIDChaZhaoID(R.id.location_text);
        saveBChuCunAnNiu = (Button) findVBIDChaZhaoID(R.id.save_button);

        saveBChuCunAnNiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To String/ZhuanHuan wei String
                String nameMingZi = nameTMingZi.getText().toString();
                //To String/ZhuanHuan wei String
                String phoneDianHua = phoneTDianHua.getText().toString();
                //To String/ZhuanHuan wei String
                String descriptionMiaoShu = descriptionTMiaoShu.getText().toString();
                //To String/ZhuanHuan wei String
                String dateRiQi = dateTRiQi.getText().toString();
                //To String/ZhuanHuan wei String
                String locationDiDian = locationTDiDian.getText().toString();

                if (nameMingZi.isEmpty() || phoneDianHua.isEmpty() || descriptionMiaoShu.isEmpty() || dateRiQi.isEmpty() || locationDiDian.isEmpty()) {
                    //Message/Xiao xi
                    showMessxiaoXi("Please Enter all fields/Don't leave it empty");
                    return;
                }

                DatabaseShuJuKuHelper dbHelpShuJuKu = new DatabaseShuJuKuHelper(CreateAcAdShangBao.this);
                SQLiteDatabase dbShujU = dbHelpShuJuKu.getWritableDatabase();
                ContentValues valuesZhi = new ContentValues();
                valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_NAME, nameMingZi);
                valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_PHONE, phoneDianHua);
                valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_DESCRIPTION, descriptionMiaoShu);
                valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_DATE, dateRiQi);
                valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_LOCATION, locationDiDian);
                long newRowId = dbShujU.insert(DatabaseShuJuKuHelper.ItemEntry.TABLE_NAME, null, valuesZhi);

                if (newRowId == -1) {
                    //Message/Xiao xi
                    showMessxiaoXi("Failed to save/Can't Save");
                } else {
                    //Message/Xiao xi
                    showMessxiaoXi("Saved sucess");
                    finish();
                }
            }
        });
    }

    View findVBIDChaZhaoID(int id) {
        //Find the  id of the/ChaZhao ID
        View nView = findViewById(id);
        return nView;
    }

    void showMessxiaoXi(String mXiaoxi){
        //Message/Xiao xi
        Toast.makeText(this, mXiaoxi, Toast.LENGTH_SHORT).show();
    }
}
