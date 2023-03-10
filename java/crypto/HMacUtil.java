package crypto;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.encoders.Base64;

public class HMacUtil {
	public static final int MAC_LENGTH = 256;
	
	public KeyParameter getSecretKey(String password, String salt){
		PBEParametersGenerator generator = new PKCS5S2ParametersGenerator();
	    generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password.toCharArray()), salt.getBytes(), 1024);
	    KeyParameter params = (KeyParameter)generator.generateDerivedParameters(MAC_LENGTH);
		
		return params;		
	}
	
	public byte[] generateHMacBytes(KeyParameter key, byte[] byteData){
		HMac hmac = new HMac(new SHA256Digest());
		hmac.init(key);
		hmac.update(byteData, 0, byteData.length);
		byte[] result = new byte[hmac.getMacSize()];
		hmac.doFinal(result, 0);
		
		return result;
	}
	
	public String generateHMacString(KeyParameter key, String input) throws UnsupportedEncodingException{
		byte[] inputBytes = input.getBytes("UTF-8");
		byte[] outputBytes = generateHMacBytes(key, inputBytes);
		return Base64.toBase64String(outputBytes);
	}
}
