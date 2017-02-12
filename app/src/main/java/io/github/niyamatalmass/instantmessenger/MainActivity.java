package io.github.niyamatalmass.instantmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int KEY_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            TextView usernameTextView = (TextView) findViewById(R.id.userNameTextView);
            usernameTextView.setText(auth.getCurrentUser().getDisplayName());
        } else {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                    .build(), KEY_SIGN_IN);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KEY_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user successfully signed in
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                // signed in process unsuccessful
                Toast.makeText(this, "There was a problem! Please try again!", Toast.LENGTH_SHORT).show();
                return;
            } else if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
                // sign in process unsuccessful because lack of network
                //showSnackbar("Network unavailable!");
                Toast.makeText(this, "Network unavailable! Please try again", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


}
