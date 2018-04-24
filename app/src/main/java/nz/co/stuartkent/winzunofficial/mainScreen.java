package nz.co.stuartkent.winzunofficial;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;


public class mainScreen extends AppCompatActivity {

    private MarkdownView mMarkdownView;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMarkdownView = (MarkdownView)findViewById(R.id.markdown_view);
        mMarkdownView.addStyleSheet(new Github());

        mMarkdownView.loadMarkdownFromAsset("markdown1.md");

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Client Number");


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor  editor = sharedPref.edit();

        String titleText = new String(sharedPref.getString("cnum",getString(R.string.app_name)));

        if (titleText!="") {
            Toolbar tbar = findViewById(R.id.toolbar);
            setSupportActionBar(tbar);
            getSupportActionBar().setTitle(titleText);

        }

        this.invalidateOptionsMenu();


        // Set up the input
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        final Context mContext = this.getApplicationContext();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String clientNumber =  input.getText().toString();


                if (clientNumber!= "") {

                    editor.putString("cnum", new String(clientNumber));
                    editor.commit();

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        Uri number = Uri.parse("tel:01970800559009");
        final Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(callIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
