#include <VirtualWire.h>
#include <avr/pgmspace.h>

byte message[VW_MAX_MESSAGE_LEN];    // Armazena as mensagens recebidas
byte msgLength = VW_MAX_MESSAGE_LEN; // Armazena o tamanho das mensagens
 
char *lamp_on = "\"lamp\":1";
char *lamp_off = "\"lamp\":0";

void setup(){
  pinMode(13, OUTPUT); 
  digitalWrite(13, LOW);
  
  Serial.begin(9600);
  Serial.println("RECEPTOR");
  vw_set_rx_pin(8); // Define o pino 8 do Arduino como entrada 
  //de dados do receptor
  vw_setup(1200);             // Bits por segundo
  vw_rx_start();              // Inicializa o receptor
}

void loop(){
  uint8_t message[VW_MAX_MESSAGE_LEN];    
  uint8_t msgLength = VW_MAX_MESSAGE_LEN; 
  
  for(int i=0; i<VW_MAX_MESSAGE_LEN; i++){
//      message[i] = (char) (0);
      message[i] = '#';//\0';
  }
  //Serial.println("");
  
  if (vw_get_message(message, &msgLength)) // Non-blocking
  {
    //Serial.print("Recebido: ");
   /* for (int i = 0; i < msgLength; i++)
    {
      Serial.print((char) (message[i]));
    }
    Serial.println("");*/
  }
  
  
  char tmp[VW_MAX_MESSAGE_LEN];
  
  for(int i=0; i< msgLength; i++){
    //tmp[i] = '\0';
    tmp[i] = (char) (message[i]);
    //Serial.print(tmp[i]);
  } 
  /*
  Serial.print("[[[");
  Serial.print(tmp);  Serial.print("]]]");*/
  
   for(int i=0; i<VW_MAX_MESSAGE_LEN; i++){
    Serial.print((char) (message[i]));    
   }Serial.println("");
    
 
  if(strstr(tmp, lamp_off) != NULL) {
      digitalWrite(13, LOW); 
      Serial.print("$OFF");
      delay(1000);
  }
  
  if(strstr(tmp, lamp_on) != NULL) {
     digitalWrite(13, HIGH); 
      Serial.println("#ON");
      delay(1000);
  } 
  
  
}


