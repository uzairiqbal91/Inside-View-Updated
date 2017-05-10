package androidadvance.com.androidsurveyexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class CognitiveCa extends AppCompatActivity {

    private   static String DB_NAME = "InsideView";
    private   static String ENTITY_NAME_PROFILES = "profiles";
    private   static FirebaseDatabase firebase;
    private   static DatabaseReference database;
    private static DatabaseReference table;


    public CognitiveCa() {
        firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference(DB_NAME);
        table = database.child(ENTITY_NAME_PROFILES);
    }
    private FaceServiceClient faceServiceClient =
            new FaceServiceRestClient("2b72ded620924d3f939549773ee9dd7d");
    private final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private ProgressDialog detectionProgressDialog;
    Button button1;
    Face[] resultant;
    public Face[] result;
    public Face[] getface(){return result;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognitive_ca);
        button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent gallIntent = new Intent(Intent.ACTION_GET_CONTENT);
                gallIntent.setType("image*//*");
                startActivityForResult(Intent.createChooser(gallIntent, "Select Picture"), PICK_IMAGE);*/
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });

        detectionProgressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Uri uri = data.getData();
            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            imageView.setImageBitmap(photo);
            detectAndFrame(photo);
            drawFaceRectanglesOnBitmap(photo,resultant);


        }
    }

    // Detect faces by uploading face images
// Frame faces after detection

    private void detectAndFrame(final Bitmap imageBitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());
        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    true,        // returnFaceLandmarks
                                    null        // returnFaceAttributes: a string like "age, gender"
                            );


                            if (result == null)
                            {
                                publishProgress("Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(
                                    String.format("Detection Finished. %d face(s) detected",
                                            result.length));
                            //faceServiceClient.getPersonFace();

                            //Toast.makeText(MainActivity.this, ">>>> "+result[0].faceLandmarks.eyebrowLeftInner.x, Toast.LENGTH_LONG).show();
                            return result;
                        } catch (Exception e) {
                            publishProgress("Detection failed");
                            return null;
                        }
                    }
                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                    }
                    @Override
                    public void onPostExecute(Face[] result) {
                        //TODO: update face frames

// Write a message to the database
                         DatabaseReference record = table.push();
                        record.setValue(result[0]);
                        resultant = result;

                    }
                };
        detectTask.execute(inputStream);
    }
    public Face[] getResult(){return result;}
    private static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        int stokeWidth = 2;
        paint.setStrokeWidth(stokeWidth);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
            }
        }
        return bitmap;
    }



}
