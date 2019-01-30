

import java.math.BigInteger ;
import java.util.* ;
import java.io.* ;
import java.sql.*;

/**
 * Class for RSA Algorithm (RSA.java).
 *
 * Generates Prime numbers and Public/Private Keys. Performs Encryption and Decryption.
 *
 * @author  Chue Wai Lian
 * @version
 *
 * 1.0.0	11 Apr 2001
 * <br>		1st release.
 */
public class RSA
{
	/**
	 * Bit length of each prime number.
	 */
	int primeSize ;
            
	/**
	 * Two distinct large prime numbers p and q.
	 */
	BigInteger p, q ;

	/**
	 * Modulus N.
	 */
	BigInteger N ;

	/**
	 * r = ( p - 1 ) * ( q - 1 )
	 */
	BigInteger r ;

	/**
	 * Public exponent E and Private exponent D
	 */
	BigInteger E, D ;


	/**
	 * Constructor.
	 *
	 * @param	primeSize		Bit length of each prime number.
	 */
	public RSA( int primeSize )
	{
		this.primeSize = primeSize ;

		// Generate two distinct large prime numbers p and q.
		generatePrimeNumbers() ;

		// Generate Public and Private Keys.
		generatePublicPrivateKeys() ;
	}


	/**
	 * Generate two distinct large prime numbers p and q.
	 */
	public void generatePrimeNumbers()
	{
            //System.out.println
            System.out.println("Starting time is ");
            long start=getCurrentTime();
	
		
		p = new BigInteger( primeSize, 10, new Random() ) ;


		do
		{
			q = new BigInteger( primeSize, 10, new Random() ) ;
		}
		while( q.compareTo( p ) == 0 ) ;
                System.out.println("Prime number search competed at");
            long end=getCurrentTime();
            System.out.println("Total elapsed time= "+(new Timestamp(end-start)).getNanos());
            
                
	}
        public long getCurrentTime()
        {
                java.util.Date date=Calendar.getInstance().getTime();
          Timestamp a=new Timestamp(date.getTime()); 
          long currentTime=Calendar.getInstance().getTimeInMillis();
                System.out.println("Time: "+currentTime);
                
                return currentTime;
        }
            


	/**
	 * Generate Public and Private Keys.
	 */
	public void generatePublicPrivateKeys()
	{
		// N = p * q
		N = p.multiply( q ) ;


		// r = ( p - 1 ) * ( q - 1 )
		r = p.subtract( BigInteger.valueOf( 1 ) ) ;
		r = r.multiply( q.subtract( BigInteger.valueOf( 1 ) ) ) ;


		// Choose E, coprime to and less than r
		do
		{
			E = new BigInteger( 2 * primeSize, new Random() ) ;
		}
		while( ( E.compareTo( r ) != -1 ) || ( E.gcd( r ).compareTo( BigInteger.valueOf( 1 ) ) != 0 ) ) ;


		// Compute D, the inverse of E mod r
		D = E.modInverse( r ) ;
	}


	/**
	 * Encrypts the plaintext (Using Public Key).
	 *
	 * @param	message			String containing the plaintext message to be encrypted.
	 * @return	The ciphertext as a BigInteger array.
	 */
	public BigInteger[] encrypt( String message )
	{
		int i ;
		byte[] temp = new byte[1] ;


		byte[] digits = message.getBytes() ;

		BigInteger[] bigdigits = new BigInteger[digits.length] ;

		for( i = 0 ; i < bigdigits.length ; i++ )
		{
			temp[0] = digits[i] ;
			bigdigits[i] = new BigInteger( temp ) ;
		}

		BigInteger[] encrypted = new BigInteger[bigdigits.length] ;

		for( i = 0 ; i < bigdigits.length ; i++ )
			encrypted[i] = bigdigits[i].modPow( E, N ) ;


		return( encrypted ) ;
	}


	/**
	 * Decrypts the ciphertext (Using Private Key).
	 *
	 * @param	encrypted		BigInteger array containing the ciphertext to be decrypted.
	 * @return	The decrypted plaintext.
	 */
	public String decrypt( BigInteger[] encrypted )
	{
		int i ;


		BigInteger[] decrypted = new BigInteger[encrypted.length] ;

		for( i = 0 ; i < decrypted.length ; i++ )
			decrypted[i] = encrypted[i].modPow( D, N ) ;

		char[] charArray = new char[decrypted.length] ;

		for( i = 0 ; i < charArray.length ; i++ )
			charArray[i] = (char) ( decrypted[i].intValue() ) ;


		return( new String( charArray ) ) ;
	}


	/**
	 * Get prime number p.
	 *
	 * @return	Prime number p.
	 */
	public BigInteger getp()
	{
		return( p ) ;
	}


	/**
	 * Get prime number q.
	 *
	 * @return	Prime number q.
	 */
	public BigInteger getq()
	{
		return( q ) ;
	}


	/**
	 * Get r.
	 *
	 * @return	r.
	 */
	public BigInteger getr()
	{
		return( r ) ;
	}


	/**
	 * Get modulus N.
	 *
	 * @return	Modulus N.
	 */
	public BigInteger getN()
	{
		return( N ) ;
	}


	/**
	 * Get Public exponent E.
	 *
	 * @return	Public exponent E.
	 */
	public BigInteger getE()
	{
		return( E ) ;
	}


	/**
	 * Get Private exponent D.
	 *
	 * @return	Private exponent D.
	 */
	public BigInteger getD()
	{
		return( D ) ;
	}



	/**
	 * RSA Main program for Unit Testing.
	 */
	public static void main( String[] args ) throws IOException
	{
		if( args.length != 1 )
		{
			System.out.println( "Syntax: java RSA PrimeSize" ) ;
			System.out.println( "e.g. java RSA 8" ) ;
			System.out.println( "e.g. java RSA 512" ) ;
      
			//System.exit( -1 ) ;
		}else
    {
      args=new String[1];
      args[0]="1024";
    }
      args=new String[1];
      //args[0]="512";
      args[0]="1024";
		// Get bit length of each prime number
		int primeSize = Integer.parseInt( args[0] ) ;


		// Generate Public and Private Keys

		RSA rsa = new RSA( primeSize ) ;

		System.out.println( "Key Size: [" + primeSize + "]" ) ;
		System.out.println( "" ) ;

		System.out.println( "Generated prime numbers p and q" ) ;
		System.out.println( "p: [" + rsa.getp().toString( 16 ).toUpperCase() + "]" ) ;
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
		System.out.println( "Please enter message (plaintext):" ) ;
		String plaintext = ( new BufferedReader( new InputStreamReader( System.in ) ) ).readLine() ;
		System.out.println( "" ) ;

		// Encrypt Message
		BigInteger[] ciphertext = rsa.encrypt( plaintext ) ;

		System.out.print( "Ciphertext: [" ) ;
		for( int i = 0 ; i < ciphertext.length ; i++ )
		{
			System.out.print( ciphertext[i].toString( 16 ).toUpperCase() ) ;

			if( i != ciphertext.length - 1 )
				System.out.print( " " ) ;
		}
		System.out.println( "]" ) ;
		System.out.println( "" ) ;


		String recoveredPlaintext = rsa.decrypt( ciphertext ) ;

		System.out.println( "Recovered plaintext: [" + recoveredPlaintext + "]" ) ;
	}
}

