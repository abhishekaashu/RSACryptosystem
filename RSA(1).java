
import java.math.BigInteger ;
import java.util.* ;
import java.io.* ;
import java.sql.*;

/********************************************************
   1. Generate an RSA Public-Private key pair.
   2. Encrypt an arbitrary message using the public key.
   3. Decrypt the message using the private key. 

Experiment the above with
   1. Different key sizes (128, 256, 512, 768, 1024 bits) and determine the time for each of the above operations as a function of key size.
   2. Different message sizes from 0 to 4KB for any two key sizes of your choice and determine the time for encrypting/decrypting those messages as a function of message size.
   3. Different values for the "probability of P, Q being prime" variable in the constructor of the BigInteger class and determine the time for key generation for different value of this variable.  

 In addition to the time measurements for key generation, encryption and decryption AND analysis of results please submit an appendix which includes:

    * Your Java code.
    * A sample key and message with key generation time and encryption / decryption time.
    * A script file to execute your program. 
********************************************************/

public class RSA  {

	int primeSize ;				/** * Bit length of each prime number.  */
	BigInteger p, q ;			/** * Two distinct large prime numbers p and q.  */
	BigInteger N ;				/** * Modulus N.  */
	BigInteger r ; 				/** * r = ( p - 1 ) * ( q - 1 ) */
	BigInteger E, D ; 			/** * Public exponent E and Private exponent D */
        int PF ; 				/** * Probability factor used in Prime generation */
        FileOutputStream OutStream; 		/** declare a file output object  */
        PrintStream PStream; 			/** declare a print stream object  */

        /*--------------------------------------------------------------------------------------------------*/
	/** * Constructor.  * * @param	primeSize Bit length of each prime number.  */
        /*--------------------------------------------------------------------------------------------------*/
	public RSA( int primeSize , int probfactor , String filename) {
		this.primeSize = primeSize ;
                PF = probfactor;

                InitPrintFile(filename);  
                PStream.print (primeSize*2 + "	");  /* Keysize = 2*primeSize */

		// Generate two distinct large prime numbers p and q.
		generatePrimeNumbers() ;

		// Generate Public and Private Keys.
		generatePublicPrivateKeys() ;
	}


        /*--------------------------------------------------------------------------------------------------*/
	/** * Generate two distinct large prime numbers p and q.  */
        /*--------------------------------------------------------------------------------------------------*/
	public void generatePrimeNumbers() {
        	    //System.out.println
	            //System.out.println("(Prime generation) starting time is ");

	            long start=getCurrentTime();
  		    p = new BigInteger( primeSize, PF, new Random() ) ;
	
	   	    do {
			q = new BigInteger( primeSize, PF, new Random() ) ;
		    }
		    while( q.compareTo( p ) == 0 ) ;
	
	            //System.out.println("Prime number search competed at");
	            long end=getCurrentTime();
	            System.out.println("(Prime generation) Total elapsed time= "+(new Timestamp(end-start)).getNanos());
	}

        /*--------------------------------------------------------------------------------------------------*/
        /** * Get Current time.      */
        /*--------------------------------------------------------------------------------------------------*/
        public long getCurrentTime() {
	          java.util.Date date=Calendar.getInstance().getTime();
        	  Timestamp a=new Timestamp(date.getTime()); 
	          long currentTime=Calendar.getInstance().getTimeInMillis();
          
	          // System.out.println("Time: "+currentTime);
                   return currentTime;
        }
            

        /*--------------------------------------------------------------------------------------------------*/
	/** * Generate Public and Private Keys.  */
        /*--------------------------------------------------------------------------------------------------*/
	public void generatePublicPrivateKeys() {
		// N = p * q
                // System.out.println("(Gen Pub Pri Keys) Starting time is ");
	        long start=getCurrentTime();

		N = p.multiply( q ) ;

		// r = ( p - 1 ) * ( q - 1 )
		r = p.subtract( BigInteger.valueOf( 1 ) ) ;
		r = r.multiply( q.subtract( BigInteger.valueOf( 1 ) ) ) ;

		// Choose E, coprime to and less than r
		do {
			E = new BigInteger( 2 * primeSize, new Random() ) ;
		}
		while( ( E.compareTo( r ) != -1 ) || ( E.gcd( r ).compareTo( BigInteger.valueOf( 1 ) ) != 0 ) ) ;


		// Compute D, the inverse of E mod r
		D = E.modInverse( r ) ;
	       // System.out.println("(Gen Pub Pri Keys) competed at");

                long end=getCurrentTime();
                PStream.print ((new Timestamp(end-start)).getNanos() + "	");
                System.out.println("(Gen Pub Pri Keys) Total elapsed time= "+(new Timestamp(end-start)).getNanos());
	}


        /*--------------------------------------------------------------------------------------------------*/
	/** * Encrypts the plaintext (Using Public Key).  
	 * @param	message	String containing the plaintext message to be encrypted.
	 * @return	The ciphertext as a BigInteger array.  */
        /*--------------------------------------------------------------------------------------------------*/

	public BigInteger[] encrypt( String message ) {
		int i ;
		byte[] temp = new byte[1] ;  /* array of bytes */
		byte[] digits = message.getBytes() ; /*  Encodes this String into a sequence of bytes 
							using the platform's default charset, 
							storing the result into a new byte array. */

		BigInteger[] bigdigits = new BigInteger[digits.length] ; /* for storing into new array of BigInteger */

	        // System.out.println("(Encryption) starting time is ");
                long start=getCurrentTime();

		for( i = 0 ; i < bigdigits.length ; i++ ) {
			temp[0] = digits[i] ;
			bigdigits[i] = new BigInteger( temp ) ;  /* converting and storing */
		}
		/* digits array can be deallocated now */

		BigInteger[] encrypted = new BigInteger[bigdigits.length] ; /* for storing encrypted data */

		for( i = 0 ; i < bigdigits.length ; i++ )
			encrypted[i] = bigdigits[i].modPow( E, N ) ; /* encrypting each byte and storing */
                
                /* should deallocate bigdigits array also */
               //System.out.println("(Encryption) competed at");

                long end=getCurrentTime();
                PStream.print ((new Timestamp(end-start)).getNanos() + "	");
                System.out.println("(Encryption) Total elapsed time= "+(new Timestamp(end-start)).getNanos());
    	        return( encrypted ) ;
	}


        /*--------------------------------------------------------------------------------------------------*/
	/** * Decrypts the ciphertext (Using Private Key).  *
	 * @param	encrypted		BigInteger array containing the ciphertext to be decrypted.
	 * @return	The decrypted plaintext.  */
        /*--------------------------------------------------------------------------------------------------*/
	public String decrypt( BigInteger[] encrypted ) {
		int i ;

		BigInteger[] decrypted = new BigInteger[encrypted.length] ; /* for storing decrypted data */

               // System.out.println("(Decryption) starting time is ");
               long start=getCurrentTime();

       	       for( i = 0 ; i < decrypted.length ; i++ )
			decrypted[i] = encrypted[i].modPow( D, N ) ; /* decrypting each byte */

	       char[] charArray = new char[decrypted.length] ;

	       for( i = 0 ; i < charArray.length ; i++ )
			charArray[i] = (char) ( decrypted[i].intValue() ) ;  /* converting from BigInteger to char */

               /* can deallocate decrypted array */
               // System.out.println("(Decryption) competed at");

               long end=getCurrentTime();    
               PStream.print ((new Timestamp(end-start)).getNanos() + "	");
               System.out.println("(Decryption) Total elapsed time= "+(new Timestamp(end-start)).getNanos());
   	       return( new String( charArray ) ) ;
	}


        /*--------------------------------------------------------------------------------------------------*/
	/** * Get prime number p.  * * @return	Prime number p.  */
        /*--------------------------------------------------------------------------------------------------*/

	public BigInteger getp() {
		return( p ) ;
	}


        /*--------------------------------------------------------------------------------------------------*/
	/** * Get prime number q.  * * @return	Prime number q.  */
        /*--------------------------------------------------------------------------------------------------*/

	public BigInteger getq() {
		return( q ) ;
	}


        /*--------------------------------------------------------------------------------------------------*/
	/** * Get r.  * * @return	r.   phi(n) */
        /*--------------------------------------------------------------------------------------------------*/

	public BigInteger getr() {
		return( r ) ;
	}

        /*--------------------------------------------------------------------------------------------------*/

	/** * Get modulus N.  * * @return	Modulus N.  */
        /*--------------------------------------------------------------------------------------------------*/
	public BigInteger getN() {
		return( N ) ;
	}

        /*--------------------------------------------------------------------------------------------------*/

	/** * Get Public exponent E.  * * @return	Public exponent E.  */
        /*--------------------------------------------------------------------------------------------------*/
	public BigInteger getE() {
		return( E ) ;
	}


        /*--------------------------------------------------------------------------------------------------*/
	/** * Get Private exponent D.  * * @return	Private exponent D.  */
        /*--------------------------------------------------------------------------------------------------*/
	public BigInteger getD() {
		return( D ) ;
	}

        /*--------------------------------------------------------------------------------------------------*/
        /* set the probability factor */        	
        /*--------------------------------------------------------------------------------------------------*/
        public void setPF(int probfactor) {
	 	PF = probfactor;
	}


        /*--------------------------------------------------------------------------------------------------*/
        /* return the probability factor */        	
        /*--------------------------------------------------------------------------------------------------*/
        public int getPF() {
		return(PF);
	}

        /*--------------------------------------------------------------------------------------------------*/
        /* create an output stream for writing into files */

        /*--------------------------------------------------------------------------------------------------*/
        public void InitPrintFile(String filename) {
            try
            {   
             	 OutStream = new FileOutputStream(filename);
	         // Connect print stream PStream to the output stream
                 PStream  = new PrintStream( OutStream );
             }
             catch (Exception e)
             {
                        System.err.println ("Error writing to file");
             }
        }
	
        /*--------------------------------------------------------------------------------------------------*/
        /*--------------------------------------------------------------------------------------------------*/
	/**
	 * RSA Main program for Unit Testing.
	 */
        /*--------------------------------------------------------------------------------------------------*/
        /*--------------------------------------------------------------------------------------------------*/
	public static void main( String[] args ) throws IOException {
		System.out.println( "Usage: java RSA KeySize Messagesize PFactor" ) ;
		System.out.println( "e.g. java RSA 1024 100 10" ) ;
		
		if( args.length == 0) {
		      args=new String[1];
		      args[0]="1024";
		}

		// Get bit length of each prime number
		
		int primeSize = Integer.parseInt( args[0] ) ;
                int primeFactor;
               
                if (args.length < 2)   /* prime factor not given assume it 10 */
		 	primeFactor = 10;
                else 
			primeFactor = Integer.parseInt( args[2]);
                	
		// Generate Public and Private Keys

		RSA rsa = new RSA( primeSize/2 , primeFactor , "table") ; /* generate prime nos + N , r , E ,D */
		/* user enters key size. hence prime size should be 0.5 times key size */	

		System.out.println( "Key Size: [" + primeSize + "]" ) ;
		System.out.println( "" ) ;

		System.out.println( "Prime Factor: [" + primeFactor + "]" ) ;
		System.out.println( "" ) ;

		System.out.println( "Generated prime numbers p and q" ) ;
		System.out.println( "p: [" + rsa.getp().toString( 16 ).toUpperCase() + "]" ) ; 
					/*  Returns the String representation of this BigInteger in the given radix. */
		System.out.println( "q: [" + rsa.getq().toString( 16 ).toUpperCase() + "]" ) ;
		System.out.println( "" ) ;

		System.out.println( "The public key is the pair (N, E) which will be published." ) ;
		System.out.println( "N: [" + rsa.getN().toString( 16 ).toUpperCase() + "]" ) ;
		System.out.println( "E: [" + rsa.getE().toString( 16 ).toUpperCase() + "]" ) ;
		System.out.println( "" ) ;

		System.out.println( "The private key is the pair (N, D) which will be kept private." ) ;
		System.out.println( "N: [" + rsa.getN().toString( 16 ).toUpperCase() + "]" ) ;
		System.out.println( "D: [" + rsa.getD().toString( 16 ).toUpperCase() + "]" ) ;
		System.out.println( "" ) ;

                
		// Get message (plaintext) from user
                String plaintext=" ";
 
                if (args.length <= 1) {
			System.out.println( "Enter message (plaintext):" ) ;
			plaintext = ( new BufferedReader( new InputStreamReader( System.in ) ) ).readLine() ;
                }
                else {       /* automatically generate message of given size. */
                	Integer I = new Integer(args[1]); 
	                int mesglength ;
	                mesglength = I.intValue();

		        char local_plaintext[]  = new char[mesglength];  /* message length */
			for ( int i = 0; i< mesglength ; i++)
				local_plaintext[i] = (char)(((2*i + 10) % 26) + 65);/*random message*/ //'A' = 65 'Z' = 90
			 /* between 0-25 then add 65 to get in range 65-90 */
			 /* random message */ // 'A' = 65 'Z' = 90
                        plaintext = plaintext.valueOf(local_plaintext,0,mesglength-1);
	        }
		      	
		System.out.println( "" ) ;
                System.out.println("message length:"+plaintext.length());
		System.out.println( "" ) ;
		System.out.println( "message:"+plaintext) ;
                

		// Encrypt Message
		BigInteger[] ciphertext = rsa.encrypt( plaintext ) ;

		System.out.print( "Ciphertext: [" ) ;
		for( int i = 0 ; i < ciphertext.length ; i++ ) {
			System.out.print( ciphertext[i].toString( 16 ).toUpperCase() ) ;

			if( i != ciphertext.length - 1 )
				System.out.print( " " ) ;
		}

		System.out.println( "]" ) ;
		System.out.println( "" ) ;

		String recoveredPlaintext = rsa.decrypt( ciphertext ) ;
		System.out.println( "Recovered plaintext: [" + recoveredPlaintext + "]" ) ;
                rsa.PStream.println ("");
	}
} /* End of Class RSA

        /*--------------------------------------------------------------------------------------------------*/
        /*--------------------------------------------------------------------------------------------------*/
