package com.example.rotibanksystem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    FirebaseUser usern= FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenRefreshed= FirebaseInstanceId.getInstance().getToken();
        if(usern!=null)
            updateTokenToFirebase(tokenRefreshed);
    }

    private void updateTokenToFirebase(String tokenRefereshed) {
        FirebaseDatabase db=FirebaseDatabase.getInstance();

        DatabaseReference tokens=db.getReference("Tokens");
        Token token=new Token(tokenRefereshed,false);
        tokens.child(usern.getUid()).setValue(token);
    }
}
