

package com.neteamindustry.dearmantan.ui;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.service.exception.BadRequestException;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;
import com.neteamindustry.dearmantan.AlertDialogFragment;
import com.neteamindustry.dearmantan.R;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;

import org.w3c.dom.Text;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * DaftarMantanActivity list activity
 */
public class CobaBalikanActivity extends AppCompatActivity {

    private static final String USER_USER = "user";
    private static final String USER_WATSON = "watson";

    private ConversationService conversationService;
    private Map<String, Object> conversationContext;
    private ArrayList<ConversationMessage> conversationLog;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cobabalikan_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize watson conversation
        conversationService = new ConversationService(ConversationService.VERSION_DATE_2016_07_11);
        conversationService.setUsernameAndPassword("xxxxxx-xxxx-xxxxx-xxxxx-xxx",
                "xxxxxxx-xxxxxx-xxxxx-xxx");
        conversationLog = new ArrayList<>();

        //jika nmempunyai saved instance

        if (savedInstanceState != null) {
            conversationContext = (Map<String, Object>) savedInstanceState.getSerializable("context");
            conversationLog = (ArrayList<ConversationMessage>) savedInstanceState.getSerializable("backlog");

            //repopulasii Ui nya dengan pesan sebelum nya
            if (conversationLog != null) {
                for (ConversationMessage message : conversationLog) {
                    addMessageFromUser(message);
                }
            }

            final ScrollView scrollView = (ScrollView) findViewById(R.id.message_scrollview);
            scrollView.scrollTo(0, scrollView.getBottom());
        } else {
            //validasi untuk user credntial benar
            ValidateCredentialTask vct = new ValidateCredentialTask();
            vct.execute();
        }

        ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView entryText = (TextView) findViewById(R.id.entry_text);
                String text = entryText.getText().toString();

                if (!text.isEmpty()) {
                    //menambahkan pesan ke UI nya
                    addMessageFromUser(new ConversationMessage(text, USER_USER));

                    //record pesan di log konversasi
                    conversationLog.add(new ConversationMessage(text, USER_USER));

                    //mengirim pesan ke watson konversai
                    ConversationTask ct = new ConversationTask();
                    ct.execute(text);

                    entryText.setText("");
                }

            }
        });

        //sdk watson harus sudah terinstall
        BMSClient.getInstance().initialize(getApplicationContext(), BMSClient.REGION_US_SOUTH);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu nya
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handling action bar
        int id = item.getItemId();

        if (id == R.id.clear_session_action) {
            //clear conversation
            conversationContext = null;
            conversationLog = new ArrayList<>();

            LinearLayout messageContainer = (LinearLayout) findViewById(R.id.message_container);
            messageContainer.removeAllViews();

            //restar konversasi nya
            ConversationTask ct = new ConversationTask();
            ct.execute("");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    @SuppressWarnings("Unchecked")
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //retain konvesasi
        if (conversationContext != null) {
            HashMap map = new HashMap(conversationContext);
            savedInstanceState.putSerializable("context", map);
        }
        if (conversationLog != null) {
            savedInstanceState.putSerializable("backlog", conversationLog);
        }
    }



    private void showDialog(int errorTitle, String errorMessage, boolean canContinue) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(errorTitle, errorMessage, canContinue);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private void addMessageFromUser(ConversationMessage message) {
        View messageView;
        LinearLayout messagecontainer = (LinearLayout) findViewById(R.id.message_container);

        if (message.getUser().equals(USER_WATSON)) {
            messageView = this.getLayoutInflater().inflate(R.layout.watson_text, messagecontainer, false);
            TextView watsonMessageText = (TextView) messageView.findViewById(R.id.watsonTextView);
            watsonMessageText.setText(message.getMessageText());
        } else {
            messageView = this.getLayoutInflater().inflate(R.layout.user_text, messagecontainer, false);
            TextView userMessageText = (TextView) messageView.findViewById(R.id.userTextView);
            userMessageText.setText(message.getMessageText());
        }

        messagecontainer.addView(messageView);


        //scroll ke bawah view nya untuk melihat update
        final ScrollView scrollView = (ScrollView) findViewById(R.id.message_scrollview);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

    }


    private class ValidateCredentialTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean success = true;

            try {
                conversationService.getToken().execute();
            } catch (Exception ex) {
                success = false;

                //melihat jika credential valid atau tidak
                if (ex.getClass().equals(UnauthorizedException.class) ||
                        ex.getClass().equals(IllegalArgumentException.class)) {
                    showDialog(R.string.error_title_invalid_credentials,
                            getString(R.string.error_message_invalid_credentials), false);
                } else if (ex.getCause() != null &&
                        ex.getCause().getClass().equals(UnknownHostException.class)) {
                    showDialog(R.string.error_title_bluemix_connection,
                            getString(R.string.error_message_bluemix_connection), true);
                } else {
                    showDialog(R.string.error_title_default, ex.getMessage(), true);
                }

            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                ConversationTask ct = new ConversationTask();
                ct.execute("");
            }
        }
    }


    private class ConversationTask extends AsyncTask <String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String entryText = params[0];

            MessageRequest messageRequest;

            //mengirim context ke watson untuk melanjutkan conversation
            if (conversationContext == null) {
                messageRequest = new MessageRequest.Builder()
                        .inputText(entryText).build();
            } else {
                messageRequest = new MessageRequest.Builder()
                        .inputText(entryText)
                        .context(conversationContext).build();
            }
            try {
                MessageResponse messageResponse = conversationService.message(
                        "e89d2f82-c021-4c15-b9c8-86aec0ad1953", messageRequest).execute();

                conversationContext = messageResponse.getContext();
                return messageResponse.getText().get(0);
            } catch (Exception ex) {
                if (ex.getClass().equals(BadRequestException.class)) {
                    showDialog(R.string.error_title_invalid_workspace,
                            getString(R.string.error_message_invalid_workspace), false);
                } else {
                    showDialog(R.string.error_title_default, ex.getMessage(), true);
                }
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            // menambahkan pesan dari watson ke ui nya
            addMessageFromUser(new ConversationMessage(result, USER_WATSON));

            // merekam konversasi di conversation log
            conversationLog.add(new ConversationMessage(result, USER_WATSON));
        }
    }

}

