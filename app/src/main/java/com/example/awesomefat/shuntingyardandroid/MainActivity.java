package com.example.awesomefat.shuntingyardandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private EditText inputTF;
    private TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.inputTF = (EditText)this.findViewById(R.id.inputTF);
        this.outputText = (TextView)this.findViewById(R.id.outputText);

        this.inputTF.setText("4+2*3-2");
    }

    public void shuntingYardButtonPressed(View v)
    {
        ShuntingYard sy = new ShuntingYard(this.inputTF.getText().toString());
        String answer = sy.getAnswer();
        this.outputText.setText(answer);
    }
}
