
void setup(){
  Serial3.begin(9600); // taxa de comm. com SIM900
  Serial.begin(9600); // taxa de comm. com Arduino-PC
}
void loop(){
  if (Serial3.available()) // serial do SIM900 disponivel?
      Serial.write(Serial3.read()); // escrevo o que recebi do Arduino-PC no SIM900
  if (Serial.available()) // porta serial do Arduino-PC disponivel?
      Serial3.write(Serial.read()); // escrevo o que recebi do SIM900 no  Arduino-PC
}


//Definição de variáveis globais.
char buffer[10] = {0};
byte byte1;
byte byte2;
int valorLido = 0;
int tentativas = 0;
int conectado = 0;

//Configuração dos pinos dos sensores e dispositivos.
int pinoSensor = 0;
int pinoDispositivo1 = 2;
int pinoDispositivo2 = 3;

//Parâmetros da comunicação serial.
char bytes;
String bufferSerial = String(128);
String bufferSerial1 = String(128);

#define INICIALIZAR_MODULO  0
#define CONECTAR_MODULO     1
#define RECEBER_COMANDO     2
#define ANALISAR_COMANDO    3
#define ENVIAR_COMANDO      4
#define LER_TEMPERATURA     5
#define FINALIZAR_MODULO    6
byte estado;

#include <SoftwareSerial.h> //inicia biblioteca serial via software
SoftwareSerial mySerial(2, 3); // atribui os pinos 2 e 3 como tx rx respectivamente

void setup()
{
  //Inicialização dos pinos de controle dos dispositivos.
  pinMode(pinoDispositivo1,OUTPUT);
  pinMode(pinoDispositivo2,OUTPUT);
  digitalWrite(pinoDispositivo1,LOW);
  digitalWrite(pinoDispositivo2,LOW);
  
  //Inicialização da porta serial do Arduino.
  Serial.begin(9600);
  Serial.println("Inicializou Serial");
  
  //Inicialização da porta serial de controle do dispositivo externo.
  mySerial.begin(9600);
  
  //Inicialização das variáveis globais.
  bufferSerial = "";
  bufferSerial1 = "";
  
  estado = INICIALIZAR_MODULO;
}

void loop ()
{
  switch(estado)
  {
    case INICIALIZAR_MODULO:
      do
      {
        //Inicialização da configuração do módulo GPRS.
        Serial.println("Inicializando o Modulo...");
       
        //Seleção do modo transparente de envio e recebimento de informações.
        mySerial.println("AT+CIPMODE=1"); 
        Serial.println("AT+CIPMODE=1"); 

        delay(5000); 
        Serial.println(LerBufferSerial());

        //Configuração do APN da conexão GPRS.
        mySerial.println("AT+CSTT=\"CMNET\""); 
        delay(30000); 
        Serial.println(LerBufferSerial());

        //Ativa a conexão GPRS.
        mySerial.println("AT+CIICR"); 
        Serial.println("AT+CIICR"); 

        delay(60000); 
        Serial.println(LerBufferSerial());

        mySerial.println("AT+CIFSR"); 
        delay(5000); 
        Serial.println(LerBufferSerial());
        
        mySerial.println("AT+CIPSTART=\"TCP\",\"200.135.224.15\",\"6001\""); 
        Serial.println("AT+CIPSTART=\"TCP\",\"200.135.224.15\",\"6001\""); 
        delay(5000); 
        Serial.println(LerBufferSerial());
        delay(10000);
      
        //Verificação do comando de conexão com o servidor.
        if (mySerial.available())
        {        
          Serial.println("Recebendo Pacote...");

          //Limpa o conteúdo do buffer serial.
          mySerial.flush();

          //Limpa o conteudo da string de comando.
          bufferSerial1 = "";

          //Realiza a leitura dos bytes existentes na porta serial.
          while (mySerial.available() > 0)
          {
            //Lê os bytes na porta serial e os retira do buffer.
            bytes = mySerial.read();
  
            //Agrupa os caracteres lidos em uma string formando o comando.
            bufferSerial1 = bufferSerial1 + bytes;
      
            //Delay para sincronismo da comunicação serial.
            delay(2);
          }
        
          //Tratamento e interpretação do pacote de dados para controle dos dispositivos.
          if (bufferSerial1.substring(0,2) == "#:" && bufferSerial1.substring(2,4) == "OK" && bufferSerial1.substring(6,7) == ";")
          {   
            Serial.println("Tentando Conectar...");
            Serial.println("Pacote Recebido: " + bufferSerial1);

            if (bufferSerial1.substring(2,4) == "OK")
            {
              Serial.println("Conexao Estabelecida...");
             
              //Formatação do Pacote de dados.
              buffer[0] = '#';
              buffer[1] = ':';
              buffer[2] = 'C';
              buffer[3] = 'N';
              buffer[4] = '0';
              buffer[5] = '0';
              buffer[6] = ';';
      
              //Envio do pacote de dados pela porta serial.
              mySerial.println(buffer);    
               
              conectado = 1;

              estado = RECEBER_COMANDO;
            }
          }
        }        
        
        if (!conectado)
        {
          Serial.println("Finalizando Modulo...");
          
          //Comando para sair do modo transparente de dados.
          mySerial.print("+++"); 
          delay(500); 
          Serial.println(LerBufferSerial());
          
          mySerial.println(" "); 
          Serial.println(LerBufferSerial());
          
          //Fecha a conexão TCP/IP com o servidor.
          mySerial.println("AT+CIPCLOSE"); 
          delay(2000); 
          Serial.println(LerBufferSerial());
          
          //Desativa todas as conexões abertas.
          mySerial.println("AT+CIPSHUT"); 
          delay(2000); 
          Serial.println(LerBufferSerial());
        }
      }
      while(conectado != 1);
    break;
    
    case RECEBER_COMANDO:
      //Verificação do comando de conexão com o servidor.
      if (mySerial.available())
      {        
        //A cada comando válido recebido a contagem para resetar a conexão é reiniciada.
        tentativas = 0;
        
        Serial.println("Recebendo Comando...");
        
        //Limpa o conteúdo do buffer serial.
        mySerial.flush();

        //Limpa o conteudo da string de comando.
        bufferSerial1 = "";

        //Realiza a leitura dos bytes existentes na porta serial.
        while (mySerial.available() > 0)
        {
          //Lê os bytes na porta serial e os retira do buffer.
          bytes = mySerial.read();
  
          //Agrupa os caracteres lidos em uma string formando o comando.
          bufferSerial1 = bufferSerial1 + bytes;
      
          //Delay para sincronismo da comunicação serial.
          delay(2);
        }

        //Limpa o conteúdo do buffer serial.
        mySerial.flush();
                
        //Tratamento e interpretação do pacote de dados para controle dos dispositivos.
        if (bufferSerial1.substring(0,2) == "#:" && bufferSerial1.substring(6,7) == ";")
        {   
          Serial.println("PACOTE VALIDO");
      
          if (bufferSerial1.substring(2,4) == "AK")
          {
            Serial.println("Recebeu Acknowledge...");
            
            estado = RECEBER_COMANDO;        
          }

          //Controle da aquisição de temperatura.
          if (bufferSerial1.substring(2,4) == "LT")
          {
            estado = LER_TEMPERATURA;
          }

          if (bufferSerial1.substring(2,4) == "EX")
          {
            tentativas = 0;
            conectado = 0;
            estado = FINALIZAR_MODULO;
          }        

          //Controle do dispositivo 01.
          if (bufferSerial1.substring(4,5) == "0")
          {
            digitalWrite(pinoDispositivo1,LOW); 
          }
          else
          {
            digitalWrite(pinoDispositivo1,HIGH);       
          }

          //Controle do dispositivo 02.
          if (bufferSerial1.substring(5,6) == "0")
          {
            digitalWrite(pinoDispositivo2,LOW); 
          }
          else
          {
            digitalWrite(pinoDispositivo2,HIGH);       
          }      
        }  
      }
      else
      {
        Serial.print("Contagem: ");
        Serial.println(tentativas, DEC);
        tentativas++;
        
        if (tentativas >= 1000)
        {
          tentativas = 0;
          conectado = 0;
          estado = FINALIZAR_MODULO;
        }
        
      }
    break;
    
    case LER_TEMPERATURA: 
      
      Serial.println("Lendo Temperatura...");

      //Leitura do sensor de temperatura
      valorLido = analogRead(pinoSensor);
          
      //Conversão de um valor de 10 bits em 2 bytes para transmissão via serial.
      byte2 = valorLido >> 8;
      byte1 = valorLido;
      
      //Tratamento de terminadores nulos da String.
      if (byte2 == '\0') byte2 = '0';
      //if (byte1 == '\0') byte1 = '0';
      
      //Formatação do Pacote de dados.
      buffer[0] = '#';
      buffer[1] = ':';
      buffer[2] = byte2;
      buffer[3] = byte1;
      buffer[4] = '0';
      buffer[5] = '0';
      buffer[6] = ';';
      
      //Envio do pacote de dados pela porta serial.
      mySerial.println(buffer);    
      
      estado = RECEBER_COMANDO;
    break;

    case FINALIZAR_MODULO: Serial.println("Finalizando o Modulo...");    
      mySerial.print("+++"); delay(500); LerBufferSerial();
      mySerial.println(" "); LerBufferSerial();
      mySerial.println("AT+CIPCLOSE"); delay(1000); LerBufferSerial();
      mySerial.println("AT+CIPSHUT"); delay(1000); LerBufferSerial();
    
      estado = INICIALIZAR_MODULO;
    break;
  }
}

void serialEvent()
{
  if (Serial.available())
  {    
    //Limpa o conteudo da string de comando.
    bufferSerial = "";
    
    //Realiza a leitura dos bytes existentes na porta serial.
    while (Serial.available() > 0)
    {
      //Lê os bytes na porta serial e os retira do buffer.
      bytes = Serial.read();
    
      //Agrupa os caracteres lidos em uma string formando o comando.
      bufferSerial = bufferSerial + bytes;
      
      //Delay para sincronismo da comunicação serial.
      delay(5);
    }
  }
}

String LerBufferSerial()
{
  //Limpa o conteúdo do buffer serial.
  mySerial.flush();

  //Limpa o conteudo da string de comando.
  String string;
  
  if (mySerial.available())
  {        
    //Realiza a leitura dos bytes existentes na porta serial.
    while (mySerial.available() > 0)
    {
      //Lê os bytes na porta serial e os retira do buffer.
      bytes = mySerial.read();
  
      //Agrupa os caracteres lidos em uma string formando o comando.
      string = string + bytes;
      
      //Delay para sincronismo da comunicação serial.
      delay(2);
    }
  }
  
  return(string);
  
  Serial.println(bufferSerial1);
}
