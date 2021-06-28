package core.qr;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class QRCodeRead {

    /**
     *
     * @param path Полное имя файла, включая расширение
     * @param charset
     * @param hashMap
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotFoundException
     */
    public static String readQR(String path, String charset,
                                Map hashMap)
            throws FileNotFoundException, IOException,
            NotFoundException
    {
        BinaryBitmap binaryBitmap
                = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(
                                new FileInputStream(path)))));

        Result result
                = new MultiFormatReader().decode(binaryBitmap);

        return result.getText();
    }


    public static String main(String path) throws IOException {
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);

        try {
            return readQR(path, charset, hashMap);
        } catch (NotFoundException e) {
            e.printStackTrace();
            out.println("Файл " + path + " не найден!");
        }
        return charset;
    }
}
