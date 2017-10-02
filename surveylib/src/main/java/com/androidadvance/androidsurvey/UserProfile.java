package com.androidadvance.androidsurvey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserProfile extends AppCompatActivity {
    public static RatingBar ratingRatingBar;
    private static String DB_NAME = "InsideView";
    private static String ENTITY_NAME_PROFILES = "profiles";
    public static Bitmap photo;
    private FirebaseDatabase firebase;
    private DatabaseReference database;
    private DatabaseReference table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String age=getIntent().getStringExtra("age");
        String gender=getIntent().getStringExtra("gender");
        TextView textView= (TextView) findViewById(R.id.textView);
        TextView textView2= (TextView) findViewById(R.id.textView2);
        TextView textView3= (TextView) findViewById(R.id.textView3);


        ImageView imageView3= (ImageView) findViewById(R.id.imageView3);
         ratingRatingBar = (RatingBar) findViewById(R.id.rating_rating_bar);
//        firebase = FirebaseDatabase.getInstance();
//        database = firebase.getReference(DB_NAME);
//        table = database.child(ENTITY_NAME_PROFILES);
        ImageView bgImageView= (ImageView) findViewById(R.id.backgrndimageview);
        ImageView imgview = (ImageView) findViewById(R.id.imageView2);

Answers a=new Answers();


        imageView3.setImageResource(R.drawable.female);
        Bitmap photo=(Bitmap) getIntent().getParcelableExtra("userimage");
        imgview.setImageBitmap(photo);
textView.setText(MainActivity.spinner2.getSelectedItem().toString());
        if(Answers.getInstance().MayersScore().equalsIgnoreCase("istj"))
        {
            bgImageView.setBackgroundResource(R.drawable.istj);
            textView2.setText("Introversion , Sensing , Thinking , Judging ");
            textView3.setText("Quiet, serious, earn success by thoroughness and dependability. Practical, matter-of-fact, realistic, and responsible. Decide logically what should be done and work toward it steadily, regardless of distractions. Take pleasure in making everything orderly and organized - their work, their home, their life. Value traditions and loyalty.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();

        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("isfj"))
        {
            bgImageView.setBackgroundResource(R.drawable.isfj);
            textView2.setText("Introversion , Sensing , Feeling , Judging ");
            textView3.setText("Quiet, friendly, responsible, and conscientious. Committed and steady in meeting their obligations. Thorough, painstaking, and accurate. Loyal, considerate, notice and remember specifics about people who are important to them, concerned with how others feel. Strive to create an orderly and harmonious environment at work and at home.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();


        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("infj")) {
            bgImageView.setBackgroundResource(R.drawable.infj);
            textView2.setText("Introversion , Intuition , Feeling , Judging ");
            textView3.setText("Seek meaning and connection in ideas, relationships, and material possessions. Want to understand what motivates people and are insightful about others. Conscientious and committed to their firm values. Develop a clear vision about how best to serve the common good. Organized and decisive in implementing their vision.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();

        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("intj")) {
            bgImageView.setBackgroundResource(R.drawable.intj);
            textView2.setText("Introversion , Intuition ,  Thinking , Judging ");
            textView3.setText("Have original minds and great drive for implementing their ideas and achieving their goals. Quickly see patterns in external events and develop long-range explanatory perspectives. When committed, organize a job and carry it through. Skeptical and independent, have high standards of competence and performance - for themselves and others.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();


        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("istp")) {
            bgImageView.setBackgroundResource(R.drawable.istp);
            textView2.setText("Introversion , Sensing  , Thinking ,Perceiving ");
            textView3.setText("Tolerant and flexible, quiet observers until a problem appears, then act quickly to find workable solutions. Analyze what makes things work and readily get through large amounts of data to isolate the core of practical problems. Interested in cause and effect, organize facts using logical principles, value efficiency.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();


        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("isfp")) {
            bgImageView.setBackgroundResource(R.drawable.isfp);
            textView2.setText("Introversion , Sensing  , Feeling ,Perceiving ");
            textView3.setText("Quiet, friendly, sensitive, and kind. Enjoy the present moment, what's going on around them. Like to have their own space and to work within their own time frame. Loyal and committed to their values and to people who are important to them. Dislike disagreements and conflicts, do not force their opinions or values on others.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();


        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("infp")) {
            bgImageView.setBackgroundResource(R.drawable.infp);
            textView2.setText("Introversion , Intuition  , Feeling ,Perceiving ");
            textView3.setText("Idealistic, loyal to their values and to people who are important to them. Want an external life that is congruent with their values. Curious, quick to see possibilities, can be catalysts for implementing ideas. Seek to understand people and to help them fulfill their potential. Adaptable, flexible, and accepting unless a value is threatened.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();




        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("intp")) {
            bgImageView.setBackgroundResource(R.drawable.intp);
            textView2.setText("Introversion , Intuition  , Thinking ,Perceiving ");
            textView3.setText("Seek to develop logical explanations for everything that interests them. Theoretical and abstract, interested more in ideas than in social interaction. Quiet, contained, flexible, and adaptable. Have unusual ability to focus in depth to solve problems in their area of interest. Skeptical, sometimes critical, always analytical.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();





        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("estp")) {
            bgImageView.setBackgroundResource(R.drawable.estp);
            textView2.setText("Extraversion ,Sensing , Thinking ,Perceiving ");
            textView3.setText("Flexible and tolerant, they take a pragmatic approach focused on immediate results. Theories and conceptual explanations bore them - they want to act energetically to solve the problem. Focus on the here-and-now, spontaneous, enjoy each moment that they can be active with others. Enjoy material comforts and style. Learn best through doing.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();


        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("esfp")) {
            bgImageView.setBackgroundResource(R.drawable.esfp);
            textView2.setText("Extraversion ,Sensing , Feeling , Perceiving ");
            textView3.setText("Outgoing, friendly, and accepting. Exuberant lovers of life, people, and material comforts. Enjoy working with others to make things happen. Bring common sense and a realistic approach to their work, and make work fun. Flexible and spontaneous, adapt readily to new people and environments. Learn best by trying a new skill with other people.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();

        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("enfp")) {
            bgImageView.setBackgroundResource(R.drawable.enfp);
            textView2.setText("Extraversion , Intuition , Feeling , Perceiving ");
            textView3.setText("Warmly enthusiastic and imaginative. See life as full of possibilities. Make connections between events and information very quickly, and confidently proceed based on the patterns they see. Want a lot of affirmation from others, and readily give appreciation and support. Spontaneous and flexible, often rely on their ability to improvise and their verbal fluency.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();


        }

        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("entp")) {
            bgImageView.setBackgroundResource(R.drawable.entp);
            textView2.setText("Extraversion , Intuition , Thinking , Perceiving ");
            textView3.setText("Quick, ingenious, stimulating, alert, and outspoken. Resourceful in solving new and challenging problems. Adept at generating conceptual possibilities and then analyzing them strategically. Good at reading other people. Bored by routine, will seldom do the same thing the same way, apt to turn to one new interest after another.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();



        }

        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("estj")) {
            bgImageView.setBackgroundResource(R.drawable.estj);
            textView2.setText("Extraversion , Sensing , Thinking , Judging ");
            textView3.setText("Practical, realistic, matter-of-fact. Decisive, quickly move to implement decisions. Organize projects and people to get things done, focus on getting results in the most efficient way possible. Take care of routine details. Have a clear set of logical standards, systematically follow them and want others to also. Forceful in implementing their plans.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();




        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("esfj")) {
            bgImageView.setBackgroundResource(R.drawable.esfj);
            textView2.setText("Extraversion , Sensing , Feeling , Judging ");
            textView3.setText("Warmhearted, conscientious, and cooperative. Want harmony in their environment, work with determination to establish it. Like to work with others to complete tasks accurately and on time. Loyal, follow through even in small matters. Notice what others need in their day-by-day lives and try to provide it. Want to be appreciated for who they are and for what they contribute.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();





        }
        else if(Answers.getInstance().MayersScore().equalsIgnoreCase("enfj")) {
            bgImageView.setBackgroundResource(R.drawable.enfj);
            textView2.setText("Extraversion , Intuition , Feeling , Judging  ");
            textView3.setText("Warm, empathetic, responsive, and responsible. Highly attuned to the emotions, needs, and motivations of others. Find potential in everyone, want to help others fulfill their potential. May act as catalysts for individual and group growth. Loyal, responsive to praise and criticism. Sociable, facilitate others in a group, and provide inspiring leadership.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();


        }
       else if(Answers.getInstance().MayersScore().equalsIgnoreCase("entj")) {
            bgImageView.setBackgroundResource(R.drawable.entj);
            textView2.setText("Extraversion , Intuition , Thinking , Judging  ");
            textView3.setText("Frank, decisive, assume leadership readily. Quickly see illogical and inefficient procedures and policies, develop and implement comprehensive systems to solve organizational problems. Enjoy long-term planning and goal setting. Usually well informed, well read, enjoy expanding their knowledge and passing it on to others. Forceful in presenting their ideas.");
            Toast.makeText(this,Answers.getInstance().MayersScore(),Toast.LENGTH_LONG).show();



        }







        if(MainActivity.spinner1.getSelectedItem().toString().equalsIgnoreCase("Female"))
        {
       imageView3.setImageResource(R.drawable.female);
        }
        else
        {
            imageView3.setImageResource(R.drawable.male);

        }
       // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
           // imgview.setImageBitmap(bitmap);



      //  startActivityForResult(MainActivity.imgIntent, MainActivity.REQUEST_CODE01);

    }

    public void btnfinishclicked(View view) {

        //Toast.makeText(this,String.valueOf(ratingRatingBar.getRating()),Toast.LENGTH_SHORT).show();
        this.finish();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == MainActivity.REQUEST_CODE01 && resultCode == RESULT_OK) {
//
//
//            ImageView imgview = (ImageView) findViewById(R.id.imageView2);
//
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgview.setImageBitmap(bitmap);
//
//        }
//    }
}