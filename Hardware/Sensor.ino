/*
{"r":1,"l":6.232649,"h":6.252200,"t":30.195313}
*/

#include <stdlib.h>
#include <string.h>
#include <VirtualWire.h>

int temperaturePin = A3;
int gasPin = A4;
int luminosityPin = A5;
int reedSwitchPin = 13;
int radioPin = 5;

float sensorValues[3]={0};
int samples = 100;
char data[100] = {0};
 
void setup(){
  Serial.begin(9600);
  Serial.println("Iniciando...");
  
  pinMode(reedSwitchPin,INPUT);
  
  vw_set_tx_pin(radioPin);
  vw_setup(1200);   // Bits per sec
  
  send("OK 1");  
  delay(1000);
  send("OK 2");  
  delay(1000);
  send("OK 3");  
  delay(1000);
}
 
void loop(){  
 makePacket();  
 //Serial.println(harmfulGases());
  delay(100); 
} 
 
void send (char *message){
  vw_send((uint8_t *)message, strlen(message));
  vw_wait_tx(); // Aguarda o envio de dados
}


void makePacket(){
    readAllSensors();
    
    char buffer[20]={0};
    String packet = "{";
    
    packet += "\"alarmSensor\":";
    
    int reed = reedSwitch();
    if(reed == 0){
        packet += "true";
    }else{
        packet += "false";  
    //packet += reedSwitch();
    }    
    packet += ",";
    
    packet += "\"luminosity\":";
    dtostrf(sensorValues[0], 4, 6, buffer);
    packet += buffer;    
    packet += ",";
    
    packet.toCharArray(data, 100);
    send(data);
    //Serial.print(data);
    packet = "";
    
    packet += "\"harmfulGases\":";
    dtostrf(sensorValues[1], 4, 6, buffer);
    packet += buffer;  
    packet += ",";
    
    packet += "\"temperature\":";
    dtostrf(sensorValues[2], 4, 6, buffer);
    packet += buffer;  
    packet += "}";
    
    packet.toCharArray(data, 100);
    send(data);
    //Serial.println(data);
    packet = "";

}


float temperature(){
    float temperatureValue = (analogRead(temperaturePin) * 0.48828125);
    return temperatureValue;
}
float luminosity(){
    float luminosityValue = (analogRead(luminosityPin) / 10.23);
    return luminosityValue;
}
float harmfulGases(){
    float gasValue = (analogRead(gasPin) / 10.23);
    return gasValue;
}
int reedSwitch(){
    int reedSwitchValue = digitalRead(reedSwitchPin);
    return reedSwitchValue;
}

void readAllSensors(){
    delay(20);                        //A/D conversor
    sensorValues[0] = 0;
    sensorValues[1] = 0;
    sensorValues[2] = 0;

    for(int i=0;i<samples;i++){
        sensorValues[0] += luminosity();
        sensorValues[1] += harmfulGases();
        sensorValues[2] += temperature();
        delay(10);  
    }
    for(int i=0;i<3;i++){
        sensorValues[i] /= samples;      //Faz a média aritmética.
        //sensorValues[i] = 0;
    }    
}
