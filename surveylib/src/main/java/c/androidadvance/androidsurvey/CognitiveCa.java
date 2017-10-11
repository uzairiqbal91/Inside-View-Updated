package c.androidadvance.androidsurvey;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CognitiveCa extends AppCompatActivity {

    private   static String DB_NAME = "InsideView";
    private   static String ENTITY_NAME_PROFILES = "profiles";
    private   static FirebaseDatabase firebase;
    private   static DatabaseReference database;
    private StorageReference mstorage;
    private static DatabaseReference table;

    public CognitiveCa() {
        firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference(DB_NAME);
        table = database.child(ENTITY_NAME_PROFILES);
        mstorage= FirebaseStorage.getInstance().getReference();
    }
    private FaceServiceClient faceServiceClient =
            new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0","b73895176fcd47928f96dcbe934d879e");
    private final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 18;
    private ProgressDialog detectionProgressDialog;
    Button button1;
    public static Bitmap photo;
    Face[] resultant;
    public static Face[] result1;
    public static ImageView imageView;
    public Face[] getface(){return result1;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognitive_ca);
       // button1 = (Button) findViewById(R.id.button1);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////               Intent gallIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);;
////
////                startActivityForResult(Intent.createChooser(gallIntent, "Select Picture"), PICK_IMAGE);
//
//            }
//        });
        detectionProgressDialog = new ProgressDialog(this);
    }

       @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
             photo = (Bitmap) data.getExtras().get("data");
            Answers.bitmap=photo;
            Uri uri = data.getData();
            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            imageView.setImageBitmap(photo);



            detectAndFrame(photo);
            drawFaceRectanglesOnBitmap(photo,resultant);

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageLabel.setImageBitmap(imageBitmap);
//            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"erro",Toast.LENGTH_LONG).show();
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
                            result1 = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    true,        // returnFaceLandmarks
                                    null        // returnFaceAttributes: a string like "age, gender"
                            );

                            if (result1 == null)
                            {
                                publishProgress("Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(
                                    String.format("Detection Finished. %d face(s) detected",
                                            result1.length));
                            //faceServiceClient.getPersonFace();

                            //Toast.makeText(MainActivity.this, ">>>> "+result1[0].faceLandmarks.eyebrowLeftInner.x, Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),UserProfile.class);
                            intent.putExtra("userimage",photo);
                            startActivity(intent);
                            finish();
                            return result1;
                        } catch (Exception e) {
                            publishProgress("Detection failed");
                            return null;
                        }
                    }
                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                        detectionProgressDialog.show();
                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        detectionProgressDialog.setMessage(progress[0]);
                    }
                    @Override
                    public void onPostExecute(Face[] result) {
                        //TODO: update face frames
                        detectionProgressDialog.dismiss();
                        //Write a message to the database
                       // DatabaseReference record = table.push();
//                        record.setValue(result[0]);
                       resultant = result;
                    }
                };
        detectTask.execute(inputStream);
    }
    public Face[] getResult1(){return result1;}
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


//import android.support.v7.app.AppCompatActivity;
//
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Random;
//
//import javax.net.ssl.SSLException;
//public class CognitiveCa extends AppCompatActivity {
//
//    public static void main(String[] args) throws Exception{
//
//        File file = new File("YOUR IMAGE PATH");
//        byte[] buff = getBytesFromFile(file);
//        String url = "https://api-us.faceplusplus.com/facepp/v3/detect";
//        HashMap<String, String> map = new HashMap<>();
//        HashMap<String, byte[]> byteMap = new HashMap<>();
//        map.put("api_key", "WRQkh5E3Y8BiiRjKK2E3WBkX9hz_0T7w");
//        map.put("api_secret", "Z7Yn2vVRm9T52b8oFK8KtfrEbpjhvwWE");
//        byteMap.put("image_file", buff);
//        try{
//            byte[] bacd = post(url, map, byteMap);
//            String str = new String(bacd);
//            System.out.println(str);
//        }catch (Exception e) {
//            e.printStackTrace();
//          }
//    }
//
//    private final static int CONNECT_TIME_OUT = 30000;
//    private final static int READ_OUT_TIME = 50000;
//    private static String boundaryString = getBoundary();
//    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
//        HttpURLConnection conne;
//        URL url1 = new URL(url);
//        conne = (HttpURLConnection) url1.openConnection();
//        conne.setDoOutput(true);
//        conne.setUseCaches(false);
//        conne.setRequestMethod("POST");
//        conne.setConnectTimeout(CONNECT_TIME_OUT);
//        conne.setReadTimeout(READ_OUT_TIME);
//        conne.setRequestProperty("accept", "*/*");
//        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
//        conne.setRequestProperty("connection", "Keep-Alive");
//        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
//        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
//        Iterator iter = map.entrySet().iterator();
//        while(iter.hasNext()){
//            Map.Entry<String, String> entry = (Map.Entry) iter.next();
//            String key = entry.getKey();
//            String value = entry.getValue();
//            obos.writeBytes("--" + boundaryString + "\r\n");
//            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
//                    + "\"\r\n");
//            obos.writeBytes("\r\n");
//            obos.writeBytes(value + "\r\n");
//        }
//        if(fileMap != null && fileMap.size() > 0){
//            Iterator fileIter = fileMap.entrySet().iterator();
//            while(fileIter.hasNext()){
//                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
//                obos.writeBytes("--" + boundaryString + "\r\n");
//                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
//                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
//                obos.writeBytes("\r\n");
//                obos.write(fileEntry.getValue());
//                obos.writeBytes("\r\n");
//            }
//        }
//        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
//        obos.writeBytes("\r\n");
//        obos.flush();
//        obos.close();
//        InputStream ins = null;
//        int code = conne.getResponseCode();
//        try{
//            if(code == 200){
//                ins = conne.getInputStream();
//            }else{
//                ins = conne.getErrorStream();
//            }
//        }catch (SSLException e){
//            e.printStackTrace();
//            return new byte[0];
//        }
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] buff = new byte[4096];
//        int len;
//        while((len = ins.read(buff)) != -1){
//            baos.write(buff, 0, len);
//        }
//        byte[] bytes = baos.toByteArray();
//        ins.close();
//        return bytes;
//    }
//    private static String getBoundary() {
//        StringBuilder sb = new StringBuilder();
//        Random random = new Random();
//        for(int i = 0; i < 32; ++i) {
//            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
//        }
//        return sb.toString();
//    }
//    private static String encode(String value) throws Exception{
//        return URLEncoder.encode(value, "UTF-8");
//    }
//
//    public static byte[] getBytesFromFile(File f) {
//        if (f == null) {
//            return null;
//        }
//        try {
//            FileInputStream stream = new FileInputStream(f);
//            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
//            byte[] b = new byte[1000];
//            int n;
//            while ((n = stream.read(b)) != -1)
//                out.write(b, 0, n);
//            stream.close();
//            out.close();
//            return out.toByteArray();
//        } catch (IOException e) {
//        }
//        return null;
//    }
//
//}