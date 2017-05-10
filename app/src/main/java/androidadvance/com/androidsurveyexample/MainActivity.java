package androidadvance.com.androidsurveyexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.SurveyActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private static final int SURVEY_REQUEST = 1337;
    private static String DB_NAME = "InsideView";
    private static String ENTITY_NAME_PROFILES = "profiles";

    private FirebaseDatabase firebase;
    private DatabaseReference database;
    private DatabaseReference table;
    Button button_Snap;
    int ans=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Nothing fancy here. Plain old simple buttons....
          button_Snap=(Button) findViewById(R.id.snap) ;
         Button button_survey_example_1 = (Button) findViewById(R.id.button_survey_example_1);
//        Button button_survey_example_2 = (Button) findViewById(R.id.button_survey_example_2);
       // Button button_survey_example_3 = (Button) findViewById(R.id.button_survey_example_3);

        button_Snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_Sanp=new Intent(MainActivity.this,CognitiveCa.class);
                startActivityForResult(i_Sanp,SURVEY_REQUEST);
            }
        });

        button_survey_example_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i_survey = new Intent(MainActivity.this, SurveyActivity.class);
                //you have to pass as an extra the json string.
                i_survey.putExtra("json_survey", loadSurveyJson("example_survey_1.json"));
                startActivityForResult(i_survey, SURVEY_REQUEST);
            }
        });





//        button_survey_example_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i_survey = new Intent(MainActivity.this, SurveyActivity.class);
//                i_survey.putExtra("json_survey", loadSurveyJson("example_survey_2.json"));
//                startActivityForResult(i_survey, SURVEY_REQUEST);
//            }
//        });


    }
    public  MainActivity(){
        firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference(DB_NAME);
        table = database.child(ENTITY_NAME_PROFILES);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.v("****", "****************** WE HAVE ANSWERS ******************");
                Log.v("ANSWERS JSON", answers_json);
                Log.v("****", "*****************************************************");

                Answers.getInstance().StoredDataToFireBase();
                Answers.getInstance().MayersScore();
               }
        }
    }


    //json stored in the assets folder. but you can get it from wherever you like.
    private String loadSurveyJson(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
