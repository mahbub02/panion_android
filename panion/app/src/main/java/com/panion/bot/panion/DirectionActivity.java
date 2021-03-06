package com.panion.bot.panion;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class DirectionActivity extends AppCompatActivity {

    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice = null;

    final byte delimiter = 33;
    int readBufferPosition = 0;
    BluetoothAdapter mBluetoothAdapter;

    public void sendBtMsg(String msg2send){
        //UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"); //Standard SerialPortService ID
        try {


            if (mmSocket.isConnected()){
                String msg = msg2send;
                OutputStream mmOutputStream = mmSocket.getOutputStream();
                mmOutputStream.write(msg.getBytes());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        final Handler handler = new Handler();

        final TextView myLabel = (TextView) findViewById(R.id.message);
        final Button leftButton = (Button) findViewById(R.id.leftButton);
        final Button rightButton = (Button) findViewById(R.id.rightButton);
        final Button forwardButton = (Button) findViewById(R.id.forwardButton);
        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button stopButton = (Button) findViewById(R.id.stopButton);
        final Button statusButton = (Button) findViewById(R.id.statusButton);

         mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


//        final class workerThread implements Runnable {
//
//            private String btMsg;
//
//            public workerThread(String msg) {
//                btMsg = msg;
//            }
//
//            public void run()
//            {
//
//                sendBtMsg(btMsg);
//
//                while(!Thread.currentThread().isInterrupted())
//                {
//                    int bytesAvailable;
//                    boolean workDone = false;
//
//                    try {
//
//
//
//                        final InputStream mmInputStream;
//                        mmInputStream = mmSocket.getInputStream();
//                        bytesAvailable = mmInputStream.available();
//                        if(bytesAvailable > 0)
//                        {
//
//                            byte[] packetBytes = new byte[bytesAvailable];
//                            Log.e("Aquarium recv bt","bytes available");
//                            byte[] readBuffer = new byte[1024];
//                            mmInputStream.read(packetBytes);
//
//                            for(int i=0;i<bytesAvailable;i++)
//                            {
//                                byte b = packetBytes[i];
//                                if(b == delimiter)
//                                {
//                                    byte[] encodedBytes = new byte[readBufferPosition];
//                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
//                                    final String data = new String(encodedBytes, "US-ASCII");
//                                    readBufferPosition = 0;
//
//                                    //The variable data now contains our full command
//                                    handler.post(new Runnable()
//                                    {
//                                        public void run()
//                                        {
//                                            myLabel.setText(data);
//                                        }
//                                    });
//
//                                    workDone = true;
//                                    break;
//
//
//                                }
//                                else
//                                {
//                                    readBuffer[readBufferPosition++] = b;
//                                }
//                            }
//
//                            if (workDone == true){
//                                mmSocket.close();
//                                break;
//                            }
//
//                        }
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        };


        // start temp button handler

        statusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on temp button click

                UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee"); //Standard SerialPortService ID
                try {
                    if(mmSocket==null)
                        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
                    if (!mmSocket.isConnected()){
                        mmSocket.connect();
                    }
                    myLabel.setText("Socket connection status: connected="+Boolean.toString(mmSocket.isConnected()));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on temp button click

                sendBtMsg("l");

            }
        });


        //end temp button handler

        //start light on button handler
        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on temp button click

                sendBtMsg("r");

            }
        });
        //end light on button handler

        //start light off button handler

        forwardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on temp button click

                sendBtMsg("f");

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on temp button click

                sendBtMsg("b");

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on temp button click

                sendBtMsg("s");

            }
        });


        // end light off button handler

        if(mBluetoothAdapter!=null && !mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        if(mBluetoothAdapter!=null ) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if(pairedDevices.size() > 0)
            {
                for(BluetoothDevice device : pairedDevices)
                {
                    if(device.getName().equals("Device Name")) //Note, you will need to change this to match the name of your device
                    {
                        Log.e("Aquarium",device.getName());
                        mmDevice = device;
                        break;
                    }
                }
            }
        }




    }



}
