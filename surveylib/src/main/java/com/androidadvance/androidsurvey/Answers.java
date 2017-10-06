package com.androidadvance.androidsurvey;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

//Singleton Answers ........

public class Answers {
    private volatile static Answers uniqueInstance;
    private final LinkedHashMap<String, String> answered_hashmap = new LinkedHashMap<>();
    int phillsScore=0;
    private static String DB_NAME = "InsideView";
    private static String ENTITY_NAME_PROFILES = "profiles";

    private FirebaseDatabase firebase;
    private DatabaseReference database;
    private DatabaseReference table;
    public static DatabaseReference record;
    public static String type="";
    private StorageReference mstorage;


    public Answers() {
        firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference(DB_NAME);
        table = database.child(ENTITY_NAME_PROFILES);
        mstorage= FirebaseStorage.getInstance().getReference();
    }
  public void StoredDataToFireBase(){
       record = table.push();

      int i=1;

      for (Map.Entry<String, String> entry : answered_hashmap.entrySet()){
          //record.child("Record").child("Question" +i).setValue(entry.getKey());
          record.child("Record").child("Answer" +i).setValue( entry.getValue());
          i++;
      }
  //    int score=PhillsScores();
      String type=MayersScore();

      //record.child("Record").child("PhillsScore").setValue(score);
      record.child("Record").child("MayersType").setValue(type);
      record.child("Record").child("Age").setValue(MainActivity.spinner2.getSelectedItem().toString());
      record.child("Record").child("Gender").setValue(MainActivity.spinner1.getSelectedItem().toString());
      record.child("Record").child("Rating").setValue(String.valueOf(UserProfile.ratingRatingBar.getRating()));

      //image saving to firebase media with same user id

//      CognitiveCa.imageView.setDrawingCacheEnabled(true);
//      CognitiveCa.imageView.buildDrawingCache();
//
//
//
//      Bitmap bitmap = CognitiveCa.imageView.getDrawingCache();
//      ByteArrayOutputStream baos = new ByteArrayOutputStream();
//      UserProfile.photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//      byte[] data1 = baos.toByteArray();
//
//      // Uri uri=data.getData();
////            try {
////                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//      StorageReference fileupload=mstorage.child(String.valueOf("pic01"));
//      UploadTask uploadTask =fileupload.putBytes(data1);
//      uploadTask.addOnFailureListener(new OnFailureListener() {
//          @Override
//          public void onFailure(@NonNull Exception exception) {
//              // Handle unsuccessful uploads
//          }
//      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//          @Override
//          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//              // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
////              Uri downloadUrl = taskSnapshot.getDownloadUrl();
////
////              DatabaseReference record=table.child("pic01");
////              record.setValue(downloadUrl.getLastPathSegment());
////
////
//              //Toast.makeText(,"Saved into Database",Toast.LENGTH_SHORT).show();
//
//          }
//      });
//      record.child("Record").child("Json").setValue(String.valueOf(answered_hashmap));


  }
    public String MayersScore() {
        final DatabaseReference record = table.push();
        int E=0, S=0, T=0, J = 0;
        int I=0, N=0, F=0, P = 0;
        char type1;
        char type2;
        char type3;
        char type4;
        Set set = answered_hashmap.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            // for extrovert & introvert
            if ((me.getKey().equals("question_1")) || (me.getKey().equals("question_5"))
                    || (me.getKey().equals("question_9"))
                    || (me.getKey().equals("question_13"))
                    || (me.getKey().equals("question_17")) ){

                        if (me.getValue().equals("Expend energy, enjoy groups")
                             || (me.getValue().equals("More outgoing, think out loud"))
                             || (me.getValue().equals("Seek many tasks, public activities, interaction with others"))
                             || (me.getValue().equals("External, communicative, express yourself"))
                             || (me.getValue().equals("Active, initiate"))){
                            E=E+1;
                        }
                        if (me.getValue().equals("Conserve energy, enjoy one-on-one")
                                || (me.getValue().equals("More reserved, think to yourself"))
                                || (me.getValue().equals("Seek private, solitary activities with quiet to concentrate"))
                                || (me.getValue().equals("internal, reticent, keep to yourself"))
                                || (me.getValue().equals("Reflective, deliberate"))
                                ){
                            I=I+1;
                        }
                   }
  // for sensing & intutive
                if ((me.getKey().equals("question_2")) || (me.getKey().equals("question_6"))
                        || (me.getKey().equals("question_10"))
                        || (me.getKey().equals("question_14"))
                        || (me.getKey().equals("question_18")) ){

                           if (me.getValue().equals("Interpret literally")
                                    || (me.getValue().equals("Practical,realistic, experiential"))
                                    || (me.getValue().equals("Standard, usual, conventional"))
                                    || (me.getValue().equals("Focus on here-and-now"))
                                    || (me.getValue().equals("Facts, things, “what is”"))){
                                S=S+1;
                            }
                            if (me.getValue().equals("Look for meaning and possibilities")
                                    || (me.getValue().equals("Imaginative,innovative, theoretical"))
                                    || (me.getValue().equals("Different, novel, unique"))
                                    || (me.getValue().equals("Look to the future, global perspective, “big picture”"))
                                    || (me.getValue().equals("Ideas, dreams, “what could be,” philosophical"))
                                    ){
                                N=N+1;
                            }

                }
    // for Thinking and feeling

                if ((me.getKey().equals("question_3")) || (me.getKey().equals("question_7"))
                        || (me.getKey().equals("question_11"))
                        || (me.getKey().equals("question_15"))
                        || (me.getKey().equals("question_19")) ){

                        if (me.getValue().equals("Logical,thinking, questioning")
                                || (me.getValue().equals("Candid, straight forward, frank, experiential"))
                                || (me.getValue().equals("Firm, tend to criticize, hold the line"))
                                || (me.getValue().equals("Tough-minded, just"))
                                || (me.getValue().equals("matter of fact, issue-oriented"))){
                            T=T+1;
                        }
                        if (me.getValue().equals("Empathetic,feeling,accommodating")
                                || (me.getValue().equals("Tactful, kind, encouraging"))
                                || (me.getValue().equals("Gentle, tend to appreciate, conciliate"))
                                || (me.getValue().equals("Tender-hearted, merciful"))
                                || (me.getValue().equals("sensitive, people-oriented, compassionate"))
                                ){
                            F=F+1;
                        }
                    }
// for Juding or percpective

                if ((me.getKey().equals("question_4")) || (me.getKey().equals("question_8"))
                        || (me.getKey().equals("question_12"))
                        || (me.getKey().equals("question_16"))
                        || (me.getKey().equals("question_20")) ){

                        if (me.getValue().equals("Organized,orderly")
                                || (me.getValue().equals("Plan,schedule"))
                                || (me.getValue().equals("Regulated, structured"))
                                || (me.getValue().equals("Preparation, plan ahead"))
                                || (me.getValue().equals("control, govern"))){
                            J=J+1;
                        }
                        if (me.getValue().equals("Flexible,adaptable")
                                || (me.getValue().equals("Unplanned, spontaneous"))
                                || (me.getValue().equals("Easygoing, “live” and “let live”"))
                                || (me.getValue().equals("Go with the flow, adapt as you go"))
                                || (me.getValue().equals("latitude, freedom"))
                                ){
                            P=P+1;
                        }

                    }
            }

        if(E<I){
            type1='E';
        }
        else{
            type1='I';
        }

        if(S<N){
            type2 ='S';
        }
        else{
            type2='N';
        }

        if (T<F){
            type3='T';
        }
        else{
            type3='F';
        }

        if (J<P){
            type4='J';
        }
        else{
            type4='P';
        }

        System.out.println("Type 1 " +type1);
        System.out.println("Type 2 " +type2);
        System.out.println("Type 3 " +type3);
        System.out.println("Type 4 " +type4);
       String mayersType;
        StringBuilder sb = new StringBuilder();
        sb.append(type1);
        sb.append(type2);
        sb.append(type3);
        sb.append(type4);
        mayersType = sb.toString();
    return mayersType;

    }
    public  int PhillsScores(){
        int philsScore=0;
        Set set = answered_hashmap.entrySet();
        Iterator i = set.iterator();

//   score calculations
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            if (me.getKey().equals("21. When do you feel at your best?")){
                if (me.getValue().equals("In the morning")){
                    philsScore=philsScore+2;
                }
                if(me.getValue().equals("During the afternoon and early evening")){
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("Late at night")){
                    philsScore=philsScore+6;
                }
            }
            if(me.getKey().equals("22. You usually walk.....")){
                if(me.getValue().equals("Fairly fast, with long steps")){
                    philsScore=philsScore+6;
                }
                if(me.getValue().equals("Fairly fast, with little steps")){
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("Less fast head up, looking the world in the face")){
                    philsScore=philsScore+7;
                }
                if(me.getValue().equals("Less fast, head down")){
                    philsScore=philsScore+2;
                }
                if(me.getValue().equals("Very slowly")){
                    philsScore=philsScore+1;
                }
            }
            if(me.getKey().equals("23. When talking to people,you...")){
                if(me.getValue().equals("Stand with your arms folded")){
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("Have your hands clasped")){
                    philsScore=philsScore+2;
                }
                if(me.getValue().equals("Have one or both your hands on your hips or in pockets")){
                    philsScore=philsScore+5;
                }
                if(me.getValue().equals("Touch or push the person to whom you are talking")){
                    philsScore=philsScore+7;
                }
                if(me.getValue().equals("Play with your ear,touch your chin or smooth your hair")){
                    philsScore=philsScore+6;
                }
                }
            if(me.getKey().equals("24. When relaxing, you sit with...")){
                if(me.getValue().equals("Your knees bent with your legs neatly side by side")){
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("Your legs crossed")){
                    philsScore=philsScore+6;
                }
                if(me.getValue().equals("Your legs stretched out or straight")){
                    philsScore=philsScore+2;
                }
                if(me.getValue().equals("One leg curled under you")){
                    philsScore=philsScore+1;
                }
                }
            if(me.getKey().equals("25. When something really amuses you,You react with...")){
                if(me.getValue().equals("A big appreciated laugh")){
                    philsScore=philsScore+6;
                }
                if(me.getValue().equals("A laugh, but not a loud one")){
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("A quiet chuckle")){
                    philsScore=philsScore+3;
                }
                if(me.getValue().equals("A sheepish smile")){
                    philsScore=philsScore+5;
                }
            }
            if(me.getKey().equals("26. When you go to a party or social gathering, you...")){
                if(me.getValue().equals("Make a loud entrance so everyone notices you")){
                    philsScore=philsScore+6;
                }
                if(me.getValue().equals("Make a quiet entrance, looking around for someone you know")){
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("Make the quietest entrance, trying to stay unnoticed")){
                    philsScore=philsScore+2;
                }
            }
            if(me.getKey().equals("27. When you're working or concentrating very hard, and you're interrupted, you...")){
                if(me.getValue().equals("welcome the break")){
                    philsScore=philsScore+6;
                }
                if(me.getValue().equals("feel extremely irritated")){
                    philsScore=philsScore+2;
                }
                if(me.getValue().equals("vary between these two extremes")){
                    philsScore=philsScore+4;
                }
            }
            if(me.getKey().equals("28. Which of the following colors do you like most?")){
                if(me.getValue().equals("Red or orange")){
                    philsScore=philsScore+6;
                }
                if(me.getValue().equals("Black")){
                    philsScore=philsScore+7;
                }
                if(me.getValue().equals("Yellow or light blue")){
                    philsScore=philsScore+5;
                }
                if(me.getValue().equals("Green")){
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("Dark blue or purple")){
                    philsScore=philsScore+3;
                    System.out.println(philsScore);
                }
                if(me.getValue().equals("White")){
                    philsScore=philsScore+2;
                }
                if(me.getValue().equals("Brown or gray")){
                    philsScore=philsScore+1;
                }
            }
            if(me.getKey().equals("29. When you are in bed at night, in those last few moments before going to sleep,you lie...")){
                if(me.getValue().equals("Stretched out on your back")){
                    philsScore=philsScore+7;
                }
                if(me.getValue().equals("Stretched out face down on your stomach")){
                    philsScore=philsScore+6;
                }
                if(me.getValue().equals("On your side, slightly curled")){
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("With your head on one arm")){
                    philsScore=philsScore+2;
                }
                if(me.getValue().equals("With your head under the covers")){
                    philsScore=philsScore+1;
                }
            }
            if(me.getKey().equals("30. You often dream that you are...")){
                if(me.getValue().equals("Falling")){
                    System.out.println(philsScore);
                    philsScore=philsScore+4;
                }
                if(me.getValue().equals("Fighting or struggling")){
                    philsScore=philsScore+2;
                }
                if(me.getValue().equals("Searching for something or somebody")){
                    philsScore=philsScore+3;
                }
                if(me.getValue().equals("Flying or floating")){
                    philsScore=philsScore+5;
                }
                if(me.getValue().equals("You usually have dreamless sleep")){
                    philsScore=philsScore+6;
                }
                if(me.getValue().equals("Your dreams are always pleasant")){
                    philsScore=philsScore+1;
                }
            }
        }

      return philsScore;

    }


    public void put_answer(String key, String value)
    {
        answered_hashmap.put(key, value);
    }

    public String get_json_object() {
        Gson gson = new Gson();
        return gson.toJson(answered_hashmap,LinkedHashMap.class);
    }
    @Override
    public String toString() {
        return String.valueOf(answered_hashmap);
    }
    public static Answers getInstance() {
        if (uniqueInstance == null) {
            synchronized (Answers.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Answers();
                }
            }
        }
        return uniqueInstance;
    }
}
