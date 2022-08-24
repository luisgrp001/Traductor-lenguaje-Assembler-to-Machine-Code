/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asmtomachcode;
import java.io.*;
import java.util.*;
import java.lang.*;
/**
 *
 * @author fampa
 */
public class Ensamblador {
    private String instruccion;
    private String codigo;


    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
    //////////////////////////////////////////////////////////////////////
    
    public void machineCode(String instruccion){
        String k,p1;
        String[] wordsOrigin;
        String[] words = null;
        char firstLetter,d;
        instruccion=instruccion.toUpperCase();//convierte toda la instruccion en mayusculas
        instruccion=instruccion.replaceAll("^\\s*", "");//elimina espacios al inicio si los hay
        firstLetter=instruccion.charAt(0);//obtener el primer caracter de la instruccion
        System.out.println("Su instrucción es: "+instruccion);
        int index=instruccion.indexOf(";");//obtener el indice para eliminar comentarios de codigo ASM
        System.out.println("El indice de ; es "+index);
        
        if(index!=-1){//si la instruccion tiene comenarios
            String instrWithoutComments=instruccion.substring(0, index);//obtener la instruccion sin comentarios
            wordsOrigin=instrWithoutComments.split(",|\\s+|;");//separa palabras de instruccion por espacios o coma
        }else{//si la instruccion no tiene comentarios
            wordsOrigin=instruccion.split(",|\\s+|;");//separa palabras de instruccion por espacios o coma
        }
        
        int contEmptyValues=0;//contador de valores vacios en el arreglo de instruccion
        for(int i=0;i<wordsOrigin.length;i++){
            if(wordsOrigin[i].equals("")){//si el arreglo contiene espacios vacios incrementa contador
                contEmptyValues++;
            }
            System.out.println(wordsOrigin[i]);
        }
        if(contEmptyValues>0){
            int tam=wordsOrigin.length-contEmptyValues;//calcula tamaño de arreglo válido
            words=new String[tam];
            int j=0;
            for(int i=0;i<wordsOrigin.length;i++){
                if(wordsOrigin[i].equals("")){//si en el arreglo original hay valores vacios, no copiar al arreglo valido
                }else{
                    words[j]=wordsOrigin[i];//copia valor de arreglo original en el arreglo valido
                    j++;//incrementa contador de posicion de arreglo valido
                    //System.out.println(words[j]);
                }
            }
        }else{
            words=wordsOrigin;
        }

        for(int i=0;i<words.length;i++){
            System.out.println(words[i]);
        }
        
        switch(firstLetter){
            case 'A':
                System.out.println("Tu instruccion inicia con A");
                switch(words[0]){
                    case "ADDLW":
                        if(words.length==2 && words[1]!=null){
                            p1="111110";
                            String p2=hexBinDecConvertToK(words[1]);//convertir valor de K en binario
                            p2=validKValue(p2,256,8);//validar que p2 es un valor entre 0 y 256, y que esté formado por 8 bits
                            codigo=p1+p2;
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "ADDWF":
                        p1="000111";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "ANDLW":
                        if(words.length==2 && words[1]!=null){
                            p1="111001";
                            String p2=hexBinDecConvertToK(words[1]);//convertir valor de K en binario
                            p2=validKValue(p2,256,8);//validar que p2 es un valor entre 0 y 256, y que esté formado por 8 bits
                            codigo=p1+p2;
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "ANDWF":
                        p1="000101";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    default:
                        error();
                }
                break;
            case 'B':
                System.out.println("Tu instruccion inicia con B");
                switch(words[0]){
                    case "BCF":
                        p1="0100";
                        codigo=mnemonic_F_B(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "BSF":
                        p1="0101";
                        codigo=mnemonic_F_B(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "BTFSC":
                        p1="0110";
                        codigo=mnemonic_F_B(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "BTFSS":
                        p1="0111";
                        codigo=mnemonic_F_B(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    default:
                        error();
                }
                break;
            case 'C':
                System.out.println("Tu instruccion inicia con C");
                switch(words[0]){
                    case "CALL":
                        if(words.length==2 && words[1]!=null){
                            p1="100";
                            validCharacters(words[1]);
                            codigo=p1+"11111111111";
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "CLRF":
                        p1="000001";
                        codigo=mnemonic_F(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "CLRW":
                        if(words.length==1 && words[0]!=null){
                            p1="000001";
                            codigo=p1+"0"+"0000000";
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "CLRWDT":
                        if(words.length==1 && words[0]!=null){
                            p1="000000";
                            codigo=p1+"01100100";
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "COMF":
                        p1="001001";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    default:
                        error();
                }
                break;
            case 'D':
                System.out.println("Tu instruccion inicia con D");
                switch(words[0]){
                    case "DECF":
                        p1="000011";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "DECFSZ":
                        p1="001011";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    default:
                       error();
                }
                break;
            case 'G':
                System.out.println("Tu instruccion inicia con G");
                switch(words[0]){
                    case "GOTO":
                        if(words.length==2 && words[1]!=null){
                            p1="101";
                            validCharacters(words[1]);
                            codigo=p1+"11111111111";
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    default:
                        error();
                }
                break;
            case 'I':
                System.out.println("Tu instruccion inicia con I");
                switch(words[0]){
                    case "INCF":
                        p1="001010";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "INCFSZ":
                        p1="001111";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "IORLW":
                        if(words.length==2 && words[1]!=null){
                            p1="111000";
                            String p2=hexBinDecConvertToK(words[1]);//convertir valor de K en binario
                            p2=validKValue(p2,256,8);//validar que p2 es un valor entre 0 y 256, y que esté formado por 8 bits
                            codigo=p1+p2;
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "IORWF":
                        p1="000100";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    default:
                        error();
                }
                break;
            case 'M':
                System.out.println("Tu instruccion inicia con M");
                switch(words[0]){
                    case "MOVF":
                        p1="001000";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "MOVLW":
                        if(words.length==2 && words[1]!=null){
                            p1="110000";
                            String p2=hexBinDecConvertToK(words[1]);//convertir valor de K en binario
                            p2=validKValue(p2,256,8);//validar que p2 es un valor entre 0 y 256, y que esté formado por 8 bits
                            codigo=p1+p2;
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "MOVWF":
                        p1="000000";
                        codigo=mnemonic_F(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    default:
                        error();
                }
                break;
            case 'N':
                System.out.println("Tu instruccion inicia con N");
                switch(words[0]){
                    case "NOP":
                        if(words.length==1 && words[0]!=null){
                            p1="000000";
                            codigo=p1+"0"+"00"+"00000";
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    default:
                        error();
                }
                break;
            case 'R':
                System.out.println("Tu instruccion inicia con R");
                switch(words[0]){
                    case "RETFIE":
                        if(words.length==1 && words[0]!=null){
                            p1="000000";
                            codigo=p1+"00001001";
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "RETLW":
                        if(words.length==2 && words[1]!=null){
                            p1="110100";
                            String p2=hexBinDecConvertToK(words[1]);//convertir valor de K en binario
                            p2=validKValue(p2,256,8);//validar que p2 es un valor entre 0 y 256, y que esté formado por 8 bits
                            codigo=p1+p2;
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "RETURN":
                        if(words.length==1 && words[0]!=null){
                            p1="000000";
                            codigo=p1+"00001000";
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "RLF":
                        p1="001101";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "RRF":
                        p1="001100";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    default:
                        error();
                }
                break;
            case 'S':
                System.out.println("Tu instruccion inicia con S");
                switch(words[0]){
                    case "SUBWF":
                        p1="000010";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "SWAPF":
                        p1="001110";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "SLEEP":
                        if(words.length==1 && words[0]!=null){
                            p1="000000";
                            codigo=p1+"01100011";
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    case "SUBLW":
                        if(words.length==2 && words[1]!=null){
                            p1="111100";
                            String p2=hexBinDecConvertToK(words[1]);//convertir valor de K en binario
                            p2=validKValue(p2,256,8);//validar que p2 es un valor entre 0 y 256, y que esté formado por 8 bits
                            codigo=p1+p2;
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    default:
                        error();
                }
                break;
            case 'X':
                System.out.println("Tu instruccion inicia con X");
                switch(words[0]){
                    case "XORWF":
                        p1="000110";
                        codigo=mnemonic_F_D(p1,words);
                        System.out.println("Codigo maquina: "+codigo);
                        break;
                    case "XORLW":
                        if(words.length==2 && words[1]!=null){
                            p1="111010";
                            String p2=hexBinDecConvertToK(words[1]);//convertir valor de K en binario
                            p2=validKValue(p2,256,8);//validar que p2 es un valor entre 0 y 256, y que esté formado por 8 bits
                            codigo=p1+p2;
                            System.out.println("Codigo maquina: "+codigo);
                        }else{
                            error();
                        }
                        break;
                    default:
                        error();
                }
                break;
            case ';':
                System.out.println("Esto es un comentario");
                break;
            default:
                error();
        } 
    }
    
    
    public void error(){
        System.out.println("ERROR DE SINTAXIS");
        //System.exit(0);
        String msg="ERROR DE SINTAXIS!!!";
        codigo=msg;
    }
    
    public String destinationBit(String d){
        char dest=d.charAt(0);
        String p2="";
        if(dest=='W'||dest=='0'){
            p2="0";
        }else if(dest=='F'||dest=='1'){
            p2="1";
        }else{
            error();
        }
        return p2;
    }
    
    public void validCharacters(String cad){
        String specialChars="_";//valida los caracteres validos para declarar variables
            char caracter1;
            boolean specialChar=false;
            for(int i=0;i<cad.length();i++){
                caracter1=cad.charAt(i);
                if(Character.isLetter(caracter1)||specialChars.contains(String.valueOf(caracter1))||Character.isDigit(caracter1)){
                    specialChar=true;
                }else{
                    error();
                }
            }
    }
    
    public String hexDecBinToBinary(String f){
        String binary="";
        String file=f.replaceAll("[\\s+]", "");//elimina espacios de la cadena
        String defFormat=file.substring(0, 2);//obtener los dos primeros caracteres
        int base=0;
        if(defFormat.equals("0X")){//define formato como hexadecimal
            file=f.replaceAll("[\\s+]", "");
            int tamCad=file.length();
            file=file.substring(2, tamCad);//omitir '0X' en la cadena
            base=16;
        }else if(defFormat.equals("D'")){//define formato como decimal
            file=f.replaceAll("['D'\\s+]", "");
            base=10;
        }else if(defFormat.equals("B'")){
            file=f.replaceAll("['B'\\s+]", "");//define formato como binario
            if(file.length()==8){
                base=2;
            }else{
                error();
            }
        }else{
            validCharacters(f);
        }
            
        try{
            int num;
            num=Integer.parseInt(file, base);
            binary=Integer.toBinaryString(num);
        }catch(NumberFormatException ex){
            binary="1111111";
        }   
        return binary;
    }
    
    public String[] getP2andP3Values(String instr[]){
        String p2="",p3="";
        String[] valuesP2P3=new String[2];
        //System.out.println(instr.length);
        if(instr.length==3 && instr[1]!=null && instr[2]!=null){
            p2=destinationBit(instr[2]); //obtiene 'd'
            p3=hexDecBinToBinary(instr[1]);//obtiene 'fffffff'
        }else if(instr.length==2 && instr[1]!=null){
            p2="1";//el destino será el registro 'f'
            p3=hexDecBinToBinary(instr[1]);//obtiene 'fffffff'
        }else{
            error();
        }
        valuesP2P3[0]=p2;
        valuesP2P3[1]=p3;
        return valuesP2P3;
    }
    
    public void validFileValue(String valueOfF){
        int decimal=Integer.parseInt(valueOfF,2);
        System.out.println("Decimal: "+decimal);
        if(decimal>=0 && decimal<128){  
        }else{
            error();
        }
    }
    
    public String sevenBitsForF(String valueOfF){
        String sevenBitsForF=valueOfF;
        //System.out.println("Tamaño: "+valueOfF.length());
        if(valueOfF.length()<7){
                int addChar=7-valueOfF.length();
                for(int i=0;i<addChar;i++){
                    sevenBitsForF="0"+sevenBitsForF;
                }
        }
        return sevenBitsForF;
    }
    
    public String bitPosition(String valueOfB){
        int decimal=Integer.parseInt(valueOfB,10);//convierte String en numero
        String binary="";
        System.out.println("Decimal: "+decimal);
        if(decimal>=0 && decimal<=7){//si el decimal esta entre 0 y 7 convierte a binario
            binary=Integer.toBinaryString(decimal);
        }else{
            error();
        }
        if(binary.length()<3){
            int addChar=3-binary.length();
            for(int i=0;i<addChar;i++){//agrega los 0's a la izquierda si es necesario
                binary="0"+binary;
            }
        }
        return binary;
    }
    
    public String hexBinDecConvertToK(String valueOfK){
        String binary="";
        String k=valueOfK.replaceAll("[\\s+]", "");//elimina espacios de la cadena
        String defFormat=k.substring(0, 2);//obtener los dos primeros caracteres
        int base=0;
        if(defFormat.equals("0X")){//define formato como hexadecimal
            k=valueOfK.replaceAll("[\\s+]", "");
            int tamCad=k.length();
            k=k.substring(2, tamCad);//omitir '0X' en la cadena
            base=16;
        }else if(defFormat.equals("D'")){//define formato como decimal
            k=valueOfK.replaceAll("['D'\\s+]", "");
            base=10;
        }else if(defFormat.equals("B'")){
            k=valueOfK.replaceAll("['B'\\s+]", "");//define formato como binario
            if(k.length()==8){
                base=2;
            }else{
                error();
            }
        }else{
            error();
        }
            
        try{
            int num;
            num=Integer.parseInt(k, base);
            binary=Integer.toBinaryString(num);
        }catch(NumberFormatException ex){
            error();
        }   
        return binary;
    } 
    
    public String validKValue(String valueOfK,int limit,int numBits){
        String eightBits="";
        int decimal=Integer.parseInt(valueOfK,2);
        System.out.println("Decimal: "+decimal);
        if(decimal>=0 && decimal<limit){
            eightBits=valueOfK;
            if(valueOfK.length()<numBits){
                int addChar=numBits-valueOfK.length();
                for(int i=0;i<addChar;i++){
                    eightBits="0"+eightBits;
                }
        }
        }else{
            error();
        }
        return eightBits;
    }
    
    public String mnemonic_F_D(String p1,String[] words){
        String[] valuesP2P3;
        String p2,p3,code; 
        valuesP2P3=getP2andP3Values(words);//obtener los binarios de F y D
        p2=valuesP2P3[0];//binario de D
        p3=valuesP2P3[1];//binario de F
        validFileValue(p3);//valida si F es un binario de 7 bits
        p3=sevenBitsForF(p3);//completar los 7 bits de F con 0's a la izquierda si es necesario
        code=p1+p2+p3;//une los 3 binarios obtenidos
        return code;
    }
    
    public String mnemonic_F(String p1,String[] words){
        String p2="1",p3="",code;
        if(words.length==2 && words[1]!=null){
            p3=hexDecBinToBinary(words[1]);//obtiene 'fffffff'
        }else{
            error();
        }
        validFileValue(p3);//valida si F es un binario de 7 bits
        p3=sevenBitsForF(p3);//completar los 7 bits de F con 0's a la izquierda si es necesario
        code=p1+p2+p3;//une los 3 binarios obtenidos
        return code;
    }
    
    public String mnemonic_F_B(String p1,String[] words){
        String p2="",p3="",code="";
        if(words.length==3 && words[1]!=null && words[2]!=null){
            p2=bitPosition(words[2]); //obtiene 'b'
            p3=hexDecBinToBinary(words[1]);//obtiene 'fffffff'
        }else{
            error();
        }
        validFileValue(p3);//valida si F es un binario de 7 bits
        p3=sevenBitsForF(p3);//completar los 7 bits de F con 0's a la izquierda si es necesario
        code=p1+p2+p3;//une los 3 binarios obtenidos
        return code;
    }
    
}
