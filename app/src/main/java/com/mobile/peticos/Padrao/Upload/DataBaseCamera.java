package com.mobile.peticos.Padrao.Upload;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DataBaseCamera {
    public void uploadGallary(Context context, ImageView foto, Map<String, String> docData) {
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] databyte = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        String arquivo = "Postagem" + System.currentTimeMillis() + ".jpg";
        storage.getReference("galeria").child(arquivo)
                .putBytes(databyte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "Upload feito com sucesso!", Toast.LENGTH_SHORT).show();
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                docData.put("url", uri.toString());
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("url", docData.get("url")); // Retorna a URL
                                ((Activity) context).setResult(Activity.RESULT_OK, returnIntent); // Define o resultado
                                ((Activity) context).finish(); // Finaliza a atividade

                            }
                            public void onFailure(Exception e) {
                                Toast.makeText(context, "Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    public void onFailure(Exception e) {
                        Toast.makeText(context, "Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void downloadGallery(ImageView img, Uri urlFirebase) {
        img.setRotation(0);
        Glide.with(img.getContext()).asBitmap().load(urlFirebase).into(img);
    }


}
