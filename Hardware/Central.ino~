#include <VirtualWire.h>


byte message[VW_MAX_MESSAGE_LEN];    // Armazena as mensagens recebidas
byte msgLength = VW_MAX_MESSAGE_LEN; // Armazena o tamanho das mensagens

//CONNECT
String _login = "$CONNECT;avelino;123;";
//GET
String _get = "$GET;";
//POST
String _post = "$PUT;";

boolean alarm = false;
int led = 13;
int alarmPin = 53;

boolean tryAgain = true;
String response;

// ###########################################################################
// SETUP
// ###########################################################################

void setup(){
    //delay(10000);

    Serial.begin(9600);
    Serial.println("----------------------------------");
    Serial3.begin(9600);
    pinMode(led, OUTPUT);
      
    vw_set_tx_pin(10);								//10
    vw_set_rx_pin(9);								//9
    vw_setup(1200);             
    vw_rx_start(); 
    
}

// ###########################################################################
// LOOP
// ###########################################################################

void loop(){
	while(tryAgain){
		connectGPRS();
	}
	doGet();
	doPost();
        doAlarm();
}

// ###########################################################################
// CONNECT GPRS
// ###########################################################################

void connectGPRS(){
	while(tryAgain){

		for(int j = 0; j<20;j++){
	  		digitalWrite(led, HIGH);  
	  		delay(100);              
	  		digitalWrite(led, LOW);   
	  		delay(100);              
		}
		
		sendATcommand("AT", "OK", "ERROR", 2000);
		sendATcommand("AT+CSQ", "OK", "ERROR", 2000);
		sendATcommand("AT+CGATT?", "+CGATT: 1", "ERROR", 3000);
		sendATcommand("AT+CSQ", "+CSQ:", "ERROR", 2000);
		sendATcommand("AT+SAPBR=3,1,\"CONTYPE\",\"GPRS\"", "OK", "ERROR", 3000);
		sendATcommand("AT+SAPBR=3,1,\"APN\",\"umts\"", "OK", "ERROR", 3000); 
		sendATcommand("AT+SAPBR=1,1", "OK", "ERROR", 3000); 
		sendATcommand("AT+CIPMUX=0", "OK", "ERROR", 3000);
		sendATcommand("AT+CIPMODE=0", "OK", "ERROR", 3000); //Seleção do modo transparente de envio e recebimento de informações.
		sendATcommand("AT+CSTT=\"umts\"", "OK", "ERROR", 10000); 
		sendATcommand("AT+CIICR", "OK", "ERROR", 10000);
		sendATcommand("AT+CIFSR", "OK", "ERROR", 10000);
		sendATcommand("AT+CIPSTART=\"TCP\",\"184.107.114.5\",\"3334\"", "OK", "ERROR", 10000);

		//LOGIN
		sendDataSockets(_login, 4000);
		delay(100);
		int answer = readSerialWithTimeout("200 OK", "401", 6000);
		//ou //CONNECT OK

		if(answer == 0){
			Serial.println("[ERROR] CONNECTION");
			tryAgain = true;                            
		}

		if(answer == 1){
			Serial.println("200 OK");
			tryAgain = false;                            
			break;
		}

		if(answer == 2){
			Serial.println("401");
			tryAgain = true;
			//TODO
		}

		delay(5000);

	}
}

// ###########################################################################
// SEND AT COMMAND
// ###########################################################################

int sendATcommand(String ATcommand, String expectedAnswer, String expectedError, unsigned int timeout){
    delay(5);	
	Serial3.flush();
    while(Serial3.available() > 0){ Serial3.read();}    																// Clean the input buffer

    Serial3.println(ATcommand);    																						// Send the AT command 

    return readSerialWithTimeout(expectedAnswer, expectedError, timeout);
}


// ###########################################################################
// GET
// ###########################################################################

void doAlarm(){
    if(alarm){
        for(int i=0;i<10;i++){
            for (int x=0; x<180; x++){
                float sinVal = (sin(x*(3.1416/180)));
                int toneVal = 1500+(int(sinVal*900));
                tone(alarmPin, toneVal);  
                delay(2);
            }
        }
        //noTone(alarmPin);
    }else{
        noTone(alarmPin);
    }
}
void doGet(){
	sendDataSockets(_get, 5000);
	int answer = readSerialWithTimeout("},", "ERROR", 4000);//false; le ateh aih
	
        /*Serial.print(" (");
        Serial.print(answer);
        Serial.println(") ");
        Serial.print("->");
        Serial.print(response);
        Serial.println("<-");*/
        
	//if(answer == 1){
              
		if(searchString(response, "\"lamp\":true")){

			Serial.println("---LIGOU---");
			digitalWrite(led, HIGH);  

			for(int k=0; k<5; k++){
				send("{\"lamp\":1}");
				Serial.print(k);
				delay(10);
			}
			Serial.println("");                  
		}
		if(searchString(response, "\"lamp\":false")){

			Serial.print("----OFF----");
			digitalWrite(led, LOW); 

			for(int k=0; k<5;k++){
				send("{\"lamp\":0}");
				Serial.print(k);
				delay(10);
			}
			Serial.println("");
		}

                if(searchString(response, "\"alarmSensor\":true") 
                    && searchString(response, "\"alarm\":true")){
                    Serial.println("----ALARM----");
                    alarm = true;
                    doAlarm();                
                }else{
                    alarm = false;
                    noTone(alarmPin);
                }
	//}
}

// ###########################################################################
// POST
// ###########################################################################

void doPost(){
 	uint8_t message[VW_MAX_MESSAGE_LEN];    
        uint8_t msgLength = VW_MAX_MESSAGE_LEN; 
	
	String post[2];
	post[0] = NULL;
	post[1] = NULL;
	

        long previous = millis();
	int flag = 1;
	unsigned timeout = 1100;
        do{
		if (vw_get_message(message, &msgLength)){
			
			Serial.print("\nRecebido: ");
			
			for (int i = 0; i < msgLength; i++){
				Serial.print((char) (message[i]));

				if(message[i] == '{' && post[0] == NULL){
					
					for(int n=0; n < msgLength; n++){
						post[0] += (char) (message[n]);
					}

				}else if(message[msgLength-1] == '}' && post[1] == NULL){
					
					for(int n=0; n < msgLength; n++){
						post[1] += (char) (message[n]);
					}

				}
			}
                        
		}
    }while((post[0] == NULL || post[1] == NULL) && ((millis() - previous) < timeout));
                        
                        Serial.print("\npost0=");
                   Serial.println(post[0]); 
               Serial.print("post1=");    
                   Serial.println(post[1]);     
                   
	if(post[0] != NULL && post[1] != NULL){
		Serial.println("Dados a enviar");
		Serial.print(post[0]);
		Serial.print(post[1]);
                Serial.println("***");

                          
		String temp = "";
		temp = _post;
		temp += post[0];
		temp += post[1];
                temp+=";";
                Serial.print("Enviando=");
                Serial.print(temp);
                Serial.println("@@@");
		sendDataSockets(temp, 3000);	
	}
        
}

// ###########################################################################
// READ SERIAL WITH TIMEOUT (int)
// ###########################################################################

int readSerialWithTimeout(String expectedAnswer, String expectedError, unsigned int timeout){
	response = "";
	char bytes;
	int answer = 0; //0 = no response, 1 = okay, 2 = error
	unsigned long previous;
	
	delay(5);	
	Serial3.flush();
    while(Serial3.available() > 0){ Serial3.read();}    					// Clean the input buffer

    previous = millis();

    do{
        if(Serial3.available() > 0){
			bytes = Serial3.read();    
                        response += bytes;
           
			if(searchString(response, expectedAnswer)){
				answer = 1;
				break;				
			}
			if(searchString(response, expectedError)){
				answer = 2;
				break;
			}
            
        }
    }
    // Waits for the asnwer with time out
    while((answer == 0) && ((millis() - previous) < timeout));    
	Serial.println(response);	
	
	return answer;
}


// ###########################################################################
// SEARCH STRING
// ###########################################################################

boolean searchString(String original, String ocorrence){
    	int found = 0;
	int index_occ=0;
 
	if(original.length() > 0){
 
		for(int i=0; i< original.length() && found == 0; i++){
 
			if(original[i] == ocorrence[index_occ]){
				index_occ++;
 
			}else{
				index_occ=0;
			}
 
			if(index_occ == ocorrence.length()){
				found = 1;
				return true;
			}
 
		}
	}
 
	if(found == 0){
		return false;
	}
 
	return false;
    
}


// ###########################################################################
// SEND DATA SOCKETS
// ###########################################################################

void sendDataSockets(String data, unsigned timeout){
    if(sendATcommand("AT+CIPSEND", ">", "ERROR", timeout) == 1){
        delay(10);        
        Serial3.println(data);
        Serial3.write(0x1a);
		delay(10);
    }else{
        Serial.println("[ERROR] AT+CIPSEND");
    }
}

// ###########################################################################
// SEND RF
// ###########################################################################

void send (char *message){
	vw_send((uint8_t *)message, strlen(message));
	vw_wait_tx(); // Aguarda o envio de dados
}
