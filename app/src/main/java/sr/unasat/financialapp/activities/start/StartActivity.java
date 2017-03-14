package sr.unasat.financialapp.activities.start;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.MainActivity;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.User;

import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.EMAIL;
import static sr.unasat.financialapp.db.schema.Schema.SchemaUser.OPENING;

public class StartActivity extends AppCompatActivity {

    TextView email,opening;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2);
        email=(TextView)findViewById(R.id.emailfield);
        opening=(TextView)findViewById(R.id.opening_balance);
    }

    public void startActivityEvent(View view) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL,String.valueOf(email.getText()));
        contentValues.put(OPENING,Double.valueOf(String.valueOf(opening.getText())));

        new Dao(this).setUser(contentValues);

        startActivity(new Intent(this, MainActivity.class));
    }
}
